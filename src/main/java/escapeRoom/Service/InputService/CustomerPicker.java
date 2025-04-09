package escapeRoom.Service.InputService;
import escapeRoom.Model.PeopleArea.User;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.List;

public class CustomerPicker {
    static final LevenshteinDistance DISTANCE = new LevenshteinDistance(30);
    static List<User> pickUsersByName(List<User> initialList, String contrastValue){
        return initialList.stream()
                .filter(user -> DISTANCE.apply(user.getName() + user.getLastname(),contrastValue.replace(" ",""))<5)
                .toList();
    }

}
