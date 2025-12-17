package com.study.home.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.study.domain.home.model.local.FeatureItem
import com.study.core.theme.AppColor
import com.study.core.theme.AppDimen
import com.study.core.theme.AppTheme
import com.study.core.theme.type.AppTypography

@Composable
fun FeatureCard(
    item: FeatureItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: Painter,
    backgroundColor: Long = item.backgroundColor
) {
    val backgroundColor = Color(item.backgroundColor.toULong())

    Card(
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(AppDimen.dp_16))
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = icon,
                contentDescription = item.title,
                modifier = Modifier
                    .size(AppDimen.dp_100)
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(AppDimen.dp_1)
            ) {
                Text(
                    text = item.title,
                    style = AppTypography.typography.titleMedium,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = item.subtitle,
                    style = AppTypography.typography.bodyMedium.copy(fontSize = 11.sp),
                    color = Color.Gray
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFF5F5F5,
    widthDp = 180,
    heightDp = 140
)
@Composable
fun FeatureCardPreview() {
    AppTheme {
        FeatureCard(
            item = FeatureItem(
                title = "Gia sư AI",
                subtitle = "Hỏi tôi bất cứ điều gì",
                backgroundColor = AppColor.AiTutorBg.value.toLong()
            ),
            onClick = {},
            modifier = Modifier.fillMaxSize(),
            icon = painterResource(com.study.home.R.drawable.ic_task)
        )
    }
}

