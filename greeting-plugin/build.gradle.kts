plugins {
    // Apply the Java Gradle plugin development plugin to add support for developing Gradle plugins
    kotlin("jvm") version "1.7.21"
    `java-gradle-plugin`
}

repositories {
    // Use Maven Central for resolving dependencies
    mavenCentral()
}
val functionalTest = sourceSets.create("functionalTest").apply {
    kotlin.srcDir("src/functionalTest/kotlin")
    //compileClasspath += sourceSets.main.output + configurations.testRuntimeClasspath
    compileClasspath =  compileClasspath + configurations.testRuntimeClasspath
    runtimeClasspath += output + compileClasspath
}
val functionalTestTask = tasks.register<Test>("functionalTest") {
    group = "verification"
    testClassesDirs = functionalTest.output.classesDirs
    classpath = functionalTest.runtimeClasspath
    useJUnitPlatform()
}

dependencies {
    "functionalTestImplementation"("org.junit.jupiter:junit-jupiter:5.7.1")
    "functionalTestImplementation"("org.assertj:assertj-core:3.21.0")
    "functionalTestImplementation"(gradleTestKit())
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}


tasks.check {
    dependsOn(functionalTestTask)
}

gradlePlugin {
    testSourceSets(functionalTest)
}
