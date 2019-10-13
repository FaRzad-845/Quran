package ir.farzadshami.quran.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ir.farzadshami.quran.R;
import ir.farzadshami.quran.helpers.Cache;
import ir.farzadshami.quran.models.DbModel;

public class SuraTextAdapter extends RecyclerView.Adapter<SuraTextAdapter.ViewHolder> {

    private final int[] backgroundColors = {R.color.suraTextBackground, R.color.suraList};
    private final int[] movingLettersColors = {R.color.item_color_two, R.color.item_color_one};
    private ArrayList<DbModel> suraText;
    private LayoutInflater inflater;
    private Context context;


    public SuraTextAdapter(Context context, ArrayList<DbModel> sura) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.suraText = sura;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View suraView = inflater.inflate(R.layout.item_sura_text, parent, false);
        return new ViewHolder(suraView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DbModel sura = suraText.get(position);
        LinearLayout lr = holder.suraItemLr;
        TextView suraArabic = holder.suraArabicText;
        TextView suraFarsi = holder.suraFarsiText;
        int bgColor = ContextCompat.getColor(context, backgroundColors[position % 2]);
        SharedPreferences settings = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        if (settings.getBoolean("showTranslate", true))
            suraFarsi.setVisibility(View.VISIBLE);
        else
            suraFarsi.setVisibility(View.GONE);
        Typeface arabicFont = Cache.getFont(settings.getString("arabicFont", "Al Qalam Quran.ttf"), context);
        Typeface farsiFont = Cache.getFont(settings.getString("farsiFont", "BNazanin.ttf"), context);
        lr.setBackgroundColor(bgColor);
        suraArabic.setTypeface(arabicFont);
        suraFarsi.setTypeface(farsiFont);
        suraArabic.setTextSize(30);
        suraFarsi.setTextSize(14);
        suraArabic.setTextColor(context.getResources().getColor(R.color.suraText));
        suraArabic.setText(Html.fromHtml(makeColoredStringBuffer(sura.getArabicText(), settings.getString("movingLettersColor", "#FF0000"))));
        suraFarsi.setText(sura.getFarsiText());
    }

    private String makeColoredStringBuffer(String text, String color) {
        Pattern movingLetters = Pattern.compile("([^ء-ي])");
        StringBuffer sb = new StringBuffer(text.length());
        Matcher o = movingLetters.matcher(text);
        while (o.find())
            o.appendReplacement(sb, "<font color=\"" + color + "\">" + o.group(1) + "</font>");
        o.appendTail(sb);
        return sb.toString();
    }


    @Override
    public int getItemCount() {
        return suraText.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView suraArabicText;
        public TextView suraFarsiText;
        public LinearLayout suraItemLr;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            suraArabicText = itemView.findViewById(R.id.sura_arabic_tv);
            suraFarsiText = itemView.findViewById(R.id.sura_farsi_tv);
            suraItemLr = itemView.findViewById(R.id.sura_item_lr);
        }
    }
}
