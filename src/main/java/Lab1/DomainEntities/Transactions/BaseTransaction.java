package Lab1.DomainEntities.Transactions;

abstract public class BaseTransaction {
    abstract public void execute();

    abstract public void undo();
}
