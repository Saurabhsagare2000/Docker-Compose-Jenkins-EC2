pipeline {

    agent any

    environment {
        AWS_REGION = 'ap-south-1'
        ACCOUNT_ID = '882321772634it'
        FRONTEND = 'frontend-app'
        BACKEND = 'springboot-demo'
    }

    stages {

        stage('Build Frontend') {
            steps {
                dir('Frontend') {
                    sh 'npm install'
                    sh 'npm run build'
                }
            }
        }

        stage('Build Backend') {
            steps {
                dir('Backend') {
                    sh 'mvn clean package'
                }
            }
        }

        stage('Docker Build') {
            steps {
                sh '''
                docker build -t frontend-app ./frontend
                docker build -t springboot-demo ./backend
                '''
            }
        }

        stage('ECR Login') {
            steps {
                sh '''
                aws ecr get-login-password --region ap-south-1 \
                | docker login \
                --username AWS \
                --password-stdin ${ACCOUNT_ID}.dkr.ecr.ap-south-1.amazonaws.com
                '''
            }
        }

        stage('Push Images') {
            steps {
                sh '''
                docker tag frontend-app:latest ${ACCOUNT_ID}.dkr.ecr.ap-south-1.amazonaws.com/frontend-app:latest
                docker push ${ACCOUNT_ID}.dkr.ecr.ap-south-1.amazonaws.com/frontend-app:latest

                docker tag springboot-demo:latest ${ACCOUNT_ID}.dkr.ecr.ap-south-1.amazonaws.com/springboot-demo:latest
                docker push ${ACCOUNT_ID}.dkr.ecr.ap-south-1.amazonaws.com/springboot-demo:latest
                '''
            }
        }
    }
}