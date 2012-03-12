<?php
function getSqlNewCotizacion($idproducto, $costounitario, $preciounitario, $cantidad, $utilidad, $utilidadtotal, $preciototal, $observacion, $return){
    $setC[0]['campo'] = 'idproducto';
    $setC[0]['dato'] = $idproducto;
    $setC[1]['campo'] = 'costounitario';
    $setC[1]['dato'] = $costounitario;
    $setC[2]['campo'] = 'preciounitario';
    $setC[2]['dato'] = $preciounitario;
    $setC[3]['campo'] = 'cantidad';
    $setC[3]['dato'] = $cantidad;
    $setC[4]['campo'] = 'utilidad';
    $setC[4]['dato'] = $utilidad;
    $setC[5]['campo'] = 'utilidadtotal';
    $setC[5]['dato'] = $utilidadtotal;
    $setC[6]['campo'] = 'preciototal';
    $setC[6]['dato'] = $preciototal;
    $setC[7]['campo'] = 'observacion';
    $setC[7]['dato'] = $observacion;
    $sql2 = generarInsertValues($setC);
    return "INSERT INTO cotizacion ".$sql2;
}


function getSqlUpdateCotizacion($idproducto,$costounitario,$preciounitario,$cantidad,$utilidad,$utilidadtotal,$preciototal,$observacion, $return){
    $setC[0]['campo'] = 'costounitario';
    $setC[0]['dato'] = $costounitario;
    $setC[1]['campo'] = 'preciounitario';
    $setC[1]['dato'] = $preciounitario;
    $setC[2]['campo'] = 'cantidad';
    $setC[2]['dato'] = $cantidad;
    $setC[3]['campo'] = 'utilidad';
    $setC[3]['dato'] = $utilidad;
    $setC[4]['campo'] = 'utilidadtotal';
    $setC[4]['dato'] = $utilidadtotal;
    $setC[5]['campo'] = 'preciototal';
    $setC[5]['dato'] = $preciototal;
    $setC[6]['campo'] = 'observacion';
    $setC[6]['dato'] = $observacion;

    $set = generarSetsUpdate($setC);
    $wher[0]['campo'] = 'idproducto';
    $wher[0]['dato'] = $idproducto;

    $where = generarWhereUpdate($wher);
    return "UPDATE cotizacion SET ".$set." WHERE ".$where;
}




function getSqlDeleteCotizacion($idcotizacion){
    return "DELETE FROM cotizacion WHERE idproducto ='$idcotizacion';";
}
?>