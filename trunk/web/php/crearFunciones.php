<?php
session_name("trailer");
session_start();
?>
<form action="crearFunciones.php" method="GET">
    <input type="text" id='tabla' name="tabla">
    <input type="submit" value="Aceptar">
</form>

<?php
include("bd/bd.php");
$link = new BD;
$link->conectar();
$sql = "describe ".$_GET['tabla'].";";
$cab = "";
$cuerpo = "";
$pie = "";
if($re = $link->consulta($sql))
{
    if($fi = mysql_fetch_array($re))
    {
        $i = 0;
        $cab .= "function getSqlNew".ucfirst($_GET['tabla'])."(";
        do{
            if($i == 0)
            {
                $cab .= "\$".$fi['Field'];
            }
            else
            {
                $cab .= ", \$".$fi['Field'];
            }
            $cuerpo .= "\$setC[$i]['campo'] = '".$fi['Field']."';";
            $cuerpo .= "<br>";
            $cuerpo .= "\$setC[$i]['dato'] = \$".$fi['Field'].";";
            $cuerpo .= "<br>";
            $i++;
        }while($fi = mysql_fetch_array($re));
        $cab .= ", \$return)";
    }
    $cab .= "{<br>";
    echo $cab;
    echo $cuerpo;
    echo "\$sql2 = generarInsertValues(\$setC);";
    echo "<br>";
    echo "return \"INSERT INTO ".$_GET['tabla']." \".\$sql2;";
    echo "<br>";
    echo "}";
}
echo "<br><br><br>";
$link = new BD;
$link->conectar();
$sql = "describe ".$_GET['tabla'].";";
$cab = "";
$cuerpo = "";
$pie = "";
if($re = $link->consulta($sql))
{
    if($fi = mysql_fetch_array($re))
    {
        $i = 0;
        $ii = 0;
        $cab .= "function getSqlUpdate".ucfirst($_GET['tabla'])."(";
        do{
            if($i == 0)
            {
                $cab .= "\$".$fi['Field'].",";
            }
            else
            {
                $cab .= ",\$".$fi['Field'];
            }
            if(substr($fi['Field'], 0, 2) != "id")
            {
                $cuerpo .= "\$setC[$i]['campo'] = '".$fi['Field']."';";
                $cuerpo .= "<br>";
                $cuerpo .= "\$setC[$i]['dato'] = \$".$fi['Field'].";";
                $cuerpo .= "<br>";
                $i++;
            }
            else
            {
                $where .= "\$wher[$ii]['campo'] = '".$fi['Field']."';";
                $where .= "<br>";
                $where .= "\$wher[$ii]['dato'] = \$".$fi['Field'].";";
                $where .= "<br>";
                $ii++;
            }

        }while($fi = mysql_fetch_array($re));
        $cab .= ", \$return)";
    }
    $cab .= "{<br>";
    echo $cab;
    echo $cuerpo;
    echo "<br>";
    echo "\$set = generarSetsUpdate(\$setC);";
    echo "<br>";
    echo $where;
    echo "<br>";
    echo "\$where = generarWhereUpdate(\$wher);";
    echo "<br>";
    echo "return \"UPDATE ".$_GET['tabla']." SET \".\$set.\" WHERE \".\$where;";
    echo "<br>";
    echo "}";

    //para eliminar
    echo "<br>";
    echo "<br>";
    echo "<br>";
    echo "<br>";
    echo "<br>";
    $del .= "function getSqlDelete".ucfirst($_GET['tabla'])."(\$id".($_GET['tabla'])."){";
    $del .=  "<br>";
    $del .= "return \"DELETE FROM ".$_GET['tabla']." WHERE id".($_GET['tabla'])." ='\$id".($_GET['tabla'])."';\";";
    $del .=  "<br>";
    $del .= "}";
    echo $del;
}




?>