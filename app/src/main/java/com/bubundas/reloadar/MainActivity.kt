package com.bubundas.reloadar

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.IntentFilter
import android.os.PowerManager
import android.content.BroadcastReceiver
import android.content.Context
import android.os.Handler
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import android.webkit.WebViewClient




@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        webView.loadUrl("https://blockchain.info/address/3M1n7K1yMa2zwGobtwtj6ekbd57nM8g4Nx")
//        webView.loadUrl("https://google.com")
        webView.settings.javaScriptEnabled = true
        webView.setWebViewClient(WebViewClient())
        val ha = Handler()
        ha.postDelayed({ webView.reload() }, 10000)

        loadBtn.setOnClickListener {
            if (textView.text.toString() == ""){
                textView.requestFocusFromTouch()
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(textView, InputMethodManager.SHOW_IMPLICIT)
                return@setOnClickListener Toast.makeText(this@MainActivity, "Please Enter Something", Toast.LENGTH_SHORT).show()
            } else {
                webView.loadUrl(textView.text.toString())
            }
        }
        val vReceiver = object : BroadcastReceiver() {

            @SuppressLint("LongLogTag", "WakelockTimeout", "SetJavaScriptEnabled")
            override fun onReceive(context: Context, intent: Intent) {
                val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
                val isScreenOn = powerManager.isScreenOn


                if (!isScreenOn) {
                    val pm = applicationContext.getSystemService(Context.POWER_SERVICE) as PowerManager
                    val mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP, "YourService")
                    mWakeLock.acquire()
                    Log.e("Do what you want to do from this service...", "Here screen is off..which do not show or work anything due to screen is off")

                } else {
                    Log.e("Do what you want to do from this service...", "Here screen is on, works...")
                }
            }

        }
        registerReceiver(vReceiver, IntentFilter("android.media.VOLUME_CHANGED_ACTION"))
    }
}
