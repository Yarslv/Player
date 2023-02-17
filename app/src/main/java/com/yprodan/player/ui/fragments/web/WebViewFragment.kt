package com.yprodan.player.ui.fragments.web

import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.adjust.sdk.webbridge.AdjustBridge
import com.yprodan.player.R
import com.yprodan.player.arch.BaseFragment
import com.yprodan.player.arch.BaseViewModel
import com.yprodan.player.databinding.FragmentWebViewBinding

class WebViewFragment: BaseFragment<FragmentWebViewBinding>(R.layout.fragment_web_view) {
    override val viewModel: BaseViewModel
        get() = TODO("Not yet implemented")

    override fun setObservers() {
        with( binding.webView){
            settings.javaScriptEnabled = true
            webChromeClient = WebChromeClient()
            webViewClient = WebViewClient()
            AdjustBridge.registerAndGetInstance(requireActivity().application, this)
            try {
                loadUrl(BASE_URL)
            }catch (e: Throwable){
                e.printStackTrace()
            }
        }
    }

    override fun onDestroy() {
        AdjustBridge.unregister()
        super.onDestroy()
    }


    companion object{
        const val BASE_URL = "https://www.google.com/"
    }
}