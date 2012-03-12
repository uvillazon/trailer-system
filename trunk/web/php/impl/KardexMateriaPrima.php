<?php
function getSqlNewKardexmateriaprima($idkardexmateriaprima, $idmateriaprima, $fechamodificacion, $saldocantidad, $saldobs, $cantidad, $precio1bs, $precio1sus, $costounitario, $precio2bs, $precio2sus, $numero, $return){
    $setC[0]['campo'] = 'idkardexmateriaprima';
    $setC[0]['dato'] = $idkardexmateriaprima;
    $setC[1]['campo'] = 'idmateriaprima';
    $setC[1]['dato'] = $idmateriaprima;
    $setC[2]['campo'] = 'fechamodificacion';
    $setC[2]['dato'] = $fechamodificacion;
    $setC[3]['campo'] = 'saldocantidad';
    $setC[3]['dato'] = $saldocantidad;
    $setC[4]['campo'] = 'saldobs';
    $setC[4]['dato'] = $saldobs;
    $setC[5]['campo'] = 'cantidad';
    $setC[5]['dato'] = $cantidad;
    $setC[6]['campo'] = 'precio1bs';
    $setC[6]['dato'] = $precio1bs;
    $setC[7]['campo'] = 'precio1sus';
    $setC[7]['dato'] = $precio1sus;
    $setC[8]['campo'] = 'costounitario';
    $setC[8]['dato'] = $costounitario;
    $setC[9]['campo'] = 'precio2bs';
    $setC[9]['dato'] = $precio2bs;
    $setC[10]['campo'] = 'precio2sus';
    $setC[10]['dato'] = $precio2sus;
    $setC[11]['campo'] = 'numero';
    $setC[11]['dato'] = $numero;
    $sql2 = generarInsertValues($setC);
    return "INSERT INTO kardexmateriaprima ".$sql2;
}

function getSqlUpdateKardexmateriaprima($idkardexmateriaprima,$idmateriaprima,$fechamodificacion,$saldocantidad,$saldobs,$cantidad,$precio1bs,$precio1sus,$costounitario,$precio2bs,$precio2sus,$numero, $return){
    $setC[0]['campo'] = 'fechamodificacion';
    $setC[0]['dato'] = $fechamodificacion;
    $setC[1]['campo'] = 'saldocantidad';
    $setC[1]['dato'] = $saldocantidad;
    $setC[2]['campo'] = 'saldobs';
    $setC[2]['dato'] = $saldobs;
    $setC[3]['campo'] = 'cantidad';
    $setC[3]['dato'] = $cantidad;
    $setC[4]['campo'] = 'precio1bs';
    $setC[4]['dato'] = $precio1bs;
    $setC[5]['campo'] = 'precio1sus';
    $setC[5]['dato'] = $precio1sus;
    $setC[6]['campo'] = 'costounitario';
    $setC[6]['dato'] = $costounitario;
    $setC[7]['campo'] = 'precio2bs';
    $setC[7]['dato'] = $precio2bs;
    $setC[8]['campo'] = 'precio2sus';
    $setC[8]['dato'] = $precio2sus;
    $setC[9]['campo'] = 'numero';
    $setC[9]['dato'] = $numero;

    $set = generarSetsUpdate($setC);
    $wher[0]['campo'] = 'idkardexmateriaprima';
    $wher[0]['dato'] = $idkardexmateriaprima;
    $wher[1]['campo'] = 'idmateriaprima';
    $wher[1]['dato'] = $idmateriaprima;

    $where = generarWhereUpdate($wher);
    return "UPDATE kardexmateriaprima SET ".$set." WHERE ".$where;
}
function getSqlDeleteKardexmateriaprima($idkardexmateriaprima){
    return "DELETE FROM kardexmateriaprima WHERE idkardexmateriaprima ='$idkardexmateriaprima';";
}
?>