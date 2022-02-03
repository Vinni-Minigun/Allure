package ru.netology.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

import io.qameta.allure.Step;


public class DataGenerator {
    private DataGenerator() {
    }

    static Faker faker = new Faker(new Locale("ru"));

    public static class Registration {
        public static RegistrationInfo generateInfo(String locale) {
            return new RegistrationInfo(generateCity(),
                    generateName(),
                    generatePhone());
        }
    }

    @Step("Генерация города для доставки карты")
    public static String generateCity() {
        String[] city = new String[]
                {"Москва", "Челябинск", "Екатеринбург", "Симферополь", "Хабаровск", "Мурманск", "Новосибирск", "Ярославль", "Магадан"};
        int index = new Random().nextInt(city.length);
        return city[index];
    }

    @Step("Генерация даты для доставки карты")
    public static String generateDate(int plusDays) {
        return LocalDate.now().plusDays(plusDays).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Step("Генерация Ф.И.О. для заказа карты")
    public static String generateName() {
        return faker.name().fullName();
    }

    @Step("Генерация телефона для связи с курьером")
    public static String generatePhone() {
        return faker.phoneNumber().phoneNumber();
    }

    @Step("Генерация недопустимого имени для проверки метода")
    public static String generateInvalidName() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().firstName();
    }
}