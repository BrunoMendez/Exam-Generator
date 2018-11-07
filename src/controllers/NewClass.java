/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
import database.dataBase;
import java.util.*;


/**
 *
 * @author hectorleon
 */
public class NewClass {
    public static void main(String[] args){
        dataBase myData = new dataBase();
        ArrayList<String> datos = myData.getAllMaterias();
        datos.forEach(System.out::println);
    }
}
