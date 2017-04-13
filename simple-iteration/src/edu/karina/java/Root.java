package edu.karina.java;

import edu.karina.java.function.*;

public class Root {
    public static boolean DEBUG = false;

    public static int MAX_ITERATION = 1_000;

    public enum LambdaMode {
        X0,
        MAX,
        Xi,
        ;
    }

    public enum Function {
        F1,
        F2,
        F3,
        F4,
        F5,
        ;
    }

    public static LambdaMode LAMBDA_MODE = LambdaMode.MAX;
    public static Function FUNCTION = Function.F1;

    private final double a, b;
    private final double eps;

    public Root(double a, double b, double eps) {
        this.a = a;
        this.b = b;
        this.eps = eps;
        log("Создан объект для поиска корня в [%f; %f] с точностью %f", a, b, eps);
    }

    private double f(double x) {
        switch (FUNCTION) {
            case F1:
                return Function1.f(x);
            case F2:
                return Function2.f(x);
            case F3:
                return Function3.f(x);
            case F4:
                return Function4.f(x);
            case F5:
                return Function5.f(x);
            default:
                throw new IllegalStateException("Некоректный выбор функции");
        }
    }

    private double derivativeF(double x) {
        return (f(x + eps / 2) - f(x - eps / 2)) / eps;
    }

    private double findMaxDerivativeF() {
        double max = 0.0;
        for (double x = a; x < b; x += eps) {
            double derivative = derivativeF(x);
            if (Math.abs(derivative) > Math.abs(max)) {
                max = derivative;
            }
        }
        log("Max Derivative = %f", max);
        return max;
    }

    private double getLambda(double x) {
        double lambda;
        switch (LAMBDA_MODE) {
            case X0:
                lambda = 1 / derivativeF(getX0());
                break;
            case MAX:
                lambda = 1 / findMaxDerivativeF();
                break;
            case Xi:
                lambda = 1 / derivativeF(x);
                break;
            default:
                throw new IllegalStateException("Некоректный выбор Лямбды");
        }
        return lambda;
    }

    private double nextX(double x) {
        return x - getLambda(x) * f(x);
    }

    private double getX0() {
        return (b - a) / 2 + a;
    }

    public double resolve() {
        double x = getX0();
        for (int i = 0 ; i < MAX_ITERATION; i++) {
            double xNext = nextX(x);
            log("[%d] %f -> %f", i, x, xNext);
            if (Math.abs(x - xNext) < eps) {
                checkResult(x);
                return x;
            }
            x = xNext;
        }
        log("Истратили все итерации, проверь MAX_ITERATION=%d или точность=%f", MAX_ITERATION, eps);
        return Double.NaN;
    }

    public boolean checkResult(double x) {
        if (Math.abs(f(x)) > eps) {
            log("Не правильно решили уравнение: f(%f) = %f", x, f(x));
            return false;
        }
        return true;
    }

    private void log(String message, Object... args) {
        if (DEBUG) {
            System.out.println(String.format(message, args));
        }
    }
}
