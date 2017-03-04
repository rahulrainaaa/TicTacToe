package app.project.tictactoe.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.zxing.Result;

import app.project.tictactoe.Utils.Constants;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRScan extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        Toast.makeText(this, "" + rawResult.getText(), Toast.LENGTH_SHORT).show();
        Constants.friendMob = "" + rawResult.getText().toString().trim();
        Constants.mainActivity.Player2Joined();
        finish();
        return;
        //Toast.makeText(this, "" + rawResult.getBarcodeFormat().toString(), Toast.LENGTH_LONG).show();
        // If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);
    }


}
