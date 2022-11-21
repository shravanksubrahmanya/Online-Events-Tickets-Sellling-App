<?php

include 'dbcon.php';

$event_id=$_POST['event_id'];

$return_arr = array();

$fetch = mysqli_query($conn,"SELECT user.phone FROM `event`,user where event.user_id=user.uid and 
	event.event_id='$event_id' "); 

while ($row = mysqli_fetch_array($fetch)) {


    $row_array['number'] = $row['phone'];
    
    array_push($return_arr,$row_array);
}

echo json_encode($return_arr);
?>