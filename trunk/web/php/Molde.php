
<?php 
session_name("trailer");
session_start();
include("bd/bd.php");
include("impl/Utils.php");
require_once("impl/JSON.php");
require_once("impl/Molde.php");
require_once("impl/CategoriaProducto.php");

if(permitido("fun2011", $_SESSION['codigo'])==true)
{
    $funcion = $_GET['funcion'];
    if($funcion == "ListarMoldes"){

        $band = false;
        if($_GET['buscarcodigo'] != null)
        {
            if($band == false) {
                $extras .= "  prd.codigo LIKE '%".$_GET['buscarcodigo']."%'";
                $band = true;
            }
            else {
                $extras .= " AND  prd.codigo LIKE '%".$_GET['buscarcodigo']."%'";
            }
        }
        if($_GET['buscarnombre'] != null)
        {
            if($band == false) {
                $extras .= " prd.nombre LIKE '%".$_GET['buscarnombre']."%'";
                $band = true;
            }
            else {
                $extras .= " AND prd.nombre LIKE '%".$_GET['buscarnombre']."%'";
            }
        }
         if($_GET['buscarcategoria'] != null)
        {
            if($band == false) {
                $extras .= " ctp.nombre LIKE '%".$_GET['buscarcategoria']."%'";
                $band = true;
            }
            else {
                $extras .= " AND ctp.nombre LIKE '%".$_GET['buscarcategoria']."%'";
            }
        }


        ListarMoldes($_GET['star'], $_GET['limit'], $_GET['sort'], $_GET['dir'], $_GET['callback'], $_GET['_dc'],$extras, false);
    }
  


    else if($funcion == "CargarNuevoMolde"){
        //devolver encargadoM{idempleado , nombre }
        CargarNuevoMolde($_GET['callback'], $_GET['_dc'], $_GET['where'], false);
    }
    
   
    else if($funcion == "GuardarNuevoMolde"){
        GuardarNuevoMolde($_GET['callback'], $_GET['_dc'], $_GET['where'], false);
    }

    
    else if($funcion == "GuardarEditarMolde"){
        $idmolde = $_GET['idmolde'];
        GuardarEditarMolde($_GET['callback'], $_GET['_dc'], $idmolde, false);

    }
  
    else if($funcion == "EliminarMolde"){
        $idproducto = $_GET['idmolde'];
        EliminarMolde($_GET['callback'], $_GET['_dc'], $idproducto, false);

    }
    else{
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