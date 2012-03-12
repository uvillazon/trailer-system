<?php
function ListarProveedoresIdNombre($start, $limit, $sort, $dir, $callback, $_dc, $where = '', $return = false){

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
  prv.idproveedor,
  prv.nombre
FROM
  `proveedores` prv  $order
";
    }
    else{
        $sql ="
SELECT
  prv.idproveedor,
  prv.nombre
FROM
  `proveedores` prv $order
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
function ListarProveedores($start, $limit, $sort, $dir, $callback, $_dc, $where = '', $return = false){

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
  prov.idproveedor,
  prov.idciudad,
  prov.codigo,
  prov.nombre,
  prov.telefono,
  prov.representante,
  prov.direccion,
  prov.web,
  prov.email,
  prov.numero,
  ciu.nombre AS ciudad,
  ciu.idciudad
  
FROM
  proveedores prov,
  ciudades ciu
WHERE
  prov.idciudad = ciu.idciudad  $order
";
    }
    else{
        $sql ="
SELECT
  prov.idproveedor,
  prov.idciudad,
  prov.codigo,
  prov.nombre,
  prov.telefono,
  prov.representante,
  prov.direccion,
  prov.web,
  prov.email,
  prov.numero,
  ciu.nombre AS ciudad,
  ciu.idciudad,
FROM
  proveedores prov,
  ciudades ciu
WHERE
  prov.idciudad = ciu.idciudad AND $where $order
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
function CargarNuevaProveedor($callback, $_dc, $where = '', $return = false){

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
function getSqlNewProveedores($idproveedor, $idciudad, $codigo, $nombre, $telefono, $representante, $direccion, $web, $email, $numero, $pais, $observacion, $return){
$setC[0]['campo'] = 'idproveedor';
$setC[0]['dato'] = $idproveedor;
$setC[1]['campo'] = 'idciudad';
$setC[1]['dato'] = $idciudad;
$setC[2]['campo'] = 'codigo';
$setC[2]['dato'] = $codigo;
$setC[3]['campo'] = 'nombre';
$setC[3]['dato'] = $nombre;
$setC[4]['campo'] = 'telefono';
$setC[4]['dato'] = $telefono;
$setC[5]['campo'] = 'representante';
$setC[5]['dato'] = $representante;
$setC[6]['campo'] = 'direccion';
$setC[6]['dato'] = $direccion;
$setC[7]['campo'] = 'web';
$setC[7]['dato'] = $web;
$setC[8]['campo'] = 'email';
$setC[8]['dato'] = $email;
$setC[9]['campo'] = 'numero';
$setC[9]['dato'] = $numero;
$setC[10]['campo'] = 'pais';
$setC[10]['dato'] = $pais;
$setC[11]['campo'] = 'observacion';
$setC[11]['dato'] = $observacion;
$sql2 = generarInsertValues($setC);
return "INSERT INTO proveedores ".$sql2;
}

function GuardarNuevoProveedor($callback, $_dc, $where = '', $return = false){
    $nombre = $_GET['nombre'];
    $codigo = $_GET['codigo'];
    $telefono = $_GET['telefono'];
    $direccion = $_GET['direccion'];
    $email = $_GET['email'];
    $pais = $_GET['pais'];
    $web = $_GET['web'];
    $idciudad = $_GET['ciudad'];
    $observacion = $_GET['observacion'];
    $numeroA = findUltimoID("proveedores", "numero", true);
    $numero = $numeroA['resultado']+1;
    $idproveedor = "pro-".$numero;
    $representante = $_GET['representante'];
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
    $codigoB = verificarValidarText($codigo, false, "proveedores", "codigo");
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

    $sql[] = getSqlNewProveedores($idproveedor, $idciudad, $codigo, $nombre, $telefono, $representante, $direccion, $web, $email, $numero, $pais, $observacion, $return);
//       MostrarConsulta($sql);
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
function BuscarProveedorPorId($callback, $_dc,$idproveedor,$return){
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
  prov.idproveedor,
  prov.idciudad,
  prov.codigo,
  prov.nombre,
  prov.telefono,
  prov.representante,
  prov.direccion,
  prov.web,
  prov.email,
  prov.numero,
  prov.pais,
  prov.observacion
FROM
  proveedores prov
WHERE
  prov.idproveedor ='$idproveedor'
        ";

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
function getSqlUpdateProveedores($idproveedor,$idciudad,$codigo,$nombre,$telefono,$representante,$direccion,$web,$email,$numero,$pais,$observacion, $return){
$setC[0]['campo'] = 'codigo';
$setC[0]['dato'] = $codigo;
$setC[1]['campo'] = 'nombre';
$setC[1]['dato'] = $nombre;
$setC[2]['campo'] = 'telefono';
$setC[2]['dato'] = $telefono;
$setC[3]['campo'] = 'representante';
$setC[3]['dato'] = $representante;
$setC[4]['campo'] = 'direccion';
$setC[4]['dato'] = $direccion;
$setC[5]['campo'] = 'web';
$setC[5]['dato'] = $web;
$setC[6]['campo'] = 'email';
$setC[6]['dato'] = $email;
$setC[7]['campo'] = 'numero';
$setC[7]['dato'] = $numero;
$setC[8]['campo'] = 'pais';
$setC[8]['dato'] = $pais;
$setC[9]['campo'] = 'observacion';
$setC[9]['dato'] = $observacion;
$wher[1]['campo'] = 'idciudad';
$wher[1]['dato'] = $idciudad;

$set = generarSetsUpdate($setC);
$wher[0]['campo'] = 'idproveedor';
$wher[0]['dato'] = $idproveedor;


$where = generarWhereUpdate($wher);
return "UPDATE proveedores SET ".$set." WHERE ".$where;
}

function GuardarEditarProveedores($callback, $_dc, $idproveedor, $return = false){
    $nombre = $_GET['nombre'];
    $codigo = $_GET['codigo'];
    $responsable=$_GET['responsable'];
    $telefono = $_GET['telefono'];
    $pais=$_GET['pais'];
    $direccion = $_GET['direccion'];
    $web=$_GET['web'];
    $idciudad = $_GET['ciudad'];
    $observacion = $_GET['observacion'];
    $email=$_GET['email'];
    $idproveedor = $_GET['idproveedor'];
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
    
    $codigoB = verificarValidarTextUnicoEdit("idproveedor", $idproveedor, false, "proveedores", "codigo", $codigo);

    if($codigoB["error"]==false){
        $dev['mensaje'] = "Error en el campo codigo: ".$codigoB['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }

    $sql[] = getSqlUpdateProveedores($idproveedor,$idciudad,$codigo,$nombre,$telefono,$responsable,$direccion,$web,$email,$numero,$pais,$observacion, $return);
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

function getSqlDeleteProveedores($idproveedores){
return "DELETE FROM proveedores WHERE idproveedor ='$idproveedores';";
}

function EliminarProveedor($callback, $_dc,$idproveedor, $return= 'true'){

    $dev['mensaje'] = "";
    $dev['error']   = "";

    //    $sql[] = getSqlDeleteLinea_marca($idlinea);
    $sql[] = getSqlDeleteProveedores($idproveedor);
   
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