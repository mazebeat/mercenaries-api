/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mercenaries.main.email;

import com.mercenaries.jpa.Email;
import com.mercenaries.utils.EntityHelper;
import com.sun.jersey.api.client.ClientResponse;
import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import org.apache.http.HttpStatus;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author DiegoFeliú
 */
public class EmailApi {

    private static Logger logger = LoggerFactory.getLogger(EmailApi.class);

    private static final String DOMAIN_NAME = "reca.cl";
    private static final String MAILGUN_URI_API = "https://api.mailgun.net/v3/reca.cl";
    private static final String MAILGUN_API_KEY = "key-04d178e3fc1ff8d7ff3e6de74aec5b8a";
    private static final String EMAIL_FROM = "RECA <envios@reca.cl>";

    public static void main(String... args) {
        logger.info("Email Api init");
        if (args.length < 2) {
            logger.error("Verify parameters [filename]");
            System.exit(1);
        }

        String filename = args[1];

        logger.info("Getting all Email by file " + filename);
        List<Email> allToSend = allByNamefile(filename);

        logger.info("Sending all messages");
        allToSend.forEach((Email email) -> {
            Map<String, String> data = parseClientResponse(SendSimple(email));
            //TODO: update email model with jsondata
        });
    }

    public static List<Email> allByNamefile(String filename) {
        EntityManager em = null;
        List<Email> list = new ArrayList<>();
        try {
            em = EntityHelper.getInstance().getEntityManager();
            Query query = em.createQuery("SELECT e FROM Email AS e WHERE e.filename = :filename", Email.class)
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

    @Produces(MediaType.APPLICATION_JSON)
    public static ClientResponse GetLogs() {
        Client client = ClientBuilder.newClient();
        client.register(HttpAuthenticationFeature.basic("api", MAILGUN_API_KEY));

        WebTarget mgRoot = client.target(MAILGUN_URI_API);

        return mgRoot.path("/{domain}/events")
                        .resolveTemplate("domain", DOMAIN_NAME)
                        .queryParam("begin", 50)
                        .queryParam("ascending", "yes")
                        .queryParam("limit", 1)
                        .queryParam("pretty", "yes")
                        .queryParam("recipient", "joe@example.com")
                        .request()
                        .buildGet()
                        .invoke(ClientResponse.class);
    }

    @Produces(MediaType.APPLICATION_JSON)
    public static ClientResponse SendSimple(Email email) {
        Client client = ClientBuilder.newClient();
        client.register(HttpAuthenticationFeature.basic("api", MAILGUN_API_KEY));

        WebTarget mgRoot = client.target(MAILGUN_URI_API);

        Form reqData = new Form();
        reqData.param("from", EMAIL_FROM); // "Excited User <YOU@YOUR_DOMAIN_NAME>");
        reqData.param("to", email.getToEmail());
        reqData.param("subject", email.getSubject());
        reqData.param("text", "Testing out some Mailgun awesomeness!");
        reqData.param("o:tag", email.getFilename());
        reqData.param("o:tracking", "true");

        return mgRoot.path("/{domain}/messages")
                        .resolveTemplate("domain", DOMAIN_NAME)
                        .request(MediaType.APPLICATION_FORM_URLENCODED)
                        .buildPost(Entity.entity(reqData, MediaType.APPLICATION_FORM_URLENCODED))
                        .invoke(ClientResponse.class);
    }

    @Produces(MediaType.APPLICATION_JSON)
    public static ClientResponse SendComplexMessage(Email email, String html) {

        Client client = ClientBuilder.newClient();
        client.register(HttpAuthenticationFeature.basic("api", MAILGUN_API_KEY));

        WebTarget mgRoot = client.target(MAILGUN_URI_API);

        FormDataMultiPart reqData = new FormDataMultiPart();
        reqData.field("from", EMAIL_FROM);
        reqData.field("to", email.getToEmail());
        reqData.field("subject", email.getStatus());
        reqData.field("html", html);
        reqData.field("o:tag", email.getFilename());
        reqData.field("o:tracking", "true");

        //TODO: Look up how to add attachment
        String file_separator = System.getProperty("file.separator");
        File txtFile = new File("." + file_separator + "files" + file_separator + "test.txt");
        reqData.bodyPart(new FileDataBodyPart("attachment", txtFile, MediaType.TEXT_PLAIN_TYPE));

        File jpgFile = new File("." + file_separator + "files" + file_separator + "test.jpg");
        reqData.bodyPart(new FileDataBodyPart("attachment", jpgFile, MediaType.APPLICATION_OCTET_STREAM_TYPE));

        return mgRoot.path("/{domain}/messages")
                        .resolveTemplate("domain", DOMAIN_NAME)
                        .request(MediaType.MULTIPART_FORM_DATA_TYPE)
                        .accept(MediaType.APPLICATION_JSON)
                        .buildPost(Entity.entity(reqData, MediaType.APPLICATION_FORM_URLENCODED))
                        .invoke(ClientResponse.class);
    }

    public static Map<String, String> parseClientResponse(ClientResponse response) {
        Map<String, String> data = new HashMap<>();

        try {
            if (response.getStatus() != HttpStatus.SC_OK) {
                throw new RuntimeException(MessageFormat.format("Failed : HTTP error code : {0}", response.getStatus()));
            }

            String output = response.getEntity(String.class);
            logger.info(MessageFormat.format("Server response .... \n {0}", output));
//
//            JSONObject jsnobject = new JSONObject(output);
//
//            JSONArray jsonArray = jsnobject.getJSONArray("locations");
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject explrObject = jsonArray.getJSONObject(i);
//            }

            //TODO: Look into how to parse string to Json or Josn to Object
            //            JsonParser parser = new JsonParser();
            //            JsonElement parsed = parser.parse(response.getEntity(data));
            //            JsonObject asJsonObject = parsed.getAsJsonObject();
            //            JsonArray data = asJsonObject.getAsJsonArray("data");
            //            for (int i = 0; i < data.size(); i++) {
            //                JsonElement jsonElement = data.get(i);
            //                JsonArray subdata = jsonElement.getAsJsonArray();
            //                for (int j = 0; j < subdata.size(); j++) {
            //                    JsonElement subelement = subdata.get(j);
            //                    if (subelement.isJsonNull()) {
            //                        break;
            //                    }
            //                    logger.info(subelement.getAsString() + ",");
            //                }
            //            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }

        return data;
    }
}
