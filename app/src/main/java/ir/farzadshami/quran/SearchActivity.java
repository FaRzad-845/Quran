package ir.farzadshami.quran;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ir.farzadshami.quran.adapters.SearchAdapter;
import ir.farzadshami.quran.helpers.RecyclerItemClickListener;
import ir.farzadshami.quran.helpers.SqliteDbHelper;
import ir.farzadshami.quran.models.DbModel;

public class SearchActivity extends Fragment {
    private SearchAdapter adapter;
    private ArrayList<DbModel> searchResult;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_search, container, false);
        final FragmentActivity c = getActivity();
        final EditText searchEditTxt = view.findViewById(R.id.search_et);
        final RecyclerView recyclerView = view.findViewById(R.id.rvSearch);

        InputMethodManager imgr = (InputMethodManager) c.getSystemService(Context.INPUT_METHOD_SERVICE);
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        searchEditTxt.requestFocus();

        LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        recyclerView.setLayoutManager(layoutManager);

        new Thread(new Runnable() {
            @Override
            public void run() {
                adapter = new SearchAdapter(c);
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        }).start();

        searchEditTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    SqliteDbHelper dbHelper = SqliteDbHelper.getInstance(c);
                    dbHelper.openDatabase();
                    searchResult = dbHelper.getSearchDetails(charSequence.toString());
                    adapter.updateList(searchResult);
                }
                if (charSequence.length() == 0) adapter.updateList(new ArrayList<DbModel>());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(c, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent suraText = new Intent(c, SuraTextActivity.class);
                String versePosition = String.valueOf(searchResult.get(position).getVerseId() - 1);
                String suraPosition = String.valueOf(searchResult.get(position).getSuraId());
                Toast.makeText(c, String.valueOf(searchResult.get(position).getVerseId()), Toast.LENGTH_SHORT).show();
                suraText.putExtra("suraPosition", suraPosition);
                suraText.putExtra("versePosition", versePosition);
                startActivity(suraText);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));

        return view;
    }
}
