<?php

include 'dbcon.php';

$uid=$_POST['uid'];
$eid=$_POST['eid'];

$return_arr = array();

$fetch = mysqli_query($conn,"SELECT * FROM `enews` WHERE user_id='$uid' AND event_id='$eid'"); 

while ($row = mysqli_fetch_array($fetch)) {
    $row_array['message'] = $row['messages'];
   
    array_push($return_arr,$row_array);
}

echo json_encode($return_arr);

?>