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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javafx.stage.Stage;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;

/**
 *
 * @author bruno
 */
public class BajarExamenesController implements Initializable {

    public dataBase datos = new dataBase();
    public Stage stage = new Stage();
    
    @FXML
    private Button backButton;
    @FXML
    private Label label;

    ArrayList<List<String>> examenes = datos.getAllExamenes();
    ArrayList<List<String>> preguntas = new ArrayList<>();

    @FXML
    private ComboBox<String> comboExamenes;
    ArrayList<String> examenesNames = new ArrayList<>();
    private final ObservableList<String> examenesOL
            = FXCollections.observableArrayList(examenesNames);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        label.setOpacity(0);
        examenes.forEach((examen) -> {
            examenesNames.add(examen.get(1));
        });
        examenesOL.setAll(examenesNames);
        comboExamenes.setItems(examenesOL);

        FxUtilTest.autoCompleteComboBoxPlus(comboExamenes, (typedText, itemToCompare) -> itemToCompare.toLowerCase().contains(typedText.toLowerCase()) || itemToCompare.equals(typedText));
    }

    @FXML
    private void bajar(ActionEvent e)
            throws IOException {
        String selectedValue;
        selectedValue = (String) FxUtilTest.getComboBoxValue(comboExamenes);
        String fileName = selectedValue + ".txt";
        File file = new File(fileName);
        preguntas = datos.getAllPreguntasE(selectedValue);

        preguntas.forEach((pregunta) -> {
            String desc = pregunta.get(0);
            String ans = pregunta.get(5);
            String tipo = pregunta.get(6);

            ArrayList<String> variables = new ArrayList<>();
            Map<String, String> varValue = new HashMap<>();
            ArrayList<Argument> arguments = new ArrayList<>();
            List<String> tmp = new ArrayList();
            ArrayList<Function> funciones = new ArrayList<>();
            int descStarts = 0;
            if (tipo.equals("Dinamica")) {
                String fn;
                String[] lines = desc.split("[\\n]+");
                for (int i = 0; i < lines.length; i++) {
                    tmp.clear();
                    if (lines[i].charAt(0) == '$') {
                        lines[i] = lines[i].replaceAll("\\s+", "");
                        tmp.add(lines[i].substring(1, lines[i].indexOf("=")));
                        tmp.add(lines[i].substring(lines[i].indexOf("=") + 1, lines[i].indexOf("-")));
                        tmp.add(lines[i].substring(lines[i].indexOf("-") + 1));
                        variables.add(tmp.get(0));
                        double lower = Double.parseDouble(tmp.get(1));
                        double upper = Double.parseDouble(tmp.get(2));
                        double random = Math.random();
                        random = (random * (upper - lower)) + lower;
                        String arg = tmp.get(0) + " = " + String.format("%.3f", (double) random);
                        Argument argTmp = new Argument(arg);
                        arguments.add(argTmp);
                        varValue.put(tmp.get(0), String.format("%.3f", (double) random));
                    } else if (lines[i].indexOf("%%%") >= 0) {
                        descStarts = i + 1;
                    }
                    if (lines[i].indexOf("{") >= 0 && lines[i].indexOf("}") >= 1) {
                        //Crea el nombre de la funcion
                        fn = "fn" + funciones.size() + "(";
                        for (int j = 0; j < variables.size(); j++) {
                            if (j == 0 && j + 1 == variables.size()) {
                                fn = fn + variables.get(j) + ")";
                            } else if (j == 0) {
                                fn = fn + variables.get(j);
                            } else if (j + 1 == variables.size()) {
                                fn = fn + "," + variables.get(j) + ")";
                            } else {
                                fn = fn + "," + variables.get(j);
                            }
                        };
                        String expr = fn;
                        fn = fn + " = " + lines[i].substring(lines[i].indexOf("{") + 1, lines[i].indexOf("}"));
                        Function fn1 = new Function(fn);
                        funciones.add(fn1);
                        Expression e1 = new Expression(expr, funciones.get(funciones.size() - 1));
                        arguments.forEach((argument) -> {
                            e1.addArguments(argument);
                        });
                        ans = String.valueOf(e1.calculate());
                        String funcion = lines[i].substring(lines[i].indexOf("{") + 1, lines[i].indexOf("}"));
                        StringBuilder sbFn = new StringBuilder(funcion);
                        for (int k = 0; k < sbFn.length(); k++) {
                            String key = String.valueOf(sbFn.charAt(k));
                            if (varValue.containsKey(key)) {
                                sbFn.deleteCharAt(k);
                                sbFn.insert(k, varValue.get(key));
                            }
                        }
                        funcion = sbFn.toString();
                        StringBuilder newLine = new StringBuilder(lines[i]);
                        newLine.replace(lines[i].indexOf("{"), lines[i].indexOf("}") + 1, funcion);
                        lines[i] = newLine.toString();
                    }
                }

                StringBuilder sb = new StringBuilder("");
                for (int i = descStarts; i < lines.length; i++) {
                    if(i == descStarts){
                        sb.append(lines[i]);
                    } else {
                        sb.append("\r\n").append(lines[i]);
                    }
                }
                desc = sb.toString();
                pregunta.set(0, desc);
                pregunta.set(1, getRealOption(pregunta.get(1), variables, varValue, arguments));
                pregunta.set(2, getRealOption(pregunta.get(2), variables, varValue, arguments));
                pregunta.set(3, getRealOption(pregunta.get(3), variables, varValue, arguments));
                pregunta.set(4, getRealOption(pregunta.get(4), variables, varValue, arguments));
                pregunta.set(5, ans);
            }
        });

        FileWriter fileWriter;
        fileWriter = new FileWriter(file);
        try (PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.printf("\t\t%s\r\n", selectedValue);
            preguntas.forEach((pregunta) -> {
                printWriter.printf("\r\n%s\r\n\r\n", pregunta.get(0));
                printWriter.printf("a) %s\r\n", pregunta.get(1));
                printWriter.printf("b) %s\r\n", pregunta.get(2));
                printWriter.printf("c) %s\r\n", pregunta.get(3));
                printWriter.printf("d) %s\r\n", pregunta.get(4));
            });
        }
        label.setOpacity(1);
    }

    
    @FXML
    private void goBack(ActionEvent event)
            throws IOException {
        this.stage = ((Stage) this.backButton.getScene().getWindow());
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("/application/Main.fxml"));
        Scene scene = new Scene(root);
        this.stage.setScene(scene);
        this.stage.show();
    }
    @FXML
    private void borrar(ActionEvent event){
        String selectedValue;
        selectedValue = (String) FxUtilTest.getComboBoxValue(comboExamenes);
        comboExamenes.getItems().remove(selectedValue);
        int i=0;
        while(!examenes.get(i).get(1).equals(selectedValue)){
            i++;
        }
        datos.deleteExamen(Integer.parseInt(examenes.get(i).get(0)));
    }
    
    
    private String getRealOption(String option, ArrayList<String> variables, Map<String, String> varValue, ArrayList<Argument> arguments) {
        String fn, ans;
        ArrayList<Function> funciones = new ArrayList<>();
        if (option.indexOf("{") >= 0 && option.indexOf("}") >= 1) {
            //Crea el nombre de la funcion
            fn = "fn" + funciones.size() + "(";
            for (int j = 0; j < variables.size(); j++) {
                if (j == 0 && j + 1 == variables.size()) {
                    fn = fn + variables.get(j) + ")";
                } else if (j == 0) {
                    fn = fn + variables.get(j);
                } else if (j + 1 == variables.size()) {
                    fn = fn + "," + variables.get(j) + ")";
                } else {
                    fn = fn + "," + variables.get(j);
                }
            };
            String expr = fn;
            fn = fn + " = " + option.substring(option.indexOf("{") + 1, option.indexOf("}"));
            Function fn1 = new Function(fn);
            funciones.add(fn1);
            Expression e1 = new Expression(expr, funciones.get(funciones.size() - 1));
            arguments.forEach((argument) -> {
                e1.addArguments(argument);
            });
            ans = String.format("%.3f", e1.calculate());
            StringBuilder newLine = new StringBuilder(option);
            newLine.replace(option.indexOf("{"), option.indexOf("}") + 1, ans);
            option = newLine.toString();
        }
        return option;
    }
}
