package app.project.tictactoe.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.CodaBarWriter;
import com.google.zxing.qrcode.QRCodeWriter;

import app.project.tictactoe.R;
import app.project.tictactoe.Utils.Constants;

public class QRGen extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrgen);
        imageView = (ImageView) findViewById(R.id.imageView);
        if (Constants.bmp != null) {
            imageView.setImageBitmap(Constants.bmp);
        }
        generateQR(Constants.mob);
    }

    /**
     * @method generateQR
     * @desc Generate QR on screen
     */
    private void generateQR(String content) {

        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Constants.bmp = null;
            Constants.bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    Constants.bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            imageView.setImageBitmap(Constants.bmp);

        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @method generateBar
     * @desc Generate Bar on screen
     */
    private void generateBar(String content) {
        CodaBarWriter barcode = new CodaBarWriter();
        try {
            BitMatrix bitMatrix = barcode.encode(content, BarcodeFormat.CODABAR, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Constants.bmp = null;
            Constants.bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    Constants.bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            imageView.setImageBitmap(Constants.bmp);

        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
