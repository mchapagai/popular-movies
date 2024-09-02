import java.io.ByteArrayOutputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("kotlin-kapt")
}

fun getVersionName(): String? {
    return try {
        val stdout = ByteArrayOutputStream()
        exec {
            commandLine("git", "describe", "--tags", "--always")
            standardOutput = stdout
        }
        stdout.toString().trim()
    } catch (ignored: Exception) {
        null
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.isFork = true
    options.forkOptions.jvmArgs?.addAll(
        listOf(
            // Essential for ButterKnife
            "--add-opens=jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED",

            // If you need Glide or Lombok
            "--add-opens=jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED",
            "--add-opens=jdk.compiler/com.sun.tools.javac.comp=ALL-UNNAMED",
            "--add-opens=jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED",
            "--add-opens=jdk.compiler/com.sun.tools.javac.main=ALL-UNNAMED",
            "--add-opens=jdk.compiler/com.sun.tools.javac.model=ALL-UNNAMED",
            "--add-opens=jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED",
            "--add-opens=jdk.compiler/com.sun.tools.javac.processing=ALL-UNNAMED",
            "--add-opens=jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED",
            "--add-opens=jdk.compiler/com.sun.tools.javac.jvm=ALL-UNNAMED"
        )
    )
}

android {
    namespace = "com.mchapagai.movies"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mchapagai.movies"
        minSdk = 21
        targetSdk = (34)
        versionCode = 1
        versionName = getVersionName()
        testInstrumentationRunner =
            "android.support.test.runner.AndroidJUnitRunner" // Consider updating to androidx

        vectorDrawables.useSupportLibrary = true

        val p = Properties()
        p.load(project.rootProject.file("gradle.properties").reader())
        val yourKey: String = p.getProperty("API_KEY")
        buildConfigField("String", "API_KEY", "\"$yourKey\"")

        buildConfigField("String", "getVersionName", "\"${getVersionName()}\"")
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            isDebuggable = true
            enableAndroidTestCoverage = true
            enableUnitTestCoverage = true
        }

        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    packaging {
        resources {
            excludes += setOf(
                "META-INF/DEPENDENCIES",
                "META-INF/NOTICE",
                "META-INF/LICENSE", "META-INF/LICENSE.txt",
                "META-INF/NOTICE.txt",
                "META-INF/ASL2.0",
                "META-INF/notice.txt",
                "META-INF/license.txt",
                "META-INF/rxjava.properties",
                "META-INF/services/javax.annotation.processing.Processor"
            )
        }
    }

    lint {
        abortOnError = false
        lintConfig = file("lint.xml")
    }
}

dependencies {
    // Compose
//    implementation(libs.androidx.material3)
    // Android Studio Preview support
//    implementation(libs.androidx.ui.tooling.preview)
//    implementation(libs.androidx.navigation.compose)
//    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.core)
    implementation(libs.material)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.palette)
    implementation(libs.androidx.constraintlayout)

    // Guava
    implementation(libs.apache.commons)
    implementation(libs.firebase.crashlytics.buildtools)

    implementation(libs.leakcanary.android)

    // Retrofit, RxJava, OkHttp3, Picasso
    implementation(libs.retrofit.converter.gson)
    implementation(libs.retrofit)
    implementation(libs.retrofit.adapter.rxjava2)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.rxandroid)
    implementation(libs.rxjava) // Assuming this is a custom alias for RxJava 2.x
    implementation(libs.picasso)

    // Dagger2
    implementation(libs.dagger)
    annotationProcessor(libs.dagger.compiler)
    implementation(libs.dagger.android)
    implementation(libs.dagger.android.support)
    // dagger-android-
    annotationProcessor(libs.dagger.android.processor)
    kapt(libs.dagger.compiler)
    kapt(libs.dagger.android.processor)
    // ButterKnife
    implementation(libs.jakewharton.butterknife)
    kapt(libs.jakewharton.butterknife.compiler)
}