/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import database.dataBase;
import java.util.*;
import application.ExamVariable;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author hectorleon
 */
public class GenerarExamenController implements Initializable {

    public Stage stage = new Stage();
    public dataBase datos = new dataBase();

    @FXML
    private Button returnButton;
    @FXML
    private Button agregarPreguntaButton;
    @FXML
    private Button borrarButton;
    @FXML
    private TextField cantidad;
    @FXML
    private TextField examen;
    @FXML
    private Button addButton;
    @FXML
    private Button generarButton;

    // Tabla
    private final ObservableList<ExamVariable> examVariableOL
            = FXCollections.observableArrayList();
    @FXML
    private TableView<ExamVariable> examVariableTable;
    @FXML
    private TableColumn<ExamVariable, String> materiaCol;
    @FXML
    private TableColumn<ExamVariable, String> temaCol;
    @FXML
    private TableColumn<ExamVariable, String> dificultadCol;
    @FXML
    private TableColumn<ExamVariable, String> cantidadCol;
    @FXML
    private TableColumn<ExamVariable, String> tipoCol;


    // Preguntas para generar examenes
    @FXML
    ArrayList<String> preguntas = new ArrayList<>();

    // Materias
    @FXML
    private ComboBox<String> comboMaterias;
    ArrayList<String> materias = datos.getAllMateriasP();
    private final ObservableList<String> materiasOL
            = FXCollections.observableArrayList(materias);

    //Temas
    @FXML
    private ComboBox<String> comboTemas;
    ArrayList<String> temas = new ArrayList<>();
    private final ObservableList<String> temasOL
            = FXCollections.observableArrayList(temas);

    //Dificultad
    @FXML
    private ComboBox<String> comboDificultades;
    ArrayList<String> dificultades = new ArrayList<>();
    private final ObservableList<String> dificultadesOL
            = FXCollections.observableArrayList(dificultades);
    //Tipo
    @FXML
    private ComboBox<String> comboTipo;
    ArrayList<String> tipo = new ArrayList<>();
    private final ObservableList<String> tipoOL
            = FXCollections.observableArrayList(tipo);
    
    @FXML
    private void abrirAgregarPregunta(ActionEvent event)
            throws IOException {
        this.stage = ((Stage) this.agregarPreguntaButton.getScene().getWindow());
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("/application/agregarPreguntas.fxml"));
        Scene scene = new Scene(root);
        this.stage.setScene(scene);
        this.stage.show();

    }

    @FXML
    private void loadTemas(ActionEvent e)
            throws IOException {
        temas = datos.getAllTemas(comboMaterias.getValue());
        temasOL.setAll(temas);
        comboTemas.setItems(temasOL);
    }

    @FXML
    private void loadDificultades(ActionEvent e)
            throws IOException {
        dificultades = datos.getAllDificultadP(comboTemas.getValue());
        dificultadesOL.setAll(dificultades);
        comboDificultades.setItems(dificultadesOL);
    }
    @FXML
    private void loadTipo(ActionEvent e)
            throws IOException {
        tipo = datos.getAllTipoP(comboTemas.getValue(),comboDificultades.getValue());
        System.out.println(tipo.toString());
        tipoOL.setAll(tipo);
        System.out.println(tipoOL.toString());
        comboTipo.setItems(tipoOL);
    }

    @FXML
    private void goBack(ActionEvent event)
            throws IOException {
        this.stage = ((Stage) this.returnButton.getScene().getWindow());
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("/application/Main.fxml"));
        Scene scene = new Scene(root);
        this.stage.setScene(scene);
        this.stage.show();
    }

    @FXML
    private void agregarPregunta(ActionEvent event)
            throws IOException {
        examVariableOL.add(new ExamVariable(comboMaterias.getValue(),
                comboTemas.getValue(), comboDificultades.getValue(),
                cantidad.getText(),comboTipo.getValue()));
        examVariableTable.setEditable(true);
        examVariableTable.setItems(examVariableOL);
    }

    @FXML
    private void deleteRow(ActionEvent event)
            throws IOException {
        examVariableOL.remove(examVariableTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void generarExamen(ActionEvent event)
            throws IOException {
        for (ExamVariable examVariable : examVariableOL) {
            int cant = Integer.parseInt(examVariable.getCantidad());
            
            // agarra las preguntas de la base de datos
            ArrayList<List<String>> allPreguntas
                    = datos.getPreguntas(examVariable.getTema(), examVariable.getDificultad(),examVariable.getTipo());
            
            /// **** hasta aqui
            
            
            if (allPreguntas.size() >= cant) {
                // Saca la descripcion de las preguntas y las mete a una nueva lista
                ArrayList<String> allPreguntasDesc = new ArrayList<>();
                allPreguntas.forEach((pregunta) -> {
                    allPreguntasDesc.add(pregunta.get(0));
                });
                // escoge de manera random preguntas, sin repeticiones
                Random rand = new Random();
                for (int i = 0; i < cant; i++) {
                    int randomIndex = rand.nextInt(allPreguntasDesc.size());
                    preguntas.add(allPreguntasDesc.get(randomIndex));
                    allPreguntasDesc.remove(randomIndex);
                }
                datos.addExamen(examen.getText(), preguntas);
                System.out.println(datos.getAllExamenes().toString());
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        materias = datos.getAllMaterias();
        materiasOL.setAll(materias);
        comboMaterias.setItems(materiasOL);
        cantidad.setText("1");
        this.materiaCol.setCellValueFactory(new PropertyValueFactory("materia"));
        this.temaCol.setCellValueFactory(new PropertyValueFactory("tema"));
        this.dificultadCol.setCellValueFactory(new PropertyValueFactory("dificultad"));
        this.cantidadCol.setCellValueFactory(new PropertyValueFactory("cantidad"));
        this.tipoCol.setCellValueFactory(new PropertyValueFactory("tipo"));
    }

}
