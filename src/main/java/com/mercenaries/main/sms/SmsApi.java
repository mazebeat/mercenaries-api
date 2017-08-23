/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mercenaries.main.sms;

import com.mercenaries.jpa.Sms;
import com.mercenaries.utils.EntityHelper;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author DiegoFeliú
 */
public class SmsApi {

    private static Logger logger = LoggerFactory.getLogger(SmsApi.class);

    private static final String ACCOUNT_SID = "ACbd792c76363e49e5ac3781632faa4022";
    private static final String AUTH_TOKEN = "323d1b0282025fb85d9d3e5b496da333";
    private static final PhoneNumber PHONE_NUMBER_FROM = new PhoneNumber("+56945950651");

    public static void main(String... args) {
        logger.info("SMS Api init");
        if (args.length < 2) {
            logger.error("Verify parameters [filename]");
            System.exit(1);
        }

        String filename = args[1];

        logger.info("Getting all SMS by file " + filename);
        List<Sms> allToSend = allByNamefile(filename);

        logger.info("Sending all messages");
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        allToSend.forEach((Sms sms) -> {
//            fetchSmsBody(sms.getAccountSid());
            Message message = sendAndGetSms(sms.getToNumber(), sms.getBodyMessage());
            if (message != null) {
                logger.info(MessageFormat.format("Updating data of [{0}]", message.getSid()));
                updateSmsByMessage(sms, message);
            }
        });
    }

    public static String sendSms(String phoneNumber, String messageBody) {
        Message message = null;
        try {
            message = Message.creator(new PhoneNumber(phoneNumber), PHONE_NUMBER_FROM, messageBody).create();
            logger.info(MessageFormat.format("Message has been sent [{0}]", message.getSid()));

            return message.getSid();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }

        return null;
        //        return "123456789asdasdasd";
    }

    public static Message sendAndGetSms(String phoneNumber, String messageBody) {
        Message message = null;
        try {
            message = Message.creator(new PhoneNumber(phoneNumber), PHONE_NUMBER_FROM, messageBody).create();
            logger.info(MessageFormat.format("Message has been sent [{0}]", message.getSid()));

            return message;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }

        return null;
    }

    public static List<Sms> allByNamefile(String filename) {
        EntityManager em = null;
        List<Sms> list = new ArrayList<>();
        try {
            em = EntityHelper.getInstance().getEntityManager();
            Query query = em.createQuery("SELECT s FROM Sms AS s WHERE s.filename = :filename", Sms.class)
                            .setParameter("filename", filename);
            list.addAll(query.getResultList());
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return list;
    }

    public static Message fetchSms(String id) {
        try {
            Message message = Message.fetcher(id).fetch();
            logger.info(MessageFormat.format("Message body requested [{0}]", message.getBody()));

            return message;
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    public static String fetchSmsBody(String id) {
        try {
            Message message = Message.fetcher(id).fetch();
            logger.info(MessageFormat.format("Message body requested [{0}]", message.getBody()));

            return message.getBody();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    public static boolean updateSmsData(Sms sms, String body) {
        boolean status = false;
        EntityManager em = null;
        try {
            sms.setAccountSid(ACCOUNT_SID);
            sms.setApiVersion(AUTH_TOKEN);

            em = EntityHelper.getInstance().getEntityManager();
            em.getTransaction().begin();
            em.merge(sms);
            em.getTransaction().commit();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        return status;
    }

    public static boolean updateSmsByMessage(Sms sms, Message message) {
        boolean status = false;
        EntityManager em = null;
        try {
            // Set SMS object
            sms.setSid(message.getSid());
            sms.setAccountSid(message.getAccountSid());
            sms.setApiVersion(message.getApiVersion());
            sms.setDirection(message.getDirection().name());
            if (message.getErrorCode() != null) {
                sms.setErrorCode(message.getErrorCode().toString());
            }
            if (message.getErrorMessage() != null) {
                sms.setErrorMessage(message.getErrorMessage());
            }
            sms.setMessagingServiceId(message.getMessagingServiceSid());
//            sms.setNumMedia(sms.getNumMedia());
//            sms.setNumSegments(String.valueOf(message.getNumSegments());
//            sms.setPrice(message.getPrice().intValue());
//            sms.setPriceUnit(message.getPriceUnit().getNumericCode()); // TODO: check those values
//            sms.setSentAt(message.getDateSent().toDate());
//            sms.setStatus(message.getStatus().name());
//            sms.setUpdatedAt(message.getDateUpdated().toDate());
//            sms.setUriTwillio(message.getUri());

            // Updated SMS Object intop databse
            em = EntityHelper.getInstance().getEntityManager();
            em.getTransaction().begin();
            em.merge(sms);
            em.getTransaction().commit();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

        return status;
    }
}
