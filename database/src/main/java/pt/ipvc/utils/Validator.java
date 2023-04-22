package pt.ipvc.utils;

public class Validator {

    public static boolean validateEmail(String email) {
        return email.matches("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    }
}
