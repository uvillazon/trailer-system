<?php
function getSqlNewMovimientokardexmateriaprima($idmovimientokardexmateriaprima, $idmateriaprima, $entrada, $salida, $saldo, $costounitario, $ingreso, $egreso, $saldobs, $fecha, $hora, $descripcion, $numero, $saldocantidad,$operacion, $id, $return){
    $setC[0]['campo'] = 'idmovimientokardexmateriaprima';
    $setC[0]['dato'] = $idmovimientokardexmateriaprima;
    $setC[1]['campo'] = 'idmateriaprima';
    $setC[1]['dato'] = $idmateriaprima;
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
    $setC[15]['campo'] = 'id';
    $setC[15]['dato'] = $id;

    $sql2 = generarInsertValues($setC);
    return "INSERT INTO movimientokardexmateriaprima ".$sql2;
}
function getSqlUpdateMovimientokardexmateriaprima($idmovimientokardexmateriaprima,$idmateriaprima,$entrada,$salida,$saldo,$costounitario,$ingreso,$egreso,$saldobs,$fecha,$hora,$descripcion,$numero,$saldocantidad, $return){
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
    $setC[12]['campo'] = 'idmateriaprima';
    $setC[12]['dato'] = $idmateriaprima;

    $set = generarSetsUpdate($setC);
    $wher[0]['campo'] = 'idmovimientokardexmateriaprima';
    $wher[0]['dato'] = $idmovimientokardexmateriaprima;


    $where = generarWhereUpdate($wher);
    return "UPDATE movimientokardexmateriaprima SET ".$set." WHERE ".$where;
}




function getSqlDeleteMovimientokardexmateriaprima($idmovimientokardexmateriaprima){
    return "DELETE FROM movimientokardexmateriaprima WHERE idmovimientokardexmateriaprima ='$idmovimientokardexmateriaprima';";
}
function getSqlDeleteMovimientokardexmateriaprimaPorIdMateriaPrima($idmateriaprima){
    return "DELETE FROM movimientokardexmateriaprima WHERE idmateriaprima ='$idmateriaprima';";
}
function getSqlDeleteMovimientokardexmateriaprimaPorOperacion($idoperacion){
    return "DELETE FROM movimientokardexmateriaprima WHERE operacion ='$idoperacion';";
}
?>