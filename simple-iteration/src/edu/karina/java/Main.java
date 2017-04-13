package edu.karina.java;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final Scanner sc = new Scanner(System.in);
        System.out.printf("Введите a: ");
        final double a = sc.nextDouble();
        System.out.printf("Введите b: ");
        final double b = sc.nextDouble();
        System.out.printf("Введите eps: ");
        final double eps = sc.nextDouble();

        Root.DEBUG = true;
        final Root root = new Root(a, b, eps);
        System.out.printf("Результат: %f", root.resolve());
    }
}
