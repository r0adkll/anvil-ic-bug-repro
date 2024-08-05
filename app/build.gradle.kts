import com.google.devtools.ksp.gradle.KspTaskJvm
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  alias(libs.plugins.androidApplication)
  alias(libs.plugins.jetbrainsKotlinAndroid)
  alias(libs.plugins.anvil)
  kotlin("kapt")
}

android {
  namespace = "com.r0adkll.anvil_ic"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.r0adkll.anvil_ic"
    minSdk = 26
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    vectorDrawables {
      useSupportLibrary = true
    }
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  kotlinOptions {
    jvmTarget = "17"
  }
  buildFeatures {
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.5.14"
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

anvil {
  generateDaggerFactories.set(false)
  useKsp(contributesAndFactoryGeneration = true, componentMerging = true)
}

afterEvaluate {
  val kspDebugTask = tasks.named<KspTaskJvm>("kspDebugKotlin")
  val kspKotlinFiles = kspDebugTask.flatMap { it.destination }
  tasks.named<KotlinCompile>("kaptGenerateStubsDebugKotlin").configure {
    source(kspKotlinFiles)
  }
}

dependencies {
  implementation(project(":common"))
  implementation(project(":feed:impl"))

  kapt(libs.dagger)
  kapt(libs.dagger.compiler)

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.ui)
  implementation(libs.androidx.ui.graphics)
  implementation(libs.androidx.ui.tooling.preview)
  implementation(libs.androidx.material3)

  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.ui.test.junit4)
  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(libs.androidx.ui.test.manifest)
}