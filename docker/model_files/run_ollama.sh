#!/bin/bash

echo "Starting Ollama server..."
ollama serve &

sleep 5  

echo "Downloading Qwen model..."
ollama pull qwen3:0.6b

echo "Running Qwen model..."
ollama run qwen3:0.6b