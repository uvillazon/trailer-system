<?php 
session_name("trailer");
session_start();
include("bd/bd.php");
require_once("impl/JSON.php");
require_once("impl/Utils.php");
require_once("impl/StockMateriaPrima.php");
require_once("impl/MateriaPrima.php");
///dadasdasdaadas
if(permitido("fun1001", $_SESSION['codigo'])==true)
{
//    hola
    $funcion = $_GET['funcion'];
    if($funcion == "ListarStockMateriasPrimas"){
        $band = false;
        if($_GET['buscarcodigo'] != null)
        {
            if($band == false) {
                $extras .= "  mtp.codigo LIKE '%".$_GET['buscarcodigo']."%'";
                $band = true;
            }
            else {
                $extras .= " AND  mtp.codigo LIKE '%".$_GET['buscarcodigo']."%'";
            }
        }
        if($_GET['buscarnombre'] != null)
        {
            if($band == false) {
                $extras .= "  mtp.nombre LIKE '%".$_GET['buscarnombre']."%'";
                $band = true;
            }
            else {
                $extras .= " AND  mtp.nombre LIKE '%".$_GET['buscarnombre']."%'";
            }
        }
        ListarStockMateriasPrimas($_GET['star'], $_GET['limit'], $_GET['sort'], $_GET['dir'], $_GET['callback'], $_GET['_dc'],$extras, false);
  }
    
    else if($funcion = "BuscarEstiloMarcaColeccionPorId"){

    }
    else{
        //hola
        echo "else";
    }


}
else
{
    $dev['mensaje'] = "Ud no tiene privilegios para esta funcion";
    $dev['error'] = "false";
    $json = new Services_JSON();
    $output = $json->encode($dev);
    print($output);
}
?>