package escapeRoom.Service.InputService;

import java.time.LocalDate;
import java.util.Scanner;

public class InputService {
    private final Scanner input;

    public InputService(Scanner input) {
        this.input = input;
    }

    public String readString(String prompt) {
        System.out.print(prompt);
        return input.nextLine();
    }

    public LocalDate readDate(String prompt, String pattern){
        return null;
    }

    public boolean readBoolean(String prompt){
        return false;
    }

    public int readInt(String prompt){
        while(true){
            try{
                System.out.print(prompt);
                String result = input.nextLine().trim();
                return Integer.parseInt(result);
            } catch (NumberFormatException e){
                System.out.println("Error! Please insert a valid number.");
            }
        }
    }
}
