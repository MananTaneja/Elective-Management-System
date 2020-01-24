<?php 

    require_once "connect.php";

    if(!con) {
        echo "Database connection Failed!";
    }
    else {
        echo "Database connected";
    }

?>