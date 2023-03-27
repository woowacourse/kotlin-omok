plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    // kotlinx serialization
    kotlin("plugin.serialization") version "1.8.10"
    // test
    id("de.mannodermaus.android-junit5") version "1.8.2.1"
}

android {
    namespace = "woowacourse.omok"
    compileSdk = 33

    defaultConfig {
        applicationId = "woowacourse.omok"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    implementation(project(":domain"))
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.0")
    implementation("com.google.android.material:material:1.7.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // kotlinx serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")

    // Test
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.2")
    testImplementation("junit:junit:4.13.2")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.8.2")
    testImplementation("org.junit.jupiter", "junit-jupiter", "5.8.2")
    testImplementation("org.assertj", "assertj-core", "3.22.0")
}
