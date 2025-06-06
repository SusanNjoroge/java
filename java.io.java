import java.io.Console;

public class LoginSystem {
    public static void main(String[] args) {
        String correctUsername = "admin";
        String correctPassword = "pass123";
        int attempts = 3;

        Console console = System.console(); // To read password as '*'

        if (console == null) {
            System.out.println("Console is not available. Run from terminal.");
            return;
        }

        while (attempts > 0) {
            System.out.print("Enter username: ");
            String username = console.readLine();

            char[] passwordArray = console.readPassword("Enter password: ");
            String password = new String(passwordArray);

            if (username.equals(correctUsername) && password.equals(correctPassword)) {
                System.out.println("Login successful!");
                break;
            } else {
                attempts--;
                System.out.println("Incorrect credentials. Tries left: " + attempts);
            }
        }

        if (attempts == 0) {
            System.out.println("Access denied.");
        }
    }
}
