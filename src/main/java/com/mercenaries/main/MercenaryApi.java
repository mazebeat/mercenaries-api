/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mercenaries.main;

import com.mercenaries.main.email.EmailApi;
import com.mercenaries.main.sms.SmsApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author DiegoFeliú
 */
public class MercenaryApi {

    private static Logger logger = LoggerFactory.getLogger(MercenaryApi.class);

    public static void main(String... args) {
        logger.info("Application init");

        long tini = System.currentTimeMillis();
        logger.info("Process start in " + (System.currentTimeMillis() - tini) + " ms");

        try {
            switch (args[0]) {
                case "email":
                    EmailApi.main(args);
                    break;
                case "sms":
                    SmsApi.main(args);
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }

        logger.info("Application ends");
        logger.info("Processed in " + (System.currentTimeMillis() - tini) + " ms");
    }
}
