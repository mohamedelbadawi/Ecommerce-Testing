package Loaders;

import Data.UserRegistrationData;
import Utils.HelperFunctions;

import java.io.IOException;
import java.util.List;

public class UserRegistrationDataLoader {
    public static List<UserRegistrationData> loadUsersFromJson() {
        try {

            List<UserRegistrationData> users = HelperFunctions.loadFromJson("D:\\testing\\automation\\e-commerce\\src\\test\\resources\\TestData\\UserTestData.json", UserRegistrationData.class);
            for (UserRegistrationData user : users) {
                user.setEmail();
            }
            return users;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
