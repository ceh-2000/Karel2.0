package com.example.clareheinbaugh.volley;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.concurrent.TimeUnit;
import android.text.method.ScrollingMovementMethod;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    /* ------------------------------------------*/
    /*    MEMBER VARIABLES                      */

    //activity variables
    TextView direct;
    Button ledON1;
    Button ledOFF1;
    Button ledON2;
    Button ledOFF2;
    Button shortP;
    Button longP;
    Button mForward;
    Button mTurnLeft;
    TextView codeDisplay;
    Button run;
    Button mClear;
    String commandHistorytext;
    LinearLayout backgroundDude;
    Button mInstructions;
    ImageButton mHome;
    Button mPoints;

    //programmatic variables
    ResponseHelper mResponseHelper;
    Karel karel;
    int currentLevel;
    View screen;
    View root;
    int codeLineNum;
    int points;
    int wrongAnswers;

    //Commands

    //D = DUMMY START OF EVERY ANSWER SEQUENCE IN CASE USER INPUTS NO COMMANDS
    //A = LED 1 on, B = LED 1 off, C = LED 2 on, D = LED 2 off, E = short pause, F = long pause,
    // G = forward, H = left
    Level[] answers = {new Level("Turn light on.", "DA", ""), //D is dummy start to reset system
            new Level("Blink light two times.", "DABAB", ""),
            new Level("Turn in a circle.", "DHHHH", ""),
            new Level("Turn LED on. Move forward. Turn LED off.", "DAGB", ""),
            new Level("Move forward 2 times.", "DGG", ""),
            new Level("Make a box traveling counterclockwise.", "DGHGHGHGH", ""),
            new Level("Turn right.", "DHHH", ""),
            new Level("Blink LED three times and turn right.", "DABABABHHH", ""),
            new Level("Turn LED on. Turn left. Turn LED off. Turn right.", "DAHBHHH", ""),
            new Level("Turn left. Move forward. Turn left. Blink LED one time.", "DHGHAB", ""),
            new Level("Make a box traveling clockwise.", "DGHHHGHHHGHHHGHHH", ""),
            new Level("Turn around.", "DHH", ""),
            new Level("Spin in a circle 2 times.", "DHHHHHHHH", ""),
            new Level("Spin in a circle. Turn left. Spin in a circle. Blink 4 times.", "DHHHHHHHHHABABABAB", ""),
            new Level("Turn around and move forward 3 times.", "DHHGGG", ""),
            new Level("Blink 10 times.", "DABABABABABABABABABAB", ""),
            new Level("Blink. Turn left. Blink. Turn right", "DABHABHHH", ""),
            new Level("Turn light on.", "DA", ""), //dummy level
    };


/* ------------------------------------------*/
    /*    LIFECYCLE METHODS                      */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //scroll
//        setContentView(R.layout.scrollable_textview);



        //activity variables
        direct = findViewById(R.id.instructions);

        ledON1 = findViewById(R.id.on1);
        ledOFF1 = findViewById(R.id.off1);
        ledON2 = findViewById(R.id.on2);
        ledOFF2 = findViewById(R.id.off2);
        shortP = findViewById(R.id.shortPause);
        longP = findViewById(R.id.longPause);
        mForward = findViewById(R.id.forward);
        mTurnLeft = findViewById(R.id.left);

        run = findViewById(R.id.run);
        mClear = findViewById(R.id.clear);
        mInstructions = findViewById(R.id.howto);
        mHome = findViewById(R.id.home);
        mPoints = findViewById(R.id.points);
        codeDisplay = findViewById(R.id.code);
        codeLineNum = 1;
        commandHistorytext = "";
        codeDisplay.setText(commandHistorytext);
        backgroundDude = findViewById(R.id.mainBackground);
        backgroundDude.setBackgroundColor(getResources().getColor(R.color.black));


        codeDisplay.setMovementMethod(new ScrollingMovementMethod());


        //programmatic variables
        mResponseHelper = new ResponseHelper();
        karel = new Karel();
        currentLevel = 0;
        screen = findViewById(R.id.mainBackground);
        root = screen.getRootView();
        points = 0;
        wrongAnswers = 0;
        String url = karel.ledOFF2(); //add dummy start in case no commands are selected


        ledON2.setVisibility(View.GONE); //hide buttons we don't need/have wired components for
        ledOFF2.setVisibility(View.GONE);
        shortP.setVisibility(View.GONE);
        longP.setVisibility(View.GONE);

        direct.setText(answers[currentLevel].getDirections());

        run.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Handler handler = new Handler(); //use for pauses

                String letters = karel.getCommandHistory();

                //get urls for commands entered
                String[] urls = karel.convertToRun();

                //queue for volley requests
                RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());

                for(int j = 0; j < urls.length; j++) {

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, urls[j], mResponseHelper, mResponseHelper);
                    mRequestQueue.add(stringRequest);


                    Log.i("NOTE", urls[j] + "");

                    //longer pause if going forward or turning left
                    if ((letters.charAt(j) + "").equals("G") || (letters.charAt(j) + "").equals("H")) {
                        //add a one second pause
                        try {
                            Thread.sleep(2500); //2500
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    //shorter pause if turning LED on or off
                    else{
                        try {
                            Thread.sleep(1000); //1000
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }


                Log.i("SUPERCALLLL", karel.getCommandHistory()+" "+answers[currentLevel].getAnswer());

                //correct command sequence entered
                if(karel.getCommandHistory().equals(answers[currentLevel].getAnswer())){
                    backgroundDude.setBackgroundColor(getResources().getColor(R.color.green));
                    currentLevel++;
                    points+=1;
                    mPoints.setText("POINTS: "+points);
                    if(currentLevel==answers.length-1){

            //          TOAST
                        Context context = getApplicationContext();
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, "YOU WIN", duration);
                        toast.show();

                        startActivity(new Intent(MainActivity.this, Home.class));

                    }
                }

                //incorrect command sequence entered
                else{
                    backgroundDude.setBackgroundColor(getResources().getColor(R.color.red));
                    wrongAnswers+=1;
                    if(wrongAnswers==3){
                        //          TOAST
                        Context context = getApplicationContext();
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, "GAME OVER", duration);
                        toast.show();

                        startActivity(new Intent(MainActivity.this, Home.class));

                    }
                }

                handler.postDelayed(new Runnable() {
                    public void run() {
                    resetLevel();


                    }
                }, 1000);
            }
        });

        mClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                resetLevel();

