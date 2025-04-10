package escapeRoom.Service.InputService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputService {
    private final Scanner input;

    protected InputService() {
        this.input =  new Scanner(System.in);
    }

    private String filter(String result) throws BackToSecondaryMenuException {
        if (result.equalsIgnoreCase("exit")) throw new BackToSecondaryMenuException();
        return result;
    }

    public String readString(String prompt) throws BackToSecondaryMenuException {
        System.out.println(prompt);
        return filter(input.nextLine());
    }

    public LocalDate readDate(String prompt, String pattern) throws BackToSecondaryMenuException {
        while(true){
            System.out.println(prompt);
            String result = filter(input.nextLine());
            if(result.isEmpty()) {
                System.out.println("Date cannot be empty. Please provide a date with the following pattern: " + pattern);
            }
            try {
                return LocalDate.parse(result, DateTimeFormatter.ofPattern(pattern));
            } catch(DateTimeParseException e) {
                System.out.println("Invalid date format. Please use '" + pattern + "' (e.g. 2000 12 31)");
            }
        }
    }

    public boolean readBoolean(String prompt) throws BackToSecondaryMenuException {
        System.out.println(prompt);
        String result = filter(input.nextLine().toLowerCase());
        while (!result.equals("yes") && !result.equals("no")) {
            System.out.println("Please, write 'yes' or 'no':");
            result = input.nextLine().toLowerCase();
        }
        return result.equals("yes");
    }

    public int readInt(String prompt) throws BackToSecondaryMenuException{
        while(true){
            try{
                System.out.println(prompt);
                String result = filter(input.nextLine().trim());
                return Integer.parseInt(result);
            } catch (NumberFormatException e){
                System.out.println("Error! Please insert a valid number.");
            }
        }
    }
}
