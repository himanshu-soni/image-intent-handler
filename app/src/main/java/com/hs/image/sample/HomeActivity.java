package com.hs.image.sample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.hs.image.ImageIntentHandler;
import com.hs.image.ImageUtils;

import java.io.File;


public class HomeActivity extends AppCompatActivity {

    ImageView mImageView;
    Button mButtonCapture;
    Button mButtonPick;

    ImageIntentHandler.ImagePair mImagePair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mImageView = (ImageView) findViewById(R.id.imageView);
        mButtonCapture = (Button) findViewById(R.id.button_capture);
        mButtonPick = (Button) findViewById(R.id.button_pick);

        mButtonCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File f = ImageUtils.createImageFile(ImageUtils.getPackageName(HomeActivity.this));
                if ((f != null) && f.exists()) {
                    mImagePair = new ImageIntentHandler.ImagePair(mImageView, f.getAbsolutePath());
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(takePictureIntent, ImageIntentHandler.REQUEST_CAPTURE);
                } else {
                    Toast.makeText(HomeActivity.this, "Storage Error", Toast.LENGTH_LONG).show();
                }
            }
        });

        mButtonPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImagePair = new ImageIntentHandler.ImagePair(mImageView, null);
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, ImageIntentHandler.REQUEST_GALLERY);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageIntentHandler intentHandler =
                new ImageIntentHandler(HomeActivity.this, mImagePair)
                        .folder("IIH Sample")
                        .sizeDp(200);
        intentHandler.handleIntent(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_github) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/himanshu-soni/"));
            startActivity(browserIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
