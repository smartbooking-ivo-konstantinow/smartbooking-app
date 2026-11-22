package com.smartbooking.app.utils;

import java.util.regex.Pattern;

public final class PasswordValidator {

    public static final int LARGO_MINIMO = 9;

    private static final Pattern LETRA = Pattern.compile("[a-zA-Z]");
    private static final Pattern NUMERO = Pattern.compile("\\d");
    private static final Pattern ESPECIAL = Pattern.compile("[^a-zA-Z0-9]");

    private PasswordValidator() {
    }

    public static String validar(String password) {
        if (password == null || password.length() < LARGO_MINIMO) {
            return "La contraseña debe tener al menos " + LARGO_MINIMO + " caracteres.";
        }
        if (!LETRA.matcher(password).find()) {
            return "La contraseña debe contener al menos una letra.";
        }
        if (!NUMERO.matcher(password).find()) {
            return "La contraseña debe contener al menos un dígito.";
        }
        if (!ESPECIAL.matcher(password).find()) {
            return "La contraseña debe contener al menos un carácter especial.";
        }
        return null;
    }
}