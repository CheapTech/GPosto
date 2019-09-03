package br.com.senacrs.gposto.LibClass;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Imagem {

    private static final int REQUIRED_WIDTH = 400;

    public static Bitmap getSmallBitmap(String imagePosto){
        byte[] img = Base64.decode(imagePosto, Base64.DEFAULT);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(img, 0, img.length, options);

        int inSampleSize = calculateInSampleSize(options);
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;

        Bitmap bmp = BitmapFactory.decodeByteArray(img, 0, img.length, options);

        return bmp;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (width > REQUIRED_WIDTH) {
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfWidth / inSampleSize) > REQUIRED_WIDTH) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
