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

    private final ArrayList<String> variables = new ArrayList<>();
    private final Map<String, String> varValue = new HashMap<>();
    private final ArrayList<Argument> arguments = new ArrayList<>();
    private final ArrayList<Function> funciones = new ArrayList<>();

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

    private String processText(String desc, String tipo) {
        List<String> tmp = new ArrayList();
        int descStarts = 0;
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
            if (i == descStarts) {
                sb.append(lines[i]);
            } else {
                sb.append("\r\n").append(lines[i]);
            }
        }
        desc = sb.toString();

        return desc;
    }

    @FXML
    private void bajar(ActionEvent e)
            throws IOException {
        String examName;
        examName = (String) FxUtilTest.getComboBoxValue(comboExamenes);
        String fileName = examName + ".txt";
        String answerSheet = examName + "_answers.txt";
        File file = new File(fileName);
        File answers = new File(answerSheet);
        FileWriter fileWriter, fileWriterAns;
        fileWriter = new FileWriter(file);
        fileWriterAns = new FileWriter(answers);
        preguntas = datos.getAllPreguntasE(examName);

        try (PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.printf("\t\t%s\r\n\r\n\r\n", examName);
            printWriter.print("Nombre:______________________________\t\t\tMatricula:______________________________\r\n");
            
            PrintWriter printWriterAns;
            printWriterAns = new PrintWriter(fileWriterAns);
            printWriterAns.printf("\t\t%s\r\n\r\n\r\n", examName + " Answers");

            preguntas.forEach((pregunta) -> {
                String desc = pregunta.get(0);
                String op1 = pregunta.get(1);
                String op2 = pregunta.get(2);
                String op3 = pregunta.get(3);
                String op4 = pregunta.get(4);
                String ans = pregunta.get(5);
                String tipo = pregunta.get(6);
                List<String> tmp = new ArrayList();
                int descStarts = 0;
                if (tipo.equals("Dinamica")) {
                    desc = processText(desc, tipo);
                    op1 = getRealOption(op1, variables, varValue, arguments);
                    op2 = getRealOption(op2, variables, varValue, arguments);
                    op3 = getRealOption(op3, variables, varValue, arguments);
                    op4 = getRealOption(op4, variables, varValue, arguments);
                    ans = getRealOption(ans, variables, varValue, arguments);
                    variables.clear();
                    varValue.clear();
                    arguments.clear();
                }
                
                printWriter.printf("\r\n%s\r\n\r\n", desc);
                printWriter.printf("a) %s\r\n", op1);
                printWriter.printf("b) %s\r\n", op2);
                printWriter.printf("c) %s\r\n", op3);
                printWriter.printf("d) %s\r\n", op4);
                
                String option = "";
                if (ans.equals(op1)) {
                    option = "a) ";
                } else if (ans.equals(op2)) {
                    option = "b) ";
                } else if (ans.equals(op3)) {
                    option = "c) ";
                } else if (ans.equals(op4)) {
                    option = "d) ";
                }
                
                
                printWriterAns.printf("\r\n%s\r\n\r\n", desc);
                printWriterAns.printf("Answer: %s%s\r\n", option, ans);
            });
            printWriter.flush();
            printWriter.close();
            printWriterAns.flush();
            printWriterAns.close();
            label.setText("Examen Bajado");
            label.setOpacity(1);
        }
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
    private void borrar(ActionEvent event) {
        String selectedValue;
        selectedValue = (String) FxUtilTest.getComboBoxValue(comboExamenes);
        comboExamenes.getItems().remove(selectedValue);
        int i = 0;
        while (!examenes.get(i).get(1).equals(selectedValue)) {
            i++;
        }
        datos.deleteExamen(Integer.parseInt(examenes.get(i).get(0)));
        label.setText("Examen Borrado");
        label.setOpacity(1);
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
