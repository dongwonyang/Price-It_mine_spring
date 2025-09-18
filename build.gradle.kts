plugins {
	id("io.spring.dependency-management") version "1.1.7" apply false
	id("org.springframework.boot") version "3.3.4" apply false
	id("java")
}

allprojects {
	group = "project.price_it"
	version = "0.0.1-SNAPSHOT"

	repositories {
		mavenCentral()
	}
}

subprojects {
	apply(plugin = "java")
	apply(plugin = "io.spring.dependency-management")

	extensions.configure<JavaPluginExtension> {
		toolchain {
			languageVersion.set(JavaLanguageVersion.of(17))
		}
	}

	dependencies {
		// lombok
		add("compileOnly", "org.projectlombok:lombok:1.18.32")
		add("annotationProcessor", "org.projectlombok:lombok:1.18.32")
		add("testCompileOnly", "org.projectlombok:lombok:1.18.32")
		add("testAnnotationProcessor", "org.projectlombok:lombok:1.18.32")

		// 테스트용 Spring Boot
		add("testImplementation", "org.springframework.boot:spring-boot-starter-test")
		add("implementation", "org.springframework.boot:spring-boot-starter-data-jpa")
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}
}
