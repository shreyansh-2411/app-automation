package main.nativeapp.core;

import java.util.logging.Logger;

public class LogsManager {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private LogsManager(){
    }
    public static Logger getLogger(){
       return LOGGER;
   }
}
