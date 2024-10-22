package labs.lab2;

public class GroceryBill {

    private double itemTotal;

    /**
     * Constructor to initialize the total cost of the items in the grocery bill.
     *
     * @param itemTotal the total cost of items before applying any discounts or tax
     */
    public GroceryBill(double itemTotal) {
        this.itemTotal = itemTotal;
    }

    /**
     * Calculates the discount percentage based on the total cost of items.
     *
     * @return the discount percentage to be applied to the total cost
     */
    public double getDiscount() {
        if (itemTotal >= 10000) {
            return 20.0;
        } else if (itemTotal > 150) {
            return 15.0;
        } else if (itemTotal == 150) {
            return 10.0;
        } else if (itemTotal >= 100) {
            return 10.0;
        } else if (itemTotal >= 10) {
            return 5.0;
        }
        return 0.0;
    }

    /**
     * Calculates the total amount owed after applying the discount and adding a 10% sales tax.
     *
     * @return the total amount owed after discount and tax
     */
    public double getAmountOwed() {
        double discount = getDiscount();
        double discountedTotal = itemTotal * (1 - discount / 100);
        double totalWithTax = discountedTotal * 1.10;
        return totalWithTax;
    }
}
