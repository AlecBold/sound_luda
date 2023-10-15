package com.example.soundluda.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.QuestionData
import com.example.domain.model.VariantAnswer
import com.example.domain.usecase.TopicQuestionsUseCase
import com.example.soundluda.util.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UserAnswer(
  val questionData: QuestionData,
  val isAnswered: Boolean = false,
  val answer: VariantAnswer = VariantAnswer("", false)
)

data class TestingBlockQuestionsUiState(
  val isLoading: Boolean = false,
  val error: Throwable? =  null,
)

sealed class Action {
  object ShowResult: Action()
  object Noop: Action()
}

class TestingBlockQuestionsViewModel(
  private val topicQuestionsUseCase: TopicQuestionsUseCase,
  private val topicId: String,
  private val from: Int,
  private val to: Int
) : ViewModel() {

  private val _uiState: MutableStateFlow<TestingBlockQuestionsUiState> = MutableStateFlow(
    TestingBlockQuestionsUiState()
  )
  val uiState: StateFlow<TestingBlockQuestionsUiState>
    get() = _uiState.asStateFlow()

  private val _action: MutableStateFlow<Action> = MutableStateFlow(Action.Noop)
  val action: StateFlow<Action>
    get() = _action.asStateFlow()

  private val _userAnswers = mutableStateListOf<UserAnswer>()
  val userAnswers: List<UserAnswer> = _userAnswers

  private val _questions = mutableStateListOf<QuestionData>()
  val questions: List<QuestionData> = _questions

  init {
    getQuestions()
  }

  private fun getQuestions() {
    viewModelScope.launch(Dispatchers.IO) {
      val questions = topicQuestionsUseCase.getPart(topicId = topicId, from = from, to = to)
      val prepareFirstUserQuestion = UserAnswer(questions[0])
      launch(Dispatchers.Main) {
        _uiState.update { it.copy(isLoading = false, error = null) }
        _userAnswers.add(prepareFirstUserQuestion)
        _questions.addAll(questions)
      }
    }
  }

  fun answer(index: Int, answer: VariantAnswer) {
    val ans = _userAnswers[index]
    val isNotFinished = questions.size > index
    if (isNotFinished) {
      _userAnswers[index] = ans.copy(
        isAnswered = true,
        answer = answer
      )
      val isNotLastAnswer = questions.size > index + 1
      if (isNotLastAnswer) {
        _userAnswers.add(UserAnswer(questions[index + 1]))
      }
    } else {
      _action.value = Action.ShowResult
    }
  }
}