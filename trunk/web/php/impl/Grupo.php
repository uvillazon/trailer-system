<?php
function ListarGrupos($start, $limit, $sort, $dir, $callback, $_dc, $where = '', $return = false){

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
  gru.idgrupo,
  gru.nombre,
  gru.descripcion
FROM
  grupos gru $order
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
function getSqlNewGrupos($idgrupo, $nombre, $descripcion, $numero, $return){
    $setC[0]['campo'] = 'idgrupo';
    $setC[0]['dato'] = $idgrupo;
    $setC[1]['campo'] = 'nombre';
    $setC[1]['dato'] = $nombre;
    $setC[2]['campo'] = 'descripcion';
    $setC[2]['dato'] = $descripcion;
    $setC[3]['campo'] = 'numero';
    $setC[3]['dato'] = $numero;
    $sql2 = generarInsertValues($setC);
    return "INSERT INTO grupos ".$sql2;
}

function GuardarNuevoGrupo($callback, $__dc, $where='', $return=false){
    $descripcion = $_GET['descripcion'];
    $nombre = $_GET['nombre'];
    //    $codigo = $_GET['numero'];
    $nombreA = validarText($nombre, true);
    if($nombreA["error"]==false){
        $dev['mensaje'] = "Error en el campo nombre: ".$nombreA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $numeroA = findUltimoID("grupos", "numero", true);
    $numero = $numeroA['resultado']+1;
    $idgrupo = "gru-".$numero;
    $sql[] = getSqlNewGrupos($idgrupo, $nombre, $descripcion, $numero, $return);
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
function BuscarGruposPorId($callback, $__dc, $idgrupo, $return=false){
    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";
    $sql ="
       SELECT
  gru.idgrupo,
  gru.nombre,
  gru.descripcion
FROM
  grupos gru
WHERE
  gru.idgrupo='$idgrupo'
        ";
    if($idgrupo != null)
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
function getSqlUpdateGrupos($idgrupo,$nombre,$descripcion,$numero,$costo, $return){
    $setC[0]['campo'] = 'nombre';
    $setC[0]['dato'] = $nombre;
    $setC[1]['campo'] = 'descripcion';
    $setC[1]['dato'] = $descripcion;
    $setC[2]['campo'] = 'numero';
    $setC[2]['dato'] = $numero;
    $setC[3]['campo'] = 'costo';
    $setC[3]['dato'] = $costo;

    $set = generarSetsUpdate($setC);
    $wher[0]['campo'] = 'idgrupo';
    $wher[0]['dato'] = $idgrupo;

    $where = generarWhereUpdate($wher);
    return "UPDATE grupos SET ".$set." WHERE ".$where;
}
function GuardarEditarGrupo($callback, $__dc, $idgrupo, $return=false){
    $dev['mensaje'] = "";
    $dev['error'] = "false";
    $dev['resultado'] = "";
    $descripcion = $_GET['descripcion'];
    $nombre = $_GET['nombre'];
    $nombreA = validarText($nombre, true);
    if($nombreA["error"]==false){
        $dev['mensaje'] = "Error en el campo nombre: ".$nombreA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }


    //    $codigoB = verificarValidarTextUnicoEdit("idgrupo", $idgrupo, false, "grupos", "codigo", $codigo);
    //    $codigoB = verificarValidarText($codigo, false, "cargos", "codigo");
    //    if($codigoB["error"]==false){
    //        $dev['mensaje'] = "Error en el campo codigo: ".$codigoB['mensaje'];
    //        $json = new Services_JSON();
    //        $output = $json->encode($dev);
    //        print($output);
    //        exit;
    //    }

    $sql[] = getSqlUpdateGrupos($idgrupo,$nombre,$descripcion,$numero,"", $return);

    if(ejecutarConsultaSQLBeginCommit($sql))
    {
        $dev['mensaje'] = "Se guardaron y actualizaron correctamente los datos";
        $dev['error'] = "true";
        $dev['resultado'] = "";
    }
    else
    {
        $dev['mensaje'] = "Ocurrio un error al actualizar o guardar los datos";
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
function getSqlDeleteGrupos($idgrupos){
    return "DELETE FROM grupos WHERE idgrupo ='$idgrupos';";
}
function EliminarGrupo($callback, $_dc, $idgrupo, $return = false){

    $dev['mensaje'] = "";
    $dev['error']   = "";

    $sql[] = getSqlDeleteComponentegrupoPorGrupo($idgrupo);
    $sql[] =  getSqlDeleteGrupos($idgrupo);

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