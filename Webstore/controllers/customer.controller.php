<?php
    require_once("rootDBconnection.php");
    include("../otherFiles/functions.php");

    $fname = $_POST["fname"];
    $lname = $_POST["lname"];
    $email = $_POST["email"];
    $reEmail = $_POST["re-email"];
    $pass = $_POST["pass"];
    $rePass = $_POST["re-pass"];

    if($email != $reEmail || $pass != $rePass) {
        echo '<script>alert("Password or Email do not match")</script>';
        echo "<script>";
        echo "window.location = 'http://localhost/Assignment-3-HANN-PATEL/registration.php'";
        echo "</script>";
    }

    addCustomer($fname, $lname, $email, $pass);
    echo "<script>";
        echo "window.location = 'http://localhost/Assignment-3-HANN-PATEL/index.php'";
        echo "</script>";

?>