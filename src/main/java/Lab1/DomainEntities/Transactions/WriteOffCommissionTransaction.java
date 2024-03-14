package Lab1.DomainEntities.Transactions;

import Lab1.DomainEntities.BankAccounts.BaseBankAccount;
import Lab1.DomainServices.Utilities.CustomTuple;

public class WriteOffCommissionTransaction extends BaseTransaction {
    private final BaseBankAccount _receiver;

    public WriteOffCommissionTransaction(BaseBankAccount receiver) {
        _receiver = receiver;
    }

    @Override
    public void execute() {
        _receiver.writeOffCommission();
        CustomTuple<Long, BaseTransaction> transactionHistoryEntry = new CustomTuple<>(Long.valueOf(_receiver.transactionsHistory.size() + 1), this);
        _receiver.transactionsHistory.push(transactionHistoryEntry);
    }

    @Override
    public void undo() {
        _receiver.unWriteOffCommission();
        _receiver.transactionsHistory.pop();
    }
}
