import java.util.ArrayList;
import java.util.Scanner;

public class Calc {
    public static final ArrayList<String> ROMAN_LIST = new ArrayList<>();
    public static final ArrayList<Integer> ARABIAN_LIST = new ArrayList<>();
    public static final String START_HELLO = "Hello, it's calculator";
    public static final String NUMBERS_RANGE = "Numbers need in range 1 - 10";
    public static final String ACTION_RANGE = "He use just with: +, -, *, /";
    public static final String START = "Please enter exercise with space";
    public static final String EXIT = "Good bye!";
    public static final String ANSWER_INPUT = "Your answer is: " + "\n";

    public static void main(String[] args) throws Exception {
        startCalc();
        System.out.println("Input: ");
        String dataUser = new Scanner(System.in).nextLine();
        System.out.println(calc(dataUser.trim()));
        exitCalc();
    }

    public static String calc(String input) throws Exception {
        addRomanList();

        addArabianList();

        String result;

        if (input.equalsIgnoreCase("exit")) {
            exitCalc();
        }

        String[] symbol = input.split(" ");

        if (symbol.length != 3) {
            throw new Exception("Please use space between symbols");
        }

        String action = symbol[1];
        Number number1 = checkAndChange(symbol[0]);
        Number number2 = checkAndChange(symbol[2]);

        result = actionClass(number1, number2, action);

        if ((number1.type() == NumberType.ROMAN)) {
            if (Integer.parseInt(result) < 1) {
                throw new Exception("Result < 1");
            } else {
                return ANSWER_INPUT + arabianToRoman(result);
            }
        } else {
            return ANSWER_INPUT + result;
        }
    }

    static void addArabianList() {
        ARABIAN_LIST.add(1);
        ARABIAN_LIST.add(2);
        ARABIAN_LIST.add(3);
        ARABIAN_LIST.add(4);
        ARABIAN_LIST.add(5);
        ARABIAN_LIST.add(6);
        ARABIAN_LIST.add(7);
        ARABIAN_LIST.add(8);
        ARABIAN_LIST.add(9);
        ARABIAN_LIST.add(10);
    }

    static void addRomanList() {
        ROMAN_LIST.add("I");
        ROMAN_LIST.add("II");
        ROMAN_LIST.add("III");
        ROMAN_LIST.add("IV");
        ROMAN_LIST.add("V");
        ROMAN_LIST.add("VI");
        ROMAN_LIST.add("VII");
        ROMAN_LIST.add("VIII");
        ROMAN_LIST.add("IX");
        ROMAN_LIST.add("X");
    }

    static void startCalc() {
        System.out.println(START_HELLO);
        System.out.println(NUMBERS_RANGE);
        System.out.println(ACTION_RANGE);
        System.out.println(START);
        System.out.println();
    }

    static void exitCalc() {
        System.out.println(EXIT);
    }

    record Number(int value, NumberType type) {

    }

    static Number checkAndChange(String symbol) throws Exception {
        int value;
        NumberType type;

        try {
            value = Integer.parseInt(symbol);
            type = NumberType.ARABIC;
        } catch (NumberFormatException e) {
            value = toArabicNumber(symbol);
            type = NumberType.ROMAN;
        }
        if (value < 1 || value > 10) {
            throw new Exception(NUMBERS_RANGE);
        }
        return new Number(value, type);
    }

    static int toArabicNumber(String symbol) {
        return ARABIAN_LIST.get(ROMAN_LIST.indexOf(symbol));
    }

    static String actionClass(Number first, Number second, String action) throws Exception {
        int result;
        if (first.type() != second.type()) {
            throw new Exception("Different types");
        }
        result = switch (action) {
            case "+" -> first.value() + second.value();
            case "-" -> first.value() - second.value();
            case "*" -> first.value() * second.value();
            case "/" -> first.value() / second.value();
            default -> throw new Exception("He use just with +, -, *, /");
        };
        return String.valueOf(result);
    }

    static String arabianToRoman(String arabian) {
        int number = Integer.parseInt(arabian);
        int[] arrayValue = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] arrayChar = new String[]{"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder roman = new StringBuilder();

        for (int i = 0; i < arrayValue.length; i += 1) {
            while (number >= arrayValue[i]) {
                number -= arrayValue[i];
                roman.append(arrayChar[i]);
            }
        }
        return String.valueOf(roman);
    }
}
