<?php
function ListarEgresos($start, $limit, $sort, $dir, $callback, $_dc, $where = '', $return = false){

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
  egr.fecha,
  cue.nombre AS detalle,
  egr.idcuenta,
  egr.total,
  egr.idegreso,
  egr.idempleado,
  CONCAT(emp.codigo,' - ',emp.nombre) As empleado
FROM
  egreso egr,
  cuenta cue,
  `empleados` emp
WHERE
  egr.idcuenta = cue.idcuenta AND
  egr.idempleado = emp.idempleado $order
";
    }
    else{
        $sql ="
SELECT
  egr.fecha,
  cue.nombre AS detalle,
  egr.idcuenta,
  egr.total,
  egr.idegreso,
  egr.idempleado,
  CONCAT(emp.codigo,' - ',emp.nombre) As empleado
FROM
  egreso egr,
  cuenta cue,
  `empleados` emp
WHERE
  egr.idcuenta = cue.idcuenta AND
  egr.idempleado = emp.idempleado AND $where $order
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
function CargarPanelEgreso($callback, $_dc, $where = '', $return = false){

    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";

    $cuentaA =  ListarCuentas("", "", "nombre", "DESC", "", "", "",true);
    if($cuentaA["error"]==false){
        $dev['mensaje'] = "No se pudo encontro cuenta";
        $dev['error']   = "false";
        $dev['resultado'] = "";

    }
    $encargadoA =  ListarEmpleadosIdNombre("", "", "nombre", "DESC", "", "", "", true);
    if($encargadoA["error"]==false){
        $dev['mensaje'] = "No se pudo encontro cuenta".$encargadoA['mensaje'];
        $dev['error']   = "false";
        $dev['resultado'] = "";

    }
    if(($cuentaA["error"]==true)&&($encargadoA["error"]==true)){
        $dev['mensaje'] = "Todo Ok";
        $dev['error']   = "true";
        $dev["resultado"] ="";
        $dev["cuentaM"] = $cuentaA['resultado'];
        $dev['encargadoM']=$encargadoA['resultado'];
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
function getSqlNewEgreso($idegreso, $fecha, $hora, $idcuenta, $idempleado, $total, $detalle, $numero,$ordenproduccion, $return){
$setC[0]['campo'] = 'idegreso';
$setC[0]['dato'] = $idegreso;
$setC[1]['campo'] = 'fecha';
$setC[1]['dato'] = $fecha;
$setC[2]['campo'] = 'hora';
$setC[2]['dato'] = $hora;
$setC[3]['campo'] = 'idcuenta';
$setC[3]['dato'] = $idcuenta;
$setC[4]['campo'] = 'idempleado';
$setC[4]['dato'] = $idempleado;
$setC[5]['campo'] = 'total';
$setC[5]['dato'] = $total;
$setC[6]['campo'] = 'detalle';
$setC[6]['dato'] = $detalle;
$setC[7]['campo'] = 'numero';
$setC[7]['dato'] = $numero;
$setC[8]['campo'] = 'ordenproduccion';
$setC[8]['dato'] = $ordenproduccion;
$sql2 = generarInsertValues($setC);
return "INSERT INTO egreso ".$sql2;
}
function GuardarNuevoEgreso($callback, $_dc, $where = '', $return = false){
    $idcuenta = $_GET['cuenta'];
    $idempleado = $_GET['encargado'];
    $fecha = $_GET['fecha'];
    $orden = $_GET['ordenproduccion'];
    $hora = date("H:i:s");
    $numeroA = findUltimoID("egreso", "numero", true);
    $numero = $numeroA['resultado']+1;
    $idegreso = "egr-".$numero;
    if($fecha==""){
        $fecha = date("Y-m-d");
    }
    $total = $_GET['total'];
    $detalle = $_GET['detalle'];
    $cuenta = $_GET['cuenta'];
    $ordenA = verificarValidarText($orden, true, "ordenproduccion", "numeroorden");
    if($ordenA["error"]==false){
        $dev['mensaje'] = "Error la Orden De Produccion No Existe: ".$ordenA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $cuentaA = validarText($idcuenta, true);
    if($cuentaA["error"]==false){
        $dev['mensaje'] = "Error en el campo codigo: ".$cuentaA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $encargadoA = validarText($idempleado, true);
    if($encargadoA["error"]==false){
        $dev['mensaje'] = "Error en el campo nombre: ".$encargadoA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $sql[] = getSqlNewEgreso($idegreso, $fecha, $hora, $idcuenta, $idempleado, $total, $detalle, $numero, $orden,$return);
    //    MostrarConsulta($sql);
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
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);

    }
}
function BuscarEgresoPorId($callback, $_dc,$idegreso, $return = false){
    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";

    $cuentaA =  ListarCuentas("", "", "nombre", "DESC", "", "", "",true);
    if($cuentaA["error"]==false){
        $dev['mensaje'] = "No se pudo encontro cuenta";
        $dev['error']   = "false";
        $dev['resultado'] = "";

    }
    $encargadoA =  ListarEmpleadosIdNombre("", "", "nombre", "DESC", "", "", "", true);
    if($encargadoA["error"]==false){
        $dev['mensaje'] = "No se pudo encontro cuenta".$encargadoA['mensaje'];
        $dev['error']   = "false";
        $dev['resultado'] = "";

    }
    if(($cuentaA["error"]==true)&&($encargadoA["error"]==true)){
        $dev['mensaje'] = "Todo Ok";
        $dev['error']   = "true";
        $dev["resultado"] ="";
        $dev["cuentaM"] = $cuentaA['resultado'];
        $dev['encargadoM']=$encargadoA['resultado'];
    }
    $sql ="
     SELECT 
  egr.idegreso,
  egr.fecha,
  egr.total,
  egr.detalle,
  cue.nombre
FROM
  egreso egr,
  cuenta cue
WHERE
  egr.idcuenta = cue.idcuenta AND 
  egr.idegreso= '$idegreso'
        ";

    if($idegreso != null)
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
function getSqlUpdateEgreso($idegreso,$fecha,$hora,$idcuenta,$idempleado,$total,$detalle,$numero, $return){
    $setC[0]['campo'] = 'fecha';
    $setC[0]['dato'] = $fecha;
    $setC[1]['campo'] = 'hora';
    $setC[1]['dato'] = $hora;
    $setC[2]['campo'] = 'total';
    $setC[2]['dato'] = $total;
    $setC[3]['campo'] = 'detalle';
    $setC[3]['dato'] = $detalle;
    $setC[4]['campo'] = 'numero';
    $setC[4]['dato'] = $numero;

    $set = generarSetsUpdate($setC);
    $wher[0]['campo'] = 'idegreso';
    $wher[0]['dato'] = $idegreso;
    $wher[1]['campo'] = 'idcuenta';
    $wher[1]['dato'] = $idcuenta;
    $wher[2]['campo'] = 'idempleado';
    $wher[2]['dato'] = $idempleado;

    $where = generarWhereUpdate($wher);
    return "UPDATE egreso SET ".$set." WHERE ".$where;
}
function GuardarEditarEgresos($callback, $_dc, $idegreso, $return = false){
    $idcuenta = $_GET['idcuenta'];
    $idempleado = $_GET['idempleado'];
    $fecha = $_GET['fecha'];
    $hora = date("H:i:s");
    if($fecha==""){
        $fecha = date("Y-m-d");
    }
    $total = $_GET['total'];
    $detalle = $_GET['detalle'];
    $cuentaA = validarText($cuenta, true);
    if($cuentaA["error"]==false){
        $dev['mensaje'] = "Error en el campo codigo: ".$cuentaA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $encargadoA = validarText($encargado, true);
    if($encargadoA["error"]==false){
        $dev['mensaje'] = "Error en el campo nombre: ".$encargadoA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $sql[] = getSqlUpdateEgreso($idegreso,$fecha,$hora,$idcuenta,$idempleado,$total,$detalle,$numero, $return);
    //    MostrarConsulta($sql);
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
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);

    }
}
function getSqlDeleteEgreso($idegreso){
return "DELETE FROM egreso WHERE idegreso ='$idegreso';";
}
function EliminarEgreso($callback, $_dc,$idegreso, $return= 'true'){

    $dev['mensaje'] = "";
    $dev['error']   = "";

    //    $sql[] = getSqlDeleteLinea_marca($idlinea);
    $sql[] =  getSqlDeleteEgreso($idegreso);

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