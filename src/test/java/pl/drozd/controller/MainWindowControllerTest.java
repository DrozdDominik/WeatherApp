package pl.drozd.controller;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.testfx.framework.junit5.ApplicationTest;
import pl.drozd.view.ViewFactory;


import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;

public class MainWindowControllerTest extends ApplicationTest{

    private TextField leftCity;
    private TextField rightCity;

    @Override
    public void start(Stage stage) {
        ViewFactory viewFactory = new ViewFactory();
        viewFactory.showMainWindow();
    }

    @BeforeEach
    void cleanUpTextFields() {

        leftCity = lookup("#leftAnchorPane").lookup("#cityLeft").query();
        rightCity = lookup("#rightAnchorPane").lookup("#cityRight").query();
        leftCity.clear();
        rightCity.clear();

    }


    @ParameterizedTest
    @ValueSource(strings = {""})
    void shouldShowErrorMessageWhenLeftCityNameEmpty(String cityName){

        //given
        Button button = lookup("#leftAnchorPane").lookup(".button").query();


        //when
        leftCity.setText(cityName);
        clickOn(button);

        //then
        verifyThat("#errorLabelLeft", hasText("Podaj nazwę miasta!"));
    }

    @ParameterizedTest
    @ValueSource(strings = {""})
    void shouldShowErrorMessageWhenRightCityNameEmpty(String cityName){

        //given
        Button button = lookup("#rightAnchorPane").lookup(".button").query();


        //when
        rightCity.setText(cityName);
        clickOn(button);

        //then
        verifyThat("#errorLabelRight", hasText("Podaj nazwę miasta!"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Krakowsk"})
    void shouldShowErrorMessageWhenLeftCityNameWrong(String cityName){

        //given
        Button button = lookup("#leftAnchorPane").lookup(".button").query();

        //when
        leftCity.setText(cityName);
        clickOn(button);

        //then
        verifyThat("#errorLabelLeft", hasText("Błąd w nazwie miasta!"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Krakows"})
    void shouldShowErrorMessageWhenRightCityNameWrong(String cityName){

        //given
        Button button = lookup("#rightAnchorPane").lookup(".button").query();


        //when
        rightCity.setText(cityName);
        clickOn(button);

        //then
        verifyThat("#errorLabelRight", hasText("Błąd w nazwie miasta!"));
    }

}
