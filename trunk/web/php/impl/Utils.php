<?php
function findUltimoOrdenProduccion($return){
    $sql = "SELECT
  `ord`.numeroorden as numero
FROM
  `ordenproduccion` `ord`
ORDER BY
  `ord`.numero  DESC LIMIT 1";

    if($link=new BD)
    {
        if($link->conectar())
        {
            if($re = $link->consulta($sql))
            {
                if($fi = mysql_fetch_array($re))
                {

                    $dev['mensaje'] = "Existen resultados";
                    $dev['error']   = "true";
                    $dev['resultado'] = $fi['numero'];
                }
                else
                {
                    $dev['mensaje'] = "No se encontro datos en la consulta";
                    $dev['error']   = "false";
                    $dev['resultado'] = 0;
                }

            }
            else
            {
                $dev['mensaje'] = "No se encontro datos en la consulta";
                $dev['error']   = "false";
                $dev['resultado'] = 0;
            }
        }
        else
        {
            $dev['mensaje'] = "No se pudo conectar a la BD";
            $dev['error']   = "false";
            $dev['resultado'] = 0;
        }
    }
    else
    {
        $dev['mensaje'] = "No se pudo crear la conexion a la BD";
        $dev['error']   = "false";
        $dev['resultado'] = 0;
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
function validarTextSpace($dato, $null)
{
    $dev['error'] = true;
    $dev['mensaje'] = "Todo ok";
    $dev['dato'] = $dato;

    $dato = strip_tags($dato);
    $dato = trim($dato);
    if (ereg("[^A-Za-z ]", $dato)) {
        $dev['error'] = false;
        $dev['mensaje'] = "Solo se permiten caracteres";
    }
    if($null == true)
    {
        if($dato == "" || $dato == null)
        {
            $dev['error'] = false;
            $dev['mensaje'] = "El parametro es nulo";
        }
    }
    else
    {
        $dev['dato'] = $dato;
    }
    return $dev;
}
function validarTextNumericSpace($dato, $null)
{
    $dev['error'] = true;
    $dev['mensaje'] = "Todo ok";
    $dev['dato'] = $dato;

    $dato = strip_tags($dato);
    $dato = trim($dato);
    if (ereg("[^a-zA-Z0-9\-_ ]$", $dato)) {
        $dev['error'] = false;
        $dev['mensaje'] = "Solo se permiten caracteres alfanumericos";
    }
    if($null == true)
    {
        if($dato == "" || $dato == null)
        {
            $dev['error'] = false;
            $dev['mensaje'] = "El parametro es nulo";
        }
    }
    else
    {
        $dev['dato'] = $dato;
    }
    return $dev;
}

function ean($cadena)//función para generar el control de verificación de un código de barras EAN13 (son 12 dígitos y el de control separado por un guión)
{
    $cadena = strrev($cadena);//se presentan los 12 números de manera inversa
    $i = 0;
    while($i < strlen($cadena))
    {
        if($i%2 == 0) $impares += $cadena[$i];//se suman los impares
        else $pares += $cadena[$i];//se suman los pares
        $i++;
    }
    $suma = $pares + ($impares*3); //se realiza la suma de los pares con el resultado de multiplicar la suma de los impares por 3
    $valor = 10 -($suma%10);
    if($valor == 10){
        return $valor =0;
    }
    else{
        return $valor;
    }
    ////se resta a 10 el resto de dividir esa suma por 10.
}

function validarText($dato, $null)
{
    $dev['error'] = true;
    $dev['mensaje'] = "Todo ok";
    $dev['dato'] = $dato;

    $dato = strip_tags($dato);
    $dato = trim($dato);
    if($null == true)
    {
        if($dato == "" || $dato == null)
        {
            $dev['error'] = false;
            $dev['mensaje'] = "El parametro es nulo";
        }
    }
    else
    {
        $dev['dato'] = $dato;
    }
    return $dev;
}
function validarEmail($dato, $null)
{
    $dato = strip_tags($dato);
    $dato = trim($dato);
    $dev['error'] = true;
    $dev['mensaje'] = "Todo ok";
    $dev['dato'] = $dato;
    return $dev;
}
function validarEntero($dato, $null)
{
    $dev['error'] = "true";
    $dev['mensaje'] = "Todo ok";
    $dev['dato'] = $dato;
    return $dev;
}
function validarDecimal($dato, $null)
{
    $dev['error'] = "true";
    $dev['mensaje'] = "Todo ok";
    $dev['dato'] = $dato;
    return $dev;
}
function validarTelefono($dato, $null)
{
    $dev['error'] = "true";
    $dev['mensaje'] = "Todo ok";
    $dev['dato'] = $dato;
    return $dev;
}
function validarPassword($pass1, $pass2, $null)
{
    $pass1 = strip_tags($pass1);
    $pass1 = trim($pass1);
    $pass2 = strip_tags($pass2);
    $pass2 = trim($pass2);
    $dev['error'] = false;
    $dev['mensaje'] = "Todo ok";
    $dev['dato'] = $pass1;
    if($pass1 == $pass2)
    {
        $dev['error'] = true;
    }
    else
    {
        $dev['mensaje'] = "No coinciden las contrasenas";
        $dev['dato'] = "";
    }
    return $dev;
}
function validarFecha($dato, $return = true, $formato = "yyyy-mm-dd")
{
    $dev['error'] = true;
    $dev['mensaje'] = "Todo ok";
    $dev['dato'] = $dato;

    if(strtolower($formato) == "yyyy-mm-dd")
    {
        $a = split("-", $dato);
        $ano = $a[0];
        $mes = $a[1];
        $dia = $a[2];
    }

    if(checkdate($mes, $dia, $ano) != true)
    {
        $dev['error'] = false;
    }
    return $dev;
}
function compararFecha($fechaanterior , $horaanterior, $fechanuevo , $horanuevo , $return = true){
    $dev['error'] = true;
    $dev['mensaje'] = "Todo ok";
    $a = split("-", $fechaanterior);
    $ano = $a[0];
    $mes = $a[1];
    $dia = $a[2];
    $a1 = split(":", $horaanterior);
    $h = $a1[0];
    $m = $a1[1];
    $s = $a1[2];
    $b = split("-", $fechanuevo);
    $ano1 = $b[0];
    $mes1 = $b[1];
    $dia1 = $b[2];
    $b1 = split(":", $horanuevo);
    $h1 = $b1[0];
    $m1 = $b1[1];
    $s1 = $b1[2];
    $anterior = mktime($h, $m, $s, $mes, $dia, $ano);
    $nuevo = mktime($h1, $m1, $s1, $mes1, $dia1, $ano1);
    if($anterior > $nuevo){
        $dev['error'] = false;
    }
    return $dev;
}
function verificarBorrarenTablas($dato, $existe)
{
    if($existe == true)
    {
        return true;
    }
    return $dev;
}
function verificarValidarText($dato, $existe, $tabla, $campo)
{
    if($existe == true)
    {
        $link = new BD;
        $link->conectar();
        $sql = "SELECT $campo FROM $tabla WHERE $campo = '$dato'";
        $dev['error'] = false;
        $dev['mensaje'] = "Todo ok2";
        $dev['dato'] = $dato;
        if($re = $link->consulta($sql))
        {
            if(mysql_num_rows($re)>=1)
            {
                $dev['error'] = true;
                $dev['mensaje'] = "Todo ok3";
            }
            else
            {
                $dev['mensaje'] = "No existe ".$dato." En la tabla ".$tabla;
            }
        }
        else
        {
            $dev['mensaje'] = "Error al buscar ".$dato." En la tabla ".$tabla;
        }
    }
    else
    {
        $link = new BD;
        $link->conectar();
        $sql = "SELECT $campo FROM $tabla WHERE $campo = '$dato'";
        $dev['error'] = false;
        $dev['mensaje'] = "Todo ok";
        $dev['dato'] = $dato;
        if($re = $link->consulta($sql))
        {
            if(mysql_num_rows($re)>=1)
            {
                $dev['mensaje'] = "Ya existe el dato ".$dato." En la tabla ".$tabla;
                $dev['error'] = false;
            }
            else
            {
                $dev['error'] = true;
            }
        }
        else
        {
            $dev['mensaje'] = "Error al buscar ".$dato." En la tabla ".$tabla;
        }
    }
    return $dev;
}
function verificarValidarTextWithCondition($dato, $existe, $tabla, $campo,$idvalor,$valor){
    if($existe == true)
    {
        $link = new BD;
        $link->conectar();
        $sql = "SELECT $campo FROM $tabla WHERE $campo = '$dato' AND $idvalor = '$valor'";
        //        echo $sql;
        //        exit();
        $dev['error'] = false;
        $dev['mensaje'] = "Todo ok2";
        $dev['dato'] = $dato;
        if($re = $link->consulta($sql))
        {
            if(mysql_num_rows($re)>=1)
            {
                $dev['error'] = true;
                $dev['mensaje'] = "Todo ok3";
            }
            else
            {
                $dev['mensaje'] = "No existe ".$dato." En la tabla ".$tabla;
            }
        }
        else
        {
            $dev['mensaje'] = "Error al buscar ".$dato." En la tabla ".$tabla;
        }
    }
    else
    {
        $link = new BD;
        $link->conectar();
        $sql = "SELECT $campo FROM $tabla WHERE $campo = '$dato' AND $idvalor = '$valor'";
        $dev['error'] = false;
        $dev['mensaje'] = "Todo ok";
        $dev['dato'] = $dato;
        if($re = $link->consulta($sql))
        {
            if(mysql_num_rows($re)>=1)
            {
                $dev['mensaje'] = "Ya existe el dato ".$dato." En la tabla ".$tabla;
                $dev['error'] = false;
            }
            else
            {
                $dev['error'] = true;
            }
        }
        else
        {
            $dev['mensaje'] = "Error al buscar ".$dato." En la tabla ".$tabla;
        }
    }
    return $dev;
}
function NumeroTuplas($sql)
{

    $link = new BD;
    $link->conectar();

    $dev['error'] = false;
    $dev['mensaje'] = "Todo ok2";

    if($re = $link->consulta($sql))
    {
        if(mysql_num_rows($re)>=1)
        {
            $dev['error'] = true;
            $dev['mensaje'] = "Todo ok3";
            $dev['resultado'] = mysql_num_rows($re);
        }
        else
        {
            $dev['mensaje'] = "no hay datos";
            $dev['resultado'] = 0;
        }
    }
    else
    {
        $dev['mensaje'] = "La consulta esta mal realizada";
    }


    return $dev;
}
//Esta funcion sirve para verificar si existe un dato que es unico en una tabla necesita
// el datos y tambien necesita el id de la tupla
function verificarValidarTextUnicoEdit($idcampo, $idvalor, $existe, $tabla, $campo, $valor)
{
    $link = new BD;
    $link->conectar();
    $sql = "SELECT $campo FROM $tabla WHERE $idcampo  = '$idvalor'";
    $dev['error'] = false;
    $dev['mensaje'] = "Todo ok2";
    $dev['dato'] = $dato;
    if($re = $link->consulta($sql))
    {
        if(mysql_num_rows($re)>=1)
        {
            if($fi = mysql_fetch_array($re))
            {
                $dat = "S".$fi[$campo];
                $dat1 = "S".$valor;
                if($dat == $dat1)
                {
                    $de = verificarValidarText($valor, true, $tabla, $campo);
                    $dat .="si";
                }
                else
                {
                    $de = verificarValidarText($valor, false, $tabla, $campo);
                    $dat .="no";
                }
                $dev['error'] = $de['error'];
                $dev['mensaje'] = $de['mensaje'];
                $dev['dato'] = $de['dato'];
            }
            else
            {
                $dev['mensaje'] = "No existe ".$dato." En la tabla ".$tabla;
            }
        }
        else
        {
            $dev['mensaje'] = "No existe ".$dato." En la tabla ".$tabla;
        }
    }
    else
    {
        $dev['mensaje'] = "Error al buscar ".$dato." En la tabla ".$tabla;
    }
    return $dev;
}
function generarSetsUpdate($setC)
{
    $dev = '';
    if($setC != NULL)
    {
        $i = 0;
        do{
            if($setC[$i]['dato'] != null && $setC[$i]['dato'] != "")
            {
                if($dev == "")
                {
                    $dev = $setC[$i]['campo']." = '".$setC[$i]['dato']."' ";
                }
                else
                {
                    $dev .= ", ".$setC[$i]['campo']." = '".$setC[$i]['dato']."' ";
                }
            }
            $i++;
        }while(next($setC)!=false);
    }
    return $dev;
}
function generarInsertValues($setC)
{
    $tem = $setC;
    $dev = '';
    if($setC != NULL)
    {
        $i = 0;
        do{
            if(($setC[$i]['dato'] != null && $setC[$i]['dato'] != "") || ($setC[$i]['dato'] == "0"))
            {
                //                echo $setC[$i]['campo']."----".$setC[$i]['dato']."---";
                if($dev == "")
                {
                    $dev = "(".$setC[$i]['campo']." ";
                }
                else
                {
                    $dev .= ", ".$setC[$i]['campo']." ";
                }
            }
            $i++;
        }while(next($setC)!=false);
        $dev .= ") VALUES";
        $devv ="";
        $i = 0;
        do{
            if(($setC[$i]['dato'] != null && $setC[$i]['dato'] != "") || ($setC[$i]['dato'] == "0"))
            {
                if($devv == "")
                {
                    $tt = substr($tem[$i]['dato'], 0, 1);
                    if($tt == "(")
                    {
                        $dev .= "(".$tem[$i]['dato']." ";
                    }
                    else
                    {
                        $dev .= "('".$tem[$i]['dato']."' ";
                    }
                    $devv ="asdf";
                }
                else
                {
                    $tt = substr($tem[$i]['dato'], 0, 1);
                    if($tt == "(")
                    {
                        $dev .= ", ".$tem[$i]['dato']." ";
                    }
                    else
                    {
                        $dev .= ", '".$tem[$i]['dato']."' ";
                    }
                }
            }
            $i++;
        }while(next($tem)!=false);
    }
    $dev .= ");";
    return $dev;
}
function generarWhereUpdate($setC)
{
    $dev = "";
    if($setC != NULL)
    {
        $i = 0;
        do{

            if($setC[$i]['dato'] != null && $setC[$i]['dato'] != "")
            {
                if($dev == "")
                {
                    $dev = $setC[$i]['campo']." = '".$setC[$i]['dato']."' ";
                }
                else
                {
                    $dev .= " AND ".$setC[$i]['campo']." = '".$setC[$i]['dato']."' ";
                }
            }
            $i++;
        }while(next($setC)!=false);
        $dev = $dev.";";
    }
    return $dev;
}

function ejecutarConsultaSQLBeginCommit($sql)
{
    if($sql!=NULL)
    {
        $link=new BD;
        $link->conectar();
        $i=0;
        $link->consulta("begin");
        do{
            if($link->consulta($sql[$i]))
            {
                $bandera=true;

            }
            else
            {
                $bandera=false;
                break;
            }
            $i++;
        }while(next($sql)!=false);
        if($bandera==true)
        { $link->consulta("commit");
            $sqll = ReturnConsultas($sql);
            GenerarErrorLog("error mysql", "Edwin", $sqll, "Ok", "no", "si", "./");
            return true;
        }
        else
        { $link->consulta("rollback");
            $sqll = ReturnConsultas($sql)."-->".$link->getError;
            GenerarErrorLog("error", "Edwin", $sqll, "Error", "no", "si", "./");
            return false;
        }
        $link->cerrar();
    }
    else
    {
        return false;
    }
}
function ejecutarConsultaSQL($sql)
{
    if($sql!=NULL)
    {
        $link=new BD;
        $link->conectar();
        $i=0;
        do{
            $link->consulta("begin");
            $link->consulta($sql[$i]);
            $link->consulta("commit");
            $i++;
        }while(next($sql)!=false);

        return true;
    }
    else
    {
        return false;
    }
}
/**
* Generar codigo alfanumerico
*
* Este codigo genera codigo alfanumerico, como parametros hay que pasarle
* la longitud y el tipo que puede ser numerico y alfanumerico por defecto
* esta como alfanumerico.
*
*@access public
*@param int $longitud La longitud del codigo
*@param string $tipo El tipo de codigo que se quiere generar
*@return string Retorna el codigo generado
*@author Edwin Salguero C.
*@author edwin16@gmail.com
*@copyright Kernel S.R.L.
*@copyright http://www.kernel.com.bo
*@version 1.0
*
*/
function genera_password($longitud,$tipo="alfanumerico")
{
    if ($tipo=="alfanumerico"){
        $exp_reg="[^A-Z0-9]";
    } elseif ($tipo=="numerico"){
        $exp_reg="[^0-9]";
    }
    return strtoupper(substr(eregi_replace($exp_reg, "", md5(time())) .
            eregi_replace($exp_reg, "", md5(time())) .
            eregi_replace($exp_reg, "", md5(time())),
            0, $longitud));
}

function MostrarErroresAvisos()
{
    if($_SESSION['error']!=NULL)
    {
        GenerarErrorLog($_SESSION['error'],'Edwin', $_SESSION['error'], 'error', 'si', 'si', '');
        $_SESSION['error']='';
    }
    if($_SESSION['mensaje']!=NULL)
    {
        GenerarErrorLog($_SESSION['mensaje'],'Edwin', $_SESSION['mensaje'], 'mensaje', 'si', 'si', '');
        $_SESSION['mensaje']='';
    }
}

function GenerarErrorLog($error, $autor, $otros='.', $tipo='error', $mostrar='si', $guardar='si', $dirLog='')
{
    if($guardar == 'si')
    {
        $file=date("Ymd");

        $file=$dirLog."logs/".$file.".txt";
        $log=$tipo."\t".$_SESSION['user']."\t".$autor."\t".date("H:i:s")."\t".$_SERVER['PHP_SELF'].$otros."\n\n";
        if($f=fopen($file, 'a'))
        {
            fwrite($f, $log);
            fclose($f);
        }
    }
    if($tipo=='error')
    {
        $dev="<div class='error'>".$error."</div>";
    }
    else
    {
        $dev="<div class='mensaje'>".$error."</div>";
    }
    if($mostrar=='si')
    {
        echo $dev;
    }
    else
    {
        return $dev;
    }
}
/**
* Funcion para verificar si el usuario tiene permiso
* este archivo, arque pasarle como parametros codigo
* del archivo y del usuario
* @access public
* @param string $codigo codigo del archivo
* @param int $ci ci de identificacion del usuario
* @return boolean
* @author Edwin Salguero C.
* @author edwin16@gmail.com
* @copyright Kernel S.R.L.
* @copyright http://www.kernel.com.bo
* @version 1.0
*/
function permitido($codigo, $ci)
{
    if(($codigo!=NULL)&&($ci!=NULL))
    {
        $lincf=new BD;
        $lincf->conectar();
        $sql="SELECT u.idusuario FROM usuario u, funcion f, rol r, rolfuncion rf  WHERE (u.idusuario = '".$ci."') AND (u.idrol = r.idrol) AND (r.idrol = rf.idrol) AND  (rf.idfuncion = f.idfuncion) AND (f.idfuncion='".$codigo."')";
        $archivos=$lincf->consulta($sql);
        if($tabla = mysql_fetch_array($archivos))
        {
            if($tabla["idusuario"]==$ci)
            {return true;}
            else
            {return false;}
        }
        else
        {return false;}
        //$lincf->cerrar();
    }
    else
    {
        return false;
    }
}
/**
* Muestra la lista de consultas que se agregan a un arreglo
* @access public
* @param array $a Arreglo de consultas
* @return void
* @author Edwin Salguero C.
* @author edwin16@gmail.com
* @copyright Kernel S.R.L.
* @copyright http://www.kernel.com.bo
* @version 1.0
*/
function MostrarConsulta($a)
{
    if($a!=NULL)
    {
        $i=0;
        do{
            //            echo $i.".- ".$a[$i];
            echo $a[$i];
            echo "<br>";
            $i++;
        }while(next($a)!=false);
    }
}
function ReturnConsultas($a)
{
    $dev = '';
    if($a!=NULL)
    {
        $i=0;
        do{
            $dev .=$a[$i]." -- ";
            $i++;
        }while(next($a)!=false);
    }
    return $dev;
}
function findBySqlReturnCampoUnique($sql, $return, $existe, $campo)
{
    $dev['mensaje'] = "";
    $dev['error']   = "";
    //cho $sql."-------".$campo."<br>";
    if($link=new BD)
    {
        if($link->conectar())
        {
            if($re = $link->consulta($sql))
            {
                if($fi = mysql_fetch_array($re))
                {
                    if(mysql_num_rows($re) == 1)
                    {
                        $dev['mensaje'] = "Existen resultados";
                        $dev['error']   = "true";
                        $dev['resultado'] = $fi[$campo];
                    }
                    else if(mysql_num_rows($re) > 1)
                    {
                        $dev['mensaje'] = "Existe mas de un resultado para este dato";
                        $dev['error']   = "false";
                        $dev['resultado'] = "";
                    }
                    else
                    {
                        $dev['mensaje'] = "No existen datos";
                        $dev['error']   = "false";
                        $dev['resultado'] = "";
                    }
                }
                else
                {
                    $dev['mensaje'] = "No se encontro datos en la consulta2".$sql;
                    $dev['error']   = "false";
                    $dev['resultado'] = "";
                }

            }
            else
            {
                $dev['mensaje'] = "No se encontro datos en la consulta3";
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
        print($output);
    }

}
function findBySeguridad($tabla, $campo, $seguridad, $return)
{

    $dev['mensaje'] = "";
    $dev['error']   = "";

    echo $sql = "SELECT $campo FROM $tabla WHERE seguridad = '$seguridad'";
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

                        $dev['mensaje'] = "Existen resultados";
                        $dev['error']   = "true";
                        $dev['resultado'] = $fi[$campo];
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
        print($output);
    }

}

function findUltimoID($tabla, $campo, $return)
{

    $dev['mensaje'] = "";
    $dev['error']   = "";

    $sql = "SELECT MAX($campo) AS ultimo FROM $tabla";
    //    echo $sql;
    if($campo != null && $tabla != null)
    {
        if($link=new BD)
        {
            if($link->conectar())
            {
                if($re = $link->consulta($sql))
                {
                    if($fi = mysql_fetch_array($re))
                    {

                        $dev['mensaje'] = "Existen resultados";
                        $dev['error']   = "true";
                        $dev['resultado'] = $fi['ultimo'];
                    }
                    else
                    {
                        $dev['mensaje'] = "No se encontro datos en la consulta";
                        $dev['error']   = "false";
                        $dev['resultado'] = 0;
                    }

                }
                else
                {
                    $dev['mensaje'] = "No se encontro datos en la consulta";
                    $dev['error']   = "false";
                    $dev['resultado'] = 0;
                }
            }
            else
            {
                $dev['mensaje'] = "No se pudo conectar a la BD";
                $dev['error']   = "false";
                $dev['resultado'] = 0;
            }
        }
        else
        {
            $dev['mensaje'] = "No se pudo crear la conexion a la BD";
            $dev['error']   = "false";
            $dev['resultado'] = 0;
        }
    }
    else
    {
        $dev['mensaje'] = "El codigo de rol es nulo";
        $dev['error']   = "false";
        $dev['resultado'] = 0;
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
function findUltimoIDCondicion($tabla, $campo,$condicion,$valor, $return)
{

    $dev['mensaje'] = "";
    $dev['error']   = "";

    $sql = "SELECT MAX($campo) AS ultimo FROM $tabla WHERE $condicion = '$valor'" ;
    if($campo != null && $tabla != null)
    {
        if($link=new BD)
        {
            if($link->conectar())
            {
                if($re = $link->consulta($sql))
                {
                    if($fi = mysql_fetch_array($re))
                    {

                        $dev['mensaje'] = "Existen resultados";
                        $dev['error']   = "true";
                        $dev['resultado'] = $fi['ultimo'];
                    }
                    else
                    {
                        $dev['mensaje'] = "No se encontro datos en la consulta";
                        $dev['error']   = "false";
                        $dev['resultado'] = 0;
                    }

                }
                else
                {
                    $dev['mensaje'] = "No se encontro datos en la consulta";
                    $dev['error']   = "false";
                    $dev['resultado'] = 0;
                }
            }
            else
            {
                $dev['mensaje'] = "No se pudo conectar a la BD";
                $dev['error']   = "false";
                $dev['resultado'] = 0;
            }
        }
        else
        {
            $dev['mensaje'] = "No se pudo crear la conexion a la BD";
            $dev['error']   = "false";
            $dev['resultado'] = 0;
        }
    }
    else
    {
        $dev['mensaje'] = "El codigo de rol es nulo";
        $dev['error']   = "false";
        $dev['resultado'] = 0;
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
function redondear($numero, $digito=2)
{

    $dev = 0.00;
    if($numero==NULL)
    {
        $numero=0;
        $numero=$numero.".";
        for($i=0;$i<$digito;$i++)
        {
            $numero=$numero."0";
        }
        $dev = $numero;
    }
    else
    {
        if(strpos($numero, '.')==false)
        {
            $numero=$numero.".";
            for($i=0;$i<$digito;$i++)
            {
                $numero=$numero."0";
            }
            $dev = $numero;
        }
        else
        {
            if(strlen($numero)<strpos($numero, '.')+$digito+1)
            {
                $numero=substr($numero, 0, (strpos($numero, '.')+$digito+1));
                for($i=1;$i<$digito;$i++)
                {
                    $numero=$numero."0";
                }
                $dev = $numero;
            }
            else
            {
                $dev = substr($numero, 0, (strpos($numero, '.')+$digito+1));
            }
        }
    }
    return $dev;
}

function getTablaToArrayOfSQL($sql)
{
    //
    //    echo $sql;
    $totalCount = 0;
    if($link=new BD)
    {
        if($link->conectar())
        {
            if($re = $link->consulta($sql))
            {

                //                echo mysql_num_rows($re);
                if($fi = mysql_fetch_array($re))
                {
                    $ii = 0;
                    //$totalCount = mysql_num_rows($re);
                    do{

                        for($i = 0; $i< mysql_num_fields($re); $i++)
                        {

                            if(mysql_field_type($re, $i) == "real")
                            {
                                //echo mysql_field_name($re, $i)."--".mysql_field_type($re, $i)."--------------".redondear($fi[$i]);;
                                $value{$ii}{mysql_field_name($re, $i)}= redondear($fi[$i]);
                            }
                            else
                            {
                                $value{$ii}{mysql_field_name($re, $i)}= $fi[$i];
                            }
                        }

                        $ii++;
                    }while($fi = mysql_fetch_array($re));
                    $dev['mensaje'] = "Existen resultados";
                    $dev['error']   = "true";
                    $dev['resultado'] = $value;
                }
                else
                {
                    $dev['mensaje'] = "No se encontro datos en la consulta2".mysql_error();
                    $dev['error']   = "false";
                    $dev['resultado'] = "";
                }
            }
            else
            {
                $dev['mensaje'] = "Error en la consulta";
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
    return $dev;
}
function allBySql($sql)
{
    $sql = eregi_replace("SELECT (.*) FROM", "SELECT COUNT(*) AS total FROM", $sql);
    //    echo "<br>";
    //    echo $sql;
    $dev = 0;
    //    echo $sql;
    if($sql != null)
    {
        if($link=new BD)
        {
            if($link->conectar())
            {
                if($re = $link->consulta($sql))
                {

                    if($fi = mysql_fetch_array($re))
                    {
                        $dev = mysql_num_rows($re);
                    }
                    else
                    {
                        $dev = 0;
                    }
                }
                else
                {
                    $dev = 0;
                }
            }
            else
            {
                $dev = 0;
            }
        }
        else
        {
            $dev = 0;
        }
    }
    else
    {

    }
    return $dev;
}
function ObtenerNavegador($user_agent) {
    //    $navegadores = array(
    //           'Opera' => 'Opera',
    //           'Mozilla Firefox'=> '(Firebird)|(Firefox)',
    //           'Galeon' => 'Galeon',
    //           'Mozilla'=>'Gecko',
    //           'MyIE'=>'MyIE',
    //           'Lynx' => 'Lynx',
    //           'Netscape' => '(Mozilla/4\.75)|(Netscape6)|(Mozilla/4\.08)|(Mozilla/4\.5)|(Mozilla/4\.6)|(Mozilla/4\.79)',
    //           'Konqueror'=>'Konqueror',
    //           'Internet Explorer 7' => '(MSIE 7\.[0-9]+)',
    //           'Internet Explorer 6' => '(MSIE 6\.[0-9]+)',
    //           'Internet Explorer 5' => '(MSIE 5\.[0-9]+)',
    //           'Internet Explorer 4' => '(MSIE 4\.[0-9]+)',
    //    );
    //    foreach($navegadores as $navegador=>$pattern){
    //        if (eregi($pattern, $user_agent))
    //        return $navegador;
    //    }
    //    return 'Desconocido';

    $is_lynx = $is_gecko = $is_winIE = $is_macIE = $is_opera = $is_NS4 = $is_safari = $is_chrome = false;

    if (strpos($_SERVER['HTTP_USER_AGENT'], 'Lynx') !== false) {
        $is_lynx = true;
    } elseif ( strpos(strtolower($_SERVER['HTTP_USER_AGENT']), 'chrome') !== false ) {
        $is_chrome = true;
    } elseif ( strpos(strtolower($_SERVER['HTTP_USER_AGENT']), 'webkit') !== false ) {
        $is_safari = true;
    } elseif (strpos($_SERVER['HTTP_USER_AGENT'], 'Gecko') !== false) {
        $is_gecko = true;
    } elseif (strpos($_SERVER['HTTP_USER_AGENT'], 'MSIE') !== false && strpos($_SERVER['HTTP_USER_AGENT'], 'Win') !== false) {
        $is_winIE = true;
    } elseif (strpos($_SERVER['HTTP_USER_AGENT'], 'MSIE') !== false && strpos($_SERVER['HTTP_USER_AGENT'], 'Mac') !== false) {
        $is_macIE = true;
    } elseif (strpos($_SERVER['HTTP_USER_AGENT'], 'Opera') !== false) {
        $is_opera = true;
    } elseif (strpos($_SERVER['HTTP_USER_AGENT'], 'Nav') !== false && strpos($_SERVER['HTTP_USER_AGENT'], 'Mozilla/4.') !== false) {
        $is_NS4 = true;
    }

    //    $is_IE = ( $is_macIE || $is_winIE );
    //        return $is_chrome;
    return true;
}
function convertir_a_letras($numero, $abr = "Bs.")
{
    if($numero>0)
    {
        $a = explode('.', $numero);
        if($a[1] == null)
        {
            $cadena = NumerosALetras($a[0])." 00/100 $abr";
        }
        else
        {
            $cadena = NumerosALetras($a[0])." ".$a[1]."/100 $abr";
        }
        $cadena = ucfirst($cadena);
        return $cadena;
    }
    return '';
}
function NumerosALetras($Numero){


    $Decimales = 0;
    //$Numero = intval($Numero);
    $letras = "";

    while ($Numero != 0){

        // '*---> ValidaciÃ³n si se pasa de 100 millones

        If ($Numero >= 1000000000) {
            $letras = "Error en Conversion a Letras";
            $Numero = 0;
            $Decimales = 0;
        }

        // '*---> Centenas de MillÃ³n
        If (($Numero < 1000000000) And ($Numero >= 100000000)){
            If ((Intval($Numero / 100000000) == 1) And (($Numero - (Intval($Numero / 100000000) * 100000000)) < 1000000)){
                $letras .= (string) "cien millones ";
            }
            Else {
                $letras = $letras & Centenas(Intval($Numero / 100000000));
                If ((Intval($Numero / 100000000) <> 1) And (Intval($Numero / 100000000) <> 5) And (Intval($Numero / 100000000) <> 7) And (Intval($Numero / 100000000) <> 9)) {
                    $letras .= (string) "cientos ";
                }
                Else {
                    $letras .= (string) " ";
                }
            }
            $Numero = $Numero - (Intval($Numero / 100000000) * 100000000);
        }

        // '*---> Decenas de MillÃ³n
        If (($Numero < 100000000) And ($Numero >= 10000000)) {
            If (Intval($Numero / 1000000) < 16) {
                $tempo = Decenas(Intval($Numero / 1000000));
                $letras .= (string) $tempo;
                $letras .= (string) " millones ";
                $Numero = $Numero - (Intval($Numero / 1000000) * 1000000);
            }
            Else {
                $letras = $letras & Decenas(Intval($Numero / 10000000) * 10);
                $Numero = $Numero - (Intval($Numero / 10000000) * 10000000);
                If ($Numero > 1000000) {
                    $letras .= $letras & " y ";
                }
            }
        }

        // '*---> Unidades de MillÃ³n
        If (($Numero < 10000000) And ($Numero >= 1000000)) {
            $tempo=(Intval($Numero / 1000000));
            If ($tempo == 1) {
                $letras .= (string) " un millon ";
            }
            Else {
                $tempo= Unidades(Intval($Numero / 1000000));
                $letras .= (string) $tempo;
                $letras .= (string) " millones ";
            }
            $Numero = $Numero - (Intval($Numero / 1000000) * 1000000);
        }

        // '*---> Centenas de Millar
        If (($Numero < 1000000) And ($Numero >= 100000)) {
            $tempo=(Intval($Numero / 100000));
            $tempo2=($Numero - ($tempo * 100000));
            If (($tempo == 1) And ($tempo2 < 1000)) {
                $letras .= (string) "cien mil ";
            }
            Else {
                $tempo=Centenas(Intval($Numero / 100000));
                $letras .= (string) $tempo;
                $tempo=(Intval($Numero / 100000));
                If (($tempo <> 1) And ($tempo <> 5) And ($tempo <> 7) And ($tempo <> 9)) {
                    $letras .= (string) "cientos ";
                }
                Else {
                    $letras .= (string) " ";
                }
            }
            $Numero = $Numero - (Intval($Numero / 100000) * 100000);
        }

        // '*---> Decenas de Millar
        If (($Numero < 100000) And ($Numero >= 10000)) {
            $tempo= (Intval($Numero / 1000));
            If ($tempo < 16) {
                $tempo = Decenas(Intval($Numero / 1000));
                $letras .= (string) $tempo;
                $letras .= (string) " mil ";
                $Numero = $Numero - (Intval($Numero / 1000) * 1000);
            }
            Else {
                $tempo = Decenas(Intval($Numero / 10000) * 10);
                $letras .= (string) $tempo;
                $Numero = $Numero - (Intval(($Numero / 10000)) * 10000);
                If ($Numero > 1000) {
                    $letras .= (string) " y ";
                }
                Else {
                    $letras .= (string) " mil ";

                }
            }
        }


        // '*---> Unidades de Millar
        If (($Numero < 10000) And ($Numero >= 1000)) {
            $tempo=(Intval($Numero / 1000));
            If ($tempo == 1) {
                $letras .= (string) "un";
            }
            Else {
                $tempo = Unidades(Intval($Numero / 1000));
                $letras .= (string) $tempo;
            }
            $letras .= (string) " mil ";
            $Numero = $Numero - (Intval($Numero / 1000) * 1000);
        }

        // '*---> Centenas
        If (($Numero < 1000) And ($Numero > 99)) {
            If ((Intval($Numero / 100) == 1) And (($Numero - (Intval($Numero / 100) * 100)) < 1)) {
                $letras = $letras & "cien ";
            }
            Else {
                $temp=(Intval($Numero / 100));
                $l2=Centenas($temp);
                $letras .= (string) $l2;
                If ((Intval($Numero / 100) <> 1) And (Intval($Numero / 100) <> 5) And (Intval($Numero / 100) <> 7) And (Intval($Numero / 100) <> 9)) {
                    $letras .= "cientos ";
                }
                Else {
                    $letras .= (string) " ";
                }
            }

            $Numero = $Numero - (Intval($Numero / 100) * 100);

        }

        // '*---> Decenas
        If (($Numero < 100) And ($Numero > 9) ) {
            If ($Numero < 16 ) {
                $tempo = Decenas(Intval($Numero));
                $letras .= $tempo;
                $Numero = $Numero - Intval($Numero);
            }
            Else {
                $tempo= Decenas(Intval(($Numero / 10)) * 10);
                $letras .= (string) $tempo;
                $Numero = $Numero - (Intval(($Numero / 10)) * 10);
                If ($Numero > 0.99) {
                    $letras .=(string) " y ";

                }
            }
        }

        // '*---> Unidades
        If (($Numero < 10) And ($Numero > 0.99)) {
            $tempo=Unidades(Intval($Numero));
            $letras .= (string) $tempo;

            $Numero = $Numero - Intval($Numero);
        }


        // '*---> Decimales
        If ($Decimales > 0) {

            // $letras .=(string) " con ";
            // $Decimales= $Decimales*100;
            // echo ("*");
            // $Decimales = number_format($Decimales, 2);
            // echo ($Decimales);
            // $tempo = Decenas(Intval($Decimales));
            // $letras .= (string) $tempo;
            // $letras .= (string) "centavos";
        }
        Else {
            If (($letras <> "Error en Conversion a Letras") And (strlen(Trim($letras)) > 0)) {
                $letras .= (string) " ";

            }
        }
        return $letras;
    }
}
function Centenas($VCentena) {
    $Numeros[0] = "cero";
    $Numeros[1] = "uno";
    $Numeros[2] = "dos";
    $Numeros[3] = "tres";
    $Numeros[4] = "cuatro";
    $Numeros[5] = "cinco";
    $Numeros[6] = "seis";
    $Numeros[7] = "siete";
    $Numeros[8] = "ocho";
    $Numeros[9] = "nueve";
    $Numeros[10] = "diez";
    $Numeros[11] = "once";
    $Numeros[12] = "doce";
    $Numeros[13] = "trece";
    $Numeros[14] = "catorce";
    $Numeros[15] = "quince";
    $Numeros[20] = "veinte";
    $Numeros[30] = "treinta";
    $Numeros[40] = "cuarenta";
    $Numeros[50] = "cincuenta";
    $Numeros[60] = "sesenta";
    $Numeros[70] = "setenta";
    $Numeros[80] = "ochenta";
    $Numeros[90] = "noventa";
    $Numeros[100] = "ciento";
    $Numeros[101] = "quinientos";
    $Numeros[102] = "setecientos";
    $Numeros[103] = "novecientos";
    If ($VCentena == 1) { return $Numeros[100]; }
    Else If ($VCentena == 5) { return $Numeros[101];}
    Else If ($VCentena == 7 ) {return ( $Numeros[102]); }
    Else If ($VCentena == 9) {return ($Numeros[103]);}
    Else {return $Numeros[$VCentena];}

}
function Unidades($VUnidad) {
    $Numeros[0] = "cero";
    $Numeros[1] = "un";
    $Numeros[2] = "dos";
    $Numeros[3] = "tres";
    $Numeros[4] = "cuatro";
    $Numeros[5] = "cinco";
    $Numeros[6] = "seis";
    $Numeros[7] = "siete";
    $Numeros[8] = "ocho";
    $Numeros[9] = "nueve";
    $Numeros[10] = "diez";
    $Numeros[11] = "once";
    $Numeros[12] = "doce";
    $Numeros[13] = "trece";
    $Numeros[14] = "catorce";
    $Numeros[15] = "quince";
    $Numeros[20] = "veinte";
    $Numeros[30] = "treinta";
    $Numeros[40] = "cuarenta";
    $Numeros[50] = "cincuenta";
    $Numeros[60] = "sesenta";
    $Numeros[70] = "setenta";
    $Numeros[80] = "ochenta";
    $Numeros[90] = "noventa";
    $Numeros[100] = "ciento";
    $Numeros[101] = "quinientos";
    $Numeros[102] = "setecientos";
    $Numeros[103] = "novecientos";

    $tempo=$Numeros[$VUnidad];
    return $tempo;
}
function Decenas($VDecena) {
    $Numeros[0] = "cero";
    $Numeros[1] = "uno";
    $Numeros[2] = "dos";
    $Numeros[3] = "tres";
    $Numeros[4] = "cuatro";
    $Numeros[5] = "cinco";
    $Numeros[6] = "seis";
    $Numeros[7] = "siete";
    $Numeros[8] = "ocho";
    $Numeros[9] = "nueve";
    $Numeros[10] = "diez";
    $Numeros[11] = "once";
    $Numeros[12] = "doce";
    $Numeros[13] = "trece";
    $Numeros[14] = "catorce";
    $Numeros[15] = "quince";
    $Numeros[20] = "veinte";
    $Numeros[30] = "treinta";
    $Numeros[40] = "cuarenta";
    $Numeros[50] = "cincuenta";
    $Numeros[60] = "sesenta";
    $Numeros[70] = "setenta";
    $Numeros[80] = "ochenta";
    $Numeros[90] = "noventa";
    $Numeros[100] = "ciento";
    $Numeros[101] = "quinientos";
    $Numeros[102] = "setecientos";
    $Numeros[103] = "novecientos";
    $tempo = ($Numeros[$VDecena]);
    return $tempo;
}
function findBySqlUnique($sql, $campos, $return)
{
    $dev['mensaje'] = "";
    $dev['error']   = "";

    $devT = getTablaToArrayOfSQL($sql);
    $dev['error'] = $devT['error'];
    $dev['mensaje'] = $devT['mensaje'];
    $dev['resultado'] = $devT['resultado'][0];
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
function getConvertido($monto, $monedaOper, $monedaCuenta, $idtipoCambio)
{
    $tcSusA = getTcMonto($idtipoCambio, "mon-1001");
    $tcSusR = $tcSusA['resultado'][0];
    $tcSus = $tcSusR['monto'];
    $tcBs = 1;

    if($monedaOper == "mon-1000")
    {
        if($monedaCuenta == "mon-1000")
        {
            return $monto;
        }
        else
        {
            return $monto/$tcSus;
        }
    }
    else
    {
        //si viene en dolares u otro caso
        if($monedaCuenta == "mon-1000")
        {
            return $monto*$tcSus;
        }
        else
        {
            return $monto;
        }
    }
}
function TamanoPermitido($dato,$tamano){
    $inttamano = strlen($dato);
    if($inttamano>$tamano){
        $dev['error'] = false;
        $dev['mensaje'] = "Excede el Tamano Permitido";
        $dev['dato'] = "ok";
    }
    else{
        $dev['error'] = true;
        $dev['mensaje'] = "todo Ok";
        $dev['dato'] = "ok";
    }
    return $dev;
}


?>