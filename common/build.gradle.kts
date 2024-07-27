plugins {
  alias(libs.plugins.jetbrainsKotlinJvm)
  alias(libs.plugins.anvil)
}

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
  jvmToolchain(17)
}

dependencies {
  api(libs.dagger)
  api(libs.javax.inject)
  implementation(libs.kotlinx.coroutines.core)
}