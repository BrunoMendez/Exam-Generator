/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import javafx.beans.property.*;

/**
 *
 * @author hectorleon
 */
public class QuestionVariable {
  private final SimpleStringProperty materia;
  private final SimpleStringProperty tema;
  private final SimpleStringProperty dificultad;
  private final SimpleStringProperty descripcion;
  private final SimpleStringProperty tipo;
    public QuestionVariable(String sDesc,String sMateria, String sTema, String sDificultad, String sTipo)
  {
    this.materia = new SimpleStringProperty(sMateria);
    this.tema = new SimpleStringProperty(sTema);
    this.dificultad = new SimpleStringProperty(sDificultad);
    this.descripcion = new SimpleStringProperty(sDesc);
    this.tipo = new SimpleStringProperty(sTipo);
  }
  
  public String getMateria()
  {
    return this.materia.get();
  }
  
  public String getTema()
  {
    return this.tema.get();
  }
  
  public String getDificultad()
  {
    return this.dificultad.get();
  }
  
  public String getDescripcion()
  {
    return this.descripcion.get();
  }
   public String getTipo()
  {
    return this.tipo.get();
  }

  
  
  public void setMateria(String nMateria)
  {
    this.materia.set(nMateria);
  }
  
  public void setTema(String nTema)
  {
    this.tema.set(nTema);
  }
  
  public void setDificultad(String nDificultad)
  {
    this.dificultad.set(nDificultad);
  }
  
  public void setDescripcion(String nDesc)
  {
    this.descripcion.set(nDesc);
  }
  public void setTipo(String nTipo)
  {
    this.tipo.set(nTipo);
  }

}
