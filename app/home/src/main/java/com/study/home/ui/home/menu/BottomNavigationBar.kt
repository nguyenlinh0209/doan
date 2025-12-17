package com.study.home.ui.home.menu

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.study.home.model.BottomNavItem
import com.study.home.navigation.Screen

@Composable
fun BottomNavigationBar(
    currentScreen: Screen,
    onScreenSelected: (Screen) -> Unit
) {
    val items = listOf(
        BottomNavItem(Screen.Home, "Trang chủ", "🏠"),
        BottomNavItem(Screen.Calendar, "Lịch", "📅"),
        BottomNavItem(Screen.Camera, "Camera", "📸"),
        BottomNavItem(Screen.Profile, "Cá nhân", "👤")
    )

    NavigationBar(
        containerColor = Color.White,
        contentColor = Color.Black
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentScreen == item.screen,
                onClick = { onScreenSelected(item.screen) },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 10.sp
                    )
                },
                icon = {
                    Text(item.icon, fontSize = 20.sp)
                },
                alwaysShowLabel = true
            )
        }
    }
}