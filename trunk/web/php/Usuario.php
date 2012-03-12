<?php 
session_name("trailer");
session_start();
include("impl/UsuariosAdmin.php");
include("impl/Rol.php");
include("impl/Utils.php");
include("bd/bd.php");
require_once("impl/JSON.php");

if(permitido("fun1000", $_SESSION['codigo'])==true)
{
    $funcion = $_GET['funcion'];

    if($funcion == "findAllUsuario")
    {
        $band = false;
        if($_GET['buscarci'] != null)
        {
            if($band == false) {
                $extras .= " u.ci LIKE '%".$_GET['buscarci']."%'";
                $band = true;
            }
            else {
                $extras .= " AND u.ci LIKE '%".$_GET['buscarci']."%'";
            }
        }
        if($_GET['buscarnombres'] != null)
        {
            if($band == false) {
                $extras .= " u.nombre LIKE '%".$_GET['buscarnombres']."%'";
                $band = true;
            }
            else {
                $extras .= " AND u.nombre LIKE '%".$_GET['buscarnombres']."%'";
            }
        }
        if($_GET['buscarapellido'] != null)
        {
            if($band == false) {
                $extras .= " (u.apellido1 LIKE '%".$_GET['buscarapellido']."%' OR u.apellido2 LIKE '%".$_GET['buscarapellido']."%') ";
                $band = true;
            }
            else {
                $extras .= " AND (u.apellido1 LIKE '%".$_GET['buscarapellido']."%' OR u.apellido2 LIKE '%".$_GET['buscarapellido']."%') ";
            }
        }
        if($_GET['buscarlogin'] != null)
        {
            if($band == false) {
                $extras .= " u.login LIKE '%".$_GET['buscarlogin']."%'";
                $band = true;
            }
            else {
                $extras .= " AND u.login LIKE '%".$_GET['buscarlogin']."%'";
            }
        }
        findAllUsuario($_GET['start'], $_GET['limit'], $_GET['sort'], $_GET['dir'], $_GET['callback'], $_GET['_dc'], $extras, false);
    }
    else if($funcion == "findUsuarioByIdRolSucursalAlmacen")
    {
        findUsuarioByIdRolSucursalAlmacen($_GET['idusuario'], $_GET['callback'], $_GET['_dc'], false);
    } else if($funcion == "findRolSucursalAlmacenForNewUsuario")
    {
        findRolSucursalAlmacenForNewUsuario($_GET['callback'], $_GET['_dc'], false);
    }
   
    else if($funcion =="EliminarUsuario"){
         txDeleteUsuario($_GET['idusuario'], $_GET['callback'], $_GET['_dc'], false);
    }
    
    else if($funcion == "GuardarNuevoUsuario"){

        $resultado = $_POST['resultado'];
        $json = new Services_JSON();
        $datos = $json->decode($resultado);
        $da = txNewUsuario($datos->nombres, $datos->apellido1, $datos->apellido2, $datos->carnetidentidad, $datos->email, $datos->telefono, $datos->celular,
            $datos->login, $datos->paswd1, $datos->paswd2, $datos->idrol, $datos->estado, $datos->idalmacen, false);

    }
    
    else if($funcion == "txUpdateUsuario"){

        $resultado = $_POST['resultado'];
        $json = new Services_JSON();
        $datos = $json->decode($resultado);
        $da = txUpdateUsuario($datos->idusuario, $datos->nombres, $datos->apellido1, $datos->apellido2, $datos->carnetidentidad, $datos->email, $datos->telefono, $datos->celular,
            $datos->login, $datos->paswd1, $datos->paswd2, date("Y-m-d"), $datos->idrol, $datos->estado, $datos->idalmacen, false);
      
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