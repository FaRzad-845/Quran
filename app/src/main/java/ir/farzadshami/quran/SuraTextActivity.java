package ir.farzadshami.quran;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ir.farzadshami.quran.adapters.SuraTextAdapter;
import ir.farzadshami.quran.helpers.RecyclerItemClickListener;
import ir.farzadshami.quran.helpers.SqliteDbHelper;
import ir.farzadshami.quran.models.DbModel;

public class SuraTextActivity extends AppCompatActivity {
    private SuraTextAdapter adapter;
    private RecyclerView rv;
    private String position;
    private String versePosition;
    private int suraAyehPosition;
    private ArrayList<DbModel> suraText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sura_text);
        position = GetExtras(savedInstanceState, "suraPosition");
        versePosition = GetExtras(savedInstanceState, "versePosition");
        SqliteDbHelper dbHelper = SqliteDbHelper.getInstance(getApplicationContext());
        dbHelper.openDatabase();
        suraText = dbHelper.getDetails("SuraId", position);
        rv = findViewById(R.id.rvText);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SuraTextAdapter(this, suraText);
        rv.setAdapter(adapter);
        if (versePosition != null) {
            int vPosition = Integer.valueOf(versePosition);
            rv.smoothScrollToPosition(vPosition);
            suraAyehPosition = vPosition + 1;
        }
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                    suraAyehPosition = getCurrentAyehPosition();
                Toast.makeText(getApplicationContext(), String.valueOf(suraAyehPosition), Toast.LENGTH_SHORT).show();
            }
        });

        rv.addOnItemTouchListener(new RecyclerItemClickListener(SuraTextActivity.this, rv, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                /*SqliteDbHelper dbHelper = SqliteDbHelper.getInstance(getApplicationContext());
                dbHelper.openDatabase();
                String sqlQuery = "SuraId = " + position + " and VerseId";
                String ayehPosition = String.valueOf(suraAyehPosition);
                ArrayList<DbModel> favorite = dbHelper.getDetails(sqlQuery, ayehPosition);
                if (favorite.get(0).getFavorite())
                    Toast.makeText(getApplicationContext(), "remove from list", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "remove from list", Toast.LENGTH_SHORT).show();
                dbHelper.setDetails("UPDATE quran set Favorite = " + !favorite.get(0).getFavorite() + "WHERE = " + sqlQuery + " = " + ayehPosition);
            */
            }

            @Override
            public void onItemLongClick(View view, int position) {

                /*
                ** Copy To Clip Board
                *
                ClipboardManager cm = (ClipboardManager) SuraTextActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                DbModel sura = suraText.get(position);
                String textToClipBoard = "سوره " + "" + "آیه " + (position + 1) + "\n" + sura.getArabicText() + "\n" + sura.getFarsiText();
                ClipData clip = ClipData.newPlainText("آیه", textToClipBoard);
                cm.setPrimaryClip(clip);
                Toast.makeText(SuraTextActivity.this, "آیه موزد نظر شما در حافظه ذخیره شد.", Toast.LENGTH_SHORT).show();*/

                DbModel sura = suraText.get(position);
                String shareBody = "سوره " + "" + "آیه " + (position + 1) + "\n" + sura.getArabicText() + "\n" + sura.getFarsiText();
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "آیه");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "اشتراک گذاری"));
            }
        }));

    }

    private int getCurrentAyehPosition() {
        return ((LinearLayoutManager) rv.getLayoutManager())
                .findFirstVisibleItemPosition();
    }


    private String GetExtras(Bundle savedInstanceState, String value) {
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null)
                return null;
            return extras.getString(value);
        } else {
            return (String) savedInstanceState.getSerializable(value);
        }
    }


}
