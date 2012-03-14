<?php
function ListaBordados($return)
{
    $html .= "<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'>";
    $html .= "<html>";
    $html .= "<head>";
    $html .= "<title></title>";
    $html .= "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>";
    $html .= "<link href='".$_SESSION["ruta"]."css/print.css' rel='stylesheet' type='text/css' media='all'>";
    //$html .= "<link href='../print.css' rel='stylesheet' type='text/css' media='all'>";
    $html .= "</head>";
    $html .= "<body>";
    $html .= "<table  ' align='center'  border='0' style='width:750px; font-size:11px; font-family:Tahoma;'>  ";
    $html .= "<tr>";
   
        $html .= "<td style='width:150px;height:100px;border-bottom:1px solid #000000;text-align:center;font-size:20px;font-family:Tahoma;'>";
        $html .= "REPORTE<br>DE<br> BORDADOS";
        $html .= "</td>";
    //    $html .= "<td style='width:200px;height:100px;border-bottom:1px solid #000000;text-align:right;font-size:11px;font-family:Tahoma;'>";
    //    $html .= "<table cellpadding='0' cellspacing='0' border='0' style='width:200px;'>";
    //    //$html .= "<tr><td align='right'><img src='".$_SESSION["ruta"]."php/impl/codigo.jpg'><td><tr>";
    //    $html .= "<tr><td align='right' style='font-size:11px; font-family:Tahoma; text-align:center;font-weight:bold;'>".$ven[0]['idcompra']."<td><tr>";
    //    $html .= "</table>";
    //    $html .= "</td>";
    $html .= "</tr>";
   // $html .= "<tr>";
    $html .= "<td>";
  //  $html .= "<table cellpadding='0' cellspacing='0' border='0' style='width:750px; font-size:11px; font-family:Tahoma;'>";
   // $html .= "<tr><td style='width:100px;font-size:11px;font-weight:bold;'></td><td style='width:500px;' ></td></tr>";
   // $html .= "<tr><td style='font-size:11px;font-weight:bold;'>Almacen:</td><td>".$almacen."</td></tr>";
   // $html .= "<tr><td style='font-size:11px;font-weight:bold;'>Tipo Cambio:</td><td>".$cambioNuevo."</td></tr>";
    //$html .= "<tr><td style='font-size:11px;font-weight:bold;'>Fecha</td><td>".date("Y-m-d")."</td></tr><br>";
   // $html .= "</table>";
    $html .= "</td>";
    $html .= "</tr>";
    //        $html .= "<tr>";
    $sql = "SELECT
  prod.idproducto,
  prod.nombre,
  prod.imagen,
  emp.nombre as empresa
FROM
  productos prod,
  empresas emp
WHERE
  prod.idempresa = emp.idempresa";
    //    MostrarConsulta($sql);
    $producto = dibujarTuplaOfSQLNormal($sql, "Informacion de los Bordados");
    $html2 .= $producto['resultado'];
    $html .="</tr>";
    $html .="<tr>";
    $html .="<td align='center'>$html2</td>";

    //echo "no esta vacio";
    $sqlKar = "SELECT
  k.saldocantidad AS 'Cantidad Disponible en Almacen',
  k.costounitario AS Costo,
  k.fechamodificacion AS Fecha,
  k.saldobs AS 'Inventario en Bs',
  k.precio2bs AS 'Precio por Mayor',
  k.precio1bs AS 'Precio por Unidad'
FROM
  kardexalmacen k
WHERE
  k.idalmacen = '$idalmacen' AND
  k.idproducto = '$idproducto'
";


    //    $sqlKar = "SELECT k.saldocantidad AS Cantidad, k.costounitario AS Costo, k.fechamodificacion AS Fecha
    //FROM kardexalmacen k WHERE k.idproducto = '$idproducto' and k.idalmacen = '$idalmacen' ";
    $kardex = dibujarTuplaOfSQLNormal($sqlKar, "Kardex");
    $html1 .= $kardex['resultado'];
    $html .="<td align='center'>$html1</td>";
    $html .= "</tr>";


    $html .= "<tr><td colspan='4' align='center' style='border-top:solid 1px #000000;'><div id='pie'>
</div></td></tr>";
    $html .= "</table>";
    $html .= "</body>";
    $html .= "</html>";
    if($return == true)
    {
        return $html;
    }
    else
    {
        echo $html;
    }

}
function reporteProductos($return)
{
     $hoy = date("Y-m-d");

        $html.="    <!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'>";
        $html.="<html>";
        $html.=" <head>";
        $html.=" <title></title>";
        $html.=" <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>";
        // $html.=" <link href='".$_SESSION["ruta"]."css/print.css' rel='stylesheet' type='text/css' media='all'>";
        //<link href='../print.css' rel='stylesheet' type='text/css' media='all'>
        $html.="  </head>";
        $html.="  <body>";
        $html.=" <table cellpadding='0' cellspacing='0' border='0' style='width:750px; font-size:11px; font-family:Tahoma;'>";
        $html.="  <tr>";
        $html.="  <td style='width:400px;height:100px;border-bottom:1px solid #000000;'>ROPA DE TRABAJO Y MATERIAL PUBLICITARIO TEXTIL<br>
      De: Martha Magne Iquise<br>
      Calle Agustín Aspiazu Nº 640<br>
      Zona Las Cuadras<br>
      </td>";
        $html.=" <td style='width:150px;height:100px;border-bottom:1px solid #000000;text-align:center;font-size:20px;font-family:Tahoma;'>MOVIMIENTO <br>";
        $html.="      DE<br>";
        $html.="PRODUCTOS</td>";
        $html.="  <td style='width:200px;height:100px;border-bottom:1px solid #000000;text-align:right;font-size:11px;font-family:Tahoma;'>";
        $html.="   <table cellpadding='0' cellspacing='0' border='0' style='width:200px;'>";
        $html.="   <tr>";
        $html.="     <td align='right'>";
        $html.=" <tr><td> </td> FECHA:  $hoy     </tr>";
        $html.="  <td>";
        $html.="   <tr>";
        $html.="       <tr>";
        $html.="        <td align='right' style='font-size:11px; font-family:Tahoma; text-align:center;font-weight:bold;'>";

        $html.="         <td><tr>";
        $html.="  </table>";
        $html.="   </td>";
        $html.="   </tr>";
        $html.="   <tr>";
        $html.="   <td colspan='3'>";
        $html.="   <table cellpadding='0' cellspacing='0' border='0' style='width:750px; font-size:11px; font-family:Tahoma;'>";
        $html.="   <tr><td style='width:100px;font-size:11px;font-weight:bold;'></td><td style='width:500px;' ></td></tr><br>";
        $html.="    </table>";
        $html.="    </td>";
        $html.="   </tr>";

        $html.="          <tr>";
        $html.="  <td colspan='1'>";

        $html.="   </td>";

        $html.="   <tr>";
        $html.="     <td>";
        $html.="<strong>Responsable:</strong> ".$responsable;
        $html.="</td>";
        $html.="   </tr>";

        $html.="   <tr>";
        $html.="     <td>";
        $html.="<strong>Proveedor: </strong>".$proveedor;
        $html.="</td>";
        $html.="   </tr>";
        $html.="   <tr>";
        $html.="     <td><strong>Por lo Siguiente:....</strong></td>";
        $html.="   </tr>";
        $sql = "SELECT
  det.cantidad AS Cantidad,
  det.detalle AS Detalle,
  det.unidad AS Unidad,
  det.preciounitario AS `P/uni`,
  det.preciototal AS Total
FROM
  detallecompra det
WHERE
  det.idcompra = '$idcompra'";
        $table = dibujarTablaOfSQLNormal($sql, "Detalle Compra");
        $html.="  <tr><td colspan='4' align='center'><div id='pie'>";
        $html.="  <p>";
        $html.=$table['resultado'];
        $html .="</p>";
        $html.="  <p> </p>";
        $html.=" </div></td></tr>";

        $html.="   <tr>";
        $html.="     <td><strong>Son:</strong>  ".convertir_a_letras($totalMonto);
        $html.="</td>";
        $html.="     <td></td>";

        $html.="     <td>Total Precio</td>";
        $html.="   </tr>";

        $html.="   <tr>";
        $html.="     <td></td>";
        $html.="     <td></td>";

        $html.="     <td>Total Descuento</td>";
        $html.="   </tr>";
        $html.="   <tr>";
        $html.="     <td></td>";
        $html.="     <td></td>";

        $html.="     <td>Total Compra</td>";
        $html.="   </tr>";

        $html.="   <tr><td colspan='4' align='center' style='border-top:solid 1px #000000;'><div id='pie'>";
        $html.="   <p><br />
         Cochabamba - Bolivia<br />
         Telf.: 4233928 - 4542962
    </p>
</div></td></tr>

    </table>
    </body>
    </html>";
        if($return == true)
        {
            return $html;
        }
        else
        {
            echo $html;
        }
    
}
function MovimientoProducto($codigoproducto,$fechainiV,$fechafinV, $return)
{

    //echo "codigob".$codigobarra;
    //echo "codigo".$codigoproducto;
    if(($codigoproducto =="")){
        echo "INGRESE MATERIA PRIMA";
        exit();
    }




    $html .= "<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'>";
    $html .= "<html>";
    $html .= "<head>";
    $html .= "<title></title>";
    $html .= "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>";
    $html .= "<link href='".$_SESSION["ruta"]."css/print.css' rel='stylesheet' type='text/css' media='all'>";
    //$html .= "<link href='../print.css' rel='stylesheet' type='text/css' media='all'>";
    $html .= "</head>";
    $html .= "<body>";
    $html .= "<table cellpadding='0' cellspacing='0' border='0' style='width:750px; font-size:11px; font-family:Tahoma;'>";
    $html .= "<tr>";
    //$html .= "<td style='width:400px;height:100px;border-bottom:1px solid #000000;'><img style='height:100px;' src='logo.jpg'>";
    //    $html .= "</td>";
    $html .= "<td style='width:150px;height:100px;border-bottom:1px solid #000000;text-align:center;font-size:20px;font-family:Tahoma;'>";
    $html .= "MOVIMIENTO <br>DE<br> PRODUCTOS";
    $html .= "</td>";
    $html .= "<td style='width:200px;height:100px;border-bottom:1px solid #000000;text-align:right;font-size:11px;font-family:Tahoma;'>";
    $html .= "<table cellpadding='0' cellspacing='0' border='0' style='width:200px;'>";
    //$html .= "<tr><td align='right'><img src='".$_SESSION["ruta"]."php/impl/codigo.jpg'><td><tr>";
    //    $html .= "<tr><td align='right' style='font-size:11px; font-family:Tahoma; text-align:center;font-weight:bold;'>".$ven[0]['idcompra']."<td><tr>";
    $html .= "</table>";
    $html .= "</td>";
    $html .= "</tr>";
    $html .= "<tr>";
    $html .= "<td colspan='3'>";
    $html .= "<table cellpadding='0' cellspacing='0' border='0' style='width:750px; font-size:11px; font-family:Tahoma;'>";
    $html .= "<tr><td style='width:100px;font-size:11px;font-weight:bold;'></td><td style='width:500px;' ></td></tr>";
    $html .= "<tr><td style='font-size:11px;font-weight:bold;'>Producto:</td><td>".$codigoproducto."</td></tr>";
    //    $html .= "<tr><td style='font-size:11px;font-weight:bold;'>Tipo Cambio:</td><td>".$cambioNuevo."</td></tr>";
    $html .= "<tr><td style='font-size:11px;font-weight:bold;'>Fecha</td><td>".date("Y-m-d")."</td><td></td></tr><br>";
    $html .= "</table>";
    $html .= "</td>";
    $html .= "</tr>";

    //        $html .= "<tr>";
    $html .= "<td colspan='1'>";
    if($codigoproducto != null)
    {
        //            echo "no esta vacio";
        $sql = "SELECT
  mat.codigo,
  mat.nombre,
  mat.caracteristica,
  mat.stockminimo,
  cat.nombre as categoria,
  col.nombre as color
FROM
  materiaprima mat,
  categorias cat,
  colores col
WHERE
  mat.idmateriaprima = '$codigoproducto' AND
  mat.idcategoria = cat.idcategoria AND
  col.idcolor = mat.idcolo rORDER BY
  movm.fecha,
  movm.hora";

    }


    //    MostrarConsulta($sql);
    $producto = dibujarTuplaOfSQLNormal($sql, "Informacion de materia prima");
    $html .= $producto['resultado'];
    $html .= "</td>";

    $sqlKar1 = " SELECT
  CONCAT(movm.fecha, ' - ', movm.hora) AS Fecha,
  map.nombre AS `Detalle Material`,
  movm.descripcion AS Detalle,
  movm.entrada AS Entrada,
  movm.salida AS Salida,
  movm.saldo AS Saldo,
  movm.costounitario AS `C/u`,
  movm.ingreso AS Ingreso,
  movm.egreso AS Egreso,
  movm.saldobs AS `Saldo en Bs`,
  movm.saldocantidad AS 'Disponible',
  movm.numero ,
  movm.id AS movimiento
FROM
  materiaprima map,
  movimientokardexmateriaprima movm
WHERE
  map.idmateriaprima = movm.idmateriaprima AND
  map.idmateriaprima = '$codigoproducto' AND
  movm.fecha BETWEEN '$fechainiV' AND '$fechafinV' ORDER BY
  movm.fecha,
  movm.hora,
  movm.numero";
//    echo $sqlKar1;
    $kardex = dibujarTablaOfSQLNormal($sqlKar1, "Movimiento Materia Prima");
    $html1 .= $kardex['resultado'];
    //    $html .="
    //    $html1
    //";
    //
    //
    //    $html .="
    //    <tr><td>HOLA MUNDO</td></tr>
    //";
    $html .= "<tr><td colspan='4' align='center'><div id='pie'>
    <p>$html1
    </p>
</div></td></tr>";
    $html .= "<tr><td colspan='4' align='center' style='border-top:solid 1px #000000;'><div id='pie'>
    <p><br />
         Cochabamba - Bolivia<br />

    </p>
</div></td></tr>";

    $html .= "</table>";
    $html .= "</body>";
    $html .= "</html>";
    if($return == true)
    {
        return $html;
    }
    else
    {
        echo $html;
    }
}

