<?php 
session_name("trailer");
session_start();
include("bd/bd.php");
require_once("impl/JSON.php");
require_once("impl/Utils.php");
require_once("impl/Distribucion.php");
require_once("impl/Empleado.php");
require_once("impl/MateriaPrima.php");
require_once("impl/MovimientoKardexMateriaPrima.php");
require_once("impl/OrdenProduccion.php");
//require_once("impl/K")
///dadasdasdaadas
if(permitido("fun8000", $_SESSION['codigo'])==true)
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


    else if($funcion =="CargarPanelDistribucion"){
        $numeroproduccion = $_GET['op'];
        CargarPanelDistribucion($_GET['callback'], $_GET['_dc'], $numeroproduccion, false);
    }
    else if($funcion =="CargarDetalleItems"){
        $id = $_GET['id'];
        CargarDetalleItems($_GET['callback'], $_GET['_dc'], $id, false);
    }
    else if($funcion =="CargarDetalleItemsEncargado"){
        $id = $_GET['idencargado'];
        $iditem = $_GET['iditem'];
        CargarDetalleItemsEncargado($_GET['callback'], $_GET['_dc'], $id,$iditem, false);
    }
    else if($funcion =="ListarDetalleDistribucion"){
        $id = $_GET['id'];
        ListarDetalleDistribucion($_GET['callback'], $_GET['_dc'], $id, false);
    }
    else if($funcion =="CargarDetalleItemsMaterial"){
        $id = $_GET['id'];
        $idi = $_GET['iditm'];
        $iden = $_GET['idencagado'];
        CargarDetalleItemsMaterial($_GET['callback'], $_GET['_dc'], $id,$idi,$iden, false);
    }
    else if($funcion =="GuardarItemDistribucion"){

        GuardarItemDistribucion($_GET['callback'], $_GET['_dc'], $where, false);
    }
    else if($funcion =="eliminarItemDistribucion"){
        $id = $_GET['id'];
        eliminarItemDistribucion($_GET['callback'], $_GET['_dc'],$id, false);
    }
    else if($funcion =="eliminarItemDistribucionMaterial"){
        $id = $_GET['id'];
        eliminarItemDistribucionMaterial($_GET['callback'], $_GET['_dc'],$id, false);
    }

    //    else if($funcion =="eliminarItemCorte"){
    //         $id = $_GET['idcorte'];
    //        eliminarItemCorte($_GET['callback'], $_GET['_dc'],$id, false);
    //    }
    else if($funcion == "GuardarNuevaDistribucion"){
        $resultado = $_GET['resultado'];
        $json = new Services_JSON();
        $datos = $json->decode($resultado);
        GuardarNuevaDistribucion($_GET['callback'], $_GET['_dc'], $datos, false);
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