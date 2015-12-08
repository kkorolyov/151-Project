package org.cs151.callrejector.gui;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

//added 12/03 watson
// use the following to download image
//ImageView img1 = (ImageView) findViewById(R.id.image1);
//String url1 = "http://opendatakosovo.org/wp-content/uploads/2015/08/android-logo.png";
//img1.setTag(url1);
//(new ImageDownload()).execute(img1);

/**
 * Class that downloads images from a String URL
 */
public class ImageDownload extends AsyncTask<ImageView, Void, Bitmap> {

    ImageView imageView = null;
    
    //use this method to download
    public void startDonwload ( ImageView img, String url ) {    	
    	img.setTag(url);    
    	doInBackground(img);
    }

    @Override
    protected Bitmap doInBackground(ImageView... imageViews) {
        this.imageView = imageViews[0];
        return download((String)imageView.getTag());
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
    }

    /*
     * downloads bitmap from a String URL
     */
    private Bitmap download(String url) {

        Bitmap bmp =null;
        try{
            URL ulrn = new URL(url);
            HttpURLConnection con = (HttpURLConnection)ulrn.openConnection();
            InputStream is = con.getInputStream();
            bmp = BitmapFactory.decodeStream(is);
            if (null != bmp)
                return bmp;

            }catch(Exception e){}
        return bmp;
    }
}