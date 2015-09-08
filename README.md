# image-intent-handler
Easiest way to handle image via camera and gallery intent.


####Features:

- Resizes Image in specific size and folder
- take care of `OutOfMemoryException`
- take care of image rotation while camera capture

####Sample Use:

1) Create an `ImagePair`
``` java
ImageIntentHandler.ImagePair mImagePair;
```

2) Fire intent:

``` java
Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
File f = ImageUtils.createImageFile(ImageUtils.getPackageName(HomeActivity.this));
if ((f != null) && f.exists()) {
  mImagePair = new ImageIntentHandler.ImagePair(mImageView, f.getAbsolutePath());
  takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
  startActivityForResult(takePictureIntent, ImageIntentHandler.REQUEST_CAPTURE);
} else {
  Toast.makeText(HomeActivity.this, "Storage Error", Toast.LENGTH_LONG).show();
}
```
OR
``` java
mImagePair = new ImageIntentHandler.ImagePair(mImageView, null);
Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
startActivityForResult(i, ImageIntentHandler.REQUEST_GALLERY);
```

3) Tell ImageIntentHandler to handle result:
``` java
public void onActivityResult(int requestCode, int resultCode, Intent data) {
  super.onActivityResult(requestCode, resultCode, data);
  ImageIntentHandler intentHandler =
          new ImageIntentHandler(HomeActivity.this, mImagePair)
            .folder("IIH Sample")
            .sizeDp(200);
  intentHandler.handleIntent(requestCode, resultCode, data);
}
```

==================
developed to make programming easy. 

by Himanshu Soni (himanshusoni.me@gmail.com)
