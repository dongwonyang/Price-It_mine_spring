plugins {
    id("java")
}

group = "project.price_it"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(mapOf("path" to ":domain")))

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // Aop
    implementation("org.springframework.boot:spring-boot-starter-aop")
}

tasks.test {
    useJUnitPlatform()
}