<?php
session_name("trailer");
session_start();
include("bd/bd.php");
require_once("impl/JSON.php");
require_once("impl/Utils.php");
require_once("impl/InventarioMateriaPrima.php");
require_once("impl/KardexMateriaPrima.php");
require_once("impl/MovimientoKardexMateriaPrima.php");
if(permitido("fun1001", $_SESSION['codigo'])==true)
{
    $funcion = $_GET['funcion'];
    if($funcion == "ListarInventariosMateriasPrimas"){
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

        ListarInventarioMateriasPrimas($_GET['star'], $_GET['limit'], $_GET['sort'], $_GET['dir'], $_GET['callback'], $_GET['_dc'],$extras, false);
    }



    else if($funcion == "GuardarInventarioMateriaPrima"){
        $resultado = $_GET['resultado'];
        $json = new Services_JSON();
        $datos = $json->decode($resultado);

        GuardarInventarioMateriaPrima($_GET['callback'], $_GET['_dc'], $datos, false);

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