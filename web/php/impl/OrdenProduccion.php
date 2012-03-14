<?php
function CargarPanelOrdenProduccion($callback, $_dc, $where='',$return = false){
    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";
    $clienteA = ListarClienteIdNombre("", "", "nombre", "DESC", "", "", "", true);
    if($clienteA["error"]=="false"){
        $dev['mensaje'] = "No se pudo encontrar clientes";
        $dev['error']   = "false";


    }
    $telaA = ListarMateriaPrimaIdDetalleTela("", "", "detalle", "DESC", "", "", "", true);
    if($telaA["error"]=="false"){
        $dev['mensaje'] = "No se pudo encontrar Telas";
        $dev['error']   = "false";


    }
    $colorA = ListarColoresIdNombre("", "", "nombre", "DESC", "", "", "", true);
    if($colorA["error"]=="false"){
        $dev['mensaje'] = "No se pudo encontrar Colores";
        $dev['error']   = "false";


    }
    $productoA = ListarProductoIdNombreUnidadPrecio("", "", "nombre", "DESC", "", "", "", true);
    if($productoA["error"]=="false"){
        $dev['mensaje'] = "No se pudo encontro productos";
        $dev['error']   = "false";

    }
    $responsableA = ListarResponsablesIdNombre("", "", "nombre", "DESC", "", "", "", true);
    if($responsableA["error"]=="false"){
        $dev['mensaje'] = "No se pudo encontro responsables";
        $dev['error']   = "false";
    }

    $empresaA = ListarEmpresasIdNombre("", "", "nombre", "DESC", "", "", "", true);
    if($empresaA["error"]=="false"){
        $dev['mensaje'] = "No se pudo encontro empresas";
        $dev['error']   = "false";
    }
    $numerorodenA = findUltimoOrdenProduccion( true);
    $orden = $numerorodenA['resultado']+1;
    $dev['numeroproduccion'] = "$orden";

    //    $dev['resultado'] = $productoA['error'];
    if(($productoA["error"]=="true")&&($clienteA["error"]=="true")&&($responsableA["error"]=="true")&&($empresaA["error"]=="true")&&($telaA["error"]=="true")&&($colorA["error"]=="true")){
        $dev['mensaje'] = "Todo Ok";
        $dev['error'] = "true";
        $dev['productoM'] = $productoA['resultado'];
        $dev['clienteM']= $clienteA['resultado'];
        $dev['responsableM']= $responsableA['resultado'];
        $dev['empresaM']= $empresaA['resultado'];
        $dev['telaM']= $telaA['resultado'];
        $dev['colorM']= $colorA['resultado'];
        $dev["fecha"] = date("m/d/Y");

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


function ListarOrdenProduccion($start, $limit, $sort, $dir, $callback, $_dc, $where = '', $return = false){

    if($start == null)
    {
        $start = 0;
    }
    if($limit == null)
    {
        $limit = 100;
    }
    if($sort != null)
    {
        $order = "ORDER BY $sort ";
        if($dir != null)
        {
            $order .= " $dir ";
        }
    }
    //    $dev['totalCount'] = 0;
    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";
    if(($where == null)||($where =="")){
        $sql = "
SELECT
  `ord`.numeroorden,
  `ord`.idordenproduccion,
  `ord`.fechaentrega,
  `ord`.fecharecepcion,
  `ord`.montototal,
  `ord`.montoapagar,
  `ord`.saldo,
  `ord`.observacion,
  `det`.estado,
`ord`.estado AS estadoop,
   ord.cliente AS cliente,
   CONCAT(`det`.`detalle`, ' ',  `det`.`talla`) AS item,
  `det`.`cantidad`
FROM
  ordenproduccion `ord` ,`detalleorden` `det` WHERE `ord`.`idordenproduccion` = `det`.`idordenproduccion` $order
";
    }
    else{
        $sql ="
SELECT
  `ord`.numeroorden,
  `ord`.idordenproduccion,
  `ord`.fechaentrega,
  `ord`.fecharecepcion,
  `ord`.montototal,
  `ord`.montoapagar,
  `ord`.saldo,
  `ord`.observacion,
  `det`.estado,
`ord`.estado AS estadoop,
  ord.cliente AS cliente,
   CONCAT(`det`.`detalle`, ' ',  `det`.`talla`) AS item,
  `det`.`cantidad`
FROM
  ordenproduccion `ord` ,`detalleorden` `det` WHERE `ord`.`idordenproduccion` = `det`.`idordenproduccion` AND $where $order
";
    }

//            echo $sql;
    if($link=new BD)
    {
        if($link->conectar())
        {
            if($re = $link->consulta($sql))
            {

                if($fi = mysql_fetch_array($re))
                {
                    $dev['totalCount'] = mysql_num_rows($re);
                    $ii = 0;
                    do{

                        for($i = 0; $i< mysql_num_fields($re); $i++)
                        {
                            $value{$ii}{mysql_field_name($re, $i)}= $fi[$i];

                        }

                        $ii++;
                    }while($fi = mysql_fetch_array($re));
                    $dev['mensaje'] = "Existen resultados";
                    $dev['error']   = "true";
                    $dev['resultado'] = $value;

                }
                else
                {
                    $dev['mensaje'] = "No se encontro datos en la consulta";
                    $dev['error']   = "false";
                    $dev['resultado'] = "";
                }
            }
            else
            {
                $dev['mensaje'] = "No existe un usuario con estos datos";
                $dev['error']   = "false";
                $dev['resultado'] = "";
            }
        }
        else
        {
            $dev['mensaje'] = "No se pudo conectar a la BD";
            $dev['error']   = "false";
            $dev['resultado'] = "";
        }
    }
    else
    {
        $dev['mensaje'] = "No se pudo crear la conexion a la BD";
        $dev['error']   = "false";
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

function getSqlNewOrdenproduccion($idordenproduccion, $numeroorden, $idcliente, $idempresa, $idresponsable, $fechaentrega, $fecharecepcion, $montototal, $montoapagar, $saldo, $observacion, $estado, $numero, $idusuario, $cliente, $numerorecibo, $return){
    $setC[0]['campo'] = 'idordenproduccion';
    $setC[0]['dato'] = $idordenproduccion;
    $setC[1]['campo'] = 'numeroorden';
    $setC[1]['dato'] = $numeroorden;
    $setC[2]['campo'] = 'idcliente';
    $setC[2]['dato'] = $idcliente;
    $setC[3]['campo'] = 'idempresa';
    $setC[3]['dato'] = $idempresa;
    $setC[4]['campo'] = 'idresponsable';
    $setC[4]['dato'] = $idresponsable;
    $setC[5]['campo'] = 'fechaentrega';
    $setC[5]['dato'] = $fechaentrega;
    $setC[6]['campo'] = 'fecharecepcion';
    $setC[6]['dato'] = $fecharecepcion;
    $setC[7]['campo'] = 'montototal';
    $setC[7]['dato'] = $montototal;
    $setC[8]['campo'] = 'montoapagar';
    $setC[8]['dato'] = $montoapagar;
    $setC[9]['campo'] = 'saldo';
    $setC[9]['dato'] = $saldo;
    $setC[10]['campo'] = 'observacion';
    $setC[10]['dato'] = $observacion;
    $setC[11]['campo'] = 'estado';
    $setC[11]['dato'] = $estado;
    $setC[12]['campo'] = 'numero';
    $setC[12]['dato'] = $numero;
    $setC[13]['campo'] = 'idusuario';
    $setC[13]['dato'] = $idusuario;
    $setC[14]['campo'] = 'cliente';
    $setC[14]['dato'] = $cliente;
    $setC[15]['campo'] = 'numerorecibo';
    $setC[15]['dato'] = $numerorecibo;
    $sql2 = generarInsertValues($setC);
    return "INSERT INTO ordenproduccion ".$sql2;
}


function getSqlDeleteOrdenproduccion($idordenproduccion){
    return "DELETE FROM ordenproduccion WHERE idordenproduccion ='$idordenproduccion';";
}
function getSqlNewDetalleorden($iddetalleorden, $idordenproduccion, $idproducto, $detalle, $talla, $preciounitario, $cantidad, $total, $numero, $saldocantidad, $idtela, $idcolor, $detalleitem, $detallebordado, $detallecostura, $estado, $return){
    $setC[0]['campo'] = 'iddetalleorden';
    $setC[0]['dato'] = $iddetalleorden;
    $setC[1]['campo'] = 'idordenproduccion';
    $setC[1]['dato'] = $idordenproduccion;
    $setC[2]['campo'] = 'idproducto';
    $setC[2]['dato'] = $idproducto;
    $setC[3]['campo'] = 'detalle';
    $setC[3]['dato'] = $detalle;
    $setC[4]['campo'] = 'talla';
    $setC[4]['dato'] = $talla;
    $setC[5]['campo'] = 'preciounitario';
    $setC[5]['dato'] = $preciounitario;
    $setC[6]['campo'] = 'cantidad';
    $setC[6]['dato'] = $cantidad;
    $setC[7]['campo'] = 'total';
    $setC[7]['dato'] = $total;
    $setC[8]['campo'] = 'numero';
    $setC[8]['dato'] = $numero;
    $setC[9]['campo'] = 'saldocantidad';
    $setC[9]['dato'] = $saldocantidad;
    $setC[10]['campo'] = 'idtela';
    $setC[10]['dato'] = $idtela;
    $setC[11]['campo'] = 'idcolor';
    $setC[11]['dato'] = $idcolor;
    $setC[12]['campo'] = 'detalleitem';
    $setC[12]['dato'] = $detalleitem;
    $setC[13]['campo'] = 'detallebordado';
    $setC[13]['dato'] = $detallebordado;
    $setC[14]['campo'] = 'detallecostura';
    $setC[14]['dato'] = $detallecostura;
    $setC[15]['campo'] = 'estado';
    $setC[15]['dato'] = $estado;
    $sql2 = generarInsertValues($setC);
    return "INSERT INTO detalleorden ".$sql2;
}

function getSqlUpdateDetalleorden($iddetalleorden,$idordenproduccion,$idproducto,$detalle,$talla,$preciounitario,$cantidad,$total,$numero,$saldocantidad,$idtela,$idcolor,$detalleitem,$detallebordado,$detallecostura,$estado, $return){
    $setC[0]['campo'] = 'detalle';
    $setC[0]['dato'] = $detalle;
    $setC[1]['campo'] = 'talla';
    $setC[1]['dato'] = $talla;
    $setC[2]['campo'] = 'preciounitario';
    $setC[2]['dato'] = $preciounitario;
    $setC[3]['campo'] = 'cantidad';
    $setC[3]['dato'] = $cantidad;
    $setC[4]['campo'] = 'total';
    $setC[4]['dato'] = $total;
    $setC[5]['campo'] = 'numero';
    $setC[5]['dato'] = $numero;
    $setC[6]['campo'] = 'saldocantidad';
    $setC[6]['dato'] = $saldocantidad;
    $setC[7]['campo'] = 'detalleitem';
    $setC[7]['dato'] = $detalleitem;
    $setC[8]['campo'] = 'detallebordado';
    $setC[8]['dato'] = $detallebordado;
    $setC[9]['campo'] = 'detallecostura';
    $setC[9]['dato'] = $detallecostura;
    $setC[10]['campo'] = 'estado';
    $setC[10]['dato'] = $estado;
    $setC[11]['campo'] = 'idordenproduccion';
    $setC[11]['dato'] = $idordenproduccion;
    $setC[12]['campo'] = 'idproducto';
    $setC[12]['dato'] = $idproducto;
    $setC[13]['campo'] = 'idtela';
    $setC[13]['dato'] = $idtela;
    $setC[14]['campo'] = 'idcolor';
    $setC[14]['dato'] = $idcolor;

    $set = generarSetsUpdate($setC);
    $wher[0]['campo'] = 'iddetalleorden';
    $wher[0]['dato'] = $iddetalleorden;


    $where = generarWhereUpdate($wher);
    return "UPDATE detalleorden SET ".$set." WHERE ".$where;
}

function GuardarOrdenProduccion($callback, $_dc, $resultado,$return = false){
    $dev['mensaje'] = "";
    $dev['error']   = "false";
    $dev['resultado'] = "";

    $orden = $resultado->orden;
    $idcliente = $orden->cliente;
    if($idcliente != ""){
        $sqlcliente =    "
SELECT
  cli.nombre AS cliente
FROM
  `clientes` cli
WHERE
  cli.idcliente = '$idcliente'
";
    }

    $idempresa = $orden->empresa;
    if($idempresa!=""){
        $sqlcliente = "
SELECT
  emp.nombre AS cliente
FROM
  `empresas` emp
WHERE
  emp.idempresa = '$idempresa'
";
    }

    $clienteA = findBySqlReturnCampoUnique($sqlcliente, true, true, "cliente");
    $cliente = $clienteA['resultado'];
    $idresponsable = $orden->responsable;
    $fechaentrega = $orden->fechaentrega;
    $fechaentregaA = validarText($fechaentrega, true);
    if($fechaentregaA["error"]==false){
        $dev['mensaje'] = "Error en el campo fecha entrega: ".$fechaentregaA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $fecharecepcion = $orden->fecharecepcion;
    $fecharecepcionA = validarText($fecharecepcion, true);
    if($fecharecepcionA["error"]==false){
        $dev['mensaje'] = "Error en el campo fecha recepcion: ".$fecharecepcionA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    //validar orden de produccion
    $montoapagar = $orden->montoacuenta;
    $montototal = $orden->montototal;
    $numerorecibo = $orden->recibo;
    $saldo = $orden->saldo;
    $numeroorden = $orden->orden;
    $ordenA = verificarValidarText($numeroorden, false, "ordenproduccion", "numeroorden");
    if($ordenA["error"]==false){
        $dev['mensaje'] = "Error en el campo No Orden de Produccion ".$ordenA["mensaje"];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $estado = "recepcion";
    $idusuario = $_SESSION['idusuario'];
    $numeroA = findUltimoID("ordenproduccion", "numero", true);
    $numero = $numeroA['resultado']+1;
    $idordenproduccion = 'ord-'.$numero;

    $detallebordado = $orden->detallebordado;
    $detallecostura = $orden->detallecostura;
    $sql[] = getSqlNewOrdenproduccion($idordenproduccion, $numeroorden, $idcliente, $idempresa, $idresponsable, $fechaentrega, $fecharecepcion, $montototal, $montoapagar, $saldo, $observacion, $estado, $numero, $idusuario, $cliente, $numerorecibo, $return);
    //    $sql[] = getSqlNewOrdenproduccion($idordenproduccion, $numeroorden, $idcliente, $idempresa, $idresponsable, $fechaentrega,$fecharecepcion,  $montototal, $montoapagar, $saldo, $observacion, $estado, $numero, $idusuario,$cliente,$detallecostura, $detallebordado, $return);
    $sql[] = getSqlNewCredito($idordenproduccion, $idcliente, $idempresa, $montototal, $saldo, $observacion, $idresponsable, $fecharecepcion, $return);
    $numerocA = findUltimoID("detallecredito", "numero", true);
    $numeroc = $numerocA['resultado']+1;
    $iddetallecredito = "dec-".$numeroc;
    $sql[] = getSqlNewDetallecredito($iddetallecredito, $idordenproduccion, $montoapagar, $saldo, $fecharecepcion, $idusuario, $numeroc, $return);

    $detalles = $resultado->detalles;
    if(count($detalles)>0){

        $numerodetalleA = findUltimoID("detalleorden", "numero", true);
        $numerodetalle = $numerodetalleA['resultado']+1;
        for($i=0;$i<count($detalles);$i++){
            $detalle1 = $detalles[$i];
            $id = $detalle1->id;
            //        echo "hola".$id;
            //        exit();
            $detalle = $detalle1->detalle;
            $talla = $detalle1->unidad;
            $preciounitario = $detalle1->preciounitario;
            $preciototal = $detalle1->preciototal;
            $cantidad = $detalle1->cantidad;
            $idtela = $detalle1->tela;
            $idcolor = $detalle1->color;
            $detalleitem = $detalle1->detalle1;
            $detallebordado = $detalle1->detallebordado;
            $detallecostura = $detalle1->detallecostura;
            $estadoi = $detalle1->estado;

            $iddetalleorden = "deo-".$numerodetalle;
            $sql[] = getSqlNewDetalleorden($iddetalleorden, $idordenproduccion, $id, $detalle, $talla, $preciounitario, $cantidad, $preciototal, $numerodetalle, $cantidad, $idtela, $idcolor, $detalleitem, $detallebordado, $detallecostura, $estadoi, $return);
            //            $sql[] = getSqlNewDetalleorden($iddetalleorden, $idordenproduccion, $id, $detalle, $unidad, $preciounitario, $cantidad, $preciototal, $numerodetalle, $cantidad, $return);
            $numerodetalle++;
        }
    }
    else{
        $dev['mensaje'] = "Error Debe de ingresar algun producto";
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
//                MostrarConsulta($sql);
    if(ejecutarConsultaSQLBeginCommit($sql)) {
        //        for($k=0;$k<count($movimiento);$k++){
        //            //            echo $movimiento[$j]['idproducto'];
        //            actualizarSaldoMovimientoKardexMateriaPrima($movimiento[$k]['idmateriaprima'],$movimiento[$k]['fecha'],$movimiento[$k]['hora'], false);
        //        }
        $dev['mensaje'] = "Se actualizo correctamente";
        $dev['error'] = "true";
        $dev['resultado'] = "";
        $numero = $numeroorden +1;
        $dev['numeroorden'] = "$numero";
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

function getSqlUpdateOrdenproduccion($idordenproduccion,$numeroorden,$idcliente,$idempresa,$idresponsable,$fechaentrega,$fecharecepcion,$montototal,$montoapagar,$saldo,$observacion,$estado,$numero,$idusuario,$cliente,$numerorecibo, $return){
    $setC[0]['campo'] = 'numeroorden';
    $setC[0]['dato'] = $numeroorden;
    $setC[1]['campo'] = 'fechaentrega';
    $setC[1]['dato'] = $fechaentrega;
    $setC[2]['campo'] = 'fecharecepcion';
    $setC[2]['dato'] = $fecharecepcion;
    $setC[3]['campo'] = 'montototal';
    $setC[3]['dato'] = $montototal;
    $setC[4]['campo'] = 'montoapagar';
    $setC[4]['dato'] = $montoapagar;
    $setC[5]['campo'] = 'saldo';
    $setC[5]['dato'] = $saldo;
    $setC[6]['campo'] = 'observacion';
    $setC[6]['dato'] = $observacion;
    $setC[7]['campo'] = 'estado';
    $setC[7]['dato'] = $estado;
    $setC[8]['campo'] = 'numero';
    $setC[8]['dato'] = $numero;
    $setC[9]['campo'] = 'cliente';
    $setC[9]['dato'] = $cliente;
    $setC[10]['campo'] = 'numerorecibo';
    $setC[10]['dato'] = $numerorecibo;
    $setC[11]['campo'] = 'idcliente';
    $setC[11]['dato'] = $idcliente;
    $setC[12]['campo'] = 'idempresa';
    $setC[12]['dato'] = $idempresa;
    $setC[13]['campo'] = 'idresponsable';
    $setC[13]['dato'] = $idresponsable;
    $setC[14]['campo'] = 'idusuario';
    $setC[14]['dato'] = $idusuario;

    $set = generarSetsUpdate($setC);
    $wher[0]['campo'] = 'idordenproduccion';
    $wher[0]['dato'] = $idordenproduccion;


    $where = generarWhereUpdate($wher);
    return "UPDATE ordenproduccion SET ".$set." WHERE ".$where;
}
function GuardarEditarOrdenProduccion($callback, $_dc, $resultado,$return = false){
    $dev['mensaje'] = "";
    $dev['error']   = "false";
    $dev['resultado'] = "";
    $id = $_GET['idorden'];
    $orden = $resultado->orden;
    $idcliente = $orden->cliente;
    if($idcliente != ""){
        $sqlcliente =    "
SELECT
  cli.nombre AS cliente
FROM
  `clientes` cli
WHERE
  cli.idcliente = '$idcliente'
";
    }

    $idempresa = $orden->empresa;
    if($idempresa!=""){
        $sqlcliente = "
SELECT
  emp.nombre AS cliente
FROM
  `empresas` emp
WHERE
  emp.idempresa = '$idempresa'
";
    }

    $clienteA = findBySqlReturnCampoUnique($sqlcliente, true, true, "cliente");
    $cliente = $clienteA['resultado'];
    $idresponsable = $orden->responsable;
    $fechaentrega = $orden->fechaentrega;
    $fechaentregaA = validarText($fechaentrega, true);
    if($fechaentregaA["error"]==false){
        $dev['mensaje'] = "Error en el campo fecha entrega: ".$fechaentregaA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $fecharecepcion = $orden->fecharecepcion;
    $fecharecepcionA = validarText($fecharecepcion, true);
    if($fecharecepcionA["error"]==false){
        $dev['mensaje'] = "Error en el campo fecha recepcion: ".$fecharecepcionA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    //validar orden de produccion
    $montoapagar = $orden->montoacuenta;
    $montototal = $orden->montototal;
    $numerorecibo = $orden->recibo;
    $saldo = $orden->saldo;
    $numeroorden = $orden->orden;
    $ordenA = verificarValidarTextUnicoEdit("idordenproduccion", $id, false, "ordenproduccion", "numeroorden", $numeroorden);
    if($ordenA["error"]==false){
        $dev['mensaje'] = "Error en el campo No Orden de Produccion ".$ordenA["mensaje"];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    //    echo"hola";
    //    exit();

    $idusuario = $_SESSION['idusuario'];


    $detallebordado = $orden->detallebordado;
    $detallecostura = $orden->detallecostura;
    //    $sql[] = getSqlNewOrdenproduccion($idordenproduccion, $numeroorden, $idcliente, $idempresa, $idresponsable, $fechaentrega, $fecharecepcion, $montototal, $montoapagar, $saldo, $observacion, $estado, $numero, $idusuario, $cliente, $numerorecibo, $return);
    //    $sql[] = getSqlNewOrdenproduccion($idordenproduccion, $numeroorden, $idcliente, $idempresa, $idresponsable, $fechaentrega,$fecharecepcion,  $montototal, $montoapagar, $saldo, $observacion, $estado, $numero, $idusuario,$cliente,$detallecostura, $detallebordado, $return);
    $sql[] = getSqlUpdateOrdenproduccion($id, $numeroorden, $idcliente, $idempresa, $idresponsable, $fechaentrega, $fecharecepcion, $montototal, $montoapagar, $saldo, $observacion, $estado, "", $idusuario, $cliente, $numerorecibo, $return);
    $detalles = $resultado->detalles;
    if(count($detalles)>0){
        $numerodetalleA = findUltimoID("detalleorden", "numero", true);
        $numerodetalle = $numerodetalleA['resultado']+1;

        for($i=0;$i<count($detalles);$i++){
            $detalle1 = $detalles[$i];
            $iddetalleop = $detalle1->id;
            $id1 = substr($iddetalleop,0,3);

            //        echo "hola".$id;
            //        exit();
            $detalle = $detalle1->detalle;
            $talla = $detalle1->unidad;
            $preciounitario = $detalle1->preciounitario;
            $preciototal = $detalle1->preciototal;
            $cantidad = $detalle1->cantidad;
            $idtela = $detalle1->tela;
            $idcolor = $detalle1->color;
            $detalleitem = $detalle1->detalle1;
            $detallebordado = $detalle1->detallebordado;
            $detallecostura = $detalle1->detallecostura;
            $estadoi = $detalle1->estado;
            if($id1 =="deo"){
                $sql[] = getSqlUpdateDetalleorden($iddetalleop, $id, $idproducto, $detalle, $talla, $preciounitario, $cantidad, $preciototal, $numero, $cantidad1, $idtela, $idcolor, $detalleitem, $detallebordado, $detallecostura, $estadoi, $return);

            }
            else{
                $iddetalleorden = "deo-".$numerodetalle;
                $sql[] = getSqlNewDetalleorden($iddetalleorden, $id, $iddetalleop, $detalle, $talla, $preciounitario, $cantidad, $preciototal, $numerodetalle, $cantidad, $idtela, $idcolor, $detalleitem, $detallebordado, $detallecostura, $estadoi, $return);
                //            $sql[] = getSqlNewDetalleorden($iddetalleorden, $idordenproduccion, $id, $detalle, $unidad, $preciounitario, $cantidad, $preciototal, $numerodetalle, $cantidad, $return);
                $numerodetalle++;
            }
            //            $sql[] = getSqlNewDetalleorden($iddetalleorden, $idordenproduccion, $id, $detalle, $talla, $preciounitario, $cantidad, $preciototal, $numerodetalle, $cantidad, $idtela, $idcolor, $detalleitem, $detallebordado, $detallecostura, $estadoi, $return);
            //            $sql[] = getSqlUpdateDetalleorden($iddetalleorden, $idordenproduccion, $idproducto, $detalle, $talla, $preciounitario, $cantidad, $total, $numero, $saldocantidad, $idtela, $idcolor, $detalleitem, $detallebordado, $detallecostura, $estado, $return);
            //            $sql[] = getSqlNewDetalleorden($iddetalleorden, $idordenproduccion, $id, $detalle, $unidad, $preciounitario, $cantidad, $preciototal, $numerodetalle, $cantidad, $return);

        }
    }
    else{
        $dev['mensaje'] = "Error Debe de ingresar algun producto";
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    //            MostrarConsulta($sql);exit();
    if(ejecutarConsultaSQLBeginCommit($sql)) {
        //        for($k=0;$k<count($movimiento);$k++){
        //            //            echo $movimiento[$j]['idproducto'];
        //            actualizarSaldoMovimientoKardexMateriaPrima($movimiento[$k]['idmateriaprima'],$movimiento[$k]['fecha'],$movimiento[$k]['hora'], false);
        //        }
        $dev['mensaje'] = "Se actualizo correctamente";
        $dev['error'] = "true";
        $dev['resultado'] = "";
        $numero = $numeroorden +1;
        $dev['numeroorden'] = "$numero";
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

function ListarProductosOrdenIdNombreUnidad($start, $limit, $sort, $dir, $callback, $_dc, $numeroorden, $return = false){

    if($start == null)
    {
        $start = 0;
    }
    if($limit == null)
    {
        $limit = 100;
    }
    if($sort != null)
    {
        $order = "ORDER BY $sort ";
        if($dir != null)
        {
            $order .= " $dir ";
        }
    }
    //    $dev['totalCount'] = 0;
    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";

    $sql ="
SELECT
  det.idproducto as id,
  det.detalle,
  det.unidad
FROM
  detalleorden det,
  ordenproduccion `ord`
WHERE
  `ord`.numeroorden = '$numeroorden' AND
  `ord`.idordenproduccion = det.idordenproduccion $order
";
    //        echo $sql;
    if($link=new BD)
    {
        if($link->conectar())
        {
            if($re = $link->consulta($sql))
            {

                if($fi = mysql_fetch_array($re))
                {
                    $dev['totalCount'] = mysql_num_rows($re);
                    $ii = 0;
                    do{

                        for($i = 0; $i< mysql_num_fields($re); $i++)
                        {
                            $value{$ii}{mysql_field_name($re, $i)}= $fi[$i];

                        }

                        $ii++;
                    }while($fi = mysql_fetch_array($re));
                    $dev['mensaje'] = "Existen resultados";
                    $dev['error']   = "true";
                    $dev['resultado'] = $value;

                }
                else
                {
                    $dev['mensaje'] = "No se encontro datos en la consulta";
                    $dev['error']   = "false";
                    $dev['resultado'] = "";
                }
            }
            else
            {
                $dev['mensaje'] = "No existe un usuario con estos datos";
                $dev['error']   = "false";
                $dev['resultado'] = "";
            }
        }
        else
        {
            $dev['mensaje'] = "No se pudo conectar a la BD";
            $dev['error']   = "false";
            $dev['resultado'] = "";
        }
    }
    else
    {
        $dev['mensaje'] = "No se pudo crear la conexion a la BD";
        $dev['error']   = "false";
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

function BuscarOrdenPorId($callback, $_dc, $id,$return = false){
    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";
    $clienteA = ListarClienteIdNombre("", "", "nombre", "DESC", "", "", "", true);
    if($clienteA["error"]=="false"){
        $dev['mensaje'] = "No se pudo encontrar clientes";
        $dev['error']   = "false";


    }
    $telaA = ListarMateriaPrimaIdDetalleTela("", "", "detalle", "DESC", "", "", "", true);
    if($telaA["error"]=="false"){
        $dev['mensaje'] = "No se pudo encontrar Telas";
        $dev['error']   = "false";


    }
    $colorA = ListarColoresIdNombre("", "", "nombre", "DESC", "", "", "", true);
    if($colorA["error"]=="false"){
        $dev['mensaje'] = "No se pudo encontrar Colores";
        $dev['error']   = "false";


    }
    $productoA = ListarProductoIdNombreUnidadPrecio("", "", "nombre", "DESC", "", "", "", true);
    if($productoA["error"]=="false"){
        $dev['mensaje'] = "No se pudo encontro productos";
        $dev['error']   = "false";

    }
    $responsableA = ListarResponsablesIdNombre("", "", "nombre", "DESC", "", "", "", true);
    if($responsableA["error"]=="false"){
        $dev['mensaje'] = "No se pudo encontro responsables";
        $dev['error']   = "false";
    }

    $empresaA = ListarEmpresasIdNombre("", "", "nombre", "DESC", "", "", "", true);
    if($empresaA["error"]=="false"){
        $dev['mensaje'] = "No se pudo encontro empresas";
        $dev['error']   = "false";
    }
    $sqln = "SELECT
  op.numeroorden
FROM
  `ordenproduccion` op
WHERE
  op.idordenproduccion = '$id'";
    //echo $sqln;
    $numerorodenA = findBySqlReturnCampoUnique($sqln, true, true, "numeroorden");
    $orden = $numerorodenA['resultado'];
    $dev['numeroproduccion'] = "$orden";

    //    $dev['resultado'] = $productoA['error'];
    if(($productoA["error"]=="true")&&($clienteA["error"]=="true")&&($responsableA["error"]=="true")&&($empresaA["error"]=="true")&&($telaA["error"]=="true")&&($colorA["error"]=="true")){
        $dev['mensaje'] = "Todo Ok";
        $dev['error'] = "true";
        $dev['productoM'] = $productoA['resultado'];
        $dev['clienteM']= $clienteA['resultado'];
        $dev['responsableM']= $responsableA['resultado'];
        $dev['empresaM']= $empresaA['resultado'];
        $dev['telaM']= $telaA['resultado'];
        $dev['colorM']= $colorA['resultado'];


    }
    $sqldetalle = "
SELECT
  det.iddetalleorden AS id,
  det.detalle,
  det.talla AS unidad,
  det.cantidad,
  det.preciounitario,
  det.total AS preciototal,
  det.idtela AS tela,
  det.idcolor AS color,
  det.detalleitem AS detalle1,
  det.detallebordado,
  det.detallecostura,
  det.estado
FROM
  detalleorden det
WHERE
  det.idordenproduccion = '$id'
";
    $detalleM = getTablaToArrayOfSQL($sqldetalle);
    $dev['detalleM'] = $detalleM['resultado'];

    $sqlorden = "
SELECT
  op.idordenproduccion,
  op.fechaentrega,
  op.fecharecepcion,
  op.idcliente,
  op.idempresa,
  op.idresponsable,
  op.montototal,
  op.montoapagar AS montoacuenta,
  op.saldo,
  op.numerorecibo
FROM
  `ordenproduccion` op
WHERE
  op.idordenproduccion = '$id';
";
    $ordenM = getTablaToArrayOfSQL($sqlorden);
    $dev['orden'] = $ordenM['resultado'];




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

function getSqlDeleteOrdenesItem($idItemOrden){
    return "delete from detalleorden WHERE iddetalleorden = '$idItemOrden';";
}
function EliminarOrdenProduccionItem($idItemOrden,$callback,$return=false)
{
    $dev['mensaje'] = "";
    $dev['error']   = "";

    //    $sql[] = getSqlDeleteLinea_marca($idlinea);
    $sql[] =  getSqlDeleteOrdenesItem($idItemOrden);

    //    MostrarConsulta($sql);
    if(ejecutarConsultaSQLBeginCommit($sql))
    {
        $dev['mensaje'] = "Se Elimino correctamente";
        $dev['error'] = "true";
        $dev['resultado'] = "";
    }
    else
    {
        $dev['mensaje'] = "No se puede Eliminar...  Existe el valor en alguna tabla";
        $dev['error'] = "false";
        $dev['resultado'] = "";
    }
    if($return == true)
    {
        return $dev;
    }
    else
    {
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
    }
}

function getSqlDeleteOrdenes($idordenes){
    return "delete from ordenproduccion WHERE idordenproduccion = '$idordenes';";
}
function EliminarOrdenProduccion($idorden,$callback,$return=false)
{
    $dev['mensaje'] = "";
    $dev['error']   = "";

    //    $sql[] = getSqlDeleteLinea_marca($idlinea);
    $sql[] =  getSqlDeleteOrdenes($idorden);

    //    MostrarConsulta($sql);
    if(ejecutarConsultaSQLBeginCommit($sql))
    {
        $dev['mensaje'] = "Se Elimino correctamente";
        $dev['error'] = "true";
        $dev['resultado'] = "";
    }
    else
    {
        $dev['mensaje'] = "No se puede Eliminar...  Existe el valor en alguna tabla";
        $dev['error'] = "false";
        $dev['resultado'] = "";
    }
    if($return == true)
    {
        return $dev;
    }
    else
    {
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
    }
}
?>