/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import application.QuestionData;
import database.dataBase;
import java.util.*;
import application.QuestionVariable;
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
 * @author bruno
 */
public class EditarPreguntaController implements Initializable {
    public Stage stage = new Stage();
    public dataBase datos = new dataBase();
    private ArrayList<List<String>> preg = new ArrayList<List<String>>();
    @FXML
    private Label label;
    @FXML
    private Button returnButton;
    @FXML
    private Button buscarButton;
    @FXML
    private Button editarButton;
    private final ObservableList<QuestionVariable> questionVariableOL
            = FXCollections.observableArrayList();
    @FXML
    private TableView<QuestionVariable> questionVariableTable;
    @FXML
    private TableColumn<QuestionVariable, String> materiaCol;
    @FXML
    private TableColumn<QuestionVariable, String> temaCol;
    @FXML
    private TableColumn<QuestionVariable, String> dificultadCol;
    @FXML
    private TableColumn<QuestionVariable, String> descripcionCol;
    @FXML
    private TableColumn<QuestionVariable, String> tipoCol;
    @FXML
    private TextField name;
    
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
    private void loadTemas(ActionEvent e)
            throws IOException {
        questionVariableOL.clear();
        for(int i=0; i<preg.size(); i++){
            if(preg.get(i).get(1).equals(comboMaterias.getValue())){
                questionVariableOL.add(new QuestionVariable(preg.get(i).get(0),preg.get(i).get(1),
                        preg.get(i).get(2), preg.get(i).get(3),
                        preg.get(i).get(4)));
            }
        }
        questionVariableTable.setEditable(true);
        questionVariableTable.setItems(questionVariableOL);
        temas = datos.getAllTemas(comboMaterias.getValue());
        temasOL.setAll(temas);
        comboTemas.setItems(temasOL);
    }

