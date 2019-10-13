package ir.farzadshami.quran.helpers;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.File;


public class Downloader {

    public static void start(String name, String url, Context context) {
        File path = new File(Environment.DIRECTORY_DOWNLOADS, "/QuranSounds");
        if (!path.exists())
            path.mkdirs();
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("دانلود");
        request.setDescription("دریافت فایل");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(path.getPath(), name + ".jpg");
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }


}
