#!/bin/bash

echo "Установка Ticket Analyzer..."
echo "============================="

# Проверка Java
if ! command -v java &> /dev/null; then
    echo "Установка Java..."
    sudo apt update
    sudo apt install -y openjdk-11-jdk
fi

# Проверка Maven
if ! command -v mvn &> /dev/null; then
    echo "Установка Maven..."
    sudo apt install -y maven
fi

echo "Проверка установки:"
java -version
mvn --version

echo "Установка завершена!"
echo "Для запуска: ./scripts/run.sh"