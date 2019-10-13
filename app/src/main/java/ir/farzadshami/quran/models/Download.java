package ir.farzadshami.quran.models;

import java.util.ArrayList;
import java.util.Locale;

import static java.lang.String.format;

public class Download {

    private String text;
    private Boolean checked;

    private Download(String text, Boolean checked) {
        this.text = text;
        this.checked = checked;
    }

    public static ArrayList<Download> createDownloadList() {
        ArrayList<Download> downloadList = new ArrayList<>();
        for (int index = 0; index < 30; index++)
            downloadList.add(new Download("جز " + persianNumbers(index + 1), false));
        return downloadList;
    }

    private static String persianNumbers(int input) {
        return format(new Locale("ar"), "%d", input);
    }

    public String getText() {
        return text;
    }

    public Boolean getChecked() {
        return checked;
    }

}
