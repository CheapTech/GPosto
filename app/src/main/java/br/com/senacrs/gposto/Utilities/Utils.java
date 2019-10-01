package br.com.senacrs.gposto.Utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.Gravity;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Utils {

    public static void longToast(Context context, String message){
        Toast msg = Toast.makeText(context,message,Toast.LENGTH_LONG);
        msg.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,450);
        msg.show();
    }
    public static void shortToast(Context context, String message){
        Toast msg = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        msg.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 450);
        msg.show();
    }

    public static String convertBitmapToBase64(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG,70,baos);
        byte[] imgBytes = baos.toByteArray();
        String strImg64 = Base64.encodeToString(imgBytes, Base64.DEFAULT);
        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strImg64;
    }

    public static Bitmap convertBase64ToBitmap(String strImage64){
        byte[] img = Base64.decode(strImage64,Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(img,0,img.length);
        return bmp;
    }
}
