package org.frog.utility;

public class validate {
    public static String checkMail(String mail) {
        String mailCheck = null;
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        if(p.matcher(mail).matches()) {
            mailCheck = mail;
        }
        return mailCheck;
    }
    public static String checkPassword(String password) {
        String passwordCheck = null;
        String ePattern = "^.{8,}$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        if(p.matcher(password).matches()) {
            passwordCheck = password;
        }
        return passwordCheck;
    }
}
