package Tools;

import java.util.Scanner;

public class InputGetter {

    private Scanner scanner;

    public String takeInputFromUser() {
        String userInput;
        initializeScanner();

        while(scanner.hasNext()) {
            userInput = scanner.nextLine();

            if(userInput != null) {
                return userInput;
            }
        }
        throw new IllegalArgumentException();
    }

    private void initializeScanner() {
        scanner = new Scanner(System.in);
    }
}
