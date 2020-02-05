package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Andoni Yeray
 */
public class Validator {

    /**
     * Method that check if the email is well written
     *
     * @param email parameter that is recibed from de interface
     * @return true if the email format is usefull.
     */
    public static boolean emailChecker(String email) {
        boolean auxEmail = true;

        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(email);

        if (!mather.find()) {
            auxEmail = false;
        }
        return auxEmail;
    }

    /**
     * Method that check if the password complete the requirement
     *
     * @param password parameter that is recibed from de interface
     * @return true if the password complete the requirements
     */
    public static boolean passwordChecker(String password) {
        boolean auxPwd = true;
        //TODO
        Pattern pattern = Pattern
                .compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,40}$");

        Matcher mather = pattern.matcher(password);

        if (!mather.find()) {
            auxPwd = false;
        }
        return auxPwd;
    }

    public static boolean cifChecker(String cif) {
        /* identificador del tipo de organismo */
        String letras_validas = "ABCDEFGHJPQRSUV";

        /* Caracteres de control */
        String caracteres_de_control = "JABCDEFGHI";

        /* tipos donde el carácter de control tiene que ser una letra */
        String tipo_de_letra = "PQS";

        /* tipos donde el carácter de control tiene que ser un nombre */
        String tipo_de_nombre = "ABEH";

        boolean resultado = false;
        int digito_de_control;

        try {
            /* Un CIF tiene que tener nueve dígitos */
            if (cif.length() == 9) {

                /* Toma la primera letra del CIF */
                char letra_CIF = cif.charAt(0);

                /* Comprueba si la primera letra del CIF es válida */
                if (letras_validas.indexOf(letra_CIF) >= 0) {

                    if (Character.isDigit(cif.charAt(8))) {
                        digito_de_control = Character.getNumericValue(cif
                                .charAt(8));
                        if (tipo_de_letra.indexOf(letra_CIF) >= 0) {
                            digito_de_control = 100;
                        }
                    } else {
                        digito_de_control = caracteres_de_control.indexOf(cif
                                .charAt(8));
                        if (tipo_de_nombre.indexOf(letra_CIF) >= 0) {
                            digito_de_control = 100;
                        }
                    }

                    int a = 0, b = 0, c = 0;
                    byte[] impares = {0, 2, 4, 6, 8, 1, 3, 5, 7, 9};

                    /* Calcula A y B. */
                    for (int t = 1; t <= 6; t = t + 2) {

                        /* Suma los pares */
                        a = a + Character.getNumericValue(cif.charAt(t + 1));
                        b = b
                                + impares[Character.getNumericValue(cif
                                        .charAt(t))];
                    }

                    b = b + impares[Character.getNumericValue(cif.charAt(7))];
                    /* Calcula C */
                    c = 10 - ((a + b) % 10);
                    /* Compara C con los dígitos de control */
                    resultado = (c == digito_de_control);
                }
            }
        } catch (Exception e) {
            resultado = false;
        }
        return resultado;
    }
}
