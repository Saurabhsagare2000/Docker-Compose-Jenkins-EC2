pipeline {

    agent any

    environment {
        AWS_REGION = 'ap-south-1'
        ACCOUNT_ID = '882321772634'
        FRONTEND = 'frontend-app'
        BACKEND = 'springboot-demo'
        DB = 'mysql-custom'
    }

    stages {

        stage('Build Frontend') {
            steps {
                dir('frontend') {
                    sh 'npm install'
                    sh 'npm run build'
                }
            }
        }

        stage('Build Backend') {
            steps {
                dir('backend') {
                    sh 'mvn clean package'
                }
            }
        }

        stage('Docker Build') {
            steps {
                sh '''
                docker build -t frontend-app ./frontend
                docker build -t springboot-demo ./backend
                docker build -t mysql-custom ./mysql
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
                docker tag frontend-app:latest ${ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${FRONTEND}:latest
                docker push ${ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${FRONTEND}:latest

                docker tag springboot-demo:latest ${ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${BACKEND}:latest
                docker push ${ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${BACKEND}:latest

                docker tag mysql-custom:latest ${ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${DB}:latest
                docker push ${ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${DB}:latest
                '''
            }
        }
    }
}