//          TOAST
//                Context context = getApplicationContext();
//                CharSequence text = answers[currentLevel].get();
//                int duration = Toast.LENGTH_SHORT;
//
//                Toast toast = Toast.makeText(context, text, duration);
//                toast.show();
            }
        });

        mInstructions.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //go to instructions screen
                startActivity(new Intent(MainActivity.this, EndScreen.class));
            }
        });

        mHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //go to home screen
                startActivity(new Intent(MainActivity.this, Home.class));
            }
        });

        //LED on
        ledON1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String url = karel.ledON();
                commandHistorytext += codeLineNum+". LED turns on\n";
                codeLineNum+=1;
                codeDisplay.setText(commandHistorytext);
           }
        });

        //LED off
        ledOFF1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String url = karel.ledOFF();
                commandHistorytext += codeLineNum+". LED turns off\n";
                codeLineNum+=1;
                codeDisplay.setText(commandHistorytext);

            }
        });

        //not used
        ledON2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String url = karel.ledON2();
                commandHistorytext += codeLineNum+". LED 2 turns on\n";
                codeLineNum+=1;
                codeDisplay.setText(commandHistorytext);

            }
        });

        //not used
        ledOFF2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String url = karel.ledOFF2();
                commandHistorytext += codeLineNum+". LED 2 turns off\n";
                codeLineNum+=1;
                codeDisplay.setText(commandHistorytext);

            }
        });

        //not used
        shortP.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String url = karel.shortPause();
                commandHistorytext += codeLineNum+". Pause for 1 second\n";
                codeLineNum+=1;
                codeDisplay.setText(commandHistorytext);
            }
        });

        //not used
        longP.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String url = karel.longPause();
                commandHistorytext += codeLineNum+". Pause for 5 seconds\n";
                codeLineNum+=1;
                codeDisplay.setText(commandHistorytext);

            }
        });

        //robot moves forward
        mForward.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String url = karel.forward();
                commandHistorytext += codeLineNum+". Move forward\n";
                codeLineNum+=1;
                codeDisplay.setText(commandHistorytext);

            }
        });

        //robot turns left
        mTurnLeft.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String url = karel.turnLeft();
                commandHistorytext += codeLineNum+". Turn Left\n";
                codeLineNum+=1;
                codeDisplay.setText(commandHistorytext);

            }
        });
    }

    /* ------------------------------------------*/
    /*    INTERFACE METHODS                      */


    public void getIt(String url) {


        RequestQueue mRequestQueue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, mResponseHelper, mResponseHelper);
        mRequestQueue.add(stringRequest);

        Log.i("NOTE", mRequestQueue+"");


    }

    public void resetLevel(){
        direct.setText(answers[currentLevel].getDirections());
        getIt(karel.ledOFF());
        getIt(karel.ledOFF2());
        karel.setCommandHistory("");
        Log.i("HELLO", commandHistorytext);
        commandHistorytext = "";
        codeDisplay.setText(commandHistorytext);
        backgroundDude.setBackgroundColor(getResources().getColor(R.color.black));
        codeLineNum=1;
        String url = karel.ledOFF2(); //add dummy start in case no commands are selected

    }


    /* ------------------------------------------*/
    /*    HELPER CLASSES                         */

    class ResponseHelper implements Response.Listener<String>, Response.ErrorListener {

        public ResponseHelper() {
        }

        @Override
        public void onResponse(String response) {
            Log.i("HEINBAUGH", response);

        }

        @Override
        public void onErrorResponse(VolleyError error) {
            Log.i("ERROR", error+"");
            Log.i("HEINBAUGH", "error");

        }

    }
}