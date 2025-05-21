pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'witherlloyd/teedy-app'
        DOCKER_TAG = "${env.BUILD_NUMBER}"
    }

    stages {
        stage('Build') {
            steps {
                checkout scmGit(
                    branches: [[name: '*/docker']],
                    extensions: [],
                    userRemoteConfigs: [[url: 'https://github.com/Witheringstorm/se1.git']]
                )
                sh 'mvn -B -DskipTests clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${env.DOCKER_IMAGE}:${env.DOCKER_TAG}")
                }
            }
        }

        stage('Upload to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'docker_hub') {
                        docker.image("${env.DOCKER_IMAGE}:${env.DOCKER_TAG}").push()
                        docker.image("${env.DOCKER_IMAGE}:${env.DOCKER_TAG}").push('latest')
                    }
                }
            }
        }

        stage('Run Container') {
            steps {
                script {
                    sh 'docker stop teedy-container-8085 || true'
                    sh 'docker rm teedy-container-8085 || true'
                    docker.image("${env.DOCKER_IMAGE}:${env.DOCKER_TAG}").run(
                        '--name teedy-container-8085 -d -p 8085:8080'
                    )
                    sh 'docker ps --filter "name=teedy-container"'
                }
            }
        }
    }
}
