package Lab1.DomainEntities.BankAccounts;

import Lab1.DomainEntities.Transactions.BaseTransaction;
import Lab1.DomainEntities.User.BankAccountStatus;
import Lab1.DomainServices.Utilities.CustomTuple;

import java.util.Stack;

abstract public class BaseBankAccount {
    protected Double balance;

    public Double commission;

    public Double monthlyInterestInPercent;

    protected Double accruedInterest = 0.0;

    protected Double lastMonthAccruedInterest = 0.;

    protected Long amountOfDaysBeforePayingAccruedInterest = 0L;

    public Long userId;

    public Double limitForUntrustworthyAccounts;

    public Double creditLimit;

    public BankAccountType type;

    public BankAccountStatus status;

    public Stack<CustomTuple<Long, BaseTransaction>> transactionsHistory = new Stack<>();

    public Double getBalance() { return balance; }

    abstract public void setBalance(Double newBalance);

    abstract public void writeOffCommission();

    abstract public void unWriteOffCommission();

    public void getInterestPayed() {
        Double dailyInterestInPercent = monthlyInterestInPercent / 30;
        accruedInterest += balance * dailyInterestInPercent / 100;
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
