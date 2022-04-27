package com.example.email

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tempImageUri = initTempUri()
        registerTakePictureLauncher(tempImageUri)
        registerSendEmailLauncher(tempImageUri)
    }

    private fun initTempUri(): Uri {
        val tempImagesDir = File(applicationContext.filesDir, getString(R.string.temp_images_dir))
        tempImagesDir.mkdir()
        val tempImage = File(tempImagesDir, getString(R.string.temp_image))

        return FileProvider.getUriForFile(applicationContext, getString(R.string.authorities), tempImage)
    }

    private fun registerTakePictureLauncher(path: Uri) {
        val button = findViewById<Button>(R.id.button)
        val imageView = findViewById<ImageView>(R.id.imageView)

        val resultLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) {
            imageView.setImageURI(null)
            imageView.setImageURI(path)
        }

        button.setOnClickListener {
            resultLauncher.launch(path)
        }
    }

    private fun registerSendEmailLauncher(path: Uri) {
        val button = findViewById<Button>(R.id.button2)

        button.setOnClickListener() {
            val email = "hodovychenko.labs@gmail.com"
            val subject = "КПП АІ-195 Урбанович"
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.type = "plain/text"
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            emailIntent.putExtra(Intent.EXTRA_STREAM, path)
            this.startActivity(Intent.createChooser(emailIntent, "Sending email..."))
        }

    }

}