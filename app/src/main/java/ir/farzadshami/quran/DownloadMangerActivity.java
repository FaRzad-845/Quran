package ir.farzadshami.quran;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ir.farzadshami.quran.adapters.DownloadsAdapter;
import ir.farzadshami.quran.helpers.Downloader;
import ir.farzadshami.quran.widget.Switch;

public class DownloadMangerActivity extends Fragment {
    private static final int PERMISSION_STORAGE_CODE = 1000;
    private FragmentActivity c;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_download_manger, container, false);
        c = getActivity();
        Switch voiceSwitch = view.findViewById(R.id.voice_switch);
        voiceSwitch.setSwitchToggleListener(new Switch.SwitchToggleListener() {
            @Override
            public void onSwitchToggle(Switch.SwitchToggleState switchToggleState) {
                Toast.makeText(c, String.format("Switch state %s", switchToggleState.name()), Toast.LENGTH_SHORT).show();
            }
        });

        final RecyclerView recyclerView = view.findViewById(R.id.download_rv);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(c, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        DownloadsAdapter adapter = new DownloadsAdapter(c);
        recyclerView.setAdapter(adapter);

        Button downloadBtn = view.findViewById(R.id.download_btn);

        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (c.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_STORAGE_CODE);
                    } else {
                        Downloader.start("1", "xxx.yyy", c);
                    }
                } else {
                    Downloader.start("1", "xxx.yyy", c);
                }
            }
        });

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_STORAGE_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Downloader.start("1", "xxx.yyy", c);
                } else {
                    Toast.makeText(c, "Permission denied ...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
