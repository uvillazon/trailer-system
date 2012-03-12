<?php
function getSqlNewMovimientoproducto($idmovimientoproducto, $idproducto, $entrada, $salida, $saldo, $costounitario, $ingreso, $egreso, $saldobs, $fecha, $hora, $descripcion, $numero, $saldocantidad, $operacion, $return){
    $setC[0]['campo'] = 'idmovimientoproducto';
    $setC[0]['dato'] = $idmovimientoproducto;
    $setC[1]['campo'] = 'idproducto';
    $setC[1]['dato'] = $idproducto;
    $setC[2]['campo'] = 'entrada';
    $setC[2]['dato'] = $entrada;
    $setC[3]['campo'] = 'salida';
    $setC[3]['dato'] = $salida;
    $setC[4]['campo'] = 'saldo';
    $setC[4]['dato'] = $saldo;
    $setC[5]['campo'] = 'costounitario';
    $setC[5]['dato'] = $costounitario;
    $setC[6]['campo'] = 'ingreso';
    $setC[6]['dato'] = $ingreso;
    $setC[7]['campo'] = 'egreso';
    $setC[7]['dato'] = $egreso;
    $setC[8]['campo'] = 'saldobs';
    $setC[8]['dato'] = $saldobs;
    $setC[9]['campo'] = 'fecha';
    $setC[9]['dato'] = $fecha;
    $setC[10]['campo'] = 'hora';
    $setC[10]['dato'] = $hora;
    $setC[11]['campo'] = 'descripcion';
    $setC[11]['dato'] = $descripcion;
    $setC[12]['campo'] = 'numero';
    $setC[12]['dato'] = $numero;
    $setC[13]['campo'] = 'saldocantidad';
    $setC[13]['dato'] = $saldocantidad;
    $setC[14]['campo'] = 'operacion';
    $setC[14]['dato'] = $operacion;
    $sql2 = generarInsertValues($setC);
    return "INSERT INTO movimientoproducto ".$sql2;
}


function getSqlUpdateMovimientoproducto($idmovimientoproducto,$idproducto,$entrada,$salida,$saldo,$costounitario,$ingreso,$egreso,$saldobs,$fecha,$hora,$descripcion,$numero,$saldocantidad,$operacion, $return){
    $setC[0]['campo'] = 'entrada';
    $setC[0]['dato'] = $entrada;
    $setC[1]['campo'] = 'salida';
    $setC[1]['dato'] = $salida;
    $setC[2]['campo'] = 'saldo';
    $setC[2]['dato'] = $saldo;
    $setC[3]['campo'] = 'costounitario';
    $setC[3]['dato'] = $costounitario;
    $setC[4]['campo'] = 'ingreso';
    $setC[4]['dato'] = $ingreso;
    $setC[5]['campo'] = 'egreso';
    $setC[5]['dato'] = $egreso;
    $setC[6]['campo'] = 'saldobs';
    $setC[6]['dato'] = $saldobs;
    $setC[7]['campo'] = 'fecha';
    $setC[7]['dato'] = $fecha;
    $setC[8]['campo'] = 'hora';
    $setC[8]['dato'] = $hora;
    $setC[9]['campo'] = 'descripcion';
    $setC[9]['dato'] = $descripcion;
    $setC[10]['campo'] = 'numero';
    $setC[10]['dato'] = $numero;
    $setC[11]['campo'] = 'saldocantidad';
    $setC[11]['dato'] = $saldocantidad;
    $setC[12]['campo'] = 'operacion';
    $setC[12]['dato'] = $operacion;

    $set = generarSetsUpdate($setC);
    $wher[0]['campo'] = 'idmovimientoproducto';
    $wher[0]['dato'] = $idmovimientoproducto;
    $wher[1]['campo'] = 'idproducto';
    $wher[1]['dato'] = $idproducto;

    $where = generarWhereUpdate($wher);
    return "UPDATE movimientoproducto SET ".$set." WHERE ".$where;
}




function getSqlDeleteMovimientoproducto($idmovimientoproducto){
    return "DELETE FROM movimientoproducto WHERE idmovimientoproducto ='$idmovimientoproducto';";
}
?>