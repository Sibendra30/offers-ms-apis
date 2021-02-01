
@Library('pipeline-library','shared-lib-pipeline')_
import com.example.SharedLib

node {
    
	stage('Welcome Script') {
                echo 'Welcome to Jenkins pipeline...'
		def var1 = -1
		var1 = SharedLib.addNumber()
		echo var1
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
