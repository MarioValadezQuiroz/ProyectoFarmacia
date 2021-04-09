 //Esta clase se va a ocupar para generar los metodos de acceso a datos, en esta capa o paquete va todo lo relacionado con la base de datos o con la persistencia de informacion
package DataAccessLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class DataAccess {

    //Aplicar el concepto de singleton que es solo crear una sola instancia y no muchas para la creacion de un objeto
    //Esta variables solo van a poder ser utilizadas por la clase DataAccess por ser privadas
    private static DataAccess instance;
    private Connection con = null;
    private Statement statement;
    private ResultSet resultSet;

    //constructor vacio privado ya que la forma de instanciarlo no va hacer mediante el constructor
    private DataAccess() {

    }

    //Sera como lo siguiente
    //la primera vez que mande a llamar el metodo si es nulo crea la instancia por primera vez, la sigueinete vez que lo mande a llamar ya no va hacer nulo y retorna la instancia que ya se tiene
    //Aparte el modificador de acceso statico hace que pueda accder a los elementos de la clase sin necesidad de instanciarlos
    public static DataAccess Instance() {
        if (instance == null) {
            instance = new DataAccess();
        }
        return instance;
    }
    //Hacer un metodo que sirve para conectar a la base de datos cada que yo requiera hacer una accion, ya que primero agregue la libreria Jar o el conector MySql para poder conecatrlo a la base de datos

    public void ConectarDB() {
        try {
            //Aqui se utiliza la variable de conexion a la base de datos "con" y en el metodo getConnection dentro de parentesis va la URL y el puerto de mysql que es 3306 y lleva localhost porque en este momento se esta apuntando a la misma maquina, si yo quisiera hacer un sistema que apuntara a una maquina que no fuera esta, en vez de localhost iria la IP de la otra maquina, y despues va la base de datos que creee en workbench la cual llame farmaciadb, depsues va el nombre del usuario que agregue al instalar el mysql installer y por ultimo la contraseña que yo invente de ese usuario agregado al instalar el mysql installer y con esto se inicializa o se abre la conexion a la base de datos
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/farmaciadb", "fbd", "Mariovaladez30110.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en la conexion: " + e.getMessage());
        }
    }

    //Se creo un metodo privado para desconectar con la base de datos
    private void DesconectarDB() {
        //Ocupo hacer la consulta de los productos, osea conecto-ejecuto instruccion-ya se trae la informacion y desconecto ya que no es bueno mantener conexiones abiertas
        try {
            //Se va a desconectar despues de ejecutar una accion
            statement.close();
            resultSet.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en la desconexion: " + e.getMessage());
        }
    }
    //Faltan los metodos que se van a lanzar cada que yo haga una ejecucion hacia la base de datos, ¿Como que una ejecucion? R= Osea crear defaultTableModel que son las tablas crearlas aqui mismo en la capa de datos para que cuando el paquete negocio lo mande a llamar el negocio reciba ya como tal el tableModel y lo presente en la capa de vista

    //Entonces se cra un metodo el cual lo llame Query ya que query es una consulta o una ejecucion o un comando que tu lanzas a sql que lo que hace es consultar y retornar conjunto de valores; entonces yo le llamo Query a este metodo porque este metodo para mi significa que voy a traer algo de informacion
    public DefaultTableModel Query(String query) {
        try {
            ConectarDB();//Primero conectamos la base de datos
            statement = con.createStatement();//Inicializar el statement, ¿Que es el statement? R= es como preparar el comando o ejecutar sobre la base de datos
            resultSet = statement.executeQuery(query);//El resultSet Es la variable donde almacenamos el resultado de la consulta, es como un tipo de defaultTableModel osea una tabla en memoria; entonces la variable resultSet=statement punto ejecutame la siguiente query ¿Cual query? R=la que yo recibi por parametro, recordemos que query es una instruccion o un comando de tipo sql hacia la base de datos, por ejemplo clk*form productos osea dame todos los registros de la tabla de productos, te los da y los almacena en la variable resultSet
            Vector<String> ColumnNames = new Vector<String>();//Ahora la variable resultSet va a crear un vector para crear la parte de las columnas para el defaultTableModel
            int columnCount = resultSet.getMetaData().getColumnCount();//creamos la variable de tipo int "columnCount" para el conteo o el numero total de las columnas ya que el conteo de las columnas va hacer dinamico, osea me pueden pedir dame todo lo que tenga la tabla productos y en este caso son como 6 campos y despues me pueden pedir dame todo lo que tenga la tabla de farmacias que pudiera ser hasta 10 campos, entonces el tipo o el tamaño del columnCount va a cambiar segun el resultado que tengan en la base de datos; despues del igual esto se va a sacar de la variable (resultSet.getMetaData().getColumnCount();)
            //iteramos con un for sencillo
            for (int column = 1; column <= columnCount; column++) {//iterar la cantidad de columnas
                ColumnNames.add(resultSet.getMetaData().getColumnName(column));//el vector "ColumnNames" es como si fuera un renglon en memoria, punto agregame lo que yo tengo en la variable resultSet punto getMetaData().getColumnName(column) osea dame la primer columna entonces yo me fijo en el workben la tabla que hice y la primera es idProducto no no no asi no es, es todo dinamico no me importa cual tabla se consulte, todo va hacer dinamico osea dame la primer columna, la segunda, la tercera, la cuarta y veme ya danso ese renglon; en otras palabras es un arreglo unidimensional que se va llenando con valores eso es un vector, entonces se llena.
            }
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();//Una vez llenado el vector anterior se crea otro vector de vectores osea una matriz de tipo Object llamado data
            while (resultSet.next()) {//Con un ciclo while se pone mientras el resultSet tenga datos o mejor  dicho registros a lo loco en la tabla de productos voy a iterar lo que esta entre parentesis del while, el next es para que de o continue el siguiente registro del resultSet, recuerda que el resultSet almacena el resultado de la consulta que se hizo, por ejmeplo si mi tabla de productos tiene 25 productos entonces en la variable resultSet va haber 25 registros ya declarada esta variable con aterioridad en el metodo defaultTableModel . entoces el next hace dame el siguinte registro, dame el sguiente, dame el siguiente hasta que ya no hay ningun registro regresa un false y se sale del ciclo while por lo tanto regresa un boolean
                Vector<Object> vector = new Vector<Object>();//Entonces mientras alla algo osea algun registro voy a crear un Vector nuevo llamado vector de tipo Object
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {//voy a iterar la variable columnCount recuerda que es el numero total de columnas de la tabla de productos
                    vector.add(resultSet.getObject(columnIndex));//Dentro del for agregamos toda esa informacion al Vector y agregamos con la palabra add y entre parentesis (resultSet.getObject(columnIndex)) y se le manda el columnIndex, osea dame todo el objeto en ese indice osea el indice que tenga la variable columnIndex
                }
                data.add(vector);//terminando lo agregatos a data, recuerda que data es la matriz, agregan el vector que se esta trabajando o manejando que esta entre parentesis; osea lo mismo que se hizo con las matrices pero ahora con vectores y de forma dinamica sin preocuparnos de que esque la primer posicion de la matriz se llama nombre no eso no, no me importa como se llame solo dame lo primero lo segundo en ese orden de forma dinamica
            }
            return new DefaultTableModel(data, ColumnNames); //retornamos nuevo defaultTableModel y se le manda como info por parametros o entre parentesis el data y el columnNames que es el vector lo cual se llamo columnNames
            //public DefaultTableModel Query(String query): Este metodo Query recibe una consulta y retorna un defaultTableModel adaptado a la consulta, y esto facilmente se puede vincular a una tabla en Java en la parte de vista
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en la consulta: " + e.getMessage());
            return null;//
        } finally {
            DesconectarDB();
        }
    }

    public int Execute(String query) {//Creamos otro metodo llamado Execute, este metodo lo vamos a ocupar para hacer las sentencias que no sean de obtener informacion, sentencias como insertar, modificar y eliminar, esas tres sentencias seran incluidas en el Execute
        try {
            ConectarDB();
            statement = con.createStatement();
            return statement.executeUpdate(query);//le mandamos la query por parametros
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en el comando: " + e.getMessage());
            return 0; //cuando estemos en la clase de vista nos explica porque cero
        } finally {
            DesconectarDB();
        }
    }
}

//Esta clase la cual es la capa de datos si puede reutilizar, esta clase llamada DataAccess ya jamas se va  a tocar, ya que si no sabes borrar por ejemplo un elemento ya es parte de logica ya es negocio ya es en el paqute de BussinnesModelLayer
//Esta clase solamente se encarga de recibir informacion, procesarla, consultarla y retornar
