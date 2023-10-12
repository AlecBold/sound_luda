package com.example.soundluda.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.soundluda.util.Toast

data class BottomNavItem(
  val name: String,
  val route: String,
  val icon: ImageVector
)

fun BottomNavItems() = listOf(
  BottomNavItem(
    "Browse",
    Screen.Browse.route,
    Icons.Filled.Search
  ),
  BottomNavItem(
    "Favorite",
    Screen.Favorite.route,
    Icons.Filled.Favorite
  )
)

@Composable
fun BrowseScreen(showAdoptionPage: (String) -> Unit) {
  Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
  ) {
    Text(text = "Browse screen")
  }
}
@Composable
fun FavoriteScreen(showAdoptionPage: (String) -> Unit) {
  Scaffold(
    floatingActionButton = {
      FloatingActionButton(onClick = {
        Toast.show("floast")
      }) {
        Icon(Icons.Filled.Add, "")
      }
    }
  ) {
    Text(text = "Favorite screen", modifier = Modifier.padding(it))
  }
}


@Composable
fun BottomNavigationBar(
  items: List<BottomNavItem>,
  navController: NavController,
  modifier: Modifier = Modifier,
  onItemClick: (BottomNavItem) -> Unit
) {
  val backStackEntry = navController.currentBackStackEntryAsState()
  BottomNavigation(
    modifier = modifier,
    elevation = 5.dp
  ) {
    items.forEach {
      val selected = it.route == backStackEntry.value?.destination?.route
      BottomNavigationItem(
        selected = selected,
        onClick = { onItemClick(it) },
        icon = {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = it.icon, contentDescription = it.name)
          }
        }
      )
    }
  }
}
