#!/bin/bash
set -e # exit with nonzero exit code if anything fails

./gradlew javadoc

echo "Removing old website working directory"
rm -rf build/website

echo "Cloning tensorics.github.io repo"
mkdir build/website
cd build/website
git clone -b dev https://github.com/tensorics/tensorics.github.io.git
cd tensorics.github.io

echo "Copying javadoc"
rm -rf ./javadoc
mkdir javadoc
cp -r ../../docs/javadoc .

echo "Copying ascidoc files"
rm -rf ./doc/*
cp ../../../src/asciidoc/*.ad ./doc/

echo "Committing changes"
git add .
git commit -m "Automatic deployment from Travis"

echo "Push"
git push --force --quiet "https://${GH_PAGES_TOKEN}@github.com/tensorics/tensorics.github.io"