package com.mkiisoft.iowallet

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.google.zxing.integration.android.IntentIntegrator
import android.content.Intent

class MainActivity : AppCompatActivity() {

    private val logo by lazy { findViewById<AppCompatImageView>(R.id.logo) }
    private val scan by lazy { findViewById<Button>(R.id.scanner) }

    private val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Glide.with(this).load(R.drawable.io_google).into(logo)

        scan.setOnClickListener {
            val integrator = IntentIntegrator(this)
            integrator.setBeepEnabled(false)
            integrator.initiateScan()
        }

        Utils.getTicket(context)?.let {
            startActivity(Intent(context, QrActivity::class.java).putExtra(QrActivity.QR_DATA, it))
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {
                Utils.saveTicket(context, result.contents)
                startActivity(Intent(context, QrActivity::class.java).putExtra(QrActivity.QR_DATA, result.contents))
                finish()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}