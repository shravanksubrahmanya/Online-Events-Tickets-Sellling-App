<?php

include ('dbcon.php');

$username=$_POST['username'];
$pass=$_POST['password'];



$q=mysqli_query($conn,"SELECT * FROM `user` WHERE `email`='$username' and `password`='$pass' ");


$res=mysqli_fetch_array($q);

if ($res) {

	echo $res['uid'];
}

else
{
	echo "login fail";
}

?>