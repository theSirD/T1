import ru.isaev.BankAccounts.BankAccountType;
import ru.isaev.BankAccounts.BaseBankAccount;
import ru.isaev.User.User;
import ru.isaev.BankService.BankService;
import ru.isaev.CentralBankService.CentralBankService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class Tests {
    @Test
    public void removeMoneyFromAccount() {
        CentralBankService centralBank = new CentralBankService();
        BankService bank = new BankService(500.0, 5.0, 3., 60L, 100.0, 2.5, centralBank);
        centralBank.registerBank(bank);

        User user = new User("Daniel", "Isaev", "Saint-Petersburg", "123");
        bank.registerUser(user);

        user.createBankAccount(BankAccountType.Credit, 250.0);
        user.removeMoneyFromAccount(1L, 100.);

//        BaseBankAccount account =  bank.bankAccountsList.get(1L);

//        Assertions.assertEquals(150, account.getBalance());
    }

    @Test
    public void seeBalanceIn30Days() {
        CentralBankService centralBank = new CentralBankService();
        BankService bank = new BankService(500.0, 5.0, 3., 60L, 100.0, 2.5, centralBank);
        centralBank.registerBank(bank);

        User user = new User("Daniel", "Isaev", "Saint-Petersburg", "123");
        bank.registerUser(user);
        user.createBankAccount(BankAccountType.Debit, 250.0);
        Double balanceIn30Days = user.seeBalanceInTheFuture(1L, 30);

        Assertions.assertEquals(257.5, balanceIn30Days);
    }

    @Test
    public void transferMoneyBetweenAccountsOfDifferentBanks() {
        CentralBankService centralBank = new CentralBankService();
        BankService bank1 = new BankService(500.0, 5.0, 3., 60L, 100.0, 2.5, centralBank);
        BankService bank2 = new BankService(500.0, 5.0, 3., 60L, 100.0, 2.5, centralBank);
        User user1 = new User("Daniel", "Isaev", "Saint-Petersburg", "123");
        User user2 = new User("Geralt", "Gerom", "Toissant", "1234");

        bank1.registerUser(user1);
        bank2.registerUser(user2);
        centralBank.registerBank(bank1);
        centralBank.registerBank(bank2);

        user1.createBankAccount(BankAccountType.Debit, 250.0);
        user2.createBankAccount(BankAccountType.Debit, 250.0);

        user1.transferMoneyToOtherAccount(1L, 1L, 2L, 1L, 100.);


//        BaseBankAccount accountOfUser1 =  bank1.bankAccountsList.get(1L);
//        BaseBankAccount accountOfUser2 =  bank2.bankAccountsList.get(1L);

//        Boolean condition = accountOfUser1.getBalance() == 150. && accountOfUser2.getBalance() == 350;

//        Assertions.assertEquals(true, condition);
    }

    @Test
    public void cancelTransaction() {
        CentralBankService centralBank = new CentralBankService();
        BankService bank = new BankService(500.0, 5.0, 3., 60L, 100.0, 2.5, centralBank);
        centralBank.registerBank(bank);

        User user = new User("Daniel", "Isaev", "Saint-Petersburg", "123");
        bank.registerUser(user);

        user.createBankAccount(BankAccountType.Credit, 250.0);
        user.removeMoneyFromAccount(1L, 100.);
        user.addMoneyToAccount(1L, 300.);

        user.cancelTransaction(1L, 1L);

//        BaseBankAccount account =  bank.bankAccountsList.get(1L);

//        Assertions.assertEquals(550, account.getBalance());
    }
}