package fi.jamk.vko38;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    private TextView textView;

    private ProgressBar progressBar;

    private final String PATH = "http//domain.fi/android";

    private String[] images = {"image1.jpg","image2.jpg","image3.jpg"};

    private int imageIndex;

    private DownloadImageTask task;

    private float x1,x2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView)findViewById(R.id.imageView);
        textView = (TextView)findViewById(R.id.textView);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        imageIndex =0;
        showImage();
    }

    public void showImage() {
        task = new DownloadImageTask();
        task.execute(PATH + images[imageIndex]);
    }

    private class DownloadImageTask extends AsyncTask<String,Void,Bitmap> {


        @Override
        protected void onPreExecute() {

            progressBar.setVisibility(View.VISIBLE);
        }


        @Override
        protected Bitmap doInBackground(String... urls) {
            URL imageUrl;
            Bitmap bitmap = null;
            try {
                imageUrl = new URL(urls[0]);
                InputStream in = imageUrl.openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("<<LOADIMAGE>>", e.getMessage());
            }
            return bitmap;
        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
            textView.setText("Image " + (imageIndex + 1) + "/" + images.length);

            progressBar.setVisibility(View.INVISIBLE);
        }
    }


}
