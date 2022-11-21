<?php

include ('dbcon.php');

$json = json_decode(file_get_contents('php://input'),true);


 	$name = $json["name"].".JPG";
 	$fname = $json["fname"]; //within square bracket should be same as Utils.imageName & Utils.image
    $image = $json["image"];
    $lname = $json["lname"];
    $email = $json["email"];
    $password = $json["password"];
    $selectedgender = $json["selectedgender"];
    $dateofbirth = $json["dateofbirth"];
    $mobileno = $json["mobileno"];
    $images = $json["images"];


$response = array();
 
    $decodedImage = base64_decode("$image");
 
    $return = file_put_contents("images/".$name, $decodedImage);
 
    if($return !== false){
        $response['success'] = 1;
    $q=mysqli_query($conn,"INSERT INTO `user`(`fname`, `lname`, `email`, `gender`, `phone`, `dob`, `password`, `user_image`,`status`) VALUES ('$fname','$lname','$email','$selectedgender','$mobileno','$dateofbirth','$password','$name','1')")or die(mysqli_error($conn));

    	$response['success'] = 1;
        $response['fname'] = "Image Uploaded Successfully";

    }else{
        $response['success'] = 0;
        $response['fname'] = "Image Upload Failed";
    }
 
    echo json_encode($response);

?>