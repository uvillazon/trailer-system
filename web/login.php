<meta http-equiv="X-UA-Compatible" content="chrome=1"> 
<?php
session_name("trailer");
session_start();
include("php/impl/Utils.php");
//include("php/impl/Usuario.php");
include("php/bd/bd.php");
$_SESSION['codigoGrafico'] = strtolower(genera_password(6, 'alfanumerico'));
if($_SESSION["user"]!=NULL)
{
//    echo "-----:".$_SESSION["user"].":-----";
        header("location: index.php");
}
//if(ObtenerNavegador($_SERVER["HTTP_USER_AGENT"]) == true)
//{
    echo '<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    <html>
    <head>

    <meta http-equiv="content-type" content="text/html; charset=utf-8"  />
            <title>Iniciar Sesion</title>
<script type="text/javascript" src="js/jscapslock.js"></script>
        <LINK href="css/index.css" rel="stylesheet" type="text/css" />
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
        </head>';

    echo '<body bgcolor="#231f20">';
//    echo "<CENTER><img src = 'php/configuracion/7c/membrete.png'></CENTER>";
    echo '<table align="center" border="0" cellpadding="0" cellspacing="0" style="width:220px;">';
    echo "<tr>";
    echo "<td>";
    echo '<div class="cuerpoTitulo" style="width:200px;" align="center"><strong>.:: Iniciar sesi&oacute;n ::.</strong><br />
                    <TABLE width=153 border=0 cellpadding="5">
                        <TR>
                            <TD height=160 colspan="3" align="left">
                                <form action="validar.php" method="post" target="_parent">
                                <b>Usuario</b><br />
                                <input type="text" name="us" class="cuerpo" size="25" /><br /><br />
                                <b>Contrase&ntilde;a</b><br />
                                <input type="password" name="pass" class="cuerpo" size="25" /><br />
                                <div id="errorTeclado"></div>
                                <br />
                                <center>';
    if($_SESSION["intentos"] >=3)
    {
        echo "<img src='php/dao/GenerarCodigoGrafico.php' style='width:60px; height: 17px;' border='1' alt='C' align='left'></img><input type='text' name='CodigoGraficoVerificar' id='CodigoGraficoVerificar' style='width: 70px;font-size:12px; border: solid 1px #000000; height: 17px;' maxlength='6'>";
    }
//    echo "----".$_SESSION["intentos"]."----";
    echo "<input type='hidden' value='".$_GET["dir"]."' name='dir' />";
    echo "<input type='submit' value='Aceptar' class='boton' />";
    echo "</center>";
    echo "</form>";
    if($_SESSION["intentos"] >=3)
    {
        echo "<b>Nota: </b>Por favor escriba el codigo de seguridad en la casilla correspondiente";
    }
    MostrarErroresAvisos();
    echo getDireccion();
    echo "<br>";
    echo "<hr size='1'>";
//    echo "Para ingresar al sistema de demostracion por favor ingreso con los datos que se muestran a continuacion: <br>";
//    echo "<b>Usuario: </b> edwin@kernel<br>";
//    echo "<b>Contrasena: </b> edwin<br>";
//    echo getRealIpAddr();
    echo "<br>";
//    echo $_SERVER['PHP_SELF'] ;
//    echo gethostbyaddr("66.240.232.62");
    echo "<br>";
    echo "<br>";
    echo "</TD>";
    echo "</TR>";
    echo "</TABLE>";
    echo "</div>";
    echo "</td>";
    echo "</tr>";
    echo "</table>";
    echo "</body>";
    echo "</html>";
//}
//else
//{
//    echo "<center>";
//    echo "Por razones de seguridad no esta permitido el ingreso al sistema desde este navegador.";
//    echo "<br>";
//    echo "7 CACHOEIRAS Te recomiendas usar <b>Google Chrome</b> puede descargarlo <a href='http://www.google.com/chrome/eula.html' title='Descargar Google Chrome'>aqui</a> o visite la pagina <a href='http://www.google.com/chrome'>http://www.google.com/chrome</a>";
//    echo "<br>";
//    echo "<a href='http://www.google.com/chrome'><img src='img/logo_google_chrome.png' border='0'></a>";
//    echo "</center>";
//}
function getRealIpAddr() {
    if (!empty($_SERVER['HTTP_CLIENT_IP'])) {
        $ip=$_SERVER['HTTP_CLIENT_IP'];
    } elseif (!empty($_SERVER['HTTP_X_FORWARDED_FOR'])) {
        $ip=$_SERVER['HTTP_X_FORWARDED_FOR'];
    }else{
        $ip=$_SERVER['REMOTE_ADDR'];
    }
    return $ip;
}
function getDireccion()
{
    return "http://".$_SERVER['SERVER_NAME'];
}
?>


