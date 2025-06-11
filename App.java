import java.util.Scanner;

public class App {
  static Scanner scanner = new Scanner(System.in);
  static Database db = new Database();
  static int accountId;
  static double balance = 0;
  static boolean isLogin = false;

  public static void main(String[] args) {
    boolean isRunning = true;

    while (isRunning) {
      if (isLogin) {
        System.out.println("---------------");
        System.out.println("  SHELL BANK");
        System.out.println("---------------");
        System.out.println("1. Show Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Logout");
        System.out.println("---------------");

        System.out.print("Enter your choice : ");
        int choice = scanner.nextInt();

        switch (choice) {
          case 1 -> showBalance();
          case 2 -> deposit(accountId);
          case 3 -> withdraw(accountId);
          case 4 -> logout();
          default -> System.out.println("Invalid Choice!");
        }
      } else {
        System.out.println("---------------");
        System.out.println("  SHELL BANK");
        System.out.println("---------------");
        System.out.println("1. Login");
        System.out.println("2. SignUp");
        System.out.println("3. Exit");
        System.out.println("---------------");

        System.out.print("Enter your choice : ");
        int choice = scanner.nextInt();

        switch (choice) {
          case 1 -> login();
          case 2 -> signUp();
          case 3 -> isRunning = false;
          default -> System.out.println("Invalid Choice!");
        }
      }
    }

    System.out.println("Thank You! Good Bye!");

    scanner.close();
    db.close();
  }

  static void signUp() {
    System.out.print("Enter account name : ");
    String name = scanner.nextLine();

    scanner.nextLine();

    System.out.print("Enter account password : ");
    String pass = scanner.nextLine();

    db.signUp(name, pass);
  }

  static void login() {
    System.out.print("Enter account id : ");
    int id = scanner.nextInt();

    scanner.nextLine();

    System.out.print("Enter account password : ");
    String pass = scanner.nextLine();

    int result = db.login(id, pass);

    handleIsLogin(result, id);
  }

  static void handleIsLogin(int result, int id) {
    if (result == 0) {
      isLogin = true;
      accountId = id;
      balance = db.getBalance(accountId);
    } else {
      isLogin = false;
      accountId = 0;
      balance = 0;
    }
  }

  static void showBalance() {
    System.out.printf("Your balance is : $%.2f\n\n", balance);
  }

  static void deposit(int accountId) {
    System.out.print("Enter an amount to deposit : ");
    double amount = scanner.nextDouble();

    if (amount < 0) {
      System.out.println("Amount can't be negative!\n");
    } else {
      double newBalance = balance + amount;
      db.updateBalance(accountId, newBalance);
      balance = db.getBalance(accountId);
      System.out.printf("Your balance is $%.2f\n\n", balance);
    }
  }

  static void withdraw(int accountId) {
    System.out.print("Enter an amount to withdraw : ");
    double amount = scanner.nextDouble();

    if (amount < 0) {
      System.out.println("Amount can't be negative!\n");
    } else if (amount > balance) {
      System.out.println("Insufficient Funds!\n");
    } else {
      double newBalance = balance - amount;
      db.updateBalance(accountId, newBalance);
      balance = db.getBalance(accountId);
      System.out.printf("Your balance is $%.2f\n\n", balance);
    }
  }

  static void logout() {
    isLogin = false;
    balance = 0;
    accountId = 0;
    System.out.println("Logout success!\n");
  }
}
