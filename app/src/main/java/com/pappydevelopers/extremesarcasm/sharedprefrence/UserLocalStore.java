package com.pappydevelopers.extremesarcasm.sharedprefrence;

/**
 * Created by yash on 11/2/18.
 */

import android.content.Context;
import android.content.SharedPreferences;

public  class UserLocalStore{

    public static final String SP_Name = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context)
    {
        userLocalDatabase = context.getSharedPreferences(SP_Name,0);
    }


    public void setUserloggedIn(boolean loggedIn){
        SharedPreferences.Editor speditor = userLocalDatabase.edit();
        speditor.putBoolean("loggedIn",loggedIn);
        speditor.commit();

    }



    public void setFlag(int flag){
        SharedPreferences.Editor speditor = userLocalDatabase.edit();
        speditor.putInt("flag",flag);
        speditor.commit();

    }



    public int getFlag(){
        return userLocalDatabase.getInt("flag", 0);
    }

    public boolean getuserloggedIn(){

        if(userLocalDatabase.getBoolean("loggedIn",false) == true)
            return true;
        else
            return false;
    }


    public void clearUserdata(){
        SharedPreferences.Editor speditor = userLocalDatabase.edit();
        speditor.clear();

    }
}
