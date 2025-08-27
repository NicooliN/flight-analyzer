#!/bin/bash

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(dirname "$SCRIPT_DIR")"

cd "$PROJECT_ROOT"

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

print_info() { echo -e "${BLUE}[INFO]${NC} $1"; }
print_success() { echo -e "${GREEN}[SUCCESS]${NC} $1"; }
print_warning() { echo -e "${YELLOW}[WARNING]${NC} $1"; }
print_error() { echo -e "${RED}[ERROR]${NC} $1"; }

check_dependencies() {
    if ! command -v java &> /dev/null; then
        print_error "Java не установлена"
        exit 1
    fi

    if ! command -v mvn &> /dev/null; then
        print_warning "Maven не установлен, попытка использовать JAR"
        if [ ! -f "target/ticket-analyzer.jar" ]; then
            print_error "JAR файл не найден. Установите Maven: sudo apt install maven"
            exit 1
        fi
    fi
}

build_project() {
    print_info "Сборка проекта..."
    mvn clean package
    if [ $? -eq 0 ]; then
        print_success "Проект успешно собран"
    else
        print_error "Ошибка сборки проекта"
        exit 1
    fi
}

main() {
    check_dependencies

    local file_path="${1:-src/main/resources/tickets.json}"

    if [ ! -f "$file_path" ]; then
        print_error "Файл $file_path не найден!"
        echo "Использование: $0 [файл.json]"
        echo "По умолчанию используется: src/main/resources/tickets.json"
        exit 1
    fi

    # Если Maven установлен и JAR не существует, собираем проект
    if command -v mvn &> /dev/null && [ ! -f "target/ticket-analyzer.jar" ]; then
        build_project
    fi

    print_info "Запуск анализа файла: $file_path"
    echo "========================================"

    if command -v mvn &> /dev/null; then
        mvn exec:java -Dexec.args="$file_path" -q
    else
        java -jar target/ticket-analyzer.jar "$file_path"
    fi

    if [ $? -eq 0 ]; then
        print_success "Анализ завершен успешно"
    else
        print_error "Ошибка при выполнении анализа"
        exit 1
    fi
}

case "${1:-}" in
    "-h"|"--help")
        echo "Ticket Analyzer - Анализатор авиабилетов"
        echo "Использование: $0 [файл.json]"
        echo "  файл.json - путь к JSON файлу с билетами"
        echo "              (по умолчанию: src/main/resources/tickets.json)"
        echo ""
        echo "Опции:"
        echo "  -h, --help    Показать эту справку"
        echo "  build         Собрать проект"
        echo "  clean         Очистить проект"
        echo "  test          Запустить тестовый прогон"
        ;;
    "build")
        if command -v mvn &> /dev/null; then
            build_project
        else
            print_error "Maven не установлен"
        fi
        ;;
    "clean")
        if command -v mvn &> /dev/null; then
            mvn clean
        else
            rm -rf target *.class
        fi
        print_success "Проект очищен"
        ;;
    "test")
        main "src/main/resources/tickets.json"
        ;;
    *)
        main "$1"
        ;;
esac