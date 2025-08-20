pipeline {
  agent any
  stages {
    stage('Build & Package') {
      steps {
        echo "Package Project"
        sh "mvn package"
        sh "mv /var/lib/jenkins/workspace/HandsAppPipeline/target/HandsApp.war /var/lib/jenkins/workspace/HandsAppPipeline/target/ROOT.war"
        echo "End Build and Package"
      }
    }
    stage('Deploy') {
      steps {
        echo "Deploy Project"
        sh "scp -r root@195.161.62.229:/opt/tomcat/webapps/ROOT root@195.161.62.229:/opt/backups/Jenkins"
        sh "scp /var/lib/jenkins/workspace/HandsAppPipeline/target/ROOT.war root@195.161.62.229:/opt/tomcat/webapps"
        sh "sleep 30"
        sh "scp -r root@195.161.62.229:/opt/backups/Jenkins/ROOT/resources/video root@195.161.62.229:/opt/tomcat/webapps/ROOT/resources"
        sh "scp -r root@195.161.62.229:/opt/backups/Jenkins/ROOT/resources/img/users root@195.161.62.229:/opt/tomcat/webapps/ROOT/resources/img/users"
        sh "scp root@195.161.62.229:/opt/backups/Jenkins/ROOT/WEB-INF/classes/application.properties root@195.161.62.229:/opt/tomcat/webapps/ROOT/WEB-INF/classes/application.properties"
        echo "End Deploy Project"
      }
    }
    stage('Restart Tomcat') {
      steps {
        echo "Restart Tomcat"
        sh "ssh root@195.161.62.229 /opt/tomcat/bin/shutdown.sh"
        sh "sleep 5"
        sh "ssh root@195.161.62.229 /opt/tomcat/bin/startup.sh"
        echo "End Restart Tomcat"
      }
    }
  }
}
