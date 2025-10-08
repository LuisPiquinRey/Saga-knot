package com.luispiquinrey.user.Configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionListener;

public class ConnectionCustomListener implements ConnectionListener {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionCustomListener.class);

    @Override
    public void onCreate(Connection connection) {
        logger.info("Connection created: {}", connection);
    }


    @Override
    public void onClose(Connection connection) {
        logger.info("Connection closed: {}", connection);
    }

}
