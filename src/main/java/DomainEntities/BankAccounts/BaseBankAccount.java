package DomainEntities.BankAccounts;

import DomainEntities.Transactions.BaseTransaction;
import DomainEntities.User.BankAccountStatus;
import DomainServices.Utilities.CustomTuple;

import java.util.Stack;

abstract public class BaseBankAccount {
    protected Double balance;

    public Double commission;

    public Double monthlyInterestInPercent;

    protected Double accruedInterest;

    protected Double lastMonthAccruedInterest;

    protected Long amountOfDaysBeforePayingAccruedInterest;

    public Long userId;

    public Double limitForUntrustworthyAccounts;

    public Double creditLimit;

    public BankAccountType type;

    public BankAccountStatus status;

    public Stack<CustomTuple<Long, BaseTransaction>> transactionsHistory;

    public Double getBalance() { return balance; }

    abstract public void setBalance(Double newBalance);

    abstract public void writeOffCommission();

    abstract public void unWriteOffCommission();

    public void getInterestPayed() {
        accruedInterest += balance * monthlyInterestInPercent / 365;
        amountOfDaysBeforePayingAccruedInterest += 1;

        if (amountOfDaysBeforePayingAccruedInterest == 30) {
            balance += accruedInterest;
            lastMonthAccruedInterest = accruedInterest;
            accruedInterest = 0.0;
            amountOfDaysBeforePayingAccruedInterest = 0L;
        }
    }

    public void unGetInterestPayed() {
        if (amountOfDaysBeforePayingAccruedInterest == 0 && lastMonthAccruedInterest != 0) {
            amountOfDaysBeforePayingAccruedInterest = 30L;
            accruedInterest = lastMonthAccruedInterest;
            balance -= accruedInterest;
        }

        accruedInterest -= balance * monthlyInterestInPercent;
    }
}
