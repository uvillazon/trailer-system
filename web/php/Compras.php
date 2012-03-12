<?php 
session_name("trailer");
session_start();
include("bd/bd.php");
require_once("impl/JSON.php");
require_once("impl/Utils.php");
require_once("impl/Compras.php");
require_once("impl/Proveedor.php");
require_once("impl/Categoria.php");
require_once("impl/Empleado.php");
require_once("impl/MateriaPrima.php");
require_once("impl/DetalleCompra.php");
require_once("impl/MovimientoKardexMateriaPrima.php");

///dadasdasdaadas
if((permitido("fun2002", $_SESSION['codigo'])==true)||(permitido("fun2003", $_SESSION['codigo'])==true))
{
    //    hola
    $funcion = $_GET['funcion'];
    if($funcion == "CargarNuevaCompra"){
        CargarNuevaCompra($_GET['callback'], $_GET['_dc'], $_GET['where'], false);
    }

    else if($funcion=="ListarCompras"){
        $band = false;
        if($_GET['buscarnumerodocumento'] != null)
        {
            if($band == false) {
                $extras .= "  cmp.numerodocumento LIKE '%".$_GET['buscarnumerodocumento']."%'";
                $band = true;
            }
            else {
                $extras .= " AND  cmp.numerodocumento LIKE '%".$_GET['buscarnumerodocumento']."%'";
            }
        }
        if($_GET['buscarfecha']){
            if($band==false){
                $extras .= "  cmp.fecha LIKE '%".$_GET['buscarfecha']."%'";
                $band = true;

            }
            else {
                $extras .= " AND  cmp.fecha LIKE '%".$_GET['buscarfecha']."%'";
            }


        }

        ListarCompras($_GET['star'], $_GET['limit'], $_GET['sort'], $_GET['dir'], $_GET['callback'], $_GET['_dc'],$extras, false);


    }
    else if($funcion == "GuardarNuevaCompra"){
        $resultado = $_GET['resultado'];
        $json = new Services_JSON();
        $idcompra= $_GET['idcompra'];
        $datos = $json->decode($resultado);
        if(($idcompra == "")||($idcompra == null)){
            GuardarNuevaCompra($_GET['callback'], $_GET['_dc'], $datos, false);
        }
        else{
            GuardarEditarCompras($_GET['callback'], $_GET['_dc'], $datos, false);
        }



    }
    else if($funcion == "BuscarCompraPorId"){
        $idcompra = $_GET['idcompra'];


        BuscarCompraPorId($_GET['callback'], $_GET['_dc'], $idcompra, false);

    }

    else if ($funcion =="EliminarCompras"){
        $idcompra = $_GET['idcompra'];


        EliminarCompras($_GET['callback'], $_GET['_dc'], $idcompra, false);

    }

    //    sistema flores jaldin
    else if ($funcion =="CargarNuevaCompraFlores"){
          CargarNuevaCompraFlores($_GET['callback'], $_GET['_dc'], $_GET['where'], false);

    }


    //    fin sistema flores jaldin
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