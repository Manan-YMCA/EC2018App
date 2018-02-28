package com.manan.dev.ec2018app.Utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.manan.dev.ec2018app.R;

import java.util.HashMap;
import java.util.Map;

import static android.graphics.Color.WHITE;

/**
 * Created by nisha on 2/27/2018.
 */

public class TicketsGenerator {
    Context context;
    public ImageView GenerateClick(View view,ImageView imageViewBitmap,String editText) {
        try {
            //setting size of qr code
            int width = imageViewBitmap.getWidth();
            int height = imageViewBitmap.getHeight();
            int smallestDimension = width < height ? width : height;
            String qrCodeData = editText;
            //setting parameters for qr code
            String charset = "UTF-8";
            Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            CreateQRCode(qrCodeData, charset, hintMap, smallestDimension, smallestDimension,imageViewBitmap);

        } catch (Exception ex) {
            Log.e("QrGenerate", ex.getMessage());
        }
        return imageViewBitmap;
    }

    public void CreateQRCode(String qrCodeData, String charset, Map hintMap, int qrCodeheight, int qrCodewidth,ImageView imageViewBitmap) {


        try {
            //generating qr code in bitmatrix type
            BitMatrix matrix = new MultiFormatWriter().encode(new String(qrCodeData.getBytes(charset), charset),
                    BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight, hintMap);
            //converting bitmatrix to bitmap

            int width = matrix.getWidth();
            int height = matrix.getHeight();
            int[] pixels = new int[width * height];
            // All are 0, or black, by default
            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {
                    //pixels[offset + x] = matrix.get(x, y) ? BLACK : WHITE;
                    pixels[offset + x] = matrix.get(x, y) ?
                            ResourcesCompat.getColor(context.getResources(), R.color.Black, null) : WHITE;
                }
            }

            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            //setting bitmap to image view

            Bitmap b = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo_300);
            Bitmap overlay = Bitmap.createScaledBitmap(b, 70, 70, false);
            imageViewBitmap.setImageBitmap(mergeBitmaps(overlay, bitmap));

        } catch (Exception er) {
            Log.e("QrGenerate", er.getMessage());
        }
    }


    public Bitmap mergeBitmaps(Bitmap overlay, Bitmap bitmap) {

        int height = bitmap.getHeight();
        int width = bitmap.getWidth();

        Bitmap combined = Bitmap.createBitmap(width, height, bitmap.getConfig());
        Canvas canvas = new Canvas(combined);
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        canvas.drawBitmap(bitmap, new Matrix(), null);

        int centreX = (canvasWidth - overlay.getWidth()) / 2;
        int centreY = (canvasHeight - overlay.getHeight()) / 2;
        canvas.drawBitmap(overlay, centreX, centreY, null);

        return combined;
    }
}
