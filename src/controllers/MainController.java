/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import application.Main;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author bruno
 */
public class MainController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public Stage stage = new Stage();
    @FXML
    private Button generateExamButton;
    @FXML
    private Button viewExamButton;
    @FXML
    private Button editQuestionButton;
    @FXML
    private Button addQuestionButton;
    
    @FXML
    public void loadGenerateExam(ActionEvent e)
        throws IOException
    {
        this.stage = ((Stage)this.generateExamButton.getScene().getWindow());
        Parent root = (Parent)FXMLLoader.load(getClass().getResource("/application/generarExamen.fxml"));
        Scene scene = new Scene(root);
        this.stage.setScene(scene);
        this.stage.show();
    }
    
    @FXML
    public void loadViewExam(ActionEvent e)
        throws IOException
    {
        this.stage = ((Stage)this.viewExamButton.getScene().getWindow());
        Parent root = (Parent)FXMLLoader.load(getClass().getResource("/application/bajarExamenes.fxml"));
        Scene scene = new Scene(root);
        this.stage.setScene(scene);
        this.stage.show();
    }
    
    @FXML
    public void loadEditQuestion(ActionEvent e)
        throws IOException
    {
        this.stage = ((Stage)this.editQuestionButton.getScene().getWindow());
        Parent root = (Parent)FXMLLoader.load(getClass().getResource("/application/EditarPregunta.fxml"));
        Scene scene = new Scene(root);
        this.stage.setScene(scene);
        this.stage.show();
    }
    
    @FXML
    public void loadAddQuestion(ActionEvent e)
        throws IOException
    {
        this.stage = ((Stage)this.addQuestionButton.getScene().getWindow());
        Parent root = (Parent)FXMLLoader.load(getClass().getResource("/application/agregarPreguntas.fxml"));
        Scene scene = new Scene(root);
        this.stage.setScene(scene);
        this.stage.show();
    }
}