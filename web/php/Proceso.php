
<?php 
session_name("trailer");
session_start();
include("bd/bd.php");
include("impl/Utils.php");
require_once("impl/JSON.php");
require_once("impl/Proceso.php");
require_once("impl/Empleado.php");
require_once("impl/CuentaPrincipal.php");
if(permitido("fun1012", $_SESSION['codigo'])==true)
{
    $funcion = $_GET['funcion'];
    if($funcion == "ListarProcesos"){
        ListarProcesos($_GET['star'], $_GET['limit'], $_GET['sort'], $_GET['dir'], $_GET['callback'], $_GET['_dc'],$_GET['where'], false);
    }

    else if($funcion == "CargarNuevoProceso"){
        //devolver encargadoM{idempleado , nombre }
         CargarNuevoProceso($_GET['callback'], $_GET['_dc'], $_GET['where'], false);
    }
     else if($funcion == "GuardarNuevoProceso"){
        GuardarNuevoProceso($_GET['callback'], $_GET['_dc'], $_GET['where'], false);
    }

    else if($funcion == "BuscarProcesoPorId"){
         //devolver encargadoM{idempleado , nombre }
        $idproceso = $_GET['idproceso'];
       
        BuscarProcesoPorId($_GET['callback'], $_GET['_dc'], $idproceso, false);
       
    }
    else if($funcion == "GuardarEditarProceso"){
        $idproceso = $_GET['idproceso'];
        GuardarEditarProceso($_GET['callback'], $_GET['_dc'], $idproceso, false);

    }
    else if($funcion == "EliminarProceso"){
        $idproceso=$_GET['idproceso'];
        EliminarProceso($_GET['callback'], $_GET['_dc'], $idproceso, false);

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