    @FXML
    private void loadDificultades(ActionEvent e)
            throws IOException {
        questionVariableOL.clear();
        for(int i=0; i<preg.size(); i++){
            if(preg.get(i).get(1).equals(comboMaterias.getValue()) && preg.get(i).get(2).equals(comboTemas.getValue())){
                questionVariableOL.add(new QuestionVariable(preg.get(i).get(0),preg.get(i).get(1),
                        preg.get(i).get(2), preg.get(i).get(3),
                        preg.get(i).get(4)));
            }
        }
        questionVariableTable.setEditable(true);
        questionVariableTable.setItems(questionVariableOL);
        dificultades = datos.getAllDificultadP(comboTemas.getValue());
        dificultadesOL.setAll(dificultades);
        comboDificultades.setItems(dificultadesOL);
    }
    @FXML
    private void loadTipo(ActionEvent e)
            throws IOException {
        questionVariableOL.clear();
        for(int i=0; i<preg.size(); i++){
            if(preg.get(i).get(1).equals(comboMaterias.getValue()) && preg.get(i).get(2).equals(comboTemas.getValue())
                    && preg.get(i).get(3).equals(comboDificultades.getValue())){
                questionVariableOL.add(new QuestionVariable(preg.get(i).get(0),preg.get(i).get(1),
                        preg.get(i).get(2), preg.get(i).get(3),
                        preg.get(i).get(4)));
            }
        }
        questionVariableTable.setEditable(true);
        questionVariableTable.setItems(questionVariableOL);
        tipo = datos.getAllTipoP(comboTemas.getValue(),comboDificultades.getValue());
        System.out.println(tipo.toString());
        tipoOL.setAll(tipo);
        System.out.println(tipoOL.toString());
        comboTipo.setItems(tipoOL);
    }
    @FXML
    private void loadAll(ActionEvent e)
            throws IOException {
        questionVariableOL.clear();
        for(int i=0; i<preg.size(); i++){
            if(preg.get(i).get(1).equals(comboMaterias.getValue()) && preg.get(i).get(2).equals(comboTemas.getValue())
                    && preg.get(i).get(3).equals(comboDificultades.getValue()) && preg.get(i).get(4).equals(comboTipo.getValue()) ){
                questionVariableOL.add(new QuestionVariable(preg.get(i).get(0),preg.get(i).get(1),
                        preg.get(i).get(2), preg.get(i).get(3),
                        preg.get(i).get(4)));
            }
        }
        questionVariableTable.setEditable(true);
        questionVariableTable.setItems(questionVariableOL);

    }
    @FXML
    private void goBack(ActionEvent event) 
        throws IOException
    {
        this.stage = ((Stage)this.returnButton.getScene().getWindow());
        Parent root = (Parent)FXMLLoader.load(getClass().getResource("/application/Main.fxml"));
        Scene scene = new Scene(root);
        this.stage.setScene(scene);
        this.stage.show();
    }
    @FXML
    private void search(ActionEvent event) 
        throws IOException
    {
        questionVariableOL.clear();
        for(int i=0; i<preg.size(); i++){
            if(preg.get(i).get(0).contains(name.getText())){
                questionVariableOL.add(new QuestionVariable(preg.get(i).get(0),preg.get(i).get(1),
                        preg.get(i).get(2), preg.get(i).get(3),
                        preg.get(i).get(4)));
            }
        }
        questionVariableTable.setEditable(true);
        questionVariableTable.setItems(questionVariableOL);
    }
    @FXML
    private void editar(ActionEvent event) 
        throws IOException
    {
        QuestionVariable seleccion = questionVariableTable.getSelectionModel().getSelectedItem();
        QuestionData.getInstance().setMateria(seleccion.getMateria());
        QuestionData.getInstance().setTema(seleccion.getTema());
        QuestionData.getInstance().setDesc(seleccion.getDescripcion());
        QuestionData.getInstance().setTipo(seleccion.getTipo());
        QuestionData.getInstance().setDif(seleccion.getDificultad());
        QuestionData.getInstance().setUp(Boolean.TRUE);
        int i=0;
        while(!preg.get(i).get(0).equals(seleccion.getDescripcion())){
            i++;
        }
        QuestionData.getInstance().setOp1(preg.get(i).get(5));
        QuestionData.getInstance().setOp2(preg.get(i).get(6));
        QuestionData.getInstance().setOp3(preg.get(i).get(7));
        QuestionData.getInstance().setOp4(preg.get(i).get(8));
        QuestionData.getInstance().setAns(preg.get(i).get(9));
        QuestionData.getInstance().setId(Integer.parseInt(preg.get(i).get(10)));
        this.stage = ((Stage) this.editarButton.getScene().getWindow());
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("/application/agregarPreguntas.fxml"));
        Scene scene = new Scene(root);
        this.stage.setScene(scene);
        this.stage.show();
    }
    @FXML
    private void deleteRow(ActionEvent event)
            throws IOException {
        QuestionVariable seleccion = questionVariableTable.getSelectionModel().getSelectedItem();
        questionVariableOL.remove(questionVariableTable.getSelectionModel().getSelectedItem());
        datos.deletePregunta(seleccion.getDescripcion());
        
    }

    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.materiaCol.setCellValueFactory(new PropertyValueFactory("materia"));
        this.temaCol.setCellValueFactory(new PropertyValueFactory("tema"));
        this.dificultadCol.setCellValueFactory(new PropertyValueFactory("dificultad"));
        this.descripcionCol.setCellValueFactory(new PropertyValueFactory("descripcion"));
        this.tipoCol.setCellValueFactory(new PropertyValueFactory("tipo"));
        materias = datos.getAllMateriasP();
        materiasOL.setAll(materias);
        comboMaterias.setItems(materiasOL);
        preg = datos.getAllPreguntasEditar();
        for(int i=0; i<preg.size(); i++){
            questionVariableOL.add(new QuestionVariable(preg.get(i).get(0),preg.get(i).get(1),
                    preg.get(i).get(2), preg.get(i).get(3),
                    preg.get(i).get(4)));
        }
        questionVariableTable.setEditable(true);
        questionVariableTable.setItems(questionVariableOL);
    }
    
}