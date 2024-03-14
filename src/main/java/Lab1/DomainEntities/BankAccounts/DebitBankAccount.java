package Lab1.DomainEntities.BankAccounts;

import Lab1.DomainEntities.User.BankAccountStatus;

public class DebitBankAccount extends BaseBankAccount {
    public DebitBankAccount(Double limitForUntrustworthyAccounts, BankAccountStatus status, Long userId, Double initialBalance, Double monthlyInterestInPercent) {
        balance = initialBalance;
        type = BankAccountType.Debit;
        commission = 0.0;
        this.monthlyInterestInPercent = monthlyInterestInPercent;
        this.userId = userId;
        this.status = status;
        this.limitForUntrustworthyAccounts = limitForUntrustworthyAccounts;
    }

    @Override
    public void setBalance(Double newBalance) {
        if (newBalance >= 0) balance = newBalance;
    }

    @Override
    public void writeOffCommission() {
        balance -= commission;
    }

    @Override
    public void unWriteOffCommission() {
        balance += commission;
    }
}
