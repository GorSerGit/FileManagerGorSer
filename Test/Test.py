import requests
import json

# Функция для запросов к микросервису
def requests_to_microservice(url, data):

    # Отправляем POST запрос
    response = requests.post(url, json=data)
    

    # Проверяем статус-код ответа
    if response.status_code == 200:

        # Если ответ получен - выводим его в печать

        print("Получен ответ сервера:")

        for i in response.json()['responseMessage']:
            print(i)

# URL для получения всех записей
url1 = "http://localhost:8080/api/get/all"

# URL для получения записи по id
url2 = "http://localhost:8080/api/get/this"

# URL для добавления новых записей
url3 = "http://localhost:8080/api/insert/this"


# Певым делом создадим несколько новых записей в базе данных

data = {"file": "*Много текста*", "title": "Первый файл для добавления в базу данных","description": "Очень полезная информация про кошечек...","creation_date":"2024-05-06"}
requests_to_microservice(url3, data)

data = {"file": "*Много текста*", "title": "Второй файл для добавления в базу данных","description": "Руководство по эксплуатации микроволновки","creation_date":"2023-05-06"}
requests_to_microservice(url3, data)

data = {"file": "*Много текста*", "title": "Третий файл для добавления в базу данных","description": "Сборник рецептов домашних блинчиков","creation_date":"2021-08-06"}
requests_to_microservice(url3, data)

# Теперь получим записи по id

data = {"id" : 1}
requests_to_microservice(url2, data)

data = {"id" : 3} 
requests_to_microservice(url2, data)

# Получим все записи

requests_to_microservice(url1,{})

# На выходе получаем эти записи

'''
Получен ответ сервера:
{"id":"1"}
Получен ответ сервера:
{"id":"2"}
Получен ответ сервера:
{"id":"3"}
Получен ответ сервера:
{"file":"*Много текста*","description":"Очень полезная информация про кошечек...","id":1,"creation_date":"2024-05-06","title":"Первый файл для добавления в базу данных"}
Получен ответ сервера:
{"file":"*Много текста*","description":"Сборник рецептов домашних блинчиков","id":3,"creation_date":"2021-08-06","title":"Третий файл для добавления в базу данных"}
Получен ответ сервера:
{"file":"*Много текста*","description":"Сборник рецептов домашних блинчиков","id":3,"creation_date":"2021-08-06","title":"Третий файл для добавления в базу данных"}
{"file":"*Много текста*","description":"Руководство по эксплуатации микроволновки","id":2,"creation_date":"2023-05-06","title":"Второй файл для добавления в базу данных"}
{"file":"*Много текста*","description":"Очень полезная информация про кошечек...","id":1,"creation_date":"2024-05-06","title":"Первый файл для добавления в базу данных"}
'''

# Как можно заметить все сработало правильно. Включая сортировку по дате создания при выводе всех файлов
    
