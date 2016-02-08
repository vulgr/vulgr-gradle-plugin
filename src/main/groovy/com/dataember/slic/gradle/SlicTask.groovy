package com.dataember.slic.gradle

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

import javax.inject.Inject

/**
 * Created by chaospie on 08/02/16.
 */
class SlicTask extends ConventionTask implements Reporting<DependencyReportContainer> {


    private final DefaultDependencyReportContainer reports;

    private Set<Project> projects;


    SlicTask() {
        println "test test"
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
        println "test test test"

        JsonProjectDependencyRenderer renderer =
                new JsonProjectDependencyRenderer(getVersionScheme(), getVersionComparator())

        projects.each {
            println it.name
            println renderer.render(it)
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
}
