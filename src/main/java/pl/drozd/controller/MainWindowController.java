package pl.drozd.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import pl.drozd.view.ViewFactory;

public class MainWindowController extends BaseController {

    @FXML
    private TextField CityLeft;

    @FXML
    private TableView<?> weatherTabelLeft;

    @FXML
    private TableColumn<?, ?> DataColLeft;

    @FXML
    private TableColumn<?, ?> TemperatureColLeft;

    @FXML
    private TableColumn<?, ?> DescriptionColLeft;

    @FXML
    private TextField CityRight;

    @FXML
    private TableView<?> WeatherTableRight;

    @FXML
    private TableColumn<?, ?> DataColRight;

    @FXML
    private TableColumn<?, ?> TemperatureColRight;

    @FXML
    private TableColumn<?, ?> DescriptionColRight;

    @FXML
    void searchButtonLeftAction() {

    }

    @FXML
    void searchButtonRightAction() {

    }

    public MainWindowController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }
}
