<?php
function getSqlNewResponsables($idresponsable, $idempresa, $idciudad, $codigo, $nombre, $apellido1, $direccion, $email, $fax, $numero, $estado, $telefono, $celular, $observacion,$fechanacimiento, $return){
    $setC[0]['campo'] = 'idresponsable';
    $setC[0]['dato'] = $idresponsable;
    $setC[1]['campo'] = 'idempresa';
    $setC[1]['dato'] = $idempresa;
    $setC[2]['campo'] = 'idciudad';
    $setC[2]['dato'] = $idciudad;
    $setC[3]['campo'] = 'codigo';
    $setC[3]['dato'] = $codigo;
    $setC[4]['campo'] = 'nombre';
    $setC[4]['dato'] = $nombre;
    $setC[5]['campo'] = 'apellido1';
    $setC[5]['dato'] = $apellido1;
    $setC[6]['campo'] = 'direccion';
    $setC[6]['dato'] = $direccion;
    $setC[7]['campo'] = 'email';
    $setC[7]['dato'] = $email;
    $setC[8]['campo'] = 'fax';
    $setC[8]['dato'] = $fax;
    $setC[9]['campo'] = 'numero';
    $setC[9]['dato'] = $numero;
    $setC[10]['campo'] = 'estado';
    $setC[10]['dato'] = $estado;
    $setC[11]['campo'] = 'telefono';
    $setC[11]['dato'] = $telefono;
    $setC[12]['campo'] = 'celular';
    $setC[12]['dato'] = $celular;
    $setC[13]['campo'] = 'observacion';
    $setC[13]['dato'] = $observacion;
    $setC[14]['campo'] = 'fechanacimiento';
    $setC[14]['dato'] = $fechanacimiento;
    $sql2 = generarInsertValues($setC);
    return "INSERT INTO responsables ".$sql2;
}
function ListarResponsables($start, $limit, $sort, $dir, $callback, $_dc, $where = '', $return = false){

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
  res.idresponsable,
  res.idempresa,
  res.idciudad,
  res.nombre,
  res.codigo,
  res.direccion,
  res.apellido1,
  res.email,
  res.telefono,
  res.celular,
  res.fax,
  emp.nombre AS empresa,
  ciu.nombre AS ciudad
FROM
  responsables res,
  empresas emp,
  `ciudades` ciu
WHERE
  res.idempresa = emp.idempresa AND
  ciu.idciudad = res.idciudad $order
";
    }
    else{
        $sql ="
SELECT
  res.idresponsable,
  res.idempresa,
  res.idciudad,
  res.nombre,
  res.codigo,
  res.direccion,
  res.apellido1,
  res.email,
  res.telefono,
  res.celular,
  res.fax,
  emp.nombre AS empresa,
  ciu.nombre AS ciudad
FROM
  responsables res,
  empresas emp,
  `ciudades` ciu
WHERE
  res.idempresa = emp.idempresa AND
  ciu.idciudad = res.idciudad AND $where $order
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

function ListarResponsablesIdNombre($start, $limit, $sort, $dir, $callback, $_dc, $where = '', $return = false){

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
  res.idresponsable,
  res.nombre,
res.idempresa
FROM
  responsables res $order
";
    }
    else{
        $sql ="
SELECT
  res.idresponsable,
  res.nombre,
res.idempresa
FROM
  responsables res WHERE $where $order
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

function CargarNuevoResponsable($callback, $_dc, $where = '', $return = false){

    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";

    $ciudadA =  ListarCiudad("", "", "nombre", "DESC", "", "", "",true);
    if($ciudadA["error"]==false){
        $dev['mensaje'] = "No se pudo encontro ciudades";
        $dev['error']   = "false";
        $dev['resultado'] = "";

    }
    $empresaA =  ListarEmpresas("", "", "nombre", "DESC", "", "", "",true);
    if($empresaA["error"]==false){
        $dev['mensaje'] = "No se pudo encontro ciudades";
        $dev['error']   = "false";
        $dev['resultado'] = "";

    }


    if(($ciudadA["error"]==true)&&($empresaA["error"]==true)){
        $dev['mensaje'] = "Todo Ok";
        $dev['error']   = "true";
        $value["ciudadM"] = $ciudadA['resultado'];
        $value["empresaM"] = $empresaA['resultado'];
        $dev['resultado'] = $value;

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
function GuardarNuevoResponsable($callback, $_dc, $where = '', $return = false){
    $nombre = $_GET['nombre'];
    $codigo = $_GET['codigo'];
    $idempresa = $_GET['empresa'];
    $telefono = $_GET['telefono'];
    $celular =  $_GET['celular'];
    $apellido1 = $_GET['apellido'];
    $direccion = $_GET['direccion'];
    $email = $_GET['email'];
    $fax =$_GET['fax'];
    $idciudad = $_GET['ciudad'];
    $observacion = $_GET['observacion'];
    $fechanacimiento = $_GET['fecha'];
    $numeroA = findUltimoID("responsables", "numero", true);
    $numero = $numeroA['resultado']+1;
    $idresponsable = "res-".$numero;
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
    $codigoB = verificarValidarText($codigo, false, "responsables", "codigo");
    if($codigoB["error"]==false){
        $dev['mensaje'] = "Error en el campo codigo: ".$codigoB['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $ciudadA = validarText($idciudad, true);
    if($ciudadA["error"]==false){
        $dev['mensaje'] = "Error en el campo ciudad: ".$ciudadA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $empresaA = validarText($idempresa, true);
    if($empresaA["error"]==false){
        $dev['mensaje'] = "Error en el campo Empresa: ".$empresaA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $estado = "Activo";

    $sql[] = getSqlNewResponsables($idresponsable, $idempresa, $idciudad, $codigo, $nombre, $apellido1, $direccion, $email, $fax, $numero, $estado, $telefono, $celular, $observacion, $fechanacimiento,$return);
    //        MostrarConsulta($sql);
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
function BuscarResponsablePorId($callback, $_dc,$idresponsable,$return){
    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";

    $ciudadA =  ListarCiudad("", "", "nombre", "DESC", "", "", "",true);
    if($ciudadA["error"]==false){
        $dev['mensaje'] = "No se pudo encontro ciudades";
        $dev['error']   = "false";
        $dev['resultado'] = "";

    }
    $empresaA =  ListarEmpresas("", "", "nombre", "DESC", "", "", "",true);
    if($empresaA["error"]==false){
        $dev['mensaje'] = "No se pudo encontro ciudades";
        $dev['error']   = "false";
        $dev['resultado'] = "";

    }


    if(($ciudadA["error"]==true)&&($empresaA["error"]==true)){
        $dev['mensaje'] = "Todo Ok";
        $dev['error']   = "true";
        $value["ciudadM"] = $ciudadA['resultado'];
        $value["empresaM"] = $empresaA['resultado'];


    }


    $sql ="
SELECT 
  res.idresponsable,
  res.idempresa,
  res.idciudad,
  res.codigo,
  res.nombre,
  res.apellido1 AS apellido,
  res.direccion,
  res.email,
  res.fax,
  res.telefono,
  res.celular,
  res.observacion,
  res.fechanacimiento AS fecha
FROM
  responsables res
WHERE
  res.idresponsable =  '$idresponsable'
        ";

    if($idresponsable != null)
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
function getSqlUpdateResponsables($idresponsable,$idempresa,$idciudad,$codigo,$nombre,$apellido1,$direccion,$email,$fax,$numero,$estado,$telefono,$celular,$observacion, $fechanacimiento,$return){
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
    $setC[9]['campo'] = 'celular';
    $setC[9]['dato'] = $celular;
    $setC[10]['campo'] = 'observacion';
    $setC[10]['dato'] = $observacion;
    $setC[11]['campo'] = 'idempresa';
    $setC[11]['dato'] = $idempresa;
    $setC[12]['campo'] = 'idciudad';
    $setC[12]['dato'] = $idciudad;
    $setC[13]['campo'] = 'fechanacimiento';
    $setC[13]['dato'] = $fechanacimiento;

    $set = generarSetsUpdate($setC);
    $wher[0]['campo'] = 'idresponsable';
    $wher[0]['dato'] = $idresponsable;

    $where = generarWhereUpdate($wher);
    return "UPDATE responsables SET ".$set." WHERE ".$where;
}
function GuardarEditarResponsable($callback, $_dc, $idresponsable, $return = false){
    $nombre = $_GET['nombre'];
    $codigo = $_GET['codigo'];
    $idempresa = $_GET['empresa'];
    $telefono = $_GET['telefono'];
    $celular =  $_GET['celular'];
    $apellido1 = $_GET['apellido'];
    $direccion = $_GET['direccion'];
    $email = $_GET['email'];
    $fax =$_GET['fax'];
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
    $codigoB = verificarValidarTextUnicoEdit("idresponsable", $idresponsable, false, "responsables", "codigo", $codigo);
    if($codigoB["error"]==false){
        $dev['mensaje'] = "Error en el campo codigo: ".$codigoB['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $ciudadA = validarText($idciudad, true);
    if($ciudadA["error"]==false){
        $dev['mensaje'] = "Error en el campo ciudad: ".$ciudadA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $empresaA = validarText($idempresa, true);
    if($empresaA["error"]==false){
        $dev['mensaje'] = "Error en el campo Empresa: ".$empresaA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }



    $sql[] = getSqlUpdateResponsables($idresponsable,$idempresa,$idciudad,$codigo,$nombre,$apellido1,$direccion,$email,$fax,$numero,$estado,$telefono,$celular,$observacion, $fechanacimiento,$return);
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
function getSqlDeleteResponsables($idresponsables){
    return "DELETE FROM responsables WHERE idresponsable ='$idresponsables';";
}
function EliminarResponsable($callback, $_dc,$idresponsable, $return= 'true'){

    $dev['mensaje'] = "";
    $dev['error']   = "";

    //    $sql[] = getSqlDeleteLinea_marca($idlinea);

    $sql[] =  getSqlDeleteResponsables($idresponsable);
    //
    //        MostrarConsulta($sql);
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