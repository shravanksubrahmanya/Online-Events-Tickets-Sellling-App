<?php

include 'dbcon.php';

$uid=18;//$_POST['uid'];
$eid=29;//$_POST['eid'];

$return_arr = array();

$fetch = mysqli_query($conn,"SELECT * FROM `booking` WHERE user_id='$uid' AND event_id='$eid'"); 

while ($row = mysqli_fetch_array($fetch)) {


    $row_array['event_id'] = $row['event_id'];
    $ev_id=$row['event_id'];
    $row_array['event_name'] = $row['event_name'];
    $row_array['transactionid'] = $row['transactionid'];
     $row_array['bid'] = $row['bid'];
       $row_array['tickettype'] = $row['ticket_type'];
        $row_array['numberoftickets'] = $row['noofticket'];
        $row_array['totalamount'] = $row['totalamount'];
        $row_array['fname'] = $row['fname'];
        $row_array['lname'] = $row['lname'];
         $row_array['phone'] = $row['phone'];
         $row_array['email'] = $row['email'];

    array_push($return_arr,$row_array);
}

echo json_encode($return_arr);
?>