package com.example.soundluda.di

import com.example.domain.repository.TopicsRepository
import com.example.domain.usecase.TopicDataInteractor
import com.example.domain.usecase.TopicDataUseCase
import com.example.domain.usecase.TopicQuestionsInteractor
import com.example.domain.usecase.TopicQuestionsUseCase
import com.example.soundluda.ui.RouteNavigator
import com.example.soundluda.viewmodel.TopicViewModel
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Named

@PerScreen
@Component(
  modules = [TopicScreenModule::class],
  dependencies = [AppComponent::class]
)
interface TopicScreenComponent {

  fun getViewModel(): TopicViewModel

  @Component.Builder
  interface Builder {

    fun appComponent(appComponent: AppComponent): Builder

    @BindsInstance
    fun idTopic(@TopicId idTopic: String): Builder

    fun build(): TopicScreenComponent
  }
}

@Module
class TopicScreenModule() {

  @PerScreen
  @Provides
  fun provideTopicQuestionsUseCase(
    topicsRepository: TopicsRepository
  ): TopicQuestionsUseCase {
    return TopicQuestionsInteractor(topicsRepository)
  }

  @PerScreen
  @Provides
  fun provideTopicDataUseCase(
    topicsRepository: TopicsRepository
  ): TopicDataUseCase {
    return TopicDataInteractor(topicsRepository)
  }


  @PerScreen
  @Provides
  fun provideTopicViewModel(
    topicDataUseCase: TopicDataUseCase,
    @TopicId idTopic: String,
    routeNavigator: RouteNavigator
  ): TopicViewModel {
    return TopicViewModel(topicDataUseCase, idTopic, routeNavigator)
  }
}