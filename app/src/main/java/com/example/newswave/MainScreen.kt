package com.example.newswave

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.example.newswave.ui.theme.NewsWaveTheme
import com.google.android.material.slider.Slider
import kotlin.random.Random


data class NewsArticle(val id: Int, val title: String, val content: String)

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NewsAppContent(newsList: List<NewsArticle>, pagerState: PagerState) {


    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {

                TopAppBar(
                    title = { Text(text = "NewsWave") },
                    actions = {
//
                    }, modifier = Modifier.background(Color(0xFF0F9D58)),

                )




        // Top Slider (Carousel)
        NewsCarousel(newsList, pagerState)

        Spacer(modifier = Modifier.height(5.dp))

        // Search Bar
        // Search Bar
        OutlinedTextField(
            value = "",
            onValueChange = { /* Handle search input change */ },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onDone = { /* Handle search action */ }
            ),

            placeholder = { Text("Search for news...") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5))
                .padding(16.dp)
                .height(56.dp)
        )


        Spacer(modifier = Modifier.height(5.dp))

        // List of News Cards
        NewsList(newsList)
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NewsCarousel(newsList: List<NewsArticle>, pagerState: PagerState) {
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxWidth(),
        pageCount = newsList.size
    ) { page ->
        NewsCarouselItem(newsList[page])
    }
}

@Composable
fun NewsCarouselItem(article: NewsArticle) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(16.dp)
    ) {

        Image(
            painter = painterResource(id = R.drawable.news_png), contentDescription = "news png",
            Modifier
                .fillMaxWidth()
                .height(250.dp), Alignment.Center,
            contentScale = ContentScale.Crop
        )
//        Column {
//            Text(
//                text = article.title,
//                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(text = article.content)
//        }
    }
}

@Composable
fun NewsList(newsList: List<NewsArticle>) {
    LazyColumn {
        items(newsList) { article ->
            NewsCard(article)
            Divider()
        }
    }
}

@Composable
fun NewsCard(article: NewsArticle) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        Row {
            Image(
                painter = painterResource(id = R.drawable.news_logo),
                contentDescription = "dummy",
                Modifier
                    .size(100.dp)
                    .padding(10.dp),
            )


            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = article.title,
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = article.content)
            }

        }

    }
}

private fun generateDummyNews(): List<NewsArticle> {
    val dummyNews = mutableListOf<NewsArticle>()
    for (i in 1..10) {
        dummyNews.add(
            NewsArticle(
                id = i,
                title = "News Title $i",
                content = "This is the content of news article $i."
            )
        )
    }
    return dummyNews
}

@Preview
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen() {
    val newsList = remember { generateDummyNews() }
    val pagerState = rememberPagerState(newsList.size)


//    WindowCompat.setDecorFitsSystemWindows(window, false)

    NewsAppContent(newsList, pagerState)
}