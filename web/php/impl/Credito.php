<?php
function getSqlNewCredito($idordenproduccion, $idcliente, $idempresa, $total, $saldo, $observacion, $responsable, $fecha, $return){
    $setC[0]['campo'] = 'idordenproduccion';
    $setC[0]['dato'] = $idordenproduccion;
    $setC[1]['campo'] = 'idcliente';
    $setC[1]['dato'] = $idcliente;
    $setC[2]['campo'] = 'idempresa';
    $setC[2]['dato'] = $idempresa;
    $setC[3]['campo'] = 'total';
    $setC[3]['dato'] = $total;
    $setC[4]['campo'] = 'saldo';
    $setC[4]['dato'] = $saldo;
    $setC[5]['campo'] = 'observacion';
    $setC[5]['dato'] = $observacion;
    $setC[6]['campo'] = 'responsable';
    $setC[6]['dato'] = $responsable;
    $setC[7]['campo'] = 'fecha';
    $setC[7]['dato'] = $fecha;
    $sql2 = generarInsertValues($setC);
    return "INSERT INTO credito ".$sql2;
}


function getSqlUpdateCredito($idordenproduccion,$idcliente,$idempresa,$total,$saldo,$observacion,$responsable,$fecha, $return){
    $setC[0]['campo'] = 'total';
    $setC[0]['dato'] = $total;
    $setC[1]['campo'] = 'saldo';
    $setC[1]['dato'] = $saldo;
    $setC[2]['campo'] = 'observacion';
    $setC[2]['dato'] = $observacion;
    $setC[3]['campo'] = 'responsable';
    $setC[3]['dato'] = $responsable;
    $setC[4]['campo'] = 'fecha';
    $setC[4]['dato'] = $fecha;
    $setC[5]['campo'] = 'idcliente';
    $setC[5]['dato'] = $idcliente;
    $setC[6]['campo'] = 'idempresa';
    $setC[6]['dato'] = $idempresa;

    $set = generarSetsUpdate($setC);
    $wher[0]['campo'] = 'idordenproduccion';
    $wher[0]['dato'] = $idordenproduccion;


    $where = generarWhereUpdate($wher);
    return "UPDATE credito SET ".$set." WHERE ".$where;
}




function getSqlDeleteCredito($idcredito){
    return "DELETE FROM credito WHERE idcredito ='$idcredito';";
}

function getSqlNewDetallecredito($iddetallecredito, $idordenproduccion, $importe, $saldo, $fecha, $idusuario, $numero, $return){
    $setC[0]['campo'] = 'iddetallecredito';
    $setC[0]['dato'] = $iddetallecredito;
    $setC[1]['campo'] = 'idordenproduccion';
    $setC[1]['dato'] = $idordenproduccion;
    $setC[2]['campo'] = 'importe';
    $setC[2]['dato'] = $importe;
    $setC[3]['campo'] = 'saldo';
    $setC[3]['dato'] = $saldo;
    $setC[4]['campo'] = 'fecha';
    $setC[4]['dato'] = $fecha;
    $setC[5]['campo'] = 'idusuario';
    $setC[5]['dato'] = $idusuario;
    $setC[6]['campo'] = 'numero';
    $setC[6]['dato'] = $numero;
    $sql2 = generarInsertValues($setC);
    return "INSERT INTO detallecredito ".$sql2;
}

function getSqlDeleteDetallecredito($iddetallecredito){
    return "DELETE FROM detallecredito WHERE iddetallecredito ='$iddetallecredito';";
}
function getSqlDeleteDetallecreditoOrden($idordenproduccion){
    return "DELETE FROM detallecredito WHERE idordenproduccion ='$idordenproduccion';";
}
?>