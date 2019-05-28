#!groovy

node('master') {
    stage('Checkout') {
        checkout scm
    }

    stage('Run tests') {
	    withMaven(maven: 'Maven') {
            sh 'mvn -Dtest=MainRunner clean test -e -Dwebdriver.type=remote -Dwebdriver.url=http://localhost:4444/wd/hub -Dwebdriver.cap.browserName=chrome'
	        archiveArtifacts artifacts: 'target/**/*'
	        }
          }
    stage('Artefatos') {
        archiveArtifacts artifacts: 'target/**/*'
    }
    stage('reports') {
	    script {
	            allure([
	                    includeProperties: false,
	                    jdk: '',
	                    properties: [],
	                    reportBuildPolicy: 'ALWAYS',
	                    results: [[path: 'target/allure-results']]
	            ])
        }
    }
  }