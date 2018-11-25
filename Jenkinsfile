pipeline {
    agent {
        docker {
            image 'maven:3-alpine' 
            args '-v /root/.m2:/root/.m2' 
        }
    }
    stages {


        stage('Build') { 
            steps {
                sh 'mvn -B -DskipTests clean package' 
            }
        }

        stage('Test') {
                            steps {
                                sh 'mvn test'
                            }
                        }

        stage('Create image') {

        agent {
                docker { image 'node:7-alpine' }
            }
                    steps {
                        sh 'docker build -t band-wars:0.0.1 .'
                    }
                }
    }
}