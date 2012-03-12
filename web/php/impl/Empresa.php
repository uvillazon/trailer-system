<?php
function ListarEmpresas($start, $limit, $sort, $dir, $callback, $_dc, $where = '', $return = false){

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
  empr.idempresa,
  empr.idciudad,
  empr.codigo,
  empr.nombre,
  empr.direccion,
  empr.telefono,
  empr.fax,
  empr.numero,
  empr.fechareg,
  empr.email,
  empr.descripcion AS observacion,
  empr.estado,
  empr.nit,
  empr.responsable,
  ciu.nombre AS ciudad
FROM
  ciudades ciu,
  empresas empr
WHERE
  empr.idciudad = ciu.idciudad $order
";
    }
    else{
        $sql ="
SELECT
  empr.idempresa,
  empr.idciudad,
  empr.codigo,
  empr.nombre,
  empr.direccion,
  empr.telefono,
  empr.fax,
  empr.numero,
  empr.fechareg,
  empr.email,
  empr.descripcion AS observacion,
  empr.estado,
  empr.nit,
  empr.responsable,
  ciu.nombre AS ciudad
FROM
  ciudades ciu,
  empresas empr
WHERE
  empr.idciudad = ciu.idciudad AND $where $order
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

function ListarEmpresasIdNombre($start, $limit, $sort, $dir, $callback, $_dc, $where = '', $return = false){

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
  empr.idempresa,
  empr.nombre
FROM
 
  empresas empr
$order
";
    }
    else{
        $sql ="
SELECT
  empr.idempresa,
 
  empr.nombre
  
FROM
  
  empresas empr
WHERE
  $where $order
";
    }

//                echo $sql;
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

function CargarNuevaEmpresa($callback, $_dc, $where = '', $return = false){

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
function getSqlNewEmpresas($idempresa, $idciudad, $codigo, $nombre, $direccion, $telefono, $fax, $numero, $fechareg, $email, $descripcion, $estado, $nit, $responsable, $fechaaniversario,$paginaweb,$return){
    $setC[0]['campo'] = 'idempresa';
    $setC[0]['dato'] = $idempresa;
    $setC[1]['campo'] = 'idciudad';
    $setC[1]['dato'] = $idciudad;
    $setC[2]['campo'] = 'codigo';
    $setC[2]['dato'] = $codigo;
    $setC[3]['campo'] = 'nombre';
    $setC[3]['dato'] = $nombre;
    $setC[4]['campo'] = 'direccion';
    $setC[4]['dato'] = $direccion;
    $setC[5]['campo'] = 'telefono';
    $setC[5]['dato'] = $telefono;
    $setC[6]['campo'] = 'fax';
    $setC[6]['dato'] = $fax;
    $setC[7]['campo'] = 'numero';
    $setC[7]['dato'] = $numero;
    $setC[8]['campo'] = 'fechareg';
    $setC[8]['dato'] = $fechareg;
    $setC[9]['campo'] = 'email';
    $setC[9]['dato'] = $email;
    $setC[10]['campo'] = 'descripcion';
    $setC[10]['dato'] = $descripcion;
    $setC[11]['campo'] = 'estado';
    $setC[11]['dato'] = $estado;
    $setC[12]['campo'] = 'nit';
    $setC[12]['dato'] = $nit;
    $setC[13]['campo'] = 'responsable';
    $setC[13]['dato'] = $responsable;
    $setC[14]['campo'] = 'fechaaniversario';
    $setC[14]['dato'] = $fechaaniversario;
    $setC[15]['campo'] = 'paginaweb';
    $setC[15]['dato'] = $paginaweb;
    $sql2 = generarInsertValues($setC);
    return "INSERT INTO empresas ".$sql2;
}
function GuardarNuevaEmpresa($callback, $_dc, $where = '', $return = false){
    $nombre = $_GET['nombre'];
    $codigo = $_GET['codigo'];
    $nit = $_GET['nit'];
    $telefono = $_GET['telefono'];
    $fax =  $_GET['fax'];
    $direccion = $_GET['direccion'];
    $email = $_GET['email'];
    $idciudad = $_GET['ciudad'];
    $descripcion = $_GET['observacion'];
    $numeroA = findUltimoID("empresas", "numero", true);
    $numero = $numeroA['resultado']+1;
    $idempresa = "epr-".$numero;
    $responsable = $_GET['responsable'];
    $fecha = $_GET['fecha'];
    $web = $_GET['web'];

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
    $codigoB = verificarValidarText($codigo, false, "empresas", "codigo");
    if($codigoB["error"]==false){
        $dev['mensaje'] = "Error en el campo codigo: ".$codigoB['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $idciudadA= validarText($idciudad, true);
    if($idciudadA["error"]==false){
        $dev['mensaje'] = "Error en el campo ciudad: ".$idciudadA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }

    $estado = "Activo";
    $numeroB = findUltimoID("responsables", "numero", true);
    $numerores = $numeroB['resultado']+1;
    $idresponsable = "res-".$numerores;

    $sql[] = getSqlNewEmpresas($idempresa, $idciudad,  $codigo, $nombre, $direccion, $telefono, $fax, $numero, $fechareg, $email, $descripcion, $estado, $nit, $responsable,$fecha,$web, $return);
    $sql[] = getSqlNewResponsables($idresponsable, $idempresa, $idciudad, "", $responsable, "", "","", "", $numerores, $estado, "", "","","" ,$return);
//    $sql[] = getSqlNewResponsables($idresponsable, $idempresa, $idciudad, "", $responsable, "", "", "", "", $numerores, $estado, $return);

//       MostrarConsulta($sql);exit();
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
function BuscarEmpresaPorId($callback, $_dc,$idempresa,$return){
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
  emp.idempresa,
  emp.idciudad,
  emp.codigo,
  emp.nombre,
  emp.direccion,
  emp.telefono,
  emp.fax,
  emp.numero,
  emp.fechareg,
  emp.email,
  emp.descripcion  AS observacion,
  emp.estado,
  emp.nit,
  emp.responsable,
  emp.fechaaniversario as fecha,
  emp.paginaweb as web
FROM
  `empresas` emp
WHERE
  emp.idempresa = '$idempresa'
        ";
//echo $sql;
    if($idempresa != null)
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
function getSqlUpdateEmpresas($idempresa,$idciudad,$codigo,$nombre,$direccion,$telefono,$fax,$numero,$fechareg,$email,$descripcion,$estado,$nit,$responsable,$fecha,$web, $return){
    $setC[0]['campo'] = 'codigo';
    $setC[0]['dato'] = $codigo;
    $setC[1]['campo'] = 'nombre';
    $setC[1]['dato'] = $nombre;
    $setC[2]['campo'] = 'direccion';
    $setC[2]['dato'] = $direccion;
    $setC[3]['campo'] = 'telefono';
    $setC[3]['dato'] = $telefono;
    $setC[4]['campo'] = 'fax';
    $setC[4]['dato'] = $fax;
    $setC[5]['campo'] = 'numero';
    $setC[5]['dato'] = $numero;
    $setC[6]['campo'] = 'fechareg';
    $setC[6]['dato'] = $fechareg;
    $setC[7]['campo'] = 'email';
    $setC[7]['dato'] = $email;
    $setC[8]['campo'] = 'descripcion';
    $setC[8]['dato'] = $descripcion;
    $setC[9]['campo'] = 'estado';
    $setC[9]['dato'] = $estado;
    $setC[10]['campo'] = 'nit';
    $setC[10]['dato'] = $nit;
    $setC[11]['campo'] = 'responsable';
    $setC[11]['dato'] = $responsable;
    $setC[12]['campo'] = 'idciudad';
    $setC[12]['dato'] = $idciudad;
    $setC[13]['campo'] = 'fechaaniversario';
    $setC[13]['dato'] = $fecha;
    $setC[14]['campo'] = 'paginaweb';
    $setC[14]['dato'] = $web;

    $set = generarSetsUpdate($setC);
    $wher[0]['campo'] = 'idempresa';
    $wher[0]['dato'] = $idempresa;


    $where = generarWhereUpdate($wher);
    return "UPDATE empresas SET ".$set." WHERE ".$where;
}
function GuardarEditarEmpresa($callback, $_dc, $idempresa, $return = false){
    $nombre = $_GET['nombre'];
    $codigo = $_GET['codigo'];
    $responsable=$_GET['responsable'];
    $nit = $_GET['nit'];
    $telefono = $_GET['telefono'];
    $fax =  $_GET['fax'];
    $direccion = $_GET['direccion'];
    $email = $_GET['email'];
    $idciudad = $_GET['ciudad'];
    $descripcion = $_GET['observacion'];
    $numero= $_GET['numero'];
    $fecha = $_GET['fecha'];
    $web = $_GET['web'];
    $codigoA = validarText($codigo, true);
    if($codgoA["error"]==true){
        $dev['mensaje'] = "Error en el campo codigo: ".$codigoA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $idciudadA =validarText($idciudad, true);
    if($responsable["error"]==false){
        $dev['mensaje'] = "Error en el campo nombre: ".$idciudadA['mensaje'];
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
    $idempresa = $_GET['idempresa'];
    $codigoB = verificarValidarTextUnicoEdit("idempresa", $idempresa, false, "empresas", "codigo", $codigo);

    if($codigoB["error"]==false){
        $dev['mensaje'] = "Error en el campo codigo: ".$codigoB['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }

    $sql[] = getSqlUpdateEmpresas($idempresa,$idciudad,$codigo,$nombre,$direccion,$telefono,$fax,$numero,$fechareg,$email,$descripcion,$estado,$nit,$responsable,$fecha,$web, $return);
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

function getSqlDeleteEmpresas($idempresas){
    return "DELETE FROM empresas WHERE idempresa ='$idempresas';";
}
function getSqlDeleteResponsableEmpresas($idempresas){
    return "DELETE FROM responsables WHERE idempresa ='$idempresas';";
}

function EliminarEmpresa($callback, $_dc,$idempresas, $return= 'true'){

    $dev['mensaje'] = "";
    $dev['error']   = "";

    //    $sql[] = getSqlDeleteLinea_marca($idlinea);
    $sql[] = getSqlDeleteResponsableEmpresas($idempresas);
    $sql[] =  getSqlDeleteEmpresas($idempresas);
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