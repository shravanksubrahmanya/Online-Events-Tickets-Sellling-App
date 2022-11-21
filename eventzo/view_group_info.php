<?php

include 'dbcon.php';

$groupname=$_POST['groupname'];
$uid=$_POST['uid'];
$return_arr = array();

$fetch = mysqli_query($conn,"SELECT * FROM `groups` WHERE grpname='$groupname' AND uid='$uid'"); 
if ($fetch) {

while ($row = mysqli_fetch_array($fetch)) {


    $row_array['fname'] = $row['fname'];
    $row_array['uid'] = $row['connectedid'];

    $row_array['lname'] = $row['lname'];
    $row_array['image'] = $row['image'];
     $row_array['dob'] = $row['dob'];
       $row_array['gender'] = $row['gender'];
       $row_array['moreinfo'] = $row['moreinfo'];

    array_push($return_arr,$row_array);
}
echo json_encode($return_arr);
}
else
{
	echo "No members found";
}
?>