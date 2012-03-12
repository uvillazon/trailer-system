<?php 
session_name("trailer");
session_start();
include("bd/bd.php");
require_once("impl/JSON.php");
require_once("impl/Utils.php");
require_once("impl/Recepcion.php");
require_once("impl/Empleado.php");
require_once("impl/MateriaPrima.php");
require_once("impl/MovimientoKardexMateriaPrima.php");
require_once("impl/OrdenProduccion.php");
//require_once("impl/K")
///dadasdasdaadas
if(permitido("fun8001", $_SESSION['codigo'])==true)
{
    //    hola
    $funcion = $_GET['funcion'];
    if($funcion == "ListarProcesosDistrubucion"){
        //        $band = false;
        //        if($_GET['buscarnumeroorden'] != null)
        //        {
        //            if($band == false) {
        //                $extras .= "  prp.numeroorden LIKE '%".$_GET['buscarnumeroorden']."%'";
        //                $band = true;
        //            }
        //            else {
        //                $extras .= " AND  prp.numeroorden LIKE '%".$_GET['buscarnumeroorden']."%'";
        //            }
        //        }
        //        if($_GET['buscarnproduccion'] != null)
        //        {
        //            if($band == false) {
        //                $extras .= " prp.numeroproduccion LIKE '%".$_GET['buscarnproduccion']."%'";
        //                $band = true;
        //            }
        //            else {
        //                $extras .= " AND prp.numeroproduccion LIKE '%".$_GET['buscarnproduccion']."%'";
        //            }
        //        }
        //
        //
        //
        //
        //        ListarProduccionProcesos($_GET['star'], $_GET['limit'], $_GET['sort'], $_GET['dir'], $_GET['callback'], $_GET['_dc'],$extras, false);
    }


    else if($funcion =="CargarPanelRecepcion"){
        $numeroproduccion = $_GET['op'];
        CargarPanelRecepcion($_GET['callback'], $_GET['_dc'], $numeroproduccion, false);
    }
//    else if($funcion =="CargarDetalleItems"){
//        $id = $_GET['id'];
//        CargarDetalleItems($_GET['callback'], $_GET['_dc'], $id, false);
//    }
    else if($funcion =="CargarDetalleItemsEncargado"){
        $id = $_GET['idencargado'];
        CargarDetalleItemsEncargado($_GET['callback'], $_GET['_dc'], $id, false);
    }
    else if($funcion =="ListarDetalleEntregaDistribucion"){
        $id = $_GET['id'];
        ListarDetalleEntregaDistribucion($_GET['callback'], $_GET['_dc'], $id, false);
    }
    else if($funcion =="GuardarItemEntrega"){
        $id = $_GET['iditem'];
        $detalle = $_GET['detalle'];
        $fecha = $_GET['fecha'];
        $cantidad = $_GET['cantidad'];
        GuardarItemEntrega($_GET['callback'], $_GET['_dc'], $where, false);
    }
//    else if($funcion =="GuardarItemDistribucion"){
//
//        GuardarItemDistribucion($_GET['callback'], $_GET['_dc'], $where, false);
//    }
//    else if($funcion =="eliminarItemDistribucion"){
//        $id = $_GET['id'];
//        eliminarItemDistribucion($_GET['callback'], $_GET['_dc'],$id, false);
//    }
    else if($funcion =="eliminarItemDistribucionEntrega"){
        $id = $_GET['id'];
        eliminarItemDistribucionEntrega($_GET['callback'], $_GET['_dc'],$id, false);
    }

    //    else if($funcion =="eliminarItemCorte"){
    //         $id = $_GET['idcorte'];
    //        eliminarItemCorte($_GET['callback'], $_GET['_dc'],$id, false);
    //    }
//    else if($funcion == "GuardarNuevaDistribucion"){
//        $resultado = $_GET['resultado'];
//        $json = new Services_JSON();
//        $datos = $json->decode($resultado);
//        GuardarNuevaDistribucion($_GET['callback'], $_GET['_dc'], $datos, false);
//    }



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