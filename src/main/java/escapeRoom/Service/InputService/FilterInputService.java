package escapeRoom.Service.InputService;

import java.time.LocalDate;
import java.util.function.BiFunction;
import java.util.function.Function;

public class FilterInputService implements InputService {
    private RealInputService realInputService;
    protected FilterInputService(){
        this.realInputService = new RealInputService();
    }

    <T> Object filterInputService(String prompt, Function<String, T> readSomething){
        try{
            readSomething.apply(prompt);
        }
    }

    <T> Object filterInputService(String prompt, String pattern, BiFunction<String,String,T> readSomething){
        try{
            readSomething.apply(prompt,pattern);
        }
    }
    @Override
    public String readString(String prompt) {
        return (String) filterInputService(prompt,(item)->realInputService.readString(item));
    }

    @Override
    public LocalDate readDate(String prompt, String pattern) {
        return (LocalDate) filterInputService(prompt,pattern,(item1,item2)->realInputService.readDate(item1,item2));
    }

    @Override
    public boolean readBoolean(String prompt) {
        return (boolean) filterInputService(prompt,(item)->realInputService.readBoolean(item));
    }

    @Override
    public int readInt(String prompt) {
        return (int) filterInputService(prompt,(item)->realInputService.readInt(item));
    }
}
