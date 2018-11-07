/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;
import java.sql.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author hectorleon
 */
public class dataBase{
    ArrayList<String> subj = new ArrayList<String>();
    public ArrayList<String> getAllMaterias(){
        try (
         // Step 1: Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/examenes?useJDBCCompliantTimezoneShift=true;useLegacyDatetimeCode=false;serverTimezone=UTC", "root", "proyecto");
               // MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"
 
         // Step 2: Allocate a 'Statement' object in the Connection
         Statement stmt = conn.createStatement();
      ){
        String state = "SELECT DISTINCT nombre FROM materias";
        ResultSet rset = stmt.executeQuery(state);
        subj = new ArrayList<String>();
        while(rset.next()) {
           subj.add(rset.getString("nombre"));
        }
      }
      catch(SQLException ex){
        ex.printStackTrace();
      }
      return subj;
    }
    public ArrayList<String> getAllTemasP(String materia){
        try (
         // Step 1: Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/examenes?useJDBCCompliantTimezoneShift=true;useLegacyDatetimeCode=false;serverTimezone=UTC", "root", "proyecto");
               // MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"
 
         // Step 2: Allocate a 'Statement' object in the Connection
         Statement stmt = conn.createStatement();
      ){
        String state = "SELECT DISTINCT t.nombre FROM temas AS t INNER JOIN materias AS m ON t.materia_ID=m.materiaID WHERE m.nombre = '" + materia + "'";
        ResultSet rset = stmt.executeQuery(state);
        subj = new ArrayList<String>();
        while(rset.next()) {
           subj.add(rset.getString("nombre"));
        }
      }
      catch(SQLException ex){
        ex.printStackTrace();
      }
      return subj;
    }
    public ArrayList<String> getAllDificultadP(String materia, String tema){
        try (
         // Step 1: Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/examenes?useJDBCCompliantTimezoneShift=true;useLegacyDatetimeCode=false;serverTimezone=UTC", "root", "proyecto");
               // MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"
 
         // Step 2: Allocate a 'Statement' object in the Connection
         Statement stmt = conn.createStatement();
      ){
        String state = "SELECT DISTINCT p.dificultad FROM preguntas AS p INNER JOIN materias AS m INNER JOIN temas AS t ON t.materia_ID=m.materiaID ON p.materia_ID=m.materiaID WHERE m.nombre = '" + materia + "' AND t.nombre = '"+tema+"'";
        ResultSet rset = stmt.executeQuery(state);
        subj = new ArrayList<String>();
        while(rset.next()) {
           subj.add(rset.getString("dificultad"));
        }
      }
      catch(SQLException ex){
        ex.printStackTrace();
      }
      return subj;
    }
    public void addMateria(String materia){
        try (
         // Step 1: Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/examenes?useJDBCCompliantTimezoneShift=true;useLegacyDatetimeCode=false;serverTimezone=UTC", "root", "proyecto");
               // MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"
 
         // Step 2: Allocate a 'Statement' object in the Connection
         Statement stmt = conn.createStatement();
      ){
        String sqlInsert = "insert into materias (nombre)" // need a space
               + "values ('"+materia+"')";
         int countInserted = stmt.executeUpdate(sqlInsert);
      }
      catch(SQLException ex){
        ex.printStackTrace();
      }
    }
    public void addTema(String materia, String tema){
        int id=0;
        try (
         // Step 1: Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/examenes?useJDBCCompliantTimezoneShift=true;useLegacyDatetimeCode=false;serverTimezone=UTC", "root", "proyecto");
               // MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"
 
         // Step 2: Allocate a 'Statement' object in the Connection
         Statement stmt = conn.createStatement();
      ){
        ResultSet rset = stmt.executeQuery("SELECT materiaID FROM materias WHERE nombre='"+materia+"'");
        if(rset.next()){
            id = rset.getInt("materiaID");
        }
        String sqlInsert = "insert into temas (nombre,materia_ID)" // need a space
               +"values ('"+tema+"',"+id+")";
         int countInserted = stmt.executeUpdate(sqlInsert);
      }
      catch(SQLException ex){
        ex.printStackTrace();
      }
    }
    public void addPregunta(String materia, String tema, int diff, String desc, String op1, String op2, String op3, String op4, String ans){
        int id=0;
        try (
         // Step 1: Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/examenes?useJDBCCompliantTimezoneShift=true;useLegacyDatetimeCode=false;serverTimezone=UTC", "root", "proyecto");
               // MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"
 
         // Step 2: Allocate a 'Statement' object in the Connection
         Statement stmt = conn.createStatement();
      ){
        ResultSet rset = stmt.executeQuery("SELECT materiaID FROM materias WHERE nombre='"+materia+"'");
        if(rset.next()){
            id = rset.getInt("materiaID");
        }
        rset = stmt.executeQuery("SELECT temaID FROM temas WHERE nombre='"+tema+"'");
        int temaid=0;
        if(rset.next()){
            temaid = rset.getInt("temaID");
        }
        String sqlInsert = "insert into preguntas(descripcion,dificultad,materia_ID,tema_ID,o1,o2,o3,o4,ans) " // need a space
               +"values ('"+desc+"',"+diff+","+id+","+temaid+",'"+op1+"','"+op2+"','"+op3+"','"+op4+"','"+ans+"')";
         int countInserted = stmt.executeUpdate(sqlInsert);
      }
      catch(SQLException ex){
        ex.printStackTrace();
      }
    }
    public ArrayList<String> getAllMateriasP(){
        try (
         // Step 1: Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/examenes?useJDBCCompliantTimezoneShift=true;useLegacyDatetimeCode=false;serverTimezone=UTC", "root", "proyecto");
               // MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"
 
         // Step 2: Allocate a 'Statement' object in the Connection
         Statement stmt = conn.createStatement();
      ){
        String state = "SELECT DISTINCT m.nombre FROM preguntas AS p INNER JOIN materias AS m ON p.materia_ID=m.materiaID";
        ResultSet rset = stmt.executeQuery(state);
        subj = new ArrayList<String>();
        while(rset.next()) {
           subj.add(rset.getString("m.nombre"));
        }
      }
      catch(SQLException ex){
        ex.printStackTrace();
      }
      return subj;
    }
    public ArrayList<String> getAllTemas(){
        try (
         // Step 1: Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/examenes?useJDBCCompliantTimezoneShift=true;useLegacyDatetimeCode=false;serverTimezone=UTC", "root", "proyecto");
               // MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"
 
         // Step 2: Allocate a 'Statement' object in the Connection
         Statement stmt = conn.createStatement();
      ){
        String state = "SELECT DISTINCT nombre FROM temas";
        ResultSet rset = stmt.executeQuery(state);
        subj = new ArrayList<String>();
        while(rset.next()) {
           subj.add(rset.getString("nombre"));
        }
      }
      catch(SQLException ex){
        ex.printStackTrace();
      }
      return subj;
    }
    public void deleteMateria(String materia){
        try (
         // Step 1: Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/examenes?useJDBCCompliantTimezoneShift=true;useLegacyDatetimeCode=false;serverTimezone=UTC", "root", "proyecto");
               // MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"
 
         // Step 2: Allocate a 'Statement' object in the Connection
         Statement stmt = conn.createStatement();
      ){
         String sqlDelete = "delete from materias where nombre='"+materia+"'";
         int countDeleted = stmt.executeUpdate(sqlDelete);
      }
      catch(SQLException ex){
        ex.printStackTrace();
      }
    }
    public void deleteTema(String tema){
        try (
         // Step 1: Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/examenes?useJDBCCompliantTimezoneShift=true;useLegacyDatetimeCode=false;serverTimezone=UTC", "root", "proyecto");
               // MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"
 
         // Step 2: Allocate a 'Statement' object in the Connection
         Statement stmt = conn.createStatement();
      ){
         String sqlDelete = "delete from temas where nombre='"+tema+"'";
         int countDeleted = stmt.executeUpdate(sqlDelete);
      }
      catch(SQLException ex){
        ex.printStackTrace();
      }
    }
    public void deletePregunta(String descripcion){
        try (
         // Step 1: Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/examenes?useJDBCCompliantTimezoneShift=true;useLegacyDatetimeCode=false;serverTimezone=UTC", "root", "proyecto");
               // MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"
 
         // Step 2: Allocate a 'Statement' object in the Connection
         Statement stmt = conn.createStatement();
      ){
         String sqlDelete = "delete from preguntas where descripcion='"+descripcion+"'";
         int countDeleted = stmt.executeUpdate(sqlDelete);
      }
      catch(SQLException ex){
        ex.printStackTrace();
      }
    }
    public void updatePregunta(int id,String descripcion, String op1, String op2, String op3, String op4, String ans){
        try (
         // Step 1: Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/examenes?useJDBCCompliantTimezoneShift=true;useLegacyDatetimeCode=false;serverTimezone=UTC", "root", "proyecto");
               // MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"
 
         // Step 2: Allocate a 'Statement' object in the Connection
         Statement stmt = conn.createStatement();
      ){
         String strUpdate = "update preguntas set descripcion ='"+descripcion+"',op1 ='"+op1+"',op2 ='"+op2+"',op3 ='"+op3+"',op4 ='"+op4+"',ans ='"+ans+"' where id ="+id;
         int countUpdated = stmt.executeUpdate(strUpdate);
      }
      catch(SQLException ex){
        ex.printStackTrace();
      }
    }
    public int getPreguntaID(String descripcion){
        int id=0;
        try (
         // Step 1: Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/examenes?useJDBCCompliantTimezoneShift=true;useLegacyDatetimeCode=false;serverTimezone=UTC", "root", "proyecto");
               // MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"
 
         // Step 2: Allocate a 'Statement' object in the Connection
         Statement stmt = conn.createStatement();
      ){
        String state = "SELECT descripcion FROM preguntas WHERE descripcion = '"+descripcion+"'";
        ResultSet rset = stmt.executeQuery(state);
        if(rset.next()){
            id = rset.getInt("materiaID");
        }
      }
      catch(SQLException ex){
        ex.printStackTrace();
      }
      return id;
    }
    public ArrayList<List<String>> getPreguntas(String materia, String temas, int dificultad ){
        ArrayList<List<String>> preguntas = new ArrayList<List<String>>();         
        try (
         // Step 1: Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/examenes?useJDBCCompliantTimezoneShift=true;useLegacyDatetimeCode=false;serverTimezone=UTC", "root", "proyecto");
               // MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"
 
         // Step 2: Allocate a 'Statement' object in the Connection
         Statement stmt = conn.createStatement();
      ){
        ResultSet rset = stmt.executeQuery("SELECT materiaID FROM materias WHERE nombre='"+materia+"'");
        int materiaid =0;
        int temaid=0;
        if(rset.next()){
            materiaid = rset.getInt("materiaID");
        }
        rset = stmt.executeQuery("SELECT temaID FROM temas WHERE nombre='"+temas+"'");
        if(rset.next()){
            temaid = rset.getInt("temaID");
        }
        String state = "SELECT descripcion,o1,o2,o3,o4,ans FROM preguntas WHERE materia_ID="+materiaid+" AND tema_ID="+temaid+" AND dificultad="+dificultad;
        rset = stmt.executeQuery(state);
        int i=0;
        while(rset.next()) {
           preguntas.add(new ArrayList<String>());
           preguntas.get(i).add(rset.getString("descripcion"));
           preguntas.get(i).add(rset.getString("o1"));
           preguntas.get(i).add(rset.getString("o2"));
           preguntas.get(i).add(rset.getString("o3"));
           preguntas.get(i).add(rset.getString("o4"));
           preguntas.get(i).add(rset.getString("ans"));
           i++;
        }
      }
      catch(SQLException ex){
        ex.printStackTrace();
      }
      return preguntas;
    }
}
