package com.github.johnbarry.plugin

import org.gradle.internal.impldep.org.junit.rules.TemporaryFolder
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GreetingPluginTest {
    private val  tmpFolder= TemporaryFolder()

    @BeforeAll
    fun setup() {
        tmpFolder.create()
        val buildFile = tmpFolder.newFile("build.gradle")
        buildFile.writeText(
            """
            plugins {
                id 'com.github.johnbarry.plugin.greeting'
            }
             task helloWorld {
                doLast {
                    println 'Hello world!'
                }
            }
        """
        )
        val settingsFile = tmpFolder.newFile("settings.properties")
        settingsFile.writeText("rootProject.name = 'jpb-test'")

    }


    @Test
    fun pluginRegistersATask() {
        // Create a test project and apply the plugin
       // val project = ProjectBuilder.builder().build()
       // project.plugins.apply("com.github.johnbarry.plugin.greeting")

        // Verify the result
     //   assertThat(project.tasks.findByName("greet"))
      //      .isNotNull


        val result = GradleRunner.create()
            .withProjectDir(tmpFolder.root)
            .withArguments("helloWorld") //this task creates a file in my code root
            .withPluginClasspath()
            .build()

        // what files do we have in the testProjectDir root? Only build.gradle and gradle.properties created above
        tmpFolder.root.listFiles()?.forEach { file ->
            println("File: ${file.absoluteFile}")
        }
        assert(
            result.task(":helloWorld")?.outcome == TaskOutcome.SUCCESS
        )

    }
}