function MovimientoProductoTerminado($codigoproducto,$fechainiV,$fechafinV, $return)
{

    //echo "codigob".$codigobarra;
    //echo "codigo".$codigoproducto;
    if(($codigoproducto =="")){
        echo "INGRESE PRODUCTO";
        exit();
    }




    $html .= "<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'>";
    $html .= "<html>";
    $html .= "<head>";
    $html .= "<title></title>";
    $html .= "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>";
    $html .= "<link href='".$_SESSION["ruta"]."css/print.css' rel='stylesheet' type='text/css' media='all'>";
    //$html .= "<link href='../print.css' rel='stylesheet' type='text/css' media='all'>";
    $html .= "</head>";
    $html .= "<body>";
    $html .= "<table cellpadding='0' cellspacing='0' border='0' style='width:750px; font-size:11px; font-family:Tahoma;'>";
    $html .= "<tr>";
    //$html .= "<td style='width:400px;height:100px;border-bottom:1px solid #000000;'><img style='height:100px;' src='logo.jpg'>";
    //    $html .= "</td>";
    $html .= "<td style='width:150px;height:100px;border-bottom:1px solid #000000;text-align:center;font-size:20px;font-family:Tahoma;'>";
    $html .= "MOVIMIENTO <br>DE<br> PRODUCTOS";
    $html .= "</td>";
    $html .= "<td style='width:200px;height:100px;border-bottom:1px solid #000000;text-align:right;font-size:11px;font-family:Tahoma;'>";
    $html .= "<table cellpadding='0' cellspacing='0' border='0' style='width:200px;'>";
    //$html .= "<tr><td align='right'><img src='".$_SESSION["ruta"]."php/impl/codigo.jpg'><td><tr>";
    //    $html .= "<tr><td align='right' style='font-size:11px; font-family:Tahoma; text-align:center;font-weight:bold;'>".$ven[0]['idcompra']."<td><tr>";
    $html .= "</table>";
    $html .= "</td>";
    $html .= "</tr>";
    $html .= "<tr>";
    $html .= "<td colspan='3'>";
    $html .= "<table cellpadding='0' cellspacing='0' border='0' style='width:750px; font-size:11px; font-family:Tahoma;'>";
    $html .= "<tr><td style='width:100px;font-size:11px;font-weight:bold;'></td><td style='width:500px;' ></td></tr>";
    $html .= "<tr><td style='font-size:11px;font-weight:bold;'>Producto:</td><td>".$codigoproducto."</td></tr>";
    //    $html .= "<tr><td style='font-size:11px;font-weight:bold;'>Tipo Cambio:</td><td>".$cambioNuevo."</td></tr>";
    $html .= "<tr><td style='font-size:11px;font-weight:bold;'>Fecha</td><td>".date("Y-m-d")."</td><td></td></tr><br>";
    $html .= "</table>";
    $html .= "</td>";
    $html .= "</tr>";

    //        $html .= "<tr>";
    $html .= "<td colspan='1'>";
    if($codigoproducto != null)
    {
        //            echo "no esta vacio";
        $sql = "SELECT
  mat.codigo AS 'Codigo',
  mat.nombre AS 'Nombre',
  mat.medidas AS 'Medidas',
  mat.logo AS 'Logo',
  mat.canal AS 'Canal',
  mat.puntadas AS 'Puntadas',
  mat.ancho AS 'Ancho',
  mat.alto AS 'Alto',
 gru.nombre AS Grupo,
  mat.tela AS Tela,
 cat.nombre AS Categoria,
  mat.saldocantidad AS 'Saldo Disponible',
  mat.costounitario AS 'Costo Unitario',
  mat.precio1bs AS 'Precio de Venta'

FROM
  productos mat,
  categoriaproducto cat,
  `grupos` gru
WHERE
  mat.idproducto = '$codigoproducto' AND
  mat.idcategoriaproducto = cat.idcategoriaproducto AND
  mat.idgrupo = gru.idgrupo";

    }


    //    MostrarConsulta($sql);
    $producto = dibujarTuplaOfSQLNormal($sql, "Informacion de producto");
    $html .= $producto['resultado'];
    $html .= "</td>";

    $sqlKar1 = " SELECT
  CONCAT(movm.fecha, ' - ', movm.hora) AS Fecha,
  map.codigo AS `Codigo Producto`,
  movm.descripcion AS Detalle,
  movm.entrada AS Entrada,
  movm.salida AS Salida,
  movm.saldo AS Saldo,
  movm.costounitario AS `C/u`,
  movm.ingreso AS Ingreso,
  movm.egreso AS Egreso,
  movm.saldobs AS `Saldo en Bs`
FROM
  `productos` map,
  `movimientoproducto` movm
WHERE
  map.`idproducto` = movm.`idproducto` AND
  map.`idproducto` = '$codigoproducto' AND
  movm.fecha BETWEEN '$fechainiV' AND '$fechafinV' ORDER BY
  movm.fecha,
  movm.hora";
//    echo $sqlKar1;
    $kardex = dibujarTablaOfSQLNormal($sqlKar1, "Movimiento de Producto");
    $html1 .= $kardex['resultado'];
    //    $html .="
    //    $html1
    //";
    //
    //
    //    $html .="
    //    <tr><td>HOLA MUNDO</td></tr>
    //";
    $html .= "<tr><td colspan='4' align='center'><div id='pie'>
    <p>$html1
    </p>
</div></td></tr>";
    $html .= "<tr><td colspan='4' align='center' style='border-top:solid 1px #000000;'><div id='pie'>
    <p><br />
         Cochabamba - Bolivia<br />

    </p>
</div></td></tr>";

    $html .= "</table>";
    $html .= "</body>";
    $html .= "</html>";
    if($return == true)
    {
        return $html;
    }
    else
    {
        echo $html;
    }
}

