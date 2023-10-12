package com.example.domain.model

data class TopicQuestionsData(
  val id: String,
  val name: String,
  val description: String,
  val questions: List<QuestionData>
)