<?php 
session_name("trailer");
session_start();
include("bd/bd.php");
require_once("impl/JSON.php");
require_once("impl/Egreso.php");
require_once("impl/Utils.php");
require_once("impl/Cuentas.php");
require_once("impl/Empleado.php");

///dadasdasdaadas
if((permitido("fun3002", $_SESSION['codigo'])==true)||(permitido("fun3001", $_SESSION['codigo'])==true))
{
    //    hola
    $funcion = $_GET['funcion'];
    if($funcion == "ListarEgresos"){
        $band = false;
        if($_GET['buscafecha'] != null)
        {
            if($band == false) {
                $extras .= "   egr.fecha LIKE '%".$_GET['buscafecha']."%'";
                $band = true;
            }
            else {
                $extras .= " AND   egr.fecha LIKE '%".$_GET['buscafecha']."%'";
            }
        }
        if($_GET['buscardetalle'] != null)
        {
            if($band == false) {
                $extras .= " cue.nombre LIKE '%".$_GET['buscardetalle']."%'";
                $band = true;
            }
            else {
                $extras .= " AND cue.nombre LIKE '%".$_GET['buscardetalle']."%'";
            }
        }
        ListarEgresos($_GET['star'], $_GET['limit'], $_GET['sort'], $_GET['dir'], $_GET['callback'], $_GET['_dc'],$extras, false);
    }
    else if($funcion == "CargarPanelEgreso"){
        //tienes que devolver los siguientes objectos fuera de resultado
        //        encargadoM{idempleado,nombre)
        //        cuentaM(idcuenta,nombre)
        //        devolver de la sigiente forma
        //        {error:"true";mensaje:"ok";resultado:"";encargadoM{''};cuentaM{''}
        //            tomar en cuenta que los dos objectos estan fuera de resultado tomar en cuenta eso
        //}
        CargarPanelEgreso($_GET['callback'], $_GET['_dc'], $_GET['where'],false);
    }
    else if($funcion=="GuardarNuevoEgreso"){

        GuardarNuevoEgreso($_GET['callback'], $_GET['_dc'], $_GET['where'],false);


    }
    else if($funcion=="GuardarEditarEgresos"){
        $idegreso = $_GET['idegreso'];
        GuardarEditarEgresos($_GET['callback'], $_GET['_dc'], $idegreso,false);
    }
    else if($funcion=="BuscarEgresoPorId"){
        $idegreso = $_GET['idegreso'];
        BuscarEgresoPorId($_GET['callback'], $_GET['_dc'], $idegreso,false);
    }
    else if($funcion=="EliminarEgreso"){
        $idegreso = $_GET['idegreso'];
        EliminarEgreso($_GET['callback'], $_GET['_dc'], $idegreso,false);
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