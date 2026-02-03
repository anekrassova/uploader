# File Uploading System (API + Worker)

Асинхронная система загрузки файлов с поддержкой идемпотентности, Kafka, MinIO и PostgreSQL. Проект реализует надёжную архитектуру загрузки файлов, где HTTP-API отвечает быстро, а тяжёлая работа по сохранению файлов выполняется в фоне воркером.

## Архитектура 

<img width="791" height="218" alt="Диаграмма без названия drawio (1)" src="https://github.com/user-attachments/assets/9fddba0c-f75c-42c6-8262-4cc49316f9e5" />

Компоненты:
1. Client — HTTP клиент
2. API Service (оркестратор, идемпотентность, быстрый HTTP-ответ, публикация событий в Kafka)
3. Kafka
4. Worker Service (фоновая загрузка файлов, обработка ошибок)
5. PostgreSQL (хранение статусов и метаданных файлов)
6. Temp Storage (временное файловое хранилище)
7. File Storage MinIO (постоянное хранилище файлов)

## Запуск проекта

1. *Предварительные требования*: Docker.
2. *Переменные окружения.* Создать .env в корне проекта по образцу:
<img width="219" height="218" alt="Снимок экрана 2026-02-03 в 14 55 30" src="https://github.com/user-attachments/assets/049b85d2-34a4-4dbd-a64a-9f6490cddc94" />

3. *Сборка и запуск.* Из корня проекта выполнить команду `docker compose up --build`. После запуска будут подняты: PostgreSQL, Kafka, MinIO, API Service, Worker Service.
