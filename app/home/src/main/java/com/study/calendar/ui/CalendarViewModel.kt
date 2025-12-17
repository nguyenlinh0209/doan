package com.study.calendar.ui

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.study.domain.home.model.local.TaskItem
import com.study.core.base.viewmodel.BaseUiStateViewModel
import com.study.domain.home.usecase.DeleteTaskUseCase
import com.study.domain.home.usecase.GetTasksByUserIdUseCase
import com.study.domain.home.usecase.SaveItemUseCase
import com.study.domain.home.usecase.UpdateTaskDescriptionParams
import com.study.domain.home.usecase.UpdateTaskDescriptionUseCase
import com.study.domain.home.usecase.UpdateTaskStatusParams
import com.study.domain.home.usecase.UpdateTaskStatusUseCase
import com.study.domain.home.usecase.UpdateTaskTitleParams
import com.study.domain.home.usecase.UpdateTaskTitleUseCase
import com.study.domain.user.usecase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class CalendarViewModel @Inject constructor(
    app: Application,
    private val saveItemUseCase: SaveItemUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getTasksByUserIdUseCase: GetTasksByUserIdUseCase,
    private val updateTaskStatusUseCase: UpdateTaskStatusUseCase,
    private val updateTaskTitleUseCase: UpdateTaskTitleUseCase,
    private val updateTaskDescriptionUseCase: UpdateTaskDescriptionUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : BaseUiStateViewModel<CalendarUiState, CalendarUiEvent, CalendarUiAction>(app) {

    override fun initialState(): CalendarUiState = CalendarUiState()

    override fun handleAction(action: CalendarUiAction) {
        when (action) {
            is CalendarUiAction.SaveTaskItem -> saveTaskItem(action.taskItem)
            is CalendarUiAction.ToggleTaskCompleted -> toggleCompleted(action.task)
            is CalendarUiAction.DeleteTask -> deleteTask(action.task)
            is CalendarUiAction.EditTaskTitle -> editTitle(action.task, action.newTitle)
            is CalendarUiAction.EditTaskDescription -> editDescription(action.task, action.newDescription)
        }
    }

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val userID = getUserUseCase()
            updateState { it.copy(userId = userID) }
            loadTaskItem(userID)
        }
    }

    private fun loadTaskItem(userId: UUID?) {
        val id = userId ?: return
        viewModelScope.launch(Dispatchers.IO) {
            val tasks = getTasksByUserIdUseCase(id)
            updateState { it.copy(tasks = tasks) }
        }
    }

    private fun saveTaskItem(taskItem: TaskItem) {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = uiState.value.userId ?: return@launch
            val newTask = taskItem.copy(
                id = UUID.randomUUID(),
                userId = userId
            )
            saveItemUseCase(newTask)
            updateState { it.copy(tasks = it.tasks + newTask) }
        }
    }

    private fun toggleCompleted(task: TaskItem) {
        viewModelScope.launch(Dispatchers.IO) {
            updateTaskStatusUseCase(
                UpdateTaskStatusParams(task.id.toString(), !task.isCompleted)
            )
            updateState {
                it.copy(
                    tasks = it.tasks.map { t ->
                        if (t.id == task.id) t.copy(isCompleted = !t.isCompleted) else t
                    }
                )
            }
        }
    }

    private fun deleteTask(task: TaskItem) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteTaskUseCase(task.id.toString())
            updateState {
                it.copy(tasks = it.tasks.filterNot { t -> t.id == task.id })
            }
        }
    }

    private fun editTitle(task: TaskItem, title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            updateTaskTitleUseCase(
                UpdateTaskTitleParams(task.id.toString(), task, title)
            )
            updateState {
                it.copy(
                    tasks = it.tasks.map {
                        if (it.id == task.id) it.copy(title = title) else it
                    }
                )
            }
        }
    }

    private fun editDescription(task: TaskItem, description: String) {
        viewModelScope.launch(Dispatchers.IO) {
            updateTaskDescriptionUseCase(
                UpdateTaskDescriptionParams(task.id.toString(), task, description)
            )
            updateState {
                it.copy(
                    tasks = it.tasks.map {
                        if (it.id == task.id) it.copy(description = description) else it
                    }
                )
            }
        }
    }
}
