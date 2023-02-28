import java.util.Scanner;


//This is our custom UserInput class that utilizes the Scanner class to record user input information.
public class userInput {
    private Scanner scanner;

    public userInput() {
        this.scanner = new Scanner(System.in);
    }

    public String getString() {
        return this.scanner.nextLine();
    }


}




