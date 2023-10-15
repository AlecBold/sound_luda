package com.example.soundluda.ui.screens.testing_block_questions

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.domain.model.QuestionData
import com.example.domain.model.VariantAnswer
import com.example.soundluda.ui.AnnotateTransformer
import com.example.soundluda.ui.conditional
import com.example.soundluda.ui.theme.correctAnswer
import com.example.soundluda.ui.theme.wrongAnswer
import com.example.soundluda.viewmodel.TestingBlockQuestionsViewModel
import com.example.soundluda.viewmodel.UserAnswer
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun TestingBlockQuestionsScreen(
  viewModel: TestingBlockQuestionsViewModel = viewModel()
) {
  val userAnswers = remember { viewModel.userAnswers }
  val questions = remember { viewModel.questions }
  val pagerState = rememberPagerState(0)
  val coroutineScope = rememberCoroutineScope()
  Column(
    modifier = Modifier.padding(vertical = 8.dp)
  ) {
    NavigationSection(
      questionsData = questions,
      userAnswers = userAnswers,
      currentPage = pagerState.currentPage,
      contentPaddingValues = PaddingValues(horizontal = 12.dp),
      onClickNavigate = {
        coroutineScope.launch {
          pagerState.animateScrollToPage(it)
        }
      }
    )
    PagerSection(
      pagerState = pagerState,
      userAnswers = userAnswers,
      onClickAnswer = { indexQuestion, answer ->
        viewModel.answer(indexQuestion, answer)
      }
    )

  }
}

@Composable
fun NavigationSection(
  questionsData: List<QuestionData>,
  userAnswers: List<UserAnswer>,
  currentPage: Int,
  modifier: Modifier = Modifier,
  contentPaddingValues: PaddingValues = PaddingValues(0.dp),
  onClickNavigate: (Int) -> Unit
) {
  LazyRow(
    modifier = modifier,
    contentPadding = contentPaddingValues,
    horizontalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    itemsIndexed(questionsData) { index, item ->
      val userAnswer = userAnswers.getOrNull(index)
      val isAnswered = userAnswer?.isAnswered ?: false
      val isCorrect = userAnswer?.answer?.isCorrect ?: false
      Column(
        modifier = Modifier
      ) {
        OutlinedButton(
          modifier = Modifier.size(36.dp),
          shape = CircleShape,
          border = BorderStroke(1.dp, MaterialTheme.colors.primary),
          contentPadding = PaddingValues(0.dp),
          onClick = {
            if (index <= userAnswers.size - 1) {
              onClickNavigate(index)
            }
          },
          colors = ButtonDefaults.buttonColors(
            if (isAnswered) {
              if (isCorrect) MaterialTheme.colors.correctAnswer else MaterialTheme.colors.wrongAnswer
            } else {
              MaterialTheme.colors.surface
            }
          )
        ) {
          Text(text = index.toString())
        }
        Spacer(modifier = Modifier.size(4.dp))
        if (index == currentPage) {
          Box(
            modifier = Modifier
              .clip(CircleShape)
              .background(Color.White)
              .size(4.dp)
              .align(Alignment.CenterHorizontally),
          )
        } else {
          Spacer(modifier = Modifier.size(4.dp))
        }
      }
    }
  }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerSection(
  pagerState: PagerState,
  userAnswers: List<UserAnswer>,
  onClickAnswer: (Int, VariantAnswer) -> Unit
) {
  val coroutineScope = rememberCoroutineScope()
  HorizontalPager(
    modifier = Modifier
      .fillMaxSize()
      .padding(top = 12.dp),
    state = pagerState,
    pageCount = userAnswers.size
  ) { indexQuestion ->
    val userAnswer = userAnswers[indexQuestion]
    val question = userAnswer.questionData
    val interactionSource = remember { MutableInteractionSource() }
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 12.dp)
        .conditional(userAnswer.isAnswered) {
          clickable(indication = null, interactionSource = interactionSource) {
            coroutineScope.launch {
              pagerState.animateScrollToPage(userAnswers.size - 1)
            }
          }
        }
    ) {
      Text(
        text = AnnotateTransformer.RangeColor(
          0,
          question.indexQuestion.toString().length + 1,
          MaterialTheme.colors.primary
        ).transform("${question.indexQuestion}. ${question.question}"),
        style = MaterialTheme.typography.subtitle1
      )
      Spacer(modifier = Modifier.size(12.dp))
      for (answer in question.variantAnswers) {
        val color = if (userAnswer.isAnswered) {
          if (userAnswer.answer == answer) {
            if (userAnswer.answer.isCorrect) {
              MaterialTheme.colors.correctAnswer
            } else MaterialTheme.colors.wrongAnswer
          } else if (answer.isCorrect) {
            MaterialTheme.colors.correctAnswer
          } else {
            MaterialTheme.colors.surface
          }
        } else {
          MaterialTheme.colors.surface
        }
        AnswerCard(
          isClickable = userAnswer.isAnswered.not(),
          backgroundColor = color,
          answer = answer,
          onClick = { onClickAnswer(indexQuestion, answer) }
        )
      }
    }
  }
}

@Composable
fun AnswerCard(
  isClickable: Boolean,
  backgroundColor: Color,
  answer: VariantAnswer,
  onClick: (VariantAnswer) -> Unit
) {
  val colorAsState by animateColorAsState(targetValue = backgroundColor, label = "background color")
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 6.dp)
      .conditional(isClickable) {
        clickable { onClick(answer) }
      },
    backgroundColor = colorAsState
  ) {
    Text(
      modifier = Modifier.padding(vertical = 8.dp, horizontal = 14.dp),
      text = answer.answer,
      style = MaterialTheme.typography.subtitle2
    )
  }
}

@Composable
fun ResultTestingDialog(
  onDismiss: () -> Unit,
) {
  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(
      dismissOnBackPress = true,
      dismissOnClickOutside = false,
      usePlatformDefaultWidth = false
    )
  ) {
    Box {
      CircularProgressIndicator(
        strokeCap = StrokeCap.Round
      )
    }
  }
}

