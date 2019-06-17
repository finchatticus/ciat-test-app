package com.example.testapp.presentation.util

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.io.File


class PicassoSaveLocalTarget(private val directory: File, private val imageName: String) : Target {

    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

    }

    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {

    }

    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
        bitmap?.run {
            Thread(PicassoSaveImageRunnable(this, directory, imageName)).start()
        }
    }

}