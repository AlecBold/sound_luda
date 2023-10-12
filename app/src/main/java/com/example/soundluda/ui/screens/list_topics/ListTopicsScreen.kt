package com.example.soundluda.ui.screens.list_topics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.soundluda.ui.Screen
import com.example.soundluda.ui.theme.SoundLudaTheme
import com.example.soundluda.util.Toast
import com.example.soundluda.viewmodel.ListTopicsViewModel


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListTopicsScreen(
  onItemClick: (String) -> Unit,
  topicsViewModel: ListTopicsViewModel = viewModel()
) {
  val topicsUiState by topicsViewModel.uiState.collectAsState()
  var showDialog by remember { mutableStateOf(false) }
  Box {
    LazyColumn(
      modifier = Modifier,
      contentPadding = PaddingValues(vertical = 4.dp)
    ) {
      items(topicsUiState.topics) { topic ->
        Card(
          modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .height(IntrinsicSize.Min),
          onClick = {
            onItemClick(topic.id)
          },
        ) {
          Column(modifier = Modifier
            .padding(start = 12.dp, top = 2.dp, bottom = 4.dp, end = 12.dp)
            .fillMaxWidth()) {
            Text(
              text = topic.name,
              style = MaterialTheme.typography.h6
            )
            Text(
              text = topic.description,
              style = MaterialTheme.typography.caption
            )
          }
          Box(
            modifier = Modifier
              .fillMaxHeight()
              .width(4.dp)
              .background(color = Color.White)
          )
        }
      }
    }
    FloatingActionButton(
      modifier = Modifier.align(Alignment.BottomEnd),
      onClick = {
      showDialog = true
    }) {
    }
    if (showDialog) {
      Dialog(
        onDismissRequest = {
          Toast.show("on dismiss")
          showDialog = false
        },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false, usePlatformDefaultWidth = false)
      ) {
        Box(
          modifier = Modifier.fillMaxSize()
        ) {
          Text(
            modifier = Modifier.align(Alignment.Center),
            text = "Dialog"
          )
        }
      }
    }

  }
}

@Preview
@Composable
fun ListTopicsScreenPreview() {
  SoundLudaTheme {
    ListTopicsScreen(onItemClick = {})
  }
}
