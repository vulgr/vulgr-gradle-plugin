package com.dataember.slic.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.DependencyResolveDetails
import org.gradle.api.artifacts.ResolvedConfiguration
import org.gradle.api.reporting.dependencies.internal.JsonProjectDependencyRenderer

/**
 * Created by chaospie on 08/02/16.
 */
class SlicPlugin implements Plugin<Project>{
    @Override
    void apply(Project project) {
        project.extensions.create("test", SlicPluginExtension.class)
    }
}
