<?php 
session_name("trailer");
session_start();
include("bd/bd.php");
include("impl/Utils.php");
require_once("impl/JSON.php");
include("impl/Categoria.php");

if(permitido("fun1009", $_SESSION['codigo'])==true)
{
    $funcion = $_GET['funcion'];
    if($funcion == "ListarCategoria"){
          ListarCategoria($_GET['star'], $_GET['limit'], $_GET['sort'], $_GET['dir'], $_GET['callback'], $_GET['_dc'],$_GET['where'], false);
    }
    else if($funcion == "GuardarNuevaCategoria"){
        GuardarNuevaCategoria($_GET['callback'], $_GET['_dc'], $_GET['where'], false);

    }
    else if($funcion == "CargarDatosCategoriaPorId"){
        $idcategoria=$_GET['idcategoria'];       
        BuscarCategoriaPorId($_GET['callback'], $_GET['_dc'], $idcategoria, false);

    }
    else if($funcion == "GuardarEditarCategoria"){
        $idcategoria=$_GET['idcategoria'];
        GuardarEditarCategoria($_GET['callback'], $_GET['_dc'], $idcategoria, false);

    }
      else if($funcion == "EliminarCategoria"){
        $idcategoria=$_GET['idcategoria'];
        EliminarCategoria($_GET['callback'], $_GET['_dc'], $idcategoria, false);

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