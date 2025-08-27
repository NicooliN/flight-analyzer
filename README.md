# Ticket Analyzer 🎫

Анализатор авиабилетов на Java для обработки JSON данных о перелетах.

## 📋 Функциональность

- 📊 Расчет минимального времени полета между городами для каждого перевозчика
- 💰 Вычисление разницы между средней ценой и медианой
- 📁 Поддержка различных форматов JSON файлов
- 🖥️ Командный интерфейс и интерактивный режим

## 🚀 Быстрый старт
# С помощью скрипта (рекомендуется)
./scripts/run.sh
# Через Maven
mvn exec:java -Dexec.args="путь/к/файлу.json"
# Из JAR файла
java -jar target/ticket-analyzer.jar путь/к/файлу.json
### Требования

### Установка

```bash
git clone https://github.com/your-username/ticket-analyzer.git
cd ticket-analyzer