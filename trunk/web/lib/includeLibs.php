
<?php
session_start();

if(isset($_SERVER['HTTPS']))
$protocol = 'https';
else
$protocol = 'http';
switch($_SERVER['HTTP_HOST'])
{
    case 'localhost':
        		$_cfg['host'] = 'localhost';
//        $_cfg['host'] = '192.168.0.10';
        $_cfg['user'] = 'root';
        $_cfg['pass'] = '';
        $_cfg['db'] = 'trailer';
        break;
    default:
        ini_set("session.cache_expire","180");
        ini_set("session.gc_maxlifetime","3600");
        //		$_cfg['host'] = 'localhost';
        $_cfg['host'] = '192.168.0.10';
        $_cfg['user'] = 'root';
        $_cfg['pass'] = '';
        $_cfg['db'] = 'trailer';
        break;
}

mysql_connect($_cfg['host'],$_cfg['user'],$_cfg['pass']) or die(mysql_error());
mysql_select_db($_cfg['db']) or die(mysql_error());

require_once('login.lib.php');
require_once('query.lib.php');
require_once('upload.lib.php');
?>