<?php
//header('Content-type: application/pdf');
//header('Content-Disposition: attachment; filename="downloaded.pdf"');
session_name("trailer");
session_start();
//include("impl/Ciudad.php");
//include("impl/ListarUsuario.php");
include("impl/Reporte.php");
include("impl/Utils.php");
include("bd/bd.php");
require_once("impl/JSON.php");
//require('impl/fpdf.php');
include_once ("impl/html2fpdf.php");
//require_once("pdf/dompdf_config.inc.php");
//error_reporting(0);
$dev['mensaje'] = "";
$dev['error']   = "";
$dev['resultado'] = "";
$sql = "SELECT v.nombre FROM almacenes v WHERE v.idalmacen = '".$_GET['idalmacen']."'";

$funcionReporte = findBySqlReturnCampoUnique($sql, true, true, "nombre");
//
//echo $funcionReporte["resultado"];
//
//if($funcionReporte["resultado"] == "generarSalidaMaterialMediaCartaChorro")
//{
 
    $html = generarSalidaMaterialMediaCartaChorro($_GET['idalmacen'], true);
    echo $html;
//}
//-----------------------clase pdf----------------------
$pdf = new HTML2FPDF(); // Creamos una instancia de la clase HTML2FPDF

$pdf -> AddPage(); // Creamos una página
//$pdf = fopen("sample.html","r");

$pdf -> WriteHTML($html);//Volcamos el HTML contenido en la variable $html para crear el contenido del PDF

$pdf -> Output("doc.pdf");
?>