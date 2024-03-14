package DomainEntities.Transactions;

import DomainEntities.BankAccounts.BankAccountType;
import DomainEntities.BankAccounts.BaseBankAccount;
import DomainServices.Utilities.CustomTuple;

public class RemoveMoneyTransaction  extends BaseTransaction {
    private final BaseBankAccount _receiver;
    private final Double _amountOfMoneyToRemove;

    public RemoveMoneyTransaction(BaseBankAccount receiver, Double amountOfMoneyToRemove) {
        _receiver = receiver;
        _amountOfMoneyToRemove = amountOfMoneyToRemove;
    }

    @Override
    public void execute() {
        if (_receiver.limitForUntrustworthyAccounts == null || _receiver.limitForUntrustworthyAccounts >= _amountOfMoneyToRemove) {
            if (_receiver.type == BankAccountType.Debit && _receiver.getBalance() - _amountOfMoneyToRemove < 0)
                return;

            if (_receiver.type == BankAccountType.Credit && _receiver.getBalance() - _amountOfMoneyToRemove < -_receiver.creditLimit)
                return;

            _receiver.limitForUntrustworthyAccounts -= _amountOfMoneyToRemove;
            _receiver.setBalance(_receiver.getBalance() - _amountOfMoneyToRemove);
            CustomTuple<Long, BaseTransaction> transactionHistoryEntry = new CustomTuple<>(Long.valueOf(_receiver.transactionsHistory.size() + 1), this);
            _receiver.transactionsHistory.push(transactionHistoryEntry);
        }


    }

    @Override
    public void undo() {
        _receiver.setBalance(_receiver.getBalance() + _amountOfMoneyToRemove);
        _receiver.transactionsHistory.pop();
    }
}
