package com.example.data.model

import com.example.domain.model.TopicMetaData

data class TopicMetaEntity(
  val id: String,
  val subject_name: String,
  val description: String?,
  val n_questions: Int?
)

fun TopicMetaEntity.toModel(): TopicMetaData {
  return TopicMetaData(
    id,
    subject_name ?: "",
    description ?: "",
    n_questions ?: 0
  )
}