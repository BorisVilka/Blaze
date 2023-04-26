package com.blaze.crash.bleze

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.webkit.*
import com.blaze.crash.bleze.databinding.ActivityWebBinding

class BlazeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebBinding
    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebBinding.inflate(layoutInflater)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(binding.root)
        webView = binding.web
        if(savedInstanceState!=null)
            webView.restoreState(savedInstanceState.getBundle("ViewState")!!)
        else {
            val cookieManager = CookieManager.getInstance()
            cookieManager.setAcceptCookie(true)
            val settings = webView.settings
            settings.setRenderPriority(WebSettings.RenderPriority.HIGH)
            settings.cacheMode = WebSettings.LOAD_NO_CACHE
            settings.setGeolocationEnabled(true)
            settings.allowContentAccess = true
            settings.blockNetworkLoads = false
            settings.blockNetworkImage = false
            settings.safeBrowsingEnabled = true
            settings.loadWithOverviewMode = true
            settings.setSupportMultipleWindows(false)
            settings.offscreenPreRaster = true
            settings.useWideViewPort = true
            settings.loadWithOverviewMode = true
            settings.builtInZoomControls = false
            settings.displayZoomControls = false
            settings.setAppCacheEnabled(true)
            settings.domStorageEnabled = true
            settings.databaseEnabled = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.javaScriptEnabled = true

            webView.webViewClient = object : WebViewClient() {

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    view!!.loadUrl(request!!.url.toString())
                    return true
                }
            }
            Log.d("TAG",getSharedPreferences("prefs", MODE_PRIVATE).getString("url","")!!)
            binding.web.loadUrl(getSharedPreferences("prefs", MODE_PRIVATE).getString("url","")!!)
        }
    }

    override fun onBackPressed() {
        if(webView.canGoBack()) webView.goBack()
        else super.onBackPressed()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val bundle = Bundle()
        webView.saveState(bundle)
        outState.putBundle("ViewState",bundle)
    }
}


