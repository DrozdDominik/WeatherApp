package pl.drozd.model;

import net.aksingh.owmjapis.model.CurrentWeather;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

public class WeatherForecastTest {

    private WeatherForecast weatherForecast;

    @BeforeEach
    void setUp(){
        weatherForecast = mock(WeatherForecast.class);
    }

    @Test
    void shouldBeAbleToGetCityName() {
        doAnswer((Answer<Void>) invocation -> {
            Object arg0 = invocation.getArgument(0);
            assertEquals("Bochnia", arg0);
            return null;
        }).when(weatherForecast).setCityName(any(String.class));
        weatherForecast.setCityName("Bochnia");
    }


    @Test
    void shouldBeAbleToGetTemperature() {
        doAnswer((Answer<Void>) invocation -> {
            Object arg0 = invocation.getArgument(0);
            assertEquals(29, arg0);
            return null;
        }).when(weatherForecast).setTemperature(any(Integer.class));
        weatherForecast.setTemperature(29);
    }

}
