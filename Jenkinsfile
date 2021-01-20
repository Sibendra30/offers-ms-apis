@Library('shared-lib-pipeline')_

node {
    
	stage('Welcome Script') {
                sh 'echo Welcome to Jenkins pipeline'
		sayHello 'Sibendra Singh from Kolkata...'
        }
        stage('Check Maven Version') {
                sh 'mvn --version'
        }
	stage('Checkout') {
		checkout scm
	}
	stage('Clean Project') {
                sayHello.clean()
        }
	stage('Build Project') {
                sh 'mvn install'
        }
	stage('Unit Test') {
                sh 'mvn test'
        }
}
