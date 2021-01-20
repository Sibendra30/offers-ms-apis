node {
    
        stage('Check Maven Version') {
                sh 'mvn --version'
        }
	stage('Clean Project') {
                sh 'mvn clean'
        }
	stage('Build Project') {
                sh 'mvn install'
        }
	stage('Unit Test') {
                sh 'mvn test'
        }
}
