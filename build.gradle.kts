plugins {
	id("io.spring.dependency-management") version "1.1.7" apply false
	id("org.springframework.boot") version "3.3.4" apply false
	id("java-library") apply false
}

allprojects {
	group = "project.price_it"
	version = "0.0.1-SNAPSHOT"

	repositories {
		mavenCentral()
	}
}

subprojects {
	apply(plugin = "java-library")
	apply(plugin = "io.spring.dependency-management")

	java {
		toolchain {
			languageVersion.set(JavaLanguageVersion.of(17))
		}
	}

	dependencies {
		testImplementation("org.springframework.boot:spring-boot-starter-test")

		// Lombok (공통 적용)
		compileOnly("org.projectlombok:lombok:1.18.32")
		annotationProcessor("org.projectlombok:lombok:1.18.32")
		testCompileOnly("org.projectlombok:lombok:1.18.32")
		testAnnotationProcessor("org.projectlombok:lombok:1.18.32")
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}
