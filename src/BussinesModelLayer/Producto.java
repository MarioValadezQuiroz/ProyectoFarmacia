package BussinesModelLayer;

import DataAccessLayer.DataAccess;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
//Clase POJO

public class Producto {

    private DataAccess dataAccess = DataAccess.Instance();//Acceso a la instancia de DataAccess, Esta es la clase del paquete de negocio ocupamos accesso, entonces como esta clase es del paquete negocio entonces negocio si puede tener accesso a datos
    private int idProducto;
    private String nombre;
    private String caducidad;
    private int stock;
    private int idFarmacia;
    private int activo;

    public Producto() {
    }

    public Producto(int idProducto, String nombre, String caducidad, int stock, int idFarmacia, int activo) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.caducidad = caducidad;
        this.stock = stock;
        this.idFarmacia = idFarmacia;
        this.activo = activo;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCaducidad() {
        return caducidad;
    }

    public void setCaducidad(String caducidad) {
        this.caducidad = caducidad;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getIdFarmacia() {
        return idFarmacia;
    }

    public void setIdFarmacia(int idFarmacia) {
        this.idFarmacia = idFarmacia;
    }

    public int isActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public DefaultTableModel GetAllModel() { //creamos un metodo Obtenemos todos los productos, es llamado GetAllModel en formato Model, este metodo ya con su contenido me retorna el DefaultTableModel de la entidad o clase Producto
        String query = "SELECT * FROM productos";//Creamos una query que va hacer la instruccion hacia la base de datos, que va hacer entre comillas "seleccioname from de la tabla productos"
        return dataAccess.Query(query); //este metodo lo regresa para guardar en un defaultTableModel
    }
    // En esta clase que es del paquete modelo solo se hace la llamada de DataAccess con la query correspondiente y me regresa el modelo del query y orale tabla ahy te va presentalo
    //Que haria yo en la capa de vista, si yo quiero personalizar cada columna la personalizas, el DefaultTableModel viene de negocios, Aa no me gusta que diga idProducto pues cambiale el texto pero ya en vista aqui no, ya que aqui nos encargamos de analizar la informacion y presentartela a ti vista, osea tu vista se le pone la crema que tu quieras

    public void GetById() {//Creamos otro metodo que ocuapremos para obtener un solo producto que es obtener por Id
        String query = "SELECT * FROM productos WHERE idProducto = " + idProducto;//Es una consulta para obtener un solo registro de la tabla de productos, siempre y cuando el id del producto corresponda al que yo le voy a mandar como parametro; entonces la variable o propiedad idProducto ya esta declarada al inicio de la clase y tambien se encuentra dentro del constructor, entonces esa propiedad la tomo y se la mando como parametro a la consulta
        DefaultTableModel res = dataAccess.Query(query);//se ejecuta la linea anterior con el defaultTableModel ya que el metodo query de dataAccess me retorna un tableModel osea una tabla en memoria, pero siempre va a traer un registro en este caso, por eso siempre va a llevar un cero en el primer renglon en la larte de POBLO 
        //Poblo ya que esto significa alimentar o llenar con valores de una consulta a las propiedades del objeto, osea de lo que traigo de la base de datos aca a los atributos; entonces lo que se hace es alimentar esta entidad bueno en mi caso de que ya se cree el objeto, estoy alimentando el objeto con la informacion de la base de datos y ese mismo objeto que yo cree es el que trae toda la info//entonces aqui es como ponerle los titulos a la tabla de productos osea en el mismo reglon pero todos en cada columna
        idProducto = (int) res.getValueAt(0, 0);//Aqui siempre seria en el renglon cero ¿Porque? porque en la primer linea dentro de el metodoGetById yo me estoy asegurando que esa consulta me retorne un solo registro si o si
        nombre = (String) res.getValueAt(0, 1);
        caducidad = "" + (Date) res.getValueAt(0, 2);
        stock = (int) res.getValueAt(0, 3);
        idFarmacia = (int) res.getValueAt(0, 4);
        activo = (int) res.getValueAt(0, 5);
    }

    public boolean Add() {//metodo para agregar
        //recordemos que un CRUD son todas las acciones sobre una entidad, las cuatro principales que son insertar, eliminar, modificar y actualizar
        //INSERT INTO TABLA(CAMPO1, CAMPO2) VALUES(VALOR1, VALOR2); ASI QUEDARIA LA SENTENCIA
        String query = "INSERT INTO productos(nombre, caducidad, stock, idFarmacia, activo) "//sentencia para insertar valores, por lo cual se inserto el nombre, csducidada, stock, idFarmacia y activo
                + "VALUES (' " + nombre + "','" + caducidad + "'," + stock + "," + idFarmacia + "," + activo + ");";// dentro de paerntesis coloco los valores que le quiero meter, para meter valores String se le pone un apostrofe, para meter valores numericos se le pone tal cual, pero como el primero va hacer el nombre pues es String asi que se le pone solo un apostrofe, depues sigue caducidad ese tambien va con apostrofe, despues sigue stock ese va tal cual sin apostrofe
        return dataAccess.Execute(query) >= 1;//retornamos siempre y cuando sea mayor a 1, osea el metodo va a regresar un booleano, el metodo de Execute como nosotros lo programamos en DataAccess me va a retornar "1 mas" si tuvo efecto la instruccion, por ejemplo un INSERT me regresa "1 o mas" si inserto mas de un valor eso quiere decir que si funciono, entonces si fue mayor o igual a 1 entonces es que si funciono, entonces me regresa un true osea la insercion fue correcta, ahora si hubo un error en la sentencia me va a regresar un cero y cero no es mayor o igual a 1 entonces regresa un false entonces ocurre un error
    }

    public boolean Delete() {//metodo para borrar
        String query = "DELETE FROM productos WHERE idProducto = " + idProducto;//Aqui se hizo una cosulta como la anterior pero este es para borrar
        return dataAccess.Execute(query) >= 1;
    }

    public boolean Update() {//METODO PARA ACTUALIZAR UN REGISTRO
        //Seteo cada uno de los campos que yo quiera actualizar
        //Lo que estoy tratando de hacer es como lo siguiente: Update tabla set campo1=valor1, campo2=valor2, campo3=valor3; etc, esto es lo que to estoy tratando de hacer con esta query de tipo String, ya que si ejecuto esto sobre la base de datos voy a actualizar toda la tabla y a todos le voy a poner la misma informacion, entonces ocupoun filtro por asi decirlo ocupo actualizar solamente los que correspondan a idProducto igual a lo que yo le mande solamente esos, esto se hace el la linea de codigo donde dice WHERE idProducto, es la ultima linea antes del return.
        String query = "UPDATE productos SET "
                + "nombre= '" + nombre + "', "
                + "caducidad = '" + caducidad + "', "
                + "stock = " + stock + ", "
                + "idFarmacia = " + idFarmacia + ", "
                + "activo = " + activo + " "
                + "WHERE idProducto = " + idProducto;
        return dataAccess.Execute(query) >= 1;
    }
}
