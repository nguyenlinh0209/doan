package com.osprey.main.ui.main

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.study.domain.user.usecase.GetCurrentUserEmail
import com.study.main.ui.main.bottombar.BottomBarMenu
import com.study.core.base.viewmodel.BaseUiStateViewModel
import com.study.core.extension.toArrayList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val app: Application,
    private val getCurrentUserEmail: GetCurrentUserEmail,
    ) : BaseUiStateViewModel<MainUiState, MainUiEvent, MainUiAction>(app) {
    val bottomBarMenus: List<BottomBarMenu> = BottomBarMenu.getDefaults(app)

    val changePageBottomEvent = MutableLiveData<Int>()

    override fun initialState(): MainUiState = MainUiState()

    override fun handleAction(action: MainUiAction) {
        when (action) {
            is MainUiAction.ChangeTab -> changeTab(action.type)
        }
    }

    override fun onCreate() {
        super.onCreate()
        loadUser()
    }

    private fun changeTab(type: BottomBarMenu.BottomBarMenuType) {
        val menus = uiState.value.menus.toArrayList().onEach {
            it.isSelected = it.type == type
        }
        updateState { it.copy(menus = menus,
            showAddButton = type == BottomBarMenu.BottomBarMenuType.HOME,
            showLogButton = type == BottomBarMenu.BottomBarMenuType.SCENE,
            showIconProfile = type == BottomBarMenu.BottomBarMenuType.PROFILE,
            )
        }
        changePageBottomEvent.value = when(type){
            BottomBarMenu.BottomBarMenuType.HOME -> 0
            BottomBarMenu.BottomBarMenuType.SCENE -> 1
            BottomBarMenu.BottomBarMenuType.MALL -> 2
            BottomBarMenu.BottomBarMenuType.PROFILE -> 3
        }
    }

    private fun loadUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val email = getCurrentUserEmail()
            updateState {
                it.copy(
                    email = email,
                )
            }
        }
    }
}
