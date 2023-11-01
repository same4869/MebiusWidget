package com.pokemon.mebius.framework.buildsrc

/**
 * Created by mistletoe
 * on 2022/1/12
 **/
object Repos {

}

object Dependencies {
    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.agp}"
    const val mebius_log = "com.github.same4869:MebiusLog:0.0.4"
    const val mebius_commlib = "com.github.same4869:MebiusCommlib:0.0.2"
}

object SoraWidget {
}

object Kotlin {
    const val gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val std = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
}

object UIKit {
    const val material = "com.google.android.material:material:1.4.0"
    const val constraint_layout = "androidx.constraintlayout:constraintlayout:2.0.4"
    const val recycler_view = "androidx.recyclerview:recyclerview:1.1.0"
}

object AndroidX {
    const val core = "androidx.core:core:1.5.0"
    const val core_ktx = "androidx.core:core-ktx:1.3.2"
    const val appcompat = "androidx.appcompat:appcompat:1.2.0"
    const val fragment_ktx = "androidx.fragment:fragment-ktx:1.2.0"
    const val lifecycle = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1"
}

object Versions {
    const val compileSdkVersion = 33
    const val minSdkVersion = 21
    const val targetSdkVersion = 33
    const val kotlin = "1.6.20"
    const val agp = "7.0.3"
    const val glide = "4.12.0"
}
