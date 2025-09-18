dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:3.3.4")
    }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // JPA API
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")

    // Spring Data JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}