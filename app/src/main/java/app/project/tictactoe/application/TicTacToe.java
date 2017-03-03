package app.project.tictactoe.application;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import app.project.tictactoe.model.GoogleDB;

public class TicTacToe extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReferenceFromUrl("https://tictactoe-b607e.firebaseio.com/");

        GoogleDB gdb = new GoogleDB();
        gdb.setAa("-1");
        gdb.setAb("1");
        gdb.setAc("1");
        gdb.setBa("0");
        gdb.setBb("1");
        gdb.setBc("0");
        gdb.setCa("0");
        gdb.setCb("0");
        gdb.setCc("0");
        gdb.setPlayer("1");
        gdb.setWon("0");

        myRef.setValue(gdb);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

}
