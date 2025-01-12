package kz.witme.project.web_view.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.UIKit.UIView
import platform.WebKit.WKWebView

@Composable
internal actual fun WebView(url: String) {
    val webView = remember { WKWebView() }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        UIKitView(
            modifier = Modifier.matchParentSize(),
            factory = {
                val container = UIView()
                webView.loadRequest(NSURLRequest(NSURL(string = url)))
                container.addSubview(webView)
                container
            }
        )
    }
}