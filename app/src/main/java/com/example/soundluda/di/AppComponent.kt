package com.example.soundluda.di

import android.content.Context
import android.content.res.AssetManager
import com.example.data.network.TopicsAssetManagerRepositoryImpl
import com.example.data.network.TopicsNetworkRepository
import com.example.data.repo_impl.TopicsRepositoryImpl
import com.example.domain.repository.TopicsRepository
import com.example.soundluda.App
import com.example.soundluda.ui.MainRouteNavigator
import com.example.soundluda.ui.NavRoute
import com.example.soundluda.ui.RouteNavigator
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Singleton
@Component(
  modules = [AppModule::class]
)
interface AppComponent {
  fun inject(app: App)

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun context(context: Context): Builder

    fun build(): AppComponent
  }

  fun getTopicsRepository(): TopicsRepository

  fun getRouteNavigator(): RouteNavigator

  fun getNavRoute(): NavRoute
}

@Module(includes = [AppDbModule::class, AppNetworkModule::class, RepositoryModule::class])
class AppModule {

  @Singleton
  @Provides
  fun provideAssetManager(context: Context): AssetManager {
    return context.assets
  }

  @Singleton
  @Provides
  fun provideRouteNavigator(): RouteNavigator {
    return MainRouteNavigator()
  }

  @Singleton
  @Provides
  fun provideNavRoute(routeNavigator: RouteNavigator): NavRoute = NavRoute(routeNavigator)

}

@Module
class RepositoryModule {

  @Singleton
  @Provides
  fun providesTopicsRepository(
    topicsNetworkRepository: TopicsNetworkRepository
  ): TopicsRepository {
    return TopicsRepositoryImpl(topicsNetworkRepository)
  }

  @Singleton
  @Provides
  fun provideTopicsNetworkRepository(
    assetManager: AssetManager
  ): TopicsNetworkRepository {
    return TopicsAssetManagerRepositoryImpl(assetManager)
  }
}

@Module
class AppDbModule {

}

@Module
class AppNetworkModule {

  @Singleton
  @Provides
  fun provideTopicsNetworkRepository(
    assetManager: AssetManager
  ) = TopicsAssetManagerRepositoryImpl(assetManager)
}

