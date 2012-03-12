<?php
class BDPrincipal{
    var $linea,$link;
    function conectar(){

      $this->link = mysql_connect("localhost","root", '');
//      $this->link = mysql_connect("192.168.0.10","root", '');

	   if (!$this->link) {
            return false;
            exit;
        }
        if(!mysql_select_db("bdtrailer"))
        {
            return false;
            exit;
        }
        else
        {
            return true;
        }
    }
    function cerrar(){
        mysql_close($this->link);
    }

    function consulta($q){
        
        return mysql_query($q,$this->link);
    }
    function getError(){

        return mysql_error($this->link);
    }
    /************************************************************/
    /* FUNCION QUE RETORNA UN ARRAY SI ES QUE EXISTE PARA 		*/
    /* INTERACTUAR CON ELLA										*/
    /************************************************************/
    function consultaSelectt($q)
    {
        if($result=mysql_query($this->link, $q))
        {
            if($table=mysql_fetch_array($result))
            {
                return $result;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }
    function getAtr($q){
        return mysql_query("describe ".$q,$this->link);
    }
    function buscar($aguja,$pajar,$atr){
        return mysql_query("select * from ".$pajar." where ".
            $atr."=".$aguja,$this->link);
    }
    function getValues($str){
        return trim(substr(strstr($str,':'),1));
    }
    function getAtributos($q){
        $atr;
        $res=mysql_query("describe ".$q,$this->link);
        while ($fila = mysql_fetch_array($res)){
            $atr[$i]=$fila[0];
            $i++;
        }
        return $atr;
    }
    function getTipos($q){
        $atr;
        $res=mysql_query("describe ".$q,$this->link);
        while ($fila = mysql_fetch_array($res)){
            $atr[$i]=$fila[1];
            $i++;
        }
        return $atr;
    }
    function equ($atr){global $TIPOS;
        $key=substr($atr,0,strpos($atr,'(')>0?strpos($atr,'('):strlen($atr));
        $num=strstr($atr,'(');
        $equ=array("varchar"=>"cadena de ".$num." caracteres",
                   "tinytext"=>"cadena de ".$num." 255 caracteres",
                   "text"=>"cadena de ".$num." 65535 caracteres",
                   "mediumtext"=>"cadena de ".$num." 16777215 caracteres",
                   "int"=>"entero de ".$num." digitos",
                   "tinyint"=>"entero de ".$num." digitos",
                   "smallint"=>"entero de ".$num." digitos",
                   "mediumint"=>"entero de ".$num." digitos",
                   "bigint"=>"entero de ".$num." digitos",
                   "enum"=>"opción ".$num,
                   "set"=>"opción múltiple ".$num,
                   "date"=>"fecha (Año-Mes-Día)",
                   "datetime"=>"fecha y hora (Año-Mes-Día Hr:Mn:Sg)",
                   "char"=>"cadena de ".$num." caracteres",
                   "time"=>"hora (Hor-Min-Seg)",
                   "year"=>"año (AAAA)",
                   "double"=>"decimal ".$num." digitos",
                   "float"=>"decimal ".$num." digitos");
        return $equ[$key];
    }
    function addColumna($columna,$opciones,$tabla){
        $r="alter table ".$tabla." add column ".$columna." ".$opciones;
        echo 'fdsafds'.$r;
        mysql_query($r,$this->link);
        return mysql_error();
    }
    function eliminarTabla($tabla){
        $devolver="";
        mysql_query("delete from prod_serv where nombreTabla='".$tabla."'",$this->link);
        if(mysql_error()!="")$devolver=mysql_error();
        else{
            mysql_query("drop table ".$tabla);
            $devolver=mysql_error();
        }
        return $devolver;
    }
    function eliminarColumna($columna,$tabla){
        mysql_query("alter table ".$tabla." drop column ".$columna,$this->link);
        return mysql_error();
    }
}
$TIPOS=array("TINYINT"=>array("(N) ","entero de rango [-128 , 127]"),
            "SMALLINT"=>array("(N) ","entero de rango [-32768 , 32767]"),
            "MEDIUMINT"=>array("(N) ","entero de rango [-8388608 , 8388607]"),
            "INT"=>array("(N) ","entero de rango [-2147483648 , 2147483647]"),
            "BIGINT"=>array("(N) ","entero de rango [-29223372036854775808 , 9223372036854775807]"),
            "FLOAT"=>array("(N,D) ","decimal de rango [-3.402823466E+38 , -1.175494351E-38] y [1.175494351E-38 , 3.402823466E+38]"),
            "DOUBLE"=>array("(N,D) ","decimal de rango [-1.7976931348623157E+308 , -2.2250738585072014E-308] y [2.2250738585072014E-308 , 1.7976931348623157E+308]"),
            "DATE"=>array("N/A","fecha [AAAA-MM-DD]"),
            "DATETIME"=>array("N/A","fecha y hora [AAAA-MM-DD HH:MM:SS]"),
            "TIME"=>array("N/A","hora [HH:MM:SS]"),
            "YEAR"=>array("N/A","año [AAAA]"),
            "CHAR"=>array("(C)","cadena de caracteres de tamaño fijo"),
            "VARCHAR"=>array("(C)","cadena de caracteres de tamaño variable"),
            "TINYTEXT"=>array("N/A","texto de hasta 255 caracteres"),
            "TEXT"=>array("N/A","texto de hasta 65535 caracteres"),
            "MEDIUMTEXT"=>array("N/A","texto de hasta 16777215 caracteres"),
            "ENUM"=>array("N/A","opcion que puede tomar solo un valor ('opc1','opc2',...)"),
            "SET"=>array("N/A","opcion que puede tomar múltiples valores ('opc1','opc2',...)"),
);
?>