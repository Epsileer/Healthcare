<?php





require_once 'DbOperation.php';

require_once 'Firebase.php';
require_once 'Push.php'; 


function NotifyALL()
{

 $db = new DbOperation(); 
  $event = $db->getalleventusers();
  while ($row = $event->fetch_assoc()) {
  	# code...
  	//echo $row["eventcode"];

  	$devicetoken =  $db->getTokenByUser( $row['username']);

$push = new Push(
 $row['eventcode'],
 "BE PREAPARE FOR IT",
 null
 );

$notification = $push->getPush();
$firebase = new Firebase(); 
 
 //sending push notification and displaying result 
 echo $firebase->send($devicetoken, $notification);


  }
  
}

function notifyByevent($code,$time)
{
	$db = new DbOperation(); 
  $event = $db->geteventusers($code);
  while ($row = $event->fetch_assoc()) {
  	# code...
  	//echo $row["eventcode"];

  	$devicetoken =  $db->getTokenByUser( $row['username']);

$push = new Push(
 $code,
 "IT WILL Start in ".$time,
 null
 );

$notification = $push->getPush();
$firebase = new Firebase(); 
 
 //sending push notification and displaying result 
 echo $firebase->send($devicetoken, $notification);

}
}

?>