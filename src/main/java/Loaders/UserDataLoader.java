package Loaders;

import Data.UserData;
import Utils.HelperFunctions;

import java.io.IOException;
import java.util.List;

public class UserDataLoader {
    public static List<UserData> loadUsersFromJson() {
        try {

            List<UserData> users = HelperFunctions.loadFromJson("D:\\testing\\automation\\e-commerce\\src\\test\\resources\\TestData\\UserTestData.json", UserData.class);
            for (UserData user : users) {
                user.setEmail();
            }
            return users;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
