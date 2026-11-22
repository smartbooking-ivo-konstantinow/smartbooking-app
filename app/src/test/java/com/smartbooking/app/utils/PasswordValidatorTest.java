package com.smartbooking.app.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class PasswordValidatorTest {

    // AUT-UNIT-01: una contraseña que cumple todas las reglas es válida
    @Test
    public void contrasenaValida_devuelveNull() {
        assertNull(PasswordValidator.validar("Clave#123"));
    }

    // AUT-UNIT-02: 8 caracteres se rechaza (valor límite)
    @Test
    public void ochoCaracteres_rechazaPorLargo() {
        assertEquals("La contraseña debe tener al menos 9 caracteres.",
                PasswordValidator.validar("Clave#12"));
    }

    // AUT-UNIT-03: sin letra se rechaza
    @Test
    public void sinLetra_rechazaPorComposicion() {
        assertEquals("La contraseña debe contener al menos una letra.",
                PasswordValidator.validar("12345#678"));
    }

    // AUT-UNIT-04: sin número se rechaza
    @Test
    public void sinNumero_rechazaPorComposicion() {
        assertEquals("La contraseña debe contener al menos un dígito.",
                PasswordValidator.validar("Clave#####"));
    }

    // AUT-UNIT-05: sin carácter especial se rechaza
    @Test
    public void sinCaracter_rechazaPorComposicion() {
        assertEquals("La contraseña debe contener al menos un carácter especial.",
                PasswordValidator.validar("Clave12345"));
    }
}