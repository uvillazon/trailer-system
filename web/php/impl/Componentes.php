<?php
function BuscarComponentesPorId($callback, $_dc, $idgrupo, $return = false){

    $dev['mensaje'] = "";
    $dev['error']   = "false";
    $dev['resultado'] = "";
    //falta mofdificar sqlmatria
    $sqlmateria = "
SELECT
  mtp.idmateriaprima AS id,

  kmp.costounitario AS costo,
  uni.nombre AS unidad,
  mtp.nombre as detalle
FROM
  materiaprima mtp,
  kardexmateriaprima kmp,
  unidades uni
WHERE
  mtp.idunidad = uni.idunidad AND
  mtp.idmateriaprima = kmp.idmateriaprima order by mtp.`numero` ASC
";

    $materiaA = getTablaToArrayOfSQL($sqlmateria);
    $materia = $materiaA['resultado'];
    $value['materiaM'] = $materia;
    //  midificar segun lo ue va hacer
    //$catgrupo = BuscarCategoriaPorIdGrupo($idgrupo); aca hacer la  vaalidacion con la tabla aaaaaaaaaaaaaaa falta
    //    $sqldetalle = "
    //SELECT
    //  cpg.idcomponentegrupo as id,
    //  cpg.detalle,
    //  cpg.unidad,
    //  cpg.cantidad,
    //  cpg.precio as preciounitario,
    //  cpg.total as preciototal
    //FROM
    //  componentegrupo cpg
    //";
    $sqlgrupo = "SELECT
  com.idgrupo
FROM
  componentegrupo com
WHERE
  com.idgrupo = '$idgrupo'";
    $countgrupo = NumeroTuplas($sqlgrupo);
    if($countgrupo ==0){
        $sqldetalle = "
SELECT
  pro.idproceso AS id,
  pro.nombre AS detalle,
  1 AS cantidad,
  0 AS preciounitario,
  'proceso' AS unidad,
  0 AS preciototal
FROM
  `procesos` pro pro.nombre
";
    }
    else{

        ActualizarProcesoITemGrupo($idgrupo);
        $sqldetalle = "
SELECT
  com.id,
  com.detalle,
  com.unidad,
  com.cantidad,
  com.precio AS preciounitario,
  com.total AS preciototal
FROM
  `componentegrupo` com
WHERE
  com.idgrupo = '$idgrupo'
";
    }

    $detalleA = getTablaToArrayOfSQL($sqldetalle);
    $detalle = $detalleA['resultado'];
    $value['detalleM'] = $detalle;

    $sqlproceso="
SELECT
  prc.idproceso AS id,
  0 AS costo,
  'proceso' AS unidad,
  prc.detalle
FROM
  procesos prc
WHERE
  prc.tipoproceso = 'items'
";
    $procesoA = getTablaToArrayOfSQL($sqlproceso);
    $proceso = $procesoA['resultado'];
    $value['procesoM'] = $proceso;
    //    $sqlproducto = "
    //SELECT
    //  prd.idproducto,
    //  prd.nombre as detalle,
    //  prd.costo,
    //  prd.medidas as unidad
    //
    //FROM
    //  productos prd
    //";
    $sqlproducto="
SELECT
  pro.idproducto AS id,
  pro.precio1bs AS costo,
  'pieza' AS unidad,
  pro.nombre AS detalle
FROM
  `productos` pro
";
    $productoA = getTablaToArrayOfSQL($sqlproducto);
    $producto = $productoA['resultado'];
    $value['productoM'] = $producto;



    //                    echo $sql;
    $dev['mensaje'] = "Existen resultados";
    $dev['error']   = "true";
    $dev['resultado'] = $value;
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

function getSqlNewComponentegrupo($idcomponentegrupo, $idgrupo, $fecha, $idproceso, $idmateriaprima, $idproducto, $detalle, $unidad, $cantidad, $precio, $total, $producto, $numero, $iditems, $id,$return){
    $setC[0]['campo'] = 'idcomponentegrupo';
    $setC[0]['dato'] = $idcomponentegrupo;
    $setC[1]['campo'] = 'idgrupo';
    $setC[1]['dato'] = $idgrupo;
    $setC[2]['campo'] = 'fecha';
    $setC[2]['dato'] = $fecha;
    $setC[3]['campo'] = 'idproceso';
    $setC[3]['dato'] = $idproceso;
    $setC[4]['campo'] = 'idmateriaprima';
    $setC[4]['dato'] = $idmateriaprima;
    $setC[5]['campo'] = 'idproducto';
    $setC[5]['dato'] = $idproducto;
    $setC[6]['campo'] = 'detalle';
    $setC[6]['dato'] = $detalle;
    $setC[7]['campo'] = 'unidad';
    $setC[7]['dato'] = $unidad;
    $setC[8]['campo'] = 'cantidad';
    $setC[8]['dato'] = $cantidad;
    $setC[9]['campo'] = 'precio';
    $setC[9]['dato'] = $precio;
    $setC[10]['campo'] = 'total';
    $setC[10]['dato'] = $total;
    $setC[11]['campo'] = 'producto';
    $setC[11]['dato'] = $producto;
    $setC[12]['campo'] = ' numero';
    $setC[12]['dato'] = $numero;
    $setC[13]['campo'] = 'iditems';
    $setC[13]['dato'] = $iditems;
    $setC[14]['campo'] = 'id';
    $setC[14]['dato'] = $id;
    $sql2 = generarInsertValues($setC);
    return "INSERT INTO componentegrupo ".$sql2;
}
function getSqlDeleteComponentegrupo($idcomponentegrupo){
    return "DELETE FROM componentegrupo WHERE idcomponentegrupo ='$idcomponentegrupo';";
}
function getSqlDeleteComponentegrupoPorGrupo($idgrupo){
    return "DELETE FROM componentegrupo WHERE idgrupo ='$idgrupo';";
}
function getSqlDeleteComponentegrupoPorProducto($idproducto){
    return "DELETE FROM componentegrupo WHERE producto ='$idproducto';";
}

function getSqlUpdateComponentegrupo($idcomponentegrupo,$precio,$total,$return){

    $setC[0]['campo'] = 'precio';
    $setC[0]['dato'] = $precio;
    $setC[1]['campo'] = 'total';
    $setC[1]['dato'] = $total;


    $set = generarSetsUpdate($setC);
    $wher[0]['campo'] = 'idcomponentegrupo';
    $wher[0]['dato'] = $idcomponentegrupo;


    $where = generarWhereUpdate($wher);
    return "UPDATE componentegrupo SET ".$set." WHERE ".$where;
}
function GuardarComponenteGrupo($callback, $_dc, $resultado, $return=false){
    $dev['error'] = "false";
    $dev['resultado']="";
    $dev['mensaje']="";
    $grupo = $resultado->grupo;
    $idgrupo = $grupo->idgrupo;
    $nombre = $grupo->nombre;
    $descripcion =$grupo->descripcion;
    $costo = $grupo->costounitario;
    $sql[]=getSqlUpdateGrupos($idgrupo,$nombre,$descripcion,$numero,$costo, $return);
    $detalles=$resultado->detalles;
    $numeroA = findUltimoID("componentegrupo", "numero", true);

    $numeroC = $numeroA['resultado']+1;
    //    echo $numeroC;exit();
    $sql[] = getSqlDeleteComponentegrupoPorGrupo($idgrupo);
    for($j=0;$j<count($detalles);$j++){
        $fecha = date("Y-m-d");
        $detalle = $detalles[$j];
        $idvariable=$detalle->id;
        $id = substr($idvariable,0,3);
        $nombre = $detalle->detalle;
        $unidad = $detalle->unidad;
        $cantidad = $detalle->cantidad;
        $precio = $detalle->preciounitario;
        $total = $detalle->preciototal;

        $idcomponentegrupo = "cpg-".$numeroC;
        if($id=="mtp"){
            $sql[] = getSqlNewComponentegrupo($idcomponentegrupo, $idgrupo, $fecha, "", $idvariable, "", $nombre, $unidad, $cantidad, $precio, $total, "producto", $numeroC, "iditems",$idvariable, $return);
        }
        else if($id=="prc"){
            $sql[] = getSqlNewComponentegrupo($idcomponentegrupo, $idgrupo, $fecha, $idvariable, "", "", $nombre, $unidad, $cantidad, $precio, $total, "producto", $numeroC, "iditems", $idvariable,$return);
        }
        else if($id=="prd"){
            $sql[] = getSqlNewComponentegrupo($idcomponentegrupo, $idgrupo, $fecha,"", "", $idvariable, $nombre, $unidad, $cantidad, $precio, $total, "producto", $numeroC, "iditems",$idvariable, $return);
        }
        $numeroC++;
        //$movimiento[$j]["fecha"] = $fecha;
        //sleep(1);
        //echo $numeroC."-".$id."<br>";
    }



    //    MostrarConsulta($sql);
    if(ejecutarConsultaSQLBeginCommit($sql)) {
        //        for($k=0;$k<count($movimiento);$k++){
        //            //            echo $movimiento[$j]['idproducto'];
        //            actualizarSaldoMovimientoKardexMateriaPrima($movimiento[$k]['idmateriaprima'],$movimiento[$k]['fecha'],$movimiento[$k]['hora'], false);
        //        }
        $dev['mensaje'] = "Se actualizo correctamente";
        $dev['error'] = "true";
        $dev['resultado'] = "";
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

function GuardarComponenteProducto($callback, $_dc, $resultado, $return=false){
    $dev['error'] = "false";
    $dev['resultado']="";
    $dev['mensaje']="";
    $grupo = $resultado->grupo;
    $idgrupo = $grupo->idgrupo;
    $nombre = $grupo->nombre;
    $costounitario = $grupo->costounitario;
    $precioventa = $grupo->precioventa;
    $preciototal = $grupo->preciototal;
    $cantidad = $grupo->cantidad;
    $utilidad = $grupo->utilidad;
    $utilidadtotal = $grupo->utilidadtotal;
    $observacion = $grupo->descripcion;

    $sql[]=getSqlDeleteCotizacion($idgrupo);
    $sql[]=getSqlNewCotizacion($idgrupo, $costounitario, $precioventa, $cantidad, $utilidad, $utilidadtotal, $preciototal, $observacion, $return);
    $sql[]=getSqlUpdateProductos($idgrupo, $nombre, '', '', '', '', '', '', $costounitario, $precioventa, '', $observacion, $return);
    $detalles=$resultado->detalles;
    $numeroA = findUltimoID("componentegrupo", "numero", true);

    $numeroC = $numeroA['resultado']+1;
    //    echo $numeroC;exit();
    $sql[] = getSqlDeleteComponentegrupoPorProducto($idgrupo);
    for($j=0;$j<count($detalles);$j++){
        $fecha = date("Y-m-d");
        $detalle = $detalles[$j];
        $idvariable=$detalle->id;
        $id = substr($idvariable,0,3);
        $nombre = $detalle->detalle;
        $unidad = $detalle->unidad;
        $cantidad = $detalle->cantidad;
        $precio = $detalle->preciounitario;
        $total = $detalle->preciototal;

        $idcomponentegrupo = "cpg-".$numeroC;
        if($id=="mtp"){
            $sql[] = getSqlNewComponentegrupo($idcomponentegrupo, "grupo", $fecha, "", $idvariable, "", $nombre, $unidad, $cantidad, $precio, $total, $idgrupo, $numeroC, "iditems",$idvariable, $return);
        }
        else if($id=="prc"){
            $sql[] = getSqlNewComponentegrupo($idcomponentegrupo, "grupo", $fecha, $idvariable, "", "", $nombre, $unidad, $cantidad, $precio, $total, $idgrupo, $numeroC, "iditems", $idvariable,$return);
        }
        else if($id=="prd"){
            $sql[] = getSqlNewComponentegrupo($idcomponentegrupo, "grupo", $fecha,"", "", $idvariable, $nombre, $unidad, $cantidad, $precio, $total, $idgrupo, $numeroC, "iditems",$idvariable, $return);
        }
        $numeroC++;
        //$movimiento[$j]["fecha"] = $fecha;
        //sleep(1);
        //echo $numeroC."-".$id."<br>";
    }



    //        MostrarConsulta($sql);
    if(ejecutarConsultaSQLBeginCommit($sql)) {
        //        for($k=0;$k<count($movimiento);$k++){
        //            //            echo $movimiento[$j]['idproducto'];
        //            actualizarSaldoMovimientoKardexMateriaPrima($movimiento[$k]['idmateriaprima'],$movimiento[$k]['fecha'],$movimiento[$k]['hora'], false);
        //        }
        $dev['mensaje'] = "Se actualizo correctamente";
        $dev['error'] = "true";
        $dev['resultado'] = "";
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


function GuardarComponentesProductoDuplicar($callback, $_dc, $resultado, $return=false){
    $dev['error'] = "false";
    $dev['resultado']="";
    $dev['mensaje']="";
    $grupo = $resultado->grupo;

    $nombre = $grupo->nombre;
    $costounitario = $grupo->costounitario;
    $precioventa = $grupo->precioventa;
    $preciototal = $grupo->preciototal;
    $cantidad = $grupo->cantidad;
    $utilidad = $grupo->utilidad;
    $utilidadtotal = $grupo->utilidadtotal;
    $observacion = $grupo->descripcion;
    $numeroP = findUltimoID("productos", "numero", true);
    $numeropo = $numeroP['resultado']+1;
    $idgrupo = "prd-".$numeropo;
    //    los datos de idgrupo,idcategoriaproducto y idempresa seran optenidos de una clase donde se almacena todas sus configuraciones
    $sql[]=getSqlNewProductosLogos($idgrupo, $nombre, '', '', '', $tela,'gru-1', 'ctp-1', $costounitario, $precioventa, $numeropo, $observacion, 0, 0, '', '', '', '', '', '', $return);
    $sql[]=getSqlNewCotizacion($idgrupo, $costounitario, $precioventa, $cantidad, $utilidad, $utilidadtotal, $preciototal, $observacion, $return);
    //    $sql[]=getSqlUpdateProductos($idgrupo, $nombre, '', '', '', '', '', '', $costounitario, $precioventa, '', $observacion, $return);
    $detalles=$resultado->detalles;
    $numeroA = findUltimoID("componentegrupo", "numero", true);

    $numeroC = $numeroA['resultado']+1;
    //    echo $numeroC;exit();
    $sql[] = getSqlDeleteComponentegrupoPorProducto($idgrupo);
    for($j=0;$j<count($detalles);$j++){
        $fecha = date("Y-m-d");
        $detalle = $detalles[$j];
        $idvariable=$detalle->id;
        $id = substr($idvariable,0,3);
        $nombre = $detalle->detalle;
        $unidad = $detalle->unidad;
        $cantidad = $detalle->cantidad;
        $precio = $detalle->preciounitario;
        $total = $detalle->preciototal;

        $idcomponentegrupo = "cpg-".$numeroC;
        if($id=="mtp"){
            $sql[] = getSqlNewComponentegrupo($idcomponentegrupo, "grupo", $fecha, "", $idvariable, "", $nombre, $unidad, $cantidad, $precio, $total, $idgrupo, $numeroC, "iditems",$idvariable, $return);
        }
        else if($id=="prc"){
            $sql[] = getSqlNewComponentegrupo($idcomponentegrupo, "grupo", $fecha, $idvariable, "", "", $nombre, $unidad, $cantidad, $precio, $total, $idgrupo, $numeroC, "iditems", $idvariable,$return);
        }
        else if($id=="prd"){
            $sql[] = getSqlNewComponentegrupo($idcomponentegrupo, "grupo", $fecha,"", "", $idvariable, $nombre, $unidad, $cantidad, $precio, $total, $idgrupo, $numeroC, "iditems",$idvariable, $return);
        }
        $numeroC++;
        //$movimiento[$j]["fecha"] = $fecha;
        //sleep(1);
        //echo $numeroC."-".$id."<br>";
    }



    //            MostrarConsulta($sql);
    if(ejecutarConsultaSQLBeginCommit($sql)) {
        //        for($k=0;$k<count($movimiento);$k++){
        //            //            echo $movimiento[$j]['idproducto'];
        //            actualizarSaldoMovimientoKardexMateriaPrima($movimiento[$k]['idmateriaprima'],$movimiento[$k]['fecha'],$movimiento[$k]['hora'], false);
        //        }
        $dev['mensaje'] = "Se actualizo correctamente";
        $dev['error'] = "true";
        $dev['resultado'] = "";
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

function BuscarComponentePorIdProducto($callback, $_dc, $idproducto, $return = false){
    $sqlmateria = "
SELECT
  mtp.idmateriaprima AS id,

  kmp.costounitario AS costo,
  uni.nombre AS unidad,
  mtp.nombre as detalle
FROM
  materiaprima mtp,
  kardexmateriaprima kmp,
  unidades uni
WHERE
  mtp.idunidad = uni.idunidad AND
  mtp.idmateriaprima = kmp.idmateriaprima
";

    $materiaA = getTablaToArrayOfSQL($sqlmateria);
    $materia = $materiaA['resultado'];
    $value['materiaM'] = $materia;

    $sqlproceso="
SELECT
  prc.idproceso AS id,
  0 AS costo,
  'proceso' AS unidad,
  prc.nombre as detalle
FROM
  procesos prc
WHERE
  prc.tipoproceso = 'items'
";
    //    echo $sqlproceso;
    $procesoA = getTablaToArrayOfSQL($sqlproceso);
    $proceso = $procesoA['resultado'];
    $value['procesoM'] = $proceso;
    if($_GET['operacion']!==""){
        ActualizarProcesoITemProducto($idproducto);

    }

    //    ActualizarProcesoITemProducto($idproducto);
    $sqldetalle = "
SELECT
  com.cantidad,
  com.detalle,
  com.unidad,
  com.precio AS preciounitario,
  com.total AS preciototal,
  com.id
FROM
  componentegrupo com
WHERE
  com.producto = '$idproducto'
";
//            echo"hola".$sqldetalle;
    $detalleA = getTablaToArrayOfSQL($sqldetalle);
    if($detalleA['resultado']==true){
        $detalle = $detalleA['resultado'];
        $value['detalleM'] = $detalle;
    }
    else {
        $sql2 = "SELECT
  com.cantidad,
  com.detalle,
  com.unidad,
  com.precio AS preciounitario,
  com.total AS preciototal,
  com.id
FROM
  componentegrupo com,
  productos pro
WHERE
  pro.idproducto = '$idproducto' AND
  pro.idgrupo = com.idgrupo";
        $detalle2 = getTablaToArrayOfSQL($sql2);
        if($detalle2['resultado']==true){
            $detalleA2 = $detalle2['resultado'];
            $value['detalleM'] = $detalleA2;

        }
        else{
            $sqlproceso1 = "SELECT
  pro.idproceso AS id,
  pro.nombre as detalle,
  'proceso' AS unidad,
  1 AS cantidad ,
  1 AS preciounitario,
  1 AS preciototal
FROM
  `procesos` pro
WHERE
  pro.tipoproceso = 'items'";
            $proceso1 = getTablaToArrayOfSQL($sqlproceso1);
            $value['detalleM'] = $proceso1['resultado'];
        }

    }
    $sqlproducto="
SELECT
  pro.idproducto AS id,
  pro.precio1bs AS costo,
  'pieza' AS unidad,
  pro.nombre AS detalle
FROM
  `productos` pro
";
    $productoA = getTablaToArrayOfSQL($sqlproducto);
    $producto = $productoA['resultado'];
    $value['productoM'] = $producto;

    //                    echo $sql;
    $dev['mensaje'] = "Existen resultados";
    $dev['error']   = "true";
    $dev['resultado'] = $value;
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
function ActualizarProcesoITemGrupo($idgrupo){
    $sqlMateria = "SELECT
  com.idcomponentegrupo,
  com.cantidad,
  kar.costounitario AS costo
FROM
  componentegrupo com,
  `kardexmateriaprima` kar
WHERE
  com.idgrupo = '$idgrupo' AND
  com.idmateriaprima IS NOT NULL AND
  com.idmateriaprima = kar.idmateriaprima";
    $materiaA = getTablaToArrayOfSQL($sqlMateria);
    $materia = $materiaA['resultado'];

    for($i=0; $i<count($materia);$i++){
        $materiaprima = $materia[$i];

        $idcomponentegrupo = $materiaprima['idcomponentegrupo'];
        $costo = $materiaprima['costo'];
        $cantidad = $materiaprima['cantidad'];
        $sql[] = getSqlUpdateComponentegrupo($idcomponentegrupo,$costo,$cantidad*$costo,$return);
    }
    ejecutarConsultaSQLBeginCommit($sql);
    //    MostrarConsulta($sql);
    //    exit();
    //    $materia
}
function ActualizarProcesoITemProducto($idproducto){
    $sqlMateria = "SELECT
  com.idcomponentegrupo,
  com.cantidad,
  kar.costounitario AS costo,
  kar.precio1bs
FROM
  componentegrupo com,
  kardexmateriaprima kar
WHERE
  com.idmateriaprima IS NOT NULL AND
  com.idmateriaprima = kar.idmateriaprima AND
  com.producto =  '$idproducto'";
    $materiaA = getTablaToArrayOfSQL($sqlMateria);
    $materia = $materiaA['resultado'];

    for($i=0; $i<count($materia);$i++){
        $materiaprima = $materia[$i];

        $idcomponentegrupo = $materiaprima['idcomponentegrupo'];
        $costo = $materiaprima['costo'];
        $cantidad = $materiaprima['cantidad'];
        $sql[] = getSqlUpdateComponentegrupo($idcomponentegrupo,$costo,$cantidad*$costo,$return);
    }
    ejecutarConsultaSQLBeginCommit($sql);
    //    MostrarConsulta($sql);
    //    exit();
    //    $materia
}
?>