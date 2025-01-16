@file:OptIn(ExperimentalForeignApi::class, ExperimentalComposeUiApi::class)

package kz.witme.project.web_view.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitInteropProperties
import androidx.compose.ui.viewinterop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.UIKit.UIWebView

@Composable
internal actual fun WebView(url: String) {
    val webView = remember { UIWebView() }
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
            isInteractive = true,
            isNativeAccessibilityEnabled = true
        )
    )
}