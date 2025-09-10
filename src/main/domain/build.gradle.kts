plugins {
    id("java")
}

group = "project.price_it"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // JPA API
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")

    // Spring Data JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}

tasks.test {
    useJUnitPlatform()
}