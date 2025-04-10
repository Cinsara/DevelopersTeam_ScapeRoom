package escapeRoom.Controller.GameController.ControllerHelpers;

import escapeRoom.Model.GameArea.GameBuilder.Game;
import escapeRoom.Service.InputService.BackToSecondaryMenuException;
import escapeRoom.Service.InputService.InputCollector;
import escapeRoom.Service.InputService.InputService;
import escapeRoom.Service.InputService.InputServiceManager;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class GameControllerHelpers {
    static int chooseGamesClassification(String bookedOrAvailable) throws BackToSecondaryMenuException {
        InputService inputService = InputServiceManager.getInputService();
        String menuBasis = " ----\n Do you want to see \n ---- \n";
        StringBuilder listOptions = new StringBuilder();
        listOptions.append("1. All the ").append(bookedOrAvailable).append(" games\n").append("2. Only the ").append(bookedOrAvailable).append(" games on a given date\n").append("3. Only the ").append(bookedOrAvailable).append(" games in a given room.\n").append("4. Take me back!");
        int input;
        do {input = inputService.readInt(menuBasis + listOptions);} while(!new HashSet<>(List.of(1, 2, 3, 4)).contains(input));
        return input;
    }

    static List<Game> getGamesToDisplay(int choice, Supplier<List<Game>> allGamesSupplier, Function<LocalDate,List<Game>> withDate , Function<Integer,List<Game>> withRoom, InputCollector inputCollector) throws BackToSecondaryMenuException, SQLException {
        return switch (choice){
            case 1:
                yield allGamesSupplier.get();
            case 2:
                yield withDate.apply(inputCollector.getDate());
            case 3:
                yield withRoom.apply(inputCollector.getRoom().getId());
            default:
                yield new ArrayList<>();
        };
    }
    static boolean isGameList(Object object){
        return object instanceof List<?> list && list.stream().allMatch(e -> e instanceof Game);
    }
    static void expressFailure(){
        System.out.println("Something went wrong. Let's start again");
    }
}
