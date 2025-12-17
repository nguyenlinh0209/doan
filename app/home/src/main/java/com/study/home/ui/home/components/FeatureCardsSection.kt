package com.study.home.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.study.domain.home.model.local.FeatureItem
import com.study.core.theme.AppColor
import com.study.core.theme.AppDimen
import com.study.core.theme.AppTheme

@Composable
fun FeatureCardsSection(
    onCardClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.padding(AppDimen.dp_16)
    ) {

        val features = listOf(
            FeatureItem(
                title = "Gia sư AI",
                subtitle = "Hỏi tôi bất cứ điều gì",
                backgroundColor = AppColor.AiTutorBg.value.toLong()
            ),
            FeatureItem(
                title = "Tạo Quiz",
                subtitle = "Kiểm tra kiến thức",
                backgroundColor = AppColor.QuizBg.value.toLong()
            ),
            FeatureItem(
                title = "Flash Card",
                subtitle = "Ôn tập kiến thức",
                backgroundColor = AppColor.FlashCardBg.value.toLong()
            ),
            FeatureItem(
                title = "Lịch sử học",
                subtitle = "Xem lại những bài học",
                backgroundColor = AppColor.HistoryBg.value.toLong()
            )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(AppDimen.dp_140),
            horizontalArrangement = Arrangement.spacedBy(AppDimen.dp_12)
        ) {
            FeatureCard(
                item = features[0],
                onClick = { onCardClick(features[0].title) },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                icon = painterResource(com.study.home.R.drawable.ic_ai)
            )

            FeatureCard(
                item = features[1],
                onClick = { onCardClick(features[1].title) },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                icon = painterResource(com.study.home.R.drawable.ic_quiz)

            )
        }

        Spacer(modifier = Modifier.height(AppDimen.dp_12))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(AppDimen.dp_140),
            horizontalArrangement = Arrangement.spacedBy(AppDimen.dp_12)
        ) {
            FeatureCard(
                item = features[2],
                onClick = { onCardClick(features[2].title) },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                icon = painterResource(com.study.home.R.drawable.ic_flashcard)

            )
            FeatureCard(
                item = features[3],
                onClick = { onCardClick(features[3].title) },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                icon = painterResource(com.study.home.R.drawable.ic_history)
            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFF5F5F5
)
@Composable
fun FeatureCardsSectionPreview() {
    AppTheme {
        FeatureCardsSection(
            onCardClick = {}
        )
    }
}
