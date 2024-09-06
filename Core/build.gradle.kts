import java.util.Properties
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-parcelize")
}

android {
    namespace = "com.mchapagai.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        val p = Properties()
        p.load(project.rootProject.file("gradle.properties").reader())
        val yourKey: String = p.getProperty("API_KEY")
        buildConfigField("String", "API_KEY", "\"$yourKey\"")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.androidx.runtime)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.runtime.rxjava2)
    implementation(libs.androidx.core.ktx)
    // LiveData
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.android)
    // Retrofit
    implementation(libs.retrofit)
    // Gson to convert JSON responses into Java/Kotlin Objects
    implementation(libs.retrofit.converter.gson)
    // OkHttp - configure the HTTP client
    implementation(libs.okhttp)
    // OkHttp - logging interceptor
    implementation(libs.okhttp.logging.interceptor)
    // RxJava Adapter is used for handling asynchronous calls
    implementation(libs.adapter.rxjava2.v230)
    // RxJava
    implementation(libs.rxandroid)
    implementation(libs.rxjava)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}