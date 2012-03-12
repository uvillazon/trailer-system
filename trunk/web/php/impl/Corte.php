<?php
function CargarPanelCorte($callback, $_dc, $numeroorden,$return = false){

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
    $sqlmpcompra = "
SELECT
  det.idmateriaprima AS id,
  det.detalle AS nombre
FROM
  `detallecompra` det
WHERE
  det.op = $numeroorden
";
    $mpcompraA = getTablaToArrayOfSQL($sqlmpcompra);
    $value['mpcompraM'] = $mpcompraA['resultado'];

    $mpalmacenA =ListarMateriaPrimaIdDetalleTela($start, $limit, $sort, $dir, $callback, $_dc, $where, true);
    $value['mpalmacenM'] = $mpalmacenA['resultado'];



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

function CargarMolde($callback, $_dc, $id,$return = false){

    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";


    $sqlitems = "
SELECT
  mol.idmolde AS id,
  CONCAT(mol.codigo,' ',mol.nombre) as nombre
FROM
  `detalleorden` det,
  `productos` pro,
  `categoriaproducto` cat,
  `moldes` mol
WHERE
  det.iddetalleorden = '$id' AND
  det.idproducto = pro.idproducto AND
  pro.idcategoriaproducto = cat.idcategoriaproducto AND
  cat.idcategoriaproducto = mol.idcategoria
";
    //"id", "idtela","idcorte", "uds", "nombrei", "nombret","nombrec", "tela", "hoja", "uds1", "total", "totalunidades"
    $sqldetalle = "
SELECT
  cor.idcorte AS id,
  cor.idtela,
  cor.idmolde AS idcorte,
  det.cantidad AS uds,
  cor.nombrei,
  cor.nombret,
  cor.nombrem AS nombrec,
  cor.tela,
  cor.hoja,
  cor.uds As uds1,
  cor.totaltela AS total,
  cor.totalunidades
FROM
  cortes cor,
  `detalleorden` det
WHERE
  cor.iddetalleop = '$id' AND
  cor.iddetalleop = det.iddetalleorden
";
    $itemsA = getTablaToArrayOfSQL($sqlitems);
    $detalleA = getTablaToArrayOfSQL($sqldetalle);

    $value['moldeM'] = $itemsA['resultado'];

    $value['detalleM'] = $detalleA['resultado'];
    if($itemsA['error']=="true"){
        $dev['mensaje'] = "Existen resultados";
        $dev['error']   = "true";
        $dev['resultado'] = $value;
    }
    else{
        $dev['mensaje'] = "No Existen resultados";
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

function getSqlNewCortes($idcorte, $op, $iddetalleop, $idtela, $idmolde, $nombrei, $nombret, $nombrem, $tela, $hoja, $uds, $totaltela, $totalunidades, $numero, $idusuario, $fecha, $return){
    $setC[0]['campo'] = 'idcorte';
    $setC[0]['dato'] = $idcorte;
    $setC[1]['campo'] = 'op';
    $setC[1]['dato'] = $op;
    $setC[2]['campo'] = 'iddetalleop';
    $setC[2]['dato'] = $iddetalleop;
    $setC[3]['campo'] = 'idtela';
    $setC[3]['dato'] = $idtela;
    $setC[4]['campo'] = 'idmolde';
    $setC[4]['dato'] = $idmolde;
    $setC[5]['campo'] = 'nombrei';
    $setC[5]['dato'] = $nombrei;
    $setC[6]['campo'] = 'nombret';
    $setC[6]['dato'] = $nombret;
    $setC[7]['campo'] = 'nombrem';
    $setC[7]['dato'] = $nombrem;
    $setC[8]['campo'] = 'tela';
    $setC[8]['dato'] = $tela;
    $setC[9]['campo'] = 'hoja';
    $setC[9]['dato'] = $hoja;
    $setC[10]['campo'] = 'uds';
    $setC[10]['dato'] = $uds;
    $setC[11]['campo'] = 'totaltela';
    $setC[11]['dato'] = $totaltela;
    $setC[12]['campo'] = 'totalunidades';
    $setC[12]['dato'] = $totalunidades;
    $setC[13]['campo'] = 'numero';
    $setC[13]['dato'] = $numero;
    $setC[14]['campo'] = 'idusuario';
    $setC[14]['dato'] = $idusuario;
    $setC[15]['campo'] = 'fecha';
    $setC[15]['dato'] = $fecha;
    $sql2 = generarInsertValues($setC);
    return "INSERT INTO cortes ".$sql2;
}
function getSqlDeleteCortes($idcortes){
    return "DELETE FROM cortes WHERE idcorte ='$idcortes';";
}
function GuardarNuevaCorte($callback, $_dc, $resultado,$return = false){
    $dev['mensaje'] = "";
    $dev['error']   = "false";
    $dev['resultado'] = "";

    $corte = $resultado->corte;
    $op = $corte->op;
    $ordenA = verificarValidarText($op, true, "ordenproduccion", "numeroorden");
    if($ordenA["error"]==false){
        $dev['mensaje'] = "Error en el campo No Orden de Produccion ".$ordenA["mensaje"];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }




    $idusuario = $_SESSION['idusuario'];
    $numeroA = findUltimoID("cortes", "numero", true);
    $numero = $numeroA['resultado']+1;

    $fecha = date("Y-m-d");
    $hora = date("H:i:s");
    $detalles = $resultado->detalles;
    if(count($detalles)>0){


        for($i=0;$i<count($detalles);$i++){
            $detalle1 = $detalles[$i];
            $iddetalleop = $detalle1->id;

            $id = substr($iddetalleop,0,3);

            $idtela = $detalle1->idtela;
            $idmolde = $detalle1->idcorte;
            $nombrei = $detalle1->nombrei;
            $nombret = $detalle1->nombret;
            $nombrem = $detalle1->nombrec;
            $tela = $detalle1->tela;
            $hoja = $detalle1->hoja;
            $uds = $detalle1->uds1;
            $totaltela = $detalle1->total;
            $totalunidades = $detalle1->totalunidades;
            $idcorte = 'cor-'.$numero;
            $sql1 = "
SELECT
  k.saldocantidad
FROM
  kardexmateriaprima k
WHERE
  k.idmateriaprima = '$idtela'
";
            $cantidadventaA1 = findBySqlReturnCampoUnique($sql1, true, true, "saldocantidad");
            $cantidadventa1 = $cantidadventaA1['resultado'];
            if($totaltela > $cantidadventa1){
                $dev['mensaje'] = "No tiene suficiente Stock : ".$nombret." Cantidad Disponible :".$cantidadventa1;
                $json = new Services_JSON();
                $output = $json->encode($dev);
                print($output);
                exit;
            }

            if($id=="deo"){

                $sql[] = getSqlNewCortes($idcorte, $op, $iddetalleop, $idtela, $idmolde, $nombrei, $nombret, $nombrem, $tela, $hoja, $uds, $totaltela, $totalunidades, $numero, $idusuario, $fecha, $return);
                //            $sql[] = getSqlNewDetalleorden($iddetalleorden, $idordenproduccion, $id, $detalle, $unidad, $preciounitario, $cantidad, $preciototal, $numerodetalle, $cantidad, $return);

            }
            else{
                $sql[] = getSqlDeleteCortes($iddetalleop);
                $sqlid = "
SELECT 
  cor.iddetalleop
FROM
  cortes cor
WHERE
  cor.idcorte = '$iddetalleop'
";
                $idA = findBySqlReturnCampoUnique($sqlid, true, true, "iddetalleop");
                $idA1 = $idA['resultado'];
                $sql[] = getSqlNewCortes($idcorte, $op, $idA1, $idtela, $idmolde, $nombrei, $nombret, $nombrem, $tela, $hoja, $uds, $totaltela, $totalunidades, $numero, $idusuario, $fecha, $return);
                //echo "hola";
                revertirMovimientoMateriaPrima($iddetalleop,true);
                //                exit();

            }
            $movimiento[$i]["id"] = $idtela;
            $movimiento[$i]["fecha"] = $fecha;
            $movimiento[$i]["hora"] = $hora;
            $movimiento[$i]["cantidad"] = $totaltela;
            MovimientoKardexMateriaPrimaSalida($movimiento[$i]['id'], $movimiento[$i]['cantidad'],$movimiento[$i]['fecha'],$movimiento[$i]['hora'], "Proceso Corte OP".$op, $idcorte, false);

            $numero++;



        }
    }
    else{
        $dev['mensaje'] = "Error Debe de ingresar algun producto";
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    //     exit();
    //                MostrarConsulta($sql);exit();
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

function eliminarItemCorte($callback, $_dc, $idcorte,$return = false){
    $dev['mensaje'] = "";
    $dev['error']   = "";

    //    $sql[] = getSqlDeleteLinea_marca($idlinea);
    $sql[] = getSqlDeleteCortes($idcorte);
    revertirMovimientoMateriaPrima($idcorte,true);
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
?>