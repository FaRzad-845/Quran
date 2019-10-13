package ir.farzadshami.quran.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

import ir.farzadshami.quran.R;
import ir.farzadshami.quran.helpers.Cache;
import ir.farzadshami.quran.models.Sura;

import static java.lang.String.format;

public class SurasAdapter extends RecyclerView.Adapter<SurasAdapter.ViewHolder> {

    private List<Sura> suras;
    private LayoutInflater mInflater;
    private Typeface arabicFont;
    private Typeface farsiFont;

    public SurasAdapter(Context context, List<Sura> suras) {
        this.mInflater = LayoutInflater.from(context);
        this.suras = suras;
        SharedPreferences settings = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        arabicFont = Cache.getFont(settings.getString("arabicFont", "Al Qalam Quran.ttf"), context);
        farsiFont = Cache.getFont(settings.getString("farsiFont", "BNazanin.ttf"), context);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View suraView = mInflater.inflate(R.layout.item_sura, parent, false);
        return new ViewHolder(suraView);
    }

    @Override
    public void onBindViewHolder(@NonNull SurasAdapter.ViewHolder holder, int position) {
        Sura sura = suras.get(position);
        TextView suraName = holder.suraNameTv;
        TextView nozolId = holder.nozolIdTv;
        TextView hezbIdTv = holder.hezbIdTv;
        TextView suraCountTv = holder.suraCountTv;
        TextView suraPlaceTv = holder.suraPlaceTv;
        TextView idTv = holder.idTv;
        suraName.setText(sura.getSuraName());
        suraName.setTypeface(arabicFont);
        nozolId.setText(persianNumbers(sura.getNozolId()));
        nozolId.setTypeface(farsiFont);
        hezbIdTv.setText(persianNumbers(sura.getHezbId()));
        hezbIdTv.setTypeface(farsiFont);
        suraCountTv.setText(persianNumbers(sura.getSuraCount()));
        suraCountTv.setTypeface(farsiFont);
        suraPlaceTv.setText(sura.getPlace() + " - ");
        suraPlaceTv.setTypeface(farsiFont);
        idTv.setText(persianNumbers(sura.getId() + 1));
        idTv.setTypeface(farsiFont);
    }

    @Override
    public int getItemCount() {
        return suras.size();
    }

    private String persianNumbers(int input) {
        return format(new Locale("ar"), "%d", input);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView suraNameTv;
        public TextView nozolIdTv;
        public TextView hezbIdTv;
        public TextView suraPlaceTv;
        public TextView suraCountTv;
        public TextView idTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            suraNameTv = itemView.findViewById(R.id.sura_name);
            nozolIdTv = itemView.findViewById(R.id.nozol_id);
            hezbIdTv = itemView.findViewById(R.id.hezb_id);
            suraPlaceTv = itemView.findViewById(R.id.sura_place);
            suraCountTv = itemView.findViewById(R.id.sura_count);
            idTv = itemView.findViewById(R.id.id);
        }
    }

}
