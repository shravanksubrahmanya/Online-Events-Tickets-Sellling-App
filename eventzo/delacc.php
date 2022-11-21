<?php

include ('dbcon.php');

$uid=$_POST['uid'];

$q=mysqli_query($conn,"DELETE FROM `user` WHERE uid='$uid'");

if ($q) {

	echo "Your account has been deleted";
}

else
{
	echo "Your account has not been deleted";
}

?>