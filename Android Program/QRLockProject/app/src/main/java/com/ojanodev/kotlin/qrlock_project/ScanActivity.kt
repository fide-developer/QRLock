package com.ojanodev.kotlin.qrlock_project

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Matrix
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.HandlerThread
import android.util.Log
import android.util.Rational
import android.view.Surface
import android.view.TextureView
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.ojanodev.kotlin.qrlock_project.Helper.QrCodeAnalyzer
import android.os.Handler
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.util.Executors
import com.ojanodev.kotlin.qrlock_project.Helper.ViewDialog
import com.ojanodev.kotlin.qrlock_project.ViewModel.HakAksesViewModel
import com.ojanodev.kotlin.qrlock_project.ViewModel.PintuViewModel
import com.ojanodev.kotlin.qrlock_project.model.Message
import java.time.LocalDateTime
import java.util.concurrent.Executor

class ScanActivity : AppCompatActivity() {
    companion object {
        private const val REQUEST_CAMERA_PERMISSION = 10
    }
    lateinit var dialog : ViewDialog
    private lateinit var checkOffline : FirebaseDatabase
    private lateinit var vm : PintuViewModel
    private lateinit var textureView: TextureView
    private var delay = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        supportActionBar?.setTitle(getString(R.string.scan_qr))

        vm = ViewModelProviders.of(this)[PintuViewModel::class.java]
        textureView = findViewById(R.id.texture)

        dialog = ViewDialog(this)

