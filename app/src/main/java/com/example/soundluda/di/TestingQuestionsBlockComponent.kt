package com.example.soundluda.di

import com.example.domain.repository.TopicsRepository
import com.example.domain.usecase.TopicQuestionsInteractor
import com.example.domain.usecase.TopicQuestionsUseCase
import com.example.soundluda.viewmodel.TestingBlockQuestionsViewModel
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Named

@PerScreen
@Component(
  dependencies = [AppComponent::class],
  modules = [TestingQuestionsBlockModule::class]
)
interface TestingQuestionsBlockComponent {

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun topicId(@TopicId topicId: String): Builder

    @BindsInstance
    fun rangeQuestions(@Named(RANGE_QUESTIONS) range: Pair<Int, Int>): Builder

    fun appComponent(appComponent: AppComponent): Builder
    fun build(): TestingQuestionsBlockComponent
  }

  fun getTestingQuestionsViewModel(): TestingBlockQuestionsViewModel
}

const val RANGE_QUESTIONS = "range_questions"

@Module
class TestingQuestionsBlockModule {

  @PerScreen
  @Provides
  fun provideTestingQuestionsBlockViewModel(
    topicQuestionsUseCase: TopicQuestionsUseCase,
    @TopicId topicId: String,
    @Named(RANGE_QUESTIONS) range: Pair<Int, Int>
  ): TestingBlockQuestionsViewModel {
    return TestingBlockQuestionsViewModel(
      topicQuestionsUseCase = topicQuestionsUseCase,
      topicId,
      range.first,
      range.second
    )
  }

  @PerScreen
  @Provides
  fun provideTopicQuestionsUseCase(
    topicsRepository: TopicsRepository
  ): TopicQuestionsUseCase {
    return TopicQuestionsInteractor(
      topicsRepository = topicsRepository
    )
  }

}