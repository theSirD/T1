package ru.isaev.BankAccounts;

import ru.isaev.User.BankAccountStatus;

public class DepositBankAccount extends BaseBankAccount {
    public Long daysUntilExpiration;

    public DepositBankAccount(Double limitForUntrustworthyAccounts, BankAccountStatus status, Long userId, Long daysUntilExpiration, Double initialBalance, Double baseMonthlyInterestInPercent) {
        this.daysUntilExpiration = daysUntilExpiration;
        balance = initialBalance;
        commission = 0.0;
        type = BankAccountType.Deposit;
        this.userId = userId;
        this.status = status;
        this.limitForUntrustworthyAccounts = limitForUntrustworthyAccounts;

        if (initialBalance < 50000) {
            monthlyInterestInPercent = baseMonthlyInterestInPercent;
        }
        if (initialBalance >= 50000 && initialBalance < 100000) {
            monthlyInterestInPercent = baseMonthlyInterestInPercent + 0.5;
        }
        if (initialBalance > 100000) {
            monthlyInterestInPercent = baseMonthlyInterestInPercent + 1;
        }
    }

    @Override
    public void setBalance(Double newBalance) {
        balance = balance;
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
