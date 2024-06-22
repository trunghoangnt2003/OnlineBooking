package org.frog.utility;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.UUID;

public class SHA1 {
    public static String toSHA1(String str) {
        String salt = "q@;;sc";
        String res = null;
        str += salt;
        try {
            byte[] dataBytes = str.getBytes(StandardCharsets.UTF_8);
            MessageDigest  md  = MessageDigest.getInstance("SHA-1");
            res = org.apache.tomcat.util.codec.binary.Base64.encodeBase64String(md.digest(dataBytes));
        } catch (Exception e) {
            // TODO: handle exception
          e.printStackTrace();
        }
        return res;
    }
}
