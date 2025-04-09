package Loaders;

import Data.UserLoginData;
import Utils.HelperFunctions;

import java.io.IOException;
import java.util.List;

public class UserLoginDataLoader {
    public static List<UserLoginData> loadLoginDataFromJson() {
        try {

            List<UserLoginData> users = HelperFunctions.loadFromJson("D:\\testing\\automation\\e-commerce\\src\\test\\resources\\TestData\\UserLoginData.json", UserLoginData.class);

            return users;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
