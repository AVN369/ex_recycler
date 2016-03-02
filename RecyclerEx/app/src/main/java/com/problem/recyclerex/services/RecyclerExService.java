package com.problem.recyclerex.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RecyclerExService extends IntentService {
    private static final String ACTION_DOWNLOAD = "com.problem.recyclerex.services.action.DOWNLOAD";
    private static final String PARAM_URL = "com.problem.recyclerex.services.extra.PARAM_URL";
    private static final String EXTRA_PARAM2 = "com.problem.recyclerex.services.extra.PARAM2";

    public RecyclerExService() {
        super("RecyclerExService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionDownload(Context context, String url, int parse) {
        Intent intent = new Intent(context, RecyclerExService.class);
        intent.setAction(ACTION_DOWNLOAD);
        intent.putExtra(PARAM_URL, url);
        intent.putExtra(EXTRA_PARAM2, parse);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_DOWNLOAD.equals(action)) {
                final String url = intent.getStringExtra(PARAM_URL);
                final int parse = intent.getIntExtra(EXTRA_PARAM2, -1);
                String response = handleActionDownload(url);
                if(response == null){

                }else{

                }
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private String handleActionDownload(String urlString) {
        //This should be the implementation to get the data from the server,
        //but since url is not availble , we are going to use a local file
        HttpURLConnection mHttpsURLConnection;
        try {
            URL url = new URL(urlString);
            mHttpsURLConnection = (HttpURLConnection) url.openConnection();

            mHttpsURLConnection.setReadTimeout(7000);
            mHttpsURLConnection.setConnectTimeout(7000);
            mHttpsURLConnection.setRequestMethod("GET");
            mHttpsURLConnection.setDoOutput(true);

            BufferedReader br = new BufferedReader(new InputStreamReader(mHttpsURLConnection.getInputStream()));
            String line = "";
            StringBuilder responseOutput = new StringBuilder();
            while((line = br.readLine()) != null ) {
                responseOutput.append(line);
            }
            br.close();

            return responseOutput.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
