import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.4.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.4.31"
    kotlin("plugin.spring") version "1.4.31"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

fun RepositoryHandler.codeArtifact() = maven {
    url = uri("https://prada-568600232361.d.codeartifact.eu-central-1.amazonaws.com/maven/private/")
    credentials {
        username = "aws"

        val codeArtifactToken: String by project
        password = codeArtifactToken
    }
}

repositories {
    codeArtifact()

    mavenCentral()
}

//extra["springCloudVersion"] = "2020.0.2"
val togglzVersion = "2.9.3"

dependencies {
    implementation("de.otto.prada.common:togglz-spring-boot-starter:2.0.0")
    implementation("de.otto.prada:metadata-client-spring-boot-starter:6.0.1")
    implementation("de.otto.prada:product-importer-client-spring-boot-starter:2.0.0")

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    implementation("org.springframework.cloud:spring-cloud-starter-sleuth")
    implementation("org.springframework.cloud:spring-cloud-stream-binder-kafka")
    implementation("io.awspring.cloud:spring-cloud-starter-aws")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    runtimeOnly("io.awspring.cloud:spring-cloud-starter-aws-parameter-store-config")
    runtimeOnly("io.micrometer:micrometer-registry-cloudwatch")
    runtimeOnly("org.togglz:togglz-spring-security:$togglzVersion")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2020.0.2")
        mavenBom("io.awspring.cloud:spring-cloud-aws-dependencies:2.3.1")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
