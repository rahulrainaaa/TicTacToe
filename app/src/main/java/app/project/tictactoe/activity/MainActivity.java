package app.project.tictactoe.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import app.project.tictactoe.R;
import app.project.tictactoe.Utils.Constants;
import app.project.tictactoe.Utils.GameUtil;
import app.project.tictactoe.model.GoogleDB;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ValueEventListener {

    private ImageView img[][] = new ImageView[3][3];
    private TextView txtPlayer1, txtPlayer2;
    private LinearLayout layout1, layout2;
    private SharedPreferences s;
    private String mob;
    private int player = 0;
    private int red, green;
    private int M[][];
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private GoogleDB gdb = new GoogleDB();
    private int flag = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.READ_SMS,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CAMERA}, 1);

        img[0][0] = (ImageView) findViewById(R.id.img00);
        img[0][1] = (ImageView) findViewById(R.id.img01);
        img[0][2] = (ImageView) findViewById(R.id.img02);
        img[1][0] = (ImageView) findViewById(R.id.img10);
        img[1][1] = (ImageView) findViewById(R.id.img11);
        img[1][2] = (ImageView) findViewById(R.id.img12);
        img[2][0] = (ImageView) findViewById(R.id.img20);
        img[2][1] = (ImageView) findViewById(R.id.img21);
        img[2][2] = (ImageView) findViewById(R.id.img22);

        txtPlayer1 = (TextView) findViewById(R.id.txt_player_1);
        txtPlayer2 = (TextView) findViewById(R.id.txt_player_2);

        layout1 = (LinearLayout) findViewById(R.id.activity_main);
        layout2 = (LinearLayout) findViewById(R.id.activity_main1);

        img[0][0].setOnClickListener(this);
        img[0][1].setOnClickListener(this);
        img[0][2].setOnClickListener(this);
        img[1][0].setOnClickListener(this);
        img[1][1].setOnClickListener(this);
        img[1][2].setOnClickListener(this);
        img[2][0].setOnClickListener(this);
        img[2][1].setOnClickListener(this);
        img[2][2].setOnClickListener(this);

        red = Color.RED;
        green = Color.GREEN;
        txtPlayer1.startAnimation(AnimationUtils.loadAnimation(this, R.anim.appear_player));
        txtPlayer2.startAnimation(AnimationUtils.loadAnimation(this, R.anim.appear_player));

        s = getSharedPreferences("cache", 0);
        String mob = s.getString("mob", "0000000000").trim();

        if (mob.contains("0000000000")) {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        mob = s.getString("mob", "0000000000").trim();
        if (mob.contains("0000000000")) {
            Toast.makeText(this, "Please verify your mobile number.", Toast.LENGTH_SHORT).show();
            layout1.setEnabled(false);
        } else if (flag == 2) {
            player = 2;
            flag = 0;
            initFirebase();
            Toast.makeText(this, "Connected as Player 2", Toast.LENGTH_SHORT).show();
        } else if (flag == 1) {
            player = 1;
            flag = 0;
            initFirebase();
            Toast.makeText(this, "Connected as Player 1", Toast.LENGTH_SHORT).show();
        } else {
            //initFirebase();
        }
        flag = 0;
        Toast.makeText(this, "Login as:" + mob, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.menu_action_scan:
                flag = 2;   // Scan and connect as player 2.
                startActivity(new Intent(this, QRScan.class));
                break;

            case R.id.menu_action_gen:
                flag = 1; // gen and act as player 1
                Constants.mob = mob;
                startActivity(new Intent(this, QRGen.class));
                break;

            default:
                break;
        }
        return true;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        gdb = dataSnapshot.getValue(GoogleDB.class);
        Toast.makeText(this, "data change callback from google firebase DB.", Toast.LENGTH_SHORT).show();
        parseRTDB(gdb);
    }

    @Override
    public void onCancelled(DatabaseError error) {
        // Failed to read value
        Log.w("TicTacToe Google RTDB:", "Failed to read value." + error.toException());
    }

    @Override
    public void onClick(View view) {

        int mark = 1;
        if (player == 1) {
            mark = 0;
        } else if (player == 2) {
            mark = 1;
        }

        switch (view.getId()) {

            case R.id.img00:
                setMark(0, 0, mark);
                break;
            case R.id.img01:
                setMark(0, 1, mark);
                break;
            case R.id.img02:
                setMark(0, 2, mark);
                break;
            case R.id.img10:
                setMark(1, 0, mark);
                break;
            case R.id.img11:
                setMark(1, 1, mark);
                break;
            case R.id.img12:
                setMark(1, 2, mark);
                break;
            case R.id.img20:
                setMark(2, 0, mark);
                break;
            case R.id.img21:
                setMark(2, 1, mark);
                break;
            case R.id.img22:
                setMark(2, 2, mark);
                break;

            default:

                Toast.makeText(this, "Warning: Unhandeled OnClick Event", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    private void initFirebase() {
        layout1.setEnabled(true);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReferenceFromUrl("https://tictactoe-b607e.firebaseio.com/" + mob.trim());
        resetGame();
        myRef.addValueEventListener(this);
    }

    private void parseRTDB(GoogleDB fdb) {

        img[0][0].setImageResource(GameUtil.getImgRes(fdb.getAa()));
        img[0][1].setImageResource(GameUtil.getImgRes(fdb.getAb()));
        img[0][2].setImageResource(GameUtil.getImgRes(fdb.getAc()));
        img[1][0].setImageResource(GameUtil.getImgRes(fdb.getBa()));
        img[1][1].setImageResource(GameUtil.getImgRes(fdb.getBb()));
        img[1][2].setImageResource(GameUtil.getImgRes(fdb.getBc()));
        img[2][0].setImageResource(GameUtil.getImgRes(fdb.getCa()));
        img[2][1].setImageResource(GameUtil.getImgRes(fdb.getCb()));
        img[2][2].setImageResource(GameUtil.getImgRes(fdb.getCc()));

        M[0][0] = fdb.getAa();
        M[0][1] = fdb.getAb();
        M[0][2] = fdb.getAc();
        M[1][0] = fdb.getBa();
        M[1][1] = fdb.getBb();
        M[1][2] = fdb.getBc();
        M[2][0] = fdb.getCa();
        M[2][1] = fdb.getCb();
        M[2][2] = fdb.getCc();
    }

    private void setMark(int x, int y, int mark) {

        if (gdb.getPlayer() != player) {
            return;
        }

        int m = M[x][y];
        if (m == -1) {
            M[x][y] = mark;
            img[x][y].setImageResource(GameUtil.getImgRes(mark));
            int p = checkGame();
            gdb.setWon(p);
            reflectToRTDB();
            if (p < 0) {
                // continue
            } else if (p == 0) {
                gameDraw();
            } else {
                gameWon(p);
            }
        } else {
            // Already Marked
        }
    }

    private void reflectToRTDB() {
        if (gdb.getPlayer() == 1) {
            gdb.setPlayer(2);
        } else {
            gdb.setPlayer(1);
        }
        gdb.setAa(M[0][0]);
        gdb.setAb(M[0][1]);
        gdb.setAc(M[0][2]);
        gdb.setBa(M[1][0]);
        gdb.setBb(M[1][1]);
        gdb.setBc(M[1][2]);
        gdb.setCa(M[2][0]);
        gdb.setCb(M[2][1]);
        gdb.setCc(M[2][2]);
        myRef.setValue(gdb);
        if (gdb.getPlayer() == 1) {
            txtPlayer1.setTextColor(green);
            txtPlayer2.setTextColor(red);

        } else if (gdb.getPlayer() == 2) {
            txtPlayer1.setTextColor(red);
            txtPlayer2.setTextColor(green);
        }
    }

    private void gameWon(int player) {
        Toast.makeText(this, "Player " + player + " won...!", Toast.LENGTH_SHORT).show();
        if (player == 1) {
            //txtPlayer1.startAnimation(AnimationUtils.loadAnimation(this, R.anim.win_player));
        } else if (player == 2) {
            //txtPlayer2.startAnimation(AnimationUtils.loadAnimation(this, R.anim.win_player));
        }
        newGame();

    }

    private void gameDraw() {
        Toast.makeText(this, "Match Draw...!", Toast.LENGTH_SHORT).show();
        newGame();
    }

    private void newGame() {

        layout1.setEnabled(true);
        layout1.startAnimation(AnimationUtils.loadAnimation(this, R.anim.next_game1));
        layout2.startAnimation(AnimationUtils.loadAnimation(this, R.anim.next_game2));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                MainActivity.this.resetGame();
            }
        }, 1000);

    }

    private void resetGame() {

        gdb.setAa(-1);
        gdb.setAb(-1);
        gdb.setAc(-1);
        gdb.setBa(-1);
        gdb.setBb(-1);
        gdb.setBc(-1);
        gdb.setCa(-1);
        gdb.setCb(-1);
        gdb.setCc(-1); // 0=o, 1=x, -1=nil (mark on board)
        gdb.setPlayer(player); // 1 = player1 and 2 = player 2
        gdb.setWon(-1);  // -1 = Nothing, 0 = Draw, 1 = player 1 and 2 = player 2
        try {
            myRef.setValue(gdb);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Exception:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        M = new int[][]{
                {-1, -1, -1},
                {-1, -1, -1},
                {-1, -1, -1}
        };

        img[0][0].setImageResource(android.R.color.transparent);
        img[0][1].setImageResource(android.R.color.transparent);
        img[0][2].setImageResource(android.R.color.transparent);
        img[1][0].setImageResource(android.R.color.transparent);
        img[1][1].setImageResource(android.R.color.transparent);
        img[1][2].setImageResource(android.R.color.transparent);
        img[2][0].setImageResource(android.R.color.transparent);
        img[2][1].setImageResource(android.R.color.transparent);
        img[2][2].setImageResource(android.R.color.transparent);

        img[0][0].setBackgroundColor(Color.TRANSPARENT);
        img[0][1].setBackgroundColor(Color.TRANSPARENT);
        img[0][2].setBackgroundColor(Color.TRANSPARENT);
        img[1][0].setBackgroundColor(Color.TRANSPARENT);
        img[1][1].setBackgroundColor(Color.TRANSPARENT);
        img[1][2].setBackgroundColor(Color.TRANSPARENT);
        img[2][0].setBackgroundColor(Color.TRANSPARENT);
        img[2][1].setBackgroundColor(Color.TRANSPARENT);
        img[2][2].setBackgroundColor(Color.TRANSPARENT);

        img[0][0].clearAnimation();
        img[0][1].clearAnimation();
        img[0][2].clearAnimation();
        img[1][0].clearAnimation();
        img[1][1].clearAnimation();
        img[1][2].clearAnimation();
        img[2][0].clearAnimation();
        img[2][1].clearAnimation();
        img[2][2].clearAnimation();

        txtPlayer1.clearAnimation();
        txtPlayer2.clearAnimation();

        layout1.setEnabled(true);
    }

    private int checkGame() {

        if (M[0][0] == M[1][1] && M[1][1] == M[2][2] & M[2][2] != -1) {
            img[0][0].setBackgroundResource(R.drawable.pattern_win);
            img[1][1].setBackgroundResource(R.drawable.pattern_win);
            img[2][2].setBackgroundResource(R.drawable.pattern_win);
            return M[2][2]; //Won

        } else if (M[2][0] == M[1][1] && M[1][1] == M[0][2] & M[0][2] != -1) {
            img[2][0].setBackgroundResource(R.drawable.pattern_win);
            img[1][1].setBackgroundResource(R.drawable.pattern_win);
            img[0][2].setBackgroundResource(R.drawable.pattern_win);
            return M[0][2]; //Won

        }

        for (int i = 0; i < 3; i++) {
            if (M[i][0] == M[i][1] && M[i][1] == M[i][2] & M[i][2] != -1) {
                img[i][0].setBackgroundResource(R.drawable.pattern_win);
                img[i][1].setBackgroundResource(R.drawable.pattern_win);
                img[i][2].setBackgroundResource(R.drawable.pattern_win);
                return M[i][2]; //Won

            } else if (M[0][i] == M[1][i] && M[1][i] == M[2][i] & M[2][i] != -1) {
                img[0][i].setBackgroundResource(R.drawable.pattern_win);
                img[1][i].setBackgroundResource(R.drawable.pattern_win);
                img[2][i].setBackgroundResource(R.drawable.pattern_win);
                return M[2][i]; // Won
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (M[i][j] == -1) {
                    return -1;  // Nothing
                }
            }
        }

        return 0; //Draw
    }
}
