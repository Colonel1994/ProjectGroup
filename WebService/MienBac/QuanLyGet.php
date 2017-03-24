<?php
    require_once '../dbConnect.php';
 
    function dispInfo(){
        $db = new Database();
        $db->prepare('SELECT * FROM tltmienbac');
        
        $response["quanLyMienBac"] = array();
        
        foreach($db->getArray() as $row){ 
            $t = array();
            $t["id"] = $row["ID"];
            $t["image"] = base64_encode($row[ "IMAGE"]);
            $t["name"] = $row["NAME"];
            $t["title"] = $row["TITLE"];
            
            
            
            
            array_push($response["quanLyMienBac"], $t);
        }
        header('Content-Type: application/json');
    
        echo json_encode($response);
    }
 
    dispInfo();
?>