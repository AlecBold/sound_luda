plugins {
  id("com.android.library")
  id("org.jetbrains.kotlin.android")
  id("kotlin-kapt")
}

android {
  namespace = "com.example.di"
  compileSdk = SDK.compileSDK

  defaultConfig {
    minSdk = SDK.minSDK

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_18
    targetCompatibility = JavaVersion.VERSION_18
  }
  kotlinOptions {
    jvmTarget = "18"
  }
}

dependencies {
  implementation(project(":core"))

  implementation(Dagger.dagger)
  kapt(Dagger.daggerCompiler)

  implementation(Deps.core)
  implementation(Deps.appCompat)
  implementation(Material.material)

  testImplementation(TestImplementation.jUnit)
  androidTestImplementation(AndroidTestImplementation.testExtJunit)
  androidTestImplementation(AndroidTestImplementation.testEspressoCore)
}