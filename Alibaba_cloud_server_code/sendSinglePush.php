<?php 
//importing required files 
require_once 'DbOperation.php';
require_once 'Firebase.php';
require_once 'Push.php'; 
 
$db = new DbOperation();
 
$response = array(); 
 
if($_SERVER['REQUEST_METHOD']=='GET'){ 
 //hecking the required params 
 if(isset($_GET['title']) and isset($_GET['message']) and isset($_GET['username'])){
 
 //creating a new push
 $push = null; 
 //first check if the push has an image with it
 if(isset($_GET['image'])){
 $push = new Push(
 $_GET['title'],
 $_GET['message'],
 $_GET['image']
 );
 }else{
 //if the push don't have an image give null in place of image
 $push = new Push(
 $_GET['title'],
 $_GET['message'],
 null
 );
 }
 
 //getting the push from push object
 $mPushNotification = $push->getPush(); 
 
 //getting the token from database object 
 $devicetoken = $db->getTokenByUser($_GET['username']);
 
 //creating firebase class object 
 $firebase = new Firebase(); 
 
 //sending push notification and displaying result 
 echo $firebase->send($devicetoken, $mPushNotification);
 }else{
 $response['error']=true;
 $response['message']='Parameters missing';
 }
}else{
 $response['error']=true;
 $response['message']='Invalid request';
}
 
echo json_encode($response);