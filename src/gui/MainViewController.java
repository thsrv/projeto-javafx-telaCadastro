package gui;

import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import sample.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    @FXML
    private MenuItem menuItemVendedor;
    @FXML
    private MenuItem menuItemDepartamento;
    @FXML
    private MenuItem menuItemAbout;

    @FXML
    public void onMenuItemVendedorAction(){
        System.out.println("onMenuItemVendedorAction");
    }

    @FXML
    public void onMenuItemDepartamentoAction(){
        loadView("/gui/DepartamentoList.fxml");
    }

    @FXML
    public void onMenuItemAboutAction(){
        loadView("/gui/About.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private synchronized void loadView(String absolutName){
        try {
            FXMLLoader ler = new FXMLLoader(getClass().getResource(absolutName));
            VBox novoVBox = ler.load();

            Scene mainScene = Main.getMainScene();
            VBox mainVBox = (VBox)((ScrollPane)mainScene.getRoot()).getContent();

            Node mainMenu = mainVBox.getChildren().get(0);
            mainVBox.getChildren().clear();
            mainVBox.getChildren().add(mainMenu);
            mainVBox.getChildren().addAll(novoVBox.getChildren());

        }catch(IOException e){
            Alerts.showAlert("IOExcption","Error ao carregar a pagina",e.getMessage(), Alert.AlertType.ERROR);
        }

    }
}
