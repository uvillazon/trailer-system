<?php
function findRolByIdFunciones($idrol, $callback, $_dc, $return)
{

    $dev['mensaje'] = "";
    $dev['error']   = "";

    $sql = "SELECT r.* FROM rol r WHERE r.idrol = '$idrol'";
    if($idrol != null)
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
                            $value{mysql_field_name($re, $i)}= $fi[$i];
                        }

                        $func = findAllFuncionByRol($rol, "", "", true);
                        if($func['error'] == true)
                        {
                            $value['funciones'] = "true";
                            $value['funcionesM'] = $func['resultado'];
                        }
                        else
                        {
                            $value['funciones'] = "false";
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
        $dev['mensaje'] = "El codigo de rol es nulo";
        $dev['error']   = "false";
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
        //$output = substr($output, 1);
        //$output = "$callback({".$output.");";
        print($output);
    }

}
function findAllRolWithFunctions($rol, $callback, $_dc, $return)
{
    $dev['mensaje'] = "";
    $dev['error']   = "";

    $sql = "SELECT cf.idcategoriafuncion, cf.nombre FROM categoriafuncion cf";
    if($link=new BD)
    {
        if($link->conectar())
        {
            if($re = $link->consulta($sql))
            {
                if($fi = mysql_fetch_array($re))
                {
                    do{
                        $catNom = $fi['nombre'];
                        $catNomM = $fi['nombre']."M";
                        $func = findAllFuncionByRolCategoria($rol, $fi['idcategoriafuncion'], "", "", true);
                        $cat['idcategoriafuncion'] = $fi['idcategoriafuncion'];
                        $cat['nombre'] = $fi['nombre'];
                        if(count($func)>=1)
                        {
                            $cat[$catNomM] = $func['resultado'];
                            $devC = null;
                        }
                        $devCat[] = $cat;
                        $devC[$catNom] = $cat;
                        $cat = null;
                        $ii++;
                    }while($fi = mysql_fetch_array($re));
                    $dev['resultado'] = $devCat;
                    if($rol != null)
                    {
                        $sqlr = "SELECT idrol, nombre, estado FROM rol WHERE idrol = '$rol'";
                        if($re2 = $link->consulta($sqlr))
                        {
                            if($fi2 = mysql_fetch_array($re2))
                            {
                                $dev['mensaje'] = "Se recupero correctamente los datos del rol";
                                $dev['estado'] = $fi2['estado'];
                                $dev['nombre'] = $fi2['nombre'];
                                $dev['error']   = "true";
                            }
                            else
                            {
                                $dev['mensaje'] = "No existe este rol";
                                $dev['error']   = "false";
                            }

                        }
                        else
                        {
                            $dev['mensaje'] = "Error al recuperar los datos del rol";
                            $dev['error']   = "false";
                        }

                    }
                    else
                    {
                        $dev['mensaje'] = "Se recupero correctamente los datos del rol";
                        $dev['error']   = "true";
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
    if($return == true)
    {
        return $dev;
    }
    else
    {
        $json = new Services_JSON();
        $output = $json->encode($dev);
        //$output = substr($output, 1);
        //$output = "$callback({".$output.");";
        print($output);
    }

}

/************************************edwin*******/
function findAllRol($start, $limit, $sort, $dir, $callback, $_dc, $return)
{
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
    $dev['totalCount'] = 0;
    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";
    if($star == 0 && $limit == 0)
    {
        $sql = "SELECT * FROM rol $order";
    }
    else
    {
        $sql = "SELECT * FROM rol $order LIMIT $start,$limit ";
    }
    if($link=new BD)
    {
        if($link->conectar())
        {
            if($re = $link->consulta($sql))
            {

                if($fi = mysql_fetch_array($re))
                {
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
    $dev['totalCount'] = allBySql($sql);
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
function txSaveUpdateRol($idrol, $nombre, $estado, $funciones, $return)
{
    if($idrol == "nuevo")
    {
        $idrolA = validarText($idrol, false);
    }
    else
    {
        $idrolA = verificarValidarText($idrol, true, "rol", "idrol");
    }

    $nombreA   = validarText($nombre, true);
    $estadoA    = validarText($estado, true);

    $dev['mensaje'] = "";
    $dev['error'] = "false";
    $dev['resultado'] = "";

    if(strtolower($idrol) == "nuevo")
    {
        $idrolA["dato"] = "nuevo";
    }
    else
    {
        if($idrolA['error'] == false)
        {
            $dev['mensaje'] = "Error en el campo idrol: ".$idrolA['mensaje'];
            $json = new Services_JSON();
            $output = $json->encode($dev);
            print($output);
            exit;
        }
    }
    if($nombreA['error'] == false)
    {
        $dev['mensaje'] = "Error en el campo nombre: ".$nombreA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    if($estadoA['error'] == false)
    {
        $dev['mensaje'] = "Error en el campo Estado: ".$estadoA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    if($idrolA['dato'] == "nuevo")
    {
        //guardamos uno nuevo
        $seguridadD = mktime(date("h"), date("i"), date("s"), date("m"), date("d"), date("Y"));
        $seguridadD = $_SESSION['user'].$seguridadD;

        $idRolUltimoA = findUltimoID("rol", "numero", true);
        $idRolUltimo = $idRolUltimoA['resultado'] +1;

        $setC[0]['campo'] = "nombre";
        $setC[0]['dato'] = $nombreA['dato'];
        $setC[1]['campo'] = "estado";
        $setC[1]['dato'] = $estadoA['dato'];
        $setC[2]['campo'] = "seguridad";
        $setC[2]['dato'] = $seguridadD;
        $setC[3]['campo'] = "idrol";
        $setC[3]['dato'] = "rol-".$idRolUltimo;
        $setC[4]['campo'] = "numero";
        $setC[4]['dato'] = $idRolUltimo;
        $sql = generarInsertValues($setC);
        $sql3[] = "INSERT INTO rol ".$sql;
        $setC = null;
        for($i=0; $i<count($funciones); $i++)
        {
            $setC[0]['campo'] = "idrol";
            $setC[0]['dato'] = "rol-".$idRolUltimo;
            $setC[1]['campo'] = "idfuncion";
            $setC[1]['dato'] = $funciones[$i];
            $sql2 = generarInsertValues($setC);
            $sql3[] = "INSERT INTO rolfuncion ".$sql2;
            $setC = null;
            $sql2 = null;
        }
        //        MostrarConsulta($sql3);
        if(ejecutarConsultaSQLBeginCommit($sql3))
        {
            $dev['mensaje'] = "Se guardo correctamente el rol";
            $dev['error'] = "true";
            $dev['resultado'] = "";
        }
        else
        {
            $dev['mensaje'] = "Ocurrio un error al guardar el rol";
            $dev['error'] = "false";
            $dev['resultado'] = "";
        }
    }
    else
    {
        //actuallizamos

        $setC[0]['campo'] = "nombre";
        $setC[0]['dato'] = $nombreA['dato'];
        $setC[1]['campo'] = "estado";
        $setC[1]['dato'] = $estadoA['dato'];

        $set = generarSetsUpdate($setC);

        $wher[0]['campo'] = "idrol";
        $wher[0]['dato'] = $idrolA['dato'];

        $where = generarWhereUpdate($wher);

        $sql[] = "UPDATE rol SET ".$set." WHERE ".$where;
        $sql[] = "DELETE FROM rolfuncion WHERE idrol = '".$idrolA['dato']."'";
        $setC = null;
        for($i=0; $i<count($funciones); $i++)
        {
            $setC[0]['campo'] = "idrol";
            $setC[0]['dato'] = $idrolA['dato'];
            $setC[1]['campo'] = "idfuncion";
            $setC[1]['dato'] = $funciones[$i];
            $sql2 = generarInsertValues($setC);
            $sql[] = "INSERT INTO rolfuncion ".$sql2;
            $setC = null;
            $sql2 = null;
        }
        if(ejecutarConsultaSQLBeginCommit($sql))
        {
            $dev['mensaje'] = "Se actualizo correctamente el rol";
            $dev['error'] = "true";
            $dev['resultado'] = "";
        }
        else
        {
            $dev['mensaje'] = "Ocurrio un error al actulizar el rol";
            $dev['error'] = "false";
            $dev['resultado'] = "";
        }
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
function txDeleteRol($roles, $callback, $_dc, $return)
{
    $dev['mensaje'] = "";
    $dev['error']   = "";
    for($i = 0; $i< count($roles); $i++)
    {
        $sql[] = "DELETE FROM rolfuncion WHERE idrol = '".$roles[$i]."'";
        $sql[] = "DELETE FROM rol WHERE idrol = '".$roles[$i]."'";

    }
//    MostrarConsulta($sql);
    if($link=new BD)
    {
        if($link->conectar())
        {
            if(ejecutarConsultaSQLBeginCommit($sql) == true)
            {
                $dev['mensaje'] = "Se elimino(aron) correctamente los roles ";
                $dev['error']   = "true";
                $dev['resultado'] = "";
            }
            else
            {
                $dev['mensaje'] = "Error al eliminar los roles";
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
        $json = new Services_JSON();
        $output = $json->encode($dev);
        //$output = substr($output, 1);
        //$output = "$callback({".$output.");";
        print($output);
    }

}
function txDuplicateRol($idrol, $nombre, $return)
{
    $idrolA = verificarValidarText($idrol, true, "rol", "idrol");
    $nombreA   = validarText($nombre, true);

    $dev['mensaje'] = "";
    $dev['error'] = "false";
    $dev['resultado'] = "";

    if($idrolA['error'] == false)
    {
        $dev['mensaje'] = "Error en el campo idrol: ".$idrolA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    if($nombreA['error'] == false)
    {
        $dev['mensaje'] = "Error en el campo nombre: ".$nombreA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }

    $seguridadD = mktime(date("h"), date("i"), date("s"), date("m"), date("d"), date("Y"));
    $seguridadD = $_SESSION['user'].$seguridadD;

    $idRolUltimoA = findUltimoID("rol", "numero", true);
    $idRolUltimo = $idRolUltimoA['resultado'] +1;
    $idRolUltimoG = "rol-".$idRolUltimo;

    $setC[0]['campo'] = "nombre";
    $setC[0]['dato'] = $nombreA['dato'];
    $setC[1]['campo'] = "estado";
    $setC[1]['dato'] = "Activo";
    $setC[2]['campo'] = "seguridad";
    $setC[2]['dato'] = $seguridadD;
    $setC[3]['campo'] = "idrol";
    $setC[3]['dato'] = $idRolUltimoG;
    $setC[4]['campo'] = "numero";
    $setC[4]['dato'] = $idRolUltimo;
    $sql = generarInsertValues($setC);
    $sql3[] = "INSERT INTO rol ".$sql;
    $setC = null;
    $funciones = findAllFuncionByRolOnlySelecteds($idrolA['dato'], "","" , true);
    $funcionesA = $funciones['resultado'];
    for($i=0; $i<count($funcionesA); $i++)
    {
        $setC[0]['campo'] = "idrol";
        $setC[0]['dato'] = $idRolUltimoG;
        $setC[1]['campo'] = "idfuncion";
        $setC[1]['dato'] = $funcionesA[$i]['idfuncion'];
        $sql2 = generarInsertValues($setC);
        $sql3[] = "INSERT INTO rolfuncion ".$sql2;
        $setC = null;
        $sql2 = null;

    }
    if(ejecutarConsultaSQLBeginCommit($sql3))
    {
        $dev['mensaje'] = "Se guardo correctamente el rol";
        $dev['error'] = "true";
        $dev['resultado'] = "";
    }
    else
    {
        $dev['mensaje'] = "Ocurrio un error al guardar el rol";
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
