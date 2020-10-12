package com.info.encrypted.service;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.stereotype.Service;

@Service
public class EncryptorDecryptorService {

    // @Comment: Jasypt encrypt and decrypted data based on the secret-key
    private static String mpSecretKey = "MY_SECRET";


    // @Comment: In this method - Jasypt decrypt encrypted data based on the secret-key
    public String setEncryptedValue(String value) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(mpSecretKey);
        String encryptedValue = encryptor.encrypt(value);
      //  System.out.println("encryptedValue: "+encryptedValue);
        return encryptedValue;
    }

    // @Comment: In this method - Jasypt decrypt encrypted data based on the secret-key
    public String getDecryptedValue(String value) {
        StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
        decryptor.setPassword(mpSecretKey);
        //   System.out.println("decryptedValue: "+decryptor.decrypt(value));
        return decryptor.decrypt(value);
    }

}
