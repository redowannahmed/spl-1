public enum TokenType {
    BREAKFAST(50),
    LUNCH(100),
    DINNER(150);

    private final int price;

    TokenType(int price) {
        this.price = price;
    }

    public int getCost() {
        return price;
    }
}
