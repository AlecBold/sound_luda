package com.example.soundluda.di

import com.example.domain.repository.TopicsRepository
import com.example.domain.usecase.AvailableTopicsInteractor
import com.example.domain.usecase.AvailableTopicsUseCase
import com.example.soundluda.ui.RouteNavigator
import com.example.soundluda.viewmodel.ListTopicsViewModel
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides

@PerScreen
@Component(
  modules = [ListTopicsScreenModule::class],
  dependencies = [AppComponent::class]
)
interface ListTopicsScreenComponent {

  fun getViewModel(): ListTopicsViewModel

  @Component.Builder
  interface Builder {

    fun appComponent(appComponent: AppComponent): Builder

    fun build(): ListTopicsScreenComponent
  }
}

@Module
class ListTopicsScreenModule {

  @Provides
  @PerScreen
  fun providesAvailableTopicsUseCase(topicsRepository: TopicsRepository): AvailableTopicsUseCase {
    return AvailableTopicsInteractor(topicsRepository)
  }

  @Provides
  @PerScreen
  fun provideViewModel(
    availableTopicsUseCase: AvailableTopicsUseCase,
    routeNavigator: RouteNavigator
  ): ListTopicsViewModel = ListTopicsViewModel(availableTopicsUseCase, routeNavigator)

}