<?php

include 'dbcon.php';

$uid=$_POST['uid'];

$return_arr = array();

$fetch = mysqli_query($conn,"SELECT DISTINCT(notice.event_id),notice.event_name,notice.event_image,notice.synopsis FROM `notice`,groups where groups.connectedid='$uid' and groups.grpname=notice.grpname or notice.type='public'"); 

while ($row = mysqli_fetch_array($fetch)) {


    $row_array['event_id'] = $row['event_id'];
    $row_array['event_name'] = $row['event_name'];
     $row_array['image'] = $row['event_image'];
       $row_array['synopsis'] = $row['synopsis'];

    array_push($return_arr,$row_array);
}

echo json_encode($return_arr);
?>