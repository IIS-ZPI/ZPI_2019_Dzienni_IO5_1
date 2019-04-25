package com.global;

public class Main implements IArithmeticsAdd,IArithmeticsDiff, IArithmeticsDiv {

    public static void main(String[] args) {
        // write your code here
        System.out.println("Group name: ZPI_2019_Dzienni_IO5_1\nTeam Leader: badfilling\nRole: operations");
        System.out.println("kmzarski");
        System.out.println("Mariusz1306");
        System.out.println("GitHub id KovalykVolodymyr");
        System.out.println("GitHub id EkoGroszek");
        System.out.println("Artur Stepaniuk - githubID: badfilling");
    }

    @Override
    public double Difference(double A, double B) {
        return A - B;
    }

    @Override
    public double Addition(double A, double B) {
        return A + B;
    }

    @Override
    public double Division(double A, double B) { return A / B; }
}
