package escapeRoom.model.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationCenter {
    private final List<Observer> observersUsers = new ArrayList<>();

    public void addObserver(Observer observerUser){
        observersUsers.add(observerUser);
        System.out.println("The user " + observerUser.toString() + " has been subscribed");
    }

    public void removeObserver(Observer observerUser) {
        observersUsers.remove(observerUser);
        System.out.println("The user " + observerUser.toString() + " has been unsubscribed");
    }

    public void notifyUsers(Notification notification){
        for(Observer observerUser : observersUsers){
            observerUser.update(notification);
        }
    }
}
