package com.jemeisha.gocheeta;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util {

    private final static double PRICE_PER_KM= 80.0;

    private final static String ISSUER="go-cheeta";
    private final static String SECRET="SMYrsfILl0DmqAIa6b5mOAPsP9vBLiMieiVYo1Q5e9FLIKah67HwhbKStOco";
    public static String hashMD5(String password) throws NoSuchAlgorithmException {

        MessageDigest md= MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte [] digest= md.digest();

        return bytesToHex(digest);
    }
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static double calculatePrice(double distance){

        return PRICE_PER_KM*distance;
    }

    public static String signJWT(String username,String audience){
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            String token = JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(username)
                    .withAudience(audience)
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception){
            throw exception;

        }

    }

    public static DecodedJWT verifyToken(String token,String audience){
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET); //use more secure key
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .withAudience(audience)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            return jwt;
        } catch (JWTVerificationException exception){
            return null;
        }

    }
}
