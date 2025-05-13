import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.*
import jetbrains.buildServer.configs.kotlin.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.triggers.Trigger
import jetbrains.buildServer.configs.kotlin.vcs.*

object CIPipeline : BuildType({
    id("ApiNodeTypescriptJest_Build")
    name = "Build"

    vcs {
        root(RelativeId("ApiNodeTypescriptJest_HttpsGithubComJoelrodriguesvieiraApiNodeTypescriptJestRefsHeadsMain")) // Assumindo que "ApiNodeTypescriptJest" é o ID do seu VCS Root
    }

    steps {
        script {
            name = "Instalar Dependencias (npm)"
            workingDir = ""
            scriptContent = """
                npm install
            """.trimIndent()
        }

        script {
            name = "Verificar Compilacao Typescript"
            workingDir = ""
            scriptContent = """
                npx tsc --noEmit
            """.trimIndent()
        }
    }

    triggers {
        vcs {
        }
    }

    artifacts {
    }
})