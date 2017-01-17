node ('master'){

	stage('back-maven') {
		checkout scm
		sh 'mvn -f GeoCatchingWebService/pom.xml clean package'
	}

	stage('front-android') {
		checkout scm
		sh 'cd GeoCatching;'
		sh './gradlew assemble --stacktrace'
	}
}