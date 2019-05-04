package com.mkiisoft.iowallet

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

class QrActivity : AppCompatActivity() {

    private val qrCode by lazy { findViewById<AppCompatImageView>(R.id.qrcode) }
    private val remove by lazy { findViewById<LinearLayout>(R.id.remove) }
    private val location by lazy { findViewById<LinearLayout>(R.id.location) }

    private val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr)

        val qrData = intent.extras?.let { it[QR_DATA] as String }

        try {
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.encodeBitmap(qrData, BarcodeFormat.QR_CODE, 600, 600)
            qrCode.setImageBitmap(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        remove.setOnClickListener {
            Utils.deleteTicket(context)
            startActivity(Intent(context, MainActivity::class.java))
            finish()
        }

        location.setOnClickListener {
            Utils.openMap(context)
        }
    }

    companion object {
        const val QR_DATA = "qr_data"
    }
}