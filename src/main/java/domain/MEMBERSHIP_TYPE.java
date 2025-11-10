package domain;

public enum MEMBERSHIP_TYPE {
    GOLD("The Gold Pack", 32.99, "Daily/Weekly/Monthly",
            "Restricted to the specific gym location where the plan was bought", 100),
    PLATINUM("The Platinum Pack", 42.99, "Monthly/Quarterly",
            "Restricted to the gym locations, available within the residency state", 200),
    DIAMOND("The Diamond Pack", 57.0, "Monthly/Quarterly/Annual",
            "Full access to all existing gym locations in the country", 300);

    private String name;
    private double price;
    private String billingFrequency;
    private String accessLevel;
    private int productCode;

    private MEMBERSHIP_TYPE(String name, double price, String billingFrequency, String accessLevel,
                            int productCode) {
        setName(name);
        setPrice(price);
        setBillingFrequency(billingFrequency);
        setLevelOfAccess(accessLevel);
        setProductCode(productCode);
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        if(name.isBlank()) {
            throw new IllegalArgumentException("The name of the membership cannot be empty!");
        }
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    private void setPrice(double price) {
        if(price <= 0){
            throw new IllegalArgumentException("The price of the membership cannot be a negative number o zero!");
        }
        this.price = price;
    }

    public String getBillingFrequency() {
        return billingFrequency;
    }

    private void setBillingFrequency(String paymentFrequency) {
        if(paymentFrequency.isBlank()){
            throw new IllegalArgumentException("The billing frequency is not defined or is null!");
        }
        this.billingFrequency = paymentFrequency;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    private void setLevelOfAccess(String accessLevel) {
        if(accessLevel.isBlank()) {
            throw new IllegalArgumentException("Gym's access level was not specified!");
        }
        this.accessLevel = accessLevel;
    }

    public int getProductCode() {
        return productCode;
    }

    private void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MEMBERSHIP_TYPE {\n");
        sb.append("\t - [NAME_OF_PRODUCT]: ").append(name).append('\n');
        sb.append("\t - [PRICE]: ").append(price).append("\n");
        sb.append("\t - [BILLING_FREQUENCY]: ").append(billingFrequency).append('\n');
        sb.append("\t - [LEVEL_OF_ACCESS]: ").append(accessLevel).append('\n');
        sb.append("\t - [PRODUCT_CODE]: ").append(productCode).append('\n');
        sb.append('}');

        return sb.toString();
    }
}
