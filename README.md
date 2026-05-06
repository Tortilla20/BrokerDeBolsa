<h1 align="center">Broker de Bolsa</h1>
<p align="center">Iván Duro Fernández</p>

<p align="center">Aplicación que simula un pequeño mercado de compra y venta entre Agentes a través de una gráfica realizada por varias interfaces gráficas</p>

- - -

## Requisitos del Proyecto

1. **Java 25** - El proyecto se realizó utilizando Java 25, por lo que para su ejecución se recomienda ejecutarlo en esa versión o en versiones futuras superiores a ella
2. **Maven** - También cabe destacar que se completo en Maven, con lo que las librerías que se necesitaron, están cubiertas en el archivo pom.xml en el que se hablará después
3. **Netbeans** - Por útlimo, se utilizó el IDE Netbeans en el que se escribió el código y se añadiron las interfaces gráficas, por lo que se recomienda, aunque no es necesario, utilizar este IDE para su ejecución

- - -

## Dependencias

Como se explicó antes, este proyecto está realizado en Maven, por lo que las dependecias que se necesitaron, que para el proyecto fueron 2 concretamente, están añadidas en el pom.xml.
- La primera dependencia que se añadió fue el **JFreeChart**, que su función es pintar la gráfica en tiempo real a través de la interfaz gráfica

<p align="center">
  <img src="mediaReadme/dependencia1.png" alt="Alt">
</p>

- La segunda dependencia fue **Gson** que hace la función de guardar los agentes y las operaciones en un archivo de esa extensión, con la casuistica de que no se utilizó, ya que estos datos se guardan en archivos .TXT para que el desarrollo del código fuera más fácil pero se deja la dependencia instalada por si en el futuro se necesita o se quiere usar esa extensión 

<p align="center">
  <img src="mediaReadme/dependencia2.png" alt="Alt">
</p>

- - - 

## Estructura del proyecto

La estructura del proyecto tiene los siguiente paquetes y clases relizados en un modelo MVC - Modelo, Vista y Controlador:

-- 📦 com.mycompany.brokerdebolsarecuperacion
- 📄 BrokerDeBolsaRecuperacion.java
-- 📦 controller
- 📄 Broker.java
- 📄 FrontController.java
- 📄 GraficaBolsa.java
- 📄 TareaBolsa.java
📦 model
- 📄 Agente.java
- 📄 Operacion.java
📦 persistencia
- 📄 GuardarAgente.java
- 📄 GuardarOperacion.java
📦 view
- 📄 MainFrame.java
