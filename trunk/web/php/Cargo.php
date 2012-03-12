<?php 
session_name("trailer");
session_start();
include("bd/bd.php");
include("impl/Utils.php");
require_once("impl/JSON.php");
include("impl/Cargo.php");
if(permitido("fun1005", $_SESSION['codigo'])==true)
{
    $funcion = $_GET['funcion'];
    if($funcion == "ListarCargos"){
        ListarCargos($_GET['star'], $_GET['limit'], $_GET['sort'], $_GET['dir'], $_GET['callback'], $_GET['_dc'],$_GET['where'], false);
    }

    else if($funcion == "GuardarNuevoCargo"){
        GuardarNuevoCargo($return);

    }
    else if($funcion == "BuscarCargoPorId"){
        $idcargo = $_GET['idcargo'];
        BuscarCargoPorId($idcargo,false);
    }
    else if($funcion == "GuardarEditarCargo"){
        GuardarEditarCargo();

    }
    else if($funcion == "EliminarCargo"){
        $idcargo=$_GET['idcargo'];
        EliminarCargo($idcargo, $_GET['callback'],$_GET['_dc'],false);

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