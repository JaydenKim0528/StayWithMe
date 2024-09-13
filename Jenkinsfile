pipeline {
    agent any
    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'dev', credentialsId: 'accommodation-ssh-key', url: 'git@github.com:JaydenKim0528/StayWithMe.git'
            }
        }
        stage('Build') {
            steps {
                sh './gradlew build'
            }
        }
        stage('Deploy') {
            steps {
                // EC2 배포 명령어 추가
                sh 'scp -i /path/to/private/key build/libs/your-app.jar ec2-user@18.220.239.27:/path/to/deploy/'
            }
        }
    }
}
