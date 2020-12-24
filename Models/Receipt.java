package Models;

public class Receipt {
    private final long timestamp;
    private final String type;
    private final double amount;

    public Receipt(String type, double amount) {
        timestamp = System.currentTimeMillis() / 1000L;
        this.type = type;
        this.amount = amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "timestamp=" + timestamp +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                '}';
    }
}
