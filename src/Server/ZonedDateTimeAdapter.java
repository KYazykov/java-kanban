package Server;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * адаптер ZonedDateTime
 */
public class ZonedDateTimeAdapter extends TypeAdapter<ZonedDateTime> {

    private static final DateTimeFormatter formatterReaderWriter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
    @Override
    public void write(JsonWriter jsonWriter, ZonedDateTime zonedDateTime) throws IOException {
        if(zonedDateTime == null){
            jsonWriter.value("Не задана");
            return;
        }
        jsonWriter.value(zonedDateTime.format(formatterReaderWriter));
    }

    @Override
    public ZonedDateTime read(JsonReader jsonReader) throws IOException {
        String curString = jsonReader.nextString();
        if(curString.equals("Не задана")){
            return null;
        }
        return ZonedDateTime.parse(curString, formatterReaderWriter);
    }
}