        // Request camera permissions
        if (isCameraPermissionGranted()) {
            textureView.post { startCamera() }
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
        }
    }
    private fun startCamera() {
        //set the preview
        val previewConfig = PreviewConfig.Builder()
            .setLensFacing(CameraX.LensFacing.BACK)
            .build()

        val preview = Preview(previewConfig)

        val imageAnalysisConfig = ImageAnalysisConfig.Builder()
            .setTargetAspectRatio(Rational(1,1))
            .apply {
            // Use a worker thread for image analysis to prevent glitches
            val analyzerThread = HandlerThread("BarcodeThread").apply {
                start()
            }
            setCallbackHandler(Handler(analyzerThread.looper))
        }
            .setImageReaderMode(ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE)
            .build()

        val imageAnalysis = ImageAnalysis(imageAnalysisConfig)
        val qrCodeAnalyzer = QrCodeAnalyzer { qrCodes ->

            qrCodes.forEach {

                if(!delay){
                    delay = true
                    Log.d("DELAT",delay.toString())
                    if(!(it.rawValue.isNullOrBlank())){
                        it?.rawValue?.let { it ->
                            dialog.showDialog()
                            //viewmodel
                            vm.getPintuStats(it).observe(this, Observer<ArrayList<Message>>{ message->
                            if(message.size > 0) {
                                if (message[0]?.message.equals("TRUE")) {
                                    dialog.hideDialog()
                                    message[0]?.returnVal?.let {
                                        Log.d("COUNTQR", it)
                                        openPintu(it)
                                    }
                                } else {
                                    if (message[0]?.message.equals("FALSE")) {
                                        dialog.hideDialog()
                                        Snackbar.make(
                                            this.textureView,
                                            "QR Code salah! Mohon untuk pindai QR Code yang tertera pada pintu...",
                                            Snackbar.LENGTH_LONG
                                        ).show()
                                        changeDelayStats()
                                    } else {
                                        if (message[0]?.message.equals("MISS")) {
                                            dialog.hideDialog()
                                            Snackbar.make(
                                                this.textureView,
                                                "Mohon maaf. Anda tidak memiliki hak akses!",
                                                Snackbar.LENGTH_LONG
                                            ).show()
                                            changeDelayStats()
                                        } else {
                                            if (message[0]?.message.equals("KONEKSI")) {
                                                dialog.hideDialog()
                                                Snackbar.make(
                                                    this.textureView,
                                                    "Koneksi Bermasalah!",
                                                    Snackbar.LENGTH_LONG
                                                ).show()
                                                changeDelayStats()
                                            }
                                        }
                                    }
                                }
                            }
                            })
                        }
                    }
                }
            }
        }

        imageAnalysis.analyzer = qrCodeAnalyzer

        preview.setOnPreviewOutputUpdateListener { previewOutput ->
            val parent = textureView.parent as ViewGroup
            parent.removeView(textureView)
            parent.addView(textureView,0)

            textureView.surfaceTexture = previewOutput.surfaceTexture
            updateTransform()
        }

        CameraX.bindToLifecycle(this as LifecycleOwner, preview, imageAnalysis)
    }
    private fun openPintu(code:String){
        val dialog = ViewDialog(this)
        dialog.showDialog()

        Log.d("KODEKODE",code)
        var isEnd = false

        FirebaseDatabase.getInstance().reference.child("Pintu").child(code).addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                loop@ for (data in p0.children){
                    if(data.key.toString().equals("Status")&&data.value.toString().equals("locked")){
                        if(!isEnd){
                            FirebaseDatabase.getInstance().reference.child("Pintu").child(code).child("Status").setValue("Unlocked",{ error, ref ->
                                if(error==null){
                                     FirebaseDatabase.getInstance().reference.child("Pintu").child(code).child("feedback").addValueEventListener(object:ValueEventListener{
                                        override fun onCancelled(p0: DatabaseError) {
                                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                        }

                                        override fun onDataChange(p0: DataSnapshot) {
                                            if(p0.value=="true"){
                                                isEnd = true
                                                FirebaseDatabase.getInstance().reference.child("Pintu").child(code).child("feedback").setValue("false")
                                                android.os.Handler().postDelayed({
                                                    createNotification(code,getString(R.string.notifTitle_pintu_locked),getString(R.string.notif_pintu_locked))
                                                },10000)
                                                createNotification(code,getString(R.string.notifTitle_pintu_unlocked),getString(R.string.notif_pintu_unlocked))
                                                val intent = Intent(this@ScanActivity,HomeActivity::class.java)
                                                dialog.hideDialog()
                                                startActivity(intent)
                                            }else{
                                                android.os.Handler().postDelayed({
                                                    FirebaseDatabase.getInstance().reference.child("Pintu").child(code).child("Status").setValue("locked",{error,ref ->
                                                        if(error == null){
                                                            isEnd = false
                                                            changeDelayStats()
                                                            dialog.hideDialog()
                                                            Snackbar.make(textureView,"System Offline! Mohon hubungi administrator!",Snackbar.LENGTH_INDEFINITE).show()
                                                        }
                                                    })
                                                },7000)
                                            }
                                        }

                                    })
                                }
                            })
                        }
                        break@loop
                    }else{
                        if(data.key.toString().equals("feedback")&&data.value.toString().equals("false")){
                            dialog.hideDialog()
                            Snackbar.make(textureView,"System Offline! Mohon hubungi administrator!",Snackbar.LENGTH_INDEFINITE).show()
                        }
                    }
                }
            }

        })

    }
    fun createNotification(code:String, textTitle:String, message:String){
        val CHANNEL_ID = "channel1"
        val notificationId = 101
        FirebaseDatabase.getInstance().reference.child("Pintu").child(code).child("Status").setValue("locked")


        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.qr_lock)
            .setContentTitle(textTitle)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, builder.build())
        }



    }
    private fun changeDelayStats(){
        android.os.Handler().postDelayed({
            if(delay){
                delay = false
            }
        },3000)
    }
    private fun updateTransform() {
        val matrix = Matrix()

        // Compute the center of the view finder
        val centerX = textureView.width / 2f
        val centerY = textureView.height / 2f

        // Correct preview output to account for display rotation
        val rotationDegrees = when(textureView.display.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }
        matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)

        // Finally, apply transformations to our TextureView
        textureView.setTransform(matrix)
    }

    private fun isCameraPermissionGranted(): Boolean {
        val selfPermission = ContextCompat.checkSelfPermission(baseContext, android.Manifest.permission.CAMERA)
        return selfPermission == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (isCameraPermissionGranted()) {
                textureView.post { startCamera() }
            } else {
                Toast.makeText(this, "Camera permission is required.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}

private fun Any.setCallbackHandler(handler: Handler) {

}
