val kodeinVersion: String by project
val konformVersion: String by project
val kotlinVersion: String by project
val ktorVersion: String by project
val logbackVersion: String by project
val mockkVersion: String by project
val prometeusVersion: String by project


plugins {
    application
    kotlin("jvm") version "1.7.10"
    kotlin("plugin.serialization") version "1.7.10"
    id("io.ktor.plugin") version "2.1.0"
}

group = "wf.poc.ktor2"
version = "0.0.1"
application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

tasks.withType<Test> {
    useJUnitPlatform()
}

configurations.testImplementation {
    exclude(group = "org.jetbrains.kotlin", module = "kotlin-test-junit")
    exclude(group = "junit")
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-cors-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-call-logging-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-metrics-micrometer-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-status-pages:$ktorVersion")
    implementation("io.ktor:ktor-server-request-validation:$ktorVersion")
    implementation("io.micrometer:micrometer-registry-prometheus:$prometeusVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("org.kodein.di:kodein-di-framework-ktor-server-jvm:$kodeinVersion")
    implementation("io.konform:konform:$konformVersion")

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:$kotlinVersion")

    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")
    testImplementation("io.ktor:ktor-server-test-host-jvm:$ktorVersion")
    testImplementation("io.ktor:ktor-client-content-negotiation-jvm:$ktorVersion")

}