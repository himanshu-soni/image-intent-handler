package com.hs.image;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * Created by Himanshu on 3/17/2015.
 */
public class ImageIntentHandler {

    public static final int REQUEST_CAPTURE = 100,
            REQUEST_GALLERY = 200;

    private Context mContext;
    private ImagePair mImagePair;

    private int mWidth = 120, mHeight = 120;
    private String folderName = "IIH";

    public ImageIntentHandler(Context context, ImagePair imagePair) {
        mContext = context;
        mImagePair = imagePair;
        folderName = context.getPackageName();
    }

    public ImageIntentHandler sizePx(int width) {
        this.mWidth = width;
        this.mHeight = width;
        return this;
    }

    public ImageIntentHandler folder(String folderName) {
        this.folderName = folderName;
        return this;
    }

    public ImageIntentHandler sizePx(int width, int height) {
        this.mWidth = width;
        this.mHeight = height;
        return this;
    }


    public ImageIntentHandler sizeDp(int width) {
        this.mWidth = UnitUtils.dpToPx(width);
        this.mHeight = UnitUtils.dpToPx(width);
        return this;
    }

    public ImageIntentHandler sizeDp(int width, int height) {
        this.mWidth = UnitUtils.dpToPx(width);
        this.mHeight = UnitUtils.dpToPx(height);
        return this;
    }

    public void handleIntent(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CAPTURE) {
            setCapturedImage();
        } else if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_GALLERY && null != data) {
            mImagePair.imagePath = ImageUtils.getSmallImageFromSDCard(folderName, ImageUtils.getRealPathFromURI(mContext, data.getData()), mWidth, mHeight);
            mImagePair.imageView.setImageBitmap(ImageUtils.getBitmapFromFile(mImagePair.imagePath, mWidth, mHeight));
        } else if (resultCode == Activity.RESULT_CANCELED && (requestCode == REQUEST_CAPTURE || requestCode == REQUEST_GALLERY)) {
            mImagePair.imagePath = null;
        }
    }

    private void setCapturedImage() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    return ImageUtils.getRightAngleImage(mImagePair.imagePath);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
                return mImagePair.imagePath;
            }

            @Override
            protected void onPostExecute(String imagePath) {
                super.onPostExecute(imagePath);
                mImagePair.imageView.setImageBitmap(ImageUtils.getBitmapFromFile(imagePath, mWidth, mHeight));
            }
        }.execute();
    }


    public static class ImagePair {
        public ImageView imageView;
        public String imagePath;

        public ImagePair(ImageView view, String path) {
            imageView = view;
            imagePath = path;
        }
    }


}
