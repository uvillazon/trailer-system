<?php
function ListarProductos($start, $limit, $sort, $dir, $callback, $_dc, $where = '', $return = false){

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
  prd.idproducto,
  prd.nombre,
  prd.medidas,
  prd.tela,
  prd.codigo,
prd.precio1bs AS precio,
prd.utilidad,
 prd.imagen,
  prd.descripcion,
  ctp.nombre AS categoria,
  gru.nombre AS grupo,
  prd.idgrupo,
  prd.idcategoriaproducto
FROM
  productos prd,
  categoriaproducto ctp,
  grupos gru
WHERE
  prd.idcategoriaproducto = ctp.idcategoriaproducto AND
  prd.idgrupo = gru.idgrupo AND gru.nombre <> 'logos' $order
";
    }
    else{
        $sql ="
SELECT
  prd.idproducto,
  prd.nombre,
  prd.medidas,
  prd.codigo,
  prd.tela,
 prd.imagen,
prd.precio1bs AS precio,
prd.utilidad,
  prd.descripcion,
  ctp.nombre AS categoria,
  gru.nombre AS grupo,
  prd.idgrupo,
  prd.idcategoriaproducto
FROM
  productos prd,
  categoriaproducto ctp,
  grupos gru
WHERE
  prd.idcategoriaproducto = ctp.idcategoriaproducto AND
  prd.idgrupo = gru.idgrupo AND gru.nombre <> 'logos' AND$where $order
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
function ListarProductosLogos($start, $limit, $sort, $dir, $callback, $_dc, $where = '', $return = false){

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
  prd.canal,
  prd.kardex,
  prd.idproducto,
  prd.nombre,
  prd.puntadas,
  prd.ancho,
  prd.alto,
  prd.imagen,
  prd.idempresa,
  emp.nombre as empresa,
  prd.precio1bs as precio,
  prd.descripcion as observacion
FROM
  productos prd,
  `empresas` emp
WHERE
  prd.idempresa = emp.idempresa AND
  prd.logo = 'si' $order
";
    }
    else{
        $sql ="
SELECT
  prd.canal,
  prd.kardex,
  prd.idproducto,
  prd.puntadas,
  prd.nombre,
  prd.ancho,
  prd.alto,
  prd.imagen,
  prd.idempresa,
  emp.nombre as empresa,
  prd.precio1bs as precio,
  prd.descripcion as observacion
FROM
  productos prd,
  `empresas` emp
WHERE
  prd.idempresa = emp.idempresa AND
  prd.logo = 'si' AND $where $order
";
    }      //
    //           echo $sql;
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


function ListarProductoIdNombreUnidadPrecio($start, $limit, $sort, $dir, $callback, $_dc, $where = '', $return = false){

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
pro.idproducto AS id,
  'pieza' AS unidad,
  pro.nombre AS detalle,
  pro.precio1bs AS preciounitario
FROM
  productos pro  WHERE pro.idgrupo = 'gru-1'$order
";
    }
    else{
        $sql ="
SELECT
pro.idproducto AS id,
  'pieza' AS unidad,
  pro.nombre AS detalle,
  pro.precio1bs AS preciounitario
FROM
  productos pro WHERE pro.idgrupo = 'gru-1' AND
        $where $order
";
    }      //        echo $sql;
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
function ListarProductoIdNombreUnidad($start, $limit, $sort, $dir, $callback, $_dc, $where = '', $return = false){

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
pro.idproducto AS id,
  'pieza' AS unidad,
  pro.nombre AS detalle
FROM
  productos pro WHERE
  pro.idgrupo = 'gru-1' $order
";
    }
    else{
        $sql ="
SELECT
pro.idproducto AS id,
  'pieza' AS unidad,
  pro.nombre AS detalle
FROM
  productos pro WHERE  pro.idgrupo = 'gru-1' AND
        $where $order
";
    }
//           echo $sql;
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

function ListarProductoIdDetalle($start, $limit, $sort, $dir, $callback, $_dc, $where = '', $return = false){

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
pro.idproducto AS id,

  pro.nombre AS detalle

FROM
  productos pro $order
";
    }
    else{
        $sql ="
SELECT
pro.idproducto AS id,

  pro.nombre AS detalle
FROM
  productos pro WHERE
        $where $order
";
    }      //        echo $sql;
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

function getSqlNewProductos($idproducto, $nombre, $codigo, $imagen, $medidas, $tela, $idgrupo, $idcategoriaproducto, $costo, $precio, $numero, $descripcion, $return){
    $setC[0]['campo'] = 'idproducto';
    $setC[0]['dato'] = $idproducto;
    $setC[1]['campo'] = 'nombre';
    $setC[1]['dato'] = $nombre;
    $setC[2]['campo'] = 'codigo';
    $setC[2]['dato'] = $codigo;
    $setC[3]['campo'] = 'imagen';
    $setC[3]['dato'] = $imagen;
    $setC[4]['campo'] = 'medidas';
    $setC[4]['dato'] = $medidas;
    $setC[5]['campo'] = 'tela';
    $setC[5]['dato'] = $tela;
    $setC[6]['campo'] = 'idgrupo';
    $setC[6]['dato'] = $idgrupo;
    $setC[7]['campo'] = 'idcategoriaproducto';
    $setC[7]['dato'] = $idcategoriaproducto;
    $setC[8]['campo'] = 'costo';
    $setC[8]['dato'] = $costo;
    $setC[9]['campo'] = 'precio';
    $setC[9]['dato'] = $precio;
    $setC[10]['campo'] = 'numero';
    $setC[10]['dato'] = $numero;
    $setC[11]['campo'] = 'descripcion';
    $setC[11]['dato'] = $descripcion;
    $sql2 = generarInsertValues($setC);
    return "INSERT INTO productos ".$sql2;
}

function getSqlNewProductosLogos($idproducto, $nombre, $codigo, $imagen, $medidas, $tela, $idgrupo, $idcategoriaproducto, $costounitario, $precio1bs, $numero, $descripcion, $saldocantidad, $precio2bs, $logo, $canal, $puntadas, $ancho, $alto, $idempresa,$kardex, $return){
    $setC[0]['campo'] = 'idproducto';
    $setC[0]['dato'] = $idproducto;
    $setC[1]['campo'] = 'nombre';
    $setC[1]['dato'] = $nombre;
    $setC[2]['campo'] = 'codigo';
    $setC[2]['dato'] = $codigo;
    $setC[3]['campo'] = 'imagen';
    $setC[3]['dato'] = $imagen;
    $setC[4]['campo'] = 'medidas';
    $setC[4]['dato'] = $medidas;
    $setC[5]['campo'] = 'tela';
    $setC[5]['dato'] = $tela;
    $setC[6]['campo'] = 'idgrupo';
    $setC[6]['dato'] = $idgrupo;
    $setC[7]['campo'] = 'idcategoriaproducto';
    $setC[7]['dato'] = $idcategoriaproducto;
    $setC[8]['campo'] = 'costounitario';
    $setC[8]['dato'] = $costounitario;
    $setC[9]['campo'] = 'precio1bs';
    $setC[9]['dato'] = $precio1bs;
    $setC[10]['campo'] = 'numero';
    $setC[10]['dato'] = $numero;
    $setC[11]['campo'] = 'descripcion';
    $setC[11]['dato'] = $descripcion;
    $setC[12]['campo'] = 'saldocantidad';
    $setC[12]['dato'] = $saldocantidad;
    $setC[13]['campo'] = 'precio2bs';
    $setC[13]['dato'] = $precio2bs;
    $setC[14]['campo'] = 'logo';
    $setC[14]['dato'] = $logo;
    $setC[15]['campo'] = 'canal';
    $setC[15]['dato'] = $canal;
    $setC[16]['campo'] = 'puntadas';
    $setC[16]['dato'] = $puntadas;
    $setC[17]['campo'] = 'ancho';
    $setC[17]['dato'] = $ancho;
    $setC[18]['campo'] = 'alto';
    $setC[18]['dato'] = $alto;
    $setC[19]['campo'] = 'idempresa';
    $setC[19]['dato'] = $idempresa;
    $setC[20]['campo'] = 'kardex';
    $setC[20]['dato'] = $kardex;
    $sql2 = generarInsertValues($setC);
    return "INSERT INTO productos ".$sql2;
}

function GuardarNuevoProducto($callback, $_dc, $where = '', $return = false){
    $nombre = $_GET['nombre'];
    $codigo = $_GET['codigo'];
    $tela=$_GET['tela'];
    $idgrupo = "gru-1";
    $idcategoriaproducto = $_GET['categoria'];
    $imagen = $_GET['imagen'];
    $descripcion = $_GET['descripcion'];
    $medidas =$_GET['medidas'];
    $numeroA = findUltimoID("productos", "numero", true);
    $numero = $numeroA['resultado']+1;
    $idproducto = "prd-".$numero;
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
    $grupoA = validarText($idgrupo, true);
    if($grupoA["error"]==false){
        $dev['mensaje'] = "Error en el campo grupo: ".$grupoA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $categoriaA = validarText($idcategoriaproducto, true);
    if($categoriaA["error"]==false){
        $dev['mensaje'] = "Error en el campo categoria: ".$categoriaA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    // $estado = "Activo";
    $sql[] = getSqlNewProductos($idproducto, $nombre, $codigo, $imagen, $medidas, $tela, $idgrupo, $idcategoriaproducto, $costo, $precio, $numero, $descripcion, $return);

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

function GuardarNuevoLogo($callback, $_dc, $where = '', $return = false){
    $nombre = $_GET['nombre'];


    $imagen = $_GET['imagen'];
    $descripcion = $_GET['observacion'];
    $canal = $_GET['canal'];
    $puntadas = $_GET['puntadas'];
    $ancho = $_GET['ancho'];
    $alto = $_GET['alto'];
    $idempresa = $_GET['idempresa'];
    $kardex = $_GET['kardex'];


    $numeroA = findUltimoID("productos", "numero", true);
    $numero = $numeroA['resultado']+1;
    $idproducto = "prd-".$numero;


    $nombreA = validarText($nombre, true);
    if($nombreA["error"]==false){
        $dev['mensaje'] = "Error en el campo nombre: ".$nombreA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $grupoA = verificarValidarText("logos", true, "grupos", "nombre");
    if($grupoA["error"]==false){
        $dev['mensaje'] = "No existe grupo Logos: ".$grupoA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $sqlgrupo = "SELECT
  grp.idgrupo
FROM
  `grupos` grp
WHERE
  grp.nombre = 'logos'";
    $idgrupoA =findBySqlReturnCampoUnique($sqlgrupo, true, true, 'idgrupo');
    $idgrupo = $idgrupoA['resultado'];
    $categoriaA = verificarValidarText("logos", true, "categoriaproducto", "nombre");
    if($categoriaA["error"]==false){
        $dev['mensaje'] = "No existe categoria productos Logos: ".$categoriaA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $sqlcat = "SELECT
  cat.idcategoriaproducto
FROM
  `categoriaproducto` cat
WHERE
  cat.nombre = 'logos'";
    $idcategoriaA = findBySqlReturnCampoUnique($sqlcat, true, true, 'idcategoriaproducto');
    $idcategoriaproducto = $idcategoriaA['resultado'];
    $empresaA = validarText($idempresa, true);
    if($empresaA["error"]==false){
        $dev['mensaje'] = "Error en el campo empresa: ".$empresaA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    // $estado = "Activo";
    //    $sql[] = getSqlNewProductos($idproducto, $nombre, $codigo, $imagen, $medidas, $tela, $idgrupo, $idcategoriaproducto, $costo, $precio, $numero, $descripcion, $return);
    $sql[] = getSqlNewProductosLogos($idproducto, $nombre, $codigo, $imagen, $medidas, $tela, $idgrupo, $idcategoriaproducto, $costounitario, $precio1bs, $numero, $descripcion, $saldocantidad, $precio2bs, 'si', $canal, $puntadas, $ancho, $alto, $idempresa, $kardex,$return);
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


function GuardarEditarLogo($callback, $_dc, $idproducto, $return = false){
    $nombre = $_GET['nombre'];
    $imagen = $_GET['imagen'];
    $descripcion = $_GET['observacion'];
    $canal = $_GET['canal'];
    $puntadas = $_GET['puntadas'];
    $ancho = $_GET['ancho'];
    $alto = $_GET['alto'];
    $idempresa = $_GET['idempresa'];
    $kardex = $_GET['kardex'];


    $nombreA = validarText($nombre, true);
    if($nombreA["error"]==false){
        $dev['mensaje'] = "Error en el campo nombre: ".$nombreA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }

    $empresaA = validarText($idempresa, true);
    if($empresaA["error"]==false){
        $dev['mensaje'] = "Error en el campo empresa: ".$empresaA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    // $estado = "Activo";
    //    $sql[] = getSqlNewProductos($idproducto, $nombre, $codigo, $imagen, $medidas, $tela, $idgrupo, $idcategoriaproducto, $costo, $precio, $numero, $descripcion, $return);
    $sql[] = getSqlUpdateLogo($idproducto, $nombre, $codigo, $imagen, $medidas, $tela, $idgrupo, $idcategoriaproducto, $costounitario, $precio1bs, $numero, $descripcion, $saldocantidad, $precio2bs, $logo, $canal, $puntadas, $ancho, $alto, $idempresa,$kardex, $return);
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


function CargarNuevoProducto($callback, $_dc, $where = '', $return = false){

    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";

    $grupoA =  ListarGrupos("", "", "nombre","DESC", "", "", "", true);
    if($grupoA["error"]==false){
        $dev['mensaje'] = "No se encontro grupo".$grupoA['mensaje'];
        $dev['error']   = "false";
        $dev['resultado'] = "";

    }
    $categoriaA =  ListarCategoriaProducto("", "", "nombre","DESC", "", "", "", true);
    if($categoriaA["error"]==false){
        $dev['mensaje'] = "No se encontro categoria".$categoriaA['mensaje'];
        $dev['error']   = "false";
        $dev['resultado'] = "";

    }
    if(($categoriaA["error"]==true)&&($grupoA["error"]==true)){
        $dev['mensaje'] = "Todo Ok";
        $dev['error']   = "true";
        $value["grupoM"] = $grupoA['resultado'];
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

function CargarNuevoLogo($callback, $_dc, $where = '', $return = false){

    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";

    $sqlempresa = "SELECT
  emp.idempresa,
  emp.nombre
FROM
  `empresas` emp
ORDER BY
  emp.nombre";

    $grupoA =  getTablaToArrayOfSQL($sqlempresa);
    if($grupoA["error"]==false){
        $dev['mensaje'] = "No se encontro empresa".$grupoA['mensaje'];
        $dev['error']   = "false";
        $dev['resultado'] = "";

    }


    $dev['mensaje'] = "Todo Ok";
    $dev['error']   = "true";

    $value["empresaM"] = $grupoA['resultado'];
    $dev["resultado" ] = $value;


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
function BuscarProductoPorId($callback, $_dc, $idproducto, $return = false){
    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";
    $grupoA =  ListarGrupos("", "", "nombre","DESC", "", "", "", true);
    if($grupoA["error"]==false){
        $dev['mensaje'] = "No se encontro grupo".$grupoA['mensaje'];
        $dev['error']   = "false";
        $dev['resultado'] = "";

    }
    $categoriaA =  ListarCategoriaProducto("", "", "nombre","DESC", "", "", "", true);
    if($categoriaA["error"]==false){
        $dev['mensaje'] = "No se encontro categoria".$categoriaA['mensaje'];
        $dev['error']   = "false";
        $dev['resultado'] = "";

    }
    if(($categoriaA["error"]==true)&&($grupoA["error"]==true)){
        $dev['mensaje'] = "Todo Ok";
        $dev['error']   = "true";
        $value["grupoM"] = $grupoA['resultado'];
        $value["categoriaM"] = $categoriaA['resultado'];
        $dev["resultado" ] = $value;
    }

    $sql ="
SELECT
  prd.idproducto,
  prd.nombre,
  prd.codigo,
  prd.imagen,
  prd.medidas,
  prd.idgrupo,
  gru.nombre AS grupo,
  ctp.idcategoriaproducto ,
  ctp.nombre AS categoria,
  prd.tela,
  prd.descripcion
FROM
  productos prd,
  grupos gru,
  categoriaproducto ctp
WHERE
  prd.idgrupo = gru.idgrupo AND
  prd.idcategoriaproducto = ctp.idcategoriaproducto AND
  prd.idproducto ='$idproducto'
        ";
    //echo $sql;
    //exit();
    if($idproducto != null)
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
function getSqlUpdateProductos($idproducto,$nombre,$codigo,$imagen,$medidas,$tela,$idgrupo,$idcategoriaproducto,$costo,$precio,$numero,$descripcion, $return){
    $setC[0]['campo'] = 'nombre';
    $setC[0]['dato'] = $nombre;
    $setC[1]['campo'] = 'codigo';
    $setC[1]['dato'] = $codigo;
    $setC[2]['campo'] = 'imagen';
    $setC[2]['dato'] = $imagen;
    $setC[3]['campo'] = 'medidas';
    $setC[3]['dato'] = $medidas;
    $setC[4]['campo'] = 'tela';
    $setC[4]['dato'] = $tela;
    $setC[5]['campo'] = 'costounitario';
    $setC[5]['dato'] = $costo;
    $setC[6]['campo'] = 'precio1bs';
    $setC[6]['dato'] = $precio;
    $setC[7]['campo'] = 'numero';
    $setC[7]['dato'] = $numero;
    $setC[8]['campo'] = 'descripcion';
    $setC[8]['dato'] = $descripcion;
    $setC[9]['campo'] = 'idgrupo';
    $setC[9]['dato'] = $idgrupo;
    $setC[10]['campo'] = 'idcategoriaproducto';
    $setC[10]['dato'] = $idcategoriaproducto;

    $set = generarSetsUpdate($setC);
    $wher[0]['campo'] = 'idproducto';
    $wher[0]['dato'] = $idproducto;


    $where = generarWhereUpdate($wher);
    return "UPDATE productos SET ".$set." WHERE ".$where;
}

function getSqlUpdateProductos1($idproducto,$nombre,$codigo,$imagen,$medidas,$tela,$idgrupo,$idcategoriaproducto,$costounitario,$precio1bs,$numero,$descripcion,$saldocantidad,$precio2bs,$logo,$canal,$puntadas,$ancho,$alto,$idempresa, $return){
    $setC[0]['campo'] = 'nombre';
    $setC[0]['dato'] = $nombre;
    $setC[1]['campo'] = 'codigo';
    $setC[1]['dato'] = $codigo;
    $setC[2]['campo'] = 'imagen';
    $setC[2]['dato'] = $imagen;
    $setC[3]['campo'] = 'medidas';
    $setC[3]['dato'] = $medidas;
    $setC[4]['campo'] = 'tela';
    $setC[4]['dato'] = $tela;
    $setC[5]['campo'] = 'costounitario';
    $setC[5]['dato'] = $costounitario;
    $setC[6]['campo'] = 'precio1bs';
    $setC[6]['dato'] = $precio1bs;
    $setC[7]['campo'] = 'numero';
    $setC[7]['dato'] = $numero;
    $setC[8]['campo'] = 'descripcion';
    $setC[8]['dato'] = $descripcion;
    $setC[9]['campo'] = 'saldocantidad';
    $setC[9]['dato'] = $saldocantidad;
    $setC[10]['campo'] = 'precio2bs';
    $setC[10]['dato'] = $precio2bs;
    $setC[11]['campo'] = 'logo';
    $setC[11]['dato'] = $logo;
    $setC[12]['campo'] = 'canal';
    $setC[12]['dato'] = $canal;
    $setC[13]['campo'] = 'puntadas';
    $setC[13]['dato'] = $puntadas;
    $setC[14]['campo'] = 'ancho';
    $setC[14]['dato'] = $ancho;
    $setC[15]['campo'] = 'alto';
    $setC[15]['dato'] = $alto;
    $setC[16]['campo'] = 'idgrupo';
    $setC[16]['dato'] = $idgrupo;
    $setC[17]['campo'] = 'idcategoriaproducto';
    $setC[17]['dato'] = $idcategoriaproducto;
    $setC[18]['campo'] = 'idempresa';
    $setC[18]['dato'] = $idempresa;

    $set = generarSetsUpdate($setC);
    $wher[0]['campo'] = 'idproducto';
    $wher[0]['dato'] = $idproducto;


    $where = generarWhereUpdate($wher);
    return "UPDATE productos SET ".$set." WHERE ".$where;
}

function getSqlUpdateLogo($idproducto,$nombre,$codigo,$imagen,$medidas,$tela,$idgrupo,$idcategoriaproducto,$costounitario,$precio1bs,$numero,$descripcion,$saldocantidad,$precio2bs,$logo,$canal,$puntadas,$ancho,$alto,$idempresa, $kardex,$return){
    $setC[0]['campo'] = 'nombre';
    $setC[0]['dato'] = $nombre;
    $setC[1]['campo'] = 'codigo';
    $setC[1]['dato'] = $codigo;
    $setC[2]['campo'] = 'imagen';
    $setC[2]['dato'] = $imagen;
    $setC[3]['campo'] = 'medidas';
    $setC[3]['dato'] = $medidas;
    $setC[4]['campo'] = 'tela';
    $setC[4]['dato'] = $tela;
    $setC[5]['campo'] = 'costounitario';
    $setC[5]['dato'] = $costounitario;
    $setC[6]['campo'] = 'precio1bs';
    $setC[6]['dato'] = $precio1bs;
    $setC[7]['campo'] = 'numero';
    $setC[7]['dato'] = $numero;
    $setC[8]['campo'] = 'descripcion';
    $setC[8]['dato'] = $descripcion;
    $setC[9]['campo'] = 'saldocantidad';
    $setC[9]['dato'] = $saldocantidad;
    $setC[10]['campo'] = 'precio2bs';
    $setC[10]['dato'] = $precio2bs;
    $setC[11]['campo'] = 'logo';
    $setC[11]['dato'] = $logo;
    $setC[12]['campo'] = 'canal';
    $setC[12]['dato'] = $canal;
    $setC[13]['campo'] = 'puntadas';
    $setC[13]['dato'] = $puntadas;
    $setC[14]['campo'] = 'ancho';
    $setC[14]['dato'] = $ancho;
    $setC[15]['campo'] = 'alto';
    $setC[15]['dato'] = $alto;
    $setC[16]['campo'] = 'idgrupo';
    $setC[16]['dato'] = $idgrupo;
    $setC[17]['campo'] = 'idcategoriaproducto';
    $setC[17]['dato'] = $idcategoriaproducto;
    $setC[18]['campo'] = 'idempresa';
    $setC[18]['dato'] = $idempresa;
    $setC[19]['campo'] = 'kardex';
    $setC[19]['dato'] = $kardex;


    $set = generarSetsUpdate($setC);
    $wher[0]['campo'] = 'idproducto';
    $wher[0]['dato'] = $idproducto;


    $where = generarWhereUpdate($wher);
    return "UPDATE productos SET ".$set." WHERE ".$where;
}

function GuardarEditarProducto($callback,$_dc, $idproducto,$return=false){

    $nombre = $_GET['nombre'];
    $codigo = $_GET['codigo'];
    $estado = $_GET['estado'];
    $detalle = $_GET['detalle'];
    $tela=$_GET['tela'];
    $idgrupo = 'gru-1';
    $idcategoriaproducto = $_GET['categoria'];
    $imagen = $_GET['imagen'];
    $descripcion = $_GET['descripcion'];
    $medidas =$_GET['medidas'];
    $nombreA = validarText($nombre, true);
    if($nombreA['error']==false){
        $dev['mensaje'] = "Error en el campo nombre: ".$nombreA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $grupoA = validarText($idgrupo, true);
    if($grupoA['error']==false){
        $dev['mensaje'] = "Error en el campo grupo: ".$grupoA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $categoriaA = validarText($idcategoriaproducto, true);
    if($categoriaA['error']==false){
        $dev['mensaje'] = "Error en el campo categoria: ".$categoriaA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $codigoA=validarText($codigo, true);
    if($codigoA['error']==false){
        $dev['mensaje'] = "Error en el campo codigo: ".$codigoA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $codigoB = verificarValidarTextUnicoEdit("idproducto",$idproducto,false, "productos", "codigo",$codigo);
    if($codigoB["error"]==false){
        $dev['mensaje'] = "Error en el campo codigo: ".$codigoB['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $sql[] = getSqlUpdateProductos($idproducto,$nombre,$codigo,$imagen,$medidas,$tela,$idgrupo,$idcategoriaproducto,$costo,$precio,$numero,$descripcion, $return);
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
function getSqlDeleteProductos($idproductos){
    return "DELETE FROM productos WHERE idproducto ='$idproductos';";
}
function EliminarProducto($callback, $_dc, $idproducto,$return = false){
    $dev['mensaje'] = "";
    $dev['error']   = "";

    //    $sql[] = getSqlDeleteLinea_marca($idlinea);
    $sql[] = getSqlDeleteProductos($idproducto);
    $sql[] = getSqlDeleteComponentegrupoPorProducto($idproducto);
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
function CargarPanelConsultaProducto($callback, $_dc, $where='',$return = false){
    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";

    $productoA = ListarProductoIdNombreUnidad("", "", "nombre", "DESC", "", "", "", true);
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
function actualizarSaldoMovimientoProducto($idproducto, $fecha,$hora = '00:00:01' ,$return = false ){
    $sql="
SELECT COALESCE(mk.saldo, 0) AS saldo,COALESCE(mk.saldobs, 0) AS saldobs
FROM `movimientoproducto` mk
WHERE mk.`idproducto` = '$idproducto' AND
TIMESTAMP(mk.fecha , mk.hora) < TIMESTAMP ('$fecha','$hora')
ORDER BY mk.fecha DESC, mk.hora DESC LIMIT 1
";
    $saldo = findBySqlReturnCampoUnique($sql, true, true, "saldo");
    $saldobs = findBySqlReturnCampoUnique($sql, true, true, "saldobs");
    $sql1 = "
SELECT * FROM  `movimientoproducto` mk
 WHERE mk.`idproducto` = '$idproducto' AND
TIMESTAMP(mk.fecha,mk.hora) >= TIMESTAMP ('$fecha','$hora')
order by mk.fecha asc , mk.hora asc ;
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
        $idmovimientokardexmateriaprima = $res1['idmovimientoproducto'];
        $sqlA[]="UPDATE movimientoproducto SET saldo = '$saldoActual' , saldobs = '$saldoActualBs' WHERE idmovimientoproducto = '$idmovimientokardexmateriaprima' AND idproducto = '$idproducto'";
    }
    $sqlA[]="UPDATE productos SET saldocantidad = '$saldoActual' ,saldobs = '$saldoActualBs' WHERE idproducto = '$idproducto'";

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

function MovimientoProductoSalida($idproducto,$cantidad, $fecha,$hora = '00:00:01' ,$numerooperacion,$operacion,$return = false ){
    $sql="
SELECT
  mk.saldocantidad,
  mk.costounitario,
  mk.idmovimientoproducto
FROM
  `movimientoproducto` mk
WHERE
  mk.`idproducto` = '$idproducto' AND
  mk.saldocantidad > 0
ORDER BY
  mk.fecha DESC,
  mk.hora DESC
LIMIT 1
";

    $saldocantidadA = findBySqlReturnCampoUnique($sql, true, true, "saldocantidad");
    $saldocantidad = $saldocantidadA['resultado'];
    //    echo $saldocantidad."saldo";

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
        $idmovimientokardexA =findUltimoID('movimientoproducto', 'numero', true);
        $numeromk = $idmovimientokardexA['resultado']+1;
        $idmovimientokardex = "mpr-".$numeromk;
        //        echo $preciounitario;
        $egreso = $preciounitario * $cantidad;
        //        $sqlA[] = getSqlNewMovimientoproducto($idmovimientoproducto, $idproducto, $entrada, $salida, $saldo, $costounitario, $ingreso, $egreso, $saldobs, $fecha, $hora, $descripcion, $numero, $saldocantidad, $operacion, $return);
        $sqlA[]=getSqlNewMovimientoproducto($idmovimientokardex, $idproducto, 0, $cantidad, 0, $preciounitario, 0, $egreso, 0, $fecha, $hora, "Salida  de Producto #:".$numerooperacion, $numeromk, 0, $operacion, $return);
        //        $sqlA[]=getSqlNewMovimientokardexalmacen($idmovimientokardex, $idproducto, $idalmacen, 0, $cantidad, 0, $preciounitario, 0, $egreso, 0, $fecha,  date("H:i:s"), $compra, $numeromk, 0, false);
        //        $sqlA[]=getSqlNewMovimientokardex($idmovimientokardex, $idproducto, 0, $salida, 0, 0, $egreso, 0, $compra, $fecha, $hora, $numeromk, $preciounitario, "traspaso", 0, false);
        $saldocantidad = $saldocantidad - $cantidad;
        $sqlA[] = "UPDATE movimientoproducto SET saldocantidad = '$saldocantidad' WHERE idmovimientoproducto = '$idmovimiento' AND idproducto = '$idproducto' ";


        //        $idmovimientokardexalmacen = "mka-".$numeromka;
        //
        //        $sqlA[] = getSqlNewMovimientokardexalmacen($idmovimientokardexalmacen, $idproducto, $destino, $salida, 0, 0, $preciounitario, $egreso, 0, 0, $fecha, $hora, $compra, $numeromka, $salida,false);


        //                MostrarConsulta($sqlA);
        ejecutarConsultaSQLBeginCommit($sqlA);


    }
    else{
        $idmovimientokardexA =findUltimoID('movimientoproducto', 'numero', true);
        $numeromk = $idmovimientokardexA['resultado']+1;
        $idmovimientokardex = "mpr-".$numeromk;
        //        echo $preciounitario;
        $egreso = $preciounitario * $saldocantidad;
        $hora = date("H:i:s");
        $sqlA1[]=getSqlNewMovimientoproducto($idmovimientokardex, $idproducto, 0, $cantidad, 0, $preciounitario, 0, $egreso, 0, $fecha, $hora, "Salida  de Producto #:".$numerooperacion, $numeromk, 0, $operacion, $return);

        //        $sqlA1[]=getSqlNewMovimientokardexproducto($idmovimientokardex, $idkardexproducto, 0, $cantidad, 0, $costounitario, $ingreso, $egreso, $saldobs, $fecha, $hora, $descripcion, $numero, $saldocantidad, $operacion, $return);
        //        $sqlA1[]=getSqlNewMovimientokardexalmacen($idmovimientokardex, $idproducto, $idalmacen, 0, $saldocantidad, 0, $preciounitario, 0, $egreso, 0, $fecha,  date("H:i:s"), $compra, $numeromk, 0, false);

        //        $sqlA1[]=getSqlNewMovimientokardex($idmovimientokardex, $idproducto, 0, $saldocantidad, 0, 0, $egreso, 0, $compra, $fecha, $hora, $numeromk, $preciounitario, "traspaso", 0, false);






        //        $idmovimientokardexalmacen = "mka-".$numeromka;
        //
        //        $sqlA1[] = getSqlNewMovimientokardexalmacen($idmovimientokardexalmacen, $idproducto, $destino, $saldocantidad, 0, 0, $preciounitario, $egreso, 0, 0, $fecha, $hora, $compra, $numeromka, $saldocantidad,false);
        $cantidad = $cantidad - $saldocantidad;
        $saldocantidad = 0;
        $sqlA1[] = "UPDATE movimientoproducto SET saldocantidad = '$saldocantidad' WHERE idmovimientoproducto = '$idmovimiento' AND idproducto = '$idproducto'";


        //        MostrarConsulta($sqlA1);
        ejecutarConsultaSQLBeginCommit($sqlA1);
        sleep(1);
        MovimientoProductoSalida($idproducto, $cantidad, $fecha, $hora, $numerooperacion, $operacion, $return);
        //        salidamovientomoventaalmacen($idalmacen,$idproducto,$cantidad ,$fecha,$hora ,$compra ,false );


    }

}
?>