buildscript {
    val kotlinVersion = "1.4.30"

    repositories {
        google()
        mavenCentral()
        jcenter {
            content {
                includeModule("org.jetbrains.trove4j", "trove4j")
            }
        }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.1.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter {
            content {
                includeModule("org.jetbrains.trove4j", "trove4j")
            }
        }
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}