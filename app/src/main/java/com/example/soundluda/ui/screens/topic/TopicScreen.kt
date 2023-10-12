package com.example.soundluda.ui.screens.topic

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.soundluda.viewmodel.GroupQuestions
import com.example.soundluda.viewmodel.TopicViewModel


@Composable
fun TopicScreen(
  onClickGroupQuestions: (Int, Int) -> Unit,
  topicViewModel: TopicViewModel = viewModel()
) {
  val topicUiState by topicViewModel.uiState.collectAsState()
  Column(
    Modifier.padding(horizontal = 12.dp)
  ) {
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp),
      shape = MaterialTheme.shapes.medium
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(start = 12.dp, top = 2.dp, bottom = 4.dp, end = 12.dp)
      ) {
        Text(
          text = topicUiState.topicData.name,
          style = MaterialTheme.typography.h4
        )
        Text(
          text = topicUiState.topicData.description,
          style = MaterialTheme.typography.caption
        )
      }
    }
    LazyVerticalGrid(
      columns = GridCells.Fixed(3),
      verticalArrangement = Arrangement.spacedBy(4.dp),
      horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      items(topicUiState.questionsGroups.group) { groupQuestions ->
        QuestionsPartCard(
          onClick = { onClickGroupQuestions(it.from, it.to) },
          groupQuestions = groupQuestions
        )
      }
    }
  }
}

@Composable
fun QuestionsPartCard(
  onClick: (GroupQuestions) -> Unit,
  groupQuestions: GroupQuestions
) {
  Box(
    Modifier
      .clip(MaterialTheme.shapes.medium)
      .background(MaterialTheme.colors.onSecondary)
      .height(30.dp)
      .clickable { onClick(groupQuestions) },
    contentAlignment = Alignment.Center,
  ) {
    Text(
      text = "${groupQuestions.from}-${groupQuestions.to}",
      style = MaterialTheme.typography.body2
    )
  }
}