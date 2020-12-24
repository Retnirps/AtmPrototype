package Services;

import Models.UserCard;

import java.sql.Connection;
import java.sql.SQLException;

public class DepositService implements IService {
    private final BankService bankService = new BankService();
    private final Connection connection;
    private double comission;

    public DepositService(Connection connection) {
        this.connection = connection;
    }

    public void depositMoney(double amount, UserCard card, String bankOfATM) throws SQLException {
        if (!card.bankOfCard.equals(bankOfATM)) {
            calculateComission(amount);
        }
        double amountToUpdate = bankService.getAmountById(card.getCardId(), connection) + amount - comission;
        bankService.updateAmountById(card.getCardId(), amountToUpdate, connection);
    }

    @Override
    public void calculateComission(double amount) {
        comission = 0.05 * amount;
    }
}
