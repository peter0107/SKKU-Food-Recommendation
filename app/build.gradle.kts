import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

var properties=Properties()
properties.load(FileInputStream("local.properties"))


android {
    namespace = "com.example.finalproject"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.finalproject"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String","NATIVE_KEY",properties.getProperty("kakaoNativeKey"))
        buildConfigField("String","KEY_HASH",properties.getProperty("keyhash"))
        buildConfigField("String","GPT_API_KEY",properties.getProperty("openaiApiKey"))


    }

    buildTypes {
        debug{
            isMinifyEnabled=false
            manifestPlaceholders["NATIVE_KEY"]=properties["kakaoNativeKey"] as String
            //manifestPlaceholders["MAP_KEY"]=properties["googleMapKey"] as String
        }

        release {
            isMinifyEnabled = false
            manifestPlaceholders["NATIVE_KEY"]=properties["kakaoNativeKey"] as String
            //manifestPlaceholders["MAP_KEY"]=properties["googleMapKey"] as String
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures{
        buildConfig=true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.android.gms:play-services-location:21.3.0")
    implementation("com.google.maps.android:android-maps-utils:3.8.0")
    //kakao login
    implementation("com.kakao.sdk:v2-user:2.20.1")
    implementation("com.github.kofigyan:StateProgressBar:69b4192777")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("com.github.ybq:Android-SpinKit:1.4.0")


}