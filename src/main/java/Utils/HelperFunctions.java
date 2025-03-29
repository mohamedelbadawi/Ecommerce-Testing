package Utils;

public class HelperFunctions {
    public static Double getNumberFromString(String string) {
        return Double.parseDouble(string.replaceAll("\\D+", ""));
    }
}
