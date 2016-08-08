#!/bin/bash
set -e # exit with nonzero exit code if anything fails

gradle javadoc asciidoctor

echo "Gathering files"
cd build/asciidoc
# copy the documentation
cp ../docs/javadoc . -r

echo "Init new git repo"
git init
git config user.name "tensorics-dev"
git config user.email "tensorics-dev@cern.ch"
git add . &> /dev/null
git commit -m "Automatic deployment to GitHub Pages" &> /dev/null

echo "Pushing to gh-pages"
# Force push from the current repo's master branch to the remote
# repo's gh-pages branch. (All previous history on the gh-pages branch
# will be lost, since we are overwriting it.) We redirect any output to
# /dev/null to hide any sensitive credential data that might otherwise be exposed.
#git push --force --quiet "https://${GH_TOKEN}@${GH_REF}" master:gh-pages > /dev/null 2>&1
git push --force "https://${GH_TOKEN}@${GH_REF}" master:gh-pages
