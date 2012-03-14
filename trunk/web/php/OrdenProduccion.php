<?php 
session_name("trailer");
session_start();
include("bd/bd.php");
require_once("impl/JSON.php");
require_once("impl/Utils.php");
require_once("impl/OrdenProduccion.php");
require_once("impl/Cliente.php");
require_once("impl/Producto.php");
require_once("impl/Responsable.php");
require_once("impl/Empresa.php");
require_once("impl/Credito.php");
require_once("impl/MateriaPrima.php");
require_once("impl/Colores.php");

///dadasdasdaadas
if((permitido("fun4001", $_SESSION['codigo'])==true)||(permitido("fun4000", $_SESSION['codigo'])==true))
{
    //    hola
    $funcion = $_GET['funcion'];
    if($funcion == "ListarOrdenProduccion"){
        $band = false;
        if($_GET['buscarnumeroorden'] != null)
        {
            if($band == false) {
                $extras .= "  ord.numeroorden LIKE '%".$_GET['buscarnumeroorden']."%'";
                $band = true;
            }
            else {
                $extras .= " AND  ord.numeroorden LIKE '%".$_GET['buscarnumeroorden']."%'";
            }
        }
        if($_GET['buscarempresa'] != null)
        {
            if($band == false) {
                $extras .= " ord.cliente LIKE '%".$_GET['buscarempresa']."%'";
                $band = true;
            }
            else {
                $extras .= " AND ord.cliente LIKE '%".$_GET['buscarempresa']."%'";
            }
        }




        ListarOrdenProduccion($_GET['star'], $_GET['limit'], $_GET['sort'], $_GET['dir'], $_GET['callback'], $_GET['_dc'],$extras, false);
    }

    else if($funcion == "CargarPanelOrdenProduccion"){
        CargarPanelOrdenProduccion($_GET['callback'], $_GET['_dc'], $_GET['where'], false);
    }
    else if($funcion == "GuardarOrdenProduccion"){
        $resultado = $_GET['resultado'];
        $json = new Services_JSON();
        $datos = $json->decode($resultado);
        $id= $_GET['idorden'];

        if(($id == "")||($id == null)){
           GuardarOrdenProduccion($_GET['callback'], $_GET['_dc'], $datos, false);
        }
        else{
            GuardarEditarOrdenProduccion($_GET['callback'], $_GET['_dc'], $datos, false);
        }
        
    }
    else if ($funcion == "EliminarOrdenProduccion")
    {
        EliminarOrdenProduccion($_GET['idordenproduccion'],$_GET['callback'], $_GET['_dc'], false);
    }
    else if($funcion == "EliminarOrdenProduccionItem")
    {
        //echo("llego");
        EliminarOrdenProduccionItem($_GET['idordenDetalle'],$_GET['callback'], $_GET['_dc'], false);
    }
    else if ($funcion == "BuscarOrdenPorId"){
        $id = $_GET['idordenproduccion'];
        BuscarOrdenPorId($_GET['callback'], $_GET['_dc'], $id, false);
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