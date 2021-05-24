import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import java.util.*

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt") // because of room
}

group = "de.heilsen.virtuallychallenging"
version = "0.4"
val versionCode = 4

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.3")

    defaultConfig {
        applicationId("de.heilsen.virtuallychallenging")
        minSdkVersion(16)
        targetSdkVersion(30)
        versionCode(versionCode)
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
            keyPassword(properties.getProperty("appSigningKeyPassword"))
        }
        register("upload") {
            storeFile(file("virtually-challenging-keystore.jks"))
            storePassword(properties.getProperty("storePassword"))
            keyAlias("upload-key")
            keyPassword(properties.getProperty("uploadKeyPassword"))
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
        register("releasePlaystore") {
            initWith(getByName("release"))
            signingConfig = signingConfigs.named("upload").get()
        }
        getByName("debug") {
            isMinifyEnabled = false
            isTestCoverageEnabled = false

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
    }

    sourceSets.all {
        java.srcDirs("src/$name/kotlin")
    }

    sourceSets.named("test") {
        java.srcDirs("src/sharedTest/kotlin")
        withConvention(KotlinSourceSet::class) {
            kotlin.srcDirs("src/sharedTest/kotlin")
        }
    }

    sourceSets.named("androidTest") {
        java.srcDirs("src/sharedTest/kotlin")
        withConvention(KotlinSourceSet::class) {
            kotlin.srcDirs("src/sharedTest/kotlin")
        }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    buildFeatures {
        aidl = false
        buildConfig = false
        compose = false
        dataBinding = false
        mlModelBinding = false
        prefab = false
        renderScript = false
        resValues = false
        shaders = false
        viewBinding = false
    }
}

dependencies {
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")

    implementation(project(":edittext-compat"))
    implementation("androidx.activity:activity-ktx:1.2.3")
    implementation("androidx.fragment:fragment-ktx:1.3.4")
    implementation("androidx.appcompat:appcompat:1.3.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.core:core-ktx:1.5.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")

    implementation("com.google.android.material:material:1.3.0")

    val roomVersion = "2.3.0"
    implementation("androidx.room:room-runtime:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation(project(":coroutines-test"))
    testImplementation("org.robolectric:robolectric:4.5.1")
    testImplementation("androidx.test.ext:junit-ktx:1.1.2")
    testImplementation("androidx.test:core-ktx:1.3.0")
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation("org.mockito:mockito-core:3.9.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.0")

    androidTestImplementation("androidx.test:rules:1.3.0")
    androidTestImplementation("org.hamcrest:hamcrest-core:1.3")
    androidTestImplementation("org.hamcrest:hamcrest-library:1.3")
    androidTestImplementation("androidx.test.ext:junit-ktx:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    androidTestImplementation("com.agoda.kakao:kakao:2.4.0")
    androidTestImplementation("org.mockito:mockito-core:3.9.0")
    androidTestImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")
    androidTestImplementation("com.linkedin.dexmaker:dexmaker-mockito-inline:2.28.1") {
        because("mockito-android can only subclass and therefore not mock/spy final classes")
    }
}
