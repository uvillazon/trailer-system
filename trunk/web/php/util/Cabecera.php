<?php
session_name("trailer");
session_start();

echo "<b>Usuario&nbsp;&nbsp;->".$_SESSION['user']."&nbsp;&nbsp;Rol->".$_SESSION['rol'];
echo "&nbsp;&nbsp;&nbsp;<a href='salir.php'>Salir</a>";
?>