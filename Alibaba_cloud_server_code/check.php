<?php
require_once 'DbOperation.php';
   ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);


function generate_access_token_from_refresh_token(){
         $db = new DbOperation(); 

         $token = $db->refreshToken();


echo $token;



    $oauth_config = array('grant_type' => 'refresh_token', 'refresh_token'=> $token, 'client_id' => "65a38ccafe3efcee62e43112bc4a3e72",
        'client_secret' => '2bb49fb495fd518ee3e66112c458b086');
    $response = json_decode(make_curl_request('https://api.codechef.com/oauth/token', $oauth_config), true);

    echo $response['status'];
 
 if(strcmp($response['status'], 'OK')==0)
 {
    echo "OJKKK";

    $result = $response['result']['data'];

    $oauth_details['access_token'] = $result['access_token'];
    $oauth_details['refresh_token'] = $result['refresh_token'];
    $oauth_details['scope'] = $result['scope'];

    $res = $db->updateadmintokens($result['access_token'],$result['refresh_token']);
    if($res==0)
    {
        echo "Successful entry";
    }
    elseif($res==1)
    {
        echo "try again";
    }
 }
 else
 {
    
 }





    return $res;

}

function make_api_request($path){
           $db = new DbOperation(); 

         $token = $db->adminToken();



    $headers[] = 'Authorization: Bearer ' .$token;
    return make_curl_request($path,false,$headers);
}

function make_curl_request($url, $post = FALSE, $headers = array())
{
    $ch = curl_init($url);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, TRUE);

    if ($post) {
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($post));
    }

    $headers[] = 'content-Type: application/json';
    curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
    $response = curl_exec($ch);

    echo $response;
 
    return $response;
}

function eventdetail($code)
{ 
   

	$url = "https://api.codechef.com/contests/".$code;
	
	
	$result = json_decode(make_api_request($url),true);
	 //echo $result;
	  $status = $result['status'];
	  echo $status;

	if(strcmp($status,"error")==0)
	{
		
		
			$rew = generate_access_token_from_refresh_token();
			 echo "<br> refresh";
		
	}
	if(strcmp($status,"OK")==0)
	{
    $a = date_parse($result['result']['data']['content']['startDate']);
    $sdate = mktime($a['hour'],$a['minute'],$a['second'],$a['month'],$a['day'],$a['year']);

     $a = date_parse($result['result']['data']['content']['startDate']);
    $edate = mktime($a['hour'],$a['minute'],$a['second'],$a['month'],$a['day'],$a['year']);
     $db = new DbOperation(); 
     $result = $db->addevent($code,$sdate,$edate);
     if($result==0)
     {
     	echo "event added";
     }
    
	}

}

generate_access_token_from_refresh_token();

?>