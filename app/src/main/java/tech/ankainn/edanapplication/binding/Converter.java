package tech.ankainn.edanapplication.binding;

import androidx.databinding.InverseMethod;

public class Converter {

    @InverseMethod("stringToInteger")
    public static String integerToString(Integer value) {
        if(value == null) return null;
        return String.valueOf(value);
    }

    public static Integer stringToInteger(String value) {
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @InverseMethod("stringToDouble")
    public static String doubleToString(Double value) {
        if(value == null) return null;
        return String.valueOf(value);
    }

    public static Double stringToDouble(String value) {
        try {
            return Double.valueOf(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
