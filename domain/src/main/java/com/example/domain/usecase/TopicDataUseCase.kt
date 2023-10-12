package com.example.domain.usecase

import com.example.domain.model.QuestionData
import com.example.domain.model.TopicQuestionsData
import com.example.domain.repository.TopicsRepository

interface TopicDataUseCase {
  suspend fun invoke(id: String): TopicQuestionsData
}

class TopicDataInteractor(
  val topicsRepository: TopicsRepository
): TopicDataUseCase {
  override suspend fun invoke(id: String): TopicQuestionsData {
    return topicsRepository.getTopicData(id)
  }
}