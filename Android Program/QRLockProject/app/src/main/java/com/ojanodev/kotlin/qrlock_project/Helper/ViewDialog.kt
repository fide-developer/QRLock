package com.ojanodev.kotlin.qrlock_project.Helper

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.centerCrop
import android.view.Window.FEATURE_NO_TITLE
import android.app.Activity
import android.app.Dialog
import android.view.Window
import android.widget.ImageView
import com.ojanodev.kotlin.qrlock_project.R
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.SizeReadyCallback
import androidx.annotation.NonNull
import android.graphics.drawable.Drawable
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions







class ViewDialog//..we need the context else we can not create the dialog so get context in constructor
    (internal var activity: Activity) {
    lateinit var dialog: Dialog

    fun showDialog() {

        dialog = Dialog(activity)
        dialog.requestWindowFeature(FEATURE_NO_TITLE)
        //...set cancelable false so that it's never get hidden
        dialog.setCancelable(false)
        //...that's the layout i told you will inflate later
        dialog.setContentView(R.layout.loading_layout)
        dialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);


        //...initialize the imageView form infalted layout
      //  val gifImageView = dialog.findViewById<ImageView>(R.id.custom_loading_imageView)

        /*
        it was never easy to load gif into an ImageView before Glide or Others library
        and for doing this we need DrawableImageViewTarget to that ImageView
        */
       // val imageViewTarget = GlideDrawableImageViewTarget(gifImageView)

        //...now load that gif which we put inside the drawble folder here with the help of Glide

//        Glide.with(activity)
//            .asGif()
//            .load(R.drawable.circle_loading)
//            .placeholder(R.drawable.circle_loading)
//            .centerCrop()
//            .into(imageViewTarget)

        //...finaly show it
        dialog.show()
    }

    //..also create a method which will hide the dialog when some work is done
    fun hideDialog() {
        dialog.dismiss()
    }

}