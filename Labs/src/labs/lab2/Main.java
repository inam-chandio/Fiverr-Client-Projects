package labs.lab2;

import java.util.Scanner;

public class Main {

    /**
     * The main method that allows testing of various problems based on user input.
     * It provides a menu to select from different problems.
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Choose a problem to test:");
        System.out.println("1: Phone Number Formatting");
        System.out.println("2: Sticker Total Calculation");
        System.out.println("3: Credit Card Payoff Calculation");
        System.out.println("4: Play Blackjack");
        System.out.println("5: Assess Password Strength");
        System.out.println("6: Troubleshoot Car Issues");

        int choice = in.nextInt();
        in.nextLine();  // Consume newline left-over

        switch (choice) {
            case 1:
                problem2_formatPhoneNumber(in);
                break;
            case 2:
                problem3_calculateTotal(in);
                break;
            case 3:
                problem4_creditCardPayoff(in);
                break;
            case 4:
                System.out.print("Enter first number: ");
                int a = in.nextInt();
                System.out.print("Enter second number: ");
                int b = in.nextInt();
                System.out.println("Result: " + problem7_playBlackjack(a, b));
                break;
            case 5:
                System.out.print("Enter a password: ");
                String password = in.nextLine();
                System.out.println("Password strength: " + problem8_assessPasswordStrength(password));
                break;
            case 6:
                problem9_troubleshootCarIssues(in);
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }

        in.close();
    }

    /**
     * Converts an amount to dollars and cents.
     *
     * @param amount the amount of money in double
     * @return a string formatted as "dollars: ..., cents: ..."
     */
    public static String problem1_getDollarsAndCents(double amount) {
        int dollars = (int) amount;
        int cents = (int) Math.round((amount - dollars) * 100);
        return "dollars: " + dollars + ", cents: " + cents;
    }

    /**
     * Formats a phone number in the form (xxx) xxx-xxxx.
     *
     * @param in the Scanner object to read the phone number
     */
    public static void problem2_formatPhoneNumber(Scanner in) {
        System.out.print("Please enter a ten-digit phone number: ");
        String phoneNumber = in.nextLine();
        String formattedNumber = "(" + phoneNumber.substring(0, 3) + ") " + phoneNumber.substring(3, 6) + "-" + phoneNumber.substring(6);
        System.out.print(formattedNumber);
    }

    /**
     * Calculates the total cost of stickers, including a 25% sales tax.
     *
     * @param in the Scanner object to read price and quantity
     */
    public static void problem3_calculateTotal(Scanner in) {
        System.out.print("Enter price per sticker: ");
        double pricePerSticker = Double.parseDouble(in.nextLine());
        System.out.print("Enter the number of stickers: ");
        int numberOfStickers = Integer.parseInt(in.nextLine());
        double subtotal = pricePerSticker * numberOfStickers;
        double shippingCost;
        if (numberOfStickers <= 10) {
            shippingCost = 2.50;
        } else if (numberOfStickers <= 100) {
            shippingCost = 15.00;
        } else {
            shippingCost = numberOfStickers * 0.512;
        }
        double total = subtotal + shippingCost;
        System.out.printf("Your total is: $%.2f", total);
    }


    /**
     * Calculates how many months it will take to pay off a credit card.
     *
     * @param in the Scanner object to read balance, APR, and monthly payment
     */
    public static void problem4_creditCardPayoff(Scanner in) {
        System.out.print("What is your balance? ");
        double balance = Double.parseDouble(in.nextLine());
        System.out.print("What is the APR on the card? ");
        double APR = Double.parseDouble(in.nextLine());
        System.out.print("What is the monthly payment you can make? ");
        double payment = Double.parseDouble(in.nextLine());

        // Input validation
        if (balance < 0 || APR < 0 || payment <= 0) {
            // Negative balance or APR, or non-positive payment is invalid
            System.out.print("Invalid input values.");
            return;
        }

        // Case when balance is zero
        if (balance == 0) {
            // If balance is zero, no months are needed to pay off
            System.out.print("It will take you 0 months to pay off this card.");
            return;
        }

        // Case when APR is zero
        if (APR == 0) {
            // No interest accrual, so months = balance / payment
            int monthsInt = (int) Math.ceil(balance / payment);
            System.out.print("It will take you " + monthsInt + " months to pay off this card.");
            return;
        }

        // Calculate daily rate from APR
        double dailyRate = APR / 36500.0;

        // Calculate the exponential part once for efficiency
        double pow = Math.pow(1 + dailyRate, 30);

        // Calculate monthly rate
        double monthlyRate = pow - 1;

        // Check if the payment is sufficient to cover at least the interest
        if (payment <= balance * monthlyRate) {
            System.out.print("You will never pay off your card with this payment.");
            return;
        }

        // Calculate the numerator and denominator for the formula
        double numerator = Math.log(1 + (balance / payment) * (1 - pow));
        double denominator = Math.log(1 + dailyRate);

        // Calculate the number of months using the formula
        double months = -(1.0 / 30.0) * (numerator / denominator);

        // Round up to the next whole month
        int monthsInt = (int) Math.ceil(months);

        System.out.print("It will take you " + monthsInt + " months to pay off this card.");
    }


