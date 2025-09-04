package com.example.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.ui.theme.NewsAppTheme

// Colores del diseño
private val Purple = Color(0xFF6A5ACD)       // Morado
private val ChipGray = Color(0xFFE0E0E0)     // Gris chip inferior
private val CardGray = Color(0xFFF0F0F0)     // Placeholder gris
private val PageBg = Color(0xFFF7F7FA)       // Fondo general

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = PageBg) {
                    NewsScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen() {
    var search by remember { mutableStateOf("") }
    val tabs = listOf("Noticias", "Eventos", "Clima")
    var selectedTab by remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {

        Spacer(Modifier.height(12.dp))

        // Barra de búsqueda con lupa
        OutlinedTextField(
            value = search,
            onValueChange = { search = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Buscar") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Buscar",
                    tint = Color.Gray
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(28.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Purple,
                unfocusedBorderColor = Color(0xFFBDBDBD),
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        Spacer(Modifier.height(14.dp))

        // Tabs funcionales
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = PageBg,
            contentColor = Color.Black,
            indicator = { positions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier
                        .tabIndicatorOffset(positions[selectedTab])
                        .height(3.dp),
                    color = Purple
                )
            },
            divider = {}
        ) {
            tabs.forEachIndexed { i, label ->
                Tab(
                    selected = selectedTab == i,
                    onClick = { selectedTab = i },
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Color(0xFFBDBDBD),
                    text = {
                        Text(
                            text = label,
                            fontWeight = if (selectedTab == i) FontWeight.Bold else FontWeight.SemiBold,
                            fontSize = 20.sp
                        )
                    }
                )
            }
        }

        Spacer(Modifier.height(10.dp))

        // Contenido para Noticias
        if (selectedTab == 0) {
            // "Ultimas noticias"
            Text(
                "Ultimas noticias",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 6.dp),
                color = Color.Black
            )

            LazyRow(contentPadding = PaddingValues(end = 16.dp)) {
                item {
                    NewsCardHorizontal(
                        title = "El presidente de EE.UU. no muestra signos de arrepentimiento...",
                        date = "febrero 08 – 2024"
                    )
                }
                item {
                    NewsCardHorizontal(
                        title = "Bañarse en la piscina del desierto de Cleopatra",
                        date = "febrero 10 – 2024"
                    )
                }
                item {
                    NewsCardHorizontal(
                        title = "Gigantes tecnológicos",
                        date = "febrero 12 – 2024"
                    )
                }
            }

            Spacer(Modifier.height(10.dp))

            // "Alrededor del mundo"
            Text(
                "Alrededor del mundo",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 6.dp),
                color = Color.Black
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val titles = listOf(
                    "El presidente de EE.UU. no muestra signos de arrepentimiento...",
                    "Bañarse en la piscina del desierto de Cleopatra",
                    "Gigantes tecnológicos",
                    "El rover de Marte envía"
                )
                items(titles) { t -> NewsCardVertical(title = t) }
            }
        }
    }
}

// 🔹 Tarjetas horizontales moradas
@Composable
fun NewsCardHorizontal(title: String, date: String) {
    Box(
        modifier = Modifier
            .padding(end = 14.dp)
            .size(width = 280.dp, height = 160.dp)
            .clip(RoundedCornerShape(26.dp))
            .background(Purple),
        contentAlignment = Alignment.BottomStart
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                title,
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                lineHeight = 22.sp
            )
            Spacer(Modifier.height(8.dp))
            Text(date, color = Color.White, fontSize = 13.sp)
        }
    }
}

// 🔹 Tarjetas verticales (gris, sin imagen)
@Composable
fun NewsCardVertical(title: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(CardGray)
            .aspectRatio(0.80f) // más alto para evitar huecos
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(10.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(ChipGray)
                .padding(horizontal = 12.dp, vertical = 10.dp)
        ) {
            Text(
                title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Color.Black
            )
        }
    }
}
