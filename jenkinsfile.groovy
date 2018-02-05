node {
   def mvnHome
   stage('Checkout') { // for display purposes
      // Get some code from a GitHub repository
      git 'https://github.com/ofirgut007/spring-boot-examples.git'
      // Get the Maven tool.
      // ** NOTE: This 'M3' Maven tool must be configured
      // **       in the global configuration.           
      mvnHome = tool 'm1'
   }
   stage('Build') {
      // Run the maven build
      if (isUnix()) {
         sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore  -DskipTests clean package"
      } else {
         bat(/"${mvnHome}\bin\mvn" -Dmaven.test.failure.ignore -DskipTests clean package/)
      }
   }
   stage('Tests') {
      sh "'${mvnHome}/bin/mvn' test"
      junit '**/target/surefire-reports/TEST-*.xml'
      archive 'target/*.jar'
   }
   stage('Deploy') {
        sh "echo test"
        //def dockerimage = docker.build "spring-boot-package-war:${env.version}"
        //def dockerimage = docker.build "spring-boot-package-war:${env.BUILD_NUMBER}"
        //dockerimage.push()
    }
}