<?php 
session_name("trailer");
session_start();
include("bd/bd.php");
include("impl/Utils.php");
require_once("impl/JSON.php");
include("impl/Colores.php");

if(permitido("fun1011", $_SESSION['codigo'])==true)
{
    $funcion = $_GET['funcion'];
    if($funcion == "ListarColores"){
          ListarColores($_GET['star'], $_GET['limit'], $_GET['sort'], $_GET['dir'], $_GET['callback'], $_GET['_dc'],$_GET['where'], false);
    }
    else if($funcion == "GuardarNuevaColores"){
        GuardarNuevaColores($_GET['callback'], $_GET['_dc'], $_GET['where'], false);

    }
    else if($funcion == "BuscarColoresPorId"){
        $idcolor=$_GET['idcolor'];
        BuscarColoresPorId($_GET['callback'], $_GET['_dc'],$idcolor, false);

    }
    else if($funcion == "GuardarEditarColores"){
        $idcolor=$_GET['idcolor'];
        GuardarEditarColores($_GET['callback'], $_GET['_dc'],$idcolor, false);

    }
      else if($funcion == "EliminarColores"){
        $idcolor=$_GET['idcolor'];
        EliminarColores($_GET['callback'], $_GET['_dc'],$idcolor, false);

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