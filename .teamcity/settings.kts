import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.githubIssues

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2021.2"

project {

    buildType(PullRequests)
    buildType(Build)

    params {
        text("git_main_branch", "master", label = "Git Main Branch", description = "The git main or default branch to use in VCS operations.", display = ParameterDisplay.HIDDEN, allowEmpty = false)
        text("github_repository_name", "CloudSPI", label = "The github repository name. Used to connect to it in VCS Roots.", description = "This is the repository slug on github. So for example `cloudspi` or `CloudLoaderMC`. It is interpolated into the global VCS Roots.", display = ParameterDisplay.HIDDEN, allowEmpty = false)
        text("env.PUBLISHED_JAVA_ARTIFACT_ID", "cloudspi", label = "Published artifact id", description = "The maven coordinate artifact id that has been published by this build. Can not be empty.", allowEmpty = false)
        text("env.PUBLISHED_JAVA_GROUP", "ml.cloudmc", label = "Published group", description = "The maven coordinate group that has been published by this build. Can not be empty.", allowEmpty = false)
        text("git_branch_spec", """
                +:refs/heads/(main*)
                +:refs/heads/(4.x)
            """.trimIndent(), label = "The branch specification of the repository", description = "By default all main branches are build by the configuration. Modify this value to adapt the branches build.", display = ParameterDisplay.HIDDEN, allowEmpty = true)
    }

    features {
        githubIssues {
            id = "CloudSPI__IssueTracker"
            displayName = "CloudLoaderMC/CloudSPI"
            repositoryURL = "https://github.com/CloudLoaderMC/CloudSPI"
        }
    }
}

object Build : BuildType({
    templates(AbsoluteId("CloudLoaderMC_SetupGradleUtilsCiEnvironmen"), AbsoluteId("CloudLoaderMC_BuildWithDiscordNotifications"), AbsoluteId("CloudLoaderMC_BuildMainBranches"), AbsoluteId("CloudLoaderMC_BuildUsingGradle"), AbsoluteId("CloudLoaderMC_PublishProjectUsingGradle"), AbsoluteId("CloudLoaderMC_TriggersStaticFilesWebpageGenerator"))
    id("CloudSPI__Build")
    name = "Build"
    description = "Builds and Publishes the main branches of the project."
})

object PullRequests : BuildType({
    templates(AbsoluteId("CloudLoaderMC_BuildPullRequests"), AbsoluteId("CloudLoaderMC_SetupGradleUtilsCiEnvironmen"), AbsoluteId("CloudLoaderMC_BuildWithDiscordNotifications"), AbsoluteId("CloudLoaderMC_BuildUsingGradle"))
    id("CloudSPI__PullRequests")
    name = "Pull Requests"
    description = "Builds pull requests for the project"
})
