plugins {
  alias(libs.plugins.jetbrainsKotlinJvm)
}

java {
  sourceCompatibility = JavaVersion.VERSION_11
  targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
  jvmToolchain(11)
}

dependencies {
  project(":common")
}