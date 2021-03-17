import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt") // because of room
    id("com.google.gms.google-services")
}

group = "de.heilsen.virtuallychallenging"
version = "0.1"

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.3")

    defaultConfig {
        applicationId("de.heilsen.virtuallychallenging")
        minSdkVersion(16)
        targetSdkVersion(30)
        versionCode(1)
        versionName("1.0")

        testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")

        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles = mutableListOf(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                file("proguard-rules.pro")
            )
        }
        getByName("debug") {
            isMinifyEnabled = true
            proguardFiles = mutableListOf(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                file("proguard-rules.pro"),
                file("proguard-rules-debug.pro")
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
        useIR = true
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
    implementation("androidx.activity:activity-ktx:1.2.0")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.0")

    implementation("com.google.android.material:material:1.3.0")

    implementation(platform("com.google.firebase:firebase-bom:26.5.0"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")

    implementation("com.firebaseui:firebase-ui-auth:6.4.0")

    val room_version = "2.2.6"
    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation ("androidx.room:room-ktx:$room_version")

    testImplementation("junit:junit:4.13.1")
    testImplementation("org.robolectric:robolectric:4.5.1")
    testImplementation("androidx.test.ext:junit-ktx:1.1.2")
    testImplementation("androidx.test:core-ktx:1.3.0")
    testImplementation("org.mockito:mockito-core:3.7.7")

    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    androidTestImplementation("org.mockito:mockito-android:3.7.7")
}
