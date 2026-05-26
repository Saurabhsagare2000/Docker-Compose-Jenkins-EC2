pipeline {

    agent any

    environment {
        AWS_REGION = 'ap-south-1'
        ACCOUNT_ID = '882321772634'
        FRONTEND = 'frontend-app'
        BACKEND = 'springboot-demo'
        CI = "false"
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
                docker build -t frontend-app ./Frontend
                docker build -t springboot-demo ./Backend
                '''
            }
        }

        stage('ECR Login') {
            steps {
                sh '''
                aws ecr get-login-password --region ${AWS_REGION} \
                | docker login \
                --username AWS \
                --password-stdin ${ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com
                '''
            }
        }

        stage('Push Images') {
            steps {
                sh '''
                docker tag frontend-app:latest ${ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${BACKEND}:frontend
                docker push ${ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${BACKEND}:frontend

                docker tag springboot-demo:latest ${ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${BACKEND}:backend
                docker push ${ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${BACKEND}:backend
                '''
            }
}
    }

    post {
        always {
            cleanWs()
        }
    }
}