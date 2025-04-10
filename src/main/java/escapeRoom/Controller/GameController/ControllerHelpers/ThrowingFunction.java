package escapeRoom.Controller.GameController.ControllerHelpers;

@FunctionalInterface
public interface ThrowingFunction<T, R> {
    R apply(T t) throws Exception;
}