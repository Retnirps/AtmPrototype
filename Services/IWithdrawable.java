package Services;

import Models.UserCard;

import java.sql.SQLException;

public interface IWithdrawable {
    boolean canPay(double amount, UserCard card) throws SQLException;
}
