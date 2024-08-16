plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt") // needed for hilt, TODO: migrate to catalog file for type safety
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.mongodb.realm.kotlin)
}

android {
    namespace = "com.dhaen.daysuntil"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dhaen.daysuntil"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        //    Hilt uses Java 8 features. To enable Java 8 in your project:
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    //==================== Core ====================
    // The ktx library for Android provides Kotlin extensions that make Android development
    // more concise, idiomatic, and pleasant by leveraging Kotlin's features.
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    //==================== Dependency Injection ====================
    // Dagger Hilt https://developer.android.com/training/dependency-injection/hilt-android#kts
    // Needs Kapt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)

    kapt(libs.hilt.android.compiler)


    //==================== Testing ====================
    testImplementation(libs.junit)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)

    //==================== Compose & UI ====================
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //==================== Networking ====================
    // TODO: add networking dependencies

    //==================== Database ====================
    // For a free tier online DB with sync capabilities,
    // add device-sync dependency, see: https://www.mongodb.com/docs/atlas/device-sdks/sdk/kotlin/install/#std-label-kotlin-install-android

    // MongoDB Local-Only Realm SDK
    implementation(libs.mongodb.realm.kotlin.library.base)

    //==================== CoRoutines ====================
    // Need this to use coroutines with the MongoDB Realm SDK (among other use cases)
    implementation(libs.kotlinx.coroutines)

    //==================== Navigation (Compose) ====================
    implementation(libs.androidx.navigation.compose)

}

// Needed for hilt
// Allow references to generated code:
kapt {
    correctErrorTypes = true
}