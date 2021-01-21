@Library('shared-lib-pipeline')_
import com.example.SharedLib

node {
    
	stage('Welcome Script') {
                sh 'echo Welcome to Jenkins pipeline...'
		def lib = new SharedLib()
		def var1 = lib.addNumber()
		sh 'echo ${var1}'
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
