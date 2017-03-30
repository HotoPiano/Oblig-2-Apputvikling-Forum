<?php
//liste over mulige hendelser
$possible_url = array("get_frontPage", "get_subCat",
    "get_thread", "insert_thread", "update_thread", "remove_thread",
    "get_post", "insert_post", "update_post", "remove_post");
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

        $thread = $row["name"];
        $sql2 = "SELECT * FROM subcategory WHERE Category_name = $thread ORDER BY name";
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
    $sql = "SELECT * FROM thread WHERE Subcategory_name = $subcat ORDER BY last_post";
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
    $sql = "SELECT * FROM post WHERE Thread_name = $thread ORDER BY date";
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
    $sql = "SELECT * FROM post WHERE postNr = $post";
    $svar = mysqli_query($dblink,$sql);
    return mysqli_fetch_assoc($svar);
}

?>