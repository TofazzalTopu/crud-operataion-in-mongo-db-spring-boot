package com.info.encrypted.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class EncryptConfig {

    private static String mpSecretKey = "MY_SECRET";

    public static void main(String[] args) {
        String valueToBeEncrypted = "admin";
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(mpSecretKey);
        String encryptedPassword = encryptor.encrypt(valueToBeEncrypted);
        System.out.println("EncryptedPassword: "+encryptedPassword);

        StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();

        decryptor.setPassword(mpSecretKey);
        System.out.println("Actual Password: "+decryptor.decrypt(encryptedPassword));
    }
}
