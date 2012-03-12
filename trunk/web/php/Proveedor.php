<?php 
session_name("trailer");
session_start();
include("bd/bd.php");
require_once("impl/JSON.php");
require_once("impl/Proveedor.php");
require_once("impl/Ciudad.php");
require_once ("impl/Utils.php");

if(permitido("fun1002", $_SESSION['codigo'])==true)
{
    $funcion = $_GET['funcion'];
  if($funcion == "ListarProveedores"){
    ListarProveedores($_GET['star'], $_GET['limit'], $_GET['sort'], $_GET['dir'], $_GET['callback'], $_GET['_dc'],$_GET['where'], false);

  }
  else if($funcion == "CargarNuevoProveedor"){
        CargarNuevaProveedor($_GET['callback'], $_GET['_dc'], $_GET['where'], false);
    }
     else if($funcion == "GuardarNuevoProveedor"){
        GuardarNuevoProveedor($_GET['callback'], $_GET['_dc'], $_GET['where'], false);
    }
    else if($funcion == "BuscarProveedorPorId"){
        $idproveedor=$_GET['idproveedor'];
        BuscarProveedorPorId($_GET['callback'], $_GET['_dc'], $idproveedor, false);
    }
     else if($funcion == "GuardarEditarProveedores"){
        $idproveedor=$_GET['idproveedor'];
        GuardarEditarProveedores($_GET['callback'], $_GET['_dc'], $idproveedor, false);
    }
    else if ($funcion == "EliminarProveedor") {
        $idproveedor = $_GET['idempresa'];
        EliminarProveedor($_GET['callback'], $_GET['_dc'], $idproveedor, false);
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