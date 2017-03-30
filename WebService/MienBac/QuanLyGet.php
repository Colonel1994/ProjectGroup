<?php
    require_once '../dbConnect.php';
 
    function dispInfo(){
        $db = new Database();
        $db->prepare('SELECT * FROM tblmienbac');
        
        $response["tblmienbac"] = array();
        
        foreach($db->getArray() as $row){ 
            $t = array();
            $t["id"] = $row["ID"];
            $t["image"] = $row[ "IMAGE"];
            $t["name"] = $row["NAME"];
            $t["title"] = $row["TITLE"];
      
            array_push($response["tblmienbac"], $t);
        }
        header('Content-Type: application/json');
    
        echo json_encode($response);
    }
 
    dispInfo();
?>