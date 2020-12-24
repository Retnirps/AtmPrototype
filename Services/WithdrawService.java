package Services;

import Models.UserCard;

import java.sql.Connection;
import java.sql.SQLException;

public class WithdrawService implements IService, IWithdrawable {
    private final BankService bankService = new BankService();
    private final Connection connection;
    private double comission;

    public WithdrawService(Connection connection) {
        this.connection = connection;
    }

    public boolean withdrawMoney(double amount, UserCard card, String bankOfATM) throws SQLException {
        if (!card.bankOfCard.equals(bankOfATM)) {
            calculateComission(amount);
        }
        if (canPay(amount, card)) {
            double amountToUpdate = bankService.getAmountById(card.getCardId(), connection) - amount - comission;
            bankService.updateAmountById(card.getCardId(), amountToUpdate, connection);
            return true;
        } else {
            return false;
        }
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
