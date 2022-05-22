package com.example.mdsback.services;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggingService {
    Logger logger = Logger.getLogger("My Logger");
    FileHandler fh;

    public LoggingService() {
        try {
            fh = new FileHandler("logs.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    public void log(String action) {
        this.logger.info(action);
    }

}
