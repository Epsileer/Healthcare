<?php 
 require_once 'DbOperation.php';
 require_once 'check.php';

 $response = array(); 
 
 if($_SERVER['REQUEST_METHOD']=='GET'){
 
 $username = $_GET['username'];
 $eventcode = $_GET['eventcode'];

 
 $db = new DbOperation(); 
 
 $result = $db->addnotification($username,$eventcode);
 
 if($result == 0){
 $response['error'] = false; 
 $response['message'] = 'Event notify successfully';
   eventdetail($eventcode);


 }elseif($result == 2){
 $response['error'] = true; 
 $response['message'] = 'not register user registered';
 }else{
 $response['error'] = true;
 $response['message']='already registered';
 }
 }else{
 $response['error']=true;
 $response['message']='Invalid Request...';
 }
 
 echo json_encode($response);