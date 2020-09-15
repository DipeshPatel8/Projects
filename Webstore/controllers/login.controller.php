<?php
    require_once("rootDBconnection.php");

    $email = $_POST["email"];
    $pass = $_POST["pass"];

    $query = 'SELECT * FROM customer WHERE email="'.$email.'" AND password="'.$pass.'"';
    $statement = $conn->prepare($query);
    $statement->execute();
    $statement->bind_result($id, $first_name, $last_name, $email, $password, $registered_on);
    $dataCustomerID = [];

    for($i = 0; $statement->fetch(); $i++) {
        $dataCustomerID[$i] = ["id"=>$id, "fname"=>$first_name, "lname"=>$last_name,"email"=>$email, "pass"=>$password, "registeredDate"=>$registered_on];
    }
    
    if(count($dataCustomerID) == 0){
        echo '<script>alert("Password or Email is invalid")</script>';
        echo "<script>";
        echo "window.location = 'http://localhost/Assignment-3-HANN-PATEL/login.php'";
        echo "</script>";
    }
    else
    {
        session_start();
        $customer = $dataCustomerID[0];
        $_SESSION['customerID'] = $customer['id'];
        $_SESSION['loggedIn'] = true;
        echo "<script>";
        echo "window.location = 'http://localhost/Assignment-3-HANN-PATEL/index.php'";
        echo "</script>";
    }
    $statement->close(); 

?>