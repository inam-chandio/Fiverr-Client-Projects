package labs.lab2.tests;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Test;

import labs.lab2.*;

public class Lab2Test {

    private final double EPSILON = 1e-3;

    @Test
    public void problem1() {
        assertEquals("dollars: 2, cents: 95", Main.problem1_getDollarsAndCents(2.95));
        assertEquals("dollars: 4, cents: 34", Main.problem1_getDollarsAndCents(4.34));
        assertEquals("dollars: 6998, cents: 0", Main.problem1_getDollarsAndCents(6998));
        assertEquals("dollars: 0, cents: 69", Main.problem1_getDollarsAndCents(0.69));
    }

    @Test
    public void problem2() {
        // Test case 1
        String input = "4155551212\n";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Main.problem2_formatPhoneNumber(new Scanner(System.in));
        String result = output.toString();
        assertEquals("Please enter a ten-digit phone number: (415) 555-1212", result);

        // Test case 2
        input = "0000000000\n";

        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Main.problem2_formatPhoneNumber(new Scanner(System.in));
        result = output.toString();
        assertEquals("Please enter a ten-digit phone number: (000) 000-0000", result);

        // Test case 3
        input = "5233039999\n";

        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Main.problem2_formatPhoneNumber(new Scanner(System.in));
        result = output.toString();
        assertEquals("Please enter a ten-digit phone number: (523) 303-9999", result);
    }

    @Test
    public void problem3() {
        // Test case 1:
        String input = "1.00\n10";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Main.problem3_calculateTotal(new Scanner(System.in));
        String total = output.toString();
        assertEquals("Enter price per sticker: " + "Enter the number of stickers: " + "Your total is: $12.50", total);


        // Test case 2:
        input = "0\n100";

        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Main.problem3_calculateTotal(new Scanner(System.in));
        total = output.toString();
        assertEquals("Enter price per sticker: " + "Enter the number of stickers: " + "Your total is: $15.00", total);


        // Test case 3:
        input = "3.62\n781";

        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Main.problem3_calculateTotal(new Scanner(System.in));
        total = output.toString();
        assertEquals("Enter price per sticker: " + "Enter the number of stickers: " + "Your total is: $3227.09", total);
    }

    @Test
    public void problem4() {
        // Test case 1:
        String input = "21394.88\n5.9\n2000";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Main.problem4_creditCardPayoff(new Scanner(System.in));
        String result = output.toString();
        assertEquals("What is your balance? " + "What is the APR on the card? " +
                "What is the monthly payment you can make? " +
                "It will take you 12 months to pay off this card.", result);


        // Test case 2:
        input = "5000\n12\n100";

        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Main.problem4_creditCardPayoff(new Scanner(System.in));
        result = output.toString();
        assertEquals("What is your balance? " + "What is the APR on the card? " +
                "What is the monthly payment you can make? " +
                "It will take you 70 months to pay off this card.", result);


        // Test case 3:
        input = "0\n100\n1000";

        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Main.problem4_creditCardPayoff(new Scanner(System.in));
        result = output.toString();
        assertEquals("What is your balance? " + "What is the APR on the card? " +
                "What is the monthly payment you can make? " +
                "It will take you 0 months to pay off this card.", result);


        // Test case 4:
        input = "1000\n59.1\n50.25";

        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Main.problem4_creditCardPayoff(new Scanner(System.in));
        result = output.toString();
        assertEquals("What is your balance? " + "What is the APR on the card? " +
                "What is the monthly payment you can make? " +
                "It will take you 95 months to pay off this card.", result);
    }

    @Test
    public void problem5() {
        Boat boat1 = new Boat(0, 0, 0);
        boat1.move(50);
        assertEquals(50.0, boat1.getX(), EPSILON);
        assertEquals(0.0, boat1.getY(), EPSILON);
        assertEquals(0.0, boat1.getDirection(), EPSILON);

        boat1.turn(360);
        boat1.move(10);
        assertEquals(60.0, boat1.getX(), EPSILON);
        assertEquals(0.0, boat1.getY(), EPSILON);
        assertEquals(360.0, boat1.getDirection(), EPSILON);

        boat1.turn(-2);
        assertEquals(358.0, boat1.getDirection(), EPSILON);

        boat1.turn(-440);
        boat1.move(255.3);
        assertEquals(95.53089267510472, boat1.getX(), EPSILON);
        assertEquals(-252.81543794972293, boat1.getY(), EPSILON);
        assertEquals(-82.0, boat1.getDirection(), EPSILON);


        Boat boat2 = new Boat(0, 0, 30);
        boat2.move(50);

        assertEquals(43.301270, boat2.getX(), EPSILON);
        assertEquals(25.0, boat2.getY(), EPSILON);
        assertEquals(30.0, boat2.getDirection(), EPSILON);

        boat2.turn(30);
        boat2.move(100);
        assertEquals(93.301270, boat2.getX(), EPSILON);
        assertEquals(111.602540, boat2.getY(), EPSILON);
        assertEquals(60.0, boat2.getDirection(), EPSILON);

        boat2.turn(-60);
        boat2.move(100);
        assertEquals(193.30127018922195, boat2.getX(), EPSILON);
        assertEquals(111.60254037844386, boat2.getY(), EPSILON);
        assertEquals(0.0, boat2.getDirection(), EPSILON);
    }

