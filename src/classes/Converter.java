package classes;

import java.util.Scanner;

/**
 * @author AlexGiazitzis at 21-December-2020 : 5:45 PM
 * @project NumeralSystemConverter
 */

public class Converter {
    final static int MAX_DIGITS = 5;
    final static Scanner scanner = new Scanner(System.in);

    public void init() {
        while (menu() == 1){
            try {
                System.out.print("Enter the source radix: ");
                int sourceRadix = new Scanner(System.in).nextInt();

                System.out.print("\nEnter your number: ");
                String sourceNum = new Scanner(System.in).next();

                System.out.print("\nEnter the target radix: ");
                int targetRadix = scanner.nextInt();

                if (sourceRadix < 1 || sourceRadix > 36 || targetRadix < 1 || targetRadix > 36)
                    throw new Exception("error");

                result(sourceRadix, sourceNum, targetRadix);
            } catch (Exception e) {
                System.out.println("error");
            }
        }

    }

    private int menu() {
        while (true) {
            System.out.println("\n1.Convert number.");
            System.out.println("2.Exit.");
            int choice = scanner.nextInt();

            if (choice == 1 || choice == 2) {
                return choice;
            } else {
                System.out.println("Invalid menu option chosen.");
            }
        }
    }

    private void result(int sourceRadix, String sourceNumber, int targetRadix) {
        if (sourceNumber.contains(".")) {

            String[] sourceNum = sourceNumber.split("\\.");
            int fractCheck = sourceRadix != 10 ? Integer.parseInt(sourceNum[1], sourceRadix) : Integer.parseInt(sourceNum[1]);
            if (fractCheck >= 0 && sourceRadix == 10) {

                StringBuilder result = new StringBuilder().append(intToRadix(Integer.parseInt(sourceNum[0]), targetRadix));
                result.append(".").append(fractToRadix(Double.parseDouble("0." + sourceNum[1]), targetRadix));
                System.out.println(result.toString());

            } else if (fractCheck >= 0) {

                int iDecStage = intToDec(sourceNum[0], sourceRadix);
                double fDecStage = fractToDec(sourceNum[1], sourceRadix);
                StringBuilder result = new StringBuilder().append(intToRadix(iDecStage, targetRadix));
                result.append(".").append(fractToRadix(fDecStage, targetRadix));
                System.out.println(result.toString());
            }

        } else {
            if (sourceRadix == 10) {

                System.out.println(intToRadix(Integer.parseInt(sourceNumber), targetRadix));

            } else {

                int iDecStage = intToDec(sourceNumber, sourceRadix);
                System.out.println(intToRadix(iDecStage, targetRadix));

            }
        }
    }

    private int intToDec(String num, int sourceRadix) {
        if (sourceRadix != 1) {
            return Integer.parseInt(num, sourceRadix);
        } else {
            return num.length();
        }
    }

    private String intToRadix(int num, int targetRadix) {
        if (targetRadix != 1) {
            return Integer.toString(num, targetRadix);
        } else {
            return "1".repeat(Math.max(0, num));
        }
    }

    private double fractToDec(String num, int sourceRadix) {
        double decFract = 0.0d;
        for (int i = 0; i < num.length(); i++) {
            decFract += (Character.getNumericValue(num.charAt(i)) / Math.pow(sourceRadix, i + 1));
        }
        if (decFract > 1.0d) {
            System.out.println("Wrong calculation on fractToDec");
        }
        return decFract;
    }

    private String fractToRadix(double num, int targetRadix) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < MAX_DIGITS; i++) {
            String sIntPart = Double.toString(num * targetRadix);
            sIntPart = sIntPart.substring(0, sIntPart.indexOf("."));
            int intPart = Integer.parseInt(sIntPart);
            if (intPart >= 10) {
                result.append(Character.toString(87 + intPart));
            } else {
                result.append(intPart);
            }
            num = num * targetRadix - intPart;
        }

        return result.toString();
    }

}
