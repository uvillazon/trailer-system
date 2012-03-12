<?php
session_name("trailer");
session_start();
include("bd/bd.php");
require_once("impl/JSON.php");
require_once("impl/Utils.php");
require_once("impl/Empleado.php");
require_once("impl/Cargo.php");
require_once("impl/Ciudad.php");

if(permitido("fun1004", $_SESSION['codigo'])==true)
{
    $funcion = $_GET['funcion'];
    if($funcion == "ListarEmpleados"){

        ListarEmpleados($_GET['star'], $_GET['limit'], $_GET['sort'], $_GET['dir'], $_GET['callback'], $_GET['_dc'],$extras, false);
    }



    else if($funcion == "CargarNuevoEmpleado"){
        CargarNuevoEmpleado($_GET['callback'], $_GET['_dc'], $_GET['where'], false);
    }
    else if($funcion == "GuardarNuevoEmpleado"){
        GuardarNuevoEmpleado($_GET['callback'], $_GET['_dc'], $_GET['where'], false);
    }
    else if($funcion == "BuscarEmpleadoPorId"){
        $idempleado = $_GET['idempleado'];
        BuscarEmpleadoPorId($_GET['callback'], $_GET['_dc'], $idempleado, false);
    }
    //    else if($funcion == "BuscarEmpeladoPorId"){
    //        $idempleado = $_GET['idempleado'];
    //        BuscarEmpeladoPorId($_GET['callback'], $_GET['_dc'], $idempleado, false);
    //    }
    else if($funcion == "GuardarEditarEmpleado"){
        $idempleado = $_GET['idempleado'];
        GuardarEditarEmpleado($_GET['callback'], $_GET['_dc'], $idempleado, false);
    }
    else if($funcion == "EliminarEmpleado"){
        $idempleado = $_GET['idempleado'];
        EliminarEmpleado($_GET['callback'], $_GET['_dc'], $idempleado, false);
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