plugins {
	java
	id("org.springframework.boot") version "3.5.5"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "project.price_it"
version = "0.0.1-SNAPSHOT"
description = "crowdSourcing_api"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

allprojects {
	repositories {
		mavenCentral()
	}
}

springBoot {
	mainClass = "project.price_it.api.ApiApplication"
}



subprojects {
	apply(plugin = "java-library")
	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")

	group = "project.graduation.crowd_sourcing"
	version = "0.0.1-SNAPSHOT"

	configurations {
		compileOnly {
			extendsFrom(configurations.annotationProcessor.get())
		}
	}

	dependencies {
		implementation("org.springframework.boot:spring-boot-starter")
		testImplementation("org.springframework.boot:spring-boot-starter-test")

		// Lombok
		compileOnly("org.projectlombok:lombok:1.18.32")
		annotationProcessor("org.projectlombok:lombok:1.18.32")
		testCompileOnly("org.projectlombok:lombok:1.18.32")
		testAnnotationProcessor("org.projectlombok:lombok:1.18.32")
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}
