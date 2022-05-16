package com.kmv;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

public class WeatherTest {

    @Test
    @DisplayName("Get weather with lat and lon params")
    void currentWeatherLatLonTest() {
        JsonPath response = given()
                .queryParam("lat", "35")
                .queryParam("lon", "139")
                .queryParam("appid", "09a6c973a53c7699876fd2117cee9c1c")
                .when()
                .get("https://api.openweathermap.org/data/2.5/weather")
                .body()
                .jsonPath();
        assertThat(response.get("coord.lat"), equalTo(35));
        assertThat(response.get("coord.lon"), equalTo(139));
        assertThat(response.get("main.temp"), greaterThan(0f));
        assertThat(response.get("main.temp"), lessThan(1000f));
    }

    @Test
    @DisplayName("Get weather with lang param and check")
    void currentWeatherWithLangParamTest() {
        JsonPath response = given()
                .queryParam("lat", "59.9375")
                .queryParam("lon", "30.308611")
                .queryParam("lang", "ru")
                .queryParam("appid", "09a6c973a53c7699876fd2117cee9c1c")
                .when()
                .get("https://api.openweathermap.org/data/2.5/weather")
                .body()
                .jsonPath();
        assertThat(response.get("name"), equalTo("Новая Голландия"));
        assertThat(response.get("sys.country"), equalTo("RU"));
        assertThat(response.get("main.temp"), greaterThan(0f));
        assertThat(response.get("main.temp"), lessThan(1000f));
        assertThat(response.get("coord.lat"), equalTo(59.9375f));
        assertThat(response.get("coord.lon"), equalTo(30.3086f));
    }

    @Test
    @DisplayName("Get weather by city name and with degrees in Celsius")
    void currentWeatherByCityTest() {
        JsonPath response = given()
                .queryParam("q", "London")
                .queryParam("units", "metric")
                .queryParam("appid", "09a6c973a53c7699876fd2117cee9c1c")
                .when()
                .get("https://api.openweathermap.org/data/2.5/weather")
                .body()
                .jsonPath();
        assertThat(response.get("sys.country"), equalTo("GB"));
        assertThat(response.get("main.temp"), greaterThan(0f));
        assertThat(response.get("main.temp"), lessThan(50f));
        assertThat(response.get("name"), equalTo("London"));
    }
}

