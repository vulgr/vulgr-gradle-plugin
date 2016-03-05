package com.dataember.vulgr.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by chaospie on 08/02/16.
 */
class VulgrPlugin implements Plugin<Project>{
    @Override
    void apply(Project project) {
        project.extensions.create("vulgr", VulgrPluginExtension.class)
    }
}
