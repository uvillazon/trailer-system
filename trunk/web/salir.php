<?php 
session_name("trailer");
session_start();
error_reporting(0);
include("php/impl/Utils.php");
include("php/bd/bd.php");
//FinalizarLogSession($_SESSION['user'], $_SESSION['codese']);
session_destroy();
$_SESSION = array();
unset($_SESSION["user"]);
unset($_SESSION['basededatos']);
unset($_SESSION['usrAlmacenes']);
unset($_SESSION['usrDescMaxPor']);
unset($_SESSION['usrDigitos']);
unset($_SESSION['usrLibroRenta']);
unset($_SESSION['usrLibroGeneral']);
unset($_SESSION['usrCaja']);
unset($_SESSION['usrRegistro']);
unset($_SESSION['usrPrecios']);
unset($_SESSION['usrMultivendedor']);
unset($_SESSION["idtipocambio"]);
$_SESSION["user_cat"]=="invitado";
$oe = $_GET['e'];
header("location: login.php")
?>