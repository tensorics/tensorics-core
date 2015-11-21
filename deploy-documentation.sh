#!/bin/bash
set -e # exit with nonzero exit code if anything fails

cd build/asciidoc
git init
git add .
git commit -m "Automatically generated GitHub Pages"

#cd ../docs
#git add .
#git commit -m "Automatically generated Javadocs Pages"

git config user.name "Travis CI"
git config user.email "${E_MAIL}"

# Force push from the current repo's master branch to the remote
# repo's gh-pages branch. (All previous history on the gh-pages branch
# will be lost, since we are overwriting it.) We redirect any output to
# /dev/null to hide any sensitive credential data that might otherwise be exposed.
#git push --force --quiet "https://${GH_TOKEN}@${GH_REF}" master:gh-pages  > /dev/null 2>&1
git push --force "https://${GH_TOKEN}@${GH_REF}" master:gh-pages
