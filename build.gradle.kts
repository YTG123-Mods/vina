plugins {
    kotlin("jvm") version "1.4.30"
    id("fabric-loom") version "0.5-SNAPSHOT"
    `maven-publish`
    id("com.modrinth.minotaur") version "1.1.0"
}

object Globals {
    const val grp = "io.github.ytg1234"
    const val abn = "vina"
    const val version = "1.0.0"

    const val mcVer = "1.16.5"
    const val yarnBuild = "3"

    const val loaderVer = "0.11.1"
    const val fapiVer = "0.30.0+1.16"
    const val flkVer = "1.4.30+build.2"

    const val fapiLapiVer = "1.0.0+8f91dfb63a"
    const val autoConfVer = "3.3.1"
    const val mmVer = "1.16.3"
    const val cc2Ver = "4.8.3"
    const val ccaVer = "2.7.10"

    const val dblVer = "24be1a"

    const val modrinthId = ""
    const val unstable = false

    const val name = "Vina"
}

val exc: Action<ExternalModuleDependency> = Action {
    exclude(group = "net.fabricmc")
    exclude(group = "net.fabricmc.fabric-api")
}

group = Globals.grp
version = Globals.version

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    maven(url = "https://maven.terraformersmc.com/releases") {
        content {
            includeGroup("com.terraformersmc")
        }
    }

    maven(url = "https://dl.bintray.com/ladysnake/libs") {
        content {
            includeGroup("io.github.onyxstudios.Cardinal-Components-API")
        }
    }

    maven(url = "https://jitpack.io/") {
        content {
            includeGroup("com.github.Chocohead")
        }
    }
}

dependencies {
    // MC
    minecraft("com.mojang", "minecraft", Globals.mcVer)
    mappings("net.fabricmc", "yarn", "${Globals.mcVer}+build.${Globals.yarnBuild}", classifier = "v2")

    // FLoader
    modImplementation("net.fabricmc", "fabric-loader", Globals.loaderVer)

    // Fabric and FLK
    modImplementation("net.fabricmc.fabric-api", "fabric-api", Globals.fapiVer)
    modImplementation("net.fabricmc", "fabric-language-kotlin", Globals.flkVer)

    // Config
    modApi("me.sargunvohra.mcmods", "autoconfig1u", Globals.autoConfVer, dependencyConfiguration = exc)
    modApi("me.shedaniel.cloth", "config-2", Globals.cc2Ver, dependencyConfiguration = exc)
    modImplementation("com.terraformersmc", "modmenu", Globals.mmVer, dependencyConfiguration = exc)

    // CCA
    include(modApi("io.github.onyxstudios.Cardinal-Components-API", "cardinal-components-base", Globals.ccaVer, dependencyConfiguration = exc))
    include(modApi("io.github.onyxstudios.Cardinal-Components-API", "cardinal-components-item", Globals.ccaVer, dependencyConfiguration = exc))
    include(modApi("io.github.onyxstudios.Cardinal-Components-API", "cardinal-components-level", Globals.ccaVer, dependencyConfiguration = exc))

    // Load Time
    modRuntime("com.github.Chocohead", "Data-Breaker-Lower", Globals.dblVer)
}

tasks {
    processResources {
        inputs.property("version", Globals.version)

        from(sourceSets["main"].resources.srcDirs) {
            include("fabric.mod.json")
            expand("version" to Globals.version)
        }
    }

    withType<JavaCompile> {
        options.encoding = "UTF-8"
        if (JavaVersion.current().isJava9Compatible) {
            options.compilerArgs.addAll(listOf("--release", "8"))
        } else {
            sourceCompatibility = "8"
            targetCompatibility = "8"
        }
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.useIR = true
        kotlinOptions.jvmTarget = "1.8"
    }

    register<Jar>("sourcesJar") {
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }

    jar {
        from("LICENSE")
    }

    register<com.modrinth.minotaur.TaskModrinthUpload>("publishModrinth") {
        token = System.getenv("MODRINTH_API_TOKEN")
        projectId = Globals.modrinthId
        versionNumber = "v${Globals.version}"
        uploadFile = "${project.buildDir.absolutePath}/libs/${Globals.abn}-${Globals.version}.jar"
        addGameVersion(Globals.mcVer)
        addLoader("fabric")
        addFile("${project.buildDir.absolutePath}/libs/${Globals.abn}-${Globals.version}-dev.jar")
        addFile("${project.buildDir.absolutePath}/libs/${Globals.abn}-${Globals.version}-sources.jar")
        versionName = "${Globals.name} v${Globals.version}"

        releaseType = if (Globals.unstable) "beta" else "release"

        dependsOn(remapJar)

        dependsOn(project.tasks.getByName("sourcesJar"))
    }

    register("allPublish") {
        dependsOn(build)
        dependsOn(publish)
        dependsOn(project.tasks.getByName("publishModrinth"))
        publish.get().mustRunAfter(build)
        project.tasks.getByName("publishModrinth").mustRunAfter(publish)
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifact(tasks.jar) {
                builtBy(tasks.remapJar)
            }
            artifact("${project.buildDir.absolutePath}/libs/${Globals.abn}-${Globals.version}.jar") {
                builtBy(tasks.remapJar)
            }
            artifact(tasks.getByName("sourcesJar")) {
                builtBy(tasks.remapSourcesJar)
            }
        }
    }

    repositories {
        if (System.getenv("MAVEN_REPO") != null) maven(url = System.getenv("MAVEN_REPO"))
    }
}
