package br.com.jerimum.fw.logging;

import java.io.File;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.BasicConfigurator;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.util.StatusPrinter;

/**
 * 
 * @author Dali Freire - dalifreire@gmail.com
 */
public class LoggerConfigurator {

    private String logbackConfigurationFile;

    public LoggerConfigurator(String logbackConfigurationFile) {
        this.logbackConfigurationFile = logbackConfigurationFile;
    }

    public void configure() {

        // assume SLF4J is bound to logback in the current environment
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

        try {
            JoranConfigurator configurator = new JoranConfigurator();
            configurator.setContext(context);
            /*
             * Call context.reset() to clear any previous configuration, e.g. default configuration.
             * For multi-step configuration, omit calling context.reset().
             */
            context.reset();
            configurator.doConfigure(new File(logbackConfigurationFile));

        } catch (Exception e) {
            BasicConfigurator.configure(context);
            LoggerUtils.logError(this.getClass(), "Error on configuring logback, BasicConfigurator will be used...", e);
        }
        StatusPrinter.printInCaseOfErrorsOrWarnings(context);
    }

}
