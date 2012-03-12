<?php
include("../dao/impl/JSON.php");
$datos = $_GET['datos'];
$json = new Services_JSON();
$input = '{"a":1,"b":2,"c":3,"d":4,"e":5}';
$value = $json->decode($datos);
$a = $value->asdf;
echo count($a);
for($i = 0; $i < 3; $i ++)
{
    echo $a[$i];
    echo "<br>";
}
?>