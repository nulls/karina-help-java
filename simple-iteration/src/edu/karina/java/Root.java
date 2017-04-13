package edu.karina.java;

import edu.karina.java.function.Function1;

public class Root {
    public static boolean DEBUG;

    public static int MAX_ITERATION = 100;

    private final double a, b;
    private final double eps;

    public Root(double a, double b, double eps) {
        this.a = a;
        this.b = b;
        this.eps = eps;
        log("Создан объект для поиска корня в [%f; %f] с точностью %f", a, b, eps);
    }

    private double f(double x) {
        return Function1.f(x);
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

    private double getBetterLambda() {
        return 1 / findMaxDerivativeF();
    }

    private double getLambda(double x) {
        //return getBetterLambda();
        return 1 / derivativeF(x);
    }

    private double nextX(double x) {
        return x - getLambda(getX0()) * f(x);
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
