plugins {
  alias(libs.plugins.jetbrainsKotlinJvm)
  alias(libs.plugins.ksp)
}

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
  ksp(libs.autoservice.ksp)

  implementation(project(":common"))
  implementation(libs.anvil.compiler.api)
  implementation(libs.anvil.compiler.utils)
  implementation(libs.autoservice.annotations)
  implementation(libs.kotlinpoet.ksp)
  implementation(libs.ksp.api)
}