plugins {
    id("com.android.application")
}

android {
    namespace = "org.vinegarhq.redeye"
    compileSdk = 34

    defaultConfig {
        applicationId = "org.vinegarhq.redeye"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    val cameraxver = ("1.3.2")
    implementation ("com.vanniktech:android-image-cropper:4.5.0")
    // https://mvnrepository.com/artifact/org.bytedeco/javacv
    implementation("org.bytedeco:javacv:1.5.9")


    implementation ("androidx.camera:camera-core:${cameraxver}")
    implementation ("androidx.camera:camera-camera2:${cameraxver}")
    implementation ("androidx.camera:camera-lifecycle:${cameraxver}")
    implementation ("androidx.camera:camera-video:${cameraxver}")
    implementation ("androidx.camera:camera-view:${cameraxver}")
    implementation ("androidx.camera:camera-extensions:${cameraxver}")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}