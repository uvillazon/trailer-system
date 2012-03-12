<?php
function ListarMateriaPrimaIdNombreUnidad($start, $limit, $sort, $dir, $callback, $_dc, $where = '', $return = false){
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
        $sql = "SELECT
  mat.idmateriaprima AS id,
  CONCAT(mat.nombre,' ', col.nombre) AS nombre,
  uni.nombre as unidad
FROM
  `materiaprima` mat,
  `unidades` uni,
   colores col
WHERE
  mat.idunidad = uni.idunidad AND mat.idcolor = col.idcolor $order
";
    }
    else{
        $sql ="
SELECT
  mat.idmateriaprima AS id,
  CONCAT(mat.nombre,' ', col.nombre) AS $where,
  uni.nombre as unidad
FROM
  `materiaprima` mat,
  `unidades` uni,
   colores col
WHERE
  mat.idunidad = uni.idunidad AND mat.idcolor = col.idcolor $order
";
    }

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
function ListarMateriaPrimaIdDetalle($start, $limit, $sort, $dir, $callback, $_dc, $where = '', $return = false){
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
        $sql = "SELECT
  mat.idmateriaprima AS id,
  CONCAT(mat.nombre,' - ', mat.caracteristica) AS detalle

FROM
  `materiaprima` mat

        $order
";
    }
    else{
        $sql ="
SELECT
  mat.idmateriaprima,
  CONCAT(mat.nombre,' - ', mat.caracteristica) AS nombre

FROM
  `materiaprima` mat
        $where $order
";
    }

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

