import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.*
import jetbrains.buildServer.configs.kotlin.triggers.*
import jetbrains.buildServer.configs.kotlin.vcs.*
import jetbrains.buildServer.configs.kotlin.requirements.* // Importar requirements
import jetbrains.buildServer.configs.kotlin.ui.* // Importar ui para reportTabs

versionedSettings {
    mode = VersionedSettings.Mode.ENABLED
}

// Define a configuracao de build para o pipeline CI (Build, Lint, Test)
object CIPipeline : BuildType({
    id("Projeto_API_And_Test") // ID unico para esta build config
    name = "CI - Build, Teste Node.js" // Nome na UI

    // Associa ao VCS Root deste novo projeto
    vcs {
        root(RelativeId("ApiNodeTypescriptJest")) // << SUBSTITUA PELO ID REAL DO VCS ROOT DESTE NOVO REPO!

        // Opcional: Filtra para que esta build config apenas faca checkout da branch 'develop'
        // E importante que o VCS Root principal (Passo 2) esteja configurado para monitorar 'develop'

        // branchFilter = "+:refs/heads/develop"
    }

    steps {
        script {
            name = "Instalar Dependencias (npm)"
            workingDir = "" // Raiz do repositorio
            scriptContent = """
                npm install
            """.trimIndent()
        }

        script { // Passo de Testes com Jest
            name = "Rodar Testes (Jest)"
            workingDir = "" // Raiz do repositorio
            scriptContent = """
                # Certifique-se que seu script 'test' no package.json executa o Jest
                npm test
            """.trimIndent()
            // Opcional: Configurar reportTabs se Jest gerar um relatorio no formato JUnit XML
            // Jest pode ser configurado para gerar JUnit XML via configuracao ou reporter
            // reportTabs {
            //      report.teamcity.importReports("junit", "caminho/para/seu/jest-junit.xml") // Verifique o caminho e nome do arquivo gerado pelo Jest
            // }
        }

        script {
             name = "Compilar TypeScript"
             workingDir = ""
             scriptContent = """
                 npx tsc # Para gerar a pasta 'dist'
             """.trimIndent()
        }
    }

    triggers {
        vcs {
            // Dispara esta build config em cada commit
            // Opcional: Filtra para disparar apenas em commits na branch 'develop'
            branchFilter = "+:refs/heads/develop"
        }
    }

    artifacts {
        // Opcional: Coletar relatorios de testes (se configurado o reportTabs acima)
        // artifact("caminho/para/seu/jest-junit.xml => test-reports")

        // Opcional: Coletar a pasta 'dist' se o passo de compilacao gerar output
        // artifact("dist => build_output")

        // Opcional: Coletar a imagem Docker buildada (requer configuracao avancada e acesso ao registry)
        // artifact(".docker/cache => docker_image")
    }

    requirements {
         // Requer um agente que tenha Node.js/npm/npx no PATH
         exists("node")
         exists("npm")
         exists("npx") # npx para rodar tsc, jest, etc. instalados localmente
         // Opcional: Requer Docker se voce incluiu o passo de build da imagem
         # exists("docker")
    }
})