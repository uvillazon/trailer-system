<?php 
session_name("trailer");
session_start();
include("bd/bd.php");
require_once("impl/JSON.php");
require_once("impl/Utils.php");
require_once("impl/MateriaPrima.php");
require_once("impl/Categoria.php");
require_once("impl/Colores.php");
require_once("impl/Unidad.php");
require_once("impl/KardexMateriaPrima.php");
require_once("impl/MovimientoKardexMateriaPrima.php");


if(permitido("fun2000", $_SESSION['codigo'])==true)
{
    $funcion = $_GET['funcion'];
    if($funcion == "ListarMateriasPrimas"){
        $band = false;
        if($_GET['buscarcolor'] != null)
        {
            if($band == false) {
                $extras .= "  col.nombre LIKE '%".$_GET['buscarcolor']."%'";
                $band = true;
            }
            else {
                $extras .= " AND  col.nombre LIKE '%".$_GET['buscarcolor']."%'";
            }
        }
        if($_GET['buscarnombre']){
            if($band==false){
                $extras .= "  matpri.nombre LIKE '%".$_GET['buscarnombre']."%'";
                $band = true;

            }
            else {
                $extras .= " AND  matpri.nombre LIKE '%".$_GET['buscarnombre']."%'";
            }


        }
        if($_GET['buscarcategoria']){
            if($band==false){
                $extras .= "  cat.nombre LIKE '%".$_GET['buscarcategoria']."%'";
                $band = true;

            }
            else {
                $extras .= " AND  cat.nombre LIKE '%".$_GET['buscarcategoria']."%'";
            }


        }

        ListarMateriaPrima($_GET['start'], $_GET['limit'], $_GET['sort'], $_GET['dir'], $_GET['callback'], $_GET['_dc'],$extras, false);
    }

    else if($funcion == "CargarNuevaMateriaPrima"){
        CargarNuevaMateriaPrima($_GET['callback'], $_GET['_dc'], $_GET['where'], false);
        //    categoriaM idcategorai nombre
        //    unidadM idunidad nombre
        //    colorM idunidad nombre
    }
    else if($funcion == "GuardarNuevaMateriaPrima"){
        //    insertar materia prima
        //    insertar kardexmateriaprima
        //    insertar movimiientokardexmateriaprima
        GuardarNuevaMateriaPrima($_GET['callback'], $_GET['_dc'], $_GET['where'], false);
    }
    else if($funcion == "BuscarMateriaPrimaPorId"){

        //    categoriaM idcategorai nombre
        //    unidadM idunidad nombre
        //    colorM idunidad nombre
        $idmateriaprima = $_GET['idmateriaprima'];
        BuscarMateriaPrimaPorId($_GET['callback'], $_GET['_dc'], $idmateriaprima, false);

    }
    else if($funcion == "GuardarEditarMateriaPrima"){

        //modificar materiaPrima
        $idmateriaprima = $_GET['idmateriaprima'];
        GuardarEditarMateriaPrima($_GET['callback'], $_GET['_dc'], $idmateriaprima, false);

    }

    else if($funcion == "EliminarMateriaPrima"){

        //eliminar materiaprima
        //eliminar kardexmateriaprima
        //eliminar movimientokardexmateriaprima
        $idmateriaprima = $_GET['idmateriaprima'];
        EliminarMateriaPrima($_GET['callback'], $_GET['_dc'], $idmateriaprima, false);

    }
    else if($funcion == "CargarPanelConsultaMateriaPrima"){

        //modificar materiaPrima

        CargarPanelConsultaMateriaPrima($_GET['callback'], $_GET['_dc'], $_GET['where'], false);

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