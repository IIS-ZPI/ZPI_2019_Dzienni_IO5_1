package com.global;

public class Main{

    public static void main(String[] args) {
        // write your code here
        System.out.println("Group name: ZPI_2019_Dzienni_IO5_1\nTeam Leader: badfilling\nRole: operations");
        System.out.println("kmzarski");
        System.out.println("Mariusz1306");
        System.out.println("GitHub id KovalykVolodymyr");
        System.out.println("GitHub id EkoGroszek");
        System.out.println("Artur Stepaniuk - githubID: badfilling");
    }
}

class ArithmeticPower implements IArithmeticsPower {

    @Override
    public double Power(double A, double B) {
        if (B == 0)
            return 1;
        double result = A;
        for (int i = 1; i < Math.abs(B); i++)
            result *= A;
        if (B > 0)
            return result;
        else
            return 1/result;
    }
}


