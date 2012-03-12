<?php
function ListarCompras($start, $limit, $sort, $dir, $callback, $_dc, $where = '', $return = false){
    if($start == null)
    {
        $start = 0;
    }
    if($limit == null)
    {
        $limit = 100;
    }
    if($sort != null)
    {
        $order = "ORDER BY $sort ";
        if($dir != null)
        {
            $order .= " $dir ";
        }
    }
    //    $dev['totalCount'] = 0;
    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";
    if(($where == null)||($where =="")){
        $sql = "SELECT
  cmp.idcompra,
  cmp.numerodocumento,
  cmp.montototal,
  cmp.observacion,
  cmp.fecha,
  prv.nombre as proveedor
FROM
  compras cmp,
  proveedores prv
WHERE
  cmp.idproveedor = prv.idproveedor $order
";
    }
    else{
        $sql ="
SELECT
  cmp.idcompra,
  cmp.numerodocumento,
  cmp.montototal,
  cmp.observacion,
  cmp.fecha,
  prv.nombre as proveedor
FROM
  compras cmp,
  proveedores prv
WHERE
  cmp.idproveedor = prv.idproveedor AND $where $order
";
    }

    //                    echo $sql;
    if($link=new BD)
    {
        if($link->conectar())
        {
            if($re = $link->consulta($sql))
            {

                if($fi = mysql_fetch_array($re))
                {
                    $dev['totalCount'] = mysql_num_rows($re);
                    $ii = 0;
                    do{

                        for($i = 0; $i< mysql_num_fields($re); $i++)
                        {
                            $value{$ii}{mysql_field_name($re, $i)}= $fi[$i];

                        }

                        $ii++;
                    }while($fi = mysql_fetch_array($re));
                    $dev['mensaje'] = "Existen resultados";
                    $dev['error']   = "true";
                    $dev['resultado'] = $value;

                }
                else
                {
                    $dev['mensaje'] = "No se encontro datos en la consulta";
                    $dev['error']   = "false";
                    $dev['resultado'] = "";
                }
            }
            else
            {
                $dev['mensaje'] = "No existe un usuario con estos datos";
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
    if($return == true)
    {
        return $dev;
    }
    else
    {

        if($callback == null || $callback == "")
        {
            $json = new Services_JSON();
            $output = $json->encode($dev);
            print($output);
        }
        else
        {
            $json = new Services_JSON();
            $output = $json->encode($dev);
            $output = substr($output, 1);
            $output = "$callback({".$output.");";
            print($output);
        }


    }



}
function CargarNuevaCompra($callback, $_dc, $where='',$return = false){
    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";
    $proveedorA = ListarProveedoresIdNombre("", "", "nombre", "DESC", "", "", "", true);
    if($proveedorA["error"]=="false"){
        $dev['mensaje'] = "No se pudo encontro proveedores";
        $dev['error']   = "false";

    }
    $productoA = ListarMateriaPrimaIdNombreUnidad("", "", "nombre", "DESC", "", "", "", true);
    if($productoA["error"]=="false"){
        $dev['mensaje'] = "No se pudo encontro proveedores";
        $dev['error']   = "false";

    }
    $encargadoA = ListarEmpleadosIdNombre("", "", "nombre", "DESC", "", "", "", true);
    if($encargadoA["error"]=="false"){
        $dev['mensaje'] = "No se pudo encontro proveedores";
        $dev['error']   = "false";
    }

    //    $dev['resultado'] = $productoA['error'];
    if(($productoA["error"]==true)&&($proveedorA["error"]==true)&&($encargadoA["error"]==true)){
        $dev['mensaje'] = "Todo Ok";
        $dev['error'] = "true";
        $dev['productoM'] = $productoA['resultado'];
        $dev['proveedorM']= $proveedorA['resultado'];
        $dev['encargadoM']= $encargadoA['resultado'];

    }




    if($return == true)
    {
        return $dev;
    }
    else
    {

        if($callback == null || $callback == "")
        {
            $json = new Services_JSON();
            $output = $json->encode($dev);
            print($output);
        }
        else
        {
            $json = new Services_JSON();
            $output = $json->encode($dev);
            $output = substr($output, 1);
            $output = "$callback({".$output.");";
            print($output);
        }


    }

}
function getSqlNewCompras($idcompra, $numerodocumento, $idproveedor, $idempleado, $fecha, $hora, $tipodocumento, $montototal, $montoapagar, $descuento, $descuentobs, $observacion, $idusuario, $numero, $ordenproduccion, $return){
    $setC[0]['campo'] = 'idcompra';
    $setC[0]['dato'] = $idcompra;
    $setC[1]['campo'] = 'numerodocumento';
    $setC[1]['dato'] = $numerodocumento;
    $setC[2]['campo'] = 'idproveedor';
    $setC[2]['dato'] = $idproveedor;
    $setC[3]['campo'] = 'idempleado';
    $setC[3]['dato'] = $idempleado;
    $setC[4]['campo'] = 'fecha';
    $setC[4]['dato'] = $fecha;
    $setC[5]['campo'] = 'hora';
    $setC[5]['dato'] = $hora;
    $setC[6]['campo'] = 'tipodocumento';
    $setC[6]['dato'] = $tipodocumento;
    $setC[7]['campo'] = 'montototal';
    $setC[7]['dato'] = $montototal;
    $setC[8]['campo'] = 'montoapagar';
    $setC[8]['dato'] = $montoapagar;
    $setC[9]['campo'] = 'descuento';
    $setC[9]['dato'] = $descuento;
    $setC[10]['campo'] = 'descuentobs';
    $setC[10]['dato'] = $descuentobs;
    $setC[11]['campo'] = 'observacion';
    $setC[11]['dato'] = $observacion;
    $setC[12]['campo'] = 'idusuario';
    $setC[12]['dato'] = $idusuario;
    $setC[13]['campo'] = 'numero';
    $setC[13]['dato'] = $numero;
    $setC[14]['campo'] = 'ordenproduccion';
    $setC[14]['dato'] = $ordenproduccion;
    $sql2 = generarInsertValues($setC);
    return "INSERT INTO compras ".$sql2;
}
function getSqlUpdateCompras($idcompra,$numerodocumento,$idproveedor,$idempleado,$fecha,$hora,$tipodocumento,$montototal,$montoapagar,$descuento,$descuentobs,$observacion,$idusuario,$numero,$ordenproduccion, $return){
    $setC[0]['campo'] = 'numerodocumento';
    $setC[0]['dato'] = $numerodocumento;
    $setC[1]['campo'] = 'fecha';
    $setC[1]['dato'] = $fecha;
    $setC[2]['campo'] = 'hora';
    $setC[2]['dato'] = $hora;
    $setC[3]['campo'] = 'tipodocumento';
    $setC[3]['dato'] = $tipodocumento;
    $setC[4]['campo'] = 'montototal';
    $setC[4]['dato'] = $montototal;
    $setC[5]['campo'] = 'montoapagar';
    $setC[5]['dato'] = $montoapagar;
    $setC[6]['campo'] = 'descuento';
    $setC[6]['dato'] = $descuento;
    $setC[7]['campo'] = 'descuentobs';
    $setC[7]['dato'] = $descuentobs;
    $setC[8]['campo'] = 'observacion';
    $setC[8]['dato'] = $observacion;
    $setC[9]['campo'] = 'numero';
    $setC[9]['dato'] = $numero;
    $setC[10]['campo'] = 'ordenproduccion';
    $setC[10]['dato'] = $ordenproduccion;

    $set = generarSetsUpdate($setC);
    $wher[0]['campo'] = 'idcompra';
    $wher[0]['dato'] = $idcompra;
    $wher[1]['campo'] = 'idproveedor';
    $wher[1]['dato'] = $idproveedor;
    $wher[2]['campo'] = 'idempleado';
    $wher[2]['dato'] = $idempleado;
    $wher[3]['campo'] = 'idusuario';
    $wher[3]['dato'] = $idusuario;

    $where = generarWhereUpdate($wher);
    return "UPDATE compras SET ".$set." WHERE ".$where;
}

function GuardarNuevaCompra($callback, $_dc, $resultado, $return = false){


    $dev['mensaje'] = "";
    $dev['error'] = "false";
    $dev['resultado'] = "";
    $compra = $resultado->compra;


    $numeroA = findUltimoID("compras", "numero", true);
    $numero = $numeroA['resultado']+1;
    $idcompra = "com-".$numero;
    //validar nuemrodocumento , idproveedor , idempleado , tipodocuemnto
    $numerodocumento = $compra->numerodocumento;
    $idproveedor = $compra->idproveedor;
    $idempleado = $compra->idempleado;
    $idusuario = $_SESSION['idusuario'];
    $tipodocumento = $compra->tipodocumento;
    $montototal = $compra->montototal;
    $montoapagar = $compra->montoapagar;
    $descuento = $compra->descuentoporcentaje;
    $descuentobs = $compra->descuento;
    $observacion = $compra->observacion;
    $fecha = $compra->fecha;
    $ordenproduccion = $compra->ordenproduccion;
    $hora = date("H:i:s");
    if($fecha == ""){

        $fecha = date("Y-m-d");
    }
    $encargadoA = validarText($idempleado, true);
    if($encargadoA["error"]==false){
        $dev['mensaje'] = "Error en el campo encargado: ".$encargadoA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $numerodocumentoA = validarText($numerodocumento, true);
    if($numerodocumentoA["error"]==false){
        $dev['mensaje'] = "Error en el campo numero de documento: ".$numerodocumentoA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $sql[] = getSqlNewCompras($idcompra, $numerodocumento, $idproveedor, $idempleado, $fecha, $hora, $tipodocumento, $montototal, $montoapagar, $descuento, $descuentobs, $observacion, $idusuario, $numero, $ordenproduccion, $return);
    $detalles = $resultado->detalles;
    $numeroD = findUltimoID("detallecompra", "numero", true);
    $numeroDC = $numeroD['resultado']+1;

    $numeroM = findUltimoID("movimientokardexmateriaprima", "numero", true);
    $numeromov = $numeroM['resultado']+1;
    for($j=0;$j<count($detalles);$j++){
       
        $detalle = $detalles[$j];
        $nombre = $detalle->detalle;
        $cantidad = $detalle->cantidad;
        $preciounitario = $detalle->preciounitario;
        $preciototal = $detalle->preciototal;
        $idmateriaprima = $detalle->id;
        $unidad = $detalle->unidad;
        $op = $detalle->op;
        //if para verificar si la op existe
        $iddetallecompra = "dec-".$numeroDC;
        $idmovimientokardexmateriaprima = "mmp-".$numeromov;

        $sql[] = getSqlNewDetallecompra($idcompra, $iddetallecompra, $nombre, $idmateriaprima, $unidad, $cantidad, $preciounitario, $preciototal, $numeroDC,$op, $return);
        $numeroDC++;
        $sql[] =getSqlNewMovimientokardexmateriaprima($idmovimientokardexmateriaprima, $idmateriaprima, $cantidad, 0, 0, $preciounitario, $cantidad * $preciounitario, 0, 0, $fecha, $hora, "Compra de materia prima ".$numerodocumento, $numeromov, $cantidad, $idcompra,$id,$return);
        $numeromov++;
        $sql[]="UPDATE kardexmateriaprima SET costounitario = '$preciounitario' WHERE idmateriaprima = '$idmateriaprima'";
        $movimiento[$j]["idmateriaprima"] = $idmateriaprima;
        $movimiento[$j]["fecha"] = $fecha;
        $movimiento[$j]["hora"] = $hora;
      

    }



    //          MostrarConsulta($sql);
    if(ejecutarConsultaSQLBeginCommit($sql)) {
        for($k=0;$k<count($movimiento);$k++){
            //            echo $movimiento[$j]['idproducto'];
            actualizarSaldoMovimientoKardexMateriaPrima($movimiento[$k]['idmateriaprima'],$movimiento[$k]['fecha'],$movimiento[$k]['hora'], false);
        }
        $dev['mensaje'] = "Se actualizo correctamente";
        $dev['error'] = "true";
        $dev['resultado'] = "";
    }
    else {
        $dev['mensaje'] = "Ocurrio un error al actulizar";
        $dev['error'] = "false";
        $dev['resultado'] = "";
    }

    if($return == true)
    {
        return $dev;
    }
    else
    {
        if($callback == null || $callback == "")
        {
            $json = new Services_JSON();
            $output = $json->encode($dev);
            print($output);
        }
        else
        {
            $json = new Services_JSON();
            $output = $json->encode($dev);
            $output = substr($output, 1);
            $output = "$callback({".$output.");";
            print($output);
        }

    }
}
function GuardarEditarCompras($callback, $_dc, $resultado, $return = false){
    $dev['mensaje'] = "";
    $dev['error'] = "false";
    $dev['resultado'] = "";

    $compra = $resultado->compra;
    $hora = date("H:i:s");
    $fecha = date("Y-m-d");
    //validar nuemrodocumento , idproveedor , idempleado , tipodocuemnto
    $numerodocumento = $compra->numerodocumento;
    $idcompra = $compra->idcompra;
    $idproveedor = $compra->idproveedor;
    $idempleado = $compra->idempleado;
    $idusuario = $_SESSION['idusuario'];
    $tipodocumento = $compra->tipodocumento;
    $montototal = $compra->montototal;
    $montoapagar = $compra->montoapagar;
    $descuento = $compra->descuentoporcentaje;
    $descuentobs = $compra->descuento;
    $observacion = $compra->observacion;
    $ordenproduccion = $compra->ordenproduccion;
    //    echo $idcompra;
    $encargadoA = validarText($idempleado, true);
    if($encargadoA["error"]== false){
        $dev['mensaje'] = "Error en el campo encargado: ".$encargadoA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $numerodocumentoA = validarText($numerodocumento, true);
    if($numerodocumentoA["error"]== false){
        $dev['mensaje'] = "Error en el campo numero de documensto: ".$numerodocumentoA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $sql[]= getSqlUpdateCompras($idcompra,$numerodocumento,$idproveedor,$idempleado,$fecha,$hora,$tipodocumento,$montototal,$montoapagar,$descuento,$descuentobs,$observacion,$idusuario,$numero,$ordenproduccion, $return);
    $detalles = $resultado->detalles;
    $numeroD = findUltimoID("detallecompra", "numero", true);
    $numeroDC = $numeroD['resultado']+1;
    $numeroM =  findUltimoID("movimientokardexmateriaprima","numero", true);
    $numeromov = $numeroM['resultado']+1;
    $sql[] = getSqlDeleteDetallecompraIdCompra($idcompra);
    $sql[] = getSqlDeleteMovimientokardexmateriaprimaPorOperacion($idcompra);

    $verificarmateriaPrima = VerificarCompra($idcompra);
    //    echo $verificarmateriaPrima["mensaje"];
    if($verificarmateriaPrima["error"]== false){
        $dev['mensaje'] = "Error No puedo Editar o Eliminar : ".$verificarmateriaPrima['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    if(count($detalles)>0){



        for($j=0;$j<count($detalles);$j++){

            $hora = date("H:i:s");
            $fecha = date("Y-m-d");
            $detalle = $detalles[$j];
            $nombre = $detalle->detalle;
            $cantidad = $detalle->cantidad;
            $preciounitario = $detalle->preciounitario;
            $preciototal = $detalle->preciototal;
            $idmateriaprima = $detalle->id;

            $unidad = $detalle->unidad;
            $iddetallecompra = "dec-".$numeroDC;
            $idmovimientokardexmateriaprima = "mmp-".$numeromov;
            $sql[] = getSqlNewDetallecompra($idcompra, $iddetallecompra, $nombre, $idmateriaprima, $unidad, $cantidad, $preciounitario, $preciototal, $numeroDC, $return);
            $numeroDC++;
            $sql[] =getSqlNewMovimientokardexmateriaprima($idmovimientokardexmateriaprima, $idmateriaprima, $cantidad, 0, 0, $preciounitario, $cantidad * $preciounitario, 0, 0, $fecha, $hora, "Compra de materia prima ".$numerodocumento, $numeromov, $cantidad, $idcompra,$id,$return);
            $numeromov++;
            $movimiento[$j]["idmateriaprima"] = $idmateriaprima;
            $movimiento[$j]["fecha"] = $fecha;
            $movimiento[$j]["hora"] = $hora;
            sleep(1);



        }
    }
    else{
        $dev['mensaje'] = "Error No puedo Editar por que no tiene ningun dato para editar es preferible eliminar";
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }


    if(ejecutarConsultaSQLBeginCommit($sql))
    {
        for($k=0;$k<count($movimiento);$k++){
            //            echo $movimiento[$j]['idproducto'];
            actualizarSaldoMovimientoKardexMateriaPrima($movimiento[$k]['idmateriaprima'],$movimiento[$k]['fecha'],$movimiento[$k]['hora'], false);//ver si es actualizar
        }
        $dev['mensaje'] = "Se actualizo correctamente";
        $dev['error'] = "true";
        $dev['resultado'] = "";
    }
    else
    {
        $dev['mensaje'] = "Ocurrio un error al actulizar";
        $dev['error'] = "false";
        $dev['resultado'] = "";
    }

    if($return == true)
    {
        return $dev;
    }
    else
    {
        if($callback == null || $callback == "")
        {
            $json = new Services_JSON();
            $output = $json->encode($dev);
            print($output);
        }
        else
        {
            $json = new Services_JSON();
            $output = $json->encode($dev);
            $output = substr($output, 1);
            $output = "$callback({".$output.");";
            print($output);
        }

    }
}
function getSqlDeleteCompras($idcompras){
    return "DELETE FROM compras WHERE idcompra ='$idcompras';";
}
function BuscarCompraPorId($callback, $_dc, $idcompra, $return = false){

    //    $dev['totalCount'] = 0;
    $dev['mensaje'] = "";
    $dev['error']   = "false";
    $dev['resultado'] = "";

    $sqlcompra = "
SELECT
  com.idcompra,
  com.numerodocumento,
  com.idproveedor,
  com.idempleado,
  com.fecha,
  com.tipodocumento,
  com.montototal,
  com.montoapagar,
  com.descuento AS descuentoporcentaje,
  com.descuentobs AS descuento,
  com.observacion
FROM
  compras com
WHERE
  com.idcompra = '$idcompra'
";
    $compraA = getTablaToArrayOfSQL($sqlcompra);
    $compra = $compraA['resultado'];
    $value['compra'] = $compra;

    $sqldetalle = "
SELECT
  dtc.idmateriaprima AS id,
  dtc.unidad,
  dtc.cantidad,
  dtc.preciounitario,
  dtc.preciototal,
  dtc.detalle
FROM
  detallecompra dtc
WHERE
  dtc.idcompra = '$idcompra'
";
    $detalleA = getTablaToArrayOfSQL($sqldetalle);
    $detalle = $detalleA['resultado'];
    $value['detalles'] = $detalle;
    $proveedorA = ListarProveedoresIdNombre("", "", "nombre", "DESC", "", "", "", true);
    if($proveedorA["error"]=="false"){
        $dev['mensaje'] = "No se pudo encontro proveedores";
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;

    }
    $encargadoA = ListarEmpleadosIdNombre("", "", "nombre", "DESC", "", "", "", true);
    if($encargadoA["error"]=="false"){
        $dev['mensaje'] = "No se pudo encontro Encargado";
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $productoA = ListarMateriaPrimaIdNombreUnidad("", "", "nombre", "DESC", "", "", "", true);
    if($productoA["error"]=="false"){
        $dev['mensaje'] = "No se pudo encontro proveedores";
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;

    }
    $value['productoM'] = $productoA['resultado'];
    $value['proveedorM']= $proveedorA['resultado'];
    $value['encargadoM']= $encargadoA['resultado'];



    //                    echo $sql;
    $dev['mensaje'] = "Existen resultados";
    $dev['error']   = "true";
    $dev['resultado'] = $value;
    if($return == true)
    {
        return $dev;
    }
    else
    {

        if($callback == null || $callback == "")
        {
            $json = new Services_JSON();
            $output = $json->encode($dev);
            print($output);
        }
        else
        {
            $json = new Services_JSON();
            $output = $json->encode($dev);
            $output = substr($output, 1);
            $output = "$callback({".$output.");";
            print($output);
        }


    }



}

function EliminarCompras($callback, $_dc, $idcompra, $return = false){
    $dev['mensaje'] = "";
    $dev['error'] = "false";
    $dev['resultado'] = "";
    //    echo "hola";

    $verificarC = VerificarCompra($idcompra);//ver si esta funcion esta bien
    if($verificarC["error"]==false){
        $dev['mensaje'] = "Ocurrio un error al verificar exite en otras tablas";

        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $sql[]= getSqlDeleteDetallecompraIdCompra($idcompra);
    $sql[]= getSqlDeleteCompras($idcompra);

    $sql[]= getSqlDeleteMovimientokardexmateriaprimaPorOperacion($idcompra);

    $sql1= "SELECT
  mka.hora,
  mka.fecha,
  mka.idmateriaprima
FROM
  movimientokardexmateriaprima mka
WHERE
  mka.operacion = '$idcompra'"
    ;


    $numSql = getTablaToArrayOfSQL($sql1);
    $numA =$numSql['resultado'];
    ////entra el sql par movimientokardexmateriaprima

    for($j=0;$j<count($numA);$j++){

        $detalle = $numA[$j];
        $movimiento[$j]["idmateriaprima"] = $detalle['idmateriaprima'];
        $movimiento[$j]["fecha"] = $detalle['fecha'];
        $movimiento[$j]["hora"] = $detalle['hora'];
    }
    //    MostrarConsulta($sql);
    //    exit();
    if(ejecutarConsultaSQLBeginCommit($sql))
    {
        //        echo "ss".count($movimiento);


        for($k=0;$k<count($movimiento);$k++){
            //            echo $movimiento[$j]['idproducto'];
            //            echo $movimiento[$k]['idmateriaprima'];
            //            echo $movimiento[$k]['fecha'];
            //            echo $movimiento[$k]['hora'];
            //
            actualizarSaldoMovimientoKardexMateriaPrima($movimiento[$k]['idmateriaprima'],$movimiento[$k]['fecha'],$movimiento[$k]['hora'], false);//ver si es actualizar
        }
        $dev['mensaje'] = "Se actualizo correctamente";
        $dev['error'] = "true";
        $dev['resultado'] = "";
    }
    else
    {
        echo "ss";
        exit();
        $dev['mensaje'] = "Ocurrio un error al actulizar";
        $dev['error'] = "false";
        $dev['resultado'] = "";
    }

    if($return == true)
    {
        return $dev;
    }
    else
    {
        if($callback == null || $callback == "")
        {
            $json = new Services_JSON();
            $output = $json->encode($dev);
            print($output);
        }
        else
        {
            $json = new Services_JSON();
            $output = $json->encode($dev);
            $output = substr($output, 1);
            $output = "$callback({".$output.");";
            print($output);
        }

    }

}
function VerificarCompra($idcompra){
    $sql1 ="
SELECT
 mka.idmovimientokardexmateriaprima
FROM
  `movimientokardexmateriaprima` mka
WHERE
  mka.operacion = '$idcompra'
";
    $sql2 = "
SELECT
 mkm.idmovimientokardexmateriaprima
FROM
  movimientokardexmateriaprima mkm
WHERE
  mkm.operacion = '$idcompra' AND
  mkm.entrada = mkm.saldocantidad
";
    $var1A = NumeroTuplas($sql1);
    $var1 = $var1A["resultado"];
    $var2A =NumeroTuplas($sql2);
    $var2 = $var2A["resultado"];
    if($var1 != $var2){
        $dev['mensaje'] = "ya fue utilizado un producto".$var1."aaa".$var2;
        $dev["error"] = false;
        $dev['resultado'] = "";
    }
    else{
        $dev['mensaje'] = "exito";
        //        $dev['mensaje'] = "ya fue utilizado un producto".$sql1."aaa".$sql2;
        $dev["error"] = true;
        $dev['resultado'] = "";
    }
    return $dev;
}


//sistema flores jaldin
function CargarNuevaCompraFlores($callback, $_dc, $where='',$return = false){
    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";
    $proveedorA = ListarProveedoresIdNombre("", "", "nombre", "DESC", "", "", "", true);
    if($proveedorA["error"]=="false"){
        $dev['mensaje'] = "No se pudo encontro proveedores";
        $dev['error']   = "false";

    }
    $sql = "
SELECT
  mat.idmateriaprima AS id,
  CONCAT(mat.nombre,' - ', mat.caracteristica) AS nombre,
  uni.nombre as unidad,
  mat.`idcategoria`
FROM
  `materiaprima` mat,
  `unidades` uni
WHERE
  mat.idunidad = uni.idunidad ORDER by nombre DESC
";
    $productoA =getTablaToArrayOfSQL($sql) ;
//    $productoA = ListarMateriaPrimaIdNombreUnidad("", "", "nombre", "DESC", "", "", "", true);
    if($productoA["error"]=="false"){
        $dev['mensaje'] = "No se pudo encontrar Proveedores";
        $dev['error']   = "false";

    }
    $encargadoA = ListarEmpleadosIdNombre("", "", "nombre", "DESC", "", "", "", true);
    if($encargadoA["error"]=="false"){
        $dev['mensaje'] = "No se pudo encontro proveedores";
        $dev['error']   = "false";
    }

    $CategoriaA = ListarCategoria("", "", "nombre", "DESC", "", "", "", true);
    if($CategoriaA["error"]=="false"){
        $CategoriaA['mensaje'] = "No se pudo encontro proveedores";
        $CategoriaA['error']   = "false";
    }

    //    $dev['resultado'] = $productoA['error'];
    if(($productoA["error"]==true)&&($proveedorA["error"]==true)&&($encargadoA["error"]==true)&&($CategoriaA["error"]==true)){
        $dev['mensaje'] = "Todo Ok";
        $dev['error'] = "true";
        $dev['productoM'] = $productoA['resultado'];
        $dev['proveedorM']= $proveedorA['resultado'];
        $dev['encargadoM']= $encargadoA['resultado'];
        $dev['categoriaM']= $CategoriaA['resultado'];

    }




    if($return == true)
    {
        return $dev;
    }
    else
    {

        if($callback == null || $callback == "")
        {
            $json = new Services_JSON();
            $output = $json->encode($dev);
            print($output);
        }
        else
        {
            $json = new Services_JSON();
            $output = $json->encode($dev);
            $output = substr($output, 1);
            $output = "$callback({".$output.");";
            print($output);
        }


    }

}

//fin sistea flores jaldin
?>