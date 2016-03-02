package com.problem.recyclerex.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.problem.recyclerex.database.ImageItemModel;
import com.problem.recyclerex.database.ImageItemsParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RecyclerExService extends IntentService {
    private static final String ACTION_DOWNLOAD = "com.problem.recyclerex.services.action.DOWNLOAD";
    private static final String PARAM_URL = "com.problem.recyclerex.services.extra.PARAM_URL";
    public static final String DATA_FETCH_ACTION_STRING = "com.problem.recyclerex.services.extra.DATA_FETCH_ACTION";

    public RecyclerExService() {
        super("RecyclerExService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionDownload(Context context, String url) {
        Intent intent = new Intent(context, RecyclerExService.class);
        intent.setAction(ACTION_DOWNLOAD);
        intent.putExtra(PARAM_URL, url);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_DOWNLOAD.equals(action)) {
                final String url = intent.getStringExtra(PARAM_URL);
                String response = handleActionDownload(url);
                Intent receiverIntent = new Intent();
                receiverIntent.setAction(DATA_FETCH_ACTION_STRING);
                ArrayList<ImageItemModel> imageItemModels = null;
                boolean status = false;
                if(response == null){
                    //TODO: Fire FAILURE
                    status = false;
                }else{
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.optJSONArray("data");
                        imageItemModels = ImageItemsParser.getImageItemModels(jsonArray);
                        if(imageItemModels != null && imageItemModels.size() > 0) {
                            status = true;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        status = false;
                        //TODO: Fire FAILURE
                    }
                }
                receiverIntent.putParcelableArrayListExtra("data", imageItemModels);
                receiverIntent.putExtra("status", status);
                sendBroadcast(receiverIntent);
            }
        }
    }

    /**
     * Handle action Download in the provided background thread with the provided
     * parameters.
     */
    private String handleActionDownload(String urlString) {
        //This should be the implementation to get the data from the server,
        //but since url is not availble , we are going to use a local file
        HttpURLConnection mHttpURLConnection;
        try {
            URL url = new URL(urlString);
            mHttpURLConnection = (HttpURLConnection) url.openConnection();

            mHttpURLConnection.setReadTimeout(7000);
            mHttpURLConnection.setConnectTimeout(7000);
            mHttpURLConnection.setRequestMethod("GET");
            mHttpURLConnection.setDoOutput(false);

            BufferedReader br = new BufferedReader(new InputStreamReader(mHttpURLConnection.getInputStream()));
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
