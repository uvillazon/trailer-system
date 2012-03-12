<?php 
session_name("trailer");
session_start();
include("bd/bd.php");
require_once("impl/JSON.php");
///dadasdasdaadas
if(permitido("fun1001", $_SESSION['codigo'])==true)
{
//    hola
    $funcion = $_GET['funcion'];
    if($funcion == "ListarLinea"){

  }
    
    else if($funcion = "BuscarEstiloMarcaColeccionPorId"){

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