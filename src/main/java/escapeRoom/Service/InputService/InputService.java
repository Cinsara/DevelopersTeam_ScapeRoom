package escapeRoom.Service.InputService;

import java.time.LocalDate;

public interface InputService {
    String readString(String prompt);
    LocalDate readDate(String prompt, String pattern);
    boolean readBoolean(String prompt);
    int readInt(String prompt);
}
