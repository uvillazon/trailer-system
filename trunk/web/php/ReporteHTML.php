<?php
session_name("trailer");
session_start();
include("impl/ReporteHTML.php");
include("impl/Utils.php");
include("bd/bd.php");
require_once("impl/JSON.php");
//error_reporting(0);
$dev['mensaje'] = "";
$dev['error']   = "";
$dev['resultado'] = "";
//if(permitido("fun1024", $_SESSION['codigo'])==true)nofunciona//reportecuentaproveedorHTML
//{
$funcion = $_GET['funcion'];

if($funcion == "reporteEmpleadoHTML")
{//hooooo
    reporteEmpleadoHTML($_GET['idempleado'],false);

}

else if($funcion == "MovimientoProducto"){

    echo $_GET['materiaPrima'];
    MovimientoProducto($_GET['materiaPrima'], $_GET['fechaini'], $_GET['fechafin'], false);

}
else if($funcion == "MovimientoProductoTerminado"){

    //        echo $_GET['producto'];
    MovimientoProductoTerminado($_GET['producto'], $_GET['fechaini'], $_GET['fechafin'], false);

}
else if($funcion == "detalleCompra"){

    //echo $_GET['idcompra'];
    detalleCompra($_GET['idcompra'], false);

}
else if($funcion == "reporteBordadoHTML"){

    //echo $_GET['idcompra'];
    reporteBordadoHTML($_GET['idproducto'], false);

}
else if($funcion == "reporteProduccionHTML"){
    $id = $_GET['idproduccionproceso'];
    //echo $_GET['idcompra'];
    reporteProduccionHTML($id, false);

}
else if($funcion == "ListaProductos")
{
    //echo ("aqui");
    reporteProductos(false);
}
else if($funcion == "reporteBordadosHTML")
{
   
    ListaBordados(false);
}

?>
