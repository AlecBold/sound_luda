package com.example.soundluda.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.AndroidLoggable
import com.example.core.Loggable
import com.example.domain.model.QuestionData
import com.example.domain.usecase.TopicQuestionsUseCase
import com.example.soundluda.util.Toast
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class QuestionsBlockGroupState(
  val isLoading: Boolean = true,
  val questions: List<QuestionData> = listOf(),
  val error: Throwable? = null
)

class QuestionsBlockGroupViewModel(
  val topicId: String,
  val rangeQuestionsGroup: Pair<Int, Int>,
  val topicQuestionsUseCase: TopicQuestionsUseCase
) : ViewModel(), Loggable by AndroidLoggable("QuestionBlockGroupVM") {

  private val _uiState: MutableStateFlow<QuestionsBlockGroupState> =
    MutableStateFlow(QuestionsBlockGroupState())
  val uiState: StateFlow<QuestionsBlockGroupState>
    get() = _uiState.asStateFlow()

  private val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
    viewModelScope.launch(Dispatchers.Main) {
      Toast.show(txt = "Error: ${throwable}")
      _uiState.update {
        it.copy(
          isLoading = false,
          error = throwable
        )
      }
    }
  }

  init {
    getQuestions()
  }

  override fun onCleared() {
    super.onCleared()
    log("onCleared")
  }

  fun getQuestions() {
    viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
      val questions = topicQuestionsUseCase.getPart(
        topicId,
        rangeQuestionsGroup.first,
        rangeQuestionsGroup.second
      )
      launch(Dispatchers.Main) {
        _uiState.update {
          it.copy(
            isLoading = false,
            questions = questions,
            error = null
          )
        }
      }
    }
  }
}