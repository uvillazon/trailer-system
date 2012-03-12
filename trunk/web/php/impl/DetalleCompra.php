<?php
function getSqlNewDetallecompra($idcompra, $iddetallecompra, $detalle, $idmateriaprima, $unidad, $cantidad, $preciounitario, $preciototal, $numero, $op, $return){
    $setC[0]['campo'] = 'idcompra';
    $setC[0]['dato'] = $idcompra;
    $setC[1]['campo'] = 'iddetallecompra';
    $setC[1]['dato'] = $iddetallecompra;
    $setC[2]['campo'] = 'detalle';
    $setC[2]['dato'] = $detalle;
    $setC[3]['campo'] = 'idmateriaprima';
    $setC[3]['dato'] = $idmateriaprima;
    $setC[4]['campo'] = 'unidad';
    $setC[4]['dato'] = $unidad;
    $setC[5]['campo'] = 'cantidad';
    $setC[5]['dato'] = $cantidad;
    $setC[6]['campo'] = 'preciounitario';
    $setC[6]['dato'] = $preciounitario;
    $setC[7]['campo'] = 'preciototal';
    $setC[7]['dato'] = $preciototal;
    $setC[8]['campo'] = 'numero';
    $setC[8]['dato'] = $numero;
    $setC[9]['campo'] = 'op';
    $setC[9]['dato'] = $op;
    $sql2 = generarInsertValues($setC);
    return "INSERT INTO detallecompra ".$sql2;
}
function getSqlUpdateDetallecompra($idcompra,$iddetallecompra,$detalle,$idmateriaprima,$unidad,$cantidad,$preciounitario,$preciototal,$numero, $return){
    $setC[0]['campo'] = 'detalle';
    $setC[0]['dato'] = $detalle;
    $setC[1]['campo'] = 'unidad';
    $setC[1]['dato'] = $unidad;
    $setC[2]['campo'] = 'cantidad';
    $setC[2]['dato'] = $cantidad;
    $setC[3]['campo'] = 'preciounitario';
    $setC[3]['dato'] = $preciounitario;
    $setC[4]['campo'] = 'preciototal';
    $setC[4]['dato'] = $preciototal;
    $setC[5]['campo'] = 'numero';
    $setC[5]['dato'] = $numero;
    $wher[6]['campo'] = 'iddetallecompra';
    $wher[6]['dato'] = $iddetallecompra;
    $wher[7]['campo'] = 'idmateriaprima';
    $wher[7]['dato'] = $idmateriaprima;

    $set = generarSetsUpdate($setC);
    $wher[0]['campo'] = 'idcompra';
    $wher[0]['dato'] = $idcompra;


    $where = generarWhereUpdate($wher);
    return "UPDATE detallecompra SET ".$set." WHERE ".$where;
}
function getSqlDeleteDetallecompra($iddetallecompra){
    return "DELETE FROM detallecompra WHERE iddetallecompra ='$iddetallecompra';";
}
function getSqlDeleteDetallecompraIdCompra($idcompra){
    return "DELETE FROM detallecompra WHERE idcompra ='$idcompra';";
}
?>