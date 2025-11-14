package domain;

// Creating an improved enum artifact to manage the different accepted currency within the VendingMachine
public enum TYPE_OF_CURRENCY {
    USD("American Dollar", '$', "United States of America (USA)"),
    EUR("Euro", '€', "European Union (EU)"),
    GBP("Sterling Pound", '£', "United Kingdom (UK)");

    private String denomination;
    private char symbol;
    private String countryProvenance;

    // Private constructor to avoid security issues
    private TYPE_OF_CURRENCY(String denomination, char symbol, String countryProvenance){
        setDenomination(denomination);
        setSymbol(symbol);
        setCountryProvenance(countryProvenance);
    }

    // Getter & Setter methods for each enum attributes
    private void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    private void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    private void setCountryProvenance(String countryProvenance) {
        this.countryProvenance = countryProvenance;
    }

    public String getDenomination() {
        return denomination;
    }

    public char getSymbol() {
        return symbol;
    }

    public String getCountryProvenance() {
        return countryProvenance;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("denomination= '").append(denomination);
        sb.append("symbol= '").append(symbol);
        sb.append("countryProvenance= '").append(countryProvenance);

        return sb.toString();
    }
}

