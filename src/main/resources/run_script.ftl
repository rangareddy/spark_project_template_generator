#!/bin/bash

echo ""
echo "Running the <$0> script"
echo ""

${projectBuilder.runScriptArguments}
${projectBuilder.sparkSubmitCommand}

echo "Finished <$0> script"