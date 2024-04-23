package ru.isaev.User;

import ru.isaev.BankAccounts.BankAccountType;
import ru.isaev.BankService.BankService;
import lombok.Getter;

@Getter
public class User {
    private BankService _bank;
    public String name;
    public String surname;
    public String address;
    public String passport;
    public UserStatus status;

    public User(String name, String surname, String address, String passport) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.passport = passport;

        if (address == null && passport == null) status = UserStatus.NoAddressAndPassport;
        if (address == null && passport != null) status = UserStatus.NoAddress;
        if (address != null && passport == null) status = UserStatus.NoPassport;
        if (address != null && passport != null) status = UserStatus.Complete;
    }

    public void addBank(BankService bank) {
        _bank = bank;
    }

    public void createBankAccount(BankAccountType typeOfAccount, Double initialBalance) {
        _bank.createBankAccount(this, typeOfAccount, initialBalance);
    }

    public void updatePersonalInfo(String address, String passport) {
        _bank.updatePersonalInfo(this, address, passport);
    }

    public void transferMoneyToOtherAccount(Long senderBankId, Long senderAccountId, Long receiverBankId, Long receiverAccountId, Double amountOfMoney) {
        _bank.transferMoneyBetweenAccounts(senderBankId, senderAccountId, receiverBankId, receiverAccountId, amountOfMoney);
    }

    public void removeMoneyFromAccount(Long accountId, Double amountOfMoney) {
        _bank.removeMoneyFromAccount(accountId, amountOfMoney);
    }

    public void addMoneyToAccount(Long accountId, Double amountOfMoney) {
        _bank.addMoneyToAccount(accountId, amountOfMoney);
    }

    public void cancelTransaction(Long accountId, Long transactionId) {
        _bank.CancelTransaction(accountId, transactionId);
    }

    public Double seeBalanceInTheFuture(Long accountId, Integer amountOfDays) {
        return _bank.calculateBalanceInTheFuture(accountId, amountOfDays);
    }
}
