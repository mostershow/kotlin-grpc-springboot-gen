import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.google.protobuf.gradle.*

buildscript {
	dependencies {
		classpath("com.google.protobuf:protobuf-gradle-plugin:0.8.13")
	}
}

plugins {
	id("org.springframework.boot") version "2.4.5"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("com.google.protobuf") version "0.8.13"
	id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
	kotlin("jvm") version "1.4.32"
	kotlin("plugin.spring") version "1.4.32"
}

group = "com.jj"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

allprojects {
	repositories {
		mavenLocal()
		mavenCentral()
		jcenter()
		google()
	}

	apply(plugin = "org.jlleitschuh.gradle.ktlint")
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	implementation("io.grpc:grpc-protobuf:1.33.1")
	implementation("io.grpc:grpc-stub:1.33.1")
	implementation("io.grpc:grpc-netty:1.33.1")
	api("com.google.protobuf:protobuf-java-util:3.13.0")
	implementation("io.grpc:grpc-all:1.33.1")
	api("io.grpc:grpc-kotlin-stub:0.2.1")
	implementation("io.grpc:protoc-gen-grpc-kotlin:0.1.5")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9")
	implementation("com.google.protobuf:protobuf-gradle-plugin:0.8.13")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

protobuf {
	protoc{
		artifact = "com.google.protobuf:protoc:3.10.1"
	}
	generatedFilesBaseDir = "$projectDir/src/"
	plugins {
		id("grpc"){
			artifact = "io.grpc:protoc-gen-grpc-java:1.33.1"
		}
		id("grpckt") {
			artifact = "io.grpc:protoc-gen-grpc-kotlin:0.1.5"
		}
	}
	generateProtoTasks {
		all().forEach {
			it.plugins {
				id("grpc")
				id("grpckt")
			}
		}
	}
}
