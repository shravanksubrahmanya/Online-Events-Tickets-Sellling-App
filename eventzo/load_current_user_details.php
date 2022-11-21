<?php

include 'dbcon.php';

$uid=$_POST['uid'];
$return_arr = array();

$fetch = mysqli_query($conn,"SELECT * FROM `user` WHERE uid='$uid'"); 
if ($fetch) {

while ($row = mysqli_fetch_array($fetch)) {


    $row_array['fname'] = $row['fname'];
    $row_array['lname'] = $row['lname'];
    $row_array['image'] = $row['user_image'];

    array_push($return_arr,$row_array);
}
echo json_encode($return_arr);
}
else
{
	echo "No search results found";
}
?>