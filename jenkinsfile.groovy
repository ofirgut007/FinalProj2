node ("master") {
	def mvnHome
	stage('Checkout') { // for display purposes
		notifySlack()
		// Get some code from a GitHub repository
		git 'https://github.com/ofirgut007/spring-boot-examples.git'
		// Get the Maven tool.
		// ** NOTE: This 'M3' Maven tool must be configured
		// **       in the global configuration.           
		mvnHome = tool 'm1'
	}
	stage('Build') {
		try {
			dir ('spring-boot-package-war') {
				// Run the maven build
				if (isUnix()) {
					sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore  -DskipTests clean package"
                    sh "'${mvnHome}/bin/mvn' versions:set versions:commit -DnewVersion=ofir_${env.BUILD_NUMBER}"
					//sh "git push ${pom.artifactId}-${version}"
				} else {
					bat(/"${mvnHome}\bin\mvn" -Dmaven.test.failure.ignore -DskipTests clean package/)
				}
			}
			} catch (exc) {
				error "ERROR: Failed to package maven"   
			}
   }
   stage('Tests') {
		dir ('spring-boot-package-war') {
			sh "'${mvnHome}/bin/mvn' test"
			junit '**/target/surefire-reports/TEST-*.xml'
			archive 'target/*.jar'
		}
   }
   stage('Deploy') {
		sh "echo Deploy steps"
		//def dockerimage = docker.build "ofirgut007/spring-boot-package-war:${env.BUILD_NUMBER}"
		// docker pull hello-world
		// docker tag hello-world ofirgut007/hello-world
		// docker login --username=ofirgut007 --password=**
		// docker commit -m "added spring" -a "NAME" hello-world ofirgut007/myspringimage:latest
		// docker push ofirgut007/myspringimage
		// dockerimage.push()
    }
    stage('Run') {
		try {
			docker.image("ofirgut007/spring-boot-package-war:${env.BUILD_NUMBER}").run('-p 8080:8080') 
		} catch (error) {

		} finally {
		 
		}
    }
    stage ('Final') {
		notifySlack(currentBuild.result)

    }
}
def notifySlack(String buildStatus = 'STARTED') {
	    // Build status of null means success.
	    buildStatus = buildStatus ?: 'SUCCESS'

	    def color

	    if (buildStatus == 'STARTED') {
			color = '#D4DADF'
	    } else if (buildStatus == 'SUCCESS') {
			color = '#BDFFC3'
	    } else if (buildStatus == 'UNSTABLE') {
			color = '#FFFE89'
	    } else {
			color = '#FF9FA1'
	    }

	    def msg = "${buildStatus}: `${env.JOB_NAME}` #${env.BUILD_NUMBER}:\n${env.BUILD_URL}"

	    slackSend(color: color, message: msg)
}
