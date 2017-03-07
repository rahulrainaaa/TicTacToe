package app.project.tictactoe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import app.project.tictactoe.R;

public class SelectActivity extends FragmentActivity implements View.OnClickListener {

    private Animation animationM = null;
    private Animation animationQ = null;

    private ImageView imgM = null;
    private ImageView imgQ = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_select);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);

        animationM = AnimationUtils.loadAnimation(this, R.anim.cloudm);
        animationQ = AnimationUtils.loadAnimation(this, R.anim.cloudq);

        imgQ = (ImageView) findViewById(R.id.btn_qr);
        imgM = (ImageView) findViewById(R.id.btn_number);

        imgQ.setOnClickListener(this);
        imgM.setOnClickListener(this);
        findViewById(R.id.image_new).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        imgM.startAnimation(animationM);
        imgQ.startAnimation(animationQ);
    }

    @Override
    protected void onPause() {
        super.onPause();
        imgM.clearAnimation();
        imgQ.clearAnimation();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_qr:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.btn_number:
                startActivity(new Intent(this, GameActivity.class));
                break;
            case R.id.image_new:
                startActivity(new Intent(this, LoginActivity.class));
                break;

            default:
                Toast.makeText(this, "Unknown onClickEvent...!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
