#!/bin/bash

echo ""
echo "Running the <$0> script"
echo ""

${projectBuilder.runScriptSecArguments}
PRINCIPAL=$1
KEYTAB=$2

${projectBuilder.sparkSubmitSecureCommand}

echo "Finished <$0> script"