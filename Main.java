import Models.Bill;
import Models.UserCard;

import java.sql.SQLException;

import static java.lang.System.out;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        EventHandler handler = new EventHandler(1342, "SBER");

        handler.scan();
        handler.validatePin();
//        handler.handleWithdraw(500.0);
        handler.handleDeposit(200.0);

//        Bill bill = new Bill(1232, "TRAFFIC_FINE", 1500.0);
//        handler.handleBillPayment(bill);

        UserCard cardTo = new UserCard(2148, "VTB");
        handler.handleTransfer(350.0, cardTo);

        double balance = handler.checkBalance();
        out.println("BALANCE: " + balance);
    }
}
