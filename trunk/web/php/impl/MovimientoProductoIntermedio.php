<?php
function getSqlNewMovimientoproductointermedio($idmovimientoproductointermedio, $idproductointermedio, $entrada, $salida, $saldo, $saldocantidad, $operacion, $descripcion, $numero, $fecha, $hora, $return){
    $setC[0]['campo'] = 'idmovimientoproductointermedio';
    $setC[0]['dato'] = $idmovimientoproductointermedio;
    $setC[1]['campo'] = 'idproductointermedio';
    $setC[1]['dato'] = $idproductointermedio;
    $setC[2]['campo'] = 'entrada';
    $setC[2]['dato'] = $entrada;
    $setC[3]['campo'] = 'salida';
    $setC[3]['dato'] = $salida;
    $setC[4]['campo'] = 'saldo';
    $setC[4]['dato'] = $saldo;
    $setC[5]['campo'] = 'saldocantidad';
    $setC[5]['dato'] = $saldocantidad;
    $setC[6]['campo'] = 'operacion';
    $setC[6]['dato'] = $operacion;
    $setC[7]['campo'] = 'descripcion';
    $setC[7]['dato'] = $descripcion;
    $setC[8]['campo'] = 'numero';
    $setC[8]['dato'] = $numero;
    $setC[9]['campo'] = 'fecha';
    $setC[9]['dato'] = $fecha;
    $setC[10]['campo'] = 'hora';
    $setC[10]['dato'] = $hora;
    $sql2 = generarInsertValues($setC);
    return "INSERT INTO movimientoproductointermedio ".$sql2;
}


function getSqlUpdateMovimientoproductointermedio($idmovimientoproductointermedio,$idproductointermedio,$entrada,$salida,$saldo,$saldocantidad,$operacion,$descripcion,$numero, $return){
    $setC[0]['campo'] = 'entrada';
    $setC[0]['dato'] = $entrada;
    $setC[1]['campo'] = 'salida';
    $setC[1]['dato'] = $salida;
    $setC[2]['campo'] = 'saldo';
    $setC[2]['dato'] = $saldo;
    $setC[3]['campo'] = 'saldocantidad';
    $setC[3]['dato'] = $saldocantidad;
    $setC[4]['campo'] = 'operacion';
    $setC[4]['dato'] = $operacion;
    $setC[5]['campo'] = 'descripcion';
    $setC[5]['dato'] = $descripcion;
    $setC[6]['campo'] = 'numero';
    $setC[6]['dato'] = $numero;
    $setC[7]['campo'] = 'idproductointermedio';
    $setC[7]['dato'] = $idproductointermedio;

    $set = generarSetsUpdate($setC);
    $wher[0]['campo'] = 'idmovimientoproductointermedio';
    $wher[0]['dato'] = $idmovimientoproductointermedio;

    $where = generarWhereUpdate($wher);
    return "UPDATE movimientoproductointermedio SET ".$set." WHERE ".$where;
}




function getSqlDeleteMovimientoproductointermedio($idmovimientoproductointermedio){
    return "DELETE FROM movimientoproductointermedio WHERE idmovimientoproductointermedio ='$idmovimientoproductointermedio';";
}

function getSqlDeleteMovimientoproductointermedioOperacion($operacion){
    return "DELETE FROM movimientoproductointermedio WHERE operacion ='$operacion';";
}

?>