package _Self.buildTypes

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object Build : BuildType({
    name = "Build"

    vcs {
        root(HttpsGithubComJoelrodriguesvieiraApiNodeTypescriptJestRefsHeadsMain)
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
    }
})
