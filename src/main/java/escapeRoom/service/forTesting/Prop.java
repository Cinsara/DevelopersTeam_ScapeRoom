package escapeRoom.service.forTesting;

import java.util.Objects;

public class Prop {
    private String type;
    private int value;
    private int room_id;
    private int id;

    public Prop(String type, int value, int room_id) {
        this.type = type;
        this.value = value;
        this.room_id = room_id;
    }

    public Prop( int id,String type, int value, int room_id) {
        this.type = type;
        this.value = value;
        this.room_id = room_id;
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Prop prop = (Prop) o;
        return value == prop.value && room_id == prop.room_id && id == prop.id && Objects.equals(type, prop.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value, room_id, id);
    }

}
