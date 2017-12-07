package com.android.crystal.boardgamehelper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.glomadrian.codeinputlib.CodeInput;

import org.json.JSONObject;


public class MainGameActivity extends AppCompatActivity {

    final Context context = this;
    private Button start;
    private Button hostButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);
        hostButton = (Button) findViewById(R.id.host_button);
        hostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HostSetting.class);
                startActivity(intent);
            }
        });

        start = (Button) findViewById(R.id.start);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context, GameActivity.class);
//                startActivity(intent);

//                Intent intent = new Intent(context, DisplayWaitingActivity.class);
//                startActivity(intent);



                LayoutInflater li = LayoutInflater.from(context);
                View roomView = li.inflate(R.layout.roomnumdialog, null);

                AlertDialog.Builder roomNumberBuilder = new AlertDialog.Builder(context);
                final EditText nickName = (EditText) roomView.findViewById(R.id.nick_name);
                final CodeInput roomNum = (CodeInput)roomView.findViewById(R.id.room_number);
                roomNumberBuilder.setView(roomView);
                roomNumberBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Log.d("Mydebugger", nickName.getCode().length + "");
                        UserInfo.getUserInstance().setNickName(nickName.getText().toString());
                        //Log.d("Mydebugger", nickName.getCode().length + "");
                        UserInfo.getUserInstance().setRoomNum(Integer.valueOf(makeString(roomNum.getCode())));
                        try {
                            enterTheRoom(UserInfo.getUserInstance().getRoomNum(), UserInfo.getUserInstance().getNickName());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Intent intent = new Intent(context, DisplayWaitingActivity.class);
                        startActivity(intent);
                    }
                });
                roomNumberBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                roomNumberBuilder.setCancelable(true);
                AlertDialog roomNumDialog = roomNumberBuilder.create();
                roomNumDialog.setCanceledOnTouchOutside(true);
                roomNumDialog.show();
            }
        });
    }
    private void enterTheRoom(int roomNum, String name) throws Exception{
        //login
        JSONObject loginJson = new JSONObject();
        try {
            loginJson.put("type", "login");
            JSONObject temp = new JSONObject();
            temp.put("name", name);
            loginJson.put("data", temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        EchoWebSocketListener.getWsInstance().send(loginJson.toString());
        Thread.sleep(100);

        //join room
        JSONObject joinRoomJson = new JSONObject();
        try {
            joinRoomJson.put("type", "joinRoom");
            JSONObject temp = new JSONObject();
            temp.put("roomNumber", roomNum);
            temp.put("id", UserInfo.getUserInstance().getId());
            joinRoomJson.put("data", temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        EchoWebSocketListener.getWsInstance().send(joinRoomJson.toString());

        Log.d("Mydebugger", "RoomNum " + roomNum + " " + name);
    }
    private String makeString(Character[] arr) {
        StringBuilder sb = new StringBuilder();
        for (char ch : arr) {
            sb.append(ch);
        }
        return sb.toString();
    }

}
