package com.android.crystal.boardgamehelper;


import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

public class GameActivity extends AppCompatActivity {

    private BottomBar bottomBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        SoundPlayer.getInstance().init(this);
        if(UserInfo.getUserInstance().getHost()) {
            SoundPlayer.getInstance().playMusic("guard");
        }
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                if (tabId == R.id.tab_role) {
                    CheckRoleFragment checkRoleFragment = new CheckRoleFragment();
                    fragmentTransaction.replace(R.id.fragmentContainer, checkRoleFragment);
                    fragmentTransaction.commit();
                } else {
                    GameFragment gameFragment = new GameFragment();
                    fragmentTransaction.replace(R.id.fragmentContainer, gameFragment);
                    fragmentTransaction.commit();
                }
            }
        });
        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {

            }
        });

    }
}
