pipeline {
    agent any
    parameters {
        string(name: 'game', defaultValue: '', description: '')
    }
    stages {
        stage('Test') {
            steps {
                withDockerContainer(image: 'maven:3.3.9', toolName: 'latest') {
                    sh "mvn clean install -U -DgameId=${game} -Dtestng.suite.xml=testng.xml"
                }
            }
        }
    }
}
