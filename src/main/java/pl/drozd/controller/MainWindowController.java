package pl.drozd.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import pl.drozd.model.WeatherForecast;
import pl.drozd.util.Errors;
import pl.drozd.view.ViewFactory;

import java.io.IOException;

public class MainWindowController extends BaseController {

    @FXML
    private TextField cityLeft;

    @FXML
    private TableView<WeatherForecast> weatherTabelLeft;

    @FXML
    private TableColumn<WeatherForecast, String> dateColLeft;

    @FXML
    private TableColumn<WeatherForecast, String> temperatureColLeft;

    @FXML
    private TableColumn<WeatherForecast, String> descriptionColLeft;

    @FXML
    private Label errorLabelLeft;

    @FXML
    private TextField cityRight;

    @FXML
    private TableView<WeatherForecast> weatherTableRight;

    @FXML
    private TableColumn<WeatherForecast, String> dateColRight;

    @FXML
    private TableColumn<WeatherForecast, String> temperatureColRight;

    @FXML
    private TableColumn<WeatherForecast, String> descriptionColRight;

    @FXML
    private Label errorLabelRight;

    @FXML
    void searchButtonLeftAction() throws IOException {
        if(CityNameIsValid(cityLeft.getText().isEmpty())){
            Errors.showErrorMessage(errorLabelLeft, "");
            cityLeftWeatherForecast.clear();
            dateColLeft.setCellValueFactory(cellData ->cellData.getValue().getDate());
            temperatureColLeft.setCellValueFactory(cellData ->  cellData.getValue().getTemperatureForSpecificDay());
            descriptionColLeft.setCellValueFactory(cellData -> cellData.getValue().getDescription());
            try {
                getDataAboutWeather(cityLeftWeatherForecast, cityLeft.getText(), errorLabelLeft);
                weatherTabelLeft.setItems(cityLeftWeatherForecast);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            Errors.showErrorMessage(errorLabelLeft, "Podaj nazwę miasta!");
        }
    }

    @FXML
    void searchButtonRightAction() throws IOException {
        if(CityNameIsValid(cityRight.getText().isEmpty())){
            Errors.showErrorMessage(errorLabelRight, "");
            cityRightWeatherForecast.clear();
            dateColRight.setCellValueFactory(cellData ->cellData.getValue().getDate());
            temperatureColRight.setCellValueFactory(cellData ->  cellData.getValue().getTemperatureForSpecificDay());
            descriptionColRight.setCellValueFactory(cellData -> cellData.getValue().getDescription());
            try {
                getDataAboutWeather(cityRightWeatherForecast, cityRight.getText(), errorLabelRight);
                weatherTableRight.setItems(cityRightWeatherForecast);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            Errors.showErrorMessage(errorLabelRight, "Podaj nazwę miasta!");
        }
    }

    private final ObservableList<WeatherForecast> cityLeftWeatherForecast;
    private final ObservableList<WeatherForecast> cityRightWeatherForecast;

    public MainWindowController(ViewFactory viewFactory, String fxmlName) {

        super(viewFactory, fxmlName);
        cityLeftWeatherForecast = FXCollections.observableArrayList();
        cityRightWeatherForecast = FXCollections.observableArrayList();
    }

    private boolean CityNameIsValid(boolean cityName) {
        if(cityName) {
            return false;
        }
        return true;
    }

    private void getDataAboutWeather(ObservableList<WeatherForecast> weatherForecast, String cityName, Label label) throws IOException {
        for(int i = 0 ; i <= 7 ; i++){
            WeatherForecast cityWeather = new WeatherForecast(cityName);
            cityWeather.getWeather(i);
            if(cityWeather.getJsonDataCorrect()){
                weatherForecast.add(cityWeather);
            }
            else{
                Errors.showErrorMessage(label, "Błąd w nazwie miasta!");
            }
        }
    }
}
