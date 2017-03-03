package app.project.tictactoe.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import app.project.tictactoe.R;
import app.project.tictactoe.Utils.GameUtil;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private ImageView img[][] = new ImageView[3][3];
    private TextView txtPlayer1, txtPlayer2;
    private LinearLayout layout1, layout2;
    private int player = 0;
    private int red, green;
    private int M[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        resetGame();
        txtPlayer1.startAnimation(AnimationUtils.loadAnimation(this, R.anim.appear_player));
        txtPlayer2.startAnimation(AnimationUtils.loadAnimation(this, R.anim.appear_player));
    }

    @Override
    public void onBackPressed() {

        resetGame();
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {

            case R.id.img00:
                setMark(0, 0, player);
                break;
            case R.id.img01:
                setMark(0, 1, player);
                break;
            case R.id.img02:
                setMark(0, 2, player);
                break;
            case R.id.img10:
                setMark(1, 0, player);
                break;
            case R.id.img11:
                setMark(1, 1, player);
                break;
            case R.id.img12:
                setMark(1, 2, player);
                break;
            case R.id.img20:
                setMark(2, 0, player);
                break;
            case R.id.img21:
                setMark(2, 1, player);
                break;
            case R.id.img22:
                setMark(2, 2, player);
                break;

            default:

                Toast.makeText(this, "Warning: Unhandeled OnClick Event", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    private void setMark(int x, int y, int mark) {

        int m = M[x][y];
        if (m == -1) {
            M[x][y] = mark;
            img[x][y].setImageResource(GameUtil.getImgRes(mark));
            int p = checkGame();
            if (p < 0) {
                swapPlayer();
            } else if (p == 0) {
                gameDraw();
            } else {
                gameWon(p);
            }
        } else {
            // Already Marked
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
        ;
    }

    private void gameDraw() {
        Toast.makeText(this, "Match Draw...!", Toast.LENGTH_SHORT).show();
        newGame();
    }

    private void newGame() {

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

    private void swapPlayer() {
        if (player == 0) {
            player = 1;
            txtPlayer1.setTextColor(red);
            txtPlayer2.setTextColor(green);

        } else if (player == 1) {
            player = 0;
            txtPlayer1.setTextColor(green);
            txtPlayer2.setTextColor(red);
        }
    }

    private void resetGame() {

        player = 0;

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

    }

    private int checkGame() {

        if (M[0][0] == M[1][1] && M[1][1] == M[2][2] & M[2][2] != -1) {
            img[0][0].setBackgroundResource(R.drawable.pattern_win);
            img[1][1].setBackgroundResource(R.drawable.pattern_win);
            img[2][2].setBackgroundResource(R.drawable.pattern_win);
            return M[2][2] + 1; //Won

        } else if (M[2][0] == M[1][1] && M[1][1] == M[0][2] & M[0][2] != -1) {
            img[2][0].setBackgroundResource(R.drawable.pattern_win);
            img[1][1].setBackgroundResource(R.drawable.pattern_win);
            img[0][2].setBackgroundResource(R.drawable.pattern_win);
            return M[0][2] + 1; //Won

        }

        for (int i = 0; i < 3; i++) {
            if (M[i][0] == M[i][1] && M[i][1] == M[i][2] & M[i][2] != -1) {
                img[i][0].setBackgroundResource(R.drawable.pattern_win);
                img[i][1].setBackgroundResource(R.drawable.pattern_win);
                img[i][2].setBackgroundResource(R.drawable.pattern_win);
                return M[i][2] + 1; //Won

            } else if (M[0][i] == M[1][i] && M[1][i] == M[2][i] & M[2][i] != -1) {
                img[0][i].setBackgroundResource(R.drawable.pattern_win);
                img[1][i].setBackgroundResource(R.drawable.pattern_win);
                img[2][i].setBackgroundResource(R.drawable.pattern_win);
                return M[2][i] + 1; // Won
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
