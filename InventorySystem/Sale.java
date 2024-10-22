public class Sale {
    private String description;
    private String filamentType;
    private String subvariant;
    private String color;
    private double gramsUsed;
    private double price;
    private String paymentStatus;

    /**
     * Constructor to initialize a Sale object with all necessary details.
     *
     * @param description   Description of the print job.
     * @param filamentType  Type of filament used (e.g., PLA, ABS, TPU).
     * @param subvariant    Subvariant of the filament type.
     * @param color         Color(s) used in the print job.
     * @param gramsUsed     Total grams of filament used.
     * @param price         Price charged for the print job.
     * @param paymentStatus Payment status ("Paid" or "Pending Payment").
     */
    public Sale(String description, String filamentType, String subvariant, String color,
                double gramsUsed, double price, String paymentStatus) {
        this.description = description;
        this.filamentType = filamentType;
        this.subvariant = subvariant;
        this.color = color;
        this.gramsUsed = gramsUsed;
        this.price = price;
        this.paymentStatus = paymentStatus;
    }

    // Getters

    public String getDescription() {
        return description;
    }

    public String getFilamentType() {
        return filamentType;
    }

    public String getSubvariant() {
        return subvariant;
    }

    public String getColor() {
        return color;
    }

    public double getGramsUsed() {
        return gramsUsed;
    }

    public double getPrice() {
        return price;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    // Setters

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFilamentType(String filamentType) {
        this.filamentType = filamentType;
    }

    public void setSubvariant(String subvariant) {
        this.subvariant = subvariant;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setGramsUsed(double gramsUsed) {
        this.gramsUsed = gramsUsed;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    // Additional Methods (if needed)

    /**
     * Calculates the cost per gram for the sale.
     *
     * @return The cost per gram.
     */
    public double calculateCostPerGram() {
        return price / gramsUsed;
    }

    /**
     * Provides a string representation of the Sale object.
     *
     * @return String representation of the sale.
     */
    @Override
    public String toString() {
        return "Sale{" +
                "description='" + description + '\'' +
                ", filamentType='" + filamentType + '\'' +
                ", subvariant='" + subvariant + '\'' +
                ", color='" + color + '\'' +
                ", gramsUsed=" + gramsUsed +
                ", price=" + price +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }
}
