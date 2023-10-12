package com.example.data.model

import com.example.domain.model.QuestionData
import com.example.domain.model.VariantAnswer

data class QuestionEntity(
  val id: String,
  var index_question: Int?,
  val question: String?,
  val opa: String?,
  val opb: String?,
  val opc: String?,
  val opd: String?,
  val subject_name: String?,
  val topic_name: String?,
  val choice_type: String?,
  val answer: String?
)

fun QuestionEntity.toModel(): QuestionData {
  val variantAnswers = mutableListOf<VariantAnswer>()
  var correctIndexAnswer = -1
  val correctAnswer = opa // TODO: change to real answer
  listOfNotNull(opa, opb, opc, opd).forEachIndexed { index, answer ->
    val isCorrect = if (correctAnswer == answer) {
      correctIndexAnswer = index
      true
    } else false
    variantAnswers.add(
      VariantAnswer(answer, isCorrect)
    )
  }
  return QuestionData(
    id = id,
    indexQuestion = index_question ?: -1,
    question = question ?: "",
    variantAnswers = variantAnswers,
    correctIndexAnswer = correctIndexAnswer,
    correctAnswer = correctAnswer ?: "",
    explanation = null,
    subjectName = subject_name ?: ""
  )
}
