package com.example.soundluda.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.example.core.GlobalLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NavRoute(
  val routeNavigator: RouteNavigator
) {

  @Composable
  fun SetupNavigation(
    navHostController: NavHostController
  ) {
    val viewState by routeNavigator.navigationState.collectAsState()

    LaunchedEffect(viewState) {
      updateNavigationState(navHostController, viewState, routeNavigator::onNavigated)
    }
  }

  private fun updateNavigationState(
    navHostController: NavHostController,
    navigationState: NavState,
    onNavigated: (NavState) -> Unit
  ) {
    when (navigationState) {
      is NavState.NavToRoute -> {
        navHostController.navigate(navigationState.route)
        onNavigated(navigationState)
      }
      is NavState.PopBackStack -> {
        navHostController.popBackStack(navigationState.route, false)
        onNavigated(navigationState)
      }
      is NavState.Idle -> {}
    }
  }
}

sealed class NavState {
  object Idle : NavState()
  data class NavToRoute(val route: String) : NavState()
  data class PopBackStack(val route: String): NavState()
}


interface RouteNavigator {
  fun onNavigated(navState: NavState)
  fun navigateToRoute(route: String)
  val navigationState: StateFlow<NavState>
}

class MainRouteNavigator() : RouteNavigator {

  override val navigationState: MutableStateFlow<NavState> = MutableStateFlow(NavState.Idle)

  override fun navigateToRoute(route: String) {
    GlobalLog.log("MainRouteNav", "navigateToRoute=${route}")
    navigationState.value = NavState.NavToRoute(route)
  }

  override fun onNavigated(navState: NavState) {
    GlobalLog.log("MainRouteNav", "onNavigated=${navState}")
    navigationState.compareAndSet(navState, NavState.Idle)
  }
}
