package br.com.jerimum.fw.i18n;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import br.com.jerimum.fw.logging.LoggerUtils;

/**
 * Utility class to manage internationalization message files.
 * 
 * @author Dali Freire Dias - dalifreire@gmail.com
 */
public abstract class I18nUtils {

    public static final String BUNDLE_FW = "br.com.jerimum.i18n.jerimum-messages";
    public static final String SUFIX_UNDEFINED = "_UNDEFINED";
    private static final String DEFAULT_FAIL_MSG = "Unable to get message in the bundle!";


    private I18nUtils() {

    }

    /**
     * Returns the {@link ResourceBundle} for the properties file passed as parameter. Throws a
     * {@link MissingResourceException} if file not found.
     * 
     * @param absolutePathToFile
     * @return {@link ResourceBundle}
     * @throws MissingResourceException
     */
    public static ResourceBundle getBundle(String absolutePathToFile) {

        try {

            return ResourceBundle.getBundle(absolutePathToFile);

        } catch (MissingResourceException e) {

            try {
                LoggerUtils.logError(I18nUtils.class, "getBundle (1)", e);
                return ResourceBundle.getBundle(StringUtils.substringAfterLast(absolutePathToFile, "."));
            } catch (MissingResourceException ex) {
                LoggerUtils.logError(I18nUtils.class, "getBundle (2)", e);
                return null;
            }
        }

    }

    /**
     * Returns the internationalized message by key.
     * 
     * @param file - Arquivo de propriedade onde a mensagem sera buscada.
     * @param key - Chave de cadastro da mensagem.
     * @return String - Mensagem correspondente cadastrada.
     */
    public static String getMsg(String file, String key) {

        ResourceBundle bundle = getBundle(file);
        return getMsg(bundle, key);
    }

    /**
     * Returns the internationalized message by key.
     * 
     * @param bundle - Arquivo de propriedade onde a mensagem sera buscada.
     * @param key - Chave de cadastro da mensagem.
     * @return String - Mensagem correspondente cadastrada.
     */
    public static String getMsg(ResourceBundle bundle, String key) {

        return getMsg(bundle, key, new Object[] {});
    }

    /**
     * Returns the internationalized message by key.
     * 
     * @param bundle - Arquivo de propriedade onde a mensagem sera buscada.
     * @param key Chave de cadastro da mensagem
     * @param arguments Argumentos utilizados para completar a mensagem
     * @return String BundleMenssageUtils cadastrada
     */
    public static String getMsg(ResourceBundle bundle, String key, Object... arguments) {

        String mensagem = null;

        try {

            mensagem = MessageFormat.format(bundle.getString(key), arguments);

        } catch (MissingResourceException e) {
            LoggerUtils.logError(I18nUtils.class, DEFAULT_FAIL_MSG, e);
            mensagem = key.concat(SUFIX_UNDEFINED);
        } catch (Exception e) {
            LoggerUtils.logError(I18nUtils.class, DEFAULT_FAIL_MSG, e);
            mensagem = key.concat(SUFIX_UNDEFINED);
        }

        return mensagem;
    }

    /**
     * Returns the internationalized message by key.
     * 
     * @param file - Arquivo de propriedade onde a mensagem sera buscada.
     * @param key Chave de cadastro da mensagem
     * @param arguments Argumentos utilizados para completar a mensagem
     * @return String BundleMenssageUtils cadastrada
     */
    public static String getMsg(String file, String key, Object... arguments) {

        ResourceBundle bundle = getBundle(file);
        return getMsg(bundle, key, arguments);
    }

    /**
     * Returns the internationalized message by key.
     * 
     * @param messageSource - Messages properties file.
     * @param key - Message key.
     * @return {@link String}
     */
    public static String getMsg(MessageSource messageSource, String key) {

        return getMsg(messageSource, key, new Object[] {});
    }

    /**
     * Returns the internationalized message by key.
     * 
     * @param messageSource - Messages properties file.
     * @param key - Message key.
     * @param arguments - Message params.
     * @return {@link String}
     */
    public static String getMsg(MessageSource messageSource, String key, Object... arguments) {

        String mensagem = null;

        try {

            mensagem = messageSource.getMessage(key, arguments, Locale.getDefault());

        } catch (NoSuchMessageException e) {
            LoggerUtils.logError(I18nUtils.class, DEFAULT_FAIL_MSG, e);
            mensagem = key.concat(SUFIX_UNDEFINED);
        } catch (Exception e) {
            LoggerUtils.logError(I18nUtils.class, DEFAULT_FAIL_MSG, e);
            mensagem = key.concat(SUFIX_UNDEFINED);
        }
        return mensagem;
    }

}
