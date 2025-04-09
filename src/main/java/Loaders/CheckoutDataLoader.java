package Loaders;

import Data.CheckoutData;
import Utils.HelperFunctions;

import java.io.IOException;
import java.util.List;

public class CheckoutDataLoader {
    public static List<CheckoutData> loadPaymentDataFromJson() {
        try {

            List<CheckoutData> data = HelperFunctions.loadFromJson("D:\\testing\\automation\\e-commerce\\src\\test\\resources\\TestData\\CheckoutData.json", CheckoutData.class);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
