<?php 
session_name("trailer");
session_start();
include("bd/bd.php");
require_once("impl/JSON.php");
require_once("impl/Utils.php");
require_once("impl/Cuentas.php");
require_once("impl/CuentaPrincipal.php");

if(permitido("fun3000", $_SESSION['codigo'])==true)
{
    $funcion = $_GET['funcion'];
    if($funcion == "ListarCuentas"){
        ListarCuentas($_GET['star'], $_GET['limit'], $_GET['sort'], $_GET['dir'], $_GET['callback'], $_GET['_dc'],$_GET['where'], false);
    }
   
     else if($funcion == "GuardarNuevaCuenta"){
        GuardarNuevaCuenta($_GET['callback'], $_GET['_dc'], $_GET['where'], false);
    }
    else if($funcion == "CargarCuentaEgreso"){
         CargarCuentaEgreso($_GET['callback'], $_GET['_dc'], $_GET['where'], false);
    }
    else if($funcion == "BuscarCuentasPorId"){
        $idcuenta = $_GET['idcuenta'];
        BuscarCuentaPorId($_GET['callback'], $_GET['_dc'], $idcuenta, false);
    }
    else if($funcion == "GuardarEditarCuenta"){
        $idcuenta = $_GET['idcuenta'];
        GuardarEditarCuenta($_GET['callback'], $_GET['_dc'], $idcuenta, false);
    }
     else if($funcion == "EliminarCuenta"){
       $idcuenta = $_GET['idcuenta'];
        EliminarCuenta($_GET['callback'], $_GET['_dc'], $idcuenta, false);
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