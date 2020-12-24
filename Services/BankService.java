package Services;

import java.sql.*;

public class BankService {
    public boolean validateCardPinById(int cardId, int pinInput, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select pin from cards where card_id = " + cardId + ";");
        int pin = 0;
        while(resultSet.next()) {
            pin = resultSet.getInt("pin");
        }
        return pinInput == pin;
    }

    public double getAmountById(int cardId, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select amount from cards where card_id = " + cardId + ";");
        double amount = 0;
        while(resultSet.next()) {
            amount = resultSet.getDouble("amount");
        }
        return amount;
    }

    public void updateAmountById(int cardId, double amount, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("update cards set amount = " + amount + " where card_id = " + cardId + ";");
    }
}
