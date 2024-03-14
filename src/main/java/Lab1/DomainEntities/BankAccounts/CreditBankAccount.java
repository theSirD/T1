package Lab1.DomainEntities.BankAccounts;

import Lab1.DomainEntities.User.BankAccountStatus;

public class CreditBankAccount extends BaseBankAccount {
    private Boolean _commissionWasWrittenOff;

    public CreditBankAccount(Double limitForUntrustworthyAccounts, BankAccountStatus status, Long userId, Double initialBalance, Double creditLimit, Double commissionForNegativeBalance) {
        this.creditLimit = creditLimit;
        this.commission = commissionForNegativeBalance;
        monthlyInterestInPercent = 0.0;
        balance = initialBalance;
        this.userId = userId;
        type = BankAccountType.Credit;
        this.status = status;
        this.limitForUntrustworthyAccounts = limitForUntrustworthyAccounts;
    }

    @Override
    public void setBalance(Double newBalance) {
        balance = newBalance;
    }

    @Override
    public void writeOffCommission() {
        if (balance < 0) {
            balance -= commission;
            _commissionWasWrittenOff = true;
        }

    }

    @Override
    public void unWriteOffCommission() {
        if (_commissionWasWrittenOff)
            balance += commission;
    }
}
