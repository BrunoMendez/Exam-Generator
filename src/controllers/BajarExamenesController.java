/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import application.FxUtilTest;
import database.dataBase;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

/**
 *
 * @author bruno
 */
public class BajarExamenesController implements Initializable {

    public dataBase datos = new dataBase();

    ArrayList<List<String>> examenes = datos.getAllExamenes();
    ArrayList<List<String>> preguntas = new ArrayList<>();

    @FXML
    private ComboBox<String> comboExamenes;
    ArrayList<String> examenesNames = new ArrayList<>();
    private final ObservableList<String> examenesOL
            = FXCollections.observableArrayList(examenesNames);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        examenes.forEach((examen) -> {
            examenesNames.add(examen.get(1));
        });
        examenesOL.setAll(examenesNames);
        comboExamenes.setItems(examenesOL);

        FxUtilTest.autoCompleteComboBoxPlus(comboExamenes, (typedText, itemToCompare) -> itemToCompare.toLowerCase().contains(typedText.toLowerCase()) || itemToCompare.equals(typedText));
    }

    @FXML
    private void bajar(ActionEvent e)
        throws IOException
    {
        String selectedValue;
        selectedValue = (String) FxUtilTest.getComboBoxValue(comboExamenes);
        String fileName = selectedValue + ".txt";
        File file = new File(fileName);
        preguntas = datos.getAllPreguntasE(71);
        FileWriter fileWriter;
        fileWriter = new FileWriter(file);
        try (PrintWriter printWriter = new PrintWriter(fileWriter)) {
            System.out.println(preguntas.toString());
            printWriter.printf("\t\t%s\r\n", selectedValue);
            preguntas.forEach((pregunta) -> {
                System.out.println(pregunta.get(0));
                System.out.println(pregunta.get(1));
                System.out.println(pregunta.get(2));
                System.out.println(pregunta.get(3));
                System.out.println(pregunta.get(4));
                printWriter.printf("\r\n%s\r\n", pregunta.get(0));
                printWriter.printf("a) %s\r\n", pregunta.get(1));
                printWriter.printf("b) %s\r\n", pregunta.get(2));
                printWriter.printf("c) %s\r\n", pregunta.get(3));
                printWriter.printf("d) %s\r\n\r\n", pregunta.get(4));
            });
        }
        System.out.println("exito");
    }
}
