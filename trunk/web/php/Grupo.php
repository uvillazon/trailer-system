<?php
session_name("trailer");
session_start();
include("bd/bd.php");
require_once("impl/JSON.php");
require_once("impl/Utils.php");
require_once("impl/Grupo.php");
require_once("impl/Componentes.php");


///dadasdasdaadas
if(permitido("fun1001", $_SESSION['codigo'])==true)
{
    //    hola
    $funcion = $_GET['funcion'];
    if($funcion == "ListarGrupos"){
        ListarGrupos($_GET['star'], $_GET['limit'], $_GET['sort'], $_GET['dir'], $_GET['callback'], $_GET['_dc'],$_GET['where'], false);
    }
    else if($funcion=="GuardarNuevoGrupo"){
        GuardarNuevoGrupo($_GET['callback'], $_GET['_dc'], $_GET['where'], false);

    }
   
    else if($funcion == "BuscarGruposPorId"){
        $idgrupo = $_GET['idgrupo'];
        BuscarGruposPorId($_GET['callback'], $_GET['_dc'], $idgrupo, false);

    }
    else if($funcion =="GuardarEditarGrupo"){
        $idgrupo = $_GET['idgrupo'];
        GuardarEditarGrupo($_GET['callback'], $_GET['_dc'], $idgrupo, false);

    }
    else if ($funcion =="EliminarGrupo"){
        $idgrupo = $_GET['idgrupo'];
        EliminarGrupo($_GET['callback'], $_GET['_dc'], $idgrupo, false);

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