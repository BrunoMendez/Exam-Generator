package application;

import javafx.beans.property.SimpleStringProperty;

public class ExamVariable
{
  private final SimpleStringProperty materia;
  private final SimpleStringProperty tema;
  private final SimpleStringProperty dificultad;
  private final SimpleStringProperty cantidad;
  private final SimpleStringProperty tipo;
  
  public ExamVariable(String sMateria, String sTema, String sDificultad, String sCantidad, String sTipo)
  {
    this.materia = new SimpleStringProperty(sMateria);
    this.tema = new SimpleStringProperty(sTema);
    this.dificultad = new SimpleStringProperty(sDificultad);
    this.cantidad = new SimpleStringProperty(sCantidad);
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
  
  public String getCantidad()
  {
    return this.cantidad.get();
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
  
  public void setCantidad(String nCantidad)
  {
    this.cantidad.set(nCantidad);
  }
  public void setTipo(String nTipo)
  {
    this.tipo.set(nTipo);
  }
}
