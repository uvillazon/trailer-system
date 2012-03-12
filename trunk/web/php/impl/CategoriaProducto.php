<?php
function ListarCategoriaProducto($start, $limit, $sort, $dir, $callback, $_dc, $where = '', $return = false){
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
  ctp.idcategoriaproducto,
  ctp.nombre,
  ctp.descripcion
FROM
  categoriaproducto ctp $order
";


    //                    echo $sql;
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
function getSqlNewCategoriaproducto($idcategoriaproducto, $nombre, $descripcion, $numero, $return){
    $setC[0]['campo'] = 'idcategoriaproducto';
    $setC[0]['dato'] = $idcategoriaproducto;
    $setC[1]['campo'] = 'nombre';
    $setC[1]['dato'] = $nombre;
    $setC[2]['campo'] = 'descripcion';
    $setC[2]['dato'] = $descripcion;
    $setC[3]['campo'] = 'numero';
    $setC[3]['dato'] = $numero;
    $sql2 = generarInsertValues($setC);
    return "INSERT INTO categoriaproducto ".$sql2;
}

function GuardarNuevaCategoriaProducto($callback, $_dc, $idcategoriaproducto, $return = false){


    $dev['mensaje'] = "";
    $dev['error'] = "false";
    $dev['resultado'] = "";
    $nombre = $_GET['nombre'];
    $descripcion =$_GET['descripcion'];
    $numeroA = findUltimoID("categoriaproducto", "numero", true);
    $numero = $numeroA['resultado']+1;
    $idcategoriaproducto = "ctp-".$numero;

    $nombreA = validarText($nombre, true);
    if($nombreA["error"]==false){
        $dev['mensaje'] = "Error en el campo nombre: ".$nombreA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }

    $sql[] = getSqlNewCategoriaproducto($idcategoriaproducto, $nombre, $descripcion, $numero, $return);

    //          MostrarConsulta($sql);
    if(ejecutarConsultaSQLBeginCommit($sql)) {
        $dev['mensaje'] = "Se actualizo correctamente";
        $dev['error'] = "true";
        $dev['resultado'] = "";
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
function getSqlUpdateCategoriaproducto($idcategoriaproducto,$nombre,$descripcion,$numero, $return){
    $setC[0]['campo'] = 'nombre';
    $setC[0]['dato'] = $nombre;
    $setC[1]['campo'] = 'descripcion';
    $setC[1]['dato'] = $descripcion;
    $setC[2]['campo'] = 'numero';
    $setC[2]['dato'] = $numero;

    $set = generarSetsUpdate($setC);
    $wher[0]['campo'] = 'idcategoriaproducto';
    $wher[0]['dato'] = $idcategoriaproducto;

    $where = generarWhereUpdate($wher);
    return "UPDATE categoriaproducto SET ".$set." WHERE ".$where;
}
function GuardarEditarCategoriaProducto($callback, $_dc, $idcategoriaproducto, $return = false){
    $dev['mensaje'] = "";
    $dev['error'] = "false";
    $dev['resultado'] = "";

    $nombre = $_GET['nombre'];
    $descripcion =$_GET['descripcion'];
    //    echo $idcompra;
    $nombreA = validarText($nombre, true);
    if($nombreA["error"]== false){
        $dev['mensaje'] = "Error en el campo nombre: ".$nombreA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }

    $sql[]= getSqlUpdateCategoriaproducto($idcategoriaproducto,$nombre,$descripcion,$numero, $return);

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

function BuscarCategoriaProductoPorId($callback, $_dc, $idcategoriaproducto, $return = false){

    //    $dev['totalCount'] = 0;
    $dev['mensaje'] = "";
    $dev['error']   = "false";
    $dev['resultado'] = "";

    $sql = "
SELECT 
  ctp.idcategoriaproducto,
  ctp.nombre,
  ctp.descripcion
FROM
  categoriaproducto ctp
WHERE
  ctp.idcategoriaproducto = '$idcategoriaproducto'
";


    if($idcategoriaproducto != null)
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
function getSqlDeleteCategoriaproducto($idcategoriaproducto){
    return "DELETE FROM categoriaproducto WHERE idcategoriaproducto ='$idcategoriaproducto';";
}
function EliminarCategoriaProducto($callback, $_dc, $idcategoriaproducto, $return = false){
    $dev['mensaje'] = "";
    $dev['error'] = "false";
    $dev['resultado'] = "";
    //    echo "hola";

    $sql[]= getSqlDeleteCategoriaproducto($idcategoriaproducto);

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

?>