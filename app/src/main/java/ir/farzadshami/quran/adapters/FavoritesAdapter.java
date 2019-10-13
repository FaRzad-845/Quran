package ir.farzadshami.quran.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.farzadshami.quran.R;
import ir.farzadshami.quran.models.Favorite;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {
    private LayoutInflater layoutInflater;
    private List<Favorite> list;

    public FavoritesAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        this.list = Favorite.createFavoriteList(context);
    }

    @NonNull
    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View favoriteView = layoutInflater.inflate(R.layout.item_favorite, parent, false);
        return new ViewHolder(favoriteView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesAdapter.ViewHolder holder, int position) {
        Favorite favorite = list.get(position);
        TextView suraText = holder.suraNameTv;
        TextView arabicText = holder.arabicTextTv;
        TextView farsiText = holder.farsiTextTv;
        TextView verseId = holder.verseIdTv;

        suraText.setText(favorite.getSuraName());
        arabicText.setText(favorite.getArabicText());
        farsiText.setText(favorite.getFarsiText());
        verseId.setText(favorite.getVerseId());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView suraNameTv;
        public TextView arabicTextTv;
        public TextView farsiTextTv;
        public TextView verseIdTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            suraNameTv = itemView.findViewById(R.id.suraNameTv);
            arabicTextTv = itemView.findViewById(R.id.arabic_tv);
            farsiTextTv = itemView.findViewById(R.id.farsi_tv);
            verseIdTv = itemView.findViewById(R.id.suraVerseId);
        }
    }
}
