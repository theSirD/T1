package Lab1.DomainEntities.Transactions;

import Lab1.DomainEntities.BankAccounts.BaseBankAccount;
import Lab1.DomainServices.Utilities.CustomTuple;

public class PayInterestTransaction extends BaseTransaction {
    private final BaseBankAccount _receiver;

    public PayInterestTransaction(BaseBankAccount receiver) {
        _receiver = receiver;
    }

    @Override
    public void execute() {
        _receiver.getInterestPayed();
        CustomTuple<Long, BaseTransaction> transactionHistoryEntry = new CustomTuple<>(Long.valueOf(_receiver.transactionsHistory.size() + 1), this);
        _receiver.transactionsHistory.push(transactionHistoryEntry);
    }

    @Override
    public void undo() {
        _receiver.unGetInterestPayed();
        _receiver.transactionsHistory.pop();
    }
}
