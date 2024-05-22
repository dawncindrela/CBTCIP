import java.util.*;
import java.io.*;

class BankAccount {
    private String accountNumber;
    private String accountHolder;
    private double balance;

    public BankAccount(String accountNumber, String accountHolder, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount);
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrew: " + amount);
        } else {
            System.out.println("Insufficient funds or invalid amount.");
        }
    }

    public void transfer(BankAccount toAccount, double amount) {
        if (amount > 0 && amount <= balance) {
            this.withdraw(amount);
            toAccount.deposit(amount);
            System.out.println("Transferred: " + amount + " to " + toAccount.getAccountNumber());
        } else {
            System.out.println("Insufficient funds or invalid amount.");
        }
    }

    @Override
    public String toString() {
        return "Account Number: " + accountNumber + ", Account Holder: " + accountHolder + ", Balance: " + balance;
    }
}

class BankY {
    private static Map<String, BankAccount> accounts = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        loadAccounts();

        while (true) {
            System.out.println("\nBankY System");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. View Accounts");
            System.out.println("6. Exit");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    createAccount(scanner);
                    break;
                case 2:
                    deposit(scanner);
                    break;
                case 3:
                    withdraw(scanner);
                    break;
                case 4:
                    transfer(scanner);
                    break;
                case 5:
                    viewAccounts();
                    break;
                case 6:
                    saveAccounts();
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void createAccount(Scanner scanner) {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.next();
        System.out.print("Enter account holder name: ");
        String accountHolder = scanner.next();
        System.out.print("Enter initial balance: ");
        double initialBalance = scanner.nextDouble();
        BankAccount account = new BankAccount(accountNumber, accountHolder, initialBalance);
        accounts.put(accountNumber, account);
        System.out.println("Account created successfully!");
    }

    private static void deposit(Scanner scanner) {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.next();
        System.out.print("Enter deposit amount: ");
        double amount = scanner.nextDouble();
        BankAccount account = accounts.get(accountNumber);
        if (account != null) {
            account.deposit(amount);
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void withdraw(Scanner scanner) {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.next();
        System.out.print("Enter withdrawal amount: ");
        double amount = scanner.nextDouble();
        BankAccount account = accounts.get(accountNumber);
        if (account != null) {
            account.withdraw(amount);
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void transfer(Scanner scanner) {
        System.out.print("Enter your account number: ");
        String fromAccountNumber = scanner.next();
        System.out.print("Enter recipient account number: ");
        String toAccountNumber = scanner.next();
        System.out.print("Enter transfer amount: ");
        double amount = scanner.nextDouble();
        BankAccount fromAccount = accounts.get(fromAccountNumber);
        BankAccount toAccount = accounts.get(toAccountNumber);
        if (fromAccount != null && toAccount != null) {
            fromAccount.transfer(toAccount, amount);
        } else {
            System.out.println("One or both accounts not found.");
        }
    }

    private static void viewAccounts() {
        for (BankAccount account : accounts.values()) {
            System.out.println(account);
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadAccounts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("accounts.dat"))) {
            accounts = (Map<String, BankAccount>) ois.readObject();
            System.out.println("Accounts loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("No saved accounts found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void saveAccounts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("accounts.dat"))) {
            oos.writeObject(accounts);
            System.out.println("Accounts saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
