package com.example.newsapp.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.newsapp.R
import com.example.newswave.DestinationScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NewsHeader(navController: NavHostController) {
    TopAppBar(
        title = {
            Text(text = "NewsWave", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        },
        navigationIcon = {
            IconButton(onClick = {/* Do Something*/ }) {
//                Icon(Icons.Filled.Home, null)
                Image(
                    painter = painterResource(id = R.drawable.news_logo),
                    contentDescription = null,
                    Modifier.size(24.dp)
                )
            }
        },
        actions = {
            Button(
                onClick = {
                    val authHelper = FirebaseAuth.getInstance()
                    authHelper.signOut()
                    navController.navigate(DestinationScreen.LoginScreenDest.route){
                        popUpTo(route = DestinationScreen.MainScreenDest.route) {
                            inclusive = true
                        }
                    }



                }, colors = ButtonDefaults.buttonColors(Color.Red)
            ) {
                Text(text = "Logout", color = Color.White)
            }
        },
        backgroundColor = Color(0xFFE4E2E2)
    )
}