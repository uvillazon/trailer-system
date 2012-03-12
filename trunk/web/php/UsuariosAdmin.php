<?php 
session_name("trailer");
session_start();
include("impl/UsuariosAdmin.php");
require_once("impl/Almacen.php");
include("impl/Utils.php");
include("bd/bd.php");
require_once("impl/JSON.php");

if(permitido("fun1001", $_SESSION['codigo'])==true)
{
    $funcion = $_GET['funcion'];
    if($funcion == "ListarUsuariosAdmin"){
        ListarUsuariosAdmin($_GET['start'], $_GET['limit'], $_GET['sort'], $_GET['dir'], $_GET['callback'], $_GET['_dc'], $_GET['where'], false);
    }
    else if($funcion =="BuscarAlmacenes"){
        BuscarAlmacenes($_GET['callback'], $_GET['_dc'], "",false);
    }
    else if($funcion =="BuscarAlmacenPorUsuario"){
        $idusuario = $_GET['idusuario'];
        BuscarAlmacenPorUsuario($idusuario,$_GET['callback'], $_GET['_dc'],false);
    }
    else if($funcion == "GuardarNuevoUsuarioAdmin"){
        GuardarNuevoUsuarioAdmin();
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