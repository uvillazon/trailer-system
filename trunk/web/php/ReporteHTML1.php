<?php
session_name("trailer");
session_start();
include("impl/Ciudad.php");
include("impl/Reporte.php");
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
$idventa = $_GET['idventa'];
$idcompra = $_GET['idcompra'];

if($funcion == "compraHTML")
{//hooooo
    generarCompra($_GET['idcompra'], false);

}
if($funcion == "reportecomprasHTML")
{
    //reporteBalanceGeneral(false);
    reporteCompras(false);
}
else
if($funcion == "reporteproductoHTML")
{
    reporteProducto($_GET['idproducto'], false);

}
else
if($funcion == "reportekardexHTML")
{
    reporteKardex($_GET['idproducto'],$_GET['idalmacen'], false);

}
else if($funcion == "reporteclienteHTML")
{
    reporteCliente($_GET['idcliente'], false);

} 
else if($funcion == "reporteclientecreditoHTML")
{
    reporteClienteCredito($_GET['idcliente'], false);

} 
else if($funcion == "reporteproveedorHTML")
{
    reporteProveedor($_GET['idproveedor'], false);

}
else if($funcion == "reporteempleadoHTML")
{
    reporteEmpleadoHTML($_GET['idempleado'],false);
}
else if($funcion == "traspasoHTML")
{
    reporteTraspaso($_GET['idtraspaso'], false);

}

else if($funcion == "reporteentregaventaHTML")
{
    reporteEntregaventa($_GET['identrega'], false);

}
else if($funcion == "reporteentregacompraHTML")
{
    reporteEntregacompra($_GET['idcompra'], false);

}

else if($funcion == "reportecuentaproveedorHTML")
{
    reportecuentaproveedorHTML($_GET['idproveedor'], false);

}
else  if($funcion == "reporteproducto")
{
    reporteProducto($_GET['idproducto'], false);

}
else  if($funcion == "reporteproformaHTML")
{
    reporteProforma($_GET['idproforma'], false);

}

else 
if($funcion == "compraconcuentaHTML")
{//hooooo
    compraconcuentaHTML($_GET['idcompra'], false);

}
else
if($funcion == "reportealmacenHTML")
{
    reportealmacenHTML($_GET['idalmacen'], false);
}

else if($funcion == "reporteEntregaItemsHTML")
{
    reporteEntregaItemsHTML($_GET['identregacompra'], false);
}


else if($funcion == "reporteventasdiarias")
{
    reporteVentaDiaria($_GET['idalmacen'],false);//falta terminar
} 
else if($funcion == "reporteventaHTML")
{
    reporteVentaHTML($_GET['idventa'], false);
}
else  if($funcion == "reporteEntrega")
{
    reporteEntrega($_GET['identrega'], false);
}
else if($funcion == "reporteGeneralVenta")
{
    reporteGeneralVenta(false);
}

else if($funcion == "reportestokHTML")
{
    reporteStok( false);
}

else if($funcion == "reportecajeroHTML")
{
    reporteCajero($_GET['idusuario'],$_GET['fechaInicio'],$_GET['fechaFin'],false);
}
else if($funcion == "reporteProductoVendido")
{
    reporteProductoVendido(false);
}
else if($funcion == "clienteDeuda")
{
    
    clienteDeuda($_GET['idcliente'],false);

}
//else if($funcion == "reporteClienteCredito")
//{
//    reporteClienteCredito($_GET['idcliente'],false);
//}
?>
