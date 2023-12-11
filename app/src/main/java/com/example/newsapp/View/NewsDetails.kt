package com.example.newsapp.View

import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.newsapp.View.ui.theme.NewsAppTheme

class NewsDetails : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var url = intent.getStringExtra("newsURL")
        setContent {
            NewsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column {
                        TopAppBar(
                            title = {
                                Text(text = "NewsWave", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            },
                            navigationIcon = {
                                IconButton(onClick = {
                                    onBackPressed()

                                }) {
                                    Icon(Icons.Filled.ArrowBack, null)

                                }
                            },

                            )


                        if (url != null) {
                        if (url!!.startsWith("http://")) {
                            url = url!!.replace("http://", "https://")
                        }
                    }

                    url?.let { WebViewPage(url = it) }
                    }


                }
            }
        }
    }
}

@Composable
fun WebViewPage(url: String) {
    
    AndroidView(factory = {
        WebView(it).apply {

            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            loadUrl(url)
        }
    },
        update = {
            it.loadUrl(url)
    })
}
