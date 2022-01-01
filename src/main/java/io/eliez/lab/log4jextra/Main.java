package io.eliez.lab.log4jextra;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger("main");
    private static final Logger log4jLogger = LogManager.getLogger("main");

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Invalid arguments.\nPass the JNDI snapshot as the only argument.");
            System.exit(1);
        }
        String uuid = args[0];
        String tmURL = String.format("${jndi:ldap://log4j-tester.trendmicro.com:1389/%s}", uuid);

        logger.info("Using SLF4J: {}", tmURL);
        log4jLogger.info("Using Log4J API: {}", tmURL);
    }
}
