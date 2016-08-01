#!/bin/bash
set -e # exit with nonzero exit code if anything fails

#if [ "$TRAVIS_BRANCH" = 'master' ] && [ "$TRAVIS_PULL_REQUEST" == 'false' ]; then
    
	openssl aes-256-cbc -K $encrypted_e60aa78a0bb7_key -iv $encrypted_e60aa78a0bb7_iv -in deployment.key.enc -out deployment.key -d
    secreteFilePath=$(pwd)/deployment.key
	echo "Deployment key found"

	echo "DEBUG"
	git status
	git tag
	ls -l
	echo "Branch: $TRAVIS_BRANCH"
	echo "Tag: $TRAVIS_TAG"
	echo "Commit: $TRAVIS_COMMIT"
	#gpg --recv-keys 4170AA62
	#./gradlew -v
	#./gradlew signArchives -Psigning.keyId=${signingKeyId} -Psigning.password=${signingPassword}  -Psigning.secretKeyRingFile=$secreteFilePath -PossrhUsername=${ossrhUsername} -PossrhPassword=${ossrhPassword} --stacktrace --info
	#gpg --verify build/libs/tensorics-core-0.0.21.jar.asc
	echo "END-DEBUG"
	
	echo "Executing gradle uploadArchives"
	./gradlew uploadArchives -Psigning.keyId=${signingKeyId} -Psigning.password=${signingPassword}  -Psigning.secretKeyRingFile=$secreteFilePath -PossrhUsername=${ossrhUsername} -PossrhPassword=${ossrhPassword} --stacktrace --info
	
	rm $secreteFilePath
#fi
