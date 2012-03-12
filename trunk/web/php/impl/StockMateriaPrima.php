<?php
function ListarStockMateriasPrimas($start, $limit, $sort , $dir, $callback, $_dc, $where = '', $return = false){

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
        $sql ="SELECT
  mtp.idmateriaprima,
  mtp.nombre,
  mtp.stockminimo,
  kmp.saldocantidad AS cantidad,
  COALESCE(kmp.saldocantidad <= mtp.stockminimo, 'baja') AS estado,
  mtp.codigo,
  mtp.calidad,
  uni.nombre AS unidad,
  cat.nombre AS categoria
FROM
  materiaprima mtp,
  kardexmateriaprima kmp,
  unidades uni,
  categorias cat
WHERE
  mtp.idunidad = uni.idunidad AND
  mtp.idcategoria = cat.idcategoria AND
  kmp.idmateriaprima = mtp.idmateriaprima $order
";
    }
    else {
        $sql ="
SELECT
  mtp.idmateriaprima,
  mtp.nombre,
  mtp.stockminimo,
  kmp.saldocantidad AS cantidad,
  COALESCE(kmp.saldocantidad <= mtp.stockminimo, 'baja') AS estado,
  mtp.codigo,
  mtp.calidad,
  uni.nombre AS unidad,
  cat.nombre AS categoria
FROM
  materiaprima mtp,
  kardexmateriaprima kmp,
  unidades uni,
  categorias cat
WHERE
  mtp.idunidad = uni.idunidad AND
  mtp.idcategoria = cat.idcategoria AND
  kmp.idmateriaprima = mtp.idmateriaprima AND $where $order
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
?>