package org.pprateek.deliveroo.utils;

public class IntegerUtils {
    public static boolean checkIfStringIsNumeric(String s) {
        try {
            Integer.parseInt(s);
            return true;
        }
        catch (NumberFormatException ex) {
            return false;
        }
    }
}
