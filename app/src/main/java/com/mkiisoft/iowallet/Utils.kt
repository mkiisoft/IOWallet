package com.mkiisoft.iowallet

import android.content.Context
import androidx.core.content.ContextCompat.startActivity
import android.content.Intent
import android.net.Uri


object Utils {

    private const val WALLET = "wallet"
    private const val TICKET = "ticket"

    fun saveTicket(context: Context, value: String) {
        val shared = context.getSharedPreferences(WALLET, Context.MODE_PRIVATE)
        val editor = shared.edit()
        editor.putString(TICKET, value)
        editor.apply()
    }

    fun getTicket(context: Context): String? {
        return context.getSharedPreferences(WALLET, Context.MODE_PRIVATE).getString(TICKET, null)
    }

    fun deleteTicket(context: Context) {
        val shared = context.getSharedPreferences(WALLET, Context.MODE_PRIVATE)
        val editor = shared.edit()
        editor.remove(TICKET)
        editor.apply()
    }

    fun openMap(context: Context) {
        val gmmIntentUri = Uri.parse("geo:37.4268342,-122.0807023")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        if (mapIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(mapIntent)
        }
    }
}