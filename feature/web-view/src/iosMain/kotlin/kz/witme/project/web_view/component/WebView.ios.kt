@file:OptIn(ExperimentalComposeUiApi::class)

package kz.witme.project.web_view.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitInteropInteractionMode
import androidx.compose.ui.viewinterop.UIKitInteropProperties
import androidx.compose.ui.viewinterop.UIKitView
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.WebKit.WKWebView

@Composable
internal actual fun WebView(url: String) {
    val webView = remember {
        WKWebView().apply {
            this.scrollView.scrollEnabled = true // Enable scrolling explicitly
            this.scrollView.bounces = true      // Enable bounce effect for better UX
        }
    }
    UIKitView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            webView
        },
        update = {
            webView.loadRequest(
                NSURLRequest(
                    NSURL(string = url)
                )
            )
        },
        properties = UIKitInteropProperties(
            interactionMode = UIKitInteropInteractionMode.NonCooperative,
            isNativeAccessibilityEnabled = true
        )
    )
}