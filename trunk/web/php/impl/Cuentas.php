<?php
function ListarCuentas($start, $limit, $sort, $dir, $callback, $_dc, $where = '', $return = false){

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

    $sql = "
SELECT
  cue.idcuenta,
  cue.nombre,
  cup.nombre as tipo
FROM
  cuenta cue,
  `cuentaprincipal` cup
WHERE
  cue.tipo = cup.idcuentaprincipal $order
";


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

function getSqlNewCuenta($idcuenta, $nombre, $tipo, $numero, $return){
    $setC[0]['campo'] = 'idcuenta';
    $setC[0]['dato'] = $idcuenta;
    $setC[1]['campo'] = 'nombre';
    $setC[1]['dato'] = $nombre;
    $setC[2]['campo'] = 'tipo';
    $setC[2]['dato'] = $tipo;
    $setC[3]['campo'] = 'numero';
    $setC[3]['dato'] = $numero;
    $sql2 = generarInsertValues($setC);
    return "INSERT INTO cuenta ".$sql2;
}

function CargarCuentaEgreso($callback, $_dc, $where = '', $return = false){

    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";

    $itemsA = ListarCuentasPrincipalesEgresoIdNombre("", "", "nombre","DESC", "", "", "", true);
    if($itemsA["error"]=="false"){
        $dev['mensaje'] = "No se pudo encontro cuentas";
        $dev['error']   = "false";
        $dev['resultado'] = "";

    }

    if(($itemsA["error"]=="true")){
        $dev['mensaje'] = "Todo Ok";
        $dev['error']   = "true";
        $value['cuentaM'] = $itemsA['resultado'];
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

function GuardarNuevaCuenta($callback, $_dc, $where = '', $return = false){
    $nombre = $_GET['nombre'];
    $cuenta = $_GET['cuenta'];
    $numeroA = findUltimoID("cuenta", "numero", true);
    $numero = $numeroA['resultado']+1;
    $idcuenta = "cue-".$numero;
    $nombreA = validarText($nombre, true);
    if($nombreA["error"]==false){
        $dev['mensaje'] = "Error en el campo nombre: ".$nombreA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $tipoA = validarText($cuenta, true);
    if($tipoA["error"]==false){
        $dev['mensaje'] = "Error en el campo tipo: ".$tipoA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }

    $sql[] = getSqlNewCuenta($idcuenta, $nombre, $cuenta, $numero, $return);
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
function BuscarCuentaPorId($callback, $_dc,$idcuenta,$return){
    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";
    $itemsA = ListarCuentasPrincipalesEgresoIdNombre("", "", "nombre","DESC", "", "", "", true);
    if($itemsA["error"]=="false"){
        $dev['mensaje'] = "No se pudo encontro cuentas";
        $dev['error']   = "false";
        $dev['resultado'] = "";

    }

    if(($itemsA["error"]=="true")){
        $dev['mensaje'] = "Todo Ok";
        $dev['error']   = "true";
        $value['cuentaM'] = $itemsA['resultado'];
        $dev["resultado" ] = $value;
    }
    

    $sql ="
      SELECT
  cue.idcuenta,
  cue.nombre,
  cue.tipo
FROM
  cuenta cue
WHERE
  cue.idcuenta = '$idcuenta'
        ";

    if($idcuenta != null)
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
function getSqlUpdateClientes($idcliente,$idciudad,$codigo,$nombre,$apellido1,$direccion,$email,$fax,$numero,$estado,$telefono,$observacion,$nit, $return){
    $setC[0]['campo'] = 'codigo';
    $setC[0]['dato'] = $codigo;
    $setC[1]['campo'] = 'nombre';
    $setC[1]['dato'] = $nombre;
    $setC[2]['campo'] = 'apellido1';
    $setC[2]['dato'] = $apellido1;
    $setC[3]['campo'] = 'direccion';
    $setC[3]['dato'] = $direccion;
    $setC[4]['campo'] = 'email';
    $setC[4]['dato'] = $email;
    $setC[5]['campo'] = 'fax';
    $setC[5]['dato'] = $fax;
    $setC[6]['campo'] = 'numero';
    $setC[6]['dato'] = $numero;
    $setC[7]['campo'] = 'estado';
    $setC[7]['dato'] = $estado;
    $setC[8]['campo'] = 'telefono';
    $setC[8]['dato'] = $telefono;
    $setC[9]['campo'] = 'observacion';
    $setC[9]['dato'] = $observacion;
    $setC[10]['campo'] = 'nit';
    $setC[10]['dato'] = $nit;
    $setC[11]['campo'] = 'idciudad';
    $setC[11]['dato'] = $idciudad;

    $set = generarSetsUpdate($setC);
    $wher[0]['campo'] = 'idcliente';
    $wher[0]['dato'] = $idcliente;

    $where = generarWhereUpdate($wher);
    return "UPDATE clientes SET ".$set." WHERE ".$where;
}
function getSqlUpdateCuenta($idcuenta,$nombre,$tipo,$numero, $return){
    $setC[0]['campo'] = 'nombre';
    $setC[0]['dato'] = $nombre;
    $setC[1]['campo'] = 'tipo';
    $setC[1]['dato'] = $tipo;
    $setC[2]['campo'] = 'numero';
    $setC[2]['dato'] = $numero;

    $set = generarSetsUpdate($setC);
    $wher[0]['campo'] = 'idcuenta';
    $wher[0]['dato'] = $idcuenta;

    $where = generarWhereUpdate($wher);
    return "UPDATE cuenta SET ".$set." WHERE ".$where;
}
function GuardarEditarCuenta($callback, $_dc, $idcuenta, $return = false){
    $nombre = $_GET['nombre'];
    $tipo = $_GET['cuenta'];
    $nombreA = validarText($nombre, true);
    if($nombreA["error"]==false){
        $dev['mensaje'] = "Error en el campo nombre: ".$nombreA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $tipoA = validarText($tipo, true);
    if($tipoA["error"]==false){
        $dev['mensaje'] = "Error en el campo tipo: ".$tipoA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $sql[] = getSqlUpdateCuenta($idcuenta,$nombre,$tipo,$numero, $return);
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
function getSqlDeleteCuenta($idcuenta){
    return "DELETE FROM cuenta WHERE idcuenta ='$idcuenta';";
}
function EliminarCuenta($callback, $_dc,$idcuenta, $return= false){

    $dev['mensaje'] = "";
    $dev['error']   = "";

    //    $sql[] = getSqlDeleteLinea_marca($idlinea);
    $sql[] = getSqlDeleteCuenta($idcuenta);

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