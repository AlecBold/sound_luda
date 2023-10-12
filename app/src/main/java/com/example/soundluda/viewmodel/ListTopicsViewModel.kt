package com.example.soundluda.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.core.AndroidLoggable
import com.example.domain.model.TopicMetaData
import com.example.domain.usecase.AvailableTopicsUseCase
import com.example.core.GlobalLog
import com.example.core.Loggable
import com.example.soundluda.util.Toast
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class ListTopicsUiState(
  val isLoading: Boolean = true,
  val error: Throwable? = null,
  val topics: List<TopicMetaData> = listOf()
)


class ListTopicsViewModel(
  private val availableTopics: AvailableTopicsUseCase
): ViewModel(), Loggable by AndroidLoggable("ListTopicsViewModel") {

  init {
    log("Created")
  }

  private val _uiState = MutableStateFlow(ListTopicsUiState())
  val uiState: StateFlow<ListTopicsUiState> = _uiState.asStateFlow()

  private val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
    viewModelScope.launch(Dispatchers.Main) {
      Toast.show(throwable.toString())
      GlobalLog.log("ListTopicsViewModel", throwable.toString())
      _uiState.update {
        it.copy(isLoading = false, error = throwable)
      }
    }
  }

  init {
    getTopics()
  }

  fun getTopics() {
    viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
      val topics = availableTopics.invoke(1)
      GlobalLog.log(txt = "Get topics :: thread=${Thread.currentThread()}")
      withContext(Dispatchers.Main) {
        _uiState.update {
          it.copy(isLoading = false, topics = topics)
        }
      }
    }
  }

  private suspend fun handleException(action: suspend () -> Unit) {
    try {
      action.invoke()
    } catch (e: Exception) {
      withContext(Dispatchers.Main) {
        Toast.show(e.toString())
        GlobalLog.log("ListTopicsViewModel", e.toString())
      }
    }
  }

  override fun onCleared() {
    super.onCleared()
  }

  companion object {
    val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
      override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return super.create(modelClass, extras)
      }
    }
  }
}