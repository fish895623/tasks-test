import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.DslContext
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.PullRequests
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.commitStatusPublisher
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.pullRequests
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.project
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2019_2.version

version = "2021.2"

project {

  buildType(Build)
}

object Build : BuildType({
  name = "Build"

  vcs {
    root(DslContext.settingsRoot)
  }

  steps {
    script {
      name = "Testing"
      scriptContent = "echo abcd"
    }
  }

  triggers {
    vcs {
    }
  }

  features {
    pullRequests {
      vcsRootExtId = "${DslContext.settingsRoot.id}"
      provider = github {
        authType = token {
          token = "credentialsJSON:fb871c07-1d54-4a49-8fb8-26a235b2b988"
        }
        filterAuthorRole = PullRequests.GitHubRoleFilter.MEMBER_OR_COLLABORATOR
      }
    }
    commitStatusPublisher {
      vcsRootExtId = "${DslContext.settingsRoot.id}"
      publisher = github {
        githubUrl = "https://api.github.com"
        authType = personalToken {
          token = "credentialsJSON:fb871c07-1d54-4a49-8fb8-26a235b2b988"
        }
      }
    }
  }
})
