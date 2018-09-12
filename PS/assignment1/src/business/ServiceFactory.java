package business;

public class ServiceFactory {

    Bank bank;
    ClientOperations clientOperations;
    AccountOperations accountOperations;
    EmployeeOperations employeeOperations;

    public ServiceFactory() {
        bank = new Bank();
        clientOperations = new ClientOperations();
        accountOperations = new AccountOperations();
        employeeOperations = new EmployeeOperations();
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public ClientOperations getClientOperations() {
        return clientOperations;
    }

    public void setClientOperations(ClientOperations clientOperations) {
        this.clientOperations = clientOperations;
    }

    public AccountOperations getAccountOperations() {
        return accountOperations;
    }

    public void setAccountOperations(AccountOperations accountOperations) {
        this.accountOperations = accountOperations;
    }

    public EmployeeOperations getEmployeeOperations() {
        return employeeOperations;
    }

    public void setEmployeeOperations(EmployeeOperations employeeOperations) {
        this.employeeOperations = employeeOperations;
    }
}
