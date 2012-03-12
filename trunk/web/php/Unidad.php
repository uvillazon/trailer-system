<?php 
session_name("trailer");
session_start();
include("bd/bd.php");
include("impl/Utils.php");
require_once("impl/JSON.php");
include("impl/Unidad.php");

if(permitido("fun1010", $_SESSION['codigo'])==true)
{
    $funcion = $_GET['funcion'];
    if($funcion == "ListarUnidad"){
          ListarUnidad($_GET['star'], $_GET['limit'], $_GET['sort'], $_GET['dir'], $_GET['callback'], $_GET['_dc'],$_GET['where'], false);
    }
    else if($funcion == "GuardarNuevaUnidad"){
        GuardarNuevaUnidad($_GET['callback'], $_GET['_dc'], $_GET['where'], false);

    }
    else if($funcion == "BuscarUnidadPorId"){
        $idunidad=$_GET['idunidad'];       
        BuscarUnidadPorId($_GET['callback'], $_GET['_dc'],$idunidad, false);

    }
    else if($funcion == "GuardarEditarUnidad"){
        $idunidad=$_GET['idunidad'];
        GuardarEditarUnidad($_GET['callback'], $_GET['_dc'],$idunidad, false);

    }
      else if($funcion == "EliminarUnidad"){
        $idunidad=$_GET['idunidad'];
        EliminarUnidad($_GET['callback'], $_GET['_dc'], $idunidad, false);

    }
    else{
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