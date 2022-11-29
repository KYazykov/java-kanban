package manager;

import task.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

public class CSVTaskFormatter {

    static String join(Object... words) {
        StringBuilder sb = new StringBuilder();

        if (words.length > 0) {
            sb.append(words[0]);
            for (int i = 1; i < words.length; i++) {
                sb.append(", ").append(words[i].toString());
            }
        }

        return sb.toString();
    }

    public static String toString(Task task) {
        return  task.getId() + ", " + task.getName() + ", " + task.getStatus() + ", " + task.getDescription() + ", " + task.getEpicID();
    }

    public Task fromString (String value) {

        return fromString(value);
    }

    public static String historyToString(HistoryManager manager) {
        return manager.getHistory().toString();

    }

    static List<Integer> historyFromString(String value) {

        List<Integer> history = new ArrayList<>();

        Pattern pat=Pattern.compile("[-]?[0-9]+(.[0-9]+)?");
        Matcher matcher=pat.matcher(value);
        while (matcher.find()) {
           history.add(Integer.parseInt(matcher.group()));
        };

        return history;
    }

    public String getHeader(){
        return "id, type, name, status, description, epic";
    }

}
