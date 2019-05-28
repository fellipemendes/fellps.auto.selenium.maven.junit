#!groovy

node('master') {
    stage('Checkout') {
        checkout scm
    }

    try{
	    stage('Run tests') {
	    withMaven(maven: 'Maven') {
            sh 'mvn -Dtest=MainRunner clean test -e -Dwebdriver.type=remote -Dwebdriver.url=http://192.168.99.100:4444/wd/hub -Dwebdriver.cap.browserName=chrome'
	      }
            stage('Archive build output') {
                    archiveArtifacts 'target/**/*'
            }
          }
        }
     finally {
       		archiveArtifacts 'target/**/*'
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