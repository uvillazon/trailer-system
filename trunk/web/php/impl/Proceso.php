<?php
function ListarProcesosIdNombre($start, $limit, $sort, $dir, $callback, $_dc, $where = '', $return = false){

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
  pro.idproceso AS id,

  pro.nombre

FROM
  procesos pro
    $order
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

function ListarProcesosIdNombreProcesos($start, $limit, $sort, $dir, $callback, $_dc, $where = '', $return = false){

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
  pro.idproceso AS id,

  pro.nombre

FROM
  procesos pro where tipoproceso = 'proceso'
    $order
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
function ListarProcesos($start, $limit, $sort, $dir, $callback, $_dc, $where = '', $return = false){

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
  pro.idproceso,
  pro.codigo,
  pro.nombre,
  pro.detalle,
  pro.tipoproceso AS tipo,
  pro.numero,
  pro.estado,
  pro.item
FROM
  procesos pro
    $order
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
function getSqlNewProcesos($idproceso, $codigo, $nombre, $detalle, $idempleado, $tipoproceso, $numero, $estado, $item,$cuenta,$formula, $return){
    $setC[0]['campo'] = 'idproceso';
    $setC[0]['dato'] = $idproceso;
    $setC[1]['campo'] = 'codigo';
    $setC[1]['dato'] = $codigo;
    $setC[2]['campo'] = 'nombre';
    $setC[2]['dato'] = $nombre;
    $setC[3]['campo'] = 'detalle';
    $setC[3]['dato'] = $detalle;
    $setC[4]['campo'] = 'idempleado';
    $setC[4]['dato'] = $idempleado;
    $setC[5]['campo'] = 'tipoproceso';
    $setC[5]['dato'] = $tipoproceso;
    $setC[6]['campo'] = 'numero';
    $setC[6]['dato'] = $numero;
    $setC[7]['campo'] = 'estado';
    $setC[7]['dato'] = $estado;
    $setC[8]['campo'] = 'item';
    $setC[8]['dato'] = $item;
    $setC[9]['campo'] = 'idcuenta';
    $setC[9]['dato'] = $cuenta;
    $setC[10]['campo'] = 'formula';
    $setC[10]['dato'] = $formula;
    $sql2 = generarInsertValues($setC);
    return "INSERT INTO procesos ".$sql2;
}
function GuardarNuevoProceso($callback, $_dc, $where = '', $return = false){
    $nombre = $_GET['nombre'];
    $codigo = $_GET['codigo'];
    $tipoproceso =$_GET['tipo'];
    $detalle = $_GET['detalle'];
    $idempleado = $_GET['encargado'];
    $estado = $_GET['estado'];
    $item=$_GET['item'];
    $numeroA = findUltimoID("procesos", "numero", true);
    $numero = $numeroA['resultado']+1;
    $idproceso = "prc-".$numero;
    $codigoA =  validarText($codigo, true);
    $formula = $_GET['formula'];
    if($codgoA["error"]==true){
        $dev['mensaje'] = "Error en el campo codigo: ".$codigoA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $nombreA = validarText($nombre, true);
    if($nombreA["error"]==false){
        $dev['mensaje'] = "Error en el campo nombre: ".$nombreA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }

    if($tipoproceso == "items"){
        $itemA = validarText($item, true);
        if($itemA["error"]==false){
            $dev['mensaje'] = "Error en el campo item: ".$itemA['mensaje'];
            $json = new Services_JSON();
            $output = $json->encode($dev);
            print($output);
            exit;
        }
        $sqlitem = "SELECT
  cue.nombre
FROM
  cuentaprincipal cue
WHERE
  cue.idcuentaprincipal = '$item'";
        $cuentaA = findBySqlReturnCampoUnique($sqlitem, true, true, 'nombre');
        $cuenta = $cuentaA['resultado'];
    }
    else{
        $idempleadoA = validarText($idempleado, true);
        if($idempleadoA["error"]==false){
            $dev['mensaje'] = "Error en el campo encargado: ".$idempleadoA['mensaje'];
            $json = new Services_JSON();
            $output = $json->encode($dev);
            print($output);
            exit;
        }
    }

    // $estado = "Activo";
    $sql[] = getSqlNewProcesos($idproceso, $codigo, $nombre, $detalle, $idempleado, $tipoproceso, $numero, $estado, $cuenta,$item,$formula, $return);

    //        MostrarConsulta($sql);exit();
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
function CargarNuevoProceso($callback, $_dc, $where = '', $return = false){

    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";

    $encargadoA =  ListarEmpleados("", "", "nombre","DESC", "", "", "", true);
    if($encargadoA["error"]=="false"){
        $dev['mensaje'] = "No se pudo encontro cargos";
        $dev['error']   = "false";
        $dev['resultado'] = "";

    }
    $itemsA = ListarCuentasPrincipalesIdNombre("", "", "nombre","DESC", "", "", "", true);
    if($itemsA["error"]=="false"){
        $dev['mensaje'] = "No se pudo encontro cuentas";
        $dev['error']   = "false";
        $dev['resultado'] = "";

    }

    if(($itemsA["error"]=="true")&&($encargadoA["error"]=="true")){
        $dev['mensaje'] = "Todo Ok";
        $dev['error']   = "true";
        $value["encargadoM"] = $encargadoA['resultado'];
        $value['itemM'] = $itemsA['resultado'];
        $dev["resultado" ] = $value;
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
function BuscarProcesoPorId($callback, $_dc, $idproceso, $return = false){
    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";
    $encargadoA =  ListarEmpleados("", "", "nombre", "DESC", "", "","",true);


    if($encargadoA["error"]==false){
        $dev['mensaje'] = "No se pudo encontro encargado";
        $dev['error']   = "false";
        $dev['resultado'] = "";

    }
    $itemsA = ListarCuentasPrincipalesIdNombre("", "", "nombre","DESC", "", "", "", true);
    if($itemsA["error"]=="false"){
        $dev['mensaje'] = "No se pudo encontro cuentas";
        $dev['error']   = "false";
        $dev['resultado'] = "";

    }

    if(($itemsA["error"]=="true")&&($encargadoA["error"]=="true")){
        $dev['mensaje'] = "Todo Ok";
        $dev['error']   = "true";
        $value["encargadoM"] = $encargadoA['resultado'];
        $value['itemM'] = $itemsA['resultado'];
        //        $dev["resultado" ] = $value;
    }

    $sql ="
        SELECT
  proc.idproceso,
  proc.codigo,
  proc.nombre,
  proc.detalle,
  proc.tipoproceso AS tipo,
  proc.numero,
  proc.estado,
  proc.idcuenta as item,
proc.idempleado,
  proc.idempleado as encargado,
  proc.formula
FROM
  procesos proc
WHERE
  proc.idproceso ='$idproceso'
        ";
    //echo $sql;
    //exit();
    if($idproceso != null)
    {
        if($link=new BD)
        {
            if($link->conectar())
            {
                if($re = $link->consulta($sql))
                {
                    if($fi = mysql_fetch_array($re))
                    {
                        for($i = 0; $i< mysql_num_fields($re); $i++)
                        {
                            if(mysql_field_type($re, $i) == "real")
                            {
                                $value{mysql_field_name($re, $i)}= redondear($fi[$i]);
                            }
                            else
                            {
                                $value{mysql_field_name($re, $i)}= $fi[$i];
                            }
                        }
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
                    $dev['mensaje'] = "No se encontro datos en la consulta";
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
    }
    else
    {
        $dev['mensaje'] = "El codigo de producto es nulo";
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
function getSqlUpdateProcesos($idproceso,$codigo,$nombre,$detalle,$idempleado,$tipoproceso,$numero,$estado,$item,$cuenta, $formula,$return){
    $setC[0]['campo'] = 'codigo';
    $setC[0]['dato'] = $codigo;
    $setC[1]['campo'] = 'nombre';
    $setC[1]['dato'] = $nombre;
    $setC[2]['campo'] = 'detalle';
    $setC[2]['dato'] = $detalle;
    $setC[3]['campo'] = 'tipoproceso';
    $setC[3]['dato'] = $tipoproceso;
    $setC[4]['campo'] = 'numero';
    $setC[4]['dato'] = $numero;
    $setC[5]['campo'] = 'estado';
    $setC[5]['dato'] = $estado;
    $setC[6]['campo'] = 'item';
    $setC[6]['dato'] = $item;
    $setC[7]['campo'] = 'idcuenta';
    $setC[7]['dato'] = $cuenta;
    $setC[8]['campo'] = 'formula';
    $setC[8]['dato'] = $formula;

    $set = generarSetsUpdate($setC);
    $wher[0]['campo'] = 'idproceso';
    $wher[0]['dato'] = $idproceso;
    $wher[1]['campo'] = 'idempleado';
    $wher[1]['dato'] = $idempleado;

    $where = generarWhereUpdate($wher);
    return "UPDATE procesos SET ".$set." WHERE ".$where;
}
function GuardarEditarProceso($callback,$_dc, $idproceso,$return=false){

    $nombre = $_GET['nombre'];
    $codigo = $_GET['codigo'];
    $estado = $_GET['estado'];
    $tipoproceso =$_GET['tipo'];
    $item = $_GET['item'];
    $detalle = $_GET['detalle'];
    $idempleado = $_GET['encargado'];
    $formula = $_GET['formula'];
    $nombreA = validarText($nombre, true);
    if($nombreA['error']==false){
        $dev['mensaje'] = "Error en el campo nombre: ".$nombreA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }

    $codigoA=validarText($codigo, true);
    if($codigoA['error']==false){
        $dev['mensaje'] = "Error en el campo codigo: ".$codigoA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $codigoB = verificarValidarTextUnicoEdit("idproceso",$idproceso,false, "procesos", "codigo",$codigo);
    if($codigoB["error"]==false){
        $dev['mensaje'] = "Error en el campo codigo: ".$codigoB['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    if($tipoproceso == "items"){
        $itemA = validarText($item, true);
        if($itemA["error"]==false){
            $dev['mensaje'] = "Error en el campo item: ".$itemA['mensaje'];
            $json = new Services_JSON();
            $output = $json->encode($dev);
            print($output);
            exit;
        }
        $sqlitem = "SELECT
  cue.nombre
FROM
  cuentaprincipal cue
WHERE
  cue.idcuentaprincipal = '$item'";
        $cuentaA = findBySqlReturnCampoUnique($sqlitem, true, true, 'nombre');
        $cuenta = $cuentaA['resultado'];
    }
    else{
        $idempleadoA = validarText($idempleado, true);
        if($idempleadoA["error"]==false){
            $dev['mensaje'] = "Error en el campo encargado: ".$idempleadoA['mensaje'];
            $json = new Services_JSON();
            $output = $json->encode($dev);
            print($output);
            exit;
        }
    }
    $sql[] = getSqlUpdateProcesos($idproceso,$codigo,$nombre,$detalle,$idempleado,$tipoproceso,$numero,$estado,$cuenta,$item,$formula, $return);
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
function getSqlDeleteProcesos($idprocesos){
    return "DELETE FROM procesos WHERE idproceso ='$idprocesos';";
}
function EliminarProceso($callback, $_dc, $idproceso,$return = false){
    $dev['mensaje'] = "";
    $dev['error']   = "";

    //    $sql[] = getSqlDeleteLinea_marca($idlinea);
    $sql[] = getSqlDeleteProcesos($idproceso);
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