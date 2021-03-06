/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import application.QuestionData;
import database.dataBase;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
    private Button guardarButton;
    @FXML
    private RadioButton opcion1;
    @FXML
    private RadioButton opcion2;
    @FXML
    private RadioButton opcion3;
    @FXML
    private RadioButton opcion4;
    @FXML
    private RadioButton tipo1;
    @FXML
    private RadioButton tipo2;
    @FXML
    private ComboBox<String> comboDificultad;
    @FXML
    private TextArea pregunta;
    @FXML
    private TextField textoOpcion1;
    @FXML
    private TextField textoOpcion2;
    @FXML
    private TextField textoOpcion3;
    @FXML
    private TextField textoOpcion4;
    @FXML
    private Label textoTipo1;
    @FXML
    private Label textoTipo2;
    @FXML
    Label label;
    
    private final ToggleGroup opciones = new ToggleGroup();
    private final ToggleGroup tipo = new ToggleGroup();
    
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
        if(QuestionData.getInstance().getGen()){
            QuestionData.getInstance().setGen(Boolean.FALSE);
            this.stage = ((Stage)this.returnButton.getScene().getWindow());
            Parent root = (Parent)FXMLLoader.load(getClass()
                    .getResource("/application/generarExamen.fxml"));
            Scene scene = new Scene(root);
            this.stage.setScene(scene);
            this.stage.show();
        }
        else if(QuestionData.getInstance().getUp()){
            QuestionData.getInstance().setUp(Boolean.FALSE);
            this.stage = ((Stage)this.returnButton.getScene().getWindow());
            Parent root = (Parent)FXMLLoader.load(getClass()
                    .getResource("/application/EditarPregunta.fxml"));
            Scene scene = new Scene(root);
            this.stage.setScene(scene);
            this.stage.show();
        }
        else{
            this.stage = ((Stage)this.returnButton.getScene().getWindow());
            Parent root = (Parent)FXMLLoader.load(getClass()
                    .getResource("/application/Main.fxml"));
            Scene scene = new Scene(root);
            this.stage.setScene(scene);
            this.stage.show();
        }
    }
    @FXML
    private void editMaterias(ActionEvent event)
        throws IOException
    {
        EventHandler<ActionEvent> handler = comboMaterias.getOnAction();
        comboMaterias.setOnAction(null);
        this.comboMaterias.setEditable(true);
        comboMaterias.setOnAction(handler);
    }
    
    @FXML
    private void editTemas(ActionEvent event)
        throws IOException
    {
        this.comboTemas.setEditable(true);
    }    
        
     @FXML
    private void loadTemas(ActionEvent e) 
            throws IOException
    {
        if (!comboMaterias.isEditable()) {
            temas = datos.getAllTemas(comboMaterias.getValue());
            temasOL.setAll(temas);
            comboTemas.setItems(temasOL);
        } else if (comboMaterias.isEditable()) {
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
            datos.addTema(comboMaterias.getValue(), text);
        }
    }
    
    @FXML
    private void guardarPregunta(ActionEvent e)
            throws IOException
    {
        if(!QuestionData.getInstance().getUp()){
            String ans = "";
            String stipo = "";
            if (opciones.getSelectedToggle().equals(opcion1)) {
                ans = textoOpcion1.getText();
            } else if (opciones.getSelectedToggle().equals(opcion2)) {
                ans = textoOpcion2.getText();
            } else if (opciones.getSelectedToggle().equals(opcion3)) {
                ans = textoOpcion3.getText();
            } else if (opciones.getSelectedToggle().equals(opcion4)) {
                ans = textoOpcion4.getText();
            }
            if (tipo.getSelectedToggle().equals(tipo1)) {
                stipo = textoTipo1.getText();
            } else if (tipo.getSelectedToggle().equals(tipo2)) {
                stipo = textoTipo2.getText();
            }
            datos.addPregunta(comboTemas.getValue(),
                    comboDificultad.getValue(), pregunta.getText(), 
                    textoOpcion1.getText(), textoOpcion2.getText(), 
                    textoOpcion3.getText(), textoOpcion4.getText(), ans,stipo);
        }
        else{
            String ans = "";
            String stipo = "";
            if (opciones.getSelectedToggle().equals(opcion1)) {
                ans = textoOpcion1.getText();
            } else if (opciones.getSelectedToggle().equals(opcion2)) {
                ans = textoOpcion2.getText();
            } else if (opciones.getSelectedToggle().equals(opcion3)) {
                ans = textoOpcion3.getText();
            } else if (opciones.getSelectedToggle().equals(opcion4)) {
                ans = textoOpcion4.getText();
            }
            if (tipo.getSelectedToggle().equals(tipo1)) {
                stipo = textoTipo1.getText();
            } else if (tipo.getSelectedToggle().equals(tipo2)) {
                stipo = textoTipo2.getText();
            }
            System.out.println(ans);         
            datos.updatePregunta(QuestionData.getInstance().getId(),
                     pregunta.getText(), textoOpcion1.getText(), textoOpcion2.getText(), 
                    textoOpcion3.getText(), textoOpcion4.getText(),ans,comboDificultad.getValue(),stipo);
            QuestionData.getInstance().setUp(Boolean.FALSE);
            this.stage = ((Stage) this.guardarButton.getScene().getWindow());
            Parent root = (Parent) FXMLLoader.load(getClass().getResource("/application/EditarPregunta.fxml"));
            Scene scene = new Scene(root);
            this.stage.setScene(scene);
            this.stage.show();
        }
        label.setOpacity(1);
    }
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        label.setOpacity(0);
        materias = datos.getAllMaterias();
        materiasOL.setAll(materias);
        comboMaterias.setItems(materiasOL);
        opcion1.setToggleGroup(opciones);
        opcion2.setToggleGroup(opciones);
        opcion3.setToggleGroup(opciones);
        opcion4.setToggleGroup(opciones);
        opciones.selectToggle(opcion1);
        
        tipo1.setToggleGroup(tipo);
        tipo2.setToggleGroup(tipo);
        tipo.selectToggle(tipo1);
        comboDificultad.setItems(dificultad);
        
        if(QuestionData.getInstance().getUp()){
            comboMaterias.getSelectionModel().select(QuestionData.getInstance().getMateria());
            comboDificultad.getSelectionModel().select(QuestionData.getInstance().getDif());
            temasOL.add(QuestionData.getInstance().getTema());
            comboTemas.setItems(temasOL);
            comboTemas.getSelectionModel().select(QuestionData.getInstance().getTema());
            pregunta.setText(QuestionData.getInstance().getDesc());
            textoOpcion1.setText(QuestionData.getInstance().getOp1());
            textoOpcion2.setText(QuestionData.getInstance().getOp2());
            textoOpcion3.setText(QuestionData.getInstance().getOp3());
            textoOpcion4.setText(QuestionData.getInstance().getOp4());
            if(QuestionData.getInstance().getTipo().equals("Dinamica")){
                tipo.selectToggle(tipo1);
            }
            else{
                tipo.selectToggle(tipo2);
            }
            if(QuestionData.getInstance().getAns().equals(QuestionData.getInstance().getOp1())){
            opciones.selectToggle(opcion1);
            }
            else if(QuestionData.getInstance().getAns().equals(QuestionData.getInstance().getOp2())){
                opciones.selectToggle(opcion2);
            }
            else if(QuestionData.getInstance().getAns().equals(QuestionData.getInstance().getOp3())){
                opciones.selectToggle(opcion3);
            }
            else if(QuestionData.getInstance().getAns().equals(QuestionData.getInstance().getOp4())){
                opciones.selectToggle(opcion4);
            }
        }
    }
}
