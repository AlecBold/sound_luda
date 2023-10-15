package com.example.soundluda.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.AndroidLoggable
import com.example.core.GlobalLog
import com.example.core.Loggable
import com.example.domain.model.QuestionData
import com.example.domain.model.TopicQuestionsData
import com.example.domain.usecase.TopicDataUseCase
import com.example.domain.usecase.TopicQuestionsUseCase
import com.example.soundluda.ui.RouteNavigator
import com.example.soundluda.ui.Screen
import com.example.soundluda.util.Toast
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class TopicUiState(
  val isLoading: Boolean = true,
  val error: Throwable? = null,
  val topicData: TopicQuestionsData = TopicQuestionsData("", "", "", listOf()),
  val questionsGroups: GroupedQuestionsModel = GroupedQuestionsModel()
)

data class GroupQuestions(
  val from: Int = 0,
  val to: Int = 0,
  val questions: List<QuestionData> = listOf()
)

data class GroupedQuestionsModel(
  val group: List<GroupQuestions> = listOf(),
  val nQuestions: Int = 0
)

const val BY_N_QUESTIONS_GROUP = 100

class TopicViewModel(
  private val topicDataUseCase: TopicDataUseCase,
  private val idTopic: String,
  private val routeNavigator: RouteNavigator
) : ViewModel(), RouteNavigator by routeNavigator, Loggable by AndroidLoggable("TopicViewModel") {

  init {
    log("Created")
  }

  private val _uiState = MutableStateFlow(TopicUiState())
  val uiState: StateFlow<TopicUiState> = _uiState.asStateFlow()

  private val coroutinesExceptionHandler =
    CoroutineExceptionHandler { _, throwable ->
      viewModelScope.launch(Dispatchers.Main) {
        Toast.show("Error = $throwable")
        GlobalLog.log("TopicViewModel", "exception=${throwable}")
        _uiState.update {
          it.copy(isLoading = false, error = throwable)
        }
      }
    }

  init {
    getTopicData(idTopic)
  }

  fun getTopicData(id: String) {
    viewModelScope.launch(Dispatchers.IO + coroutinesExceptionHandler) {
      val data = topicDataUseCase.invoke(id)
      _uiState.update {
        it.copy(
          isLoading = false,
          topicData = data,
          questionsGroups = groupQuestions(data.questions)
        )
      }
    }
  }

  fun onClickGroupQuestions(groupQuestions: GroupQuestions) {
    navigateToRoute(Screen.QuestionsBlock.buildRoute(idTopic, groupQuestions.from, groupQuestions.to))
  }

  private fun groupQuestions(questions: List<QuestionData>): GroupedQuestionsModel {
    val groupList: MutableList<GroupQuestions> = mutableListOf()
    var group = mutableListOf<QuestionData>()
    val nQuestions = questions.size
    var from = 0
    for ((i, question) in questions.withIndex()) {
      group.add(question)
      if ((i + 1) % BY_N_QUESTIONS_GROUP == 0 || i + 1 == nQuestions) {
        groupList.add(
          GroupQuestions(
            from,
            i,
            group
          )
        )
        from  = i + 1
        group = mutableListOf()
      }
    }
    val grouped = GroupedQuestionsModel(
      groupList,
      nQuestions
    )
    GlobalLog.log("TopicViewModel", "Grouped questions :: groups=${group.size}")
    return grouped
  }

  override fun onCleared() {
    super.onCleared()
    log("onCleared")
  }

}