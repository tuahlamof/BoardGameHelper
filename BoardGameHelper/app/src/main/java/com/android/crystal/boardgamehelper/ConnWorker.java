package com.android.crystal.boardgamehelper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by huiyu on 11/11/17.
 */

public class ConnWorker extends AsyncTask<String, Integer, String> {
    HttpURLConnection conn;
    public static String basicUrl = "http://10.0.2.2:3001/users/";
    public AsyncResponse delegate = null;
    DataOutputStream wr;
    InputStreamReader isr;
    private ArrayList returnArr;

    @Override
    protected String doInBackground(String... param) {
        try {
            if (param[0].equals("getHistory")) {
                URL url = new URL(basicUrl + param[1]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/json"); // dont use utf-8 dont know why
                conn.setRequestProperty("Accept", "application/json"); // accept json response from server
                conn.setReadTimeout(10000000);
                conn.setConnectTimeout(15000000);
                conn.setRequestMethod(param[2]);
                conn.setDoInput(true);

                InputStream responseStream = new BufferedInputStream(conn.getInputStream());
                BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(responseStream));
                String line = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = responseStreamReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                responseStreamReader.close();
                String response = stringBuilder.toString();
                returnArr = new ArrayList<>();
                JSONObject responseJson = new JSONObject(response);
                JSONArray gameHistoryArr =  responseJson.getJSONArray("GameHistory");
                for(int i = 0; i < gameHistoryArr.length(); i++){
                    GameHistoryModel itemModel = new GameHistoryModel();
                    JSONObject gameHistoryItem = gameHistoryArr.getJSONObject(i);
                    Log.d("mydebugger", gameHistoryItem.getString("GameName"));
                    itemModel.setGameName(gameHistoryItem.getString("GameName"));
                    itemModel.setGameTime(gameHistoryItem.getString("GameTime"));
                    itemModel.setGameResult(gameHistoryItem.getString("GameResult"));
                    itemModel.setGameRole(gameHistoryItem.getString("GameRole"));
//                    if(gameHistoryItem.getString("pic") != ""){
//                        byte[] decodedString = Base64.decode(timelineItem.getString("pic"), Base64.DEFAULT);
//                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//                        itemModel.setPic(decodedByte);
//                    }
                    returnArr.add(itemModel);
                }
                //if(responseJson.length() != 0) return canAdd.toString()+canFollow.toString();
                //else return "fail to get the posts and add status";
                return "finish";
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }
    @Override
    protected void onPostExecute(String str) {
        super.onPostExecute(str);
        delegate.getResponse(str);
        delegate.getJSONResponse(returnArr);
    }
}
