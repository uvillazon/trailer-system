    <?php
function findAllUsuario($start, $limit, $sort, $dir, $callback, $_dc, $where = '', $return = "true")
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
    $password = md5($password);
    if($where == null || $where == "")
    {
        $sql = "SELECT u.idusuario, u.nombre AS nombres, u.apellido1, u.apellido2, u.ci AS carnetidentidad,
u.email, u.telefono, u.celular, u.login, u.paswd, u.fechareg, u.estado, r.nombre AS rol FROM usuario u,
rol r WHERE u.idrol = r.idrol  $order LIMIT $start,$limit ";
        $sqlTotal = "SELECT count(u.*) AS total FROM usuario u, rol r WHERE u.idrol = r.idrol ";
    }
    else
    {
        $sql = "SELECT u.idusuario, u.nombre AS nombres, u.apellido1, u.apellido2, u.ci AS carnetidentidad,
u.email, u.telefono, u.celular, u.login, u.paswd, u.fechareg, u.estado, r.nombre AS rol, a.nombre AS almacen FROM usuario u,
rol r WHERE u.idrol = r.idrol AND $where $order LIMIT $start,$limit ";
        $sqlTotal = "SELECT count(u.*) AS total FROM usuario u, rol r WHERE u.idrol = r.idrol AND $where";
    }
    // echo $sql;
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

