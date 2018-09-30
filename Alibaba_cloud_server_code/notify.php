<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);


require_once 'DbOperation.php';
require_once 'Firebase.php';
require_once 'Push.php'; 
require_once 'message.php';

   $db = new DbOperation(); 
  $event = $db->allcontest();
  while($row = $event->fetch_assoc())
  {
  	if(($row['sdate']-time())<=14400)
  	{
  		notifyByevent($row['eventcode'],"14400");
  	}
  }

  NotifyALL();




?>