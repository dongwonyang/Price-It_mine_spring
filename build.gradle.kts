import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("io.spring.dependency-management") version "1.1.7" apply false
	id("org.springframework.boot") version "3.3.4" apply false
	id("org.jetbrains.kotlin.jvm") version "1.9.0" apply false
	id("org.jetbrains.kotlin.plugin.spring") version "1.9.0" apply false
	id("java")
	kotlin("plugin.jpa") version "1.9.0"
}

allprojects {
	group = "project.price_it"
	version = "0.0.1-SNAPSHOT"

	repositories {
		mavenCentral()
	}
}

subprojects {
	apply(plugin = "org.jetbrains.kotlin.jvm")
	apply(plugin = "org.jetbrains.kotlin.plugin.spring")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "java")
	apply(plugin = "org.jetbrains.kotlin.plugin.jpa")

	extensions.configure<JavaPluginExtension> {
		toolchain {
			languageVersion.set(JavaLanguageVersion.of(17))
		}
	}

	dependencies {
		// Kotlin
		"implementation"("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
		"implementation"("org.jetbrains.kotlin:kotlin-reflect")

		// Lombok
		"compileOnly"("org.projectlombok:lombok:1.18.32")
		"annotationProcessor"("org.projectlombok:lombok:1.18.32")
		"testCompileOnly"("org.projectlombok:lombok:1.18.32")
		"testAnnotationProcessor"("org.projectlombok:lombok:1.18.32")

		// Spring Boot
		"implementation"("org.springframework.boot:spring-boot-starter-data-jpa")
		"testImplementation"("org.springframework.boot:spring-boot-starter-test")
	}

	// allOpen 플러그인 설정 (Kotlin 클래스 자동 open)
	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs += listOf("-Xjsr305=strict")
			jvmTarget = "17"
		}
	}



	tasks.withType<Test> {
		useJUnitPlatform()
	}
}
