<?php
session_name("trailer");
session_start();
include("bd/bd.php");
require_once("impl/JSON.php");
require_once("impl/Utils.php");
require_once("impl/InventarioProducto.php");
require_once("impl/Producto.php");
require_once("impl/MovimientoProducto.php");
if(permitido("fun2010", $_SESSION['codigo'])==true)
{
    $funcion = $_GET['funcion'];
    if($funcion == "ListarInventariosProductos"){
        $band = false;
        if($_GET['buscarcodigo'] != null)
        {
            if($band == false) {
                $extras .= "  mp.codigo LIKE '%".$_GET['buscarcodigo']."%'";
                $band = true;
            }
            else {
                $extras .= " AND  mp.codigo LIKE '%".$_GET['buscarcodigo']."%'";
            }
        }
        if($_GET['buscarnombre']){
            if($band==false){
                $extras .= "  mp.nombre LIKE '%".$_GET['buscarnombre']."%'";
                $band = true;

            }
            else {
                $extras .= " AND  mp.nombre LIKE '%".$_GET['buscarnombre']."%'";
            }


        }
        if($_GET['buscarcategoria']){
            if($band==false){
                $extras .= "  cat.nombre LIKE '%".$_GET['buscarcategoria']."%'";
                $band = true;

            }
            else {
                $extras .= " AND  cat.nombre LIKE '%".$_GET['buscarcategoria']."%'";
            }


        }

        ListarInventarioProductos($_GET['star'], $_GET['limit'], $_GET['sort'], $_GET['dir'], $_GET['callback'], $_GET['_dc'],$extras, false);
    }



    else if($funcion == "GuardarInventarioProducto"){
        $resultado = $_GET['resultado'];
        $json = new Services_JSON();
        $datos = $json->decode($resultado);

        GuardarInventarioProducto($_GET['callback'], $_GET['_dc'], $datos, false);

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