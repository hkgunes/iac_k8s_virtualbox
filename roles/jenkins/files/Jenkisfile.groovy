pipeline {
    agent server3 // ??

    stages {

        stage('Project Checkout and jar') {
            steps {

                script {
                    sh'''
mkdir -p /opt/project/ &&
cd /opt/project/ &&
git clone https://iactask:task.2023@github.com/hkgunes/docker_app.git
cd docker_app
mvn package
'''

                }
            }
        }
         .

        stage('Build Docker Image') {
            steps {
                // docker image build -t docker-java-jar:latest
                script {
                //    def dockerImage = docker.build('docker-java-jar:latest', '.')
                    sh'''
cd /opt/project/docker_app
docker image build -t docker-java-jar:latest
'''
                }
            }
        }

        stage('Push to Docker Registry') {
            steps {
                script {
                    sh '''
cd /opt/project/docker_app
docker push localhost:5000/docker-java-jar:latest
  
'''
                   // docker.withRegistry('http://localhost:5000', 'docker-registry-credentials') {
                     //   dockerImage.push()
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                // Use kubectl to deploy to your Kubernetes cluster
                sh 'kubectl apply -f deployment.yaml'
            }
        }
    }

