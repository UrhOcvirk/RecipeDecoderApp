package com.example.recipedecoderapp

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipedecoderapp.ui.theme.RecipeDecoderAppTheme
import androidx.core.view.WindowCompat
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.core.view.WindowInsetsControllerCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        window.statusBarColor = 0xFF2F2F2F.toInt()
        window.navigationBarColor = 0xFF2F2F2F.toInt()

        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightNavigationBars = false

        setContent {
            RecipeDecoderAppTheme {
                Scaffold (
                    contentWindowInsets = WindowInsets.systemBars,
                ) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var smallButtonLabels by remember { mutableStateOf(listOf("", "", "")) }
    val context = LocalContext.current
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF2F2F2F))
    ) {
        Text(
            text = "GET A MEAL IDEA",
            color = Color.White,
            fontSize = 25.sp,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Button(
                onClick = { smallButtonLabels = MealRepository.getRandomMeals(3) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A1A1A)),
                shape = CircleShape,
                modifier = Modifier.size(275.dp)
            ) {
                Text(text = "GET IDEA", color = Color.White, fontSize = 25.sp)
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                smallButtonLabels.forEach { mealText ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = { openWebPage(context, "https://recipedecoder-cdg8gqe8aggxbmcs.northeurope-01.azurewebsites.net/") },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A1A1A)),
                            modifier = Modifier.size(width = 200.dp, height = 75.dp)
                        ) {
                            Text(text = mealText, color = Color.White, fontSize = 15.sp)
                        }
                        Button(
                            onClick = {
                                copyTextToClipboard(context, mealText)
                                Toast.makeText(context, "Copied \"$mealText\"", Toast.LENGTH_SHORT).show()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A1A1A)),
                            shape = CircleShape,
                            modifier = Modifier.size(75.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ContentCopy,
                                contentDescription = "Copy",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

fun copyTextToClipboard(context: Context, text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Meal Suggestion", text)
    clipboard.setPrimaryClip(clip)
}

fun openWebPage(context: Context, url: String) {
    val uri = Uri.parse(url)
    val intent = Intent(Intent.ACTION_VIEW, uri)
    context.startActivity(intent)
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    RecipeDecoderAppTheme {
        Surface {
            MainScreen()
        }
    }
}
