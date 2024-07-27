import com.android.build.api.variant.AndroidComponentsExtension

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
  alias(libs.plugins.androidApplication) apply false
  alias(libs.plugins.jetbrainsKotlinAndroid) apply false
  alias(libs.plugins.anvil) apply false
  alias(libs.plugins.jetbrainsKotlinJvm) apply false
  alias(libs.plugins.androidLibrary) apply false
  alias(libs.plugins.ksp) apply false
}

allprojects {
  layout.buildDirectory.set(File("${rootProject.rootDir}/moduleBuildDirs/${rootProject.relativePath(project.projectDir)}/build"))
}