    @Test
    public void problem6() {
        assertEquals("Hi", Main.problem6_withoutX2("xHi"));
        assertEquals("Hi", Main.problem6_withoutX2("Hxi"));
        assertEquals("Hi", Main.problem6_withoutX2("Hi"));
        assertEquals("XHi", Main.problem6_withoutX2("XHi"));
        assertEquals("Hi", Main.problem6_withoutX2("xxHi"));
        assertEquals("Hix", Main.problem6_withoutX2("Hix"));
        assertEquals("axb", Main.problem6_withoutX2("xaxb"));
        assertEquals("", Main.problem6_withoutX2("xx"));
        assertEquals("", Main.problem6_withoutX2("x"));
        assertEquals("", Main.problem6_withoutX2(""));
        assertEquals("Hello", Main.problem6_withoutX2("Hello"));
        assertEquals("Hexllo", Main.problem6_withoutX2("Hexllo"));
        assertEquals("Hxllo", Main.problem6_withoutX2("xHxllo"));
    }

    @Test
    public void problem7() {
        assertEquals(21, Main.problem7_playBlackjack(19, 21));
        assertEquals(21, Main.problem7_playBlackjack(21, 19));
        assertEquals(19, Main.problem7_playBlackjack(19, 22));
        assertEquals(19, Main.problem7_playBlackjack(19, 19));
        assertEquals(0, Main.problem7_playBlackjack(22, 22));
        assertEquals(1, Main.problem7_playBlackjack(1, 1));
        assertEquals(19, Main.problem7_playBlackjack(19, 1));
        assertEquals(19, Main.problem7_playBlackjack(1, 19));
        assertEquals(19, Main.problem7_playBlackjack(19, 220));
    }

    @Test
    public void problem8() {
        assertEquals("very weak", Main.problem8_assessPasswordStrength("12345"));
        assertEquals("weak", Main.problem8_assessPasswordStrength("abcdef"));
        assertEquals("strong", Main.problem8_assessPasswordStrength("abc123xyz"));
        assertEquals("very strong", Main.problem8_assessPasswordStrength("1337h@xor"));
        assertEquals("very strong", Main.problem8_assessPasswordStrength("1337h xor"));
        assertEquals("medium", Main.problem8_assessPasswordStrength("123abc"));
        assertEquals("medium", Main.problem8_assessPasswordStrength("a bc de f"));
        assertEquals("strong", Main.problem8_assessPasswordStrength("abc123XYZ"));
        assertEquals("very strong", Main.problem8_assessPasswordStrength("1337h@x!$or"));
        assertEquals("medium", Main.problem8_assessPasswordStrength("@#$%"));
        assertEquals("medium", Main.problem8_assessPasswordStrength("1337878978978"));
        assertEquals("medium", Main.problem8_assessPasswordStrength("abcdefjkdlskdjfkdjf"));
    }

    @Test
    public void problem9() {
        // Test case 1:
        String input = "y\nNO SIR\n";

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Main.problem9_troubleshootCarIssues(new Scanner(System.in));
        String result = output.toString();
        assertEquals("Is the car silent when you turn the key? " + "Are the battery terminals corroded? " +
                "Replace cables and try again.", result);


        // Test case 2:
        input = "no\nny\nI'm not sure";

        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Main.problem9_troubleshootCarIssues(new Scanner(System.in));
        result = output.toString();
        assertEquals("Is the car silent when you turn the key? " + "Does the car make a clicking noise? " +
                "Does the car crank up but fail to start? " + "Invalid input. Exiting.", result);


        // Test case 3:
        input = "no\nny\nNOO\ny\nYESS";

        in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        Main.problem9_troubleshootCarIssues(new Scanner(System.in));
        result = output.toString();
        assertEquals("Is the car silent when you turn the key? " + "Does the car make a clicking noise? " +
                "Does the car crank up but fail to start? " + "Does the engine start and then die? " +
                "Does your car have fuel injection? " + "Get it in for service.", result);
    }

    @Test
    public void problem10() {
        // no discount:
        GroceryBill bill = new GroceryBill(5);
        assertEquals(0, bill.getDiscount(), EPSILON);
        assertEquals(5.5, bill.getAmountOwed(), EPSILON);

        // no discount:
        bill = new GroceryBill(2.88);
        assertEquals(0, bill.getDiscount(), EPSILON);
        assertEquals(3.168, bill.getAmountOwed(), EPSILON);

        // 5% discount:
        bill = new GroceryBill(10);
        assertEquals(5, bill.getDiscount(), EPSILON);
        assertEquals(10.45, bill.getAmountOwed(), EPSILON);

        // 5% discount:
        bill = new GroceryBill(59.99);
        assertEquals(5, bill.getDiscount(), EPSILON);
        assertEquals(62.68955, bill.getAmountOwed(), EPSILON);

        // 10% discount:
        bill = new GroceryBill(100);
        assertEquals(10, bill.getDiscount(), EPSILON);
        assertEquals(99, bill.getAmountOwed(), EPSILON);

        // 10% discount:
        bill = new GroceryBill(150);
        assertEquals(10, bill.getDiscount(), EPSILON);
        assertEquals(148.5, bill.getAmountOwed(), EPSILON);

        // 15% discount:
        bill = new GroceryBill(151);
        assertEquals(15, bill.getDiscount(), EPSILON);
        assertEquals(141.185, bill.getAmountOwed(), EPSILON);

        // 15% discount:
        bill = new GroceryBill(210);
        assertEquals(15, bill.getDiscount(), EPSILON);
        assertEquals(196.35, bill.getAmountOwed(), EPSILON);

        // 20% discount:
        bill = new GroceryBill(10000);
        assertEquals(20, bill.getDiscount(), EPSILON);
        assertEquals(8800, bill.getAmountOwed(), EPSILON);

        // 20% discount:
        bill = new GroceryBill(85572.34);
        assertEquals(20, bill.getDiscount(), EPSILON);
        assertEquals(75303.66, bill.getAmountOwed(), EPSILON);
    }
}
