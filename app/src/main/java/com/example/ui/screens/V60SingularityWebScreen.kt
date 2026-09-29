package com.example.ui.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.ViewGroup
import android.webkit.RenderProcessGoneDetail
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import java.io.File
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.ui.theme.PoolAqua
import com.example.ui.theme.PoolBorder
import com.example.ui.theme.PoolBorderGold
import com.example.ui.theme.PoolDarkBg
import com.example.ui.theme.PoolGold
import com.example.ui.theme.PoolGreen
import com.example.ui.theme.PoolSurface
import com.example.ui.theme.PoolSurfaceElevated
import com.example.ui.theme.PoolTextMuted
import com.example.ui.theme.PoolTextPrimary
import com.example.ui.theme.PoolTextSecondary

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun V60SingularityWebScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var webViewRef by remember { mutableStateOf<WebView?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var currentUrl by remember { mutableStateOf("file:///android_asset/v60_platform.html") }
    var isHqActive by remember { mutableStateOf(false) }

    BackHandler {
        if (webViewRef?.canGoBack() == true) {
            webViewRef?.goBack()
        } else {
            onNavigateBack()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PoolDarkBg)
    ) {
        // Top Singularity Controller Bar
        Card(
            shape = RoundedCornerShape(0.dp),
            colors = CardDefaults.cardColors(containerColor = PoolSurface),
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 1.dp, color = PoolBorder)
        ) {
            Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = onNavigateBack,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = PoolTextPrimary
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(PoolGreen)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "V60 OMNI-SINGULARITY",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Black,
                                    color = PoolGold
                                )
                            }
                            Text(
                                text = "Titan Leaves™ · World Markets Linked",
                                fontSize = 10.sp,
                                color = PoolTextSecondary
                            )
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Secret Empire HQ trigger
                        IconButton(
                            onClick = {
                                isHqActive = !isHqActive
                                val target = if (isHqActive) "file:///android_asset/v60_platform.html?access=K17412_TITAN" else "file:///android_asset/v60_platform.html"
                                currentUrl = target
                                webViewRef?.loadUrl(target)
                            },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Security,
                                contentDescription = "Empire HQ",
                                tint = if (isHqActive) PoolGold else PoolTextMuted
                            )
                        }

                        IconButton(
                            onClick = { webViewRef?.reload() },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Reload",
                                tint = PoolTextPrimary
                            )
                        }

                        IconButton(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://ais-dev-hovuo3yz75o2jlyzziavm3-161436339311.europe-west2.run.app/"))
                                context.startActivity(intent)
                            },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.OpenInBrowser,
                                contentDescription = "Open Browser",
                                tint = PoolAqua
                            )
                        }
                    }
                }

                // Quick Switcher inside Web View
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            webViewRef?.evaluateJavascript("switchView('v60');", null)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PoolSurfaceElevated),
                        modifier = Modifier
                            .weight(1f)
                            .height(30.dp),
                        shape = RoundedCornerShape(15.dp)
                    ) {
                        Text("⚛️ V60 Singularity", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PoolAqua)
                    }

                    Button(
                        onClick = {
                            webViewRef?.evaluateJavascript("switchView('world');", null)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PoolSurfaceElevated),
                        modifier = Modifier
                            .weight(1f)
                            .height(30.dp),
                        shape = RoundedCornerShape(15.dp)
                    ) {
                        Text("🌍 World Markets", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PoolGold)
                    }
                }
            }
        }

        if (isLoading) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                color = PoolGold,
                trackColor = PoolSurface
            )
        }

        // The Web View embedding the live unified V60 platform
        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = { ctx ->
                    try {
                        val webViewCacheDir = File(ctx.cacheDir, "WebView")
                        if (!webViewCacheDir.exists()) {
                            webViewCacheDir.mkdirs()
                        }
                    } catch (_: Exception) {}

                    WebView(ctx).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        // Bypasses Mesa DRM render node search in emulator/container environment
                        setLayerType(android.view.View.LAYER_TYPE_SOFTWARE, null)
                        settings.apply {
                            javaScriptEnabled = true
                            domStorageEnabled = true
                            loadWithOverviewMode = true
                            useWideViewPort = true
                            builtInZoomControls = true
                            displayZoomControls = false
                            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                            cacheMode = WebSettings.LOAD_NO_CACHE
                        }
                        webViewClient = object : WebViewClient() {
                            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                                super.onPageStarted(view, url, favicon)
                                isLoading = true
                            }

                            override fun onPageFinished(view: WebView?, url: String?) {
                                super.onPageFinished(view, url)
                                isLoading = false
                            }

                            override fun onReceivedError(
                                view: WebView?,
                                request: WebResourceRequest?,
                                error: WebResourceError?
                            ) {
                                super.onReceivedError(view, request, error)
                                if (request?.isForMainFrame == true && !currentUrl.startsWith("file:///android_asset/")) {
                                    currentUrl = "file:///android_asset/v60_platform.html"
                                    view?.loadUrl(currentUrl)
                                }
                            }

                            override fun onRenderProcessGone(
                                view: WebView?,
                                detail: RenderProcessGoneDetail?
                            ): Boolean {
                                try {
                                    view?.let { wv ->
                                        val parent = wv.parent as? ViewGroup
                                        parent?.removeView(wv)
                                        wv.destroy()
                                    }
                                } catch (_: Throwable) {}
                                return true
                            }
                        }
                        webChromeClient = WebChromeClient()
                        loadUrl(currentUrl)
                        webViewRef = this
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
