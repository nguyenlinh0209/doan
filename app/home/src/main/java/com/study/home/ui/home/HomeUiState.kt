package com.study.home.ui.home

import com.osprey.domain.home.model.local.LearningItem

data class HomeUiState(
    val learningData: List<LearningItem> = emptyList(),
    val streak: Int = 0,

)
