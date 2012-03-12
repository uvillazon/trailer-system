<?php 
session_name("trailer");
session_start();
include("bd/bd.php");
require_once("impl/JSON.php");
require_once("impl/Utils.php");
require_once("impl/OpBordado.php");
//require_once("impl/K")
///dadasdasdaadas
if(permitido("fun7000", $_SESSION['codigo'])==true)
{
    //    hola
    $funcion = $_GET['funcion'];
    if($funcion == "ListarProduccionProcesos"){
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

    
    else if($funcion =="CargarPanelCorte"){
         $numeroproduccion = $_GET['op'];
        CargarPanelCorte($_GET['callback'], $_GET['_dc'], $numeroproduccion, false);
    }
    else if($funcion =="CargarMolde"){
         $id = $_GET['id'];
        CargarMolde($_GET['callback'], $_GET['_dc'], $id, false);
    }
    else if($funcion =="eliminarItemCorte"){
         $id = $_GET['idcorte'];
        eliminarItemCorte($_GET['callback'], $_GET['_dc'],$id, false);
    }
    else if($funcion == "GuardarNuevaCorte"){
        $resultado = $_GET['resultado'];
        $json = new Services_JSON();
        $datos = $json->decode($resultado);
        GuardarNuevaCorte($_GET['callback'], $_GET['_dc'], $datos, false);
    }

 
//    else if ($funcion =="GuardarProduccionProceso"){
//        $resultado = $_GET['resultado'];
//        $json = new Services_JSON();
//        $idproduccionproceso= $_GET['idcompra'];
//        $datos = $json->decode($resultado);
////        echo "jj";
//         GuardarProduccionProceso($datos, false);
//    }
//    else if ($funcion =="EliminarProduccionProceso"){
//        $idproduccionproceso = $_GET['idproduccionproceso'];
//
//
//        EliminarProduccionProceso($_GET['callback'], $_GET['_dc'], $idproduccionproceso, false);
//
//    }
//      else if ($funcion =="AprobarProduccionProceso"){
//        $idproduccionproceso = $_GET['idproduccionproceso'];
//
//
//        AprobarProduccionProceso($_GET['callback'], $_GET['_dc'], $idproduccionproceso, false);
//
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