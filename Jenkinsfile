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
		sayHello.checkoutSCM()
	}
	stage('Clean Project') {
                sayHello.clean()
        }
	stage('Build Project') {
               sayHello.build()
        }
	stage('Unit Test') {
                sayHello.test()
        }
}
