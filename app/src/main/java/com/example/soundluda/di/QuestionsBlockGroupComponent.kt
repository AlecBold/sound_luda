package com.example.soundluda.di

import androidx.navigation.Navigator
import com.example.domain.repository.TopicsRepository
import com.example.domain.usecase.TopicQuestionsInteractor
import com.example.domain.usecase.TopicQuestionsUseCase
import com.example.soundluda.viewmodel.QuestionsBlockGroupViewModel
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Named


@PerScreen
@Component(
  dependencies = [AppComponent::class],
  modules = [QuestionsBlockGroupModule::class]
)
interface QuestionsBlockGroupComponent {

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun idTopic(@TopicId id: String): Builder

    @BindsInstance
    fun rangeQuestionsGroup(@Named(RANGE_QUESTIONS_GROUP) from: Pair<Int, Int>): Builder


    fun appComponent(appComponent: AppComponent): Builder

    fun build(): QuestionsBlockGroupComponent
  }

  fun getQuestionsBlockGroupViewMode(): QuestionsBlockGroupViewModel
}

const val RANGE_QUESTIONS_GROUP = "range_questions_group"


@Module
class QuestionsBlockGroupModule() {

  @PerScreen
  @Provides
  fun provideTopicQuestionsUseCase(
    topicsRepository: TopicsRepository
  ): TopicQuestionsUseCase {
    return TopicQuestionsInteractor(topicsRepository)
  }

  @Provides
  fun provideQuestionsBlockGroupViewModel(
    @TopicId topicId: String,
    @Named(RANGE_QUESTIONS_GROUP) rangeQuestionsGroup: Pair<Int, Int>,
    topicQuestionsUseCase: TopicQuestionsUseCase
  ): QuestionsBlockGroupViewModel {
    return QuestionsBlockGroupViewModel(
      topicId,
      rangeQuestionsGroup,
      topicQuestionsUseCase
    )
  }
}