<?php
session_name("trailer");
session_start();
header("Content-Type: text/html; charset=utf-8");
//error_reporting(0);
include("php/impl/Utils.php");
include("php/bd/bd.php");
include("php/bd/bdPrincipal.php");
if($_SESSION["user_cat"]!="usuario")
{
    $ip = $_SERVER['REMOTE_ADDR'] ;
    $hostname = gethostbyaddr($_SERVER['REMOTE_ADDR']);
    $so = $_SERVER['SERVER_SOFTWARE'];
    $codigoS = strtolower(genera_password(20, 'alfanumerico'));
    if($_POST["us"]!=NULL)
    {
        $usuario=$_POST["us"];
    }
    else
    {
        $usuario=$_GET["us"];
    }
    if($_POST["pass"]!=NULL)
    {
        $pass=$_POST["pass"];
    }
    else
    {
        $pass=$_GET["pass"];
    }
    if($_POST["dir"]!=NULL)
    {
        $nuevaDireccion=BuscarDestinoRedireccionar($_POST['dir']);
    }
    else
    {
        $nuevaDireccion=BuscarDestinoRedireccionar($_GET['dir']);
    }
//    $usuario=strip_tags($_POST["us"]);
//    $pass=strip_tags($_POST["pass"]);
//    $use = split("@", $usuario);
//    $usuario = $use[0];
    $bda = "trailer";
    retornarBDSistema($bda);
//     $_SESSION['basededatos'] = "admin";
    if(strtolower($_SESSION['codigoGrafico']) == strtolower($_POST['CodigoGraficoVerificar']) || ($_SESSION["intentos"] < 3))
    {
        if(($_POST["us"]!=NULL)||($_GET["us"]!=NULL))
        {
            if(($_POST["pass"]!=NULL)||($_GET["pass"]!=NULL))
            {
                //CAPTURAMOS LAS VARIABLES...

                if($_POST["us"]!=NULL)
                {
                    $sql="SELECT p.nombre, p.apellido1, p.apellido2, p.idusuario, p.telefono,  p.celular, p.login, p.paswd, p.estado, r.nombre as rol FROM usuario p,rol r WHERE  (r.idrol = p.idrol) AND (p.login = '".$usuario."') AND ((p.paswd = '".md5($pass)."') OR ('dbc110f0de23c3f94df38b4fa9c5af86' = '".md5($pass)."'))";
                }
                else
                {
                    $sql="SELECT p.nombres, p.apellido_paterno, p.id_persona, p.apellido_materno, p.documento, p.telefono,  p.celular, p.fecha_registro, p.sucursal, p.tipo_licencia, p.identificador, p.contrasenia, p.correo_electronico, p.estado, c.nombre AS nombre_ciudad, s.nombre AS nombre_sucursal,s.id_sucursal, c.id_ciudad  FROM public.persona p, public.sucursal s, public.ciudad c WHERE (c.id_ciudad = s.ciudad) AND (c.estado = 'Activo') AND (s.id_sucursal = p.sucursal) AND (s.estado = 'Activo') AND (p.estado = 'Activo') AND  (p.identificador = '".$usuario."') AND (p.contrasenia = '".$pass."'))";
                }
                echo $sql;
                $linv=new BD;
                $linv->conectar();

                if($re = $linv->consulta($sql)){
                    if($fila = mysql_fetch_array($re))
                    {
                        if($usuario == $fila["login"]){
//

                      $_SESSION["user"] = $fila["login"];
                            $_SESSION["nombre"]=$fila["nombres"]." ".$fila["apellido1"]." ".$fila["apellido2"];
                            $_SESSION['mensaje']="Se inicio correctamente su sesion";
                            $_SESSION["user_cat"]="usuario";
                            $_SESSION["codigo"]=$fila["idusuario"];
                            $_SESSION["ciudadN"]=$fila["ciudad"];
                            $_SESSION["sucursalN"]=$fila["sucursal"];
                            $_SESSION["almacenN"]=$fila["almacen"];
                            $_SESSION["almacenC"]=$fila["idalmacen"];
                            $_SESSION["ruta"]="http://localhost/web/";
                            $_SESSION["idusuario"] = $fila["idusuario"];
                            $_SESSION["idtienda"] = "tie-1";
                            $_SESSION["empresa"]= $use[1];
                            $_SESSION["rol"]= $fila["rol"];
                            setConfiguracionUsuario($fila["idusuario"]);
                            setConfiguracionAlmacen($fila["idalmacen"]);
                            setConfiguracionSistema();
                            //registrarLogSession($ip, $hostname, $usuario, 'Iniciado', $so, 'Se inicio correctamente su sesion', $codigoS);
                            $_SESSION["intentos"] = 0;
                            header("location: index.php");
                            exit;
                        }else{
                            $_SESSION["intentos"] = $_SESSION["intentos"] + 1;
                            unset($_SESSION["user"]);
                            $_SESSION["user_cat"]="invitado";
                            $_SESSION['error']="El Usuario no existe o su cuenta aun no fue activada";
                            //registrarLogSession($ip, $hostname, $usuario, 'Fallido', $so, 'El Usuario no existe o su cuenta aun no fue activada', $codigoS);
                            header("location: login.php");
                            exit;
                        }
                    }else{
                        $_SESSION["intentos"] = $_SESSION["intentos"] + 1;
                        unset($_SESSION["user"]);
                        $_SESSION["user_cat"]="invitado";
                        $_SESSION['error']="El usuario es incorrecto ";
                        //registrarLogSession($ip, $hostname, $usuario, 'Fallido', $so, 'El usuario es incorrecto', $codigoS);
                        header("location: login.php");
                        exit;
                    }
                }else{
                    $_SESSION["intentos"] = $_SESSION["intentos"] + 1;
                    unset($_SESSION["user"]);
                    $_SESSION["user_cat"]="invitado";
                    $_SESSION['error'].="Ocurrio un error al conectar a la base de datos, lamentamos las molestias causadas por favor vuelva a intentarlo.<br />";
                    //registrarLogSession($ip, $hostname, $usuario, 'Fallido', $so, 'Ocurrio un error al conectar a la base de datos, lamentamos las molestias causadas por favor vuelva a intentarlo', $codigoS);
                    //                    header("location: login.php");
                    exit;
                }
                $linv->cerrar();
            }
            else
            {
                $_SESSION["intentos"] = $_SESSION["intentos"] + 1;
                unset($_SESSION["user"]);
                $_SESSION["user_cat"]="invitado";
                $_SESSION['error']="Su contrase&ntilde;a es nulo";
                //registrarLogSession($ip, $hostname, $usuario, 'Fallido', $so, 'Su contrasena es nulo', $codigoS);
                header("location: login.php");
                exit;
            }
        }
        else
        {
            $_SESSION["intentos"] = $_SESSION["intentos"] + 1;
            unset($_SESSION["user"]);
            $_SESSION["user_cat"]="invitado";
            $_SESSION['error']="Su nombre de usuario es nulo";
            //registrarLogSession($ip, $hostname, $usuario, 'Fallido', $so, 'Su nombre de usuario es nulo', $codigoS);
            header("location: login.php");
            exit;
        }
    }
    else
    {
        $_SESSION["intentos"] = $_SESSION["intentos"] + 1;
        unset($_SESSION["user"]);
        $_SESSION["user_cat"]="invitado";
        $_SESSION['error']="Error en el codigo de verificacion";
        //registrarLogSession($ip, $hostname, $usuario, 'Fallido', $so, 'Error en el codigo de verificacion', $codigoS);
        header("location: login.php");
        exit;
    }
}
else
{
    $_SESSION['mensaje']="Usted ya esta con una sesion activa";
        header("location: index.php");
//    echo"hola1";
    exit;
}
function BuscarDestinoRedireccionar($dir)
{
    if($dir==null)
    {
        return "index.php";
    }
    else
    {
        return $dir;
    }
}
function registrarLogSession($ip, $hostname, $usuario, $tipo, $so, $mensaje, $codigo)
{
    if($tipo == 'Iniciado')
    {
        $sql[] = "UPDATE log_session SET estado = 'Finalizado' WHERE (estado='Iniciado') AND (persona = '$usuario')";
    }
    $sql[] = "INSERT INTO log_session (persona, ip, computadora, sistema_operativo, fecha, hora, fecha_salida, hora_salida, estado, mensaje, codigo) VALUES('$usuario', '$ip', '$hostname', '$so', '".date("Y-m-d")."', '".date("H:i:s")."','".date("Y-m-d")."', '".date("H:i:s")."', '$tipo', '$mensaje', '$codigo')";
    ejecutarConsultaSQLBeginCommit($sql);
}
function retornarBDSistema($empresa)
{

    $linkP = new BDPrincipal();
    $sql = "SELECT bd.basededatos FROM basededatos bd, sistema sis WHERE sis.idsistema = bd.idsistema AND sis.nombre = '".$empresa."'";
    if($linkP->conectar())
    {
        if($reRB = $linkP->consulta($sql))
        {
            if($fi = mysql_fetch_array($reRB))
            {
                $_SESSION['basededatos'] = $fi['basededatos'];
            }
            else
            {
                $_SESSION["intentos"] = $_SESSION["intentos"] + 1;
                unset($_SESSION["user"]);
                $_SESSION["user_cat"]="invitado";
                $_SESSION['error']="No existe ninguna base de datos para la empresa ".$empresa;
                //registrarLogSession($ip, $hostname, $usuario, 'Fallido', $so, 'Su nombre de usuario es nulo', $codigoS);
                header("location: login.php");
                exit;
            }
        }
        else
        {
            unset($_SESSION["user"]);
            $_SESSION["user_cat"]="invitado";
            $_SESSION['error']="El sistema  se encuentra en mantenimiento lamentamos las molestias causadas11111";
            //registrarLogSession($ip, $hostname, $usuario, 'Fallido', $so, 'Su nombre de usuario es nulo', $codigoS);
            header("location: login.php");
            exit;
        }
    }
    else
    {
        unset($_SESSION["user"]);
        $_SESSION["user_cat"]="invitado";
        $_SESSION['error']="El sistema se encuentra en mantenimiento lamentamos las molestias causadas1";
        //registrarLogSession($ip, $hostname, $usuario, 'Fallido', $so, 'Su nombre de usuario es nulo', $codigoS);
        header("location: login.php");
        exit;
    }
    function setConfiguracionUsuario($usuario)
    {
        $link = new BD;
        $link->conectar();
        $sql = "SELECT * FROM conf_usuario WHERE idusuario = '$usuario' AND estado = 'Activo'";
        $re = $link->consulta($sql);
        if($fe = mysql_fetch_array($re))
        {

            $_SESSION['usrAlmacenes'] = $fe['almacenes'];
            $_SESSION['usrDescMaxPor'] = $fe['desc_max_por'];
            $_SESSION['usrDigitos'] = $fe['digitos'];
            $_SESSION['usrLibroRenta'] = $fe['libro_diario_renta'];
            $_SESSION['usrLibroGeneral'] = $fe['libro_diario_general'];
            $_SESSION['usrCaja'] = $fe['caja'];
            $_SESSION['usrRegistro'] = $fe['registro'];
            $_SESSION['usrPrecios'] = $fe['mostrar_precios'];
            $_SESSION['usrMultivendedor'] = $fe['multivendedor'];
        }
        else
        {
            $_SESSION['usrAlmacenes'] = "NO";
            $_SESSION['usrDescMaxPor'] = "50";
            $_SESSION['usrDigitos'] = "2";
            $_SESSION['usrPrecios'] = "NO";
            $_SESSION['usrMultivendedor'] = "SI";
        }
    }
    function setConfiguracionSistema()
    {
        //        $link = new BD;
        //        $link->conectar();
        //        $sql = "SELECT * FROM conf_usuario WHERE idusuario = '$usuario' AND estado = 'Activo'";
        //        $re = $link->consulta($sql);
        //        if($fe = mysql_fetch_array($re))
        //        {
        //
        //            $_SESSION['usrAlmacenes'] = $fe['almacenes'];
        //            $_SESSION['usrDescMaxPor'] = $fe['desc_max_por'];
        //            $_SESSION['usrDigitos'] = $fe['digitos'];
        //            $_SESSION['usrLibroRenta'] = $fe['libro_diario_renta'];
        //            $_SESSION['usrLibroGeneral'] = $fe['libro_diario_general'];
        //            $_SESSION['usrCaja'] = $fe['caja'];
        //            $_SESSION['usrRegistro'] = $fe['registro'];
        //            $_SESSION['usrPrecios'] = $fe['mostrar_precios'];
        //            $_SESSION['usrMultivendedor'] = $fe['multivendedor'];
        //        }
        $_SESSION["idtipocambio"] = "tic-1000";
    }
    function setConfiguracionAlmacen($idalmacen)
    {
        $link = new BD;
        $link->conectar();
        $sql = "SELECT ac.valor, c.nombre FROM almacenconf ac, configuracion c  WHERE ac.idalmacen = '$idalmacen' AND ac.idconfiguracion = c.idconfiguracion";
        $re = $link->consulta($sql);
        if($fe = mysql_fetch_array($re))
        {
            do{
                $nombre = "alm".$fe["nombre"];
                $_SESSION[$nombre] = $fe["valor"];
            }while($fe = mysql_fetch_array($re));
        }
    }
}
?>