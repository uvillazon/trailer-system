<?php
function getSqlNewDetalleproduccionproceso($iddetalleproduccionproceso, $idproduccionproceso, $detalle, $unidad, $cantidad, $id, $numero, $tipo, $return){
    $setC[0]['campo'] = 'iddetalleproduccionproceso';
    $setC[0]['dato'] = $iddetalleproduccionproceso;
    $setC[1]['campo'] = 'idproduccionproceso';
    $setC[1]['dato'] = $idproduccionproceso;
    $setC[2]['campo'] = 'detalle';
    $setC[2]['dato'] = $detalle;
    $setC[3]['campo'] = 'unidad';
    $setC[3]['dato'] = $unidad;
    $setC[4]['campo'] = 'cantidad';
    $setC[4]['dato'] = $cantidad;
    $setC[5]['campo'] = 'id';
    $setC[5]['dato'] = $id;
    $setC[6]['campo'] = 'numero';
    $setC[6]['dato'] = $numero;
    $setC[7]['campo'] = 'tipo';
    $setC[7]['dato'] = $tipo;
    $sql2 = generarInsertValues($setC);
    return "INSERT INTO detalleproduccionproceso ".$sql2;
}


function getSqlUpdateDetalleproduccionproceso($iddetalleproduccionproceso,$idproduccionproceso,$detalle,$unidad,$cantidad,$id,$numero,$tipo, $return){
    $setC[0]['campo'] = 'detalle';
    $setC[0]['dato'] = $detalle;
    $setC[1]['campo'] = 'unidad';
    $setC[1]['dato'] = $unidad;
    $setC[2]['campo'] = 'cantidad';
    $setC[2]['dato'] = $cantidad;
    $setC[3]['campo'] = 'numero';
    $setC[3]['dato'] = $numero;
    $setC[4]['campo'] = 'tipo';
    $setC[4]['dato'] = $tipo;

    $set = generarSetsUpdate($setC);
    $wher[0]['campo'] = 'iddetalleproduccionproceso';
    $wher[0]['dato'] = $iddetalleproduccionproceso;
    $wher[1]['campo'] = 'idproduccionproceso';
    $wher[1]['dato'] = $idproduccionproceso;
    $wher[2]['campo'] = 'id';
    $wher[2]['dato'] = $id;

    $where = generarWhereUpdate($wher);
    return "UPDATE detalleproduccionproceso SET ".$set." WHERE ".$where;
}




function getSqlDeleteDetalleproduccionproceso($iddetalleproduccionproceso){
    return "DELETE FROM detalleproduccionproceso WHERE iddetalleproduccionproceso ='$iddetalleproduccionproceso';";
}
function getSqlDeleteDetalleproduccionprocesoForProduccion($idproduccionproceso){
    return "DELETE FROM detalleproduccionproceso WHERE idproduccionproceso ='$idproduccionproceso';";
}
?>