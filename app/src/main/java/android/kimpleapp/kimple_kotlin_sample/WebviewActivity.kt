package android.kimpleapp.kimple_kotlin_sample

import android.annotation.TargetApi
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.webkit.*
import android.webkit.WebChromeClient.FileChooserParams
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class WebviewActivity : AppCompatActivity() {
    var url: String? = null
    var uploadMessage: ValueCallback<Array<Uri>>? = null
    private var mUploadMessage: ValueCallback<Uri>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        val hashid = intent.getStringExtra("hashId")
        val webview = findViewById<WebView>(R.id.webview)
        url = "https://kx1.co/contest-$hashid"
        webview.getSettings().javaScriptEnabled = true
        webview.getSettings().mediaPlaybackRequiresUserGesture = false
        webview.getSettings().loadWithOverviewMode = true
        webview.getSettings().useWideViewPort = true
        webview.getSettings().domStorageEnabled = true
        WebView.setWebContentsDebuggingEnabled(true)

        /*
        pour fichiers
         */webview.getSettings().allowContentAccess = true
        webview.getSettings().allowFileAccess = true
        webview.setWebChromeClient(object : WebChromeClient() {
            // For Lollipop 5.0+ Devices
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onShowFileChooser(
                mWebView: WebView,
                filePathCallback: ValueCallback<Array<Uri>>,
                fileChooserParams: FileChooserParams
            ): Boolean {
                if (uploadMessage != null) {
                    uploadMessage!!.onReceiveValue(null)
                    uploadMessage = null
                }
                uploadMessage = filePathCallback
                val intent = fileChooserParams.createIntent()
                try {
                    startActivityForResult(intent, 100)
                } catch (e: ActivityNotFoundException) {
                    uploadMessage = null
                    return false
                }
                return true
            }

            protected fun openFileChooser(uploadMsg: ValueCallback<Uri>?) {
                mUploadMessage = uploadMsg
                val i = Intent(Intent.ACTION_GET_CONTENT)
                i.addCategory(Intent.CATEGORY_OPENABLE)
                i.type = "image/*"
                startActivityForResult(
                    Intent.createChooser(i, "File Chooser"),
                    1
                )
            }
        })
        webview.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView, url: String) {}
            override fun onReceivedSslError(
                view: WebView,
                handler: SslErrorHandler,
                error: SslError
            ) {
                handler.proceed() // Ignore SSL certificate errors
            }
        })
        webview.loadUrl(url!!)
    }
}