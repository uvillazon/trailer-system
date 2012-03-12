<?php 
session_name("trailer");
session_start();
include("bd/bd.php");
include("impl/Utils.php");
require_once("impl/JSON.php");
include("impl/Ciudad.php");

if(permitido("fun1001", $_SESSION['codigo'])==true)
{
    $funcion = $_GET['funcion'];
    if($funcion == "ListarCiudad"){
          ListarCiudad($_GET['star'], $_GET['limit'], $_GET['sort'], $_GET['dir'], $_GET['callback'], $_GET['_dc'],$_GET['where'], false);
    }
    else if($funcion == "GuardarNuevaCiudad"){
        GuardarNuevaCiudad($return);

    }
    else if($funcion == "BuscarCiudadPorId"){
        $idciudad=$_GET['idciudad'];
       
        BuscarCiudadPorId($idciudad,false);

    }
    else if($funcion == "GuardarEditarCiudad"){
        GuardarEditarCiudad();

    }
      else if($funcion == "EliminarCiudad"){
        $idciudad=$_GET['idciudad'];
        EliminarCiudad($idciudad, $_GET['callback'],$_GET['_dc'],false);

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