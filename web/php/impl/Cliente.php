<?php
function ListarClientes($start, $limit, $sort, $dir, $callback, $_dc, $where = '', $return = false){

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
  cli.idcliente,
  cli.codigo,
  cli.nombre,
  cli.apellido1 AS apellido,
  cli.direccion,
  cli.email,
  cli.telefono,
  cli.nit,
  ciu.nombre AS ciudad
FROM
  `clientes` cli,
  `ciudades` ciu
WHERE
  cli.idciudad = ciu.idciudad $order
";
    }
    else{
        $sql ="
SELECT
  cli.idcliente,
  cli.codigo,
  cli.nombre,
  cli.apellido1 AS apellido,
  cli.direccion,
  cli.email,
  cli.telefono,
  cli.nit,
  ciu.nombre AS ciudad
FROM
  `clientes` cli,
  `ciudades` ciu
WHERE
  cli.idciudad = ciu.idciudad AND $where $order
";
    }

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

function ListarClienteIdNombre($start, $limit, $sort, $dir, $callback, $_dc, $where = '', $return = false){

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
  cli.idcliente,
  cli.nombre
 
FROM
  `clientes` cli
 $order
";
    }
    else{
        $sql ="
SELECT
  cli.idcliente,
  cli.nombre

FROM
  `clientes` cli
WHERE
  $where $order
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

function CargarNuevoCliente($callback, $_dc, $where = '', $return = false){

    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";

    $ciudadA =  ListarCiudad("", "", "nombre", "DESC", "", "", "",true);
    if($ciudadA["error"]==false){
        $dev['mensaje'] = "No se pudo encontro ciudades";
        $dev['error']   = "false";
        $dev['resultado'] = "";

    }


    if($ciudadA["error"]==true){
        $dev['mensaje'] = "Todo Ok";
        $dev['error']   = "true";
        $value["ciudadM"] = $ciudadA['resultado'];
        $dev["resultado"] = $value;
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
function getSqlNewClientes($idcliente, $idciudad, $codigo, $nombre, $apellido1, $direccion, $email, $fax, $numero, $estado, $telefono, $observacion, $nit, $fechanacimiento,$return){
    $setC[0]['campo'] = 'idcliente';
    $setC[0]['dato'] = $idcliente;
    $setC[1]['campo'] = 'idciudad';
    $setC[1]['dato'] = $idciudad;
    $setC[2]['campo'] = 'codigo';
    $setC[2]['dato'] = $codigo;
    $setC[3]['campo'] = 'nombre';
    $setC[3]['dato'] = $nombre;
    $setC[4]['campo'] = 'apellido1';
    $setC[4]['dato'] = $apellido1;
    $setC[5]['campo'] = 'direccion';
    $setC[5]['dato'] = $direccion;
    $setC[6]['campo'] = 'email';
    $setC[6]['dato'] = $email;
    $setC[7]['campo'] = 'fax';
    $setC[7]['dato'] = $fax;
    $setC[8]['campo'] = 'numero';
    $setC[8]['dato'] = $numero;
    $setC[9]['campo'] = 'estado';
    $setC[9]['dato'] = $estado;
    $setC[10]['campo'] = 'telefono';
    $setC[10]['dato'] = $telefono;
    $setC[11]['campo'] = 'observacion';
    $setC[11]['dato'] = $observacion;
    $setC[12]['campo'] = 'nit';
    $setC[12]['dato'] = $nit;
    $setC[13]['campo'] = 'fechanacimiento';
    $setC[13]['dato'] = $fechanacimiento;
    $sql2 = generarInsertValues($setC);
    return "INSERT INTO clientes ".$sql2;
}
function GuardarNuevoCliente($callback, $_dc, $where = '', $return = false){
    $nombre = $_GET['nombre'];
    $codigo = $_GET['codigo'];
    $apellido1 = $_GET['apellido'];
    $nit = $_GET['nit'];
    $telefono = $_GET['telefono'];
    $fax =  $_GET['fax'];
    $direccion = $_GET['direccion'];
    $email = $_GET['email'];
    $idciudad = $_GET['ciudad'];
    $observacion = $_GET['observacion'];
    $numeroA = findUltimoID("clientes", "numero", true);
    $numero = $numeroA['resultado']+1;
    $fechanacimiento = $_GET['fecha'];
    $idcliente = "cli-".$numero;

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
    $codigoB = verificarValidarText($codigo, false, "clientes", "codigo");
    if($codigoB["error"]==false){
        $dev['mensaje'] = "Error en el campo codigo: ".$codigoB['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $estado = "Activo";
//    $sql[] = getSqlNewClientes($idcliente, $idciudad, $codigo, $nombre, $apellido1, $direccion, $email, $fax, $numero, $estado, $telefono, $observacion, $nit, $fechanacimiento, $return);
    $sql[] = getSqlNewClientes($idcliente, $idciudad, $codigo, $nombre, $apellido1, $direccion, $email, $fax, $numero, $estado, $telefono, $observacion, $nit, $fechanacimiento,$return);
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
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);

    }
}
function BuscarClientePorId($callback, $_dc,$idcliente,$return){
    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";

    $ciudadA =  ListarCiudad("", "", "nombre", "DESC", "", "", "",true);
    if($ciudadA["error"]==false){
        $dev['mensaje'] = "No se pudo encontro ciudades";
        $dev['error']   = "false";
        $dev['resultado'] = "";

    }

    $value["ciudadM"] = $ciudadA['resultado'];

    $sql ="
      SELECT
  cli.idcliente,
  cli.codigo,
  cli.nombre,
  cli.apellido1 AS apellido,
  cli.direccion,
  cli.email,
  cli.telefono,
  cli.nit,
  cli.idciudad,
  cli.fax,
  cli.observacion,
  cli.fechanacimiento AS fecha
FROM
  clientes cli
WHERE
  cli.idcliente = '$idcliente'
        ";

    if($idcliente != null)
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
function getSqlUpdateClientes($idcliente,$idciudad,$codigo,$nombre,$apellido1,$direccion,$email,$fax,$numero,$estado,$telefono,$observacion,$nit, $fechanacimiento,$return){
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
    $setC[12]['campo'] = 'fechanacimiento';
    $setC[12]['dato'] = $fechanacimiento;

    $set = generarSetsUpdate($setC);
    $wher[0]['campo'] = 'idcliente';
    $wher[0]['dato'] = $idcliente;

    $where = generarWhereUpdate($wher);
    return "UPDATE clientes SET ".$set." WHERE ".$where;
}
function GuardarEditarCliente($callback, $_dc, $idcliente, $return = false){
    $nombre = $_GET['nombre'];
    $codigo = $_GET['codigo'];
    $apellido1 = $_GET['apellido'];
    $nit = $_GET['nit'];
    $telefono = $_GET['telefono'];
    $fax =  $_GET['fax'];
    $direccion = $_GET['direccion'];
    $email = $_GET['email'];
    $idciudad = $_GET['ciudad'];
    $observacion = $_GET['observacion'];
    $fechanacimiento = $_GET['fecha'];

    $codigoA = validarText($codigo, true);
    if($codigoA["error"]==false){
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
    $idcliente = $_GET['idcliente'];
    $codigoB = verificarValidarTextUnicoEdit("idcliente", $idcliente, false, "clientes", "codigo", $codigo);

    if($codigoB["error"]==false){
        $dev['mensaje'] = "Error en el campo codigo: ".$codigoB['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }

    $sql[] = getSqlUpdateClientes($idcliente,$idciudad,$codigo,$nombre,$apellido1,$direccion,$email,$fax,$numero,$estado,$telefono,$observacion,$nit, $fechanacimiento,$return);
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
function getSqlDeleteClientes($idclientes){
    return "DELETE FROM clientes WHERE idcliente ='$idclientes';";
}
function EliminarCliente($callback, $_dc,$idcliente, $return= 'true'){

    $dev['mensaje'] = "";
    $dev['error']   = "";

    //    $sql[] = getSqlDeleteLinea_marca($idlinea);
    $sql[] =  getSqlDeleteClientes($idcliente);

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