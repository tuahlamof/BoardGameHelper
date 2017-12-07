package com.android.crystal.boardgamehelper;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class GameHistoryActivity extends ListActivity implements AsyncResponse {

    //OkHttpClient client = new OkHttpClient();
    ListView lvGameHistory;
    ArrayList<GameHistoryModel> itemList;
    GameHistoryAdapter gameHistoryAdapter;
    Button enterTheGame;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("myDebuger", "create game history list view");
        Intent i = getIntent();
        String firstName = i.getStringExtra("firstName");
        String id = i.getStringExtra("id");
        String lastName = i.getStringExtra("lastName");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamehistory);
        itemList = new ArrayList<>();
        GameHistoryModel testModel = new GameHistoryModel();
        testModel.setGameName("Game0");
        testModel.setGameRole("The Witch");
        testModel.setGameResult("Win");
        testModel.setGameTime("Today");
        itemList.add(testModel);
        Log.d("mydebugger", "testModel");

        lvGameHistory = getListView();
        gameHistoryAdapter = new GameHistoryAdapter(this, R.layout.gamehistory_item, itemList);
        lvGameHistory.setAdapter(gameHistoryAdapter);
        Log.d("mydebugger", "set adapter");

        ConnWorker connWorker = new ConnWorker();
        connWorker.delegate = getAsyncResponse();
        connWorker.execute("getHistory", "1", "GET");

        enterTheGame = findViewById(R.id.start);
        enterTheGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainGameActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void getResponse(String str) {

    }

    @Override
    public void getJSONResponse(ArrayList array) {
        if(array != null){
            Log.d("mydebugger", "123");
            System.out.println("get the history arr from server");
            gameHistoryAdapter.clear();
            gameHistoryAdapter.addAll(array);
            gameHistoryAdapter.notifyDataSetChanged();
        }
    }

    public AsyncResponse getAsyncResponse(){
        return this;
    }
}

