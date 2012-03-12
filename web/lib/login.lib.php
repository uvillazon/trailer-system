<?php
class login
{
  function validate($login,$password) //recives the strings with username & password, returns the user id if the user is registered & false if there were not coincidences in the database
  {
    $query = new query;
    $pass = md5($password);
    $row = $query->getRows("login, paswd, idusuario","usuario");
    foreach($row as $key)
    {
      if ($key['login'] == $login)
      	if ($key['paswd'] == $pass)
      		return $key['idusuario'];
    }
    return false;
  }
  
	function loginUser($user_id)
	{
		$query = new query;
		$row = $query->getRow("idusuario, idrol, nombre, apellido1, apellido2, estado","usuario","WHERE idusuario = '$user_id' and estado = 'Habilitado'");
		$_SESSION['logeado'] = 1;
		$_SESSION['nombre'] = $row['nombre']." ".$row['apellido1']." ".$row['apellido2'];
		$_SESSION['idusuario'] = $row['idusuario'];
		$_SESSION['idrol'] = $row['idrol'];
	}

}
?>