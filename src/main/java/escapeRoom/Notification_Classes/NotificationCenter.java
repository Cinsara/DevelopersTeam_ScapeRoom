package escapeRoom.Notification_Classes;

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

    public void notifyUsers(Notifications notification){
        for(Observer observerUser : observersUsers){
            observerUser.update(notification);
        }
    }

    public void setRoomStatus(String roomName){
        Notifications notification = new Notifications("There is a new room available - %s".formatted(roomName));
        notifyUsers(notification);
        //Aquí debería coger directamente la room creada, el nombre.
    }
}
