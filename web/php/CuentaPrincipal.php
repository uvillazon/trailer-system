
<?php 
session_name("trailer");
session_start();
include("bd/bd.php");
include("impl/Utils.php");
require_once("impl/JSON.php");
require_once("impl/CuentaPrincipal.php");
//require_once("impl/Empleado.php");
if(permitido("fun1001", $_SESSION['codigo'])==true)
{
    $funcion = $_GET['funcion'];
    if($funcion == "ListarCuentasPrincipales"){
        ListarCuentasPrincipales($_GET['star'], $_GET['limit'], $_GET['sort'], $_GET['dir'], $_GET['callback'], $_GET['_dc'],$_GET['where'], false);
    }

  
     else if($funcion == "GuardarNuevaCuentaPrincipal"){
        GuardarNuevaCuentaPrincipal($_GET['callback'], $_GET['_dc'], $_GET['where'], false);
    }

  
    else if($funcion == "GuardarEditarCuentaPrincipal"){
        $idcuenta = $_GET['idcuenta'];
        GuardarEditarCuentaPrincipal($_GET['callback'], $_GET['_dc'], $idcuenta, false);

    }
    else if($funcion == "EliminarCuentaPrincipal"){
        $idproceso=$_GET['idcuenta'];
        EliminarCuentaPrincipal($_GET['callback'], $_GET['_dc'], $idproceso, false);

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