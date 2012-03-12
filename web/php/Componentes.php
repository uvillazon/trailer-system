<?php
session_name("trailer");
session_start();
include("bd/bd.php");
require_once("impl/JSON.php");
require_once("impl/Utils.php");
require_once("impl/Componentes.php");
require_once("impl/Cotizacion.php");
require_once("impl/Proceso.php");
require_once("impl/MateriaPrima.php");
require_once("impl/DetalleCompra.php");
require_once("impl/Grupo.php");
require_once("impl/Producto.php");

if((permitido("fun2006", $_SESSION['codigo'])==true)||(permitido("fun2007", $_SESSION['codigo'])==true))
{
    $funcion = $_GET['funcion'];
    if($funcion == "BuscarComponentePorId"){
        $idgrupo = $_GET['idgrupo'];
        BuscarComponentesPorId($_GET['callback'], $_GET['_dc'], $idgrupo, false);
    }
    else if($funcion =="GuardarComponentesGrupo"){
        $resultado = $_GET['resultado'];
        $json = new Services_JSON();
        $datos = $json->decode($resultado);
        GuardarComponenteGrupo($_GET['callback'], $_GET['_dc'], $datos, false);



    }
    else if($funcion =="GuardarComponentesProducto"){
        $resultado = $_GET['resultado'];
        $json = new Services_JSON();
        $datos = $json->decode($resultado);
        GuardarComponenteProducto($_GET['callback'], $_GET['_dc'], $datos, false);



    }
    else if($funcion =="GuardarComponentesProductoDuplicar"){
        $resultado = $_GET['resultado'];
        $json = new Services_JSON();
        $datos = $json->decode($resultado);
        GuardarComponentesProductoDuplicar($_GET['callback'], $_GET['_dc'], $datos, false);



    }

    else if($funcion=="BuscarComponentePorIdProducto"){
        $idproducto =  $_GET['idproducto'];
        BuscarComponentePorIdProducto($_GET['callback'], $_GET['_dc'], $idproducto, false);
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