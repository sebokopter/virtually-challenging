plugins {
    id("com.android.library")
    kotlin("android")
}

group = "de.heilsen.compat.edittext"
version = "0.1"

android {
    compileSdkVersion(30)
    defaultConfig {
        minSdkVersion(3)
    }
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("androidx.core:core:1.3.2")
}