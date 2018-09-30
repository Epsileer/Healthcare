<?php
 
class DbOperation
{
    //Database connection link
    private $con;
 
    //Class constructor
    function __construct()
    {
        //Getting the DbConnect.php file
        require_once dirname(__FILE__) . '/DBConnect.php';
 
        //Creating a DbConnect object to connect to the database
        $db = new DbConnect();
 
        //Initializing our connection link of this class
        //by calling the method connect of DbConnect class
        $this->con = $db->connect();
    }
 
    //storing token in database 
    public function registerDevice($username,$device){
        if(!$this->isUserexist($username)){
            $stmt = $this->con->prepare("INSERT INTO user (username, device) VALUES (?,?) ");
            $stmt->bind_param("ss",$username,$device);
            
            if($stmt->execute())
                return 0; //return 0 means success
            return 1; //return 1 means failure
        }else{
            $stmt = $this->con->prepare("UPDATE  user SET device = ? WHERE username = ?");
            $stmt->bind_param("ss",$device,$username);
            
            if($stmt->execute())
                return 0; //return 0 means success
            return 1; //retur

        }
    }
 
    //the method will check if email already exist 
    private function isUserexist($username){
        $stmt = $this->con->prepare("SELECT username FROM user WHERE username = ?");
        $stmt->bind_param("s",$username);
        $stmt->execute();
        $stmt->store_result();
        $num_rows = $stmt->num_rows;
        $stmt->close();
        return $num_rows > 0;
    }
 
    //getting all tokens to send push to all devices
    public function getAllTokens(){
        $stmt = $this->con->prepare("SELECT device FROM user");
        $stmt->execute(); 
        $result = $stmt->get_result();
        $tokens = array(); 
        while($token = $result->fetch_assoc()){
            array_push($tokens, $token['device']);
        }
        return $tokens; 
    }
 
    //getting a specified token to send push to selected device
    public function getTokenByUser($username){
        $stmt = $this->con->prepare("SELECT device FROM user WHERE username = ?");
        $stmt->bind_param("s",$username);
        $stmt->execute(); 
        $result = $stmt->get_result()->fetch_assoc();
       
        return $result['device'];        
    }
 
    //getting all the registered devices from database 
    public function getAllUser(){
        $stmt = $this->con->prepare("SELECT * FROM user");
        $stmt->execute();
        $result = $stmt->get_result();
        return $result; 
    }

    public function addnotification($username,$code)
    {
        $stmt = $this->con->prepare("INSERT INTO event (username,eventcode) VALUES (?,?)");
        $stmt->bind_param("ss",$username,$code);
        if($stmt->execute())
                return 0; //return 0 means success
            return 1; 

    }

    public function getallevent()
    {$stmt = $this->con->prepare("SELECT DISTINCT eventcode FROM event");
        $stmt->execute();
        $result = $stmt->get_result();
        return $result; 

    }

    public function geteventusers($code)
    {$stmt = $this->con->prepare("SELECT username from event WHERE eventcode = ?");
    $stmt->bind_param("s",$code);
        $stmt->execute();
        $result = $stmt->get_result();
        return $result; 
      
    }

    public function storeadmintokens($access,$refresh)
    {$stmt = $this->con->prepare("INSERT INTO Admin (username,accesstoken,refreshtoken) VALUES (?,?,?)");
    $admin = "admin";
        $stmt->bind_param("sss", $admin,$access,$refresh);
        if($stmt->execute())
                return 0; //return 0 means success
            return 1; 

    }

    public function updateadmintokens($access,$refresh)
    {$stmt = $this->con->prepare("UPDATE Admin Set accesstoken = ? , refreshtoken = ? WHERE username = 'admin'");
        $stmt->bind_param("ss",$access,$refresh);
        if($stmt->execute())
                return 0; //return 0 means success
            return 1; 

    }
    public function adminToken()
    {
        $stmt = $this->con->prepare("SELECT * FROM Admin WHERE username = 'admin'");
        $stmt->execute();
        $result = $stmt->get_result()->fetch_assoc();
        return $result['accesstoken']; 
    }
    public function refreshToken()
    {
        $stmt = $this->con->prepare("SELECT refreshtoken FROM Admin WHERE username = 'admin'");
        $stmt->execute();
        $result = $stmt->get_result()->fetch_assoc();
        return $result['refreshtoken']; 
    }

    public function addevent($code,$sdate,$enddate)
    {  if(!$this->isEvent($code))
        {
            $stmt = $this->con->prepare("INSERT INTO contest (eventcode,sdate,edate) VALUES (?,?,?)");
     $admin = "admin";
        $stmt->bind_param("sss", $code,$sdate,$enddate);
        if($stmt->execute())
                return 0; //return 0 means success
            return 1; 

        }
        else
        {
            echo "Already";
            return 2;
        }
    }

    public function eventstart($code)
    {
         $stmt = $this->con->prepare("SELECT sdate FROM contest WHERE eventcode = ?");
          $stmt->bind_param("s", $code);
        $stmt->execute();
        $result = $stmt->get_result()->fetch_assoc();
        return $result['sdate']; 
    }
     public function eventenddate($code)
    {
         $stmt = $this->con->prepare("SELECT edate FROM contest WHERE eventcode = ?");
          $stmt->bind_param("s", $code);
        $stmt->execute();
        $result = $stmt->get_result()->fetch_assoc();
        return $result['edate']; 
    }

    public function isEvent($code)
    {
         $stmt = $this->con->prepare("SELECT eventcode FROM contest WHERE eventcode = ?");
        $stmt->bind_param("s",$code);
        $stmt->execute();
        $stmt->store_result();
        $num_rows = $stmt->num_rows;
        $stmt->close();
        return $num_rows > 0;
    }

    public function allcontest()
    {   $stmt = $this->con->prepare("SELECT * FROM contest");
        $stmt->execute();
        $result = $stmt->get_result();
        return $result; 

    }

    public function getalleventusers()
    {
         $stmt = $this->con->prepare("SELECT * FROM event");
        $stmt->execute();
        $result = $stmt->get_result();
        return $result; 
    }
}

?>