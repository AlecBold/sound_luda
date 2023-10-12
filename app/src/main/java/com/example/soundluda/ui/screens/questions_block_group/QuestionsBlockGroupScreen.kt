package com.example.soundluda.ui.screens.questions_block_group

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.domain.model.QuestionData
import com.example.soundluda.ui.AnnotateTransformer
import com.example.soundluda.viewmodel.QuestionsBlockGroupViewModel


@Composable
fun QuestionsBlockGroupScreen(
  questionsBlockGroupViewModel: QuestionsBlockGroupViewModel = viewModel(),
  onClickStartTesting: () -> Unit
) {
  val uiState by questionsBlockGroupViewModel.uiState.collectAsState()
  Box {
    LazyColumn(
      modifier = Modifier,
      contentPadding = PaddingValues(top = 4.dp, bottom = 60.dp)
    ) {
      itemsIndexed(uiState.questions) { index, question ->
        QuestionWithAnswerCard(
          questionData = question
        )
      }
    }
    ExtendedFloatingActionButton(
      modifier = Modifier
        .align(Alignment.BottomEnd)
        .padding(end = 12.dp, bottom = 8.dp),
      text = {
        Text(text = "Start testing")
      },
      icon = {
        Icon(Icons.Filled.PlayArrow, "Start testing button.")
      },
      onClick = {
        onClickStartTesting()
      }
    )
  }
}

@Composable
fun QuestionWithAnswerCard(
  questionData: QuestionData
) {
  Box(
    modifier = Modifier
      .padding(horizontal = 12.dp, vertical = 4.dp)
      .clip(MaterialTheme.shapes.medium)
      .background(MaterialTheme.colors.onSecondary)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
      Row {

        Text(
          text = AnnotateTransformer.RangeColor(
            0,
            questionData.indexQuestion.toString().length + 1,
            MaterialTheme.colors.primary
          ).transform("${questionData.indexQuestion}. ${questionData.question}"),
          style = MaterialTheme.typography.subtitle1
        )
      }
      Spacer(modifier = Modifier.size(12.dp))
      for ((i, answer) in questionData.variantAnswers.withIndex()) {
        VariantAnswerBlock(answer = answer.answer, isRightAnswer = answer.isCorrect)
      }
    }
  }
}

@Composable
fun VariantAnswerBlock(
  answer: String,
  isRightAnswer: Boolean
) {
  var modifier = Modifier.clip(MaterialTheme.shapes.medium)
  if (isRightAnswer) {
    modifier = modifier
      .border(
        width = 1.dp,
        color = Color.Green,
        shape = MaterialTheme.shapes.medium,
      )
  }
  Box(
    modifier = modifier
  ) {
    Row(
      modifier = Modifier
        .padding(horizontal = 6.dp, vertical = 4.dp)
        .fillMaxWidth(),
    ) {
      val icon = if (isRightAnswer) Icons.Filled.Done else Icons.Filled.Clear
      val tint = if (isRightAnswer) Color.Green else Color.Red
      Icon(
        imageVector = icon,
        contentDescription = "check mark",
        modifier = Modifier
          .padding(end = 6.dp)
          .size(10.dp)
          .align(Alignment.CenterVertically),
        tint = tint
      )
      Text(
        text = answer,
        style = MaterialTheme.typography.caption
      )
    }
  }
}