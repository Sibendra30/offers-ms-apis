node {
    
    stages {
        stage('Check Maven Version') {
            steps {
                sh 'mvn --version'
            }
        }
	stage('Clean Project') {
            steps {
                sh 'mvn clean'
            }
        }
	stage('Build Project') {
            steps {
                sh 'mvn install'
            }
        }
	stage('Unit Test') {
            steps {
                sh 'mvn test'
            }
        }
    }
}
