<?php
header("Content-type: application/vnd.ms-excel");
$ar = "Reporte";
header("Content-Disposition: attachment; filename=$ar.xls");
session_name("Selkis");
session_start();
include("impl/Ciudad.php");
include("impl/ListarUsuario.php");
include("impl/Reporte.php");
include("impl/Utils.php");
include("bd/bd.php");
require_once("impl/JSON.php");
//error_reporting(0);
$dev['mensaje'] = "";
$dev['error']   = "";
$dev['resultado'] = "";
//if(permitido("fun1024", $_SESSION['codigo'])==true)
//{
$funcion = $_GET['function'];

$sql = "SELECT j.nombre FROM venta v, configimpresion ci, jasperreport j WHERE v.idventa = '".$_GET['idventa']."' AND v.idreport = ci.idconfjasper AND
ci.jasperreport = j.idjasperreport";

$funcionReporte = findBySqlReturnCampoUnique($sql, true, true, "nombre");

if($funcionReporte["resultado"] == "generarSalidaMaterialMediaCartaChorro")
{
    generarSalidaMaterialMediaCartaChorro($_GET['idventa'], false);
}

?>