public class Filament {
    private final String type;
    private final String subvariant;
    private final String color;
    private final int totalRolls;
    private final int gramsPerRoll = 1000;
    private double remainingGrams;

    public Filament(String type, String subvariant, String color, int totalRolls) {
        this.type = type;
        this.subvariant = subvariant;
        this.color = color;
        this.totalRolls = totalRolls;
        this.remainingGrams = totalRolls * gramsPerRoll;
    }

    public String getType() {
        return type;
    }

    public String getSubvariant() {
        return subvariant;
    }

    public String getColor() {
        return color;
    }

    public int getTotalRolls() {
        return totalRolls;
    }

    public double getRemainingGrams() {
        return remainingGrams;
    }

    public void deductGrams(double gramsUsed) {
        this.remainingGrams -= gramsUsed;
    }
}
