<?php
//liste over mulige hendelser
$possible_url = array("get_frontPage", "get_subCat",
    "get_thread", "insert_thread", "update_thread", "remove_thread",
    "get_post", "insert_post", "update_post", "remove_post",
    "login");
//retur variabel
$returverdi = "En feil oppstod.";

//Database tilkoblings info
$url = "localhost";
$bruker = "u6120gr10";
$passord = "pw.100";
$db = "db6120gr10";

//Etabler kobling til MySQL-databasen
$dblink = mysqli_connect($url, $bruker, $passord, $db);
mysqli_set_charset($dblink, 'utf8');

//Sjekk om mottatt request er en GET med riktige action-parametre
if (isset($_GET["action"]) && in_array($_GET["action"], $possible_url)) {
    switch ($_GET["action"]) {
        case "get_frontPage": //Finn og returner kategorier og subkategorier
            $returverdi = get_frontPage($dblink);
            break;
        case "get_subCat":     //Finn og returner threads under subcat
            if (isset($_GET["subcat"]))
                $returverdi = get_subCat($dblink, $_GET["subcat"]);
            else
                $returverdi = "URL mangler subKategori";
            break;
        case "get_thread": //finn og returner posts under thread
            if (isset($_GET["thread"]))
                $returverdi = get_thread($dblink,$_GET["thread"]);
            else
                $returverdi = "URL mangler thread";
            break;
        case "get_post": //finn og returner spesifikk post
            if (isset($_GET["post"]))
                $returverdi = get_post($dblink, $_GET["post"]);
            else
                $returverdi = "URL mangler postId";
            break;
        case "insert_thread": //setter inn en thread
            if (isset($_GET["thread"],$_GET["post"],$_GET["subcat"],$_GET["user"]))
                $returverdi = insert_thread($dblink,$_GET["thread"],$_GET["post"],$_GET["subcat"],$_GET["user"]);
            else
                $returverdi = "URL mangler thread, post, subcat eller user";
            break;
        case "update_thread": //oppdaterer thread med nytt innhold
            if (isset($_GET["thread"],$_GET["post"]))
                $returverdi = update_thread($dblink,$_GET["thread"],$_GET["post"]);
            else
                $returverdi = "URL mangler thread eller post";
            break;
        case "remove_thread": //fjerner thread
            if (isset($_GET["thread"]))
                $returverdi = remove_thread($dblink,$_GET["thread"]);
            else
                $returverdi = "URL mangler thread";
            break;
        case "insert_post": //setter inn en post
            if (isset($_GET["post"],$_GET["user"],$_GET["thread"]))
                $returverdi = insert_post($dblink,$_GET["post"],$_GET["user"],$_GET["thread"]);
            else
                $returverdi = "URL mangler post, user eller thread";
            break;
        case "update_post": //oppdaterer en post med nytt innhold
            if (isset($_GET["post"],$_GET["postNr"]))
                $returverdi = update_post($dblink,$_GET["post"],$_GET["postNr"]);
            else
                $returverdi = "URL mangler post eller postId";
            break;
        case "remove_post": //fjerner en post
            if(isset($_GET["postNr"]))
                $returverdi = remove_post($dblink,$_GET["postNr"]);
            else
                $returverdi = "URL mangler postNr";
            break;
        case "login": //returnerer bool for om du har logget inn
            if(isset($_GET["user"],$_GET["password"]))
                $returverdi = login($dblink,$_GET["user"],$_GET["password"]);
            else
                $returverdi = "URL mangler user eller password";
            break;
    }
}

mysqli_close($dblink);   //Lukk databasekoblingen

exit(json_encode($returverdi, JSON_UNESCAPED_UNICODE));  //Returner data til klienten som en JSON array

// Funksjon som henter kategorier og subkategorier
function get_frontPage($dblink)
{
    $sql = "SELECT * FROM category";
    $svar = mysqli_query($dblink, $sql);
    $catTab = array();
    $row = 0;
    while ($rad = mysqli_fetch_assoc($svar)) {
        $catTab[$row] = $rad;
        $row++;

        $thread = $rad["name"];
        $sql2 = "SELECT * FROM subcategory WHERE Category_name = '$thread' ORDER BY name";
        $svar2 = mysqli_query($dblink, $sql2);

        while ($rad2 = mysqli_fetch_assoc($svar2)) {
            $catTab[$row] = $rad2;
            $row++;
        }
    }
    return $catTab;
}

function get_subCat($dblink, $subcat)
{
    $sql = "SELECT * FROM thread WHERE Subcategory_name = '$subcat' ORDER BY last_post";
    $svar = mysqli_query($dblink,$sql);
    $tab = array();
    $row = 0;
    while ($rad = mysqli_fetch_assoc($svar)){
        $tab[$row] = $rad;
        $row++;
    }
    return $tab;
}

function get_thread($dblink, $thread){
    $sql = "SELECT * FROM post WHERE Thread_name = '$thread' ORDER BY date";
    $svar = mysqli_query($dblink,$sql);
    $tab = array();
    $row = 0;
    while ($rad = mysqli_fetch_assoc($svar)){
        $tab[$row] = $rad;
        $row++;
    }
    return $tab;
}

function get_post($dblink, $post){
    $sql = "SELECT * FROM post WHERE postNr = '$post'";
    $svar = mysqli_query($dblink,$sql);
    return mysqli_fetch_assoc($svar);
}

function insert_thread($dblink,$threadName, $post, $subcat,$user){
    $sql = "CALL addThread('$threadName','$post','$subcat','$user')";
    return mysqli_query($dblink,$sql);
}

function update_thread($dblink, $threadName, $post){
    $sql = "CALL editThread('$threadName','$post')";
    return mysqli_query($dblink,$sql);
}

function remove_thread($dblink, $threadName){
    $sql = "CALL deleteThread('$threadName')";
    return mysqli_query($dblink,$sql);
}

function insert_post($dblink, $post, $user, $thread){
    $sql = "CALL addPost('$post','$user','$thread')";
    return mysqli_query($dblink,$sql);
}

function update_post($dblink, $post, $postNr){
    $sql = "CALL editPost('$post','$postNr')";
    return mysqli_query($dblink,$sql);
}

function remove_post($dblink, $postNr){
    $sql = "CALL deletePost('$postNr')";
    return mysqli_query($dblink,$sql);
}

function login($dblink, $user, $password){
    $sql = "SELECT getPassword('$user')";
    $svar = mysqli_query($dblink,$sql);
    $rad = mysqli_fetch_array($svar,MYSQLI_NUM);
    $pass = $rad[0];
    return ($pass == $password);
}

?>