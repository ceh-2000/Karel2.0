package com.example.clareheinbaugh.volley;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Karel {
    public String commandHistory;

    private String ip_address = "192.168.43.178";

    //School: 192.168.1.212
    //Phone: 192.168.43.178
    //Home: 192.168.1.55

    public Karel() {
        commandHistory = "";
    }

    public String getCommandHistory() {
        return commandHistory;
    }

    public void setCommandHistory(String commandHistory) {
        this.commandHistory = commandHistory;
    }

    public String ledON(){
        commandHistory += "A";
        return "http://"+ip_address+"2/Home/on.php";
    }

    public String ledOFF(){
        commandHistory += "B";
        return "http://"+ip_address+"/Home/off.php";
    }

    public String ledON2(){
        commandHistory += "C";
        return "http://"+ip_address+"/Home/on2.php";
    }

    public String ledOFF2(){
        commandHistory += "D";
        return "http://"+ip_address+"/Home/off2.php";
    }

    public String shortPause(){
        commandHistory += "E";
        return "http://"+ip_address+"/Home/shortPause.php";
    }

    public String longPause(){
        commandHistory += "F";
        return "http://"+ip_address+"/Home/longPause.php";
    }

    public String forward(){
        commandHistory += "G";
        return "http://"+ip_address+"/Home/forward.php";
    }

    public String turnLeft(){
        commandHistory += "H";
        return "http://"+ip_address+"/Home/turnLeft.php";
    }

    public String[] convertToRun(){
        String[] commandListURLS = new String[commandHistory.length()];
        for(int i = 0; i < commandHistory.length(); i++)
        {
            String curVal = commandHistory.charAt(i)+"";
            if (curVal.equals("A")){
                commandListURLS[i] = "http://"+ip_address+"/Home/on.php";
            }
            else if (curVal.equals("B")){
                commandListURLS[i] = "http://"+ip_address+"/Home/off.php";
            }
            else if (curVal.equals("C")){
                commandListURLS[i] = "http://"+ip_address+"/Home/on2.php";
            }
            else if (curVal.equals("D")){
                commandListURLS[i] = "http://"+ip_address+"/Home/off2.php";
            }
            else if (curVal.equals("E")){
                commandListURLS[i] = "http://"+ip_address+"/Home/shortPause.php";
            }
            else if (curVal.equals("F")){
                commandListURLS[i] = "http://"+ip_address+"/Home/longPause.php";
            }
            else if (curVal.equals("G")){
                commandListURLS[i] = "http://"+ip_address+"/Home/forward.php";
            }
            else if (curVal.equals("H")){
                commandListURLS[i] = "http://"+ip_address+"/Home/turnLeft.php";
            }
        }

        Log.i("HELLO", commandListURLS[0]);
        return commandListURLS;
    }

}
