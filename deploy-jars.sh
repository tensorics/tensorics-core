#!/bin/bash
set -e # exit with nonzero exit code if anything fails

#OPENSSH.......
touch SecretFile.pgp

secreteFilePath=$(pwd)/SecretFile.pgp

echo "############ secreteFilePath =" $secreteFilePath


echo "signingKeyId : ${signingKeyId}"
echo "signingPassword : ${signingPassword}"
echo "ossrhUsername : ${ossrhUsername}"
echo "ossrhPassword : ${ossrhPassword}"


gradle uploadArchives -Psigning.keyId=${signing.keyId} -Psigning.password=${signing.password}  -Psigning.secretKeyRingFile=$secreteFilePath -PossrhUsername=${ossrhUsername} -PossrhPassword=${ossrhPassword}
