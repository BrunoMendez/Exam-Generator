/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

/**
 *
 * @author hectorleon
 */
public class QuestionData {
    
    private QuestionData() {
    }

    private static QuestionData INSTANCE = new QuestionData();

    public static QuestionData getInstance() {
        return INSTANCE;
    }
    // END SINGLETON PATTERN

    // VARS
    private String materia = "";
    private String tema = "";
    private String dif = "";
    private String tipo= "";
    private String desc= "";
    private String op1= "";
    private String op2= "";
    private String op3= "";
    private String op4= "";
    private String ans="";
    private Boolean up= false;
    private int id = 0;


    // FUNCTIONS - Add/Edit/Delete
    public void setMateria(String M) {
        materia = M;
    }

    // FUNCTIONS - getter/setters
    public String getMateria() {
        return materia;
    }
    public void setTema(String T) {
        tema = T;
    }

    // FUNCTIONS - getter/setters
    public String getTema() {
        return tema;
    }
    public void setDif(String D) {
        dif = D;
    }
    // FUNCTIONS - getter/setters
    public String getDif() {
        return dif;
    }
    public void setTipo(String t) {
        tipo = t;
    }
    // FUNCTIONS - getter/setters
    public String getTipo() {
        return tipo;
    }
    public void setDesc(String t) {
        desc = t;
    }
    // FUNCTIONS - getter/setters
    public String getDesc() {
        return desc;
    }
    public void setOp1(String t) {
        op1 = t;
    }
    // FUNCTIONS - getter/setters
    public String getOp1() {
        return op1;
    }
    public void setOp2(String t) {
        op2 = t;
    }
    // FUNCTIONS - getter/setters
    public String getOp2() {
        return op2;
    }
    public void setOp3(String t) {
        op3 = t;
    }
    // FUNCTIONS - getter/setters
    public String getOp3() {
        return op3;
    }
    public void setOp4(String t) {
        op4 = t;
    }
    // FUNCTIONS - getter/setters
    public String getOp4() {
        return op4;
    }
    public void setAns(String t) {
        ans = t;
    }
    // FUNCTIONS - getter/setters
    public String getAns() {
        return ans;
    }
    public void setUp(Boolean t) {
        up = t;
    }
    // FUNCTIONS - getter/setters
    public Boolean getUp() {
        return up;
    }
    public void setId(int t) {
        id = t;
    }
    // FUNCTIONS - getter/setters
    public int getId() {
        return id;
    }
    
    
    

}
