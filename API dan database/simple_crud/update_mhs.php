<?php
 

/*
 * Berikut adalah kelas untuk meng-update data mahasiswa
 */
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['id_mhs']) && isset($_POST['nama']) && isset($_POST['nim'])) {
 
    $id_mhs = $_POST['id_mhs'];
    $nama = $_POST['nama'];
    $nim = $_POST['nim'];
 
    // include db connect class
    require_once '../simple_crud/db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
 
    $result = mysql_query("UPDATE mahasiswa SET nama = '$nama', nim = '$nim' WHERE id_mhs = $id_mhs");
 
    // check if row inserted or not
    if ($result) {
        // successfully updated
        $response["success"] = 1;
        $response["message"] = "successfully updated.";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
 
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
?>