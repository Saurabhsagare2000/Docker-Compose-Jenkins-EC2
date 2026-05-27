pipeline {
    agent any

    environment {
        AWS_REGION = 'ap-south-1'
        ACCOUNT_ID = '882321772634'
        REPO = 'springboot-demo'
        CI = 'false'
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Saurabhsagare2000/Docker-Compose-Jenkins-EC2.git'
            }
        }

        stage('Debug Workspace') {
            steps {
                sh '''
                echo "===== WORKSPACE CONTENT ====="
                ls -ltr
                echo "============================="
                '''
            }
        }

        stage('Build Frontend') {
            steps {
                dir('Frontend') {   // ⚠️ change if your folder name differs
                    sh '''
                    npm install
                    npm run build
                    '''
                }
            }
        }

        stage('Build Backend') {
            steps {
                dir('Backend') {    // ⚠️ change if your folder name differs
                    sh '''
                    mvn clean package -DskipTests
                    '''
                }
            }
        }

        stage('Docker Build') {
            steps {
                sh '''
                echo "Building Docker images..."

                docker build -t frontend-app ./frontend
                docker build -t springboot-demo ./backend
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

        stage('Tag & Push Images') {
            steps {
                sh '''
                echo "Tagging images..."

                docker tag frontend-app:latest ${ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${REPO}:frontend
                docker tag springboot-demo:latest ${ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${REPO}:backend

                echo "Pushing images..."

                docker push ${ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${REPO}:frontend
                docker push ${ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${REPO}:backend
                '''
            }
        }

        stage('Deploy using Docker Compose') {
            steps {
                sh '''
                echo "Stopping old containers..."
                docker compose down || true

                echo "Starting services..."
                docker compose up -d --force-recreate
                '''
            }
        }
    }

    post {
        always {
            echo "Cleaning workspace..."
            cleanWs()
        }
    }
}