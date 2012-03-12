<?php 
session_name("trailer");
session_start();
include("bd/bd.php");
require_once("impl/JSON.php");
require_once("impl/Utils.php");
require_once("impl/Producto.php");
///dadasdasdaadas
if(permitido("fun6002", $_SESSION['codigo'])==true)
{
//    hola
    $funcion = $_GET['funcion'];
    if($funcion == "ListarLinea"){

  }
    
    else if($funcion = "CargarPanelConsultaProducto"){
         //modificar materiaPrima

        CargarPanelConsultaProducto($_GET['callback'], $_GET['_dc'], $_GET['where'], false);

    }
    else{
        //hola
        echo "else";
    }


}
else
{
    $dev['mensaje'] = "Ud no tiene privilegios para esta funcion";
    $dev['error'] = "false";
    $json = new Services_JSON();
    $output = $json->encode($dev);
    print($output);
}
?>