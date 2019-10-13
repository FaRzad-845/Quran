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
import ir.farzadshami.quran.models.DbModel;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private List<DbModel> list;

    public SearchAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View searchView = layoutInflater.inflate(R.layout.item_search, parent, false);
        return new ViewHolder(searchView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DbModel model = list.get(position);
        TextView text = holder.txt;
        text.setText(model.getFarsiText());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void updateList(List<DbModel> data) {
        this.list = data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.txt);
        }
    }
}