function ListarMateriaPrima($start, $limit, $sort, $dir, $callback, $_dc, $where = '', $return = false){

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
    $sqlcount = "SELECT *
FROM
  `materiaprima` mat;";
    $no = allBySql($sqlcount);

    //    echo $no."adsadas";
    if(($where == null)||($where =="")){
        $sql = "SELECT
  matpri.idmateriaprima,
  matpri.nombre,
  matpri.caracteristica,
  matpri.stockminimo,
  matpri.estado,
  matpri.numero,
  matpri.codigo,
  matpri.calidad,
matpri.imagen,
  cat.nombre AS categoria,
  uni.nombre AS unidad,
  karmatpr.saldocantidad AS cantidad,
  col.nombre as color
FROM
  materiaprima matpri,
  categorias cat,
  unidades uni,
  colores col,
  kardexmateriaprima karmatpr
WHERE
  matpri.idcategoria = cat.idcategoria AND
  col.idcolor = matpri.idcolor AND
  matpri.idunidad = uni.idunidad AND
  matpri.idmateriaprima = karmatpr.idkardexmateriaprima $order LIMIT $start,$limit;
";
    }
    else{
        $sql ="
SELECT
  matpri.idmateriaprima,
  matpri.nombre,
  matpri.caracteristica,
  matpri.stockminimo,
  matpri.estado,
  matpri.numero,
  matpri.codigo,
  matpri.calidad,
  matpri.imagen,
  cat.nombre AS categoria,
  uni.nombre AS unidad,
  karmatpr.saldocantidad AS cantidad,
 col.nombre as color
FROM
  materiaprima matpri,
  categorias cat,
  unidades uni,
  colores col,
  kardexmateriaprima karmatpr
WHERE
  matpri.idcategoria = cat.idcategoria AND
  col.idcolor = matpri.idcolor AND
  matpri.idunidad = uni.idunidad AND
  matpri.idmateriaprima = karmatpr.idkardexmateriaprima AND $where $order LIMIT $start,$limit;
";
    }

    //                    echo $sql;
    if($link=new BD)
    {
        if($link->conectar())
        {
            if($re = $link->consulta($sql))
            {

                if($fi = mysql_fetch_array($re))
                {
                    $dev['totalCount'] = $no;
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
function ListarMateriaPrimaIdDetalleTela($start, $limit, $sort, $dir, $callback, $_dc, $where = '', $return = false){

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
    $sqlcount = "SELECT *
FROM
  `materiaprima` mat;";
    $no = allBySql($sqlcount);

    //    echo $no."adsadas";

    if(($where == null)||($where =="")){
        $sql = "SELECT
  mat.idmateriaprima AS id,
   CONCAT(mat.nombre,' ', col.nombre) AS detalle
FROM
  materiaprima mat,
  `categorias` ca,
  colores col
WHERE
  mat.idcategoria = ca.idcategoria AND mat.idcolor = col.idcolor AND
  ca.nombre = 'tela'

        $order
";
    }
    else{
        $sql ="
SELECT
  mat.idmateriaprima AS id,
  CONCAT(mat.nombre,' ', col.nombre) AS detalle
FROM
  materiaprima mat,
  `categorias` ca,
   colores col
WHERE
  mat.idcategoria = ca.idcategoria AND mat.idcolor = col.idcolor AND
  ca.nombre = 'tela'
        $where $order
";
    }
//                            echo $sql;
    if($link=new BD)
    {
        if($link->conectar())
        {
            if($re = $link->consulta($sql))
            {

                if($fi = mysql_fetch_array($re))
                {
                    $dev['totalCount'] = $no;
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
function getSqlNewMateriaprima($idmateriaprima, $idcategoria, $idcolor, $idunidad, $nombre, $caracteristica, $stockminimo, $estado, $numero, $codigo, $calidad, $return){
    $setC[0]['campo'] = 'idmateriaprima';
    $setC[0]['dato'] = $idmateriaprima;
    $setC[1]['campo'] = 'idcategoria';
    $setC[1]['dato'] = $idcategoria;
    $setC[2]['campo'] = 'idcolor';
    $setC[2]['dato'] = $idcolor;
    $setC[3]['campo'] = 'idunidad';
    $setC[3]['dato'] = $idunidad;
    $setC[4]['campo'] = 'nombre';
    $setC[4]['dato'] = $nombre;
    $setC[5]['campo'] = 'caracteristica';
    $setC[5]['dato'] = $caracteristica;
    $setC[6]['campo'] = 'stockminimo';
    $setC[6]['dato'] = $stockminimo;
    $setC[7]['campo'] = 'estado';
    $setC[7]['dato'] = $estado;
    $setC[8]['campo'] = 'numero';
    $setC[8]['dato'] = $numero;
    $setC[9]['campo'] = 'codigo';
    $setC[9]['dato'] = $codigo;
    $setC[10]['campo'] = 'calidad';
    $setC[10]['dato'] = $calidad;
    $sql2 = generarInsertValues($setC);
    return "INSERT INTO materiaprima ".$sql2;
}

function GuardarNuevaMateriaPrima($callback, $_dc, $where = '', $return = false){
    $nombre = $_GET['nombre'];
    $codigo = $_GET['codigo'];
    $idcategoria = $_GET['categoria'];
    $idunidad = $_GET['unidad'];
    $idcolor = $_GET['color'];
    $estado = $_GET['estado'];
    $stockminimo = $_GET['stockminimo'];
    $caracteristica = $_GET['caracteristica'];
    $calidad = $_GET['calidad'];
    $numeroA = findUltimoID("materiaprima", "numero", true);
    $numero = $numeroA['resultado']+1;
    $idmateriaprima = "mtp-".$numero;
    $dev['error'] = "false";
    $dev['resultado'] = "";
    $codigoA =  validarText($codigo, true);
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
    $idcategoriaA = validarText($idcategoria, true);
    if($idcategoriaA["error"]==false){
        $dev['mensaje'] = "Error en el campo categoria: ".$idcategoriaA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $unidadA = validarText($idunidad, true);
    if($unidadA["error"]==false){
        $dev['mensaje'] = "Error en el campo unidad: ".$unidadA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $colorA = validarText($idcolor, true);
    if($colorA["error"]==false){
        $dev['mensaje'] = "Error en el campo color: ".$unidadA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }

    $codigoB = verificarValidarText($codigo, false, "materiaprima", "codigo");
    if($codigoB["error"]==false){
        $dev['mensaje'] = "Error en el campo codigo: ".$codigoB['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $estado = "Activo";
    $fecha = Date("Y-m-d");
    $hora = date("H:i:s");
    $sql[] = getSqlNewMateriaprima($idmateriaprima, $idcategoria, $idcolor, $idunidad, $nombre, $caracteristica, $stockminimo, $estado, $numero, $codigo, $calidad, $return);

    $sql[] = getSqlNewKardexmateriaprima($idmateriaprima, $idmateriaprima, $fecha, 0, 0, 0, 0, 0, 0, 0, 0, $numero, $return);

    $numeromovA = findUltimoID("movimientokardexmateriaprima", "numero", true);
    $numeromov = $numeromovA['resultado']+1;
    $idmovimientokardexmateriaprima = "mmp-".$numeromov;

    $sql[] = getSqlNewMovimientokardexmateriaprima($idmovimientokardexmateriaprima, $idmateriaprima, 0, 0, 0, 0, 0, 0, 0,$fecha , $hora, "Creacion de Materia Prima", $numeromov, 0,0, "",$return);
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
function CargarNuevaMateriaPrima($callback, $_dc, $where = '', $return = false){

    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";

    $categoriaM =  ListarCategoria("", "", "nombre", "DESC", "", "","", true);

    if($categoriaM["error"]==false){
        $dev['mensaje'] = "No se pudo encontrar categorias";
        $dev['error']   = "false";
        $dev['resultado'] = "";

    }
    $unidadM =  ListarUnidad("", "", "nombre", "DESC", "", "", "",true);

    if($unidadM["error"]==false){
        $dev['mensaje'] = "No se pudo encontrar unidad";
        $dev['error']   = "false";
        $dev['resultado'] = "";

    }
    $colorM =  ListarColores("", "", "nombre", "DESC", "", "", "",true);

    if($colorM["error"]==false){
        $dev['mensaje'] = "No se pudo encontrar colores";
        $dev['error']   = "false";
        $dev['resultado'] = "";

    }



    if(($categoriaM["error"]==true)&&($unidadM["error"]==true)&&($colorM['error']==true)){
        $dev['mensaje'] = "Todo Ok";
        $dev['error']   = "true";
        $value["categoriaM"] = $categoriaM['resultado'];
        $value["unidadM"] = $unidadM['resultado'];
        $value["colorM"] = $colorM['resultado'];
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
function BuscarMateriaPrimaPorId($callback, $_dc, $idmateriaprima, $return = false){
    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";


    $categoriaM =  ListarCategoria("", "", "nombre", "DESC", "", "","", true);

    if($categoriaM["error"]==false){
        $dev['mensaje'] = "No se pudo encontrar categorias";
        $dev['error']   = "false";
        $dev['resultado'] = "";

    }
    $unidadM =  ListarUnidad("", "", "nombre", "DESC", "", "", "",true);

    if($unidadM["error"]==false){
        $dev['mensaje'] = "No se pudo encontrar unidad";
        $dev['error']   = "false";
        $dev['resultado'] = "";

    }
    $colorM =  ListarColores("", "", "nombre", "DESC", "", "", "",true);

    if($colorM["error"]==false){
        $dev['mensaje'] = "No se pudo encontrar colores";
        $dev['error']   = "false";
        $dev['resultado'] = "";

    }



    if(($categoriaM["error"]==true)&&($unidadM["error"]==true)&&($colorM['error']==true)){
        $dev['mensaje'] = "Todo Ok";
        $dev['error']   = "true";
        $value["categoriaM"] = $categoriaM['resultado'];
        $value["unidadM"] = $unidadM['resultado'];
        $value["colorM"] = $colorM['resultado'];

    }


    $sql ="
    SELECT
  mat.idmateriaprima,
  mat.idcategoria,
  mat.idcolor,
  mat.idunidad,
  mat.nombre,
  mat.caracteristica,
  mat.stockminimo,
  mat.estado,
  mat.numero,
  mat.codigo,
  mat.calidad
FROM
  `materiaprima` mat
WHERE
  mat.idmateriaprima = '$idmateriaprima'
        ";

    if($idmateriaprima != null)
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
function getSqlUpdateMateriaprima($idmateriaprima,$idcategoria,$idcolor,$idunidad,$nombre,$caracteristica,$stockminimo,$estado,$numero,$codigo,$calidad, $return){
    $setC[0]['campo'] = 'nombre';
    $setC[0]['dato'] = $nombre;
    $setC[1]['campo'] = 'caracteristica';
    $setC[1]['dato'] = $caracteristica;
    $setC[2]['campo'] = 'stockminimo';
    $setC[2]['dato'] = $stockminimo;
    $setC[3]['campo'] = 'estado';
    $setC[3]['dato'] = $estado;
    $setC[4]['campo'] = 'numero';
    $setC[4]['dato'] = $numero;
    $setC[5]['campo'] = 'codigo';
    $setC[5]['dato'] = $codigo;
    $setC[6]['campo'] = 'calidad';
    $setC[6]['dato'] = $calidad;
    $setC[7]['campo'] = 'idcategoria';
    $setC[7]['dato'] = $idcategoria;
    $setC[8]['campo'] = 'idcolor';
    $setC[8]['dato'] = $idcolor;
    $setC[9]['campo'] = 'idunidad';
    $setC[9]['dato'] = $idunidad;

    $set = generarSetsUpdate($setC);
    $wher[0]['campo'] = 'idmateriaprima';
    $wher[0]['dato'] = $idmateriaprima;


    $where = generarWhereUpdate($wher);
    return "UPDATE materiaprima SET ".$set." WHERE ".$where;
}
function GuardarEditarMateriaPrima($callback, $_dc, $idmateriaprima, $return = false){
    $nombre = $_GET['nombre'];
    $codigo = $_GET['codigo'];
    $idcategoria = $_GET['categoria'];
    $idunidad = $_GET['unidad'];
    $idcolor = $_GET['color'];
    $estado = $_GET['estado'];
    $stockminimo = $_GET['stockminimo'];
    $caracteristica = $_GET['caracteristica'];
    $calidad = $_GET['calidad'];
    $estado = $_GET['estado'];
    $codgoA = validarText($codigo, true);
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
    $idcategoriaA = validarText($idcategoria, true);
    if($idcategoriaA["error"]==false){
        $dev['mensaje'] = "Error en el campo categoria: ".$idcategoriaA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $unidadA = validarText($idunidad, true);
    if($unidadA["error"]==false){
        $dev['mensaje'] = "Error en el campo unidad: ".$unidadA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $colorA = validarText($idcolor, true);
    if($colorA["error"]==false){
        $dev['mensaje'] = "Error en el campo color: ".$colorA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $codigoB = verificarValidarTextUnicoEdit("idmateriaprima",$idmateriaprima,false, "materiaprima", "codigo",$codigo);
    if($codigoB["error"]==false){
        $dev['mensaje'] = "Error en el campo codigo: ".$codigoB['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $sql[] = getSqlUpdateMateriaprima($idmateriaprima,$idcategoria,$idcolor,$idunidad,$nombre,$caracteristica,$stockminimo,$estado,$numero,$codigo,$calidad, $return);
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
function getSqlDeleteMateriaprima($idmateriaprima){
    return "DELETE FROM materiaprima WHERE idmateriaprima ='$idmateriaprima';";
}
function EliminarMateriaPrima($callback, $_dc,$idmateriaprima, $return= 'true'){

    $dev['mensaje'] = "";
    $dev['error']   = "";

    //    $sql[] = getSqlDeleteLinea_marca($idlinea);
    $sql[] = getSqlDeleteMovimientokardexmateriaprimaPorIdMateriaPrima($idmateriaprima);

    $sql[] = getSqlDeleteKardexmateriaprima($idmateriaprima);

    $sql[] =  getSqlDeleteMateriaprima($idmateriaprima);
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

function actualizarSaldoMovimientoKardexMateriaPrima($idmateriaprima, $fecha,$hora = '00:00:01' ,$return = false ){
    $sql="
SELECT COALESCE(mk.saldo, 0) AS saldo,COALESCE(mk.saldobs, 0) AS saldobs
FROM movimientokardexmateriaprima mk
WHERE mk.idmateriaprima = '$idmateriaprima' AND
TIMESTAMP(mk.fecha , mk.hora) < TIMESTAMP ('$fecha','$hora')
ORDER BY mk.fecha DESC, mk.hora DESC,mk.numero DESC LIMIT 1
";
    $saldo = findBySqlReturnCampoUnique($sql, true, true, "saldo");
    $saldobs = findBySqlReturnCampoUnique($sql, true, true, "saldobs");
    $sql1 = "
SELECT * FROM  movimientokardexmateriaprima mk
 WHERE mk.idmateriaprima = '$idmateriaprima' AND
TIMESTAMP(mk.fecha,mk.hora) >= TIMESTAMP ('$fecha','$hora')
order by mk.fecha asc , mk.hora asc,mk.numero ASC ;
";
    //                    echo $sql1."<br />";
    //                    echo $sql."<br />";
    $link=new BD;
    $link->conectar();
    $res=$link->consulta($sql1);
    $saldoActual = $saldo['resultado'];
    $saldoActualBs = $saldobs['resultado'];
    while ($res1=mysql_fetch_array($res)){
        $saldoActual = $saldoActual + $res1['entrada'] - $res1['salida'];
        $saldoActualBs = $saldoActualBs  + $res1['ingreso'] - $res1['egreso'];
        $idmovimientokardexmateriaprima = $res1['idmovimientokardexmateriaprima'];
        $sqlA[]="UPDATE movimientokardexmateriaprima SET saldo = '$saldoActual' , saldobs = '$saldoActualBs' WHERE idmovimientokardexmateriaprima = '$idmovimientokardexmateriaprima' AND idmateriaprima = '$idmateriaprima'";
    }
    $sqlA[]="UPDATE kardexmateriaprima SET saldocantidad = '$saldoActual' ,saldobs = '$saldoActualBs' WHERE idmateriaprima = '$idmateriaprima'";

    if($return == true)
    {
        return $sqlA;
    }
    else

    {
        //          MostrarConsulta($sqlA);
        if(ejecutarConsultaSQLBeginCommit($sqlA))
        {
            $dev['mensaje'] = "Se guardo una transaccion correctamente";
            $dev['error'] = "true";
            $dev['resultado'] = "";
        }
        else
        {
            $dev['mensaje'] = "Ocurrio un error al guardar una transaccion";
            $dev['error'] = "false";
            $dev['resultado'] = "";
        }

    }
    //            if($return == true)
    //            {
    //                return $dev;
    //            }
    //            else
    //            {
    //                $json = new Services_JSON();
    //                $output = $json->encode($dev);
    //                print($output);
    //            }


}


function MovimientoKardexMateriaPrimaSalida($idmateriaprima,$cantidad, $fecha,$hora = '00:00:01' , $numerooperacion ,$operacion,$return = false ){
    $sql="
SELECT
  mk.saldocantidad,
  mk.costounitario,
  mk.idmovimientokardexmateriaprima AS idmovimientoproducto
FROM
  `movimientokardexmateriaprima` mk
WHERE
  mk.idmateriaprima = '$idmateriaprima' AND
  mk.saldocantidad > 0
ORDER BY
  mk.fecha DESC,
  mk.hora DESC,
  mk.numero DESC
LIMIT 1
";

    $saldocantidadA = findBySqlReturnCampoUnique($sql, true, true, "saldocantidad");
    $saldocantidad = $saldocantidadA['resultado'];
    //        echo $saldocantidad."saldo";

    $preciounitarioA = findBySqlReturnCampoUnique($sql, true, true, "costounitario");

    $preciounitario = $preciounitarioA['resultado'];
    //    echo $preciounitario."p/u";;
    $idmovimientoA  = findBySqlReturnCampoUnique($sql, true, true, 'idmovimientoproducto');

    $idmovimiento = $idmovimientoA['resultado'];
    //    echo $idmovimiento;
    //    echo $salida."salida";;
    if($cantidad <= $saldocantidad){
        //        echo $salida;
        //        echo $saldocantidad;
        $idmovimientokardexA =findUltimoID('movimientokardexmateriaprima', 'numero', true);
        $numeromk = $idmovimientokardexA['resultado']+1;
        $idmovimientokardex = "mmp-".$numeromk;
        //        echo $preciounitario;
        $egreso = $preciounitario * $cantidad;
        //        $sqlA[]=getSqlNewMovimientokardexmateriaprima($idmovimientokardex, $idkardexproducto, $cantidad, 0, 0, $costounitario, $costounitario * $cantidad, 0, 0, $fecha, $hora, "produccion de Procesos", $numeromov, $cantidad, $idproduccionproceso, $return);

        $sqlA[]=getSqlNewMovimientokardexmateriaprima($idmovimientokardex, $idmateriaprima, 0, $cantidad, 0, $preciounitario, 0, $egreso, 0, $fecha, $hora, $numerooperacion, $numeromk, 0, $operacion, $idmovimiento,$return);
        //        $sqlA[]=getSqlNewMovimientokardexalmacen($idmovimientokardex, $idproducto, $idalmacen, 0, $cantidad, 0, $preciounitario, 0, $egreso, 0, $fecha,  date("H:i:s"), $compra, $numeromk, 0, false);
        //        $sqlA[]=getSqlNewMovimientokardex($idmovimientokardex, $idproducto, 0, $salida, 0, 0, $egreso, 0, $compra, $fecha, $hora, $numeromk, $preciounitario, "traspaso", 0, false);
        $saldocantidad = $saldocantidad - $cantidad;
        $sqlA[] = "UPDATE movimientokardexmateriaprima SET saldocantidad = '$saldocantidad' WHERE idmovimientokardexmateriaprima = '$idmovimiento' AND idmateriaprima = '$idmateriaprima' ";


        //        $idmovimientokardexalmacen = "mka-".$numeromka;
        //
        //        $sqlA[] = getSqlNewMovimientokardexalmacen($idmovimientokardexalmacen, $idproducto, $destino, $salida, 0, 0, $preciounitario, $egreso, 0, 0, $fecha, $hora, $compra, $numeromka, $salida,false);


        //                MostrarConsulta($sqlA);
        ejecutarConsultaSQLBeginCommit($sqlA);


    }
    else{
        $idmovimientokardexA =findUltimoID('movimientokardexmateriaprima', 'numero', true);
        $numeromk = $idmovimientokardexA['resultado']+1;
        $idmovimientokardex = "mmp-".$numeromk;
        //        echo $preciounitario;
        $egreso = $preciounitario * $saldocantidad;

        $sqlA1[]=getSqlNewMovimientokardexmateriaprima($idmovimientokardex, $idmateriaprima, 0, $saldocantidad, 0, $preciounitario, 0, $egreso, 0, $fecha, $hora, $numerooperacion, $numeromk, 0, $operacion,$idmovimiento, $return);

        $cantidad = $cantidad - $saldocantidad;
        $saldocantidad = 0;
        $sqlA1[] = "UPDATE movimientokardexmateriaprima SET saldocantidad = '$saldocantidad' WHERE idmovimientokardexmateriaprima = '$idmovimiento' AND idmateriaprima = '$idmateriaprima'";


        //        MostrarConsulta($sqlA1);
        ejecutarConsultaSQLBeginCommit($sqlA1);

        MovimientoKardexMateriaPrimaSalida($idmateriaprima, $cantidad, $fecha, $hora, $numerooperacion, $operacion, $return);
        //        salidamovientomoventaalmacen($idalmacen,$idproducto,$cantidad ,$fecha,$hora ,$compra ,false );


    }

}

function revertirMovimientoMateriaPrima($operacion,$return){
    $dev['mensaje'] = "";
    $dev['error']   = "false";
    $dev['resultado'] = "";
    $sql1 = "
SELECT
  movm.idmovimientokardexmateriaprima AS id,
  movm.salida,
  movm.id as idm

FROM
  movimientokardexmateriaprima movm
WHERE
  movm.operacion = '$operacion'
ORDER BY
  movm.fecha,
  movm.hora,
  movm.numero
";


    $sqlA = getTablaToArrayOfSQL($sql1);
    $sqlA1 = $sqlA['resultado'];
    //    echo count($sqlA1)."contador ";
    for($i=0;$i<count($sqlA1);$i++){
        //        echo $i."uno";
        $item = $sqlA1[$i];
        $cantidad = $item['salida'];
        $id = $item['id'];
        $idmovimiento = $item['idm'];
        $sqlexe[] = "UPDATE movimientokardexmateriaprima SET saldocantidad = saldocantidad + $cantidad WHERE idmovimientokardexmateriaprima = '$idmovimiento';";
        $sqlexe[] = getSqlDeleteMovimientokardexmateriaprima($id);
        $sql = "
SELECT
  mk.fecha,
  mk.hora,
  mk.idmateriaprima
FROM
  movimientokardexmateriaprima mk
WHERE
  mk.idmovimientokardexmateriaprima = '$idmovimiento'
";
        $idmateriaprima = findBySqlReturnCampoUnique($sql, true, true, "idmateriaprima");
        $fecha = findBySqlReturnCampoUnique($sql, true, true, "fecha");
        $hora = findBySqlReturnCampoUnique($sql, true, true, "hora");
        $movimiento[$i]["id"] = $idmateriaprima["resultado"];
        $movimiento[$i]["fecha"] = $fecha["resultado"];
        $movimiento[$i]["hora"] = $hora["resultado"];


        //        if(ejecutarConsultaSQLBeginCommit($sqlexe))
        //        {
        //            $dev['mensaje'] = "Todo Un exito";
        //            $dev['error'] = "true";
        //            $dev['resultado'] = "";
        //            $idmateriaprima = findBySqlReturnCampoUnique($sql, true, true, "idmateriaprima");
        //            $fecha = findBySqlReturnCampoUnique($sql, true, true, "fecha");
        //            $hora = findBySqlReturnCampoUnique($sql, true, true, "hora");
        //            actualizarSaldoMovimientoKardexMateriaPrima($idmateriaprima["resultado"], $fecha["resultado"], $hora["resultado"], false);
        //        }
        //        else
        //        {
        //            $dev['mensaje'] = "Ocurrio un error al Actualizar";
        //            $dev['error'] = "false";
        //            $dev['resultado'] = "";
        //        }
        //        ejecutarConsultaSQLBeginCommit($sqlexe);

        //        MostrarConsulta($sqlexe);
    }
    //    MostrarConsulta($sqlexe);
    //    exit();
    if(ejecutarConsultaSQLBeginCommit($sqlexe))
    {
        $dev['mensaje'] = "Todo Un exito";
        $dev['error'] = "true";
        $dev['resultado'] = "";
        for($k=0;$k<count($movimiento);$k++){
            //            echo $movimiento[$j]['idproducto'];
            actualizarSaldoMovimientoKardexMateriaPrima($movimiento[$k]['id'],$movimiento[$k]['fecha'],$movimiento[$k]['hora'], false);

        }
    }
    else
    {
        $dev['mensaje'] = "Ocurrio un error al Actualizar";
        $dev['error'] = "false";
        $dev['resultado'] = "";
    }

    //    echo count($sqlA1);
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
function CargarPanelConsultaMateriaPrima($callback, $_dc, $where='',$return = false){
    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";

    $productoA = ListarMateriaPrimaIdNombreUnidad("", "", "nombre", "DESC", "", "", "", true);
    if($productoA["error"]=="false"){
        $dev['mensaje'] = "No se pudo encontro proveedores";
        $dev['error']   = "false";

    }
    //    $dev['resultado'] = $productoA['error'];
    if($productoA["error"]==true){
        $dev['mensaje'] = "Todo Ok";
        $dev['error'] = "true";
        $dev['productoM'] = $productoA['resultado'];

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