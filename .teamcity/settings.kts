// .teamcity/settings.kts na raiz do seu repositorio Node.js

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.*
import jetbrains.buildServer.configs.kotlin.triggers.*
import jetbrains.buildServer.configs.kotlin.vcs.*

object CIPipeline : BuildType({
    id("Projeto_API_And_Test")
    name = "CI - Build e Teste Node.js"

    vcs {
        root(RelativeId("ApiNodeTypescriptJest")) // Assumindo que "ApiNodeTypescriptJest" é o ID do seu VCS Root
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