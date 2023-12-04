package com.example.newsapp.View

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.newsapp.Utils.ApiState
import com.example.newsapp.ViewModel.NewsViewModel
import com.example.newswave.model.Article

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsData(viewModel: NewsViewModel, navController: NavHostController) {
    val context= LocalContext.current

    val newsCategory = listOf("business","entertainment","general","health","science","sports","technology")
    var currentNewsCategoryState by remember { mutableStateOf("business") }
    var scrollableState = rememberScrollState()

    Box(modifier = Modifier.fillMaxWidth()) {
        Column {
            NewsHeader(navController)
            Spacer(modifier = Modifier.padding(10.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(scrollableState)
                ) {
                    newsCategory.forEach {
                        NewsCategory(
                            title = it,
                            isSelected = it == currentNewsCategoryState,
                            onValueChange = {
                                currentNewsCategoryState = it
                                viewModel.getHeadLineNews(selectedCategory = it)
                            })
                    }
                }
            }

            GetNewsDataForEachRow(newsViewModel = viewModel)
        }
    }
}


@Composable
fun GetNewsDataForEachRow(newsViewModel: NewsViewModel) {
    when(val result = newsViewModel.headlineResponseData.value) {
        is ApiState.Success -> {
            LazyColumn() {
                items(count = result.newsData.articles.count()) {index: Int ->
                    EachRow( result.newsData.articles[index])
                }
            }
        }

        is ApiState.Failure -> {
            result.throwable.localizedMessage?.let { Text(text = it) }
        }

        is ApiState.Loading -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(50.dp),
                horizontalArrangement = Arrangement.Center

            ) {
                CircularProgressIndicator()
            }
        }

        is ApiState.Empty -> {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EachRow(
    article: Article
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(4.dp),
        onClick = {
            val intent = Intent(context, NewsDetails::class.java)
            intent.putExtra("newsURL", article.url)
            context.startActivity(intent)
        }
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {


            Image(
                painter = rememberAsyncImagePainter(article.urlToImage),
                contentDescription = "dummy",

                Modifier
                    .requiredSize(120.dp)
                    .padding(10.dp)
                    .clip(RoundedCornerShape(10.dp)),

                )
            Column {

                article.title?.let {
                    Text(
                        text = it,
                        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    text = article.publishedAt.substring(0, 10),
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Thin),
                    textAlign = TextAlign.End
                )
            }



//                Text(text = article.content)


        }

    }
//    Card(
//        modifier = Modifier
//            .padding(horizontal = 8.dp, vertical = 8.dp)
//            .fillMaxWidth(),
//        shape = RoundedCornerShape(4.dp),
//        onClick = {
//            val intent = Intent(context, NewsDetails::class.java)
//            intent.putExtra("newsURL", newsSource.url)
//            context.startActivity(intent)
//        }
//    ) {
//
//        Text(
//            text = newsSource.name,
//            modifier = Modifier.padding(10.dp),
//            fontStyle = FontStyle.Italic,
//            fontWeight = FontWeight.Bold,
//            fontSize = 30.sp
//        )
//
//        Text(
//            text = newsSource.description,
//            modifier = Modifier.padding(10.dp),
//            fontStyle = FontStyle.Italic
//        )
//
//        Text(
//            text = "Country: " + newsSource.country.uppercase(),
//            modifier = Modifier.padding(10.dp),
//            fontStyle = FontStyle.Italic
//        )
//    }
}