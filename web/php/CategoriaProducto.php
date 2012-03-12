<?php 
session_name("trailer");
session_start();
include("bd/bd.php");
require_once("impl/JSON.php");
require_once("impl/Utils.php");
require_once("impl/CategoriaProducto.php");
///dadasdasdaadas
if(permitido("fun1013", $_SESSION['codigo'])==true)
{
    //    hola
    $funcion = $_GET['funcion'];
    if($funcion=="ListarCategoriaProducto"){
        ListarCategoriaProducto($_GET['star'], $_GET['limit'], $_GET['sort'], $_GET['dir'], $_GET['callback'], $_GET['_dc'],$_GET['where'], false);


    }
    else if($funcion == "GuardarNuevaCategoriaProducto"){
        GuardarNuevaCategoriaProducto($_GET['callback'], $_GET['_dc'], $where, false);
    }
    else if($funcion=="GuardarEditarCategoriaProducto"){
        $idcategoriaproducto = $_GET['idcategoriaproducto'];
        GuardarEditarCategoriaProducto($_GET['callback'], $_GET['_dc'], $idcategoriaproducto, false);

    }
    else if($funcion == "BuscarCategoriaProductoPorId"){
        $idcategoriaproducto = $_GET['idcategoriaproducto'];
        BuscarCategoriaProductoPorId($_GET['callback'], $_GET['_dc'], $idcategoriaproducto, false);

    }

    else if ($funcion =="EliminarCategoriaProducto"){
        $idcategoriaproducto = $_GET['idcategoriaproducto'];
        EliminarCategoriaProducto($_GET['callback'], $_GET['_dc'], $idcategoriaproducto, false);

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