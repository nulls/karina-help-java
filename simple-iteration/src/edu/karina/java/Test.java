package edu.karina.java;

public class Test {
    public static void main(String[] args) {
        Root.MAX_ITERATION = 1_000;
        Root.DEBUG = false;
        Root.LAMBDA_MODE = Root.LambdaMode.Xi;

        // function 1
        checkFunction(Root.Function.F1, Root.LAMBDA_MODE, 8, 10, 0.0001);

        // function 2
        checkFunction(Root.Function.F2, Root.LAMBDA_MODE, 0, 1, 0.0001);

        // function 3
        checkFunction(Root.Function.F3, Root.LAMBDA_MODE, 1, 2, 0.0001);

        // function 4;
        checkFunction(Root.Function.F4, Root.LAMBDA_MODE, 2, 4, 0.0001);

        // function 5
        checkFunction(Root.Function.F5, Root.LAMBDA_MODE, 2, 5, 0.0001);
    }

    private static void checkFunction(Root.Function function, Root.LambdaMode mode, double a, double b, double eps) {
        Root.FUNCTION = function;
        Root.LAMBDA_MODE = mode;
        System.out.printf("Function %s; Mode %s\n", function.name(), mode.name());

        Root root = new Root(a, b, eps);
        double result = root.resolve();
        System.out.printf("%f=%s\n", result, root.checkResult(result) ? "хороший результат" : "плохой результат");
    }
}
