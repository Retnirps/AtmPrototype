package Services;

import Models.Bill;
import Models.UserCard;

import java.sql.Connection;
import java.sql.SQLException;

public class BillsPaymentService implements IService, IWithdrawable {
    private final BankService bankService = new BankService();
    private final Connection connection;
    private double comission;

    public BillsPaymentService(Connection connection) {
        this.connection = connection;
    }

    public boolean payBill(Bill bill, UserCard card, String bankOfATM) throws SQLException {
        if (!card.bankOfCard.equals(bankOfATM)) {
            calculateComission(bill.getAmountToPay());
        }
        if (canPay(bill.getAmountToPay(), card)) {
            double amountToUpdate = bankService.getAmountById(card.getCardId(), connection)
                    - bill.getAmountToPay()
                    - comission;
            bankService.updateAmountById(card.getCardId(), amountToUpdate, connection);
            bill.setPayed(true);
            return true;
        } else {
            return false;
        }
    }

    public String showBillInfo(Bill bill) {
        return bill.toString();
    }

    @Override
    public void calculateComission(double amount) {
        comission = 0.05 * amount;
    }

    @Override
    public boolean canPay(double amount, UserCard card) throws SQLException {
        return bankService.getAmountById(card.getCardId(), connection) >= amount + comission;
    }
}
