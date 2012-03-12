<?php
function CargarPanelDistribucion($callback, $_dc, $numeroorden,$return = false){

    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";
    $sql = "
SELECT
  op.idordenproduccion AS id,
  op.fechaentrega,
  op.observacion,
  op.cliente
FROM
  `ordenproduccion` op
WHERE
  op.numeroorden = '$numeroorden'
";
    $idoA = findBySqlReturnCampoUnique($sql, true, true, 'id');
    $ido = $idoA['resultado'];

    $sqlitems = "
SELECT
  det.iddetalleorden as id,
  CONCAT(det.detalle, ' ', det.talla) AS nombre,
  det.cantidad as uds
FROM
  detalleorden det
WHERE
  det.idordenproduccion = '$ido'
";
    //    echo $sqlitems;
    $itemsA = getTablaToArrayOfSQL($sqlitems);
    $value['itemM'] = $itemsA['resultado'];

    $sqlitems1 = "
SELECT
  dis.iddistribucion AS id,
  CONCAT(dis.detalle, ' - ', dis.encargado) AS detalle
FROM
  distribuciones dis
WHERE
  dis.op = '$numeroorden'
";
    //        echo $sqlitems1;
    $itemsA1 = getTablaToArrayOfSQL($sqlitems1);
    $value['itemsGuardadosM'] = $itemsA1['resultado'];

    $mpalmacenA =ListarMateriaPrimaIdNombreUnidad($start, $limit, $sort, $dir, $callback, $_dc, "detalle", true);
    $value['mpalmacenM'] = $mpalmacenA['resultado'];

    $encargadoM = ListarEmpleadosIdNombre($start, $limit, $sort, $dir, $callback, $_dc, $where, true);
    $value['encargadoM'] = $encargadoM['resultado'];



    if($numeroorden != null)
    {
        if($link=new BD)
        {
            if($link->conectar())
            {
                if($re = $link->consulta($sql))
                {
                    if($fi = mysql_fetch_array($re))
                    {
                        for($i = 0; $i< mysql_num_fields($re); $i++)
                        {
                            if(mysql_field_type($re, $i) == "real")
                            {
                                $value{mysql_field_name($re, $i)}= redondear($fi[$i]);
                            }
                            else
                            {
                                $value{mysql_field_name($re, $i)}= $fi[$i];
                            }
                        }
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
                    $dev['mensaje'] = "No se encontro datos en la consulta";
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
    }
    else
    {
        $dev['mensaje'] = "El codigo de producto es nulo";
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

function CargarDetalleItems($callback, $_dc, $id,$return = false){

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
  dis.iddetalleorden = '$id'
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
function CargarDetalleItemsEncargado($callback, $_dc, $idencargado,$iditem,$return = false){

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
  dis.idempleado = '$idencargado' AND dis.iddetalleorden = '$iditem'
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

function ListarDetalleDistribucion($callback, $_dc, $id,$return = false){

    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";



    //"id", "idtela","idcorte", "uds", "nombrei", "nombret","nombrec", "tela", "hoja", "uds1", "total", "totalunidades"
    //echo $id;exit();
    $sqldetalle = "
SELECT
  det.iddetalledistribucion AS id,
  det.`iddetalleorden` as iditems,
  det.idempleado AS idencargado,
  det.uds,
  det.detalle,
  det.total,
  det.fecha
FROM
  `detalledistribucion` det
WHERE
  det.iddistribucion = '$id'
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

function CargarDetalleItemsMaterial($callback, $_dc, $id,$iditem,$idencargado,$return = false){

    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";



    //"id", "idtela","idcorte", "uds", "nombrei", "nombret","nombrec", "tela", "hoja", "uds1", "total", "totalunidades"
    //echo $id;exit();
    $sqldetalle = "
SELECT
  det.idmateriaprima AS id,
  '$iditem' as iditems,
  '$idencargado' AS idencargado,
  det.uds,
  det.detalle,
  det.total,
  det.fecha
FROM
  `detalledistribucion` det
WHERE
  det.iddistribucion = '$id'
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

function getSqlNewDistribuciones($iddistribucion, $iddetalleorden, $idempleado, $detalle, $encargado, $cantidad, $fecha, $op, $numero, $idusuario, $return){
    $setC[0]['campo'] = 'iddistribucion';
    $setC[0]['dato'] = $iddistribucion;
    $setC[1]['campo'] = 'iddetalleorden';
    $setC[1]['dato'] = $iddetalleorden;
    $setC[2]['campo'] = 'idempleado';
    $setC[2]['dato'] = $idempleado;
    $setC[3]['campo'] = 'detalle';
    $setC[3]['dato'] = $detalle;
    $setC[4]['campo'] = 'encargado';
    $setC[4]['dato'] = $encargado;
    $setC[5]['campo'] = 'cantidad';
    $setC[5]['dato'] = $cantidad;
    $setC[6]['campo'] = 'fecha';
    $setC[6]['dato'] = $fecha;
    $setC[7]['campo'] = 'op';
    $setC[7]['dato'] = $op;
    $setC[8]['campo'] = 'numero';
    $setC[8]['dato'] = $numero;
    $setC[9]['campo'] = 'idusuario';
    $setC[9]['dato'] = $idusuario;
    $sql2 = generarInsertValues($setC);
    return "INSERT INTO distribuciones ".$sql2;
}
function getSqlNewDetalledistribucion($iddetalledistribucion, $idmateriaprima, $iddetalleorden, $idempleado, $uds, $detalle, $total, $fecha, $iddistribucion, $numero, $idusuario, $return){
    $setC[0]['campo'] = 'iddetalledistribucion';
    $setC[0]['dato'] = $iddetalledistribucion;
    $setC[1]['campo'] = 'idmateriaprima';
    $setC[1]['dato'] = $idmateriaprima;
    $setC[2]['campo'] = 'iddetalleorden';
    $setC[2]['dato'] = $iddetalleorden;
    $setC[3]['campo'] = 'idempleado';
    $setC[3]['dato'] = $idempleado;
    $setC[4]['campo'] = 'uds';
    $setC[4]['dato'] = $uds;
    $setC[5]['campo'] = 'detalle';
    $setC[5]['dato'] = $detalle;
    $setC[6]['campo'] = 'total';
    $setC[6]['dato'] = $total;
    $setC[7]['campo'] = 'fecha';
    $setC[7]['dato'] = $fecha;
    $setC[8]['campo'] = 'iddistribucion';
    $setC[8]['dato'] = $iddistribucion;
    $setC[9]['campo'] = 'numero';
    $setC[9]['dato'] = $numero;
    $setC[10]['campo'] = 'idusuario';
    $setC[10]['dato'] = $idusuario;
    $sql2 = generarInsertValues($setC);
    return "INSERT INTO detalledistribucion ".$sql2;
}
function getSqlDeleteDetalledistribucion($iddetalledistribucion){
    return "DELETE FROM detalledistribucion WHERE iddetalledistribucion ='$iddetalledistribucion';";
}
function GuardarNuevaDistribucion($callback, $_dc, $resultado,$return = false){
    $dev['mensaje'] = "";
    $dev['error']   = "false";
    $dev['resultado'] = "";

    $distribucionOp = $resultado->distribucion;
    $op = $distribucionOp->op;
    $idusuario = $_SESSION['idusuario'];

    $numeroA = findUltimoID("detalledistribucion", "numero", true);
    $numero = $numeroA['resultado']+1;
    $detalles = $resultado->detallesmaterial;
    $hora = date("H:i:s");
    if(count($detalles)>0){


        for($j=0;$j<count($detalles);$j++){
            $detalle2 = $detalles[$j];
            $idmateriaprima = $detalle2->id;
            $iddetalleorden1 = $detalle2->iditems;
            $idempleado1 = $detalle2->idencargado;
            $uds = $detalle2->uds;
            $det1 = $detalle2->detalle;
            $total = $detalle2->total;
            $fecha1 = $detalle2->fecha;
            $iddetalledistribucion = 'ddi-'.$numero;
            $id1 = substr($idmateriaprima,0,3);

            if($id1=="mtp"){
                $sql1 = "
SELECT
  k.saldocantidad
FROM
  kardexmateriaprima k
WHERE
  k.idmateriaprima = '$idmateriaprima'
";
                $cantidadventaA1 = findBySqlReturnCampoUnique($sql1, true, true, "saldocantidad");
                $cantidadventa1 = $cantidadventaA1['resultado'];
                if($total > $cantidadventa1){
                    $dev['mensaje'] = "No tiene suficiente Stock : ".$det1." Cantidad Disponible :".$cantidadventa1;
                    $json = new Services_JSON();
                    $output = $json->encode($dev);
                    print($output);
                    exit;
                }

                $sql[] = getSqlNewDetalledistribucion($iddetalledistribucion, $idmateriaprima, $iddetalleorden1, $idempleado1, $uds, $det1, $total, $fecha1, $iddetalleorden1, $numero, $idusuario, $return);


            }
            else{

                $sql[]=  getSqlDeleteDetalledistribucion($idmateriaprima);
                revertirMovimientoMateriaPrima($idmateriaprima,true);
                $sqlid = "
                SELECT
  det.idmateriaprima
FROM
  `detalledistribucion` det
WHERE
  det.iddetalledistribucion = '$idmateriaprima'
                ";
                $idA = findBySqlReturnCampoUnique($sqlid, true, true, "idmateriaprima");
                $idA1 = $idA['resultado'];
                $sql1 = "
SELECT
  k.saldocantidad
FROM
  kardexmateriaprima k
WHERE
  k.idmateriaprima = '$idA1'
";
                $cantidadventaA1 = findBySqlReturnCampoUnique($sql1, true, true, "saldocantidad");
                $cantidadventa1 = $cantidadventaA1['resultado'];
                if($total > $cantidadventa1){
                    $dev['mensaje'] = "No tiene suficiente Stock : ".$det1." Cantidad Disponible :".$cantidadventa1;
                    $json = new Services_JSON();
                    $output = $json->encode($dev);
                    print($output);
                    exit;
                }
                $sql[] = getSqlNewDetalledistribucion($iddetalledistribucion, $idA1, $iddetalleorden1, $idempleado1 , $uds, $det1, $total, $fecha1, $iddetalleorden1, $numero, $idusuario, $return);
                $idmateriaprima = $idA1;
            }
            $movimiento[$j]["id"] = $idmateriaprima;
            $movimiento[$j]["fecha"] = $fecha1;
            $movimiento[$j]["hora"] = $hora;
            $movimiento[$j]["cantidad"] = $total;
            MovimientoKardexMateriaPrimaSalida($movimiento[$j]['id'], $movimiento[$j]['cantidad'],$movimiento[$j]['fecha'],$movimiento[$j]['hora'], "Proceso Distribucion OP".$op, $iddetalledistribucion, false);

            $numero++;


        }




    }
    //            exit();

    //            $idcorte = 'cor-'.$numero;
    //            $sql1 = "
    //SELECT
    //  k.saldocantidad
    //FROM
    //  kardexmateriaprima k
    //WHERE
    //  k.idmateriaprima = '$idtela'
    //";
    //            $cantidadventaA1 = findBySqlReturnCampoUnique($sql1, true, true, "saldocantidad");
    //            $cantidadventa1 = $cantidadventaA1['resultado'];
    //            if($totaltela > $cantidadventa1){
    //                $dev['mensaje'] = "No tiene suficiente Stock : ".$nombret." Cantidad Disponible :".$cantidadventa1;
    //                $json = new Services_JSON();
    //                $output = $json->encode($dev);
    //                print($output);
    //                exit;
    //            }
    //
    //            if($id=="deo"){
    //
    //                $sql[] = getSqlNewCortes($idcorte, $op, $iddetalleorden, $idtela, $idmolde, $nombrei, $nombret, $nombrem, $tela, $hoja, $uds, $totaltela, $totalunidades, $numero, $idusuario, $fecha, $return);
    //                //            $sql[] = getSqlNewDetalleorden($iddetalleorden, $idordenproduccion, $id, $detalle, $unidad, $preciounitario, $cantidad, $preciototal, $numerodetalle, $cantidad, $return);
    //
    //            }
    //            else{
    //                $sql[] = getSqlDeleteCortes($iddetalleorden);
    //                $sqlid = "
    //SELECT
    //  cor.iddetalleop
    //FROM
    //  cortes cor
    //WHERE
    //  cor.idcorte = '$iddetalleorden'
    //";
    //                $idA = findBySqlReturnCampoUnique($sqlid, true, true, "iddetalleop");
    //                $idA1 = $idA['resultado'];
    //                $sql[] = getSqlNewCortes($idcorte, $op, $idA1, $idtela, $idmolde, $nombrei, $nombret, $nombrem, $tela, $hoja, $uds, $totaltela, $totalunidades, $numero, $idusuario, $fecha, $return);
    //                //echo "hola";
    //                revertirMovimientoMateriaPrima($iddetalleorden,true);
    //                //                exit();
    //
    //            }
    //            $movimiento[$i]["id"] = $idtela;
    //            $movimiento[$i]["fecha"] = $fecha;
    //            $movimiento[$i]["hora"] = $hora;
    //            $movimiento[$i]["cantidad"] = $totaltela;
    //            MovimientoKardexMateriaPrimaSalida($movimiento[$i]['id'], $movimiento[$i]['cantidad'],$movimiento[$i]['fecha'],$movimiento[$i]['hora'], "Proceso Corte OP".$op, $idcorte, false);
    //
    //            $numero++;
    //
    //
    //
    //        }

    else{
        $dev['mensaje'] = "Error Debe de ingresar algun producto";
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    //     exit();
    //    MostrarConsulta($sql);exit();
    if(ejecutarConsultaSQLBeginCommit($sql)) {
        for($k=0;$k<count($movimiento);$k++){
            //            echo $movimiento[$j]['idproducto'];
            actualizarSaldoMovimientoKardexMateriaPrima($movimiento[$k]['id'],$movimiento[$k]['fecha'],$movimiento[$k]['hora'], false);

        }


        $dev['mensaje'] = "Se actualizo correctamente";
        $dev['error'] = "true";
        $dev['resultado'] = "";
        $numero = $numeroorden +1;
        $dev['numeroorden'] = "$numero";
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

function GuardarItemDistribucion($callback, $_dc, $resultado,$return = false){
    $dev['mensaje'] = "";
    $dev['error']   = "false";
    $dev['resultado'] = "";


    $idusuario = $_SESSION['idusuario'];
    $numeroA = findUltimoID("distribuciones", "numero", true);
    $numero = $numeroA['resultado']+1;
    //iditem=deo-10&idencargado=emp-10&detalle=polera prueba L&encargado=brayle&cantidad=12&fecha2011-12-15
    $iddetalleorden = $_GET['iditem'];
    $idempleado = $_GET['idencargado'];
    $detalle = $_GET['detalle'];
    $encargado = $_GET['encargado'];
    $cantidad = $_GET['cantidad'];
    $fechad = $_GET['fecha'];

    $sqlop = "
SELECT
  `ord`.numeroorden
FROM
  `detalleorden` det,
  `ordenproduccion` `ord`
WHERE
  det.idordenproduccion = `ord`.idordenproduccion AND
  det.iddetalleorden = '$iddetalleorden'
";
    $opA = findBySqlReturnCampoUnique($sqlop, true, true, "numeroorden");
    $op = $opA['resultado'];
    $iddistribucion = 'dis-'.$numero;
    $sql[] = getSqlNewDistribuciones($iddistribucion, $iddetalleorden, $idempleado, $detalle, $encargado, $cantidad, $fechad, $op, $numero, $idusuario, $return);
    $numero++;


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
        $dev['id'] = "$iddistribucion";
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
function getSqlDeleteDistribuciones($iddistribuciones){
    return "DELETE FROM distribuciones WHERE iddistribucion ='$iddistribuciones';";
}
function eliminarItemDistribucion($callback, $_dc, $id,$return = false){
    $dev['mensaje'] = "";
    $dev['error']   = "";

    //    $sql[] = getSqlDeleteLinea_marca($idlinea);
    $sql[] = getSqlDeleteDistribuciones($id);
    $sql1 = "
SELECT
  det.iddetalledistribucion
FROM
  `detalledistribucion` det
WHERE
  det.iddistribucion = '$id'
";
    $datosA = getTablaToArrayOfSQL($sql1);
    $datos = $datosA['resultado'];
    if($datosA['error']=="true"){
        for($i=0;$i<count($datos);$i++){

            $dato = $datos[$i];
            $idt = $dato['iddetalledistribucion'];
            $sql[] = getSqlDeleteDetalledistribucion($idt);
            revertirMovimientoMateriaPrima($idt,true);
        }

        //        revertirMovimientoMateriaPrima($id,true);
    }
    //    exit();

    //    $sql[] = getSqlDeleteCotizacion($idproducto);
    //    MostrarConsulta($sql);
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
function eliminarItemDistribucionMaterial($callback, $_dc, $id,$return = false){
    $dev['mensaje'] = "";
    $dev['error']   = "";

    //    $sql[] = getSqlDeleteLinea_marca($idlinea);




    $sql[] = getSqlDeleteDetalledistribucion($id);
    revertirMovimientoMateriaPrima($id,true);

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