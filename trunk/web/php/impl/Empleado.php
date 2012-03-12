<?php
function ListarEmpleadosIdNombre($start, $limit, $sort, $dir, $callback, $_dc, $where = '', $return = false){

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
    if($where == "detalle"){
  $sql ="
SELECT
 emp.idempleado AS id,
 emp.nombre

FROM
  empleados emp $order

";
    }
    else
    {


        $sql ="
SELECT
 emp.idempleado ,
 emp.nombre

FROM
  empleados emp $order

";}

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


function ListarEmpleados($start, $limit, $sort, $dir, $callback, $_dc, $where = '', $return = false){

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
  emp.codigo,
  emp.nombre as nombres,
  emp.apellido1 as apellidos,
  emp.telefono as telefeno,
  emp.celular,
  emp.email,
  emp.idempleado
FROM
  empleados emp $order

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



function CargarNuevoEmpleado($callback, $_dc, $where = '', $return = false){

    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";

    $cargoA =  ListarCargos("", "", "nombre", "DESC", "", "", "",true);

    if($cargoA["error"]==false){
        $dev['mensaje'] = "No se pudo encontro cargos";
        $dev['error']   = "false";
        $dev['resultado'] = "";

    }
    $ciudadA =  ListarCiudad("", "", "nombre", "DESC", "", "", "",true);

    if($ciudadA["error"]==false){
        $dev['mensaje'] = "No se pudo encontro ciudad";
        $dev['error']   = "false";
        $dev['resultado'] = "";

    }



    if(($cargoA["error"]==true)||($ciudadA["error"]==true)){
        $dev['mensaje'] = "Todo Ok";
        $dev['error']   = "true";
        $value["cargosM"] = $cargoA['resultado'];
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
function getSqlNewEmpleados($idempleado, $idcargo, $idciudad, $codigo, $nombre, $apellido1, $direccion, $telefono, $celular, $estado, $numero, $descripcion, $email, $fechabaja, $fechainicio, $refnombre,$reftelefono,$return){
    $setC[0]['campo'] = 'idempleado';
    $setC[0]['dato'] = $idempleado;
    $setC[1]['campo'] = 'idcargo';
    $setC[1]['dato'] = $idcargo;
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
    $setC[7]['campo'] = 'telefono';
    $setC[7]['dato'] = $telefono;
    $setC[8]['campo'] = 'celular';
    $setC[8]['dato'] = $celular;
    $setC[9]['campo'] = 'estado';
    $setC[9]['dato'] = $estado;
    $setC[10]['campo'] = 'numero';
    $setC[10]['dato'] = $numero;
    $setC[11]['campo'] = 'descripcion';
    $setC[11]['dato'] = $descripcion;
    $setC[12]['campo'] = 'email';
    $setC[12]['dato'] = $email;
    $setC[13]['campo'] = 'fechabaja';
    $setC[13]['dato'] = $fechabaja;
    $setC[14]['campo'] = 'fechainicio';
    $setC[14]['dato'] = $fechainicio;
    $setC[15]['campo'] = 'refnombre';
    $setC[15]['dato'] = $refnombre;
    $setC[16]['campo'] = 'reftelefono';
    $setC[16]['dato'] = $reftelefono;

    $sql2 = generarInsertValues($setC);
    return "INSERT INTO empleados ".$sql2;
}
function GuardarNuevoEmpleado($callback, $_dc, $where = '', $return = false){
    $nombre = $_GET['nombre'];
    $codigo = $_GET['codigo'];
    $numeroA = findUltimoID("empleados", "numero", true);
    $numero = $numeroA['resultado']+1;
    $idempleado = "emp-".$numero;
    $apellido1 = $_GET['apellido'];
    $idcargo = $_GET['cargo'];
    $idciudad = $_GET['ciudad'];
    $telefono = $_GET['telefono'];
    $celular = $_GET['celular'];
    $direccion = $_GET['direccion'];
    $email = $_GET['email'];
    $fechainicio = $_GET['fechainicio'];
    $refnombre = $_GET['refnombre'];
    $reftelefono = $_GET['reftelefono'];
    if(($fechainicio == null)||($fechainicio == "")){
        $fechainicio = date("Y-m-d");
    }
    $codgoA = validarText($nombre, true);
    if($codgoA["error"]==false){
        $dev['mensaje'] = "Error en el campo codigo: ".$codgoA['mensaje'];
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
    $idcargoA = validarText($idcargo, true);
    if($idcargoA["error"]==false){
        $dev['mensaje'] = "Error en el campo cargo: ".$idcargoA['mensaje'];
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
    $codigoB = verificarValidarText($codigo, false, "empleados", "codigo");
    if($codigoB["error"]==false){
        $dev['mensaje'] = "Error en el campo codigo: ".$codigoB['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $estado = "Activo";
    $sql[] = getSqlNewEmpleados($idempleado, $idcargo, $idciudad, $codigo, $nombre, $apellido1, $direccion, $telefono, $celular, $estado, $numero, $descripcion, $email, $fechabaja, $fechainicio,$refnombre,$reftelefono, $return);
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
function BuscarEmpleadoPorId($callback, $_dc, $idempleado, $return = false){
    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";


    $cargoA =  ListarCargos("", "", "nombre", "DESC", "", "", "",true);

    if($cargoA["error"]==false){
        $dev['mensaje'] = "No se pudo encontro cargos";
        $dev['error']   = "false";
        $dev['resultado'] = "";

    }
    $ciudadA =  ListarCiudad("", "", "nombre", "DESC", "", "", "",true);

    if($ciudadA["error"]==false){
        $dev['mensaje'] = "No se pudo encontro ciudad";
        $dev['error']   = "false";
        $dev['resultado'] = "";

    }



    if(($cargoA["error"]==true)||($ciudadA["error"]==true)){
        $dev['mensaje'] = "Todo Ok";
        $dev['error']   = "true";
        $value["cargosM"] = $cargoA['resultado'];
        $value["ciudadM"] = $ciudadA['resultado'];
    }

    $sql ="
        SELECT
  emp.idempleado,
  emp.idcargo,
  emp.idciudad,
  emp.codigo,
  emp.nombre,
  emp.apellido1 AS apellido,
  emp.direccion,
  emp.telefono,
  emp.celular,
  emp.email,
  emp.fechainicio,
  emp.refnombre,
  emp.reftelefono
FROM
  empleados emp
WHERE
  emp.idempleado ='$idempleado'
        ";

    if($idempleado != null)
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
function getSqlUpdateEmpleados($idempleado,$idcargo,$idciudad,$codigo,$nombre,$apellido1,$direccion,$telefono,$celular,$estado,$numero,$descripcion,$email,$fechabaja,$fechainicio,$refnombre,$reftelefono, $return){
    $setC[0]['campo'] = 'codigo';
    $setC[0]['dato'] = $codigo;
    $setC[1]['campo'] = 'nombre';
    $setC[1]['dato'] = $nombre;
    $setC[2]['campo'] = 'apellido1';
    $setC[2]['dato'] = $apellido1;
    $setC[3]['campo'] = 'direccion';
    $setC[3]['dato'] = $direccion;
    $setC[4]['campo'] = 'telefono';
    $setC[4]['dato'] = $telefono;
    $setC[5]['campo'] = 'celular';
    $setC[5]['dato'] = $celular;
    $setC[6]['campo'] = 'estado';
    $setC[6]['dato'] = $estado;
    $setC[7]['campo'] = 'numero';
    $setC[7]['dato'] = $numero;
    $setC[8]['campo'] = 'descripcion';
    $setC[8]['dato'] = $descripcion;
    $setC[9]['campo'] = 'email';
    $setC[9]['dato'] = $email;
    $setC[10]['campo'] = 'fechabaja';
    $setC[10]['dato'] = $fechabaja;
    $setC[11]['campo'] = 'fechainicio';
    $setC[11]['dato'] = $fechainicio;
    $setC[12]['campo'] = 'idcargo';
    $setC[12]['dato'] = $idcargo;
    $setC[13]['campo'] = 'idciudad';
    $setC[13]['dato'] = $idciudad;
    $setC[14]['campo'] = 'refnombre';
    $setC[14]['dato'] = $refnombre;
    $setC[15]['campo'] = 'reftelefono';
    $setC[15]['dato'] = $reftelefono;

    $set = generarSetsUpdate($setC);
    $wher[0]['campo'] = 'idempleado';
    $wher[0]['dato'] = $idempleado;


    $where = generarWhereUpdate($wher);
    return "UPDATE empleados SET ".$set." WHERE ".$where;
}
function GuardarEditarEmpleado($callback, $_dc, $where = '', $return = false){
    $nombre = $_GET['nombre'];
    $codigo = $_GET['codigo'];
    $idempleado = $_GET['idempleado'];
    $apellido1 = $_GET['apellido'];
    $idcargo = $_GET['cargo'];
    $idciudad = $_GET['ciudad'];
    $telefono = $_GET['telefono'];
    $celular = $_GET['celular'];
    $direccion = $_GET['direccion'];
    $email = $_GET['email'];
    $fechainicio = $_GET['fechainicio'];
    $refnombre = $_GET['refnombre'];
    $reftelefono = $_GET['reftelefono'];
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
    $idcargoA = validarText($idcargo, true);
    if($idcargoA["error"]==false){
        $dev['mensaje'] = "Error en el campo cargo: ".$idcargoA['mensaje'];
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
    $codigoB = verificarValidarTextUnicoEdit("idempleado",$idempleado,false, "empleados", "codigo",$codigo);
    if($codigoB["error"]==false){
        $dev['mensaje'] = "Error en el campo codigo: ".$codigoB['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $sql[] = getSqlUpdateEmpleados($idempleado,$idcargo,$idciudad,$codigo,$nombre,$apellido1,$direccion,$telefono,$celular,$estado,$numero,$descripcion,$email,$fechabaja,$fechainicio, $refnombre,$reftelefono,$return);
    ////    MostrarConsulta($sql);
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


function getSqlDeleteEmpleados($idempleados){
    return "DELETE FROM empleados WHERE idempleado ='$idempleados';";
}
function EliminarEmpleado($callback, $_dc,$idempleado, $return= 'true'){

    $dev['mensaje'] = "";
    $dev['error']   = "";

    //    $sql[] = getSqlDeleteLinea_marca($idlinea);
    $sql[] =  getSqlDeleteEmpleados($idempleado);

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