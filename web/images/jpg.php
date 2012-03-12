<?php

/**********************************************************************************************************************************
Display images as JPG

call:  <img src="jpg.php?[options]" alt=""/>

[options]
name=[path to the photo to be displayed]
	<img src="jpg.php?name=../images/pepe.jpg" alt="Pepe"/>

size=[max size in pixels to be displayed either width or height]
	<img src="jpg.php?name=../images/pepe.jpg&amp;size=100" alt="Pepe"/>
	
maxHeight & maxWidth = [max value in pixels the photo can take in that specific size, can go together with size]
	<img src="jpg.php?name=../images/pepe.jpg&amp;size=100&amp;maxHeight=150" alt="Pepe"/>
	
fixedHeightAspect & fixedWidthAspect = [used in conjuction of maxHeight & maxWidth, forces the image to the specified size but retains the aspect of the image, filling the rest of the image with white.]
	<img src="jpg.php?name=../images/pepe.jpg&amp;size=100&amp;maxHeight=150&amp;fixedHeightAspect=150&amp;fixedWidthAspect=150" alt="Pepe"/>

fillRed , fillGreen & fillBlue = [used to specify the fill of the image if the size id fixed mantaining aspect radio]
	
fixedHeight & fixedWidth = [This will override any of the previous size constraints and will display the picture with the selected size, this option breaks the aspect ratio.]
	<img src="jpg.php?name=../images/pepe.jpg&amp;fixedHeight=100&amp;fixedWidth=150" alt="Pepe"/>
**********************************************************************************************************************************/
function ImageCreateFromBMP($filename)
{
   if (! $f1 = fopen($filename,"rb")) return FALSE;
   $FILE = unpack("vfile_type/Vfile_size/Vreserved/Vbitmap_offset", fread($f1,14));
   if ($FILE['file_type'] != 19778) return FALSE;
   $BMP = unpack('Vheader_size/Vwidth/Vheight/vplanes/vbits_per_pixel'.
                 '/Vcompression/Vsize_bitmap/Vhoriz_resolution'.
                 '/Vvert_resolution/Vcolors_used/Vcolors_important', fread($f1,40));
   $BMP['colors'] = pow(2,$BMP['bits_per_pixel']);
   if ($BMP['size_bitmap'] == 0) $BMP['size_bitmap'] = $FILE['file_size'] - $FILE['bitmap_offset'];
   $BMP['bytes_per_pixel'] = $BMP['bits_per_pixel']/8;
   $BMP['bytes_per_pixel2'] = ceil($BMP['bytes_per_pixel']);
   $BMP['decal'] = ($BMP['width']*$BMP['bytes_per_pixel']/4);
   $BMP['decal'] -= floor($BMP['width']*$BMP['bytes_per_pixel']/4);
   $BMP['decal'] = 4-(4*$BMP['decal']);
   if ($BMP['decal'] == 4) $BMP['decal'] = 0;
   $PALETTE = array();
   if ($BMP['colors'] < 16777216)
   {
    $PALETTE = unpack('V'.$BMP['colors'], fread($f1,$BMP['colors']*4));
   }
   $IMG = fread($f1,$BMP['size_bitmap']);
   $VIDE = chr(0);
   $res = imagecreatetruecolor($BMP['width'],$BMP['height']);
   $P = 0;
   $Y = $BMP['height']-1;
   while ($Y >= 0)
   {
    $X=0;
    while ($X < $BMP['width'])
    {
     if ($BMP['bits_per_pixel'] == 24)
        $COLOR = unpack("V",substr($IMG,$P,3).$VIDE);
     elseif ($BMP['bits_per_pixel'] == 16)
     { 
        $COLOR = unpack("n",substr($IMG,$P,2));
        $COLOR[1] = $PALETTE[$COLOR[1]+1];
     }
     elseif ($BMP['bits_per_pixel'] == 8)
     { 
        $COLOR = unpack("n",$VIDE.substr($IMG,$P,1));
        $COLOR[1] = $PALETTE[$COLOR[1]+1];
     }
     elseif ($BMP['bits_per_pixel'] == 4)
     {
        $COLOR = unpack("n",$VIDE.substr($IMG,floor($P),1));
        if (($P*2)%2 == 0) $COLOR[1] = ($COLOR[1] >> 4) ; else $COLOR[1] = ($COLOR[1] & 0x0F);
        $COLOR[1] = $PALETTE[$COLOR[1]+1];
     }
     elseif ($BMP['bits_per_pixel'] == 1)
     {
        $COLOR = unpack("n",$VIDE.substr($IMG,floor($P),1));
        if     (($P*8)%8 == 0) $COLOR[1] =  $COLOR[1]        >>7;
        elseif (($P*8)%8 == 1) $COLOR[1] = ($COLOR[1] & 0x40)>>6;
        elseif (($P*8)%8 == 2) $COLOR[1] = ($COLOR[1] & 0x20)>>5;
        elseif (($P*8)%8 == 3) $COLOR[1] = ($COLOR[1] & 0x10)>>4;
        elseif (($P*8)%8 == 4) $COLOR[1] = ($COLOR[1] & 0x8)>>3;
        elseif (($P*8)%8 == 5) $COLOR[1] = ($COLOR[1] & 0x4)>>2;
        elseif (($P*8)%8 == 6) $COLOR[1] = ($COLOR[1] & 0x2)>>1;
        elseif (($P*8)%8 == 7) $COLOR[1] = ($COLOR[1] & 0x1);
        $COLOR[1] = $PALETTE[$COLOR[1]+1];
     }
     else
        return FALSE;
     imagesetpixel($res,$X,$Y,$COLOR[1]);
     $X++;
     $P += $BMP['bytes_per_pixel'];
    }
    $Y--;
    $P+=$BMP['decal'];
   }
   fclose($f1);
 return $res;
}

