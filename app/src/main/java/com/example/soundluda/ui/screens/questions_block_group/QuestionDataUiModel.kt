package com.example.soundluda.ui.screens.questions_block_group

import androidx.compose.ui.text.AnnotatedString
import com.example.domain.model.QuestionData

data class QuestionDataUiModel(
  val questionString: AnnotatedString,
  val questionData: QuestionData
)
//
//fun QuestionData.toUiModel(): QuestionDataUiModel {
//  return QuestionDataUiModel(
//    AnnotateTransformer.RangeColor(0, this.indexQuestion.toString().length + 1, MaterialTheme.colors.primary).transform("$indexQuestion. $question"),
//    this
//  )
//}
