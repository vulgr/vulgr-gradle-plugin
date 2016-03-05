package com.dataember.vulgr.gradle

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.apache.log4j.Logger
import org.gradle.api.Project
import org.gradle.api.internal.ConventionTask
import org.gradle.api.internal.artifacts.ivyservice.ivyresolve.strategy.VersionComparator
import org.gradle.api.internal.artifacts.ivyservice.ivyresolve.strategy.VersionSelectorScheme
import org.gradle.api.reporting.Reporting
import org.gradle.api.reporting.dependencies.DependencyReportContainer
import org.gradle.api.reporting.dependencies.internal.DefaultDependencyReportContainer
import org.gradle.api.reporting.dependencies.internal.JsonProjectDependencyRenderer
import org.gradle.api.tasks.TaskAction
import org.gradle.internal.reflect.Instantiator
import org.gradle.util.GFileUtils

import javax.inject.Inject

/**
 * Created by chaospie on 08/02/16.
 */
class VulgrTask extends ConventionTask implements Reporting<DependencyReportContainer> {

    Logger log = Logger.getLogger(VulgrTask.class)

    private final DefaultDependencyReportContainer reports;

    private Set<Project> projects;

    private boolean upload;

    VulgrTask() {
        reports = getInstantiator().newInstance(DefaultDependencyReportContainer.class, this)
    }

    @Override
    DependencyReportContainer getReports() {
        return reports
    }

    @Override
    DependencyReportContainer reports(Closure closure) {
        reports.configure(closure)
        return reports
    }

    @TaskAction
    void generate() {
        JsonProjectDependencyRenderer renderer =
                new JsonProjectDependencyRenderer(getVersionScheme(), getVersionComparator())

        projects.each {
            /**
             * Using the json string directly generates invalid json,
             * so create a Gson JsonObject first.
             */
            println("Generating JSON dependencies for " + it.name + ".")
            JsonParser parser = new JsonParser();
            JsonObject obj = parser.parse(renderer.render(it)).getAsJsonObject()
            JsonObject updatedObj = obj.get("project")
            updatedObj.addProperty("version", it.version.toString())
            String depsFile = it.file("${it.buildDir}/libs/${it.name}-${it.version}-dependencies.json")
            GFileUtils.writeFile(updatedObj.toString(), new File(depsFile), "utf-8")
        }

    }

    @Inject
    Instantiator getInstantiator() {

    }

    @Inject
    VersionSelectorScheme getVersionScheme() {

    }

    @Inject
    VersionComparator getVersionComparator() {

    }

    void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    void setUpload(boolean upload) {
        this.upload = upload;
    }
}
