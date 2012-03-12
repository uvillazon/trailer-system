<?php 
session_name("trailer");
session_start();
include("bd/bd.php");
require_once("impl/JSON.php");
require_once("impl/Utils.php");
require_once("impl/Cliente.php");
include("impl/Ciudad.php");
if(permitido("fun1006", $_SESSION['codigo'])==true)
{
    $funcion = $_GET['funcion'];
    if($funcion == "ListarClientes"){
        $band = false;
        if($_GET['buscarnit'] != null)
        {
            if($band == false) {
                $extras .= "  cli.nit LIKE '%".$_GET['buscarnit']."%'";
                $band = true;
            }
            else {
                $extras .= " AND  cli.nit LIKE '%".$_GET['buscarnit']."%'";
            }
        }
        if($_GET['buscarnombre'] != null)
        {
            if($band == false) {
                $extras .= " cli.nombre LIKE '%".$_GET['buscarnombre']."%'";
                $band = true;
            }
            else {
                $extras .= " AND cli.nombre LIKE '%".$_GET['buscarnombre']."%'";
            }
        }
        if($_GET['buscarapellido'] != null)
        {
            if($band == false) {
                $extras .= " cli.apellido1 LIKE '%".$_GET['buscarapellido']."%' ";
                $band = true;
            }
            else {
                $extras .= " AND cli.apellido1 LIKE '%".$_GET['buscarapellido']."%'";
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
      
        

        ListarClientes($_GET['star'], $_GET['limit'], $_GET['sort'], $_GET['dir'], $_GET['callback'], $_GET['_dc'],$extras, false);
    }



    else if($funcion == "CargarNuevoCliente"){
        CargarNuevoCliente($_GET['callback'], $_GET['_dc'], $_GET['where'], false);
    }
     else if($funcion == "GuardarNuevoCliente"){
        GuardarNuevoCliente($_GET['callback'], $_GET['_dc'], $_GET['where'], false);
    }
    else if($funcion == "BuscarClientePorId"){
        $idcliente = $_GET['idcliente'];
        BuscarClientePorId($_GET['callback'], $_GET['_dc'], $idcliente, false);
    }
    else if($funcion == "GuardarEditarCliente"){
        $idcliente = $_GET['idcliente'];
        GuardarEditarCliente($_GET['callback'], $_GET['_dc'], $idcliente, false);
    }
     else if($funcion == "EliminarCliente"){
        $idcliente = $_GET['idcliente'];
        EliminarCliente($_GET['callback'], $_GET['_dc'], $idcliente, false);
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