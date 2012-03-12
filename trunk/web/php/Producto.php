
<?php 
session_name("trailer");
session_start();
include("bd/bd.php");
include("impl/Utils.php");
require_once("impl/JSON.php");
require_once("impl/Producto.php");
require_once("impl/OpBordado.php");
require_once("impl/CategoriaProducto.php");
require_once("impl/Grupo.php");
require_once("impl/Componentes.php");
if((permitido("fun2006", $_SESSION['codigo'])==true)||(permitido("fun2007", $_SESSION['codigo'])==true))
{
    $funcion = $_GET['funcion'];
    if($funcion == "ListarProductos"){

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


        ListarProductos($_GET['star'], $_GET['limit'], $_GET['sort'], $_GET['dir'], $_GET['callback'], $_GET['_dc'],$extras, false);
    }
    else if($funcion == "ListarProductosLogos"){

        $band = false;
        if($_GET['buscarempresa'] != null)
        {
            if($band == false) {
                $extras .= "  emp.nombre LIKE '%".$_GET['buscarempresa']."%'";
                $band = true;
            }
            else {
                $extras .= " AND  emp.nombre LIKE '%".$_GET['buscarempresa']."%'";
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


        ListarProductosLogos($_GET['star'], $_GET['limit'], $_GET['sort'], $_GET['dir'], $_GET['callback'], $_GET['_dc'],$extras, false);
    }


    else if($funcion == "CargarNuevoProducto"){
        //devolver encargadoM{idempleado , nombre }
        CargarNuevoProducto($_GET['callback'], $_GET['_dc'], $_GET['where'], false);
    }
    else if($funcion == "CargarNuevoLogo"){
        //devolver encargadoM{idempleado , nombre }
        CargarNuevoLogo($_GET['callback'], $_GET['_dc'], $_GET['where'], false);
    }
    else if($funcion == "GuardarNuevoLogo"){
        //devolver encargadoM{idempleado , nombre }
        GuardarNuevoLogo($_GET['callback'], $_GET['_dc'], $_GET['where'], false);
    }
    else if($funcion == "GuardarNuevoProducto"){
        GuardarNuevoProducto($_GET['callback'], $_GET['_dc'], $_GET['where'], false);
    }

    else if($funcion == "BuscarProductoPorId"){
        //devolver encargadoM{idempleado , nombre }
        $idproducto = $_GET['idproducto'];

        BuscarProductoPorId($_GET['callback'], $_GET['_dc'], $idproducto, false);

    }
    else if($funcion == "GuardarEditarProducto"){
        $idproducto = $_GET['idproducto'];
        GuardarEditarProducto($_GET['callback'], $_GET['_dc'], $idproducto, false);

    }
    else if($funcion == "GuardarEnlazeBordadoOp"){
        $idproducto = $_GET['idproducto'];
        GuardarEnlazeBordadoOp($_GET['callback'], $_GET['_dc'], $idproducto, false);

    }
    else if($funcion == "GuardarEditarLogo"){
        $idproducto = $_GET['idproducto'];
        GuardarEditarLogo($_GET['callback'], $_GET['_dc'], $idproducto, false);

    }
    else if($funcion == "EliminarProducto"){
        $idproducto = $_GET['idproducto'];
        EliminarProducto($_GET['callback'], $_GET['_dc'], $idproducto, false);

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