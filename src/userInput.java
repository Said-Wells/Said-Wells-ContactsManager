import java.util.Scanner;

public class userInput {
    private Scanner scanner;

    public userInput() {
        this.scanner = new Scanner(System.in);
    }

    public String getString() {
        return this.scanner.nextLine();
    }

    public int getNumber() {
        return this.scanner.nextInt();
    }

}




