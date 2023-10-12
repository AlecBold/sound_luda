package com.example.domain.usecase

import com.example.core.AndroidLoggable
import com.example.core.Loggable
import com.example.domain.model.QuestionData
import com.example.domain.model.TopicMetaData
import com.example.domain.repository.TopicsRepository

interface AvailableTopicsUseCase {
  suspend fun invoke(page: Int): List<TopicMetaData>
}

class AvailableTopicsInteractor(
  private val topicsRepository: TopicsRepository
) : AvailableTopicsUseCase, Loggable by AndroidLoggable("AvailableTopicsInteractor") {

  override suspend fun invoke(page: Int): List<TopicMetaData> {
    return topicsRepository.getAvailableTopics(page)
  }
}