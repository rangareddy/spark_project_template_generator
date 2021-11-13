#!/bin/bash

SCRIPT_NAME=`basename "$0"`

printf "\nRunning the <${r"${SCRIPT_NAME}"}> script.\n"

${projectBuilder.runScriptArguments}
${projectBuilder.sparkSubmitCommand}

printf "Finished <${r"${SCRIPT_NAME}"}> script.\n"