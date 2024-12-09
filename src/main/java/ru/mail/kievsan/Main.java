package ru.mail.kievsan;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    static final String REG_OPERATIONS = "[-+*/]";
    static final String REG_NUMBERS = "([123456789]|10)";
    static final String REGEX = "%s([%s])%s".formatted(REG_NUMBERS, REG_OPERATIONS, REG_NUMBERS);
    static final String EXPRESSION_ERR = "Critical error in arithmetic expression!";
    static final String DIVISION_ERR = "Div by zero!";

    public static void main(String[] args) {
        var scan = new Scanner(System.in);
        String expression;
        System.out.println("ON");
        while (!(expression = scan.nextLine()).isBlank()) {
            try {
                System.out.println(calc(expression));
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                break;
            }
        }
        System.out.println("OFF");
        scan.close();
    }

    public static String calc(String input) throws Exception {
        String expr = input.replaceAll("\\s", "");
        if (isErrExpression(expr)) throw new Exception(EXPRESSION_ERR);

        String operation = expr.replaceAll("\\d", "");
        String[] numbers = expr.split("\\%s".formatted(operation));

        return String.valueOf(calculate(numbers, operation));
    }

    static int calculate(String[] numbers, String operation) throws Exception {
        int[] num = {0, Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1])};
        return switch (operation) {
            case "+" -> num[1] + num[2];
            case "-" -> num[1] - num[2];
            case "*" -> num[1] * num[2];
            case "/" -> {
                if (num[2] == 0) {
                    throw new Exception(DIVISION_ERR);
                }
                yield num[1] / num[2];
            }
            default -> throw new Exception(EXPRESSION_ERR);
        };
    }

    static boolean isErrExpression(String expr) {
        return !Pattern.matches(REGEX, expr);
    }
}
