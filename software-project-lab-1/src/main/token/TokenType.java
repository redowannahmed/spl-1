package token;
public enum TokenType {
    BREAKFAST(40),
    LUNCH(70),
    DINNER(70);

    private final int price;

    TokenType(int price) {
        this.price = price;
    }

    public int getCost() {
        return price;
    }
}
