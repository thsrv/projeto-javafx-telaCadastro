package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modelo.entidades.Departamento;
import sample.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class DepartamentoListController implements Initializable {

    @FXML
    private TableView<Departamento> tableViewDepartamento;

    @FXML
    private TableColumn<Departamento,Integer> tableColumnId;

    @FXML
    private TableColumn<Departamento,String> tableColumnNome;

    @FXML
    private Button btNew;

    @FXML
    public void onBtNewAction(){
        System.out.println("OnBtNEwAction");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        InitializeNodes();

    }

    private void InitializeNodes() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        tableColumnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        Stage stage = (Stage) Main.getMainScene().getWindow();
        tableViewDepartamento.prefHeightProperty().bind(stage.heightProperty());
    }
}
