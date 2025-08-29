plugins {
    `java-library`
    `maven-publish`
    alias(libs.plugins.shadow)
    alias(libs.plugins.run.paper)
}

group = "net.vertrauterdavid"
version = "1.0"
description = "Event Server System with tons of useful commands and features"

repositories {
    mavenLocal()
    mavenCentral()

    //PaperMC
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(libs.paper.api)
    compileOnly(libs.lombok)
    compileOnlyApi(libs.placeholderapi)
    annotationProcessor(libs.lombok)
}

tasks {
    jar {
        enabled = false
    }

    shadowJar {
        archiveFileName = "${rootProject.name}-${project.version}.jar"
        archiveClassifier = null


        manifest {
            attributes["Implementation-Version"] = rootProject.version
            attributes["paperweight-mappings-namespace"] = "mojang"
        }
    }

    assemble {
        dependsOn(shadowJar)
    }

    withType<JavaCompile> {
        options.encoding = Charsets.UTF_8.name()
        options.release = 21
    }

    withType<Javadoc>() {
        options.encoding = Charsets.UTF_8.name()
    }

    defaultTasks("build")

    val javaVersion = JavaLanguageVersion.of(21)

    val jvmArgsExternal = listOf(
        "-Dcom.mojang.eula.agree=true"
    )

    val sharedPlugins = runPaper.downloadPluginsSpec {
        url("https://github.com/ViaVersion/ViaVersion/releases/download/5.3.2/ViaVersion-5.3.2.jar")
        url("https://github.com/ViaVersion/ViaBackwards/releases/download/5.3.2/ViaBackwards-5.3.2.jar")
    }

    runServer {
        minecraftVersion(version)
        runDirectory = rootDir.resolve("run/paper/$version")

        javaLauncher = project.javaToolchains.launcherFor {
            languageVersion = javaVersion
        }

        downloadPlugins {
            from(sharedPlugins)
        }

        jvmArgs = jvmArgsExternal
    }

    runPaper.folia.registerTask {
        minecraftVersion(version)
        runDirectory = rootDir.resolve("run/folia/$version")

        javaLauncher = project.javaToolchains.launcherFor {
            languageVersion = javaVersion
        }

        downloadPlugins {
            from(sharedPlugins)
        }

        jvmArgs = jvmArgsExternal
    }
}