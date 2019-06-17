package com.example.testapp.presentation.util

import android.graphics.Bitmap
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

private val TAG = PicassoSaveImageRunnable::class.java.simpleName

class PicassoSaveImageRunnable(private val bitmap: Bitmap, private val directory: File, private val imageName: String) : Runnable {

    override fun run() {
        val imageFile = File(directory, imageName)
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                fos?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        Log.i(TAG, "image saved to: ${imageFile.absolutePath}")
    }

}