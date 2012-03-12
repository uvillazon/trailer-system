<?php
function CargarPanelEntregaProceso($callback, $_dc, $where='',$return = false){
    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";
    $procesoA = ListarProcesosIdNombreProcesos("", "", "nombre", "ASC", "", "", "", true);
    if($procesoA["error"]=="false"){
        $dev['mensaje'] = "No se pudo encontro clientes";
        $dev['error']   = "false";


    }
    $productoA = ListarProductoIdNombreUnidad("", "", "detalle", "ASC", "", "", "", true);
    if($productoA["error"]=="false"){
        $dev['mensaje'] = "No se pudo encontro productos";
        $dev['error']   = "false";

    }
    $materiaPrimaA = ListarMateriaPrimaIdNombreUnidad("", "", "detalle", "ASC", "", "", "detalle", true);
    if($materiaPrimaA["error"]=="false"){
        $dev['mensaje'] = "No se pudo encontro materiaprima";
        $dev['error']   = "false";
    }

    $empleadoA = ListarEmpleadosIdNombre("", "", "nombre", "ASC", "", "", "detalle", true);
    if($empleadoA["error"]=="false"){
        $dev['mensaje'] = "No se pudo encontro empresas";
        $dev['error']   = "false";
    }

    $value{0}{"id"}= "1";
    $value{0}{"detalle"}= "No existe Productos";
    $dev['productosIntermedio'] = $value;

    //    $dev['resultado'] = $productoA['error'];
    if(($productoA["error"]=="true")&&($procesoA["error"]=="true")&&($materiaPrimaA["error"]=="true")&&($empleadoA["error"]=="true")){
        $dev['mensaje'] = "Todo Ok";
        $dev['error'] = "true";
        $dev['productoM'] = $productoA['resultado'];
        $dev['procesoM']= $procesoA['resultado'];
        $dev['materiaM']= $materiaPrimaA['resultado'];
        $dev['encargadoM']= $empleadoA['resultado'];

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

function BuscarProductosPorId($callback, $_dc, $numeroorden,$return = false){

    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";


    $productoA = ListarProductoIntermedioIdNombreUnidad("", "", "detalle", "DESC", "", "", $numeroorden, true);
    if($productoA["error"]=="false"){
        $dev['mensaje'] = "No se pudo encontro productos";
        $dev['error']   = "false";

    }
    $productoOrdenA = ListarProductosOrdenIdNombreUnidad("", "", "detalle", "DESC", "", "", $numeroorden, true);
    if($productoOrdenA["error"]=="false"){
        $dev['mensaje'] = "No se pudo encontro materiaprima";
        $dev['error']   = "false";
    }



    //    $dev['resultado'] = $productoA['error'];
    //    if(($productoA["error"]=="true")&&($productoOrdenA["error"]=="true")){
    $dev['mensaje'] = "Todo Ok";
    $dev['error'] = "true";
    $value['productoIntermedioM'] = $productoA['resultado'];
    $value['productoOrdenM']= $productoOrdenA['resultado'];
    $dev['resultado'] = $value;
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
function BuscarProduccionPorNumero($callback, $_dc, $numeroorden,$return = false){

    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";
    $sql = "SELECT
  prp.idproduccionproceso as idproduccion,
  prp.numeroorden,
  prp.idproceso,
  prp.idempleado as id,
  prp.fechaentrega,
  prp.observacion
FROM
  `produccionprocesos` prp
WHERE
  prp.numeroproduccion = '$numeroorden'";
    $ordenA = findBySqlReturnCampoUnique($sql, true, true, "numeroorden");
    $orden = $ordenA['resultado'];
    $idproA = findBySqlReturnCampoUnique($sql, true, true, "idproduccion");
    $idpro = $idproA['resultado'];
    $productosA =BuscarProductosPorId($callback, $_dc, $orden, true);
    $resultado = $productosA['resultado'];
    //    echo $resultado."ss";
    $value['productoIntermedioM'] = $resultado['productoIntermedioM'];
    $value['productoOrdenM']= $resultado['productoOrdenM'];
    $sqlentrega = "
SELECT
dpr.id,
  dpr.cantidad,
  dpr.detalle,
 dpr.unidad
FROM
  detalleproduccionproceso dpr
WHERE
  dpr.idproduccionproceso = '$idpro' AND
  dpr.tipo = 'entrega'
";
    $entregasA = getTablaToArrayOfSQL($sqlentrega);
    $value['entregasM'] = $entregasA['resultado'];
    $sqlsalidas = "
SELECT
  dpr.id,
  dpr.cantidad,
  dpr.detalle,
  dpr.unidad
FROM
  detalleproduccionproceso dpr
WHERE
  dpr.idproduccionproceso = '$idpro' AND
  dpr.tipo = 'recepcion'
";
    $salidasA = getTablaToArrayOfSQL($sqlsalidas);
    $value['salidasM'] = $salidasA['resultado'];




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


function BuscarNumeroProduccion($callback, $_dc, $idproceso,$return = false){
    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";

    $sql = "SELECT
  pro.codigo
FROM
  procesos pro
WHERE
  pro.idproceso = '$idproceso'";

    $codigoA = findBySqlReturnCampoUnique($sql, true, true, "codigo");
    $codigo = $codigoA['resultado'];
    $numeroA = findUltimoID("produccionprocesos", "numero", true);
    $numero = $numeroA['resultado']+1;
    //    echo
    $dev['mensaje'] = "Ok";
    $dev['error']   = "true";
    $value['numeroproduccion'] = $codigo."".$numero;
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

function getSqlNewProduccionprocesos($idproduccionproceso, $numeroorden, $idproceso, $idempleado, $fechaentrega, $fecharecepcion, $observacion, $estado, $numero, $numeroproduccion, $return){
    $setC[0]['campo'] = 'idproduccionproceso';
    $setC[0]['dato'] = $idproduccionproceso;
    $setC[1]['campo'] = 'numeroorden';
    $setC[1]['dato'] = $numeroorden;
    $setC[2]['campo'] = 'idproceso';
    $setC[2]['dato'] = $idproceso;
    $setC[3]['campo'] = 'idempleado';
    $setC[3]['dato'] = $idempleado;
    $setC[4]['campo'] = 'fechaentrega';
    $setC[4]['dato'] = $fechaentrega;
    $setC[5]['campo'] = 'fecharecepcion';
    $setC[5]['dato'] = $fecharecepcion;
    $setC[6]['campo'] = 'observacion';
    $setC[6]['dato'] = $observacion;
    $setC[7]['campo'] = 'estado';
    $setC[7]['dato'] = $estado;
    $setC[8]['campo'] = 'numero';
    $setC[8]['dato'] = $numero;
    $setC[9]['campo'] = 'numeroproduccion';
    $setC[9]['dato'] = $numeroproduccion;
    $sql2 = generarInsertValues($setC);
    return "INSERT INTO produccionprocesos ".$sql2;
}

function GuardarProduccionProceso($resultado, $return = false){
    $dev['mensaje'] = "Se actualizo correctamente";
    $dev['error'] = "true";
    $dev['resultado'] = "";
    $orden = $resultado->orden;
    $id = $orden->id;

    $numeroorden = $orden->ordenproduccion;
    $idproceso = $orden->proceso;
    $idempleado = $orden->operador;
    $fechaentrega = $orden->fechaentrega;
    $fecharecepcion = date("Y-m-d");
    $observacion = $orden->observacion;
    $numeroproduccion = $orden->numeroproduccion;
    $numeroproduccionA = validarText($numeroproduccion, true);
    if($numeroproduccionA["error"]==false){
        $dev['mensaje'] = "Error en el campo numero produccion de proceso: ".$numeroproduccionA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }

    //    echo $idproduccionproceso;
    if($id != ""){
        //        echo "hola ";
        //        echo $id;

        EliminarProduccionProceso($callback, $_dc, $id,true);
        //        exit();
    }
    $numeroA = findUltimoID("produccionprocesos", "numero", true);
    $numero = $numeroA['resultado']+1;
    $idproduccionproceso = "prp-".$numero;

    $sql[] =getSqlNewProduccionprocesos($idproduccionproceso, $numeroorden, $idproceso, $idempleado, $fechaentrega, $fecharecepcion, $observacion, "produccion", $numero, $numeroproduccion, $return);

    $entregas = $resultado->entregas;
    $salidas = $resultado->salidas;
    $numerodetalleentrega = findUltimoID("detalleproduccionproceso", "numero", true);
    $numerod = $numerodetalleentrega['resultado']+1;
    for($j=0;$j<count($entregas);$j++){
        $entrega = $entregas[$j];
        $detalle = $entrega->detalle;
        $cantidad = $entrega->cantidad;
        $unidad = $entrega->unidad;
        $id =$entrega->id;

        $iddetalleproduccionproceso = "dpp-".$numerod;
        $sql[] = getSqlNewDetalleproduccionproceso($iddetalleproduccionproceso, $idproduccionproceso, $detalle, $unidad, $cantidad, $id, $numerod, "entrega", $return);
        $numerod++;

    }
    $numeroiA = findUltimoID("productointermedios", "numero", true);
    $numeroi = $numeroiA['resultado']+1;
    for($j=0;$j<count($salidas);$j++){
        $salida = $salidas[$j];
        $detalle = $salida->detalle;
        $cantidad = $salida->cantidad;
        $unidad = $salida->unidad;
        $id =$salida->id;

        if($id ==1){
            $idproductointermedio = "pri-".$numeroi;
            $sql[] = getSqlNewProductointermedios($idproductointermedio, $numeroorden, $idproduccionproceso, $detalle, "pieza", 0, $numeroi, $return);
            $numeroi++;
            $id = $idproductointermedio;
        }

        $iddetalleproduccionproceso = "dpp-".$numerod;
        $sql[] = getSqlNewDetalleproduccionproceso($iddetalleproduccionproceso, $idproduccionproceso, $detalle, "pieza", $cantidad, $id, $numerod, "recepcion", $return);
        $numerod++;

    }

    //    MostrarConsulta($sql);exit();
    if(ejecutarConsultaSQLBeginCommit($sql))
    {
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

function ListarProduccionProcesos($start, $limit, $sort, $dir, $callback, $_dc, $where = '', $return = false){

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
        $sql = "
SELECT
  prp.idproduccionproceso,
  prp.numeroorden,
  prp.numeroproduccion,
  prp.fechaentrega,
  emp.nombre AS empleado,
  prp.estado
FROM
  produccionprocesos prp,
  empleados emp
WHERE
  prp.idempleado = emp.idempleado $order
";
    }
    else{
        $sql ="
SELECT
  prp.idproduccionproceso,
  prp.numeroorden,
  prp.numeroproduccion,
  prp.fechaentrega,
  emp.nombre AS empleado,
  prp.estado
FROM
  produccionprocesos prp,
  empleados emp
WHERE
  prp.idempleado = emp.idempleado AND $where $order
";
    }

    //            echo $sql;
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


function EliminarProduccionProceso($callback, $_dc, $idproduccionproceso, $return = false){
    $dev['mensaje'] = "";
    $dev['error'] = "false";
    $dev['resultado'] = "";
    //    echo "hola";

    $sqlproduccion = "
SELECT
  prp.idproduccionproceso,
  prp.numeroorden,
  prp.numeroproduccion,
  prp.fechaentrega,
  prp.estado
FROM
  produccionprocesos prp
WHERE
  prp.idproduccionproceso = '$idproduccionproceso'
";
    $estadoA = findBySqlReturnCampoUnique($sqlproduccion, true, true, "estado");
    $estado = $estadoA['resultado'];
    //    echo $estado;
    if($estado == "produccion"){

        $sql[] = getSqlDeleteDetalleproduccionprocesoForProduccion($idproduccionproceso);
        $sql[] = getSqlDeleteProductointermediosProduccionProceso($idproduccionproceso);
        $sql[] = getSqlDeleteProduccionprocesos($idproduccionproceso);
    }else
    {
        $dev['mensaje'] = "No Puede Eliminar la produccion ya fue Completada";
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }

    if(ejecutarConsultaSQLBeginCommit($sql))
    {

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
function getSqlUpdateProduccionprocesos($idproduccionproceso,$numeroorden,$idproceso,$idempleado,$fechaentrega,$fecharecepcion,$observacion,$estado,$numero,$numeroproduccion, $return){
    $setC[0]['campo'] = 'numeroorden';
    $setC[0]['dato'] = $numeroorden;
    $setC[1]['campo'] = 'fechaentrega';
    $setC[1]['dato'] = $fechaentrega;
    $setC[2]['campo'] = 'fecharecepcion';
    $setC[2]['dato'] = $fecharecepcion;
    $setC[3]['campo'] = 'observacion';
    $setC[3]['dato'] = $observacion;
    $setC[4]['campo'] = 'estado';
    $setC[4]['dato'] = $estado;
    $setC[5]['campo'] = 'numero';
    $setC[5]['dato'] = $numero;
    $setC[6]['campo'] = 'numeroproduccion';
    $setC[6]['dato'] = $numeroproduccion;
    $setC[7]['campo'] = 'idproceso';
    $setC[7]['dato'] = $idproceso;
    $setC[8]['campo'] = 'idempleado';
    $setC[8]['dato'] = $idempleado;
    $set = generarSetsUpdate($setC);
    $wher[0]['campo'] = 'idproduccionproceso';
    $wher[0]['dato'] = $idproduccionproceso;


    $where = generarWhereUpdate($wher);
    return "UPDATE produccionprocesos SET ".$set." WHERE ".$where;
}

function AprobarProduccionProceso($callback, $_dc, $idproduccionproceso, $return = false){
    $dev['mensaje'] = "";
    $dev['error'] = "false";
    $dev['resultado'] = "";
    //    echo "hola";EXIT();

    $sqlproduccion = "
SELECT
  prp.idproduccionproceso,
  prp.numeroorden,
  prp.numeroproduccion,
  prp.fechaentrega,
  prp.estado
FROM
  produccionprocesos prp
WHERE
  prp.idproduccionproceso = '$idproduccionproceso'
";
    $estadoA = findBySqlReturnCampoUnique($sqlproduccion, true, true, "estado");
    $estado = $estadoA['resultado'];
    $numeroordenA = findBySqlReturnCampoUnique($sqlproduccion, true, true, "numeroproduccion");
    $numerooperacion = $numeroordenA['resultado'];
    //    echo $estado;
    if($estado == "produccion"){

        $sql[] = getSqlUpdateProduccionprocesos($idproduccionproceso, $numeroorden, $idproceso, $idempleado, $fechaentrega, $fecharecepcion, $observacion, "completado", $numero, $numeroproduccion, $return);
        $sqldetalle = "
SELECT
  det.detalle,
  det.unidad,
  det.cantidad,
  det.id,
  det.tipo
FROM
  `detalleproduccionproceso` det
WHERE
  det.idproduccionproceso = '$idproduccionproceso'
";
        //        echo $sqldetalle;
        $detallesA = getTablaToArrayOfSQL($sqldetalle);
        $detalles = $detallesA['resultado'];
        $numeroM = findUltimoID("movimientokardexmateriaprima", "numero", true);
        $numeromov = $numeroM['resultado']+1;
        $numeroPd = findUltimoID("movimientoproducto", "numero", true);
        $numeromovpro = $numeroPd['resultado'];
        $numeroPi = findUltimoID("movimientoproductointermedio", "numero", true);
        $numeromovpi = $numeroPi['resultado'];

        for($j=0;$j<count($detalles);$j++){
            $hora = date("H:i:s");
            $fecha = date("Y-m-d");
            $detalle = $detalles[$j];
            $id = $detalle['id'];
            $nombre = $detalle['detalle'];
            $cantidad = $detalle['cantidad'];
            $tipo = $detalle['tipo'];
            if($tipo == "entrega"){
                //                echo "ss";
                $idp = substr($id,0,3);
                if($idp == "pri"){
                    //                    echo " produco ";
                    $sqlproductoint = "SELECT
  pri.detalle,
  pri.saldocantidad
FROM
  `productointermedios` pri
WHERE
  pri.idproductointermedio = '$id'";
                    $saldopiA = findBySqlReturnCampoUnique($sqlproductoint, true, true, "saldocantidad");
                    if($cantidad > $saldopiA['resultado']){
                        $dev['mensaje'] = "ERROR no tiene suficiente STOCK :".$nombre;
                        $json = new Services_JSON();
                        $output = $json->encode($dev);
                        print($output);
                        exit;

                    }
                    else{
                        $movimiento[$j]["id"] = $id;
                        $movimiento[$j]["fecha"] = $fecha;
                        $movimiento[$j]["hora"] = $hora;
                        $movimiento[$j]["cantidad"] = $cantidad;
                        $movimiento[$j]["tipo"] = "pri";



                    }
                }
                if($idp == "mtp"){
                    //                                        echo "materia prima";
                    $sqlmateriaprima = "SELECT
  kar.saldocantidad,
  kar.saldobs,
  kar.costounitario
FROM
  kardexmateriaprima kar
WHERE
  kar.idmateriaprima = '$id'";
                    $saldomA = findBySqlReturnCampoUnique($sqlmateriaprima, true, true, "saldocantidad");
                    //                    echo $saldomat = $saldomA['resultado'];
                    if($cantidad > $saldomA['resultado']){
                        $dev['mensaje'] = "ERROR no tiene suficiente STOCK :".$nombre;
                        $json = new Services_JSON();
                        $output = $json->encode($dev);
                        print($output);
                        exit;

                    }
                    else{
                        $movimiento[$j]["id"] = $id;
                        $movimiento[$j]["fecha"] = $fecha;
                        $movimiento[$j]["hora"] = $hora;
                        $movimiento[$j]["cantidad"] = $cantidad;
                        $movimiento[$j]["tipo"] = "mtp";


                    }

                }
                if($idp == "prd"){
                    //                    echo "producto intermedio";
                    $sqlproducto = "SELECT
  pro.idproducto,
  pro.saldocantidad
FROM
  `productos` pro
WHERE
  pro.idproducto = '$id'";
                    $saldopA = findBySqlReturnCampoUnique($sqlproducto, true, true, "saldocantidad");
                    if($cantidad > $saldopA['resultado']){
                        $dev['mensaje'] = "ERROR no tiene suficiente STOCK :".$nombre;
                        $json = new Services_JSON();
                        $output = $json->encode($dev);
                        print($output);
                        exit;

                    }
                    else{
                        $movimiento[$j]["id"] = $id;
                        $movimiento[$j]["fecha"] = $fecha;
                        $movimiento[$j]["hora"] = $hora;
                        $movimiento[$j]["cantidad"] = $cantidad;
                        $movimiento[$j]["tipo"] = "mtp";


                    }

                }
                //                echo "hola";
                $movimiento[$j]["tipo2"] = "entrega";
                //salida de producto o material
            }
            else{
                //                echo "Ingresos de materiales o productos , productos intermedios";
                //                echo $idp = substr($id,0,3);
                if($idp == "prd"){
                    $sqlproducto = "
SELECT
  pro.costounitario
FROM
  `productos` pro
WHERE
  pro.idproducto = '$id'
";
                    $preciounitario =  findBySqlReturnCampoUnique($sqlproducto, true, true, "costounitario");
                    $costounitario = $preciounitario['resultado'];
                    $idmovimientoproducto = "mpr-".$numeromovpro;
                    $sql[] = getSqlNewMovimientoproducto($idmovimientoproducto, $id, $cantidad, 0, 0, $costounitario, $costounitario*$cantidad, 0, 0, $fecha, $hora, "Produccion de proceso", $numeromovpro, $cantidad, $idproduccionproceso, $return);
                    $numeromovpro++;
                    $movimiento[$j]["id"] = $id;
                    $movimiento[$j]["fecha"] = $fecha;
                    $movimiento[$j]["hora"] = $hora;
                    $movimiento[$j]["cantidad"] = $cantidad;
                    $movimiento[$j]["tipo"] = "prd";
                }
                if($idp == "mtp"){
                    $sqlmateriaprima = "
SELECT
  kar.costounitario
FROM
  `kardexmateriaprima` kar
WHERE
  kar.idmateriaprima = '$id'
";
                    $preciounitario = findBySqlReturnCampoUnique($sqlmateriaprima, true, true, "costounitario");
                    $costounitario = $preciounitario['resultado'];
                    $idmovimientokardexmateriaprima = "mmp-".$numeromov;
                    $sql[] = getSqlNewMovimientokardexmateriaprima($idmovimientokardexmateriaprima, $idmateriaprima, $cantidad, 0, 0, $costounitario, $costounitario * $cantidad, 0, 0, $fecha, $hora, "produccion de Procesos", $numeromov, $cantidad, $idproduccionproceso, $return);
                    $numeromov++;
                    $movimiento[$j]["id"] = $id;
                    $movimiento[$j]["fecha"] = $fecha;
                    $movimiento[$j]["hora"] = $hora;
                    $movimiento[$j]["cantidad"] = $cantidad;
                    $movimiento[$j]["tipo"] = "mtp";

                }
                if($idp == "pri"){
                    $
                    $idmovimientoproductointermedio = "mpi-".$numeromovpi;
                    //                    $sql[] = getSqlNewMovimientoproducto($idmovimientoproducto, $id, $cantidad, 0, 0, $costounitario, $costounitario*$cantidad, 0, 0, $fecha, $hora, "Produccion de proceso", $numeromovpro, $cantidad, $idproduccionproceso, $return);
                    $sql[] =getSqlNewMovimientoproductointermedio($idmovimientoproductointermedio, $id, $cantidad, 0, 0, $cantidad, $idproduccionproceso, "Produccion de Procesos", $numeromovpi, $fecha,$hora,$return);
                    $numeromovpi++;
                    $movimiento[$j]["id"] = $id;
                    $movimiento[$j]["fecha"] = $fecha;
                    $movimiento[$j]["hora"] = $hora;
                    $movimiento[$j]["cantidad"] = $cantidad;
                    $movimiento[$j]["tipo"] = "pri";

                }
                $movimiento[$j]["tipo2"] = "recepcion";

            }


        }
        sleep(1);

    }else
    {
        $dev['mensaje'] = "Ya fue Completado el esa produccion de proceso";
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    //    MostrarConsulta($sql);exit();
    //    despues de ejecutar Consultas

    //    exit();
    if(ejecutarConsultaSQLBeginCommit($sql))
    {
        for($k=0;$k<count($movimiento);$k++){
            $idp =  $movimiento[$k]['tipo'];
            $movimiento[$k]['id'];
            $tipo2 = $movimiento[$k]['tipo2']." $k";
            if($idp == "mtp"){
                if($tipo2 == "entrega"){
                    MovimientoKardexMateriaPrimaSalida($movimiento[$k]['id'], $movimiento[$k]['cantidad'],$movimiento[$k]['fecha'],$movimiento[$k]['hora'], $numerooperacion, $idproduccionproceso, false);
                }
                actualizarSaldoMovimientoKardexMateriaPrima($movimiento[$k]['id'],$movimiento[$k]['fecha'],$movimiento[$k]['hora'], false);

            }
            if($idp == "prd"){
                if($tipo2 == "entrega"){
                    MovimientoProductoSalida($movimiento[$k]['id'], $movimiento[$k]['cantidad'],$movimiento[$k]['fecha'],$movimiento[$k]['hora'], $numerooperacion, $idproduccionproceso, false);
                }
                actualizarSaldoMovimientoProducto($movimiento[$k]['id'],$movimiento[$k]['fecha'],$movimiento[$k]['hora'], false);
            }
            if($idp == "pri"){
                if($tipo2 =="entrega"){
                    MovimientoProductoIntermedioSalida($movimiento[$k]['id'], $movimiento[$k]['cantidad'],$movimiento[$k]['fecha'],$movimiento[$k]['hora'], $numerooperacion, $idproduccionproceso, false);


                }
                actualizarSaldoMovimientoProductoIntermedio($movimiento[$k]['id'],$movimiento[$k]['fecha'],$movimiento[$k]['hora'], false);

            }
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

function getSqlDeleteProduccionprocesos($idproduccionprocesos){
    return "DELETE FROM produccionprocesos WHERE idproduccionproceso ='$idproduccionprocesos';";
}
?>