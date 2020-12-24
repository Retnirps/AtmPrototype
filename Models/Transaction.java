package Models;

public class Transaction {
    private long id;
    private long timestamp;
    private String type;
    private double amount;
    private long atmId;

    public Transaction(String type, double amount, long atmId) {
        timestamp = System.currentTimeMillis() / 1000L;
        this.type = type;
        this.amount = amount;
        this.atmId = atmId;
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

    public long getAtmId() {
        return atmId;
    }
}
