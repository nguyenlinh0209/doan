package com.study.home.ui.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.osprey.domain.home.model.local.LearningItem
import com.study.core.theme.AppColor
import com.study.core.theme.AppDimen
import com.study.core.theme.type.AppTypography

@Composable
fun HotActivitySection(items: List<LearningItem>) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppDimen.dp_16)
    ) {

        Text(
            text = "Hoạt động gần đây",
            style = AppTypography.typography.titleMedium,
            modifier = Modifier.padding(bottom = AppDimen.dp_12)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(AppDimen.dp_120),
            horizontalArrangement = Arrangement.spacedBy(AppDimen.dp_12)
        ) {
            items.take(3).forEach { item ->
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    colors = CardDefaults.cardColors(
                        containerColor = AppColor.Background
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(AppDimen.dp_12),
                        verticalArrangement = Arrangement.spacedBy(AppDimen.dp_8)
                    ) {
                        Text(
                            text = item.title,
                            style = AppTypography.typography.labelMedium,
                            maxLines = 1
                        )
                        Text(
                            text = item.type,
                            style = AppTypography.typography.labelSmall,
                            color = Color.Gray,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}