package Models;

public class Bill {
    private final long id;
    private final String name;
    private final double amountToPay;
    private boolean payed;

    public Bill(long id, String name, double amountToPay) {
        this.id = id;
        this.name = name;
        this.amountToPay = amountToPay;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getAmountToPay() {
        return amountToPay;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amountToPay=" + amountToPay +
                ", payed=" + payed +
                '}';
    }
}
