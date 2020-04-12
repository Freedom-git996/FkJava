package base.basic;

import java.util.Random;

public class PriceTest {

    public static void main(String[] args) {
//        System.out.println(Price.INSTANCE.currentPricce);
//        Price p = new Price(2.8);
//        System.out.println(p.currentPricce);
        int[] arr = new int[10];
        Random random = new Random();
        for(int i = 0; i < arr.length; i ++) {
            arr[i] = random.nextInt(11) + 90;
        }
        for(int i = 0; i < arr.length; i ++) {
            System.out.println(arr[i]);
        }
    }
}

class Price {

     static double initPrice = 20;
    static Price INSTANCE = new Price(2.8);
    double currentPricce;
    public Price(double discount) {
        currentPricce = initPrice - discount;
    }
}
