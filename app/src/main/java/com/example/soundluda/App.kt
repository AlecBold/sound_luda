package com.example.soundluda

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.example.soundluda.di.AppComponent
import com.example.soundluda.di.DaggerAppComponent

class App : Application() {


  override fun onCreate() {
    super.onCreate()
    app = this
    appComponent = DaggerAppComponent
      .builder()
      .context(context = this)
      .build()
  }

  companion object {
    lateinit var app: App
      private set
    lateinit var appComponent: AppComponent
      private set
  }
}

//val Context.appComponent: AppComponent
//  get() = when (this) {
//    is App -> this.appComponent
//    else -> this.applicationContext.appComponent
//  }