package lt.vcs.notes.helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormatHelper {

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String convertToText(LocalDateTime datetime){

        String text = datetime.format(formatter);

        return text;
    }

    public static LocalDateTime convertToDatetime(String text){

        LocalDateTime datetime = LocalDateTime.parse(text, formatter);

        return datetime;
    }
}