function detalleCompra($idcompra)
{
    //    $html = "";
    $sql = "
SELECT
  com.numerodocumento,
  com.fecha,
  com.montototal,
  com.tipodocumento,
  com.montoapagar,
  com.descuento,
  com.descuentobs,
  com.observacion,
  com.ordenproduccion,
  emp.nombre AS encargado,
  pro.nombre AS proveedor
FROM
  `compras` com,
  `empleados` emp,
  `proveedores` pro
WHERE
  com.idcompra = '$idcompra' AND
  com.idempleado = emp.idempleado AND
  com.idproveedor = pro.idproveedor

";
    $datos = getTablaToArrayOfSQL($sql);
    if($datos['error'] == "false")
    {
        echo "No existe esta compra ...............";
        exit;
    }
    else
    {
        $ven = $datos["resultado"];
        $responsable = $ven[0]['encargado'];
        $proveedor = $ven[0]['proveedor'];
        $totalMonto = $ven[0]['montototal'];
        $totalDescuento = $ven[0]['descuentobs'];
        $totalNeto = $ven[0]['montoapagar'];


        //$totalMonto = redondear($totalMonto, $_SESSION['usrDigitos']);
        //$totalCredito = redondear($totalCredito, $_SESSION['usrDigitos']);
        $totalDescuento = redondear($totalDescuento, $_SESSION['usrDigitos']);
        $totalNeto = redondear($totalNeto, $_SESSION['usrDigitos']);

/*aqui modificamos los campos segun la monedad */
        $hoy = date("Y-m-d");

        $html.="    <!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'>";
        $html.="<html>";
        $html.=" <head>";
        $html.=" <title></title>";
        $html.=" <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>";
        // $html.=" <link href='".$_SESSION["ruta"]."css/print.css' rel='stylesheet' type='text/css' media='all'>";
        //<link href='../print.css' rel='stylesheet' type='text/css' media='all'>
        $html.="  </head>";
        $html.="  <body>";
        $html.=" <table cellpadding='0' cellspacing='0' border='0' style='width:750px; font-size:11px; font-family:Tahoma;'>";
        $html.="  <tr>";
        $html.="  <td style='width:400px;height:100px;border-bottom:1px solid #000000;'>ROPA DE TRABAJO Y MATERIAL PUBLICITARIO TEXTIL<br>
      De: Martha Magne Iquise<br>
      Calle Agustín Aspiazu Nº 640<br>
      Zona Las Cuadras<br>
      </td>";
        $html.=" <td style='width:150px;height:100px;border-bottom:1px solid #000000;text-align:center;font-size:20px;font-family:Tahoma;'>MOVIMIENTO <br>";
        $html.="      DE<br>";
        $html.="PRODUCTOS</td>";
        $html.="  <td style='width:200px;height:100px;border-bottom:1px solid #000000;text-align:right;font-size:11px;font-family:Tahoma;'>";
        $html.="   <table cellpadding='0' cellspacing='0' border='0' style='width:200px;'>";
        $html.="   <tr>";
        $html.="     <td align='right'>";
        $html.=" <tr><td> </td> FECHA:  $hoy     </tr>";
        $html.="  <td>";
        $html.="   <tr>";
        $html.="       <tr>";
        $html.="        <td align='right' style='font-size:11px; font-family:Tahoma; text-align:center;font-weight:bold;'>";

        $html.="         <td><tr>";
        $html.="  </table>";
        $html.="   </td>";
        $html.="   </tr>";
        $html.="   <tr>";
        $html.="   <td colspan='3'>";
        $html.="   <table cellpadding='0' cellspacing='0' border='0' style='width:750px; font-size:11px; font-family:Tahoma;'>";
        $html.="   <tr><td style='width:100px;font-size:11px;font-weight:bold;'></td><td style='width:500px;' ></td></tr><br>";
        $html.="    </table>";
        $html.="    </td>";
        $html.="   </tr>";

        $html.="          <tr>";
        $html.="  <td colspan='1'>";

        $html.="   </td>";

        $html.="   <tr>";
        $html.="     <td>";
        $html.="<strong>Responsable:</strong> ".$responsable;
        $html.="</td>";
        $html.="   </tr>";

        $html.="   <tr>";
        $html.="     <td>";
        $html.="<strong>Proveedor: </strong>".$proveedor;
        $html.="</td>";
        $html.="   </tr>";
        $html.="   <tr>";
        $html.="     <td><strong>Por lo Siguiente:....</strong></td>";
        $html.="   </tr>";
        $sql = "SELECT
  det.cantidad AS Cantidad,
  det.detalle AS Detalle,
  det.unidad AS Unidad,
  det.preciounitario AS `P/uni`,
  det.preciototal AS Total
FROM
  detallecompra det
WHERE
  det.idcompra = '$idcompra'";
        $table = dibujarTablaOfSQLNormal($sql, "Detalle Compra");
        $html.="  <tr><td colspan='4' align='center'><div id='pie'>";
        $html.="  <p>";
        $html.=$table['resultado'];
        $html .="</p>";
        $html.="  <p> </p>";
        $html.=" </div></td></tr>";

        $html.="   <tr>";
        $html.="     <td><strong>Son:</strong>  ".convertir_a_letras($totalMonto);
        $html.="</td>";
        $html.="     <td></td>";

        $html.="     <td>Total Precio</td>";
        $html.="   </tr>";

        $html.="   <tr>";
        $html.="     <td></td>";
        $html.="     <td></td>";

        $html.="     <td>Total Descuento</td>";
        $html.="   </tr>";
        $html.="   <tr>";
        $html.="     <td></td>";
        $html.="     <td></td>";

        $html.="     <td>Total Compra</td>";
        $html.="   </tr>";

        $html.="   <tr><td colspan='4' align='center' style='border-top:solid 1px #000000;'><div id='pie'>";
        $html.="   <p><br />
         Cochabamba - Bolivia<br />
         Telf.: 4233928 - 4542962
    </p>
</div></td></tr>

    </table>
    </body>
    </html>";
        if($return == true)
        {
            return $html;
        }
        else
        {
            echo $html;
        }
    }
}
function reporteBordadoHTML($idproducto, $return)
{
   
    $almacen = $_SESSION['nombrealmacen'];
    $html .= "<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'>";
    $html .= "<html>";
    $html .= "<head>";
    $html .= "<title></title>";
    $html .= "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>";
    $html .= "<link href='".$_SESSION["ruta"]."css/print.css' rel='stylesheet' type='text/css' media='all'>";
    //$html .= "<link href='../print.css' rel='stylesheet' type='text/css' media='all'>";
    $html .= "</head>";
    $html .= "<body>";
    $html .= "<table cellpadding='0' cellspacing='0' border='0' style='width:750px; font-size:11px; font-family:Tahoma;'>REPORTE DE PRODUCTOS  - Almacen : $almacen";
    $html .= "<tr>";
    //$html .= "<td style='width:400px;height:100px;border-bottom:1px solid #000000;'><img style='height:100px;' src='logo.jpg'>";
    //    $html .= "</td>";
    //    $html .= "<td style='width:150px;height:100px;border-bottom:1px solid #000000;text-align:center;font-size:20px;font-family:Tahoma;'>";
    //    $html .= "REPORTE<br>DE<br> PRODUCTOS";
    //    $html .= "</td>";
    //    $html .= "<td style='width:200px;height:100px;border-bottom:1px solid #000000;text-align:right;font-size:11px;font-family:Tahoma;'>";
    //    $html .= "<table cellpadding='0' cellspacing='0' border='0' style='width:200px;'>";
    //    //$html .= "<tr><td align='right'><img src='".$_SESSION["ruta"]."php/impl/codigo.jpg'><td><tr>";
    //    $html .= "<tr><td align='right' style='font-size:11px; font-family:Tahoma; text-align:center;font-weight:bold;'>".$ven[0]['idcompra']."<td><tr>";
    //    $html .= "</table>";
    //    $html .= "</td>";
    $html .= "</tr>";
    $html .= "<tr>";
    $html .= "<td colspan='3'>";
    $html .= "<table cellpadding='0' cellspacing='0' border='0' style='width:750px; font-size:11px; font-family:Tahoma;'>";
    $html .= "<tr><td style='width:100px;font-size:11px;font-weight:bold;'></td><td style='width:500px;' ></td></tr>";
    $html .= "<tr><td style='font-size:11px;font-weight:bold;'>Almacen:</td><td>".$almacen."</td></tr>";
    $html .= "<tr><td style='font-size:11px;font-weight:bold;'>Tipo Cambio:</td><td>".$cambioNuevo."</td></tr>";
    $html .= "<tr><td style='font-size:11px;font-weight:bold;'>Fecha</td><td>".date("Y-m-d")."</td></tr><br>";
    $html .= "</table>";
    $html .= "</td>";
    $html .= "</tr>";
    //        $html .= "<tr>";
    $sql = "SELECT p.codigo AS 'Codigo Producto',  p.nombre AS Nombre, p.detalle AS Descripcion ,
  p.stockminimo AS 'Stock Minimo', k.precio1bs AS 'Precio en (BS)', k.precio1sus AS 'Precio en (Sus)', c.nombre AS Categoria,
  k.precio2bs AS 'Precio Minimo', k.fechamodificacion AS 'Fecha Modificacion', pv.nombre AS Proveedor, p.unidad AS Unidad
FROM productos p, kardexalmacen k, categorias c, proveedores pv
WHERE p.idproveedor = pv.idproveedor AND p.idcategoria = c.idcategoria AND p.idproducto =k.idproducto  AND p.idproducto = '$idproducto' and k.idalmacen = '$idalmacen'";
    //    MostrarConsulta($sql);
    $producto = dibujarTuplaOfSQLNormal($sql, "Informacion del producto");
    $html2 .= $producto['resultado'];
    $html .="</tr>";
    $html .="<tr>";
    $html .="<td align='center'>$html2</td>";

    //echo "no esta vacio";
    $sqlKar = "SELECT
  k.saldocantidad AS 'Cantidad Disponible en Almacen',
  k.costounitario AS Costo,
  k.fechamodificacion AS Fecha,
  k.saldobs AS 'Inventario en Bs',
  k.precio2bs AS 'Precio por Mayor',
  k.precio1bs AS 'Precio por Unidad'
FROM
  kardexalmacen k
WHERE
  k.idalmacen = '$idalmacen' AND
  k.idproducto = '$idproducto'
";


    //    $sqlKar = "SELECT k.saldocantidad AS Cantidad, k.costounitario AS Costo, k.fechamodificacion AS Fecha
    //FROM kardexalmacen k WHERE k.idproducto = '$idproducto' and k.idalmacen = '$idalmacen' ";
    $kardex = dibujarTuplaOfSQLNormal($sqlKar, "Kardex");
    $html1 .= $kardex['resultado'];
    $html .="<td align='center'>$html1</td>";
    $html .= "</tr>";


    $html .= "<tr><td colspan='4' align='center' style='border-top:solid 1px #000000;'><div id='pie'>
</div></td></tr>";
    $html .= "</table>";
    $html .= "</body>";
    $html .= "</html>";
    if($return == true)
    {
        return $html;
    }
    else
    {
        echo $html;
    }

}
function dibujarTablaOfSQL($sql, $tc = 1)
{
    if($link=new BD)
    {
        if($link->conectar())
        {
            if($re = $link->consulta($sql))
            {
                //echo mysql_num_rows($re);
                if($fi = mysql_fetch_array($re))
                {
                    $devS .= "<table cellpadding='0' cellspacing='0' border='0' style='width:750px;border:1px solid #000000;font-size:11px;'>";
                    $devS .= "<tr><td style='border-bottom:1px solid #000000;font-weight:bold;font-size:11px;text-align:center;background-color:silver;'>Nro</td>";

                    //dibjamos las cabeceras



                    for($zz = 0; $zz < mysql_num_fields($re); $zz++)
                    {
                        $devS .= "<td style='border-bottom:1px solid #000000;border-left:1px solid #000000;font-weight:bold;font-size:11px;text-align:center;background-color:silver;'>".mysql_field_name($re, $zz)."</td>";
                    }
                    $devS .= "</tr>";
                    $ii = 0;
                    //$totalCount = mysql_num_rows($re);
                    $z = 1;
                    do{
                        $devS .= "<tr><td style='text-align:center'>".$z."</td>";
                        for($i = 0; $i< mysql_num_fields($re); $i++)
                        {
                            if(mysql_field_type($re, $i) == "real")
                            {
                                $dato = $fi[$i];
                                if(mysql_field_name($re, $i) == "precio" || mysql_field_name($re, $i) == "importe")
                                {
                                    //                                    $dato = $fi[$i]/$tc;
                                    $dato = $fi[$i];
                                }
                                $devS .= "<td style='text-align:right;border-left: 1px solid #000000;'>&nbsp;".redondear($dato)."&nbsp;</td>";
                            }
                            else
                            {
                                $devS .= "<td style='border-left: 1px solid #000000;'>&nbsp;".$fi[$i]."&nbsp;</td>";
                            }
                        }
                        $devS .= "</tr>";
                        $ii++;
                        $z ++;
                    }while($fi = mysql_fetch_array($re));

                    $devS .= "</table>";

                    $dev['mensaje'] = "Existen resultados";
                    $dev['error']   = "true";
                    $dev['resultado'] = $devS;

                }
                else
                {
                    $dev['mensaje'] = "No se encontro datos en la consulta2".mysql_error();
                    $dev['error']   = "false";
                    $dev['resultado'] = "";
                }
            }
            else
            {
                $dev['mensaje'] = "Error en la consulta";
                $dev['error']   = "false";
                $dev['resultado'] = "";
            }
        }
        else
        {
            $dev['mensaje'] = "No se pudo conectar a la BD";
            $dev['error']   = "false";
            $dev['resultado'] = "";
        }
    }
    else
    {
        $dev['mensaje'] = "No se pudo crear la conexion a la BD";
        $dev['error']   = "false";
        $dev['resultado'] = "";
    }
    return $dev;
}
function dibujarTuplaOfSQLNormal($sql, $titulo = "ninguno")
{
    if($link=new BD)
    {
        if($link->conectar())
        {
            if($re = $link->consulta($sql))
            {
                //echo mysql_num_rows($re);
                if($fi = mysql_fetch_array($re))
                {
                    $devS .= "<table cellpadding='0' cellspacing='0' border='0' style='width:100%;border:1px solid #000000;font-size:11px;'>";
                    if($titulo != "ninguno")
                    {
                        $devS .= "<tr><td colspan='2' style='border-bottom:1px solid #000000;font-weight:bold;font-size:11px;text-align:center;background-color:silver;'><h1>$titulo</h1></td></tr>";
                    }
                    $ii = 0;
                    //$totalCount = mysql_num_rows($re);
                    $z = 1;
                    do{
                        for($i = 0; $i< mysql_num_fields($re); $i++)
                        {
                            $dato = $fi[$i];
                            if(($dato != null)||($dato != "")){


                                if(mysql_field_type($re, $i) == "real")
                                {
                                    $devS .= "<tr><td style='font-weight:bold;'><h3>".mysql_field_name($re, $i)."</h3></td><td style='text-align:right;border-left: 1px solid #000000;'><h3>".redondear($dato)."</h3></td></tr>";
                                }
                                else
                                {
                                    $devS .= "<tr><td style='font-weight:bold;'><h3>".mysql_field_name($re, $i)."</h3></td><td style='text-align:left;border-left: 1px solid #000000;'><h3>".$dato."</h3></td></tr>";
                                }
                            }
                        }
                        $devS .= "</tr>";
                        $ii++;
                        $z ++;
                    }while($fi = mysql_fetch_array($re));

                    $devS .= "</table>";

                    $dev['mensaje'] = "Existen resultados";
                    $dev['error']   = "true";
                    $dev['resultado'] = $devS;

                }
                else
                {
                    $dev['mensaje'] = "No se encontro datos en la consulta2".mysql_error();
                    $dev['error']   = "false";
                    $dev['resultado'] = "";
                }
            }
            else
            {
                $dev['mensaje'] = "Error en la consulta";
                $dev['error']   = "false";
                $dev['resultado'] = "";
            }
        }
        else
        {
            $dev['mensaje'] = "No se pudo conectar a la BD";
            $dev['error']   = "false";
            $dev['resultado'] = "";
        }
    }
    else
    {
        $dev['mensaje'] = "No se pudo crear la conexion a la BD";
        $dev['error']   = "false";
        $dev['resultado'] = "";
    }
    return $dev;
}
function dibujarTablaOfSQLNormal($sql, $titulo = "ninguno")
{
    if($link=new BD)
    {
        if($link->conectar())
        {
            if($re = $link->consulta($sql))
            {
                //echo mysql_num_rows($re);
                if($fi = mysql_fetch_array($re))
                {
                    $devS .= "<table cellpadding='0' cellspacing='0' border='0' style='width:100%;border:1px solid #000000;font-size:11px;'>";
                    if($titulo != "ninguno")
                    {
                        $devS .= "<tr><td colspan='".(mysql_num_fields($re)+1)."' style='border-bottom:1px solid #000000;font-weight:bold;font-size:11px;text-align:center;background-color:silver;'>$titulo</td></tr>";
                    }
                    $devS .= "<tr><td style='border-bottom:1px solid #000000;font-weight:bold;font-size:11px;text-align:center;background-color:silver;'>Nro</td>";

                    //dibjamos las cabeceras



                    for($zz = 0; $zz < mysql_num_fields($re); $zz++)
                    {
                        $devS .= "<td style='border-bottom:1px solid #000000;border-left:1px solid #000000;font-weight:bold;font-size:11px;text-align:center;background-color:silver;'>".mysql_field_name($re, $zz)."</td>";
                    }
                    $devS .= "</tr>";
                    $ii = 0;
                    //$totalCount = mysql_num_rows($re);
                    $z = 1;
                    do{
                        $devS .= "<tr><td style='text-align:center'>".$z."</td>";
                        for($i = 0; $i< mysql_num_fields($re); $i++)
                        {
                            if(mysql_field_type($re, $i) == "real")
                            {
                                $dato = $fi[$i];
                                if(mysql_field_name($re, $i) == "precio" || mysql_field_name($re, $i) == "importe")
                                {
                                    //                                    $dato = $fi[$i]/$tc;
                                    $dato = $fi[$i];
                                }
                                $devS .= "<td style='text-align:right;border-left: 1px solid #000000;'>&nbsp;".redondear($dato)."&nbsp;</td>";
                            }
                            else
                            {
                                $devS .= "<td style='border-left: 1px solid #000000;'>&nbsp;".$fi[$i]."&nbsp;</td>";
                            }
                        }
                        $devS .= "</tr>";
                        $ii++;
                        $z ++;
                    }while($fi = mysql_fetch_array($re));

                    $devS .= "</table>";

                    $dev['mensaje'] = "Existen resultados";
                    $dev['error']   = "true";
                    $dev['resultado'] = $devS;

                }
                else
                {
                    $dev['mensaje'] = "No se encontro datos en la consulta2".mysql_error();
                    $dev['error']   = "false";
                    $dev['resultado'] = "";
                }
            }
            else
            {
                $dev['mensaje'] = "Error en la consulta";
                $dev['error']   = "false";
                $dev['resultado'] = "";
            }
        }
        else
        {
            $dev['mensaje'] = "No se pudo conectar a la BD";
            $dev['error']   = "false";
            $dev['resultado'] = "";
        }
    }
    else
    {
        $dev['mensaje'] = "No se pudo crear la conexion a la BD";
        $dev['error']   = "false";
        $dev['resultado'] = "";
    }
    return $dev;
}

