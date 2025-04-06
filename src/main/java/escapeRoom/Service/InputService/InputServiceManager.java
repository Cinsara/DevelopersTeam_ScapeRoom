package escapeRoom.Service.InputService;


public class InputServiceManager {
    static InputService inputService;
    static public InputService getInputService() {
        if (inputService == null){
            inputService = new InputService();
        }
        return inputService;
    }
}
