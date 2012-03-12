<?php
function ListarInventarioMateriasPrimas($start, $limit, $sort, $dir, $callback, $_dc, $where = '', $return = false){

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
  0 AS costoreal,
  0 AS cantidad,
  mp.idmateriaprima,
  mp.nombre,
  mp.calidad,
  mp.codigo,
  mp.caracteristica,
  uni.nombre AS unidad
FROM
  materiaprima mp,
  unidades uni,
  kardexmateriaprima kar,
  `categorias` cat
WHERE
  mp.idunidad = uni.idunidad AND
  mp.idmateriaprima = kar.idmateriaprima AND
  kar.saldocantidad = 0 AND
  mp.idcategoria = cat.idcategoria $order
";
    }
    else{
        $sql ="
SELECT
  0 AS costoreal,
  0 AS cantidad,
  mp.idmateriaprima,
  mp.nombre,
  mp.calidad,
  mp.codigo,
  mp.caracteristica,
  uni.nombre AS unidad
FROM
  materiaprima mp,
  unidades uni,
  kardexmateriaprima kar,
  `categorias` cat
WHERE
  mp.idunidad = uni.idunidad AND
  mp.idmateriaprima = kar.idmateriaprima AND
  kar.saldocantidad = 0 AND
  mp.idcategoria = cat.idcategoria AND $where $order
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
function GuardarInventarioMateriaPrima($callback, $_dc, $resultado, $return = false){
    $materias = $resultado->materias;
    $numeroA = findUltimoID("movimientokardexmateriaprima", "numero", true);
    $numero = $numeroA['resultado']+1;
    $hora = date("H:i:s");
    $fecha = date("Y-m-d");
    for($j=0;$j<count($materias);$j++){
        $materia = $materias[$j];
        $costoreal = $materia->costoreal;
        $cantidad = $materia->cantidad;
        $idmateriaprima = $materia->idmateriaprima;
        $sqlpro = "
SELECT
  pro.saldocantidad
FROM
  kardexmateriaprima pro
WHERE
  pro.idmateriaprima = '$idmateriaprima' AND
  pro.saldocantidad > 0
";
        $proA = findBySqlReturnCampoUnique($sqlpro, true, true ,"saldocantidad");
        if($proA['error']=="false"){
            $idmovimientokardexmateriaprima = "mmp-".$numero;
            $sql[] =getSqlNewMovimientokardexmateriaprima($idmovimientokardexmateriaprima, $idmateriaprima, $cantidad, 0, $cantidad, $costoreal, $cantidad * $costoreal, 0, $cantidad * $costoreal, $fecha, $hora, "Inventario Inicial", $numero, $cantidad,0,"" ,$return);
            $numero++;
            $sql[] = getSqlUpdateKardexmateriaprima($idmateriaprima,$idmateriaprima,$fecha,$cantidad,$cantidad * $costoreal,$cantidad,$precio1bs,$precio1sus,$costoreal,$precio2bs,$precio2sus,"", $return);

        }

    }


    //    $sql[] = getSqlNewMateriaprima($idmateriaprima, $idcategoria, $idcolor, $idunidad, $nombre, $caracteristica, $stockminimo, $estado, $numero, $codigo, $calidad, $return);
    //
    //    $sql[] = getSqlNewKardexmateriaprima($idmateriaprima, $idmateriaprima, $fecha, 0, 0, 0, 0, 0, 0, 0, 0, $numero, $return);
    //
    //    $numeromovA = findUltimoID("movimientokardexmateriaprima", "numero", true);
    //    $numeromov = $numeromovA['resultado']+1;
    //    $idmovimientokardexmateriaprima = "mmp-".$numeromov;
    //
    //    $sql[] = getSqlNewMovimientokardexmateriaprima($idmovimientokardexmateriaprima, $idmateriaprima, 0, 0, 0, 0, 0, 0, 0,$fecha , $hora, "Creacion de Materia Prima", $numeromov, 0, $return);
    //
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
?>