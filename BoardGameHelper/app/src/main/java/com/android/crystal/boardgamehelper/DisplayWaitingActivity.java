package com.android.crystal.boardgamehelper;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;

import org.json.JSONObject;

import java.util.ArrayList;

public class DisplayWaitingActivity extends AppCompatActivity {

    private static Button startButton;
    private static TextView hostStartGame;
    private static Button enterButton;
    private static CirclePgBar pgBar;
    private static ArrayList<TextView> nameList = new ArrayList<>();
    private static ArrayList<UserInfo> list;
    private static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                startButton.setVisibility(View.VISIBLE);
            } else if (msg.what == 2) {
                hostStartGame.setVisibility(View.VISIBLE);
                enterButton.setVisibility(View.VISIBLE);
            } else if (msg.what == 3) {
                updateInfo();
            }
        }
    };
    public static Handler getmHandler() {
        return mHandler;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_waiting);
        startButton = (Button)findViewById(R.id.stop_waiting_and_start);
        startButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startTheGame();
                enterTheGame();
            }
        });

        startButton.setVisibility(View.INVISIBLE);
        hostStartGame = (TextView) findViewById(R.id.host_start_the_game);
        hostStartGame.setVisibility(View.INVISIBLE);
        enterButton = (Button)findViewById(R.id.enter_the_game);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterTheGame();
            }
        });
        enterButton.setVisibility(View.INVISIBLE);
        pgBar = (CirclePgBar)findViewById(R.id.pg_bar);

        TextView v1 = (TextView)findViewById(R.id.player_item_name1);
        nameList.add(v1);
        TextView v2 = (TextView)findViewById(R.id.player_item_name2);
        nameList.add(v2);
        TextView v3 = (TextView)findViewById(R.id.player_item_name3);
        nameList.add(v3);
        TextView v4 = (TextView)findViewById(R.id.player_item_name4);
        nameList.add(v4);
        TextView v5 = (TextView)findViewById(R.id.player_item_name5);
        nameList.add(v5);
        TextView v6 = (TextView)findViewById(R.id.player_item_name6);
        nameList.add(v6);
        TextView v7 = (TextView)findViewById(R.id.player_item_name7);
        nameList.add(v7);
        TextView v8 = (TextView)findViewById(R.id.player_item_name8);
        nameList.add(v8);
        TextView v9 = (TextView)findViewById(R.id.player_item_name9);
        nameList.add(v9);
        updateInfo();
    }
    private static void updateInfo() {
        list = UserInfo.getUsersArray();
        for (int i = 0; i < 9; i++) {
            if (i < list.size()) {
                String name = list.get(i).getNickName();
                nameList.get(i).setText(name);
            } else {
                nameList.get(i).setText("empty");
            }
        }
        CirclePgBar.mProgress = CirclePgBar.mTargetProgress;
        CirclePgBar.mTargetProgress = 1 + list.size() * 11;
        pgBar.invalidate();

    }
    private void startTheGame() {
        JSONObject startGameJson = new JSONObject();
        JSONObject temp = new JSONObject();
        try {
            temp.put("id", UserInfo.getUserInstance().getId());
            temp.put("isGameStart", true);
            startGameJson.put("type", "startGame");
            startGameJson.put("data", temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        EchoWebSocketListener.getWsInstance().send(startGameJson.toString());
    }
    private void enterTheGame() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}
