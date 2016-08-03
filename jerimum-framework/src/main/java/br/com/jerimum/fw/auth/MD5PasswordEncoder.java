package br.com.jerimum.fw.auth;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.jerimum.fw.logging.LoggerUtils;

/**
 * Default implementation for MD5 password encoder.
 * 
 * @author Dali Freire - dalifreire@gmail.com
 */
public class MD5PasswordEncoder implements PasswordEncoder {

    private MessageDigest md5Encoder;

    public MD5PasswordEncoder() {
        try {
            md5Encoder = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            LoggerUtils.logError(this.getClass(), "MD5 password encoder could not be created.", e);
        }
    }

    @Override
    public String encode(CharSequence rawPassword) {
        if (rawPassword != null) {
            md5Encoder.update(rawPassword.toString().getBytes());
            return new BigInteger(1, md5Encoder.digest()).toString(16);
        } 
        return null;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        md5Encoder.update(rawPassword.toString().getBytes());
        return new BigInteger(1, md5Encoder.digest()).toString(16).equals(encodedPassword);
    }

}
