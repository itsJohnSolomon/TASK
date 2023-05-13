package com.jojo.cloudintask.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.jojo.cloudintask.R
import com.jojo.cloudintask.databinding.ActivityDetailsBinding
import com.jojo.cloudintask.databinding.ActivityMainBinding
import com.jojo.cloudintask.databinding.ActivitySplashScreenBinding
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.SimpleDateFormat
import java.util.*

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    lateinit var bacBtn: ImageView
    lateinit var title: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bacBtn = findViewById(R.id.backBtn)
        title = findViewById(R.id.pageName)
        bacBtn.setImageResource(R.drawable.ic_back_arrow)
        title.text = "USER DETAILS"
        val jsonString = intent.getStringExtra("myModel")
        val gson = Gson()
        val myModel = gson.fromJson(jsonString, com.jojo.cloudintask.models.Result::class.java)
        Log.e("myList",myModel.email);

        bacBtn.setOnClickListener {
            finish()
            Log.e("myList","myModel.email");
        }
        Log.e("myList",myModel.picture.large);
        Glide.with(this).load(myModel.picture.large).into(binding.userImage)

        binding.name.text=myModel.name.title.trim()+" "+myModel.name.first.trim()+" "+myModel.name.last.trim()
        binding.phoneNumber.text=myModel.phone
        binding.email.text=myModel.email
        binding.gender.text=myModel.gender

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.US)
        val date = inputFormat.parse(myModel.dob.date)
        val outputDate = outputFormat.format(date)
        binding.dob.text=outputDate
        binding.street.text=myModel.location.street.name+" "+myModel.location.street.number
        binding.city.text=myModel.location.city
        binding.state.text=myModel.location.state
        binding.country.text=myModel.location.country
        binding.postalCode.text=myModel.location.postcode

        binding.btnCall.setOnClickListener{
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:${myModel.phone}"))
                startActivity(intent)
        }
        binding.btnEmail.setOnClickListener{
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.type = "text/plain"
            emailIntent.putExtra(Intent.EXTRA_EMAIL,  arrayOf(myModel.email))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject of the email")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Body of the email")
            emailIntent.setPackage("com.google.android.gm")
            startActivity(Intent.createChooser(emailIntent, "Choose an email client:"))
        }
        binding.btnViewMap.setOnClickListener{
            val latitude = myModel.location.coordinates.latitude
            val longitude = myModel.location.coordinates.longitude
            val label = myModel.location.city
            val uri = "geo:$latitude,$longitude?q=$latitude,$longitude($label)"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            startActivity(intent)
        }



    }
}