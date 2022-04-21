package com.jx.demo;

import com.jx.util.TimeTest;

/**
 * Narcissistic number（水仙花数）
 * 1.The number of daffodils is a three-digit number。（水仙花数是一个三位数）
 * 2.The sum of the third power of each digit is the original number.（每一位上的数三次方之和为原数）
 */
public class DemoNarcissisticNumber extends TimeTest {
    public static void main(String[] args) {
        DemoNarcissisticNumber narcissisticNumber = new DemoNarcissisticNumber();
        narcissisticNumber.testTime();
    }

    public void testMethod1() {
        //  NarcissisticNumber：水仙花数.
        int NarcissisticNumber;

        //  hundredDigit：百位数; tenDigit：十位数; singleDigit：个位数.
        int hundredDigit;
        int tenDigit;
        int singleDigit;

        //  hundredCubic：百位数的三次方; tenCubic：十位数的三次方.
        int hundredCubic;
        int tenCubic;

        //  num：记录百位数、十位数三次方之和.
        int num;

        //  hundredDigit：百位数开始循环(1->9).
        for (hundredDigit = 1; hundredDigit < 10; hundredDigit++) {

            //  hundredCubic：百位数的三次方.
            hundredCubic = hundredDigit * hundredDigit * hundredDigit;

            //  tenDigit：十位数开始循环(0->9，因为十位数可以为0，所以从0开始循环).
            for (tenDigit = 0; tenDigit < 10; tenDigit++) {

                //  tenCubic：十位数的三次方.
                tenCubic = tenDigit * tenDigit * tenDigit;

                //  NarcissisticNumber：此时的水仙数应是百位数水仙数加上十位数.
                NarcissisticNumber = hundredDigit * 100 + tenDigit * 10;

                //  num：百位数三次方加上十位数三次方.
                num = hundredCubic + tenCubic;

                //  判断：百位数三次方、十位数三次方之和必须小于或等于当前水仙花数所处最大数才能构成水仙花数.
                if (num <= NarcissisticNumber + 9) {

                    //  singleDigit：个位数开始循环(0->9).
                    for (singleDigit = 0; singleDigit < 10; singleDigit++) {

                        //  判断水仙花数是否成立.
                        if (num + singleDigit * singleDigit * singleDigit == NarcissisticNumber + singleDigit) {

                            //  打印水仙花数.
                            System.out.println(NarcissisticNumber + singleDigit);
                        }
                    }
                }
            }
        }
    }

    public void testMethod2() {
        for (int i = 100; i < 1000; i++) {
            int hundredDigit = i / 100 % 10;
            int tenDigit = i / 10 % 10;
            int singleDigit = i % 10;
            if ((singleDigit * singleDigit * singleDigit + tenDigit * tenDigit * tenDigit + hundredDigit * hundredDigit * hundredDigit) == i) {
                System.out.println(i);
            }
        }
    }
}
