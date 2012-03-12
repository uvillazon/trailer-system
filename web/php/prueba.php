<?php
$blah = "http://www.satelsoft.com";

   function hiperlink($CadenaTexto) {
   $CadenaTexto = preg_replace("/(http:\/\/[0-9a-zA-Z\-\._\/]+)/",
     "<a href=\"\1\">\1</a>", $CadenaTexto);
     print $CadenaTexto;
            }

hiperlink($blah);

?>