    /**
     * Removes 'x' from the first two characters of a string if present.
     *
     * @param str the input string
     * @return the string without 'x' in the first two characters
     */
    public static String problem6_withoutX2(String str) {
        if (str.length() == 0) return "";
        if (str.length() == 1) return str.equals("x") ? "" : str;

        String result = str.substring(0, 2).replace("x", "") + str.substring(2);
        return result;
    }

    /**
     * Returns the number nearest to 21 without going over, given two integers.
     *
     * @param a the first integer
     * @param b the second integer
     * @return the number nearest to 21, or 0 if both are over 21
     */
    public static int problem7_playBlackjack(int a, int b) {
        if (a > 21 && b > 21) {
            return 0;
        }
        if (a > 21) {
            return b;
        }
        if (b > 21) {
            return a;
        }
        return Math.max(a, b);
    }

    /**
     * Assesses the strength of a password based on digits, letters, and special characters.
     *
     * @param password the input password
     * @return a string representing the strength of the password
     */  public static String problem8_assessPasswordStrength(String password) {
        boolean hasLetter = password.matches(".*[a-zA-Z]+.*");
        boolean hasDigit = password.matches(".*[0-9]+.*");
        boolean hasSpecial = password.matches(".*[^a-zA-Z0-9]+.*");
        int length = password.length();

        if (length >= 8) {
            if (hasLetter && hasDigit && hasSpecial) return "very strong";
            if (hasLetter && hasDigit) return "strong";
        }
        if (length < 8) {
            if (hasDigit && !hasLetter) return "very weak";
            if (hasLetter && !hasDigit) return "weak";
        }
        return "medium";
    }
    /**
     * Helps the user troubleshoot car issues based on their responses.
     *
     * @param in the Scanner object to read user input
     */
    public static void problem9_troubleshootCarIssues(Scanner in) {
        System.out.print("Is the car silent when you turn the key? ");
        String answer = in.nextLine().trim().toLowerCase();
        if (answer.startsWith("y")) {
            System.out.print("Are the battery terminals corroded? ");
            answer = in.nextLine().trim().toLowerCase();
            if (answer.startsWith("y")) {
                System.out.print("Clean terminals and try starting again.");
            } else if (answer.startsWith("n")) {
                System.out.print("Replace cables and try again."); // Corrected output
            } else {
                System.out.print("Invalid input. Exiting.");
            }
        } else if (answer.startsWith("n")) {
            System.out.print("Does the car make a clicking noise? ");
            answer = in.nextLine().trim().toLowerCase();
            if (answer.startsWith("y")) {
                System.out.print("Replace the battery.");
            } else if (answer.startsWith("n")) {
                System.out.print("Does the car crank up but fail to start? ");
                answer = in.nextLine().trim().toLowerCase();
                if (answer.startsWith("y")) {
                    System.out.print("Check spark plug connections.");
                } else if (answer.startsWith("n")) {
                    System.out.print("Does the engine start and then die? ");
                    answer = in.nextLine().trim().toLowerCase();
                    if (answer.startsWith("y")) {
                        System.out.print("Does your car have fuel injection? ");
                        answer = in.nextLine().trim().toLowerCase();
                        if (answer.startsWith("y")) {
                            System.out.print("Get it in for service.");
                        } else if (answer.startsWith("n")) {
                            System.out.print("Check to ensure the choke is opening and closing.");
                        } else {
                            System.out.print("Invalid input. Exiting.");
                        }
                    } else if (answer.startsWith("n")) {
                        System.out.print("This should not be possible.");
                    } else {
                        System.out.print("Invalid input. Exiting.");
                    }
                } else {
                    System.out.print("Invalid input. Exiting.");
                }
            } else {
                System.out.print("Invalid input. Exiting.");
            }
        } else {
            System.out.print("Invalid input. Exiting.");
        }
    }

}
