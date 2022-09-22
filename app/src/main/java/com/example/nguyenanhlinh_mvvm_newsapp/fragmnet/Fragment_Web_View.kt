package com.example.nguyenanhlinh_mvvm_newsapp.fragmnet

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.nguyenanhlinh_mvvm_newsapp.R
import kotlinx.android.synthetic.main.fragment_web_view.*

class Fragment_Web_View : Fragment(R.layout.fragment_web_view) {
    val args: Fragment_Web_ViewArgs by navArgs()
    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val article = args.article
        webView.apply {
            webViewClient = WebViewClient()
            article!!.url.let {
                settings.domStorageEnabled = true
                settings.javaScriptEnabled = true
                settings.databaseEnabled = true
                loadUrl(it)
            }
        }

    }
}