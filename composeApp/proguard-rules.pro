# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Keep WebView-related classes and methods
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void onPageStarted(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public void onPageFinished(android.webkit.WebView, java.lang.String);
}

-keepclassmembers class * extends android.webkit.WebChromeClient {
    public void onProgressChanged(android.webkit.WebView, int);
}

# Keep WebView methods if JavaScript is enabled
-keepclassmembers class * {
    public void addJavascriptInterface(java.lang.Object, java.lang.String);
}

# Keep settings if used (like javaScriptEnabled)
-keepclassmembers class android.webkit.WebSettings {
    public *;
}

# Keep any WebView implementations
-keep class android.webkit.** { *; }

# Keep data models
-keep class kz.witme.project.service.auth.data.model.* {
    *;
}

-keep class kz.witme.project.service.profile.data.model.* {
    *;
}

-keep class kz.witme.project.service.book.data.model.* {
    *;
}

# Keep Ktor classes and metadata
-keep class io.ktor.** { *; }

-keepclassmembers class io.ktor.** { *; }

-dontwarn io.ktor.**
