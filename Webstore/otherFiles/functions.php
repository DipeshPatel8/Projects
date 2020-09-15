<?php
//require_once("/Assignment-3-HANN-PATEL/controllers/rootDBconnection.php");
session_start();

function getCustomerInfo($cust_id){
    global $conn;
    $query = 'SELECT * FROM `customer` WHERE id="'.$cust_id.'";';
    // echo $query;
    
    $statement = $conn->prepare($query);
    $statement->execute();
    $statement->bind_result($id, $first_name, $last_name, $email, $password, $registered_on);
    $dataCustomerID = [];

    for($i = 0; $statement->fetch(); $i++) {
        $dataCustomerID[$i] = ["id"=>$id, "fname"=>$first_name, "lname"=>$last_name,"email"=>$email, "pass"=>$password, "registeredDate"=>$registered_on];
    }

    $statement->close(); 


    return $dataCustomerID[0];
}

function addCustomer($fname, $lname, $email, $pass){
    global $conn;

    $query = 'INSERT INTO `customer` (first_name, last_name, email, `password`, registered_on) VALUES (?,?,?,?,?)';
    $statement = $conn->prepare($query);
    $statement->bind_param("sssss", $fname, $lname, $email, $pass, date("Y-m-d"));
    $statement->execute();
    $statement->close();

    echo '<script type="text/javascript">'; 
    echo 'alert("Please sign in to use your account") </script>';

}

function insertOrderItems($itemList){
    global $conn;

    $query = 'SELECT id FROM `order` ORDER BY date DESC';

    $statement = $conn->prepare($query);
    $statement->execute();
    $statement->bind_result($id);
    $data = [];
    
    for($i = 0; $statement->fetch(); $i++) {
        $data[$i] = ['id'=>$id]; 
        break;
    }

    $statement->close();

    $orderID = $data[0];
    
    for($i = 0; $i < count($itemList); $i++){

        $tmp1 = $itemList[$i];
        $productInfo = selectProductInfo($tmp1["product_id"]);
        $product_id = $tmp1["product_id"];
        $store_id = getStore($product_id);
        $quantity = $tmp1["quantity"];
        $total = (double) $productInfo["price"] * (double) $quantity;

        $query = 'INSERT INTO `order_item`(product_id, order_id, store_id, quantity, total) VALUE (?,?,?,?,?) ';
        $statement = $conn->prepare($query);
        $statement->bind_param("iiiid", $product_id, $orderID, $store_id, $quantity, $total);
        $statement->execute();
        $statement->close();
    }
}

function insertSingleOrder($customerID, $total, $discount){

    global $conn;

    $query = 'INSERT INTO `order` (`customer_id`, `date`, `discount`, `total`) VALUES (?,?,?,?)';

    $statement = $conn->prepare($query);
    $statement->bind_param("sdsi", $discount, $total, date("Y-m-d h:i:sa"), $customerID);
    $statement->execute();
    $statement->close();

}

function selectProductInfo($id){
    global $conn;

    $query = 'SELECT * FROM product WHERE id='.$id;
    
    $statement = $conn->prepare($query);
    $statement->execute();
    $statement->bind_result($id, $name, $description, $price, $size, $colour, $picture, $rating);
    $data = [];

    for($i = 0; $statement->fetch(); $i++) {
        $data[$i] = ['id'=> $id,'name'=> $name, 'description'=> $description, 'price'=>$price, 'size'=>$size, 'colour'=>$colour, 'picture'=>$picture, 'rating'=>$rating ]; 
    }

    $productInfo = $data[0];

    $statement->close();

    return $productInfo;
}

function getStore($productID){

    global $conn;

    $query = 'SELECT store_id FROM product_store WHERE product_id='.$productID.' ORDER BY quantity DESC';

    $statement = $conn->prepare($query);
    $statement->execute();
    $statement->bind_result($store_id);
    $data = [];
    
    for($i = 0; $statement->fetch(); $i++) {
        $data[$i] = $store_id; 
        break;
    }
    
    $statement->close();

    return $data[0];
}

function getCartItems($customerID){

    if($customerID == null){
        return null;
    }
    else{
        global $conn;
        $query = "SELECT * FROM cart WHERE customer_id=".$customerID;

        $statement = $conn->prepare($query);
        $statement->execute();
        $statement->bind_result($product_id, $customer_id, $quantity);
        $dataCartItems = [];
        
        for($i = 0; $statement->fetch(); $i++) {
            $dataCartItems[$i] = ['product_id'=>$product_id,'customer_id'=>$customer_id, 'quantity'=>$quantity]; 
        }
        
        $statement->close();

        return $dataCartItems;
    }
}
    

?>