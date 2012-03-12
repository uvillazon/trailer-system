<?php 
session_name("trailer");
session_start();
include("bd/bd.php");
require_once("impl/JSON.php");
require_once ("impl/Ciudad.php");
require_once ("impl/Empresa.php");
require_once ("impl/Utils.php");
require_once("impl/Responsable.php");

if(permitido("fun1007", $_SESSION['codigo'])==true)
{
    $funcion = $_GET['funcion'];
    if($funcion == "ListarEmpresas"){
        $band = false;
        if($_GET['buscarcodigo'] != null)
        {
            if($band == false) {
                $extras .= "  empr.codigo LIKE '%".$_GET['buscarcodigo']."%'";
                $band = true;
            }
            else {
                $extras .= " AND  empr.codigo LIKE '%".$_GET['buscarcodigo']."%'";
            }
        }
        if($_GET['buscarnombre'] != null)
        {
            if($band == false) {
                $extras .= "  empr.nombre LIKE '%".$_GET['buscarnombre']."%'";
                $band = true;
            }
            else {
                $extras .= " AND  empr.nombre LIKE '%".$_GET['buscarnombre']."%'";
            }
        }
        if($_GET['buscarciudad'] != null)
        {
            if($band == false) {
                $extras .= "  ciu.nombre LIKE '%".$_GET['buscarapellido']."%' ";
                $band = true;
            }
            else {
                $extras .= " AND  ciu.nombre LIKE '%".$_GET['buscarapellido']."%'";
            }
        }
//        consulta del where cuando se trata de otra tabla
//        if($_GET['buscarciudad'] != null)
//        {
//            if($band == false) {
//                $extras .= " ciu.nombre LIKE '%".$_GET['buscarciudad']."%' ";
//                $band = true;
//            }
//            else {
//                $extras .= " AND cli.apellido1 LIKE '%".$_GET['buscarciudad']."%'";
//            }
//        }
      
        

        ListarEmpresas($_GET['star'], $_GET['limit'], $_GET['sort'], $_GET['dir'], $_GET['callback'], $_GET['_dc'],$extras, false);
    }

  
    
    else if($funcion == "CargarNuevaEmpresa"){
        CargarNuevaEmpresa($_GET['callback'], $_GET['_dc'], $_GET['where'], false);
    }
     else if($funcion == "GuardarNuevaEmpresa"){
        GuardarNuevaEmpresa($_GET['callback'], $_GET['_dc'], $_GET['where'], false);
    }
    else if($funcion == "BuscarEmpresaPorId"){
        $idempresa = $_GET['idempresa'];
        BuscarEmpresaPorId($_GET['callback'], $_GET['_dc'], $idempresa, false);
    }
    else if($funcion == "GuardarEditarEmpresa"){
        $idempresa = $_GET['idempresa'];
        GuardarEditarEmpresa($_GET['callback'], $_GET['_dc'], $idempresa, false);
    }
     else if($funcion == "EliminarEmpresa"){
        $idempresa = $_GET['idempresa'];
        EliminarEmpresa($_GET['callback'], $_GET['_dc'], $idempresa, false);
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