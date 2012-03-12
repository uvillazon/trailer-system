<?php
function getSqlNewOpBordado($idopbordado, $idop, $idproducto, $op, $iddetalleorden, $observacion, $fecha, $numero, $return){
    $setC[0]['campo'] = 'idopbordado';
    $setC[0]['dato'] = $idopbordado;
    $setC[1]['campo'] = 'idop';
    $setC[1]['dato'] = $idop;
    $setC[2]['campo'] = 'idproducto';
    $setC[2]['dato'] = $idproducto;
    $setC[3]['campo'] = 'op';
    $setC[3]['dato'] = $op;
    $setC[4]['campo'] = 'iddetalleorden';
    $setC[4]['dato'] = $iddetalleorden;
    $setC[5]['campo'] = 'observacion';
    $setC[5]['dato'] = $observacion;
    $setC[6]['campo'] = 'fecha';
    $setC[6]['dato'] = $fecha;
    $setC[7]['campo'] = 'numero';
    $setC[7]['dato'] = $numero;
    $sql2 = generarInsertValues($setC);
    return "INSERT INTO OpBordado ".$sql2;
}
function GuardarEnlazeBordadoOp($callback, $_dc, $idproducto, $return = false){
  
    $op = $_GET['op'];
    $item = $_GET['items'];

    $nombreA = validarText($item, true);
    if($nombreA["error"]==false){
        $dev['mensaje'] = "Error en el campo Items: ".$nombreA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }

    $empresaA = validarText($op, true);
    if($empresaA["error"]==false){
        $dev['mensaje'] = "Error en el campo Op: ".$empresaA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $opA1 = verificarValidarText($op, true, "ordenproduccion", "numeroorden");
    if($opA1["error"]==false){
        $dev['mensaje'] = "Error en el campo Op: ".$opA1['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $numeroA = findUltimoID("opbordado", "numero", true);
    $numero = $numeroA['resultado']+1;
    $idopbordado = 'opb-'.$numero;
    $observacion = $_GET['observacionop'];
    $fecha = date("Y-m-d");
    $sqlop = "
SELECT
  `ord`.idordenproduccion
FROM
  `ordenproduccion` `ord`
WHERE
  `ord`.numeroorden = '$op'
";
    $sqlop;
    $idopA = findBySqlReturnCampoUnique($sqlop, true, true, "idordenproduccion");
    $idop = $idopA['resultado'];
    $sql[] = getSqlNewOpBordado($idopbordado, $idop, $idproducto, $op, $item, $observacion, $fecha, $numero, $return);
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

?>