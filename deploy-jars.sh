#!/bin/bash
set -e # exit with nonzero exit code if anything fails

if [ "$TRAVIS_BRANCH" = 'master' ] && [ "$TRAVIS_PULL_REQUEST" == 'false' ]; then
    
	echo "Decrypting deployment.key.enc..."
	openssl aes-256-cbc -K $encrypted_e60aa78a0bb7_key -iv $encrypted_e60aa78a0bb7_iv -in deployment.key.enc -out deployment.key -d
    echo "... done"
	secreteFilePath=$(pwd)/deployment.key
	echo "Secrete key path found"

	echo "DEBUG"
	gpg --recv-keys 5EBAA0AD
	./gradlew -v
	./gradlew signArchives  -Psigning.keyId=${signingKeyId} -Psigning.password=${signingPassword}  -Psigning.secretKeyRingFile=$secreteFilePath -PossrhUsername=${ossrhUsername} -PossrhPassword=${ossrhPassword} --stacktrace --info
	gpg --verify build/libs/tensorics-core-0.0.21.jar.asc
	echo "END-DEBUG"
	
	echo "Executing gradle uploadArchives"
	./gradlew uploadArchives -Psigning.keyId=${signingKeyId} -Psigning.password=${signingPassword}  -Psigning.secretKeyRingFile=$secreteFilePath -PossrhUsername=${ossrhUsername} -PossrhPassword=${ossrhPassword} --stacktrace --info
	
	rm $secreteFilePath
fi
