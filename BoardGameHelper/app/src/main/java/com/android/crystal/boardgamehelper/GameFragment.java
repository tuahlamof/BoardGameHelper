package com.android.crystal.boardgamehelper;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;


public class GameFragment extends Fragment {
    private static ImageButton button1;
    private static ImageButton button2;
    private static ImageButton button3;
    private static ImageButton button4;
    private static ImageButton button5;
    private static ImageButton button6;
    private static ImageButton button7;
    private static ImageButton button8;
    private static ImageButton button9;

    private static Button everyVoteButton;

    private static boolean witchKill= false;
    private static Activity activity;
    private static Resources res;
    public static Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            Log.d("MyDebugger", "fragment get msg" + msg.what);
            if (EchoWebSocketListener.getStatus().equals("dead") && msg.what == -1002) {
                markDead(EchoWebSocketListener.dead1, EchoWebSocketListener.dead2);
            } else if (EchoWebSocketListener.getStatus().equals("vote") && msg.what == -1110) {
                markDead(EchoWebSocketListener.dead1, EchoWebSocketListener.dead2);
                everyVoteButton.setVisibility(View.INVISIBLE);
            } else if (EchoWebSocketListener.getStatus().equals("vote") && msg.what == -1010) {
                werewolfWin();
            } else if (EchoWebSocketListener.getStatus().equals("vote") && msg.what == -1011) {
                folksWin();
            } else if (EchoWebSocketListener.getStatus().equals("vote") && msg.what == -1111){
                everyVoteButton.setVisibility(View.VISIBLE);
            } else if (EchoWebSocketListener.getStatus().equals("prophetRound") && (msg.what == 100001 || msg.what == 100002)) {
                prophetResult(msg.what);
            } else if (EchoWebSocketListener.getStatus().equals("witchRound") && msg.what < 100000 && msg.what >= -1) {
                showDeadInfo(msg.what);
            } else if (EchoWebSocketListener.getStatus().equals("witchRound") && msg.what == 100000){
                showWitchKill();
            }
        }
    };
    public static Handler getmHandler() {
        return mHandler;
    }
    public GameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        activity = (Activity) view.getContext();
        res = getResources();
        ArrayList<UserInfo> userList = UserInfo.getUsersArray();
        TextView text1 = view.findViewById(R.id.player1_name);
        text1.setText("1. " + userList.get(0).getNickName());
        TextView text2 = view.findViewById(R.id.player2_name);
        text2.setText("2. " + userList.get(1).getNickName());
        TextView text3 = view.findViewById(R.id.player3_name);
        text3.setText("3. " + userList.get(2).getNickName());
        TextView text4 = view.findViewById(R.id.player4_name);
        text4.setText("4. " + userList.get(3).getNickName());
        TextView text5 = view.findViewById(R.id.player5_name);
        text5.setText("5. " + userList.get(4).getNickName());
        TextView text6 = view.findViewById(R.id.player6_name);
        text6.setText("6. " + userList.get(5).getNickName());
        TextView text7 = view.findViewById(R.id.player7_name);
        text7.setText("7. " + userList.get(6).getNickName());
        TextView text8 = view.findViewById(R.id.player8_name);
        text8.setText("8. " + userList.get(7).getNickName());
        TextView text9 = view.findViewById(R.id.player9_name);
        text9.setText("9. " + userList.get(8).getNickName());

        button1 = view.findViewById(R.id.player1);
        button2 = view.findViewById(R.id.player2);
        button3 = view.findViewById(R.id.player3);
        button4 = view.findViewById(R.id.player4);
        button5 = view.findViewById(R.id.player5);
        button6 = view.findViewById(R.id.player6);
        button7 = view.findViewById(R.id.player7);
        button8 = view.findViewById(R.id.player8);
        button9 = view.findViewById(R.id.player9);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOnPlayer(1);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOnPlayer(2);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOnPlayer(3);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOnPlayer(4);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOnPlayer(5);
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOnPlayer(6);
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOnPlayer(7);
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOnPlayer(8);
            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOnPlayer(9);
            }
        });

        everyVoteButton = (Button) view.findViewById(R.id.everyone_vote_button);
        everyVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showVoteDialog();
            }
        });
        everyVoteButton.setVisibility(View.INVISIBLE);

        Button voteButton = (Button) view.findViewById(R.id.vote_button);
        //Button nightButton = (Button)view.findViewById(R.id.go_night);
        if (UserInfo.getUserInstance().getHost()) {
            voteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder startVoteBuilder = new AlertDialog.Builder(getActivity());
                    startVoteBuilder.setTitle("Host start the vote");
                    startVoteBuilder.setMessage("Host, do you want start the vote?");
                    startVoteBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            action("readyVote", -1);
                            //showVoteDialog();
                        }
                    });
                    startVoteBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    startVoteBuilder.setCancelable(false);
                    AlertDialog startVoteDialog = startVoteBuilder.create();
                    startVoteDialog.show();

                }
            });
