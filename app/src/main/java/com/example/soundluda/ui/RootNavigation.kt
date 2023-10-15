package com.example.soundluda.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.core.GlobalLog
import com.example.soundluda.App
import com.example.soundluda.di.DaggerListTopicsScreenComponent
import com.example.soundluda.di.DaggerQuestionsBlockGroupComponent
import com.example.soundluda.di.DaggerTestingQuestionsBlockComponent
import com.example.soundluda.di.DaggerTopicScreenComponent
import com.example.soundluda.ui.screens.list_topics.ListTopicsScreen
import com.example.soundluda.ui.screens.questions_block_group.QuestionsBlockGroupScreen
import com.example.soundluda.ui.screens.testing_block_questions.TestingBlockQuestionsScreen
import com.example.soundluda.ui.screens.topic.TopicScreen
import com.example.soundluda.util.daggerViewModel

sealed class Screen(val route: String) {
  object Browse : Screen("browse")
  object Favorite : Screen("favorite")

  /*---------------------------------*/

  object ListTopics : Screen("list_topics")
  object Topic : Screen("topic/{id}") {
    fun buildRoute(id: String) = "topic/$id"
  }

  object QuestionsBlock : Screen("topic/{id}/question_block/{from}-{to}") {
    fun buildRoute(idTopic: String, from: Int, to: Int) = "topic/$idTopic/question_block/$from-$to"
  }

  object TestingBlock : Screen("topic/{id}/question_block/{from}-{to}/testing") {
    fun buildRoute(idTopic: String, from: Int, to: Int) =
      "topic/$idTopic/question_block/$from-$to/testing"
  }
}

@Composable
fun RootScreen() {
  val navController = rememberNavController()

  var currentScreen: Screen by remember { mutableStateOf(Screen.ListTopics) }

  App.appComponent.getNavRoute().SetupNavigation(navController)
  LaunchedEffect(navController) {
    navController.currentBackStackEntryFlow.collect {
      when (it.destination.route) {
        Screen.ListTopics.route -> {
          currentScreen = Screen.ListTopics
        }

        Screen.Topic.route -> {
          currentScreen = Screen.Topic
        }

        else -> {

        }
      }
    }
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = when (currentScreen) {
              is Screen.ListTopics -> {
                "ListTopics"
              }

              is Screen.Topic -> {
                "Topic"
              }

              else -> {
                ""
              }
            }
          )
        },
        navigationIcon = {
          IconButton(onClick = { navController.navigateUp() }) {
            Icon(
              imageVector = Icons.Filled.ArrowBack,
              contentDescription = "Back"
            )
          }
        }
      )
    }
  ) {
    Navigation(navController = navController, modifier = Modifier.padding(it))
  }
}

@Composable
fun Navigation(navController: NavHostController, modifier: Modifier = Modifier) {
  val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

  DisposableEffect(lifecycleOwner.value) {
    val observer = LifecycleEventObserver { owner, event ->
      GlobalLog.log("Navigation", "onEvent -> ${event}")
    }
    lifecycleOwner.value.lifecycle.addObserver(observer)
    onDispose {
      GlobalLog.log("Navigation", "onDispose")
      lifecycleOwner.value.lifecycle.removeObserver(observer)
    }
  }
  NavHost(
    navController = navController,
    startDestination = Screen.ListTopics.route,
    modifier = modifier
  ) {
    addListTopics()
    addTopicGraph()
    addQuestionsBlockGraph()
    addTestingBlockQuestions()
  }
}

val NavGraphBuilder.TAG
  get() = "RootNavigation"


private fun NavGraphBuilder.addListTopics() {
  composable(Screen.ListTopics.route) {
    val listTopicsViewModel = daggerViewModel {
      GlobalLog.log(TAG, "Create viewmodel ListTopics")
      val component = DaggerListTopicsScreenComponent
        .builder()
        .appComponent(App.appComponent)
        .build()
      component.getViewModel()
    }
    GlobalLog.log(TAG, "Navigated to ListTopicsScreen")
    ListTopicsScreen(
      topicsViewModel = listTopicsViewModel
    )
  }
}

private fun NavGraphBuilder.addTopicGraph() {
  composable(Screen.Topic.route) { navBackStackEntry ->
    navBackStackEntry.maxLifecycle
    val idTopic = navBackStackEntry.arguments?.getString("id")
    checkNotNull(idTopic) {
      "Cant open without idTopic"
    }
    val topicViewModel = daggerViewModel {
      GlobalLog.log(TAG, "Create viewmodel TopicScreen")
      val component = DaggerTopicScreenComponent
        .builder()
        .idTopic(idTopic)
        .appComponent(App.appComponent)
        .build()
      component.getViewModel()
    }
    GlobalLog.log(TAG, "Navigate to Topic")
    TopicScreen(
      topicViewModel = topicViewModel
    )
  }
}

private fun NavGraphBuilder.addQuestionsBlockGraph() {
  composable(Screen.QuestionsBlock.route) { navBackStackEntry ->
    val idTopic = navBackStackEntry.arguments?.getString("id")
    val from = navBackStackEntry.arguments?.getString("from")
    val to = navBackStackEntry.arguments?.getString("to")
    checkNotNull(idTopic) { "Can't open without idTopic" }
    checkNotNull(from) { "Can't open without arg from " }
    checkNotNull(to) { "Can't open without arg to " }
    GlobalLog.log(TAG, "Navigated to QuestionsBlockGroupScreen")
    val questionsBlockViewModel = daggerViewModel {
      val component = DaggerQuestionsBlockGroupComponent
        .builder()
        .appComponent(App.appComponent)
        .idTopic(idTopic)
        .rangeQuestionsGroup(Pair(from.toInt(), to.toInt()))
        .build()
      component.getQuestionsBlockGroupViewMode()
    }
    QuestionsBlockGroupScreen(
      questionsBlockGroupViewModel = questionsBlockViewModel
    )
  }
}

private fun NavGraphBuilder.addTestingBlockQuestions() {
  composable(Screen.TestingBlock.route) { navBackStackEntry ->
    val idTopic = navBackStackEntry.arguments?.getString("id")
    val from = navBackStackEntry.arguments?.getString("from")
    val to = navBackStackEntry.arguments?.getString("to")
    checkNotNull(idTopic) { "Can't open without idTopic" }
    checkNotNull(from) { "Can't open without arg from " }
    checkNotNull(to) { "Can't open without arg to " }
    GlobalLog.log(TAG, "Navigated to TestingBlockQuestions")

    val viewModel = daggerViewModel {
      val component = DaggerTestingQuestionsBlockComponent
        .builder()
        .appComponent(App.appComponent)
        .topicId(idTopic)
        .rangeQuestions(Pair(from.toInt(), to.toInt()))
        .build()
      component.getTestingQuestionsViewModel()
    }

    TestingBlockQuestionsScreen(
      viewModel = viewModel
    )
  }
}
