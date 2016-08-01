#!/bin/bash
set -e # exit with nonzero exit code if anything fails

openssl aes-256-cbc -K $encrypted_e60aa78a0bb7_key -iv $encrypted_e60aa78a0bb7_iv -in deployment.key.enc -out deployment.key -d
secreteFilePath=$(pwd)/deployment.key
echo "Deployment key found"

echo "Executing gradle uploadArchives"
./gradlew uploadArchives -PdeploymentVersion=${TRAVIS_TAG} -Psigning.keyId=${signingKeyId} -Psigning.password=${signingPassword}  -Psigning.secretKeyRingFile=$secreteFilePath -PossrhUsername=${ossrhUsername} -PossrhPassword=${ossrhPassword} --stacktrace --info

rm $secreteFilePath
