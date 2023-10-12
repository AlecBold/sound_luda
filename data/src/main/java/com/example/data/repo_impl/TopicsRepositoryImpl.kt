package com.example.data.repo_impl

import com.example.data.model.TopicMetaEntity
import com.example.data.model.toModel
import com.example.data.network.TopicsNetworkRepository
import com.example.domain.model.QuestionData
import com.example.domain.model.TopicMetaData
import com.example.domain.model.TopicQuestionsData
import com.example.domain.repository.TopicsRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


const val N_QUESTIONS_PER_REQUEST = 100

class TopicsRepositoryImpl(
  val networkRepository: TopicsNetworkRepository
): TopicsRepository {

  override suspend fun getAvailableTopics(page: Int): List<TopicMetaData> {
    return networkRepository.getAvailableTopics().map { it.toModel() }
  }

  override suspend fun getTopicQuestions(topicId: String): List<QuestionData> {
    return networkRepository.getTopicQuestions(topicId).map {
      it.toModel()
    }
  }

  override suspend fun getTopicQuestions(topicId: String, from: Int, to: Int): List<QuestionData> {
    return networkRepository.getPartTopicQuestions(topicId, from, to).map {
      it.toModel()
    }
  }

  override suspend fun getTopicData(topicId: String): TopicQuestionsData = coroutineScope {
    val questionsAsync = async { getTopicQuestions(topicId) }
    val metaDataAsync = async { networkRepository.getTopic(topicId) }

    val questions = questionsAsync.await()
    val metaData = metaDataAsync.await()
    TopicQuestionsData(
      metaData.id,
      metaData.subject_name ?: "",
      metaData.description ?: "",
      questions
    )
  }
}