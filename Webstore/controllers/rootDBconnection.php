<?php
    $serverName = 'mysql';
    $username = 'root';
    $password = 'rootpassword';
    $dbName = 'Wacky_Walk_Database';

    $conn = new mysqli($serverName, $username, $password, $dbName);

    if ($conn->connect_errno) {
        die("Connection failed: " . $conn->connect_error);
    }
?>