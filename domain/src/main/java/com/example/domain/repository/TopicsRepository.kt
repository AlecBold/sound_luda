package com.example.domain.repository

import com.example.domain.model.QuestionData
import com.example.domain.model.TopicMetaData
import com.example.domain.model.TopicQuestionsData

interface TopicsRepository {

  suspend fun getAvailableTopics(page: Int): List<TopicMetaData>

  suspend fun getTopicQuestions(topicId: String): List<QuestionData>
  suspend fun getTopicQuestions(topicId: String, from: Int, to: Int): List<QuestionData>

  suspend fun getTopicData(topicId: String): TopicQuestionsData
}