//            nightButton.setOnClickListener(new View.OnClickListener(){
//                @Override
//                public void onClick(View view) {
//                    SoundPlayer.getInstance().playMusic("guard");
//                    //action("dark", -1);
//                }
//            });
        } else {
            //nightButton.setVisibility(View.INVISIBLE);
            voteButton.setVisibility(View.INVISIBLE);
        }
        for (UserInfo k : UserInfo.getUsersArray()) {
            if (k.getDead()) {
                drawDead(k.getSeatId());
            }
        }
        return view;

    }
    private static void showVoteDialog(){
        final EditText editText = new EditText(activity);
        AlertDialog.Builder voteBuilder = new AlertDialog.Builder(activity);
        voteBuilder.setTitle("Vote");
        voteBuilder.setView(editText);
        voteBuilder.setMessage("Give me the seat number. Abstention after 17 sec.");
        voteBuilder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int votePlayer = Integer.valueOf(editText.getText().toString());
                for(UserInfo one : UserInfo.getUsersArray()) {
                    if (one.getSeatId() == votePlayer) {
                        action("vote", one.getId());
                    }
                }
//                try {
//                    Thread.sleep(1500);
//                }catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
        });
        voteBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog startVoteDialog = voteBuilder.create();
        startVoteDialog.show();
    }
    private static void markDead(int u1, int u2) {
        ArrayList<UserInfo> list = UserInfo.getUsersArray();
        for (UserInfo one : list) {
            if (one.getId() == u1 || one.getId() == u2) {
                drawDead(one.getSeatId());
                one.setDead(true);
            }
        }
    }
    private static void drawDead(int n) {
        if (n == 1) {
            button1.setImageDrawable(res.getDrawable(R.drawable.skull));
        } else if (n == 2) {
            button2.setImageDrawable(res.getDrawable(R.drawable.skull));
        } else if (n == 3) {
            button3.setImageDrawable(res.getDrawable(R.drawable.skull));
        } else if (n == 4) {
            button4.setImageDrawable(res.getDrawable(R.drawable.skull));
        } else if (n == 5) {
            button5.setImageDrawable(res.getDrawable(R.drawable.skull));
        } else if (n == 6) {
            button6.setImageDrawable(res.getDrawable(R.drawable.skull));
        } else if (n == 7) {
            button7.setImageDrawable(res.getDrawable(R.drawable.skull));
        } else if (n == 8) {
            button8.setImageDrawable(res.getDrawable(R.drawable.skull));
        } else if (n == 9) {
            button9.setImageDrawable(res.getDrawable(R.drawable.skull));
        }
    }
    private void clickOnPlayer(int n) {
        if (UserInfo.getUserInstance().getDead()) {
            return;
        }
        Log.d("mydebugger", "button " + n);
        ArrayList<UserInfo> list = UserInfo.getUsersArray();
        UserInfo object = new UserInfo();
        for (UserInfo one : list) {
            if (one.getSeatId() == n) {
                object = one;
            }
        }
        final int objectId = object.getId();
        String status = EchoWebSocketListener.getStatus();
        if (status.equals("guardRound") && UserInfo.getUserInstance().getRoleCode() == 5) {
            AlertDialog.Builder guardBuilder = new AlertDialog.Builder(getActivity());
            guardBuilder.setTitle("Guard Round");
            guardBuilder.setMessage("Are you sure you want to guard the player on Seat " + n + "?");
            guardBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    action("guard", objectId);
                }
            });
            guardBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            guardBuilder.setCancelable(true);
            AlertDialog guardDialog = guardBuilder.create();
            guardDialog.setCanceledOnTouchOutside(true);
            guardDialog.show();
        } else if (status.equals("werewolfRound") && UserInfo.getUserInstance().getRoleCode() == 2) {
            AlertDialog.Builder werewolfBuilder = new AlertDialog.Builder(getActivity());
            werewolfBuilder.setTitle("Werewolf Round");
            werewolfBuilder.setMessage("Are you sure you want to kill the player " + n + ". " + object.getNickName() + "?");
            werewolfBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    action("werewolf", objectId);
                }
            });
            werewolfBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            werewolfBuilder.setCancelable(true);
            AlertDialog werewolfDialog = werewolfBuilder.create();
            werewolfDialog.setCanceledOnTouchOutside(true);
            werewolfDialog.show();
        } else if (status.equals("witchRound") && witchKill) {
            AlertDialog.Builder witchKillBuilder = new AlertDialog.Builder(activity);
            witchKillBuilder.setTitle("Witch Round");
            witchKillBuilder.setMessage("Are you sure you want to poison the player " + n + ". " + object.getNickName() + "?");
            witchKillBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    action("witchPoison", objectId);
                }
            });
            witchKillBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            witchKillBuilder.setCancelable(false);
            AlertDialog witchKillDialog = witchKillBuilder.create();
            witchKillDialog.show();
        } else if (status.equals("prophetRound")) {
            AlertDialog.Builder prophetBuilder = new AlertDialog.Builder(getActivity());
            prophetBuilder.setTitle("Fortune Teller Round");
            prophetBuilder.setMessage("Are you sure you want to know the role of player " + n + ". " + object.getNickName() + "?");
            prophetBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    action("prophet",objectId);
                }
            });
            prophetBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            prophetBuilder.setCancelable(true);
            AlertDialog prophetDialog = prophetBuilder.create();
            prophetDialog.setCanceledOnTouchOutside(true);
            prophetDialog.show();
        }

    }

    //show dead info $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    private static void showDeadInfo(int n) {
        if (UserInfo.getUserInstance().getDead()) {
            return;
        }
        final int n1 = n;
        AlertDialog.Builder showDeadBuilder = new AlertDialog.Builder(activity);
        showDeadBuilder.setTitle("Witch Round");
        if (n == -1) {
            showDeadBuilder.setMessage("Nobody was attacked tonight.");
            showDeadBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    //showWitchKill();
                }
            });
        } else if (UserInfo.getUserInstance().getHasRescue()) {
            showDeadBuilder.setMessage("You can not rescue anybody tonight.");
            showDeadBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    //showWitchKill();
                }
            });
        } else {
            UserInfo deadOne = new UserInfo();
            for (UserInfo one : UserInfo.getUsersArray()) {
                if (one.getId() == n) {
                    deadOne = one;
                }
            }
            showDeadBuilder.setMessage(deadOne.getSeatId() + ". " + deadOne.getNickName() + " was attacked. Do you want to save him?");
            showDeadBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    action("witchRescue", n1);
                    UserInfo.getUserInstance().setHasRescue(true);
                }
            });
            showDeadBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    //showWitchKill();
                }
            });
        }
        showDeadBuilder.setCancelable(false);
        AlertDialog showDeadInfoDialog = showDeadBuilder.create();
        showDeadInfoDialog.show();
    }

    //witch kill $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    private static void showWitchKill() {
        if (UserInfo.getUserInstance().getDead()) {
            return;
        }
        AlertDialog.Builder witchKillBuilder = new AlertDialog.Builder(activity);
        witchKillBuilder.setTitle("Witch Round");
        if (UserInfo.getUserInstance().getHasRescue() || UserInfo.getUserInstance().getHasPoison()) {
            witchKillBuilder.setMessage("You can not poison anybody tonight.");
            witchKillBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
        } else {
            witchKillBuilder.setMessage("Do you want to poison somebody?");
            witchKillBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    witchKill = true;
                }
            });
            witchKillBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
        }
        witchKillBuilder.setCancelable(false);
        AlertDialog witchKillDialog = witchKillBuilder.create();
        witchKillDialog.show();
    }
    //prophet info $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    private static void prophetResult(int code) {
        if (UserInfo.getUserInstance().getDead()) {
            return;
        }
        AlertDialog.Builder prophetBuilder = new AlertDialog.Builder(activity);
        prophetBuilder.setTitle("Fortune Teller Round");
        if (code == 100001) {
            prophetBuilder.setMessage("The person you pointed out is a werewolf.");
        } else {
            prophetBuilder.setMessage("The person you pointed out is not a werewolf.");
        }
        prophetBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog prophetDialog = prophetBuilder.create();
        prophetDialog.show();
    }

    //action $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    private static void action(String str, int n) {
        if (UserInfo.getUserInstance().getDead()) {
            return;
        }
        JSONObject actionJson = new JSONObject();
        JSONObject temp = new JSONObject();
        try {
            temp.put("type", str);
            temp.put("id", UserInfo.getUserInstance().getId());
            temp.put("targetId", n);
            actionJson.put("type", "action");
            actionJson.put("data", temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        EchoWebSocketListener.getWsInstance().send(actionJson.toString());
    }

    private static void werewolfWin() {
        AlertDialog.Builder prophetBuilder = new AlertDialog.Builder(activity);
        prophetBuilder.setTitle("Result");
        prophetBuilder.setMessage("Werewolves Win!");
        prophetBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog prophetDialog = prophetBuilder.create();
        prophetDialog.show();
    }

    private static void folksWin() {
        AlertDialog.Builder prophetBuilder = new AlertDialog.Builder(activity);
        prophetBuilder.setTitle("Result");
        prophetBuilder.setMessage("The town folks' camp win!");
        prophetBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog prophetDialog = prophetBuilder.create();
        prophetDialog.show();
    }


    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        for (UserInfo one : UserInfo.getUsersArray()) {
//            if (one.getDead()) {
//                markDead(one.getSeatId(), -1);
//            }
//        }
    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
