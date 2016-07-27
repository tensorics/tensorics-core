#!/bin/bash
set -e # exit with nonzero exit code if anything fails

#OPENSSH.......
touch SecretFile.pgp

secreteFilePath=$(pwd)/SecretFile.pgp

echo "############ secreteFilePath =" $secreteFilePath

#gradle uploadArchives -Psigning.keyId=${signing.keyId} -Psigning.password=${signing.password}  -Psigning.secretKeyRingFile=$secreteFilePath -PossrhUsername=${ossrhUsername} -PossrhPassword=${ossrhPassword}
echo "${signing.keyId}"
echo "${signing.password}"
echo "${ossrhUsername}"
echo "${ossrhPassword}"
#echo "-Psigning.keyId=${signing.keyId} -Psigning.password=${signing.password} -Psigning.secretKeyRingFile=$secreteFilePath -PossrhUsername=${ossrhUsername} -PossrhPassword=${ossrhPassword}"