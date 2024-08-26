import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class MicroserviceRequest {

    // Функция для запросов к микросервису
    public static void requestToMicroservice(String urlString, JSONObject data) {
        try {
            // Создаем URL и открываем соединение
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Настраиваем параметры соединения
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            // Отправляем данные
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = data.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Проверяем статус ответа
            if (connection.getResponseCode() == 200) {

		// Если ответ получен - выводим его в печать

		System.out.println("Ответ сервера получен:");		

                try (Scanner scanner = new Scanner(connection.getInputStream())) {
                    String response = scanner.useDelimiter("\A").next();
                    JSONObject jsonResponse = new JSONObject(response);
                    // Обработаем ответ
                    for (Object item : jsonResponse.getJSONArray("responseMessage")) {
                        System.out.println(item.toString());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // URL для получения всех записей
        String url1 = "http://localhost:8080/api/get/all";
        // URL для получения записи по id
        String url2 = "http://localhost:8080/api/get/this";
        // URL для добавления записи
        String url3 = "http://localhost:8080/api/insert/this";

	// Сначала добавим несколько записей

        JSONObject data1 = new JSONObject();
        data1.put("file", "*Много текста*");
        data1.put("title", "Первый файл для добавления в базу данных");
        data1.put("description", "Очень полезная информация про кошечек...");
        data1.put("creation_date", "2024-05-06");
        requestToMicroservice(url3, data1);

        JSONObject data2 = new JSONObject();
        data2.put("file", "*Много текста*");
        data2.put("title", "Второй файл для добавления в базу данных");
        data2.put("description", "Руководство по эксплуатации микроволновки");
        data2.put("creation_date", "2023-05-06");
        requestToMicroservice(url3, data2);

        JSONObject data3 = new JSONObject();
        data3.put("file", "*Много текста*");
        data3.put("title", "Третий файл для добавления в базу данных");
        data3.put("description", "Сборник рецептов домашних блинчиков");
        data3.put("creation_date", "2021-08-06");
        requestToMicroservice(url3, data3);

	// Теперь получим конкретные по id

	JSONObject data4 = new JSONObject();
        data4.put("id", 1);
	requestToMicroservice(url2, data4);

	JSONObject data5 = new JSONObject();
        data5.put("id", 3);
	requestToMicroservice(url2, data5);

	// Получим все записи
	JSONObject data6 = new JSONObject();
        requestToMicroservice(url1, data6);

    }
}


// На выходе получаем эти записи

/*

Ответ сервера получен:
{"id":"1"}
Ответ сервера получен:
{"id":"2"}
Ответ сервера получен:
{"id":"3"}
Ответ сервера получен:
{"file":"*Много текста*","description":"Очень полезная информация про кошечек...","id":1,"creation_date":"2024-05-06","title":"Первый файл для добавления в базу данных"}
Ответ сервера получен:
{"file":"*Много текста*","description":"Сборник рецептов домашних блинчиков","id":3,"creation_date":"2021-08-06","title":"Третий файл для добавления в базу данных"}
Ответ сервера получен:
{"file":"*Много текста*","description":"Сборник рецептов домашних блинчиков","id":3,"creation_date":"2021-08-06","title":"Третий файл для добавления в базу данных"}
{"file":"*Много текста*","description":"Руководство по эксплуатации микроволновки","id":2,"creation_date":"2023-05-06","title":"Второй файл для добавления в базу данных"}
{"file":"*Много текста*","description":"Очень полезная информация про кошечек...","id":1,"creation_date":"2024-05-06","title":"Первый файл для добавления в базу данных"}

*/

// Как можно заметить все сработало правильно. Включая сортировку по дате создания при выводе всех файлов




