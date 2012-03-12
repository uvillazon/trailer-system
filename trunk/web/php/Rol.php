<?php 
session_name("trailer");
session_start();
include("impl/Rol.php");
include("impl/Funcion.php");
include("impl/Utils.php");
include("bd/bd.php");
require_once("impl/JSON.php");

if(permitido("fun1001", $_SESSION['codigo'])==true)
{
    $funcion = $_GET['funcion'];
    if($funcion == "listarRol"){
        findAllRol($_GET['star'], $_GET['limit'], $_GET['sort'], $_GET['dir'], $_GET['callback'], $_GET['_dc'], false);
    }

    else if($funcion == "findRolByIdFunciones")
    {
        findRolByIdFunciones($_GET['idrol'], $_GET['callback'], $_GET['_dc'], false);
    } else if($funcion == "findAllRolWithFunctions")
    {

        findAllRolWithFunctions($_GET['idrol'], $_GET['callback'], $_GET['_dc'], false);
    }
    else if($funcion =="txSaveUpdateRol"){
        $resultado = $_GET['resultado'];
        $json = new Services_JSON();
        $datos = $json->decode($resultado);
        $idrol = $datos->idrol;
        $nombrerol = $datos->nombre;
        $estadorol = $datos->estado;
        $funciones = $datos->funciones;
        txSaveUpdateRol($idrol, $nombrerol, $estadorol, $funciones, false);
    }
    else if($funcion =="txDuplicate"){
        $idrol = $_GET['idrol'];
        $nombre = $_GET['nombre'];
        $deG = txDuplicateRol($idrol, $nombre, false);
    }
    else if($funcion =="txDelete"){
        $resultado = $_GET['resultado'];
        $json = new Services_JSON();
        $datos = $json->decode($resultado);
        $roles= $datos->resultado;
        $da = txDeleteRol($roles, $_GET['callback'], $_GET['_dc'], false);
    }

    else if($funcion == "GuardarNuevaLinea"){
        InsertarNuevaLinea();
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