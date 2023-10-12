package com.example.domain.model

data class QuestionData(
  val id: String,
  val indexQuestion: Int,
  val question: String,
  val variantAnswers: List<VariantAnswer>,
  val correctIndexAnswer: Int,
  val correctAnswer: String,
  val explanation: String?,
  val subjectName: String
)

data class VariantAnswer(
  val answer: String,
  val isCorrect: Boolean
)
