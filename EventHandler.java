import Interfaces.IReceiptPrinter;
import Interfaces.IScaner;
import Interfaces.IValidation;
import Models.Bill;
import Models.Receipt;
import Models.Transaction;
import Models.UserCard;
import Services.*;

import java.sql.*;
import java.util.Scanner;

import static java.lang.System.*;

public class EventHandler implements IScaner, IReceiptPrinter, IValidation {
    private final Connection connection;
    private final WithdrawService withdrawService;
    private final DepositService depositService;
    private final BillsPaymentService billsPaymentService;
    private final TransferService transferService;
    private final BankService bankService;
    private UserCard card;
    private Receipt receipt;
    private Transaction transaction;
    private final long atmId;
    private final String bankOfATM;

    public EventHandler(long atmId, String bankOfATM) throws ClassNotFoundException, SQLException {
        this.atmId = atmId;
        this.bankOfATM = bankOfATM;
        String url = "jdbc:mysql://localhost:3306/bank_project?&serverTimezone=UTC";
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(url, "majesta", "majesta");
        withdrawService = new WithdrawService(connection);
        depositService = new DepositService(connection);
        billsPaymentService = new BillsPaymentService(connection);
        transferService = new TransferService(connection);
        bankService = new BankService();
    }

    public void handleWithdraw(double amount) throws SQLException {
        if (withdrawService.withdrawMoney(amount, card, bankOfATM)) {
            transactionResult("WITHDRAW", amount);
        } else {
            transactionResult("DON'T ENOUGH MONEY", 0);
            showMessage("DON'T ENOUGH MONEY");
        }
        printReceipt();
    }

    public void handleDeposit(double amount) throws SQLException, ClassNotFoundException {
        depositService.depositMoney(amount, card, bankOfATM);
        transactionResult("DEPOSIT", amount);
        printReceipt();
    }

    public void handleBillPayment(Bill bill) throws SQLException, ClassNotFoundException {
        out.println(billsPaymentService.showBillInfo(bill));
        if (billsPaymentService.payBill(bill, card, bankOfATM)) {
            out.println("PAYED: " + bill.isPayed());
            transactionResult("BILL_PAYMENT", bill.getAmountToPay());
        } else {
            transactionResult("DON'T ENOUGH MONEY", 0);
            showMessage("DON'T ENOUGH MONEY");
        }
        printReceipt();
    }

    public void handleTransfer(double amount, UserCard cardTo) throws SQLException, ClassNotFoundException {
        if (transferService.transferToCard(amount, card, cardTo, bankOfATM)) {
            transactionResult("TRANSFER", amount);
        } else {
            transactionResult("DON'T ENOUGH MONEY", 0);
            showMessage("DON'T ENOUGH MONEY");
        }
        printReceipt();
    }

    public double checkBalance() throws SQLException {
        return bankService.getAmountById(card.getCardId(), connection);
    }

    private void saveTransaction(Transaction transaction) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("insert into transactions(timestamp, type, amount, atm_id) values ("
                + transaction.getTimestamp()
                + ", \"" + transaction.getType() + "\", "
                + transaction.getAmount() + ", "
                + transaction.getAtmId() + ");");
    }

    private void showMessage(String message) {
        out.println(message);
    }

    private void transactionResult(String type, double amount) throws SQLException {
        receipt = new Receipt(type, amount);
        transaction = new Transaction(type, amount, atmId);
        saveTransaction(transaction);
    }

    @Override
    public void printReceipt() {
        out.println(receipt);
    }

    @Override
    public void scan() {
        card = new UserCard(3456, "SBER");
    }

    @Override
    public void giveInfo() {
        out.println(card);
    }

    @Override
    public void validatePin() throws SQLException {
        Scanner scanner = new Scanner(in);
        out.println("ENTER PIN:");
        while (!bankService.validateCardPinById(card.getCardId(), scanner.nextInt(), connection)) {
            showMessage("INCORRECT PIN, TRY AGAIN:");
        }
    }
}
