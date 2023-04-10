node {
  stage("Clone the project") {
    git branch: 'master', url: 'https://github.com/Susmitaa01/ORVBA-sping-rest-api.git'
  }

  stage("Compilation") {
    sh "./mvnw clean install -DskipTests"
  }

  stage("Tests and Deployment") {
    stage("Running unit tests") {
      sh "./mvnw test -Punit"
    }
    stage("Deployment") {
      sh 'start /B C:\\OnRoadVehicleBreakDownAssistance\\ORVBA_Backend\\On-Road-Vehicle-Breakdown-Assistance-System\\mvnw.cmd spring-boot:run -Dserver.port=9093'
    }
  }
}
