package com.chatovich.movie.util;

import com.chatovich.movie.exception.HashPasswordFailedException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * class for hashing a user password
 */
public class PasswordHash {

    /**
     * hashes user's password to put it into the db
     * @param password user password to be hashed
     * @return secure hashed password to put into database
     */
    public static String getHashPassword (String password) throws HashPasswordFailedException {

        String hashedPassword = "";
        try{
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.reset();
            md5.update(password.getBytes());
            byte [] messageDigest = md5.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < messageDigest.length; i++) {
                String hex=Integer.toHexString(0xff & messageDigest[i]);
                if(hex.length()==1) sb.append('0');
                sb.append(hex);
            }
            hashedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new HashPasswordFailedException("Problem with hashing a password: "+e.getMessage());
        }
        return hashedPassword;
    }
}
