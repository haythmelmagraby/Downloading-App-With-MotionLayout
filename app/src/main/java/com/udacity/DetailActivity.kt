package com.udacity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)

        val fileName = intent.getStringExtra("file_name")
        val state = intent.getStringExtra("status")

        findViewById<TextView>(R.id.fileNameId).text = fileName
        findViewById<TextView>(R.id.statusId).text = state

        button.setOnClickListener{
            motion_layout.transitionToEnd()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }



    }

}
