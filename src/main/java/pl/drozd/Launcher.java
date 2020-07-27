package pl.drozd;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import pl.drozd.view.ViewFactory;

import java.io.IOException;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ViewFactory viewFactory = new ViewFactory();
        viewFactory.showMainWindow();
    }

    private Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }


}