function findRolSucursalAlmacenForNewUsuario($callback, $_dc, $return)
{
    $dev['mensaje'] = "";
    $dev['error']   = "";

    $rold = findAllRol(0, 0, "", "", "", "", true);
    if($rold['error'] == true)
    {
        $value['roles'] = "true";
        $value['rolesM'] = $rold['resultado'];
    }
    else
    {
        $value['roles'] = "false";
    }

    
    $dev['mensaje'] = "Existen resultados";
    $dev['error']   = "true";
    $dev['resultado'] = $value;
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
function findUsuarioByIdRolSucursalAlmacen($idusuario, $callback, $_dc, $return)
{
    $dev['mensaje'] = "";
    $dev['error']   = "";

    $sql = "SELECT u.* FROM usuario u WHERE  u.idusuario = '$idusuario'";
    if($idusuario != null)
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

                        $rold = findAllRol(0, 0, "", "", "", "", true);
                        if($rold['error'] == true)
                        {
                            $value['roles'] = "true";
                            $value['rolesM'] = $rold['resultado'];
                        }
                        else
                        {
                            $value['roles'] = "false";
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
        $dev['mensaje'] = "El codigo de usuario es nulo";
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
        $json = new Services_JSON();
        $output = $json->encode($dev);
        //$output = substr($output, 1);
        //$output = "$callback({".$output.");";
        print($output);
    }

}

function txNewUsuario($nombres, $apellido1, $apellido2, $carnetidentidad, $email, $telefono, $celular,
    $login, $pass1, $pass2, $idrol, $estado, $idalmacen, $return)
{
    $nombresA   = validarText($nombres, true);
    $apellido1A = validarText($apellido1, true);
    $apellido2A = validarText($apellido2, false);
    $carnetidentidadA = validarText($carnetidentidad, true);
    $emailA     = validarEmail($email, true);
    $telefonoA  = validarTelefono($telefono, false);
    $celularA   = validarTelefono($celular, false);
    $loginA     = validarText($login, true);
    $passwdA    = validarPassword($pass1, $pass2, true);
    $idrolA     = verificarValidarText($idrol, true, "rol", "idrol");
    $estadoA    = validarText($estado, true);
   

    $dev['mensaje'] = "";
    $dev['error'] = "false";
    $dev['resultado'] = "";

    if($nombresA['error'] == false)
    {
        $dev['mensaje'] = "Error en el campo nombres: ".$nombresA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    if($apellido1A['error'] == false)
    {
        $dev['mensaje'] = "Error en el campo Apellido1: ".$apellido1A['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    if($apellido2A['error'] == false)
    {
        $dev['mensaje'] = "Error en el campo Apellido2: ".$apellido2A['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    if($carnetidentidadA['error'] == false)
    {
        $dev['mensaje'] = "Error en el campo CI: ".$carnetidentidadA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    if($emailA['error'] == false)
    {
        $dev['mensaje'] = "Error en el campo Email: ".$emailA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    if($telefonoA['error'] == false)
    {
        $dev['mensaje'] = "Error en el campo Telefono: ".$telefonoA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    if($celularA['error'] == false)
    {
        $dev['mensaje'] = "Error en el campo celular: ".$celularA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    if($loginA['error'] == false)
    {
        $dev['mensaje'] = "Error en el campo login: ".$loginA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    if($passwdA['error'] == false)
    {
        $dev['mensaje'] = "Error en el campo Password: ".$passwdA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    if($idrolA['error'] == false)
    {
        $dev['mensaje'] = "Error en el campo Rol: ".$idrolA['mensaje'];
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
    
    if(existeUsuarioByLogin($loginA['dato']) == true)
    {
        $dev['mensaje'] = "Ya existe un usuario con este login: ".$loginA['dato'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    if(existeUsuarioByCI($carnetidentidadA['dato']) == true)
    {
        $dev['mensaje'] = "Ya existe un usuario con este CI: ".$carnetidentidadA['dato'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $idUsuarioUltimoA = findUltimoID("usuario", "numero", true);
    $idUsuarioUltimo = $idUsuarioUltimoA['resultado'] +1;

    $setC[0]['campo'] = "nombre";
    $setC[0]['dato'] = $nombresA['dato'];
    $setC[1]['campo'] = "apellido1";
    $setC[1]['dato'] = $apellido1A['dato'];
    $setC[2]['campo'] = "apellido2";
    $setC[2]['dato'] = $apellido2A['dato'];
    $setC[3]['campo'] = "ci";
    $setC[3]['dato'] = $carnetidentidadA['dato'];
    $setC[4]['campo'] = "email";
    $setC[4]['dato'] = $emailA['dato'];
    $setC[5]['campo'] = "telefono";
    $setC[5]['dato'] = $telefonoA['dato'];
    $setC[6]['campo'] = "celular";
    $setC[6]['dato'] = $celularA['dato'];
    $setC[7]['campo'] = "paswd";
    $setC[7]['dato'] = $passwdA['dato'];
    $setC[8]['campo'] = "idrol";
    $setC[8]['dato'] = $idrolA['dato'];
    $setC[9]['campo'] = "estado";
    $setC[9]['dato'] = $estadoA['dato'];
    $setC[10]['campo'] = "login";
    $setC[10]['dato'] = $loginA['dato'];
    $setC[11]['campo'] = "fechareg";
    $setC[11]['dato'] = date("Y-m-d");
    $setC[12]['campo'] = "numero";
    $setC[12]['dato'] = $idUsuarioUltimo;
    $setC[13]['campo'] = "idusuario";
    $setC[13]['dato'] = "usr-".$idUsuarioUltimo;

    $set = generarInsertValues($setC);

    $sql[] = "INSERT INTO usuario ".$set;
//    MostrarConsulta($sql);
    if(ejecutarConsultaSQLBeginCommit($sql) == true)
    {
        $dev['mensaje'] = "Se guardo correctamente el usuario";
        $dev['error'] = "true";
        $dev['resultado'] = "";
    }
    else
    {
        $dev['mensaje'] = "Ocurrio un error guardar el usuario";
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
function txDeleteUsuario($idusuario, $callback, $_dc, $return)
{
    $dev['mensaje'] = "";
    $dev['error']   = "";
    $usuarioM = verificarValidarText($idusuario, true, "usuario", "idusuario");
    if($usuarioM['error'] == false)
    {
        $dev['mensaje'] = "Error en el campo idusuario: ".$usuarioM['mensaje'];
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
        exit;
    }
    $sql = "DELETE FROM usuario WHERE idusuario = '".$usuarioM['dato']."'";
    if($usuarioM['error'] == "true")
    {
        if($link=new BD)
        {
            if($link->conectar())
            {
                if($re = $link->consulta($sql))
                {
                   
                    if($user['error'] == "false")
                    {
                        $dev['mensaje'] = "Se elimino correctamente al usuario";
                        $dev['error']   = "true";
                    }
                    else
                    {
                        $dev['mensaje'] = "Error al eliminar el usuario";
                        $dev['error']   = "false";
                    }
                }
                else
                {
                    $dev['mensaje'] = "No se puede eliminar porque tiene asignado items";
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
        $dev['mensaje'] = $usuarioM['mensaje'];
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
function txUpdateUsuario($idUsuario, $nombres, $apellido1, $apellido2, $carnetidentidad, $email, $telefono, $celular,
    $login, $pass1, $pass2, $fechareg, $idrol, $estado, $idalmacen, $return)
{
    $idUsuarioA = verificarValidarText($idUsuario, true, "usuario", "idusuario");
    $nombresA   = validarText($nombres, true);
    $apellido1A = validarText($apellido1, true);
    $apellido2A = validarText($apellido2, false);
    $carnetidentidadA = validarText($carnetidentidad, true);
    $emailA     = validarEmail($email, true);
    $telefonoA  = validarText($telefono, false);
    $celularA   = validarText($celular, false);
    $loginA     = validarText($login, false);
    $passwdA    = validarPassword($pass1, $pass2, true);
    $fecharegA  = validarFecha($fechareg);
    $idrolA     = verificarValidarText($idrol, true, "rol", "idrol");
    $estadoA    = validarText($estado, true);
 

    $dev['mensaje'] = "";
    $dev['error'] = "false";
    $dev['resultado'] = "";

    if($idUsuarioA['error'] == false)
    {
        $dev['mensaje'] = "Error en el campo idusuario: ".$idUsuarioA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    if($nombresA['error'] == false)
    {
        $dev['mensaje'] = "Error en el campo nombres: ".$nombresA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    if($apellido1A['error'] == false)
    {
        $dev['mensaje'] = "Error en el campo Apellido1: ".$apellido1A['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    if($apellido2A['error'] == false)
    {
        $dev['mensaje'] = "Error en el campo Apellido2: ".$apellido2A['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    if($carnetidentidadA['error'] == false)
    {
        $dev['mensaje'] = "Error en el campo CI: ".$carnetidentidadA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    if($emailA['error'] == false)
    {
        $dev['mensaje'] = "Error en el campo Email: ".$emailA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    if($telefonoA['error'] == false)
    {
        $dev['mensaje'] = "Error en el campo Telefono: ".$telefonoA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    if($celularA['error'] == false)
    {
        $dev['mensaje'] = "Error en el campo celular: ".$celularA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    if($loginA['error'] == false)
    {
        $dev['mensaje'] = "Error en el campo login: ".$loginA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    if($passwdA['error'] == false)
    {
        $dev['mensaje'] = "Error en el campo Password: ".$passwdA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    if($fecharegA['error'] == false)
    {
        $dev['mensaje'] = "Error en el campo fecha registro: ".$fecharegA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    if($idrolA['error'] == false)
    {
        $dev['mensaje'] = "Error en el campo Rol: ".$idrolA['mensaje'];
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
  
    if($passwdA['dato'] == "d41d8cd98f00b204e9800998ecf8427e")
    {
        $passwdA['dato'] = "";
    }
    $setC[0]['campo'] = "nombre";
    $setC[0]['dato'] = $nombresA['dato'];
    $setC[1]['campo'] = "apellido1";
    $setC[1]['dato'] = $apellido1A['dato'];
    $setC[2]['campo'] = "apellido2";
    $setC[2]['dato'] = $apellido2A['dato'];
    $setC[3]['campo'] = "ci";
    $setC[3]['dato'] = $carnetidentidadA['dato'];
    $setC[4]['campo'] = "email";
    $setC[4]['dato'] = $emailA['dato'];
    $setC[5]['campo'] = "telefono";
    $setC[5]['dato'] = $telefonoA['dato'];
    $setC[6]['campo'] = "celular";
    $setC[6]['dato'] = $celularA['dato'];
    $setC[7]['campo'] = "paswd";
    $setC[7]['dato'] = $passwdA['dato'];
    $setC[8]['campo'] = "fechareg";
    $setC[8]['dato'] = $fecharegA['dato'];
    $setC[9]['campo'] = "idrol";
    $setC[9]['dato'] = $idrolA['dato'];
    $setC[10]['campo'] = "estado";
    $setC[10]['dato'] = $estadoA['dato'];
 

    $set = generarSetsUpdate($setC);

    $wher[0]['campo'] = "idusuario";
    $wher[0]['dato'] = $idUsuarioA['dato'];
    $wher[1]['campo'] = "login";
    $wher[1]['dato'] = $loginA['dato'];

    $where = generarWhereUpdate($wher);

    $sql[] = "UPDATE usuario SET ".$set." WHERE ".$where;
    //        MostrarConsulta($sql);
    if(ejecutarConsultaSQLBeginCommit($sql) == true)
    {
        $dev['mensaje'] = "Se actualizaron correctamente los datos";
        $dev['error'] = "true";
        $dev['resultado'] = "";
    }
    else
    {
        $dev['mensaje'] = "Ocurrio un error al actualizar los datos";
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
function existeUsuarioByLogin($login)
{
    $dev = false;
    $sql = "SELECT u.* FROM usuario u WHERE u.login = '$login'";
    if($login != null)
    {
        if($link=new BD)
        {
            if($link->conectar())
            {
                if($re = $link->consulta($sql))
                {
                    if($fi = mysql_fetch_array($re))
                    {
                        if($fi['login'] == $login)
                        {
                            $dev = true;
                        }
                        else
                        {
                            $dev = false;
                        }
                    }
                    else
                    {
                        $dev = false;
                    }

                }
                else
                {
                    $dev = false;
                }
            }
            else
            {
                $dev = false;
            }
        }
        else
        {
            $dev = false;
        }
    }
    else
    {
        $dev = false;
    }
    return $dev;
}

function existeUsuarioByCI($ci)
{
    $dev = false;
    $sql = "SELECT u.* FROM usuario u WHERE u.carnetidentidad = '$ci'";
    if($ci != null)
    {
        if($link=new BD)
        {
            if($link->conectar())
            {
                if($re = $link->consulta($sql))
                {
                    if($fi = mysql_fetch_array($re))
                    {
                        if($fi['carnetidentidad'] == $ci)
                        {
                            $dev = true;
                        }
                        else
                        {
                            $dev = false;
                        }
                    }
                    else
                    {
                        $dev = false;
                    }

                }
                else
                {
                    $dev = false;
                }
            }
            else
            {
                $dev = false;
            }
        }
        else
        {
            $dev = false;
        }
    }
    else
    {
        $dev = false;
    }
    return $dev;
}


//lista todos los usuarios administradores de almacenes
function ListarUsuariosAdmin($start, $limit, $sort, $dir, $callback, $_dc, $where = '', $return = false){

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
  usu.idusuario,
  usu.idrol,
  usu.idalmacen,
  usu.nombre,
  usu.apellido1,
  usu.apellido2,
  CONCAT(usu.apellido1, ' ', usu.apellido2) AS apellido,
  usu.ci,
  usu.email,
  usu.telefono,
  usu.celular,
  usu.login,
  usu.paswd,
  usu.fechareg,
  usu.numero,
  usu.estado
FROM
  usuario usu
WHERE
  usu.idrol = 'rol-1001';

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
function BuscarAlmacenes($callback, $_dc, $where = '', $return = false){

    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";

    $ciudad = ListarAlmacen("", "", "", "", "", "", "", true);
    if($ciudad["error"]==false){
        $dev['mensaje'] = "No se pudo encontrar almacenes";
        $dev['error']   = "false";
        $dev['resultado'] = "";

    }
    $value['almacenes'] = $ciudad['error'];
    $value["almacenM"] = $ciudad['resultado'];
    $dev["resultado"] = $value;


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
function BuscarAlmacenPorUsuario($idusuario,$callback, $_dc, $return = false){

    $dev['mensaje'] = "";
    $dev['error']   = "";
    $dev['resultado'] = "";


    $ciudad = ListarAlmacen("", "", "", "", "", "", "", true);
    if($ciudad["error"]==false){
        $dev['mensaje'] = "No se pudo encontrar almacenes";
        $dev['error']   = "false";
        $dev['resultado'] = "";

    }
    $value['almacenes'] = $ciudad['error'];
    $value["almacenM"] = $ciudad['resultado'];


    $sql ="
SELECT
  usu.idusuario,
  usu.idrol,
  usu.idalmacen,
  usu.nombre,
  usu.apellido1,
  usu.apellido2,
  CONCAT(usu.apellido1, ' ', usu.apellido2) AS apellido,
  usu.ci,
  usu.email,
  usu.telefono,
  usu.celular,
  usu.login,
  usu.paswd,
  usu.fechareg,
  usu.numero,
  usu.estado
FROM
  usuario usu
WHERE
  usu.idrol = 'rol-1001' AND
  usu.idusuario = '$idusuario';

";
    if($idusuario != null)
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


function GuardarNuevoUsuarioAdmin(){
    $dev['mensaje'] = "";
    $dev['error'] = "false";
    $dev['resultado'] = "";
    $codigo = $_GET['codigo'];
    $codigoA = validarText($codigo, true);
    if($codigoA['error']==false){
        $dev['mensaje'] = "Error en el codigo de Linea: ".$codigoA['mensaje'];
        $json = new Services_JSON();
        $output = $json->encode($dev);
        print($output);
        exit;
    }
    $idlinea = $_GET['idlinea'];
    $nombre = $_GET['nombre'];
    //    $codigo=$_GET['codigo'];
    $descripcion = $_GET['descripcion'];
    //    $pais = $_GET['pais'];
    //    $ciudad = $_GET['ciudad'];
    //    $direccion = $_GET['direcion'];
    //    $email = $_GET['email'];
    //    $web = $_GET['web'];
    //    $representante = $_GET['representante'];
    //    $idproveedor = $_GET['idproveedor'];
    $sql[] = getSqlUpdateLineas($idlinea,$nombre, $codigo, $descripcion, false);

    //    MostrarConsulta($sql);
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
?>