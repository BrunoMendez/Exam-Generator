/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import database.dataBase;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 *
 * @author bruno
 */
public class AgregarPreguntasController implements Initializable {
    public Stage stage = new Stage();
    public dataBase datos = new dataBase();
    @FXML
    private Button returnButton;
    @FXML
    private RadioButton opcion1;
    @FXML
    private RadioButton opcion2;
    @FXML
    private RadioButton opcion3;
    @FXML
    private RadioButton opcion4;
    @FXML
    private ComboBox<String> comboDificultad;
    
    // Materias
    @FXML
    private ComboBox<String> comboMaterias;
    ArrayList<String> materias = datos.getAllMaterias();
    private final ObservableList<String> materiasOL = 
            FXCollections.observableArrayList(materias);
    
    //Temas
    @FXML
    private ComboBox<String> comboTemas;
    ArrayList<String> temas = new ArrayList<>();
    private final ObservableList<String> temasOL = 
            FXCollections.observableArrayList(temas);
    
    
    private final ObservableList<String> dificultad = 
            FXCollections.observableArrayList("Facil", "Medio", "Dificil");
    
    @FXML
    private void goBack(ActionEvent event) 
        throws IOException
    {
        this.stage = ((Stage)this.returnButton.getScene().getWindow());
        Parent root = (Parent)FXMLLoader.load(getClass()
                .getResource("/application/Main.fxml"));
        Scene scene = new Scene(root);
        this.stage.setScene(scene);
        this.stage.show();
    }
    @FXML
    private void editMaterias(ActionEvent event)
        throws IOException
    {
        this.comboMaterias.setEditable(true);
    }
    
    @FXML
    private void editTemas(ActionEvent event)
        throws IOException
    {
        this.comboTemas.setEditable(true);
    }    
    
    @FXML
    private void addMateria(KeyEvent e)
    {
        if (e.getCode() == KeyCode.ENTER) {
            String text = comboMaterias.getEditor().getText();
            materias.add(text);
            materiasOL.setAll(materias);
            comboMaterias.setItems(materiasOL);
            comboMaterias.setEditable(false);
            datos.addMateria(text);
        }
    }
    
    @FXML
    private void addTema(KeyEvent e)
    {
        if (e.getCode() == KeyCode.ENTER) {
            String text = comboTemas.getEditor().getText();
            temas.add(text);
            temasOL.setAll(temas);
            comboTemas.setItems(temasOL);
            comboTemas.setEditable(false);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
        ToggleGroup opciones = new ToggleGroup();
        opcion1.setToggleGroup(opciones);
        opcion2.setToggleGroup(opciones);
        opcion3.setToggleGroup(opciones);
        opcion4.setToggleGroup(opciones);
        opciones.selectToggle(opcion1);
        comboDificultad.setItems(dificultad);
    }
    
    
}
