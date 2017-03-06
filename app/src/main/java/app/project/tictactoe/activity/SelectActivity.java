package app.project.tictactoe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import app.project.tictactoe.R;

public class SelectActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        findViewById(R.id.btn_qr).setOnClickListener(this);
        findViewById(R.id.btn_number).setOnClickListener(this);
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

            default:
                Toast.makeText(this, "Unknown onClickEvent...!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
