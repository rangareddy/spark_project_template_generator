#!/bin/bash

SCRIPT=`basename "$0"`

if [ $# -lt 2 ]; then
    echo "Usage   : $SCRIPT <PRINCIPAL> <KEYTAB>"
    echo "Example : $SCRIPT rangareddy@hadoop.com rangareddy.keytab"
    echo " "
    exit 1
fi

echo ""
echo "Running the <$SCRIPT> script"
echo ""

PRINCIPAL=$1
KEYTAB=$2

spark-submit \
  --master yarn \
  --deploy-mode client \
  --driver-memory 1g \
  --executor-memory 1g \
  --num-executors 2 \
  --executor-cores 3 \
  --principal $PRINCIPAL \
  --keytab KEYTAB \
  --class ${projectBuilder.fullClassName} \
  ${projectBuilder.jarPath}

echo "Finished <$SCRIPT> script"