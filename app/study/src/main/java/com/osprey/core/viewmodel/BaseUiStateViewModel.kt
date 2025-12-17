package com.osprey.core.viewmodel

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

abstract class BaseUiStateViewModel<S, E, A>(
    application: Application
) : AndroidViewModel(application) {
    var context: WeakReference<Context>? = null

    var data: Bundle? = null
    private val _uiState = MutableStateFlow(initialState())
    val uiState: StateFlow<S> = _uiState.asStateFlow()

    private val _uiEvent = Channel<E>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    abstract fun initialState(): S

    open fun handleAction(action: A) {}

    fun dispatch(action: A) = handleAction(action)

    protected fun updateState(reducer: (S) -> S) {
        _uiState.value = reducer(_uiState.value)
    }

    protected fun sendEvent(event: E) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    protected val currentState: S
        get() = _uiState.value

    init {
        onCreate()
    }

    open fun onCreate() {}

    open fun onDestroy() {}

    protected fun applicationContext(): Context =
        getApplication<Application>().applicationContext
}
