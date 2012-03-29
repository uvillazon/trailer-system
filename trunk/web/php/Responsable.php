<?php 
session_name("trailer");
session_start();
include("bd/bd.php");
require_once("impl/JSON.php");
require_once ("impl/Utils.php");
require_once("impl/Responsable.php");
require_once("impl/Empresa.php");
require_once("impl/Ciudad.php");

if(permitido("fun1008", $_SESSION['codigo'])==true)
{
    $funcion = $_GET['funcion'];
    if($funcion == "ListarResponsables"){
        $band = false;
        if($_GET['buscarcodigo'] != null)
        {
            if($band == false) {
                $extras .= "  res.codigo LIKE '%".$_GET['buscarcodigo']."%'";
                $band = true;
            }
            else {
                $extras .= " AND  res.codigo LIKE '%".$_GET['buscarcodigo']."%'";
            }
        }
        if($_GET['buscarnombre'] != null)
        {
            if($band == false) {
                $extras .= "  res.nombre LIKE '%".$_GET['buscarnombre']."%'";
                $band = true;
            }
            else {
                $extras .= " AND  res.nombre LIKE '%".$_GET['buscarnombre']."%'";
            }
        }
        if($_GET['buscarempresa'] != null)
        {
            if($band == false) {
                $extras .= "  emp.nombre LIKE '%".$_GET['buscarempresa']."%' ";
                $band = true;
            }
            else {
                $extras .= " AND  emp.nombre LIKE '%".$_GET['buscarempresa']."%'";
            }
        }


        ListarResponsables($_GET['star'], $_GET['limit'], $_GET['sort'], $_GET['dir'], $_GET['callback'], $_GET['_dc'],$extras, false);


    }

    else if($funcion == "CargarNuevoResponsable"){
        CargarNuevoResponsable($_GET['callback'], $_GET['_dc'], $_GET['where'], false);
    }
    else if($funcion == "GuardarNuevoResponsable"){
        GuardarNuevoResponsable($_GET['callback'], $_GET['_dc'], $_GET['where'], false);
    }
    else if($funcion == "BuscarResponsablePorId"){
        $idresponsable = $_GET['idresponsable'];
        BuscarResponsablePorId($_GET['callback'], $_GET['_dc'], $idresponsable, false);
    }
    else if($funcion == "GuardarEditarResponsable"){
        $idresponsable = $_GET['idresponsable'];
        GuardarEditarResponsable($_GET['callback'], $_GET['_dc'], $idresponsable, false);
    }
    else if($funcion == "EliminarResponsable"){
       $idresponsable = $_GET['idresponsable'];
        EliminarResponsable($_GET['callback'], $_GET['_dc'], $idresponsable, false);
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