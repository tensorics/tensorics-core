#!/bin/bash
set -e # exit with nonzero exit code if anything fails

if [ "$TRAVIS_BRANCH" = 'master' ] && [ "$TRAVIS_PULL_REQUEST" == 'false' ]; then
    
	openssl aes-256-cbc -K $encrypted_7fc09654fb2e_key -iv $encrypted_7fc09654fb2e_iv -in codesigning.asc.enc -out codesigning.asc -d
    gpg --fast-import codesigning.asc

	secreteFilePath=$(pwd)/codesigning.asc
	
	gradle uploadArchives -Psigning.keyId=${signingKeyId} -Psigning.password=${signingPassword}  -Psigning.secretKeyRingFile=$secreteFilePath -PossrhUsername=${ossrhUsername} -PossrhPassword=${ossrhPassword}
	
	rm $secreteFilePath
fi




