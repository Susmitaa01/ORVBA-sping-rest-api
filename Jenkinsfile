node {
  stage("Clone the project") {
    git branch: 'master', url: 'https://github.com/Susmitaa01/ORVBA-sping-rest-api.git'
  }

  stage("Compilation") {
    bat "./mvnw clean install -DskipTests"
  }

  stage("Tests and run") {
    stage("Running unit tests") {
      bat "./mvnw test -Punit"
    }
    stage("Run the application") {
      bat 'java -jar target/On-Road-Vehicle-Breakdown-Assistance-System-0.0.1-SNAPSHOT.jar'
    }
  }
}
