package com.example.domain.usecase

import com.example.domain.model.QuestionData
import com.example.domain.model.TopicQuestionsData
import com.example.domain.repository.TopicsRepository

interface TopicQuestionsUseCase {
  suspend fun get(topicId: String): List<QuestionData>
  suspend fun getPart(topicId: String, from: Int, to: Int): List<QuestionData>
}

class TopicQuestionsInteractor(
  private val topicsRepository: TopicsRepository
): TopicQuestionsUseCase {
  override suspend fun get(topicId: String): List<QuestionData> {
    return topicsRepository.getTopicQuestions(topicId)
  }

  override suspend fun getPart(topicId: String, from: Int, to: Int): List<QuestionData> {
    require(from in 0..to) {
      "Wrong scope from=$from, to=$to"
    }
    return topicsRepository.getTopicQuestions(topicId, from, to)
  }
}