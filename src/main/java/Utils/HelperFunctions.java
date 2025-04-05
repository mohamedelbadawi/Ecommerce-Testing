package Utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class HelperFunctions {


    public static Double getNumberFromString(String string) {
        return Double.parseDouble(string.replaceAll("\\D+", ""));
    }

    public static String getRandomEmail() {
        String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String res = "";

        String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;

        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            int index = rand.nextInt(alphaNumeric.length());
            res += alphaNumeric.charAt(index);
        }
        return res + "@gmail.com";
    }


    public static <T> List<T> loadFromJson(String filePath, Class<T> clazz) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        // Load the JSON file into a List of UserRegistrationData objects
        List<T> data = objectMapper.readValue(new File(filePath),
                objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        return data;
    }
}
