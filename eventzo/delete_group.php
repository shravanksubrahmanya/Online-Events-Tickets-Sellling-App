<?php

include ('dbcon.php');

$uid=$_POST['uid'];
$grpname=$_POST['groupname'];

$q=mysqli_query($conn,"DELETE FROM `groups` WHERE uid='$uid' and grpname='$grpname'");

if ($q) {

	echo "Group deleted successfully";
}

else
{
	echo "Group is not deleted";
}

?>