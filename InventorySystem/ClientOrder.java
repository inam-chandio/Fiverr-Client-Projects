public class ClientOrder {
    private final String description;
    private final String filamentType;
    private final String color;
    private final double gramsUsed;
    private final double price;
    private final String paymentStatus;

    public ClientOrder(String description, String filamentType, String color, double gramsUsed, double price, String paymentStatus) {
        this.description = description;
        this.filamentType = filamentType;
        this.color = color;
        this.gramsUsed = gramsUsed;
        this.price = price;
        this.paymentStatus = paymentStatus;
    }

    public String getDescription() {
        return description;
    }

    public String getFilamentType() {
        return filamentType;
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
}
