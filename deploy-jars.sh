#!/bin/bash
set -e # exit with nonzero exit code if anything fails

if [ "$TRAVIS_BRANCH" = 'master' ] && [ "$TRAVIS_PULL_REQUEST" == 'false' ]; then
    
	echo "Decrypting codesigning.asc.enc..."
	openssl aes-256-cbc -K $encrypted_e60aa78a0bb7_key -iv $encrypted_e60aa78a0bb7_iv -in codesigning.asc.enc -out codesigning.asc -d
    echo "... done"
    gpg --batch --personal-cipher-preferences "AES256 AES192 AES CAST5 3DES" --personal-digest-preferences "SHA256 SHA1 SHA384 SHA512 SHA224" --personal-compress-preferences "ZLIB BZIP2 ZIP Uncompressed" --fast-import codesigning.asc
    echo "Key added"
	secreteFilePath=$(pwd)/codesigning.asc
	echo "Secrete key path found"
	echo "Executing gradle uploadArchives"
	gradle uploadArchives -Psigning.keyId=${signingKeyId} -Psigning.password=${signingPassword}  -Psigning.secretKeyRingFile=$secreteFilePath -PossrhUsername=${ossrhUsername} -PossrhPassword=${ossrhPassword}
	
	rm $secreteFilePath
fi




