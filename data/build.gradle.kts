plugins {
  id("com.android.library")
  id("org.jetbrains.kotlin.android")
}

android {
  compileSdk = SDK.compileSDK

  namespace = "com.example.soundluda"

  defaultConfig {
    minSdk = SDK.minSDK
    targetSdk = SDK.targetSDK

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
  implementation(project(":domain"))

  implementation(Room.roomRuntime)
  annotationProcessor(Room.roomCompiler)
  implementation(Room.roomKtx)

  implementation(Gson.gson)

  implementation(Deps.core)
  implementation(Deps.appCompat)
  implementation(Material.material)
  testImplementation(TestImplementation.jUnit)
  androidTestImplementation(AndroidTestImplementation.testExtJunit)
  androidTestImplementation(AndroidTestImplementation.testEspressoCore)
}