package com.study.home.ui.home.components.header.notifcation

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.size
import androidx.compose.ui.graphics.Color
import com.study.core.theme.AppDimen

@Composable
fun NotificationIcon() {
    Icon(
        painter = painterResource(id = com.study.resources.R.drawable.ic_notification),
        contentDescription = "Notification",
        modifier = Modifier.size(AppDimen.dp_24)
    )
}
