import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import java.util.*

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt") // because of room
}

group = "de.heilsen.virtuallychallenging"
version = "0.3"

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.3")

    defaultConfig {
        applicationId("de.heilsen.virtuallychallenging")
        minSdkVersion(16)
        targetSdkVersion(30)
        versionCode(3)
        versionName(project.version as String)

        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")

        vectorDrawables.useSupportLibrary = true

        multiDexEnabled = true
    }

    signingConfigs {
        val properties = Properties().apply {
            load(file("keystore.properties").inputStream())
        }
        register("appSigning") {
            storeFile(file("virtually-challenging-keystore.jks"))
            storePassword(properties.getProperty("storePassword"))
            keyAlias("app-signing-key")
            keyPassword(properties.getProperty("keyPassword"))
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles = mutableListOf(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                file("proguard-rules.pro")
            )
            signingConfig = signingConfigs.named("appSigning").get()
        }
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles = mutableListOf(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                file("proguard-rules.pro"),
                file("proguard-rules-debug.pro")
            )
        }
    }

    compileOptions {
        // Flag to enable support for the new language APIs
        isCoreLibraryDesugaringEnabled = true
        // Sets Java compatibility to Java 8
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8" // Jetpack ktx extensions require Java 8
        useIR = true //opt in to new compiler
    }

    sourceSets.all {
        java.srcDirs("src/$name/kotlin")
    }

    sourceSets.named("test") {
        java.srcDirs("src/$name/kotlin", "src/sharedTest/kotlin")
        withConvention(KotlinSourceSet::class) {
            kotlin.srcDirs("src/$name/kotlin", "src/sharedTest/kotlin")
        }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    buildFeatures {
        aidl = false
        renderScript = false
        buildConfig = false
        viewBinding = false
        resValues = false
        shaders = false
    }
}

dependencies {
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")

    implementation("androidx.activity:activity-ktx:1.2.2")
    implementation("androidx.fragment:fragment-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")

    implementation("com.google.android.material:material:1.3.0")

    val roomVersion = "2.2.6"
    implementation("androidx.room:room-runtime:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.robolectric:robolectric:4.5.1")
    testImplementation("androidx.test.ext:junit-ktx:1.1.2")
    testImplementation("androidx.test:core-ktx:1.3.0")
    testImplementation("org.mockito:mockito-core:3.7.7")

    androidTestImplementation("androidx.test.ext:junit-ktx:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    androidTestImplementation("org.mockito:mockito-android:3.7.7")
}
