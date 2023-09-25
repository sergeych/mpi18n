import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    kotlin("multiplatform") version "1.9.10"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.10"
    id("org.jetbrains.dokka") version "1.9.0"
    `maven-publish`
}

group = "net.sergeych"
version = "0.5.2-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://maven.universablockchain.com")
}

tasks.withType<DokkaTask>().configureEach {
    dokkaSourceSets {
        configureEach { // Or source set name, for single-platform the default source sets are `main` and `test`
            samples.from("$projectDir/src/commonTest/kotlin")
            includes.from("$projectDir/readme.md")
        }
    }
}

tasks.dokkaGfm.configure {
    outputDirectory.set(projectDir.resolve("docs_gfm"))
}

kotlin {
    jvm {
        jvmToolchain(8)
        withJava()
        testRuns.named("test") {
            executionTask.configure {
                useJUnitPlatform()
            }
        }
    }
    js {
        browser {
//            commonWebpackConfig {
//                cssSupport {
//                    enabled.set(true)
//                }
//            }
        }
    }
    val hostOs = System.getProperty("os.name")
    val isArm64 = System.getProperty("os.arch") == "aarch64"
    val isMingwX64 = hostOs.startsWith("Windows")
    @Suppress("UNUSED_VARIABLE") val nativeTarget = when {
        hostOs == "Mac OS X" && isArm64 -> macosArm64("native")
        hostOs == "Mac OS X" && !isArm64 -> macosX64("native")
        hostOs == "Linux" && isArm64 -> linuxArm64("native")
        hostOs == "Linux" && !isArm64 -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }


    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("net.sergeych:mp_stools:1.4.1-SNAPSHOT")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting
        val jsMain by getting
        val jsTest by getting
        val nativeMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }
        val nativeTest by getting
    }

    publishing {
        repositories {
            maven {
                val mavenUser: String by project
                val mavenPassword: String by project
                url = uri("https://maven.universablockchain.com/")
                credentials {
                    username = mavenUser
                    password = mavenPassword
                }
            }
        }
    }
}
