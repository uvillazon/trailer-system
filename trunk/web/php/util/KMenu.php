<?php
session_name("trailer");
session_start();
//include("../impl/UsuarioDAO.php");
include("../bd/bd.php");
include("../impl/Utils.php");
include("../impl/JSON.php");
//error_reporting(0);
$dev['mensaje'] = "";
$dev['error']   = "";
$dev['resultado'] = "";
if($_SESSION['codigo'] != null)
{
    $codigo = $_SESSION["codigo"]; //$_SESSION['codigo'];
    $da = KMenu($codigo, false);
    $dev['mensaje'] = $da['mensaje'];
    $dev['error'] = $da['error'];
    $dev['resultado'] = $da['resultado'];
}
else
{
    $dev['mensaje'] = "Uste no tiene session activa";
    $dev['error'] = "false";
    $json = new Services_JSON();
    $output = $json->encode($dev);
    print($output);
}


function KMenu($codigo, $return)
{
    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";
    if($_SESSION["user"] != NULL)
    {
        $sql = "SELECT cf.nombre, cf.estado, cf.idcategoriafuncion FROM categoriafuncion cf WHERE cf.estado = 'Activo'";
        if(true)//if($_SESSION['codigo'] != null
        {
            if($link = new BD)
            {
                if($link->conectar())
                {
//                    echo $sql;
                    if($re = $link->consulta($sql))
                    {
                        if($fi = mysql_fetch_array($re))
                        {

                            do{

                                $sql2 = "SELECT f.idfuncion, f.descripcion FROM funcion f, rolfuncion rf, rol r,
 usuario u WHERE f.idcategoriafuncion = '".$fi['idcategoriafuncion']."' AND  f.idfuncion = rf.idfuncion AND
  rf.idrol = r.idrol AND r.idrol = u.idrol AND u.idusuario = '$codigo' AND f.mostrar = 'si' ORDER BY  f.descripcion";
//echo $sql2;
                                if($re2 = $link->consulta($sql2))
                                {
                                    if($fi2 = mysql_fetch_array($re2))
                                    {
                                        $ii = 0;
                                        do{
                                            //echo mysql_num_fields($re2);
                                            for($i = 0; $i< mysql_num_fields($re2); $i++)
                                            {

                                                $devf[mysql_field_name($re2, $i)] = $fi2[$i];
                                                //echo $fi2[$i];

                                            }
                                            $devC[$ii] = $devf;
                                            $ii++;

                                        }while($fi2 = mysql_fetch_array($re2));
                                        if(count($devf)>=1)
                                        {
                                            //array de categorias en su campo nombre de categoria es igual al array de funcioens
                                            $catfun['nombre'] = $fi['nombre'];
                                            $catfun['idcategoriafuncion'] = $fi['idcategoriafuncion'];
                                            $categorias[] = $catfun;
                                            $a = $fi['nombre'];
                                            $dev[$a] = $devC;
                                            $devC = null;
                                        }
                                    }
                                }
                            }while($fi = mysql_fetch_array($re));
                            $dev['mensaje'] = "Se genero el menu";
                            $dev['error']   = "true";
                            $dev['resultado'] = $categorias;
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
                        $dev['mensaje'] = "No existe un usuario con estos roles";
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
            $dev['mensaje'] = "Su session ha expirado por favor ingrese su usuario y contrasena nuevamente";
            $dev['error']   = "false";
            $dev['resultado'] = "";
        }

    }
    else
    {
        $dev['mensaje'] = "Usted no esta logueado";
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
        print($output);
    }
}

?>