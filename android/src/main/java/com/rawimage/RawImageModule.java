package com.rawimage;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.BitmapDrawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;

@ReactModule(name = RawImageModule.NAME)
public class RawImageModule extends ReactContextBaseJavaModule {
  public static final String NAME = "RawImage";

  public RawImageModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }

  @ReactMethod
  public void rawToPng(String path, Promise promise) {
    final Activity activity = getCurrentActivity();

    if (activity == null) {
      promise.resolve(null);
    }

    Glide.with(activity.getApplicationContext())
        .load(path)
        .override(Target.SIZE_ORIGINAL)
        .into(new CustomTarget<Drawable>() {
          @Override
          public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
            Bitmap image = ((BitmapDrawable) resource).getBitmap();

            String baseDirectory = activity.getApplicationContext().getCacheDir().getAbsolutePath();
            final File directory = new File(baseDirectory);

            final String uniqueId = UUID.randomUUID().toString();
            final String fileName = "photo-" + uniqueId + ".png";

            File imageFile = new File(directory, fileName);

            try {
              OutputStream fOut = new FileOutputStream(imageFile);
              image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
              fOut.flush();
              fOut.close();

              String savedImagePath = imageFile.getAbsolutePath();
              promise.resolve(savedImagePath);
            } catch (Exception e) {
              e.printStackTrace();
              promise.resolve(null);
            }
          }

          @Override
          public void onLoadCleared(@Nullable Drawable placeholder) {

          }

          @Override
          public void onLoadFailed(@Nullable Drawable errorDrawable) {
            super.onLoadFailed(errorDrawable);
            promise.resolve(null);
          }
        });
  }
}
