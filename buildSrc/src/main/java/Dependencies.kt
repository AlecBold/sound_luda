

object Version {
  const val core = "1.7.0"
  const val composeOptionVersion = "1.4.6"
  const val jetpackCompose = "1.4.0"
  const val activityCompose = "1.3.1"
  const val lifecycleRuntime = "2.3.1"
  const val jUnit = "4.13.2"
  const val testExtJunit = "1.1.5"
  const val espressoCore = "3.5.1"
  const val retrofit = "2.9.0"
  const val okhttp = "4.10.0"
  const val coroutines = "1.3.9"
  const val appCompat = "1.6.1"
  const val material = "1.9.0"
  const val navigationComponent = "2.6.0"
  const val lifecycleViewModelCompose = "2.6.1"
  const val room = "2.5.2"
  const val dagger = "2.48"
  const val hilt = "2.40.5"
  const val hiltCompiler = "1.0.0"
  const val gson = "2.8.1"
}

object SDK {
  const val targetSDK = 33
  const val compileSDK = 33
  const val minSDK = 21
}

object Deps {
  const val core = "androidx.core:core-ktx:${Version.core}"
  const val appCompat = "androidx.appcompat:appcompat:${Version.appCompat}"
}

object NavigationComponent {
  const val navCompose = "androidx.navigation:navigation-compose:${Version.navigationComponent}"
  const val navFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Version.navigationComponent}"
  const val navUiKtx = "androidx.navigation:navigation-ui-ktx:${Version.navigationComponent}"
}

object Material {
  const val material = "com.google.android.material:material:${Version.material}"
}

object Coroutines {
  const val kotlinxCoroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutines}"
}

object OkHttp {

  const val okhttp = "com.squareup.okhttp3:okhttp:${Version.okhttp}"
}

object Retrofit {
  const val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofit}"
}

object Room {
  const val roomRuntime = "androidx.room:room-runtime:${Version.room}"
  const val roomCompiler = "androidx.room:room-compiler:${Version.room}"
  const val roomKtx = "androidx.room:room-ktx:${Version.room}"
}

object Dagger {
  const val dagger = "com.google.dagger:dagger:${Version.dagger}"
  const val daggerAndroid = "com.google.dagger:dagger-android:${Version.dagger}"
  const val daggerAndroidSupport = "com.google.dagger:dagger-android-support:${Version.dagger}"
  const val daggerAndroidProcessor = "com.google.dagger:dagger-android-processor:${Version.dagger}"
  const val daggerCompiler = "com.google.dagger:dagger-compiler:${Version.dagger}"
}

object Hilt {
  const val hiltAndroid = "com.google.dagger:hilt-android:${Version.hilt}"
  const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:${Version.hilt}"

  const val hiltLifecycleViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:${Version.hiltCompiler}-alpha03"
  const val hiltCompiler = "androidx.hilt:hilt-compiler:${Version.hiltCompiler}"
  const val hiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:${Version.hiltCompiler}"
}

object Lifecycle {
  const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Version.lifecycleRuntime}"
}

object JetpackCompose {
  const val composeUi =  "androidx.compose.ui:ui:${Version.jetpackCompose}"
  const val composeMaterial = "androidx.compose.material:material:${Version.jetpackCompose}"
  const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview:${Version.jetpackCompose}"
  const val composeActivity = "androidx.activity:activity-compose:${Version.activityCompose}"
  const val composeLifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:${Version.lifecycleViewModelCompose}"
}

object Gson {
  const val gson = "com.google.code.gson:gson:${Version.gson}"
}

object TestImplementation {
  const val jUnit = "junit:junit:${Version.jUnit}"
}

object AndroidTestImplementation {
  const val testExtJunit = "androidx.test.ext:junit:${Version.testExtJunit}"
  const val testEspressoCore = "androidx.test.espresso:espresso-core:${Version.espressoCore}"
  const val composeUiTestJunit = "androidx.compose.ui:ui-test-junit4:${Version.jetpackCompose}"
  const val navComponentTesting = "androidx.navigation:navigation-testing:${Version.navigationComponent}"
}

object DebugImplementation {
  const val uiTooling = "androidx.compose.ui:ui-tooling:${Version.jetpackCompose}"
  const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:${Version.jetpackCompose}"
}