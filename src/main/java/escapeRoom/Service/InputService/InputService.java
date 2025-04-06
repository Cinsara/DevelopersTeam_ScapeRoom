package escapeRoom.Service.InputService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputService {
    private static final Scanner SHAREDSCANNER = new Scanner(System.in);
    private final Scanner input;

    protected InputService() {
        this.input = SHAREDSCANNER;
    }


    public String readString(String prompt) {
        System.out.println(prompt);
        return SHAREDSCANNER.nextLine();
    }

    public LocalDate readDate(String prompt, String pattern){
        while(true){
            System.out.println(prompt);
            String result = SHAREDSCANNER.nextLine();
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

    public boolean readBoolean(String prompt){
        System.out.println(prompt);
        String result = SHAREDSCANNER.nextLine().toLowerCase();
        while (!result.equals("yes") && !result.equals("no")) {
            System.out.println("Please, write 'yes' or 'no':");
            result = SHAREDSCANNER.nextLine().toLowerCase();
        }
        return result.equals("yes");
    }

    public int readInt(String prompt){
        while(true){
            try{
                System.out.println(prompt);
                String result = SHAREDSCANNER.nextLine().trim();
                return Integer.parseInt(result);
            } catch (NumberFormatException e){
                System.out.println("Error! Please insert a valid number.");
            }
        }
    }
}
