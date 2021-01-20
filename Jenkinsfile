node {
    
	stage('Welcome Script') {
                sh 'echo Welcome to Jenkins pipeline'
        }
        stage('Check Maven Version') {
                sh 'mvn --version'
        }
	stage('Checkout) {
		checkout scm
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
