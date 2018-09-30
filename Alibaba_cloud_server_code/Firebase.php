<?php 
 
class Firebase {
 
    public function send($registration_ids, $message) {
        $fields= array (
        'to' => $registration_ids,
        'notification' => array (
                "body" => $message['data']['message'],
                "title" => $message['data']['title'],
                "icon" => "https://www.codechef.com/download/homepage%20banner-%20SD'19.jpg"
        )
);
         //echo $registration_ids;
        return $this->sendPushNotification($fields);
    }
    
    /*
    * This function will make the actuall curl request to firebase server
    * and then the message is sent 
    */
    private function sendPushNotification($fields) {
         
        //importing the constant files
        require_once 'Config.php';
        
        
        //firebase server url to send the curl request
        $url = 'https://fcm.googleapis.com/fcm/send';
 
        //building headers for the request
        $headers = array (
        'Authorization: key=' . "AIzaSyAWQDUPVOa10PYMszQ-CbAJ6GahGYgbNfI",
        'Content-Type: application/json'
);

                 $ch = curl_init ();
                
                 curl_setopt ( $ch, CURLOPT_URL, $url );
                 curl_setopt ( $ch, CURLOPT_POST, true );
                  curl_setopt ( $ch, CURLOPT_HTTPHEADER, $headers );
                  curl_setopt ( $ch, CURLOPT_RETURNTRANSFER, true );
                  curl_setopt ( $ch, CURLOPT_POSTFIELDS, json_encode($fields) );
                
               $result = curl_exec ( $ch );
                echo $result;
       
        if ($result === FALSE) {
            die('Curl failed: ' . curl_error($ch));
        }
 
        //Now close the connection
        curl_close($ch);
 
        //and return the result 
        return $result;
    }
}