pipeline {
    agent any

    stages {
        stage('Clone Repository') {
            steps {
                // Git 리포지토리에서 dev 브랜치를 클론
                git branch: 'dev', credentialsId: 'accommodation-ssh-key', url: 'git@github.com:JaydenKim0528/StayWithMe.git'
            }
        }

        stage('Set Permissions') {
            steps {
                // gradlew 파일의 실행 권한을 설정
                sh 'chmod +x ./gradlew'
            }
        }

        stage('Build') {
            steps {
                // Gradle을 이용하여 프로젝트 빌드
                sh './gradlew build'
            }
        }

        stage('Deploy') {
            steps {
                // EC2 배포 명령어 실행
                sh '''
                scp -o StrictHostKeyChecking=no -i /var/lib/jenkins/.ssh/jenkins_ssh build/libs/your-app.jar ec2-user@18.220.239.27:/path/to/deploy/
                '''
            }
        }
    }
}
