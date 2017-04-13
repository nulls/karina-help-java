package edu.karina.java;

import edu.karina.java.function.*;

public class Root {
    public static boolean DEBUG;

    public static int MAX_ITERATION = 1_000_000;

    public enum LambdaMode {
        X0,
        MAX,
        Xi,
        ;
    }

    public static LambdaMode LAMBDA_MODE = LambdaMode.Xi;

    private final double a, b;
    private final double eps;

    public Root(double a, double b, double eps) {
        this.a = a;
        this.b = b;
        this.eps = eps;
        log("Создан объект для поиска корня в [%f; %f] с точностью %f", a, b, eps);
    }

    private double f(double x) {
        return Function2.f(x);
    }

    private double derivativeF(double x) {
        return (f(x + eps / 2) - f(x - eps / 2)) / eps;
    }

    private double findMaxDerivativeF() {
        double max = Double.MIN_VALUE;
        for (double x = a; x < b; x++) {
            double derivative = Math.abs(derivativeF(x));
            if (derivative > max) {
                max = derivative;
            }
        }
        return max;
    }

    private double getLambda(double x) {
        switch (LAMBDA_MODE) {
            case X0:
                return 1 / derivativeF(getX0());
            case MAX:
                return 1 / findMaxDerivativeF();
            case Xi:
                return 1 / derivativeF(x);
            default:
                throw new IllegalStateException("");
        }
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
                return x;
            }
            x = xNext;
        }
        log("Истратили все итерации, проверь MAX_ITERATION=%d или точность=%f", MAX_ITERATION, eps);
        return Double.NaN;
    }

    private void log(String message, Object... args) {
        if (DEBUG) {
            System.out.println(String.format(message, args));
        }
    }
}
