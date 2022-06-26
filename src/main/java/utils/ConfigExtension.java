package utils;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;



public final class ConfigExtension implements ExtensionContext.Store.CloseableResource, BeforeEachCallback {
    private final static Logger logger = Logger.getLogger(ConfigExtension.class);


    @Override
    public void beforeEach(ExtensionContext context) {
        //some configs could be added here
        logger.info("###### Started test: " + context.getTestMethod().get().getName() + "######");
    }

    @Override
    public void close() {
    }
}