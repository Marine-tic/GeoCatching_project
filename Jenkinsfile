node ('master'){

	stage('back-maven') {
		checkout scm
		sh 'mvn -f GeoCatchingWebService/pom.xml clean package'
	}

	stage('front-android') {
		checkout scm
		sh 'cd GeoCatching;./gradlew assemble'
	}
}