package escapeRoom.Service.OutPutService;

import escapeRoom.Model.AssetsArea.RewardBuilder.Reward;
import escapeRoom.Model.GameArea.CluePropFactory.Clue;
import escapeRoom.Model.GameArea.CluePropFactory.Prop;
import escapeRoom.Model.PeopleArea.User;

import java.lang.reflect.*;
import java.util.*;

public class TablePrinter {

    private static final int DEFAULT_COLUMN_WIDTH = 20;
    private static final int INT_PADDING = 2;

    public static <T> StringBuilder buildTable(List<T> list) {
        StringBuilder sb = new StringBuilder();

        if (list == null || list.isEmpty()) {
            sb.append("No data.\n");
            return sb;
        }

        Class<?> clazz = list.get(0).getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field f : fields) {
            f.setAccessible(true);
        }

        // Determine dynamic column widths
        Map<Field, Integer> columnWidths = calculateColumnWidths(fields, list);

        // Header
        for (Field field : fields) {
            int width = columnWidths.get(field);
            sb.append(String.format("%-" + width + "s", field.getName()));
        }
        sb.append("\n");

        // Separator
        for (Field field : fields) {
            int width = columnWidths.get(field);
            sb.append("-".repeat(width));
        }
        sb.append("\n");

        // Rows
        for (T item : list) {
            List<List<String>> rowChunks = new ArrayList<>();
            int maxLines = 1;

            for (Field field : fields) {
                try {
                    Object value = field.get(item);
                    List<String> lines = formatValue(value);
                    rowChunks.add(lines);
                    maxLines = Math.max(maxLines, lines.size());
                } catch (IllegalAccessException e) {
                    rowChunks.add(List.of("ERROR"));
                }
            }

            for (int line = 0; line < maxLines; line++) {
                for (int col = 0; col < fields.length; col++) {
                    Field field = fields[col];
                    List<String> cellLines = rowChunks.get(col);
                    String cell = (line < cellLines.size()) ? cellLines.get(line) : "";
                    int width = columnWidths.get(field);
                    sb.append(String.format("%-" + width + "s", cell));
                }
                sb.append("\n");
            }
        }

        return sb;
    }

    private static List<String> formatValue(Object value) {
        if (value == null) return List.of("null");

        if (value instanceof List<?> listVal) {
            List<String> result = new ArrayList<>();
            for (Object elem : listVal) {
                try {
                    if(elem instanceof User) result.add(((User) elem).getName() + " " + ((User) elem).getLastname()+"(ID:"+((User) elem).getId()+")");
                    else if(elem instanceof Clue) result.add(((Clue) elem).getTypeName());
                    else if (elem instanceof Prop) result.add(((Prop) elem).getTypeName());
                    else if (elem instanceof Reward) result.add("ID recipient: " + (((Reward) elem).getUser_id()));
                    else if (elem instanceof Integer) result.add(elem.toString());
                } catch (Exception e) {
                    result.add("<?>");
                }
            }
            return result;
        } else {
            return List.of(value.toString());
        }
    }

    private static <T> Map<Field, Integer> calculateColumnWidths(Field[] fields, List<T> list) {
        Map<Field, Integer> widths = new HashMap<>();

        for (Field field : fields) {
            int maxContentLength = field.getName().length();

            boolean isIntField = field.getType().equals(int.class) || field.getType().equals(Integer.class);

            if (isIntField) {
                for (T item : list) {
                    try {
                        Object val = field.get(item);
                        if (val != null) {
                            maxContentLength = Math.max(maxContentLength, val.toString().length());
                        }
                    } catch (IllegalAccessException ignored) {}
                }
                widths.put(field, maxContentLength + INT_PADDING);
            } else {
                widths.put(field, DEFAULT_COLUMN_WIDTH);
            }
        }

        return widths;
    }
}
