<?php

include 'dbcon.php';

$uid=$_POST['uid'];

$return_arr = array();

$fetch = mysqli_query($conn,"SELECT DISTINCT(grpname) FROM `groups` WHERE uid='$uid'"); 

while ($row = mysqli_fetch_array($fetch)) {


    $row_array['grpname'] = $row['grpname'];

    array_push($return_arr,$row_array);
}

echo json_encode($return_arr);
?>
