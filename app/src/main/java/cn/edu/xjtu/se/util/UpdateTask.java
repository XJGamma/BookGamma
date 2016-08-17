package cn.edu.xjtu.se.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jingkai Tang on 8/17/16.
 */
public class UpdateTask extends AsyncTask<String, Void, String> {
    public static final String TAG = UpdateTask.class.getName();
    public static final String UPDATE_URL = "https://api.github.com/repos/XJGamma/BookGamma/releases/latest";

    private Context context;

    public UpdateTask(Context context) {
        this.context = context;
        Toast.makeText(context, "Checking Update...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String doInBackground(String... uri) {
        String result = null;
        OkHttpClient ohc = new OkHttpClient();
        final Request request = new Request.Builder().url(uri[0]).build();
        Call call = ohc.newCall(request);
        try {
            Response response = call.execute();
            if (response.isSuccessful()) {
                Log.i(TAG, "Request Success!");
                result = response.body().string();
            } else {
                Log.e(TAG, "Request Failed!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG, "No Response!");
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            JSONObject verJSON = new JSONObject(result);
            String latestVersion = verJSON.getString("tag_name");

            String version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            Log.i(TAG, "Latest Version: " + latestVersion);
            Log.i(TAG, "Version: " + version);

            if (isUpdated(latestVersion, version)) {
                Toast.makeText(context, "Congratulations! You are updated!", Toast.LENGTH_SHORT).show();
            } else {
                final String downloadUrl = verJSON.getJSONArray("assets").getJSONObject(0).getString("browser_download_url");
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("New Version " + latestVersion);
                builder.setMessage("Download?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(downloadUrl));
                        browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(browserIntent);
                    }
                });
                builder.setNegativeButton("Later", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Note to remind later
                    }
                });
                builder.create().show();
            }
        } catch (JSONException e) {
            Log.e(TAG, "Checking Update Error!");
            e.printStackTrace();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean isUpdated(String latest, String now) {
        int[] lv = new int[3];
        int[] nv = new int[3];

        Pattern pattern = Pattern.compile("v(\\d+)\\.(\\d+)\\.(\\d+)");

        Matcher matcher = pattern.matcher(latest);
        if (matcher.find()) {
            for (int i = 0; i < 3; i++) {
                lv[i] = Integer.parseInt(matcher.group(i + 1));
            }
        }

        matcher = pattern.matcher(now);
        if (matcher.find()) {
            for (int i = 0; i < 3; i++) {
                nv[i] = Integer.parseInt(matcher.group(i + 1));
            }
        }

        boolean result = true;

        for (int i = 0; i < 3; i++) {
            if (lv[i] > nv[i]) {
                result = false;
                break;
            }
        }

        return result;
    }

    public void update() {
        this.execute(UPDATE_URL);
    }
}
