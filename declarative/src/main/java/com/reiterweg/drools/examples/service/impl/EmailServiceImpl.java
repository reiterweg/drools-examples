package com.reiterweg.drools.examples.service.impl;

import com.reiterweg.drools.examples.dto.Client;
import com.reiterweg.drools.examples.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailServiceImpl implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    private static EmailServiceImpl instance;

    private EmailServiceImpl() {

    }

    public static EmailServiceImpl getInstance() {
        if (instance == null) {
            synchronized (EmailServiceImpl.class) {
                if (instance == null) {
                    instance = new EmailServiceImpl();
                }
            }
        }

        return instance;
    }

    public void sendMessage(Client client) {
        LOGGER.info(String.format("Sending welcome email to %s with score card number %s", client.getName(), client.getScoreCard()));
    }

}