if ($_GET['name'] == "" || $_GET['name'] == null)
{
	$_GET['name'] = "nophoto.jpg";
}
$size = getimagesize ($_GET['name']);
if ($_GET['size'] == "")
{
	if ($size[0] > $size[1])
		$_GET['size'] = $size[0];
	else
		$_GET['size'] = $size[1];
}
if ($size[0] > $size[1]) //width > height
{
	$width = $_GET['size'];
	if ($_GET['maxWidth'] != "")
	{
		$width = $_GET['maxWidth'] < $width ? $_GET['maxWidth'] : $width;
	}
	$height = intval($size[1] * $width / $size[0]);
	if ($_GET['maxHeight'] != "")
	{
		$height = $_GET['maxHeight'] < $height ? $_GET['maxHeight'] : $height;
	}
}
else
{
	$height=$_GET['size'];
	if ($_GET['maxHeight'] != "")
	{
		$height = $_GET['maxHeight'] < $height ? $_GET['maxHeight'] : $height;
	}
	$width=intval($size[0] * $height / $size[1]);
	if ($_GET['maxWidth'] != "")
	{
		$width = $_GET['maxWidth'] < $width ? $_GET['maxWidth'] : $width;
	}
}
if ($_GET['fixedHeight'] != "")
	$height = $_GET['fixedHeight'];
if ($_GET['fixedWidth'] != "")
	$width = $_GET['fixedWidth'];
	
if ($_GET['fixedHeightAspect'] != "")
{
	$height1 = $_GET['fixedHeightAspect'];
	if ($height1 > $height)
		$diffHeight = ($height1 - $height) / 2;
	elseif ($height1 < $height)
		$diffHeight = ($height - $height1) /2;
	else
		$diffHeight = 0;
}
else
{
	$height1 = $height;
	$diffHeight = 0;
}
if ($_GET['fixedWidthAspect'] != "")
{
	$width1 = $_GET['fixedWidthAspect'];
	if ($width1 > $width)
		$diffWidth = ($width1 - $width) / 2;
	elseif ($width1 < $width)
		$diffWidth = ($width - $width1) /2;
	else
		$diffWidth = 0;
}
else
{
	$width1 = $width;
	$diffWidth = 0;
}

$newim=imagecreatetruecolor ($width1 , $height1);
$red = $_GET['fillRed'] != "" ? $_GET['fillRed'] : 255;
$green = $_GET['fillGreen'] != "" ? $_GET['fillGreen'] : 255;
$blue = $_GET['fillBlue'] != "" ? $_GET['fillBlue'] : 255;
$trans_colour = imagecolorallocate($newim, $red, $green, $blue);
imagefill($newim, 0, 0, $trans_colour);
switch ($size[2])
{
	case IMAGETYPE_JPEG:
		$im = ImageCreateFromJpeg($_GET['name']); 
	break;
	case IMAGETYPE_PNG:
		$im = imagecreatefromPng($_GET['name']); 
	break;
	case IMAGETYPE_GIF:
		$im = imagecreatefromGif($_GET['name']); 
	break;
	case IMAGETYPE_BMP:
		$im = ImageCreateFromBMP($_GET['name']); 
	break;
	default:
		echo "no conozco este tipo: ". $size[2];
		die;
	break;
}
imagealphablending($newim, false);
imagecopyresampled($newim, $im, $diffWidth, $diffHeight, 0, 0, $width, $height, $size[0], $size[1]);
imagesavealpha($newim, true);
Header("Content-Type: image/jpeg");
ImageJpeg($newim);
ImageDestroy($im);
ImageDestroy($newim);
?> 