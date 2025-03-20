package escapeRoom.service;


public interface CrudeService {
    static class UpdateOptions {
       final private String key;
       final private Class<?> keyClass;
       final private Object value;

        public UpdateOptions(String key, Class<?> valueClass, Object value) {
            this.key = key;
            this.keyClass = valueClass;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public Class<?> getKeyClass() {
            return keyClass;
        }

        public Object getValue() {
            return value;
        }
    }

    String getTableName();

    void create();
    void modify(int id,UpdateOptions updateOptions);
    void delete(int id);
    void read (int id);
}
