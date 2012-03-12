<?php
function CargarPanelRecepcion($callback, $_dc, $numeroorden,$return = false){

    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";


    $sqlitems = "
SELECT DISTINCT
  emp.idempleado,
  emp.nombre
FROM
  empleados emp,
  distribuciones dis
WHERE
  dis.op = '$numeroorden' AND
  dis.idempleado = emp.idempleado
";
    //    echo $sqlitems;
    $itemsA = getTablaToArrayOfSQL($sqlitems);
    $value['encargadoM'] = $itemsA['resultado'];




    if($itemsA['error']=="true"){
        $dev['mensaje'] = "Existen resultados";
        $dev['error']   = "true";
        $dev['resultado'] = $value;
    }
    else{
        $dev['mensaje'] = "No hay datos en la consulta";
        $dev['error']   = "false";
        $dev['resultado'] = "";
    }



    //    }




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


function CargarDetalleItemsEncargado($callback, $_dc, $idencargado,$return = false){

    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";



    //"id", "idtela","idcorte", "uds", "nombrei", "nombret","nombrec", "tela", "hoja", "uds1", "total", "totalunidades"
    //echo $id;exit();
    $sqldetalle = "
SELECT
  dis.iddistribucion AS id,
  dis.idempleado AS 'idencargado',
  dis.detalle,
  dis.encargado AS 'encargado',
  dis.cantidad,
  dis.fecha AS plazo
FROM
  `distribuciones` dis
WHERE
  dis.idempleado = '$idencargado'
";
    //echo $sqldetalle;

    $detalleA = getTablaToArrayOfSQL($sqldetalle);



    $value['detalleM'] = $detalleA['resultado'];
    if($detalleA['error']=="true"){
        $dev['mensaje'] = "Existen resultados";
        $dev['error']   = "true";
        $dev['resultado'] = $value;
    }
    else{
        $dev['mensaje'] = "No Existen resultados";
        $dev['error']   = "true";
        $dev['resultado'] = "";
    }





    //    }




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

function getSqlNewRecepciones($idrecepcion, $iddistribucion, $op, $cantidad, $fecha, $hora, $detalle, $idusuario, $saldocantidad, $numero, $return){
    $setC[0]['campo'] = 'idrecepcion';
    $setC[0]['dato'] = $idrecepcion;
    $setC[1]['campo'] = 'iddistribucion';
    $setC[1]['dato'] = $iddistribucion;
    $setC[2]['campo'] = 'op';
    $setC[2]['dato'] = $op;
    $setC[3]['campo'] = 'cantidad';
    $setC[3]['dato'] = $cantidad;
    $setC[4]['campo'] = 'fecha';
    $setC[4]['dato'] = $fecha;
    $setC[5]['campo'] = 'hora';
    $setC[5]['dato'] = $hora;
    $setC[6]['campo'] = 'detalle';
    $setC[6]['dato'] = $detalle;
    $setC[7]['campo'] = 'idusuario';
    $setC[7]['dato'] = $idusuario;
    $setC[8]['campo'] = 'saldocantidad';
    $setC[8]['dato'] = $saldocantidad;
    $setC[9]['campo'] = 'numero';
    $setC[9]['dato'] = $numero;
    $sql2 = generarInsertValues($setC);
    return "INSERT INTO recepciones ".$sql2;
}
function GuardarItemEntrega($callback, $_dc, $resultado,$return = false){
    $dev['mensaje'] = "";
    $dev['error']   = "false";
    $dev['resultado'] = "";


    $idusuario = $_SESSION['idusuario'];
    $numeroA = findUltimoID("recepciones", "numero", true);
    $numero = $numeroA['resultado']+1;
    //iditem=deo-10&idencargado=emp-10&detalle=polera prueba L&encargado=brayle&cantidad=12&fecha2011-12-15
    $id = $_GET['iditem'];
    $detalle = $_GET['detalle'];
    $fecha = $_GET['fecha'];
    $cantidad = $_GET['cantidad'];

    $sqlop = "
SELECT
  dis.op
FROM
  distribuciones dis
WHERE
  dis.iddistribucion = '$id'
";
    $opA = findBySqlReturnCampoUnique($sqlop, true, true, "op");
    $op = $opA['resultado'];
    $idrecepcion = 'rec-'.$numero;
    $hora = date("H:i:s");
    $saldosql ="
SELECT
  SUM(rec.cantidad) as total
FROM
  `recepciones` rec
WHERE
  rec.iddistribucion = '$id'
";
    $saldoA = findBySqlReturnCampoUnique($saldosql, true, true, "total");
    $saldo = $saldoA['resultado']+$cantidad;
    $sqli = "
SELECT
  dis.cantidad
FROM
  distribuciones dis
WHERE
  dis.iddistribucion = '$id'
";
    $cantA = findBySqlReturnCampoUnique($sqli, true, true, 'cantidad');
    $cant = $cantA['resultado'];
    if($cant >= $saldo){
        $sql[] =getSqlNewRecepciones($idrecepcion, $id, $op, $cantidad, $fecha, $hora, $detalle, $idusuario, $cantidad, $numero, $return) ;
        $numero++;

    }
    else{
        $dev['mensaje'] = "No se puede Producir Mas de lo que Indico - Cantidad a Producir = ".$cant." - Cantidad Ya producida =".$saldoA['resultado'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }



    //     exit();
    //    MostrarConsulta($sql);exit();
    if(ejecutarConsultaSQLBeginCommit($sql)) {
        //        for($k=0;$k<count($movimiento);$k++){
        //            //            echo $movimiento[$j]['idproducto'];
        //            actualizarSaldoMovimientoKardexMateriaPrima($movimiento[$k]['id'],$movimiento[$k]['fecha'],$movimiento[$k]['hora'], false);
        //
        //        }


        $dev['mensaje'] = "Se actualizo correctamente";
        $dev['error'] = "true";
        $dev['resultado'] = "";
        $numero = $numeroorden +1;
        $dev['id'] = "$idrecepcion";
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
function ListarDetalleEntregaDistribucion($callback, $_dc, $id,$return = false){

    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";



    //"id", "idtela","idcorte", "uds", "nombrei", "nombret","nombrec", "tela", "hoja", "uds1", "total", "totalunidades"
    //echo $id;exit();
    $sqldetalle = "
SELECT
  rec.idrecepcion AS id,
  rec.iddistribucion AS iditems,
  rec.cantidad AS cantidad,
  rec.detalle AS detalle,
  rec.fecha
FROM
  recepciones rec
WHERE
  rec.iddistribucion = '$id'
";
    //    echo $sqldetalle;

    $detalleA = getTablaToArrayOfSQL($sqldetalle);



    $value['productoM'] = $detalleA['resultado'];
    if($detalleA['error']=="true"){
        $dev['mensaje'] = "Existen resultados";
        $dev['error']   = "true";
        $dev['resultado'] = $value;
    }
    else{
        $dev['mensaje'] = "No Existen resultados";
        $dev['error']   = "true";
        $dev['resultado'] = $value;
    }





    //    }




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

function getSqlDeleteRecepciones($idrecepciones){
    return "DELETE FROM recepciones WHERE idrecepcion ='$idrecepciones';";
}

function eliminarItemDistribucionEntrega($callback, $_dc, $id,$return = false){
    $dev['mensaje'] = "";
    $dev['error']   = "";

    //    $sql[] = getSqlDeleteLinea_marca($idlinea);




    $sql[] = getSqlDeleteRecepciones($id);


    if(ejecutarConsultaSQLBeginCommit($sql))
    {
        $dev['mensaje'] = "Se Elimino correctamente";
        $dev['error'] = "true";
        $dev['resultado'] = "";
    }
    else
    {
        $dev['mensaje'] = "No se puede Eliminar...  Existe el valor en alguna tabla";
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

?>