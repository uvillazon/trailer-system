<?php
function ListarMoldes($start, $limit, $sort, $dir, $callback, $_dc, $where = '', $return = false){

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
        $sql ="
SELECT
  prd.idmolde,
  prd.idcategoria,
  prd.nombre,
  prd.codigo,
  prd.observacion,
  prd.imagen,
  ctp.nombre as categoria,
  prd.sastre,
  prd.hilo
FROM
  `moldes` prd,
  `categoriaproducto` ctp
WHERE
  prd.idcategoria = ctp.idcategoriaproducto $order
";
    }
    else{
        $sql ="
SELECT
  prd.idmolde,
  prd.idcategoria,
  prd.nombre,
  prd.codigo,
  prd.imagen,
  prd.observacion,
  ctp.nombre as categoria
FROM
  `moldes` prd,
  `categoriaproducto` ctp
WHERE
  prd.idcategoria = ctp.idcategoriaproducto AND $where $order
";
    }
    //                          echo $sql;
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

function getSqlNewMoldes($idmolde, $idcategoria, $nombre, $codigo, $imagen, $numero, $observacion,$sastre,$hilo, $return){
    $setC[0]['campo'] = 'idmolde';
    $setC[0]['dato'] = $idmolde;
    $setC[1]['campo'] = 'idcategoria';
    $setC[1]['dato'] = $idcategoria;
    $setC[2]['campo'] = 'nombre';
    $setC[2]['dato'] = $nombre;
    $setC[3]['campo'] = 'codigo';
    $setC[3]['dato'] = $codigo;
    $setC[4]['campo'] = 'imagen';
    $setC[4]['dato'] = $imagen;
    $setC[5]['campo'] = 'numero';
    $setC[5]['dato'] = $numero;
    $setC[6]['campo'] = 'observacion';
    $setC[6]['dato'] = $observacion;
    $setC[7]['campo'] = 'sastre';
    $setC[7]['dato'] = $sastre;
    $setC[8]['campo'] = 'hilo';
    $setC[8]['dato'] = $hilo;
    $sql2 = generarInsertValues($setC);
    return "INSERT INTO moldes ".$sql2;
}


function getSqlUpdateMoldes($idmolde,$idcategoria,$nombre,$codigo,$imagen,$numero,$observacion,$sastre,$hilo, $return){
    $setC[0]['campo'] = 'nombre';
    $setC[0]['dato'] = $nombre;
    $setC[1]['campo'] = 'codigo';
    $setC[1]['dato'] = $codigo;
    $setC[2]['campo'] = 'imagen';
    $setC[2]['dato'] = $imagen;
    $setC[3]['campo'] = 'numero';
    $setC[3]['dato'] = $numero;
    $setC[4]['campo'] = 'observacion';
    $setC[4]['dato'] = $observacion;
    $setC[5]['campo'] = 'idcategoria';
    $setC[5]['dato'] = $idcategoria;
    $setC[6]['campo'] = 'sastre';
    $setC[6]['dato'] = $sastre;
    $setC[7]['campo'] = 'hilo';
    $setC[7]['dato'] = $hilo;

    $set = generarSetsUpdate($setC);
    $wher[0]['campo'] = 'idmolde';
    $wher[0]['dato'] = $idmolde;


    $where = generarWhereUpdate($wher);
    return "UPDATE moldes SET ".$set." WHERE ".$where;
}




function getSqlDeleteMoldes($idmoldes){
    return "DELETE FROM moldes WHERE idmolde ='$idmoldes';";
}

function GuardarNuevoMolde($callback, $_dc, $where = '', $return = false){
    $nombre = $_GET['nombre'];
    $codigo = $_GET['codigo'];

    $idcategoria = $_GET['categoria'];

    $observacion = $_GET['observacion'];
    $hilo = $_GET['hilo'];
    $sastre = $_GET['sastre'];

    $numeroA = findUltimoID("moldes", "numero", true);
    $numero = $numeroA['resultado']+1;
    $idmolde = "mol-".$numero;
    $codigoA =  validarText($codigo, true);
    if($codigoA["error"]==false){
        $dev['mensaje'] = "Error en el campo Numero: ".$codigoA['mensaje'];
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

    $categoriaA = validarText($idcategoria, true);
    if($categoriaA["error"]==false){
        $dev['mensaje'] = "Error en el campo categoria: ".$categoriaA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    // $estado = "Activo";
    $sql[] = getSqlNewMoldes($idmolde, $idcategoria, $nombre, $codigo, $imagen, $numero, $observacion,$sastre,$hilo, $return);
    //    $sql[] = getSqlNewProductos($idmolde, $nombre, $codigo, $imagen, $medidas, $tela, $idgrupo, $idcategoriaprodgetSqlNewMoldesucto, $costo, $precio, $numero, $observacion, $return);

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

function GuardarEditarMolde($callback, $_dc, $idmolde, $return = false){
    $nombre = $_GET['nombre'];

    $observacion = $_GET['observacion'];
    $codigo = $_GET['codigo'];
    $idcategoria = $_GET['categoria'];
    $sastre = $_GET['sastre'];
    $hilo = $_GET['hilo'];


    $codigoA =  validarText($codigo, true);
    if($codigoA["error"]==false){
        $dev['mensaje'] = "Error en el campo Numero: ".$codigoA['mensaje'];
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

    $idcategoriaA = validarText($idcategoria, true);
    if($idcategoriaA["error"]==false){
        $dev['mensaje'] = "Error en el campo empresa: ".$idcategoriaA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    // $estado = "Activo";
    //    $sql[] = getSqlNewProductos($idproducto, $nombre, $codigo, $imagen, $medidas, $tela, $idgrupo, $idcategoriaproducto, $costo, $precio, $numero, $descripcion, $return);
    //    $sql[] = getSqlUpdateLogo($idmolde, $nombre, $codigo, $imagen, $medidas, $tela, $idgrupo, $idcategoriaproducto, $costounitario, $precio1bs, $numero, $observacion, $saldocantidad, $precio2bs, $logo, $codigo, $puntadas, $ancho, $alto, $idcategoria,$kardex, $return);
    $sql[] = getSqlUpdateMoldes($idmolde, $idcategoria, $nombre, $codigo, "", "", $observacion, $sastre,$hilo,$return);
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


function CargarNuevoMolde($callback, $_dc, $where = '', $return = false){

    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";


    $categoriaA =  ListarCategoriaProducto("", "", "nombre","DESC", "", "", "", true);
    if($categoriaA["error"]==false){
        $dev['mensaje'] = "No se encontro categoria".$categoriaA['mensaje'];
        $dev['error']   = "false";
        $dev['resultado'] = "";

    }
    if(($categoriaA["error"]==true)){
        $dev['mensaje'] = "Todo Ok";
        $dev['error']   = "true";
        $value["categoriaM"] = $categoriaA['resultado'];
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

function EliminarMolde($callback, $_dc, $idproducto,$return = false){
    $dev['mensaje'] = "";
    $dev['error']   = "";

    //    $sql[] = getSqlDeleteLinea_marca($idlinea);
    $sql[] = getSqlDeleteMoldes($idproducto);
    //    $sql[] = getSqlDeleteCotizacion($idproducto);
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