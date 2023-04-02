import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation("androidx.core:core-ktx:1.9.0")
                implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
                implementation("androidx.lifecycle:lifecycle-compiler:2.6.0")
                implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.0")
                implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.0")
                implementation("org.apache.poi:poi:5.2.3")
                implementation("org.apache.poi:poi-ooxml:5.2.3")
                implementation("org.apache.logging.log4j:log4j-api:2.20.0")
                implementation("org.apache.logging.log4j:log4j-core:2.20.0")
                implementation(files("E:\\Programiranje\\IdeaProjects\\nrjavaserial-5.2.1.jar"))
                implementation(compose.desktop.currentOs)
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "SerialPortExcel"
            packageVersion = "1.0.0"

            val iconRoot = project.file("src/jvmMain/resources/drawables")

            windows {
                iconFile.set(iconRoot.resolve("launcher_icons/icon.ico"))
            }
        }
    }
}
