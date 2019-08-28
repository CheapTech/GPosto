package br.com.senacrs.gposto.Utilities;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class Utils {

    public static void longToast(Context context, String message){
        Toast msg = Toast.makeText(context,message,Toast.LENGTH_LONG);
        msg.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,350);
        msg.show();
    }
    public static void shortToast(Context context, String message){
        Toast msg = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        msg.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 350);
        msg.show();
    }
}
