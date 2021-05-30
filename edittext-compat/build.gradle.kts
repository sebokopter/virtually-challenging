plugins {
    id("com.android.library")
    kotlin("android")
}

group = "de.heilsen.compat.edittext"
version = "0.1"

android {
    compileSdkVersion(30)
    defaultConfig {
        minSdkVersion(4)
    }

    sourceSets.all {
        java.srcDirs("src/$name/kotlin")
    }
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("androidx.core:core:1.5.0")
}