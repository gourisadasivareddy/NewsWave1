package com.example.newsapp.View

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NewsCategory(
    title: String,
    isSelected: Boolean,
    onValueChange:(String) -> Unit
) {
    TextButton(onClick = { 
        onValueChange(title)
    },
        shape = RoundedCornerShape(100.dp),
        elevation = ButtonDefaults.buttonElevation(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) {
                Color(70,111,174)
            } else Color.Transparent,
        )

    ) {
        Text(text = title, style = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = if (isSelected) Color.White else Color.Black
        ))
    }
}