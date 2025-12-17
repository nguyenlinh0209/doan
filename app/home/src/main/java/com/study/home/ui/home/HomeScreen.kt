package com.study.home.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.osprey.domain.home.model.local.LearningItem
import com.study.core.theme.AppColor
import com.study.home.ui.home.components.FeatureCardsSection
import com.study.home.ui.home.components.HotActivitySection
import com.study.home.ui.home.components.SearchBarSection
import com.study.home.ui.home.components.header.HeaderSection

@Composable
fun HomeScreen(
    state: HomeUiState,
    onAction: (HomeUiAction) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColor.Background)
    ) {
        HeaderSection(
            userName = "Chao Linh",
            streak = state.streak
        )
        SearchBarSection()

        FeatureCardsSection(
            onCardClick = {
                onAction(HomeUiAction.FeatureClicked(it))
            }
        )
        HotActivitySection(state.learningData)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val mockState = HomeUiState(
        streak = 5,
        learningData = listOf(
            LearningItem(id = "1", title = "Grammar", timestamp = 123456789, type = "grammar"),
            LearningItem(
                id = "2",
                title = "Vocabulary",
                timestamp = 123456790,
                type = "vocabulary"
            ),
            LearningItem(id = "3", title = "Reading", timestamp = 123456791, type = "reading"),
            LearningItem(id = "4", title = "Listening", timestamp = 123456792, type = "listening")
        ),
    )

    HomeScreen(
        state = mockState,
        onAction = {}
    )
}
