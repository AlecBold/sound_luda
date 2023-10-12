package com.example.data.network

import android.content.res.AssetManager
import android.content.res.Resources.NotFoundException
import com.example.core.GlobalLog
import com.example.data.model.QuestionEntity
import com.example.data.model.TopicMetaEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay


interface TopicsNetworkRepository {

  suspend fun getAvailableTopics(): List<TopicMetaEntity>
  suspend fun getTopicQuestions(topicId: String): List<QuestionEntity>
  suspend fun getPartTopicQuestions(topicId: String, from: Int, to: Int): List<QuestionEntity>
  suspend fun getTopic(id: String): TopicMetaEntity
}

//
//class TopicsNetworkRepositoryImpl: TopicsNetworkRepository {
//
//  override suspend fun getAvailableTopics(): List<TopicMetaData> {
//    TODO("Not yet implemented")
//  }
//
//  override suspend fun getTopicQuestions(topicId: String, nQuestions: Int): TopicQuestionsData {
//    TODO("Not yet implemented")
//  }
//}

class TopicsAssetManagerRepositoryImpl(
  private val assetManager: AssetManager
) : TopicsNetworkRepository {

  override suspend fun getAvailableTopics(): List<TopicMetaEntity> {
    val topics = getFromAssets<Map<String, TopicMetaEntity>>(topics_fileName)
    delay(2000)
    return topics.toList().map { it.second }
  }

  override suspend fun getTopicQuestions(topicId: String): List<QuestionEntity> {
    val questions =
      getFromAssets<List<QuestionEntity>>(topicsQuestions_directory.plus("/$topicId.json")).mapIndexed { index, questionEntity ->
        questionEntity.index_question = index
        questionEntity
      }
    delay(2000)
    return questions
  }

  override suspend fun getPartTopicQuestions(
    topicId: String,
    from: Int,
    to: Int
  ): List<QuestionEntity> {
    val questions =
      getFromAssets<List<QuestionEntity>>(topicsQuestions_directory.plus("/$topicId.json")).mapIndexed { index, questionEntity ->
        questionEntity.index_question = index
        questionEntity
      }
    return if (questions.size > from) {
      questions.subList(from, if (questions.size > to) to + 1 else questions.size + 1)
    } else listOf()
  }

  override suspend fun getTopic(id: String): TopicMetaEntity = coroutineScope {
    val a = getAvailableTopics()
    a.forEach {
      if (it.id == id) return@coroutineScope it
    }
    throw NotFoundException("Topic id [$id] don't exist")
  }

  private inline fun <reified T> getFromAssets(path: String): T {
    val inputStream = assetManager.open(path)
    val bytes = ByteArray(inputStream.available())
    inputStream.read(bytes)
    val json = String(bytes)
    val type = object : TypeToken<T>() {}.type
    GlobalLog.log(txt = "converting from json :: json=$json ::\n\n Thread=${Thread.currentThread()}")
    inputStream.close()
    return Gson().fromJson(json, type)
  }

  companion object {
    const val topics_fileName = "topics.json"
    const val topicsQuestions_directory = "topics"
  }
}