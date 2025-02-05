package net.ausiasmarch.academia.service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import org.springframework.stereotype.Service;

import net.ausiasmarch.academia.exception.NoSuchAlgorithmException;

@Service
public class HashService {

     public String hashPassword (String password) {

        try {
            // Obtenemos el algoritmo SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            
            // Generamos el hash de la contraseña
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            
            // Convertimos el byte array a formato hexadecimal
            BigInteger no = new BigInteger(1, encodedHash);
            String hashText = no.toString(16);
            
            // Aseguramos que el hash tenga 64 caracteres
            while (hashText.length() < 64) {
                hashText = "0" + hashText;
            }
            
            return hashText;
            
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmException("No se ha podido hashear la contraseña.");
        }
     }
}
