<?php
function ListarProductoIntermedioIdNombreUnidad($start, $limit, $sort, $dir, $callback, $_dc, $numeroorden, $return = false){

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

    $sql ="
SELECT
  pri.idproductointermedio AS id,
  pri.detalle,
  pri.unidad
FROM
  productointermedios pri
WHERE
  pri.numeroorden = '$numeroorden' $order
";
    //        echo $sql;
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


function GuardarNuevoProductoIntermedio($callback, $_dc, $where = '', $return = false){
    $nombre = $_GET['nombre'];
    $codigo = $_GET['codigo'];
    $tela=$_GET['tela'];
    $idgrupo = $_GET['grupo'];
    $idcategoriaproducto = $_GET['categoria'];
    $imagen = $_GET['imagen'];
    $descripcion = $_GET['descripcion'];
    $medidas =$_GET['medidas'];
    $numeroA = findUltimoID("productos", "numero", true);
    $numero = $numeroA['resultado']+1;
    $idproducto = "prd-".$numero;
    $codigoA =  validarText($codigo, true);
    if($codgoA["error"]==true){
        $dev['mensaje'] = "Error en el campo codigo: ".$codigoA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $nombreA = validarText($nombre, true);
    if($nombreA["error"]==false){
        $dev['mensaje'] = "Error en el campo nombre: ".$nombreA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $grupoA = validarText($idgrupo, true);
    if($grupoA["error"]==false){
        $dev['mensaje'] = "Error en el campo grupo: ".$grupoA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $categoriaA = validarText($idcategoriaproducto, true);
    if($categoriaA["error"]==false){
        $dev['mensaje'] = "Error en el campo categoria: ".$categoriaA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    // $estado = "Activo";
    $sql[] = getSqlNewProductos($idproducto, $nombre, $codigo, $imagen, $medidas, $tela, $idgrupo, $idcategoriaproducto, $costo, $precio, $numero, $descripcion, $return);

    //    MostrarConsulta($sql);
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

function getSqlNewProductointermedios($idproductointermedio, $numeroorden, $idproduccionproceso, $detalle, $unidad, $saldocantidad, $numero, $return){
    $setC[0]['campo'] = 'idproductointermedio';
    $setC[0]['dato'] = $idproductointermedio;
    $setC[1]['campo'] = 'numeroorden';
    $setC[1]['dato'] = $numeroorden;
    $setC[2]['campo'] = 'idproduccionproceso';
    $setC[2]['dato'] = $idproduccionproceso;
    $setC[3]['campo'] = 'detalle';
    $setC[3]['dato'] = $detalle;
    $setC[4]['campo'] = 'unidad';
    $setC[4]['dato'] = $unidad;
    $setC[5]['campo'] = 'saldocantidad';
    $setC[5]['dato'] = $saldocantidad;
    $setC[6]['campo'] = 'numero';
    $setC[6]['dato'] = $numero;
    $sql2 = generarInsertValues($setC);
    return "INSERT INTO productointermedios ".$sql2;
}


function getSqlDeleteProductointermedios($idproductointermedios){
    return "DELETE FROM productointermedios WHERE idproductointermedio ='$idproductointermedios';";
}
function getSqlDeleteProductointermediosProduccionProceso($idproduccionproceso){
    return "DELETE FROM productointermedios WHERE idproduccionproceso ='$idproduccionproceso';";
}

function actualizarSaldoMovimientoProductoIntermedio($idproductointermedio, $fecha,$hora = '00:00:01' ,$return = false ){
    $sql="
SELECT COALESCE(mk.saldo, 0) AS saldo
FROM `movimientoproductointermedio` mk
WHERE mk.`idproductointermedio` = '$idproductointermedio' AND
TIMESTAMP(mk.fecha , mk.hora) < TIMESTAMP ('$fecha','$hora')
ORDER BY mk.fecha DESC, mk.hora DESC LIMIT 1
";
    $saldo = findBySqlReturnCampoUnique($sql, true, true, "saldo");
//    $saldobs = findBySqlReturnCampoUnique($sql, true, true, "saldobs");
    $sql1 = "
SELECT * FROM  `movimientoproductointermedio` mk
 WHERE mk.`idproductointermedio` = '$idproductointermedio' AND
TIMESTAMP(mk.fecha,mk.hora) >= TIMESTAMP ('$fecha','$hora')
order by mk.fecha asc , mk.hora asc ;
";
    //                    echo $sql1."<br />";
    //                    echo $sql."<br />";
    $link=new BD;
    $link->conectar();
    $res=$link->consulta($sql1);
    $saldoActual = $saldo['resultado'];
//    $saldoActualBs = $saldobs['resultado'];
    while ($res1=mysql_fetch_array($res)){
        $saldoActual = $saldoActual + $res1['entrada'] - $res1['salida'];
//        $saldoActualBs = $saldoActualBs  + $res1['ingreso'] - $res1['egreso'];
        $idmovimientokardexmateriaprima = $res1['idmovimientoproductointermedio'];
        $sqlA[]="UPDATE movimientoproductointermedio SET saldo = '$saldoActual' WHERE idmovimientoproductointermedio = '$idmovimientokardexmateriaprima' AND idproducto = '$idproductointermedio'";
    }
    $sqlA[]="UPDATE productointermedios SET saldocantidad = '$saldoActual'WHERE idproductointermedio = '$idproductointermedio'";

    if($return == true)
    {
        return $sqlA;
    }
    else

    {
        //          MostrarConsulta($sqlA);
        if(ejecutarConsultaSQLBeginCommit($sqlA))
        {
            $dev['mensaje'] = "Se guardo una transaccion correctamente";
            $dev['error'] = "true";
            $dev['resultado'] = "";
        }
        else
        {
            $dev['mensaje'] = "Ocurrio un error al guardar una transaccion";
            $dev['error'] = "false";
            $dev['resultado'] = "";
        }

    }
    //            if($return == true)
    //            {
    //                return $dev;
    //            }
    //            else
    //            {
    //                $json = new Services_JSON();
    //                $output = $json->encode($dev);
    //                print($output);
    //            }


}


function MovimientoProductoIntermedioSalida($idproductointermedio,$cantidad, $fecha,$hora = '00:00:01' ,$numerooperacion ,$operacion,$return = false ){
    $sql="
SELECT
  mk.saldocantidad,
  mk.idmovimientoproductointermedio AS idmovimientoproducto
FROM
  `movimientoproductointermedio` mk 
WHERE
  mk.`idproductointermedio` = '$idproductointermedio' AND
  mk.aldocantidad > 0
ORDER BY
  mk.fecha DESC,
  mk.hora DESC
LIMIT 1
";

    $saldocantidadA = findBySqlReturnCampoUnique($sql, true, true, "saldocantidad");
    $saldocantidad = $saldocantidadA['resultado'];
    //    echo $saldocantidad."saldo";

//    $preciounitarioA = findBySqlReturnCampoUnique($sql, true, true, "costounitario");
//
//    $preciounitario = $preciounitarioA['resultado'];
    //    echo $preciounitario."p/u";;
    $idmovimientoA  = findBySqlReturnCampoUnique($sql, true, true, 'idmovimientoproducto');

    $idmovimiento = $idmovimientoA['resultado'];
    //    echo $idmovimiento;
    //    echo $salida."salida";;
    if($cantidad <= $saldocantidad){
        //        echo $salida;
        //        echo $saldocantidad;
        $idmovimientokardexA =findUltimoID('movimientoproductointermedio', 'numero', true);
        $numeromk = $idmovimientokardexA['resultado']+1;
        $idmovimientokardex = "mpi-".$numeromk;
        //        echo $preciounitario;
//        $egreso = $preciounitario * $cantidad;
        //        $sqlA[] = getSqlNewMovimientoproducto($idmovimientoproducto, $idproducto, $entrada, $salida, $saldo, $costounitario, $ingreso, $egreso, $saldobs, $fecha, $hora, $descripcion, $numero, $saldocantidad, $operacion, $return);
        $sqlA[]=getSqlNewMovimientoproductointermedio($idmovimientokardex, $idproductointermedio, 0, $cantidad, 0, 0, $operacion, "Salida de Producto Intermedio #:".$numerooperacion, $numeromk, $fecha, $hora, $return);
//        $sqlA[]=getSqlNewMovimientoproducto($idmovimientokardex, $idproductointermedio, 0, $cantidad, 0, $preciounitario, 0, $egreso, 0, $fecha, $hora, "Salida  de Producto #:".$numerooperacion, $numeromk, 0, $operacion, $return);
        //        $sqlA[]=getSqlNewMovimientokardexalmacen($idmovimientokardex, $idproducto, $idalmacen, 0, $cantidad, 0, $preciounitario, 0, $egreso, 0, $fecha,  date("H:i:s"), $compra, $numeromk, 0, false);
        //        $sqlA[]=getSqlNewMovimientokardex($idmovimientokardex, $idproducto, 0, $salida, 0, 0, $egreso, 0, $compra, $fecha, $hora, $numeromk, $preciounitario, "traspaso", 0, false);
        $saldocantidad = $saldocantidad - $cantidad;
        $sqlA[] = "UPDATE movimientoproductointermedio SET saldocantidad = '$saldocantidad' WHERE idmovimientoproductointermedio = '$idmovimiento' AND idproductointermedio = '$idproductointermedio' ";


        //        $idmovimientokardexalmacen = "mka-".$numeromka;
        //
        //        $sqlA[] = getSqlNewMovimientokardexalmacen($idmovimientokardexalmacen, $idproducto, $destino, $salida, 0, 0, $preciounitario, $egreso, 0, 0, $fecha, $hora, $compra, $numeromka, $salida,false);


        //                MostrarConsulta($sqlA);
        ejecutarConsultaSQLBeginCommit($sqlA);


    }
    else{
        $idmovimientokardexA =findUltimoID('movimientoproductointermedio', 'numero', true);
        $numeromk = $idmovimientokardexA['resultado']+1;
        $idmovimientokardex = "mpi-".$numeromk;
        //        echo $preciounitario;
//        $egreso = $preciounitario * $saldocantidad;
        $hora = date("H:i:s");
        $sqlA1[]=getSqlNewMovimientoproductointermedio($idmovimientokardex, $idproductointermedio, 0, $cantidad, 0, 0, $operacion, "Salida de Producto Intermedio #:".$numerooperacion, $numeromk, $fecha, $hora, $return);
//        $sqlA1[]=getSqlNewMovimientoproducto($idmovimientokardex, $idproductointermedio, 0, $cantidad, 0, $preciounitario, 0, $egreso, 0, $fecha, $hora, "Salida  de Producto #:".$numerooperacion, $numeromk, 0, $operacion, $return);

        //        $sqlA1[]=getSqlNewMovimientokardexproducto($idmovimientokardex, $idkardexproducto, 0, $cantidad, 0, $costounitario, $ingreso, $egreso, $saldobs, $fecha, $hora, $descripcion, $numero, $saldocantidad, $operacion, $return);
        //        $sqlA1[]=getSqlNewMovimientokardexalmacen($idmovimientokardex, $idproducto, $idalmacen, 0, $saldocantidad, 0, $preciounitario, 0, $egreso, 0, $fecha,  date("H:i:s"), $compra, $numeromk, 0, false);

        //        $sqlA1[]=getSqlNewMovimientokardex($idmovimientokardex, $idproducto, 0, $saldocantidad, 0, 0, $egreso, 0, $compra, $fecha, $hora, $numeromk, $preciounitario, "traspaso", 0, false);






        //        $idmovimientokardexalmacen = "mka-".$numeromka;
        //
        //        $sqlA1[] = getSqlNewMovimientokardexalmacen($idmovimientokardexalmacen, $idproducto, $destino, $saldocantidad, 0, 0, $preciounitario, $egreso, 0, 0, $fecha, $hora, $compra, $numeromka, $saldocantidad,false);
        $cantidad = $cantidad - $saldocantidad;
        $saldocantidad = 0;
        $sqlA1[] = "UPDATE movimientoproductointermedio SET saldocantidad = '$saldocantidad' WHERE idmovimientoproductointermedio = '$idmovimiento' AND idproductointermedio = '$idproductointermedio'";


        //        MostrarConsulta($sqlA1);
        ejecutarConsultaSQLBeginCommit($sqlA1);
        sleep(1);
        MovimientoProductoIntermedioSalida($idproductointermedio, $cantidad, $fecha, $hora, $numerooperacion, $operacion, $return);
        //        salidamovientomoventaalmacen($idalmacen,$idproducto,$cantidad ,$fecha,$hora ,$compra ,false );


    }

}
?>