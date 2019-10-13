package ir.farzadshami.quran;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ir.farzadshami.quran.adapters.SurasAdapter;
import ir.farzadshami.quran.helpers.Cache;
import ir.farzadshami.quran.helpers.RecyclerItemClickListener;
import ir.farzadshami.quran.models.Sura;

public class SuraListActivity extends Fragment {
    private ArrayList<Sura> suras;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.activity_sura_list, container, false);
        final FragmentActivity c = getActivity();
        final RecyclerView recyclerView = view.findViewById(R.id.rvSuras);
        LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        recyclerView.setLayoutManager(layoutManager);
        suras = Cache.getSuraList("Suras");
        new Thread(new Runnable() {
            @Override
            public void run() {
                final SurasAdapter adapter = new SurasAdapter(c, suras);
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        }).start();

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(c, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent suraText = new Intent(c, SuraTextActivity.class);
                String suraPosition = String.valueOf(suras.get(position).getId() + 1);
                suraText.putExtra("suraPosition", suraPosition);
                startActivity(suraText);
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        }));
        return view;
    }
}
