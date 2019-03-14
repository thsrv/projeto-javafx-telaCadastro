package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entidades.Departamento;
import model.servicos.DepartamentoServicos;
import sample.Main;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DepartamentoListController implements Initializable {

    private DepartamentoServicos servicos;

    @FXML
    private TableView<Departamento> tableViewDepartamento;

    @FXML
    private TableColumn<Departamento,Integer> tableColumnId;

    @FXML
    private TableColumn<Departamento,String> tableColumnNome;

    @FXML
    private Button btNew;

    private ObservableList<Departamento> obsList;

    @FXML
    public void onBtNewAction(){
        System.out.println("OnBtNEwAction");
    }

    public void setDepartamentoServicos(DepartamentoServicos servicos){
        this.servicos = servicos;
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

    public void updateTableView(){
        if(servicos == null){
            throw new IllegalStateException("Service was null");
        }
        List<Departamento> list = servicos.findAll();
        obsList = FXCollections.observableArrayList(list);
        tableViewDepartamento.setItems(obsList);
    }
}
