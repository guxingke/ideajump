#!/usr/bin/env bash

echo 'mvn build begin'
mvn -q clean package -DskipTests
echo 'mvn build done'

echo 'aot compile begin'
native-image -cp target/ideajump.jar -H:Name=target/ideajump -H:+ReportUnsupportedElementsAtRuntime --no-server com.gxk.demo.Main
echo 'aot compile done'

# build dist
echo 'build dist wf begin'
mkdir dist temp

cp -R static temp/
cp target/ideajump temp/

cp workflow.yml temp/

pushd temp

cat workflow.yml | yq . > info.plist
plutil -convert xml1 info.plist

zip -r temp.zip *

popd

mv temp/temp.zip dist/ideajump.alfredworkflow

echo 'build dist wf done.'

rm -rf temp

