package com.github.johnbarry.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class GreetingPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register("greet") {
            it.doLast { println("Hello from ")  }
        }
    }
}
