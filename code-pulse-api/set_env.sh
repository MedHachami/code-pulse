#!/bin/bash

# Check if the profile is provided (dev or prod)
if [ -z "$1" ]; then
  echo "Error: Please specify a profile: 'dev' or 'prod'."
  exit 1
fi

PROFILE=$1

# Function to load environment variables from a file
load_env_file() {
  local env_file=$1
  if [ -f "$env_file" ]; then
    # Export valid environment variables (ignoring comments and empty lines)
    export $(grep -v '^\s*#' "$env_file" | grep -v '^\s*$' | xargs -d '\n')
    echo "Environment variables loaded from $env_file."
  else
    echo "Error: Environment file '$env_file' not found!"
    exit 1
  fi
}

# Load the appropriate environment file based on the profile
if [ "$PROFILE" == "dev" ]; then
  load_env_file ".env.dev"
elif [ "$PROFILE" == "prod" ]; then
  load_env_file ".env.prod"
else
  echo "Error: Invalid profile. Please choose 'dev' or 'prod'."
  exit 1
fi

# Print environment variables specific to the project
echo "Environment variables for the '$PROFILE' profile:"
grep -v '^\s*#' ".env.$PROFILE" | grep -v '^\s*$'
