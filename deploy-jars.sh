#!/bin/bash
set -e # exit with nonzero exit code if anything fails

if [ "$TRAVIS_BRANCH" = 'master' ] && [ "$TRAVIS_PULL_REQUEST" == 'false' ]; then
    
	echo "Decrypting deployment.key.enc..."
	openssl aes-256-cbc -K $encrypted_e60aa78a0bb7_key -iv $encrypted_e60aa78a0bb7_iv -in deployment.key.enc -out deployment.key -d
    echo "... done"
	secreteFilePath=$(pwd)/deployment.key
	echo "Secrete key path found"
	echo "Executing gradle uploadArchives"
	gradle uploadArchives -Psigning.keyId=${signingKeyId} -Psigning.password=${signingPassword}  -Psigning.secretKeyRingFile=$secreteFilePath -PossrhUsername=${ossrhUsername} -PossrhPassword=${ossrhPassword}
	
	rm $secreteFilePath
fi




