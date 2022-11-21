<?php

include 'dbcon.php';

$uid=$_POST['uid'];

$return_arr = array();

$fetch = mysqli_query($conn,"SELECT * FROM `booking` WHERE user_id='$uid'"); 

while ($row = mysqli_fetch_array($fetch)) {


    $row_array['event_id'] = $row['event_id'];
    $ev_id=$row['event_id'];
    $row_array['event_name'] = $row['event_name'];
    $row_array['transactionid'] = $row['transactionid'];
     $row_array['bid'] = $row['bid'];
       $row_array['tickettype'] = $row['ticket_type'];
        $row_array['numberoftickets'] = $row['noofticket'];
        $row_array['totalamount'] = $row['totalamount'];

    array_push($return_arr,$row_array);
}

echo json_encode($return_arr);
?>