function reporteProduccionHTML($id, $return)
{

    //echo "codigob".$codigobarra;
    //echo "codigo".$codigoproducto;
    if(($id =="")){
        echo "No Seleccion ninuna produccion";
        exit();
    }




    $html .= "<!DOCTYPE HTML PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'>";
    $html .= "<html>";
    $html .= "<head>";
    $html .= "<title></title>";
    $html .= "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>";
    $html .= "<link href='".$_SESSION["ruta"]."css/print.css' rel='stylesheet' type='text/css' media='all'>";
    //$html .= "<link href='../print.css' rel='stylesheet' type='text/css' media='all'>";
    $html .= "</head>";
    $html .= "<body>";
    $html .= "<table cellpadding='0' cellspacing='0' border='0' style='width:750px; font-size:11px; font-family:Tahoma;'>";
    $html .= "<tr>";
    //$html .= "<td style='width:400px;height:100px;border-bottom:1px solid #000000;'><img style='height:100px;' src='logo.jpg'>";
    //    $html .= "</td>";
    $html .= "<td style='width:150px;height:100px;border-bottom:1px solid #000000;text-align:center;font-size:20px;font-family:Tahoma;'>";
    $html .= "PRODUCCION  <br>DE<br> PROCESOS";
    $html .= "</td>";
    $html .= "<td style='width:200px;height:100px;border-bottom:1px solid #000000;text-align:right;font-size:11px;font-family:Tahoma;'>";
    $html .= "<table cellpadding='0' cellspacing='0' border='0' style='width:200px;'>";
    //$html .= "<tr><td align='right'><img src='".$_SESSION["ruta"]."php/impl/codigo.jpg'><td><tr>";
    //    $html .= "<tr><td align='right' style='font-size:11px; font-family:Tahoma; text-align:center;font-weight:bold;'>".$ven[0]['idcompra']."<td><tr>";
    $html .= "</table>";
    $html .= "</td>";
    $html .= "</tr>";
    $html .= "<tr>";
    $html .= "<td colspan='3'>";
    $html .= "<table cellpadding='0' cellspacing='0' border='0' style='width:750px; font-size:11px; font-family:Tahoma;'>";
    $html .= "<tr><td style='width:100px;font-size:11px;font-weight:bold;'></td><td style='width:500px;' ></td></tr>";
//    $html .= "<tr><td style='font-size:11px;font-weight:bold;'>Producto:</td><td>".$codigoproducto."</td></tr>";
    //    $html .= "<tr><td style='font-size:11px;font-weight:bold;'>Tipo Cambio:</td><td>".$cambioNuevo."</td></tr>";
    $html .= "<tr><td style='font-size:11px;font-weight:bold;'>Fecha</td><td>".date("Y-m-d")."</td><td></td></tr><br>";
    $html .= "</table>";
    $html .= "</td>";
    $html .= "</tr>";

    //        $html .= "<tr>";
    $html .= "<td colspan='1'>";
    if($id != null)
    {
        //            echo "no esta vacio";
        $sql = "
SELECT
  prp.numeroorden AS '# Orden Produccion',
  prp.numeroproduccion AS '# Produccion de Proceso',
  prp.fecharecepcion AS 'Fecha de Recepcion',
  prp.fechaentrega AS 'Fecha de Entrega Produccion',
  prp.observacion AS Observaciones,
  emp.codigo AS 'Codigo Operario',
  CONCAT(emp.nombre, ' ', emp.apellido1) AS 'Nombre Operario',
  prc.nombre AS Proceso,
  `ord`.fechaentrega 'Fecha Entrega de Orden'
FROM
  produccionprocesos prp,
  procesos prc,
  empleados emp,
  `ordenproduccion` `ord`
WHERE
  prp.idproduccionproceso = '$id' AND
  prp.idempleado = emp.idempleado AND
  prp.idproceso = prc.idproceso AND
  prp.numeroorden = `ord`.numeroorden
";

    }


    //    MostrarConsulta($sql);
    $producto = dibujarTuplaOfSQLNormal($sql, "Informacion de Produccion de Proceso");
    $html .= $producto['resultado'];
    $html .= "</td>";

    $sqlKar1 = " SELECT
  dpr.cantidad AS Cant,
  dpr.unidad AS Unidad,
  dpr.detalle AS Detalle,
'................' AS 'fecha / Cantidad',
  '................' AS 'fecha / Cantidad',
  '................' AS 'fecha / Cantidad',
  '................' AS 'fecha / Cantidad',
  '................' AS 'fecha / Cantidad'
FROM
  detalleproduccionproceso dpr
WHERE
  dpr.idproduccionproceso = '$id' AND
  dpr.tipo = 'entrega'";
      $sqlKar2 = " SELECT
  dpr.cantidad AS Cant,
  dpr.unidad AS Unidad,
  dpr.detalle AS Detalle,
'................' AS 'fecha / Cantidad',
  '................' AS 'fecha / Cantidad',
  '................' AS 'fecha / Cantidad',
  '................' AS 'fecha / Cantidad',
  '................' AS 'fecha / Cantidad'
FROM
  detalleproduccionproceso dpr
WHERE
  dpr.idproduccionproceso = '$id' AND
  dpr.tipo = 'recepcion'";
//    echo $sqlKar1;
    $kardex = dibujarTablaOfSQLNormal($sqlKar1, "Entrega de Material O Productos para Produccion de Procesos");
    $html1 .= $kardex['resultado'];
    $kardex1 = dibujarTablaOfSQLNormal($sqlKar2, "Recepcion de Productos . Produccion de Procesos");
    $html2 .= $kardex1['resultado'];
    //    $html .="
    //    $html1
    //";
    //
    //
    //    $html .="
    //    <tr><td>HOLA MUNDO</td></tr>
    //";
    $html .= "<tr><td colspan='4' align='center'><div id='pie'>
    <p>$html1
    </p>

<p>$html2
    </p>
</div></td></tr>";
    $html .= "<tr><td colspan='4' align='center' style='border-top:solid 1px #000000;'><div id='pie'>
    <p><br />
         Cochabamba - Bolivia<br />

    </p>
</div></td></tr>";

    $html .= "</table>";
    $html .= "</body>";
    $html .= "</html>";
    if($return == true)
    {
        return $html;
    }
    else
    {
        echo $html;
    }
}


?>