package DomainEntities.Transactions;

import DomainEntities.BankAccounts.BankAccountType;
import DomainEntities.BankAccounts.BaseBankAccount;
import DomainServices.Utilities.CustomTuple;

public class TransferMoneyBetweenAccountsTransaction extends BaseTransaction {
    private final BaseBankAccount _sender;
    private final BaseBankAccount _receiver;
    private final Double _amountOfMoneyToTransfer;

    public TransferMoneyBetweenAccountsTransaction(BaseBankAccount receiver, BaseBankAccount sender, Double amountOfMoneyToTransfer) {
        _receiver = receiver;
        _sender = sender;
        _amountOfMoneyToTransfer = amountOfMoneyToTransfer;
    }

    @Override
    public void execute() {
        if (_sender.limitForUntrustworthyAccounts == null || _sender.limitForUntrustworthyAccounts >= _amountOfMoneyToTransfer) {
            if (_receiver.type == BankAccountType.Debit && _receiver.getBalance() - _amountOfMoneyToTransfer < 0)
                return;

            if (_sender.type == BankAccountType.Credit && _sender.getBalance() - _amountOfMoneyToTransfer < -_sender.creditLimit)
                return;

            _sender.limitForUntrustworthyAccounts -= _amountOfMoneyToTransfer;
            _sender.setBalance(_sender.getBalance() - _amountOfMoneyToTransfer);
            _receiver.setBalance(_receiver.getBalance() + _amountOfMoneyToTransfer);
            CustomTuple<Long, BaseTransaction> transactionHistoryEntry = new CustomTuple<>(Long.valueOf(_receiver.transactionsHistory.size() + 1), this);
            _receiver.transactionsHistory.push(transactionHistoryEntry);
        }
    }

    @Override
    public void undo() {
        _sender.setBalance(_sender.getBalance() + _amountOfMoneyToTransfer);
        _receiver.setBalance(_receiver.getBalance() - _amountOfMoneyToTransfer);
        _receiver.transactionsHistory.pop();
    }
}
