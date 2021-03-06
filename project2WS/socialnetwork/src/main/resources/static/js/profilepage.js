window.onload = function () {
    console.log("onload");
    getUser();
}



function getUser() {

    // STEP 1: create the XMLHttpRequest Object
    let xhttp = new XMLHttpRequest();

    // STEP 2: create the callback function for readyState changes
    xhttp.onreadystatechange = function () {
        console.log(xhttp.readyState);

        if (xhttp.readyState == 4 && xhttp.status == 200) {
            console.log("readyState is 4!!! AND status is 200!!! getAllReimbur");

            let user = JSON.parse(xhttp.responseText);

            printUsername(user);
            printProfilePic(user);
            printBio(user);
            getPosts(user);
        }
    }

    //  STEP 3: prepare connection/request details
    xhttp.open('GET', `http://54.226.130.109:9022/currentUser`);
    // STEP 4: send the request, providing any body object the request needs
    xhttp.send();
}//getgetUser



function printUsername(user) {

    console.log("in username " + user)
    let username = document.getElementById("name")
    username.innerHTML = "";
    username.innerHTML = user.username;
}//printUsername

function printProfilePic(user) {

    console.log("in printProfilePic " + user)
    let profilePic = document.getElementById("ProfilePic")
    profilePic.setAttribute("src", "https://buckylebucket.s3.us-east-2.amazonaws.com/ProfilePics/" + user.userId + "/ProfilePic.jpg");
}//printProfilePic

function printBio(user) {

    console.log("in printBio " + user)
    let bio = document.getElementById("bio")
    bio.innerHTML = "";
    bio.innerHTML = user.bio;
}//printBio

function getPosts(user) {

    console.log("in getPosts " + user);
    console.log(user);
    console.log(user.posts);

    let post = document.getElementById("posts");

    let postsAuthor = document.getElementById("postsAuthor");
    let postsContent = document.getElementById("postsContent");
    // let postsImg = document.getElementById("postsImg");


    // post.innerHTML =""
    postsAuthor.innerHTML = "";
    postsContent.innerHTML = "";
    // postsImg.innerHTML = "";


    for (let i = 0; i < user.posts.length; i++) {
        console.log("i = " + i);
        postsAuthor = document.createElement("h5");
        // .posts[i].author
        author =  document.createTextNode(user.username);
        postsAuthor.appendChild(author);
        post.appendChild(postsAuthor);

        postsContent = document.createElement("p");
        content =  document.createTextNode(user.posts[i].content);
        postsContent.appendChild(content);
        post.appendChild(postsContent);

        // postsImg = document.createElement("img");
        // img =  document.createTextNode(user.posts[i].img);
        // postsImg.appendChild(img);
        // post.appendChild(postsImg);

    }//for
}//getPosts



jQuery(document).ready(function(){

    var totalheight = 0;
    jQuery(".et-l--header .et_builder_inner_content .et_pb_section").each(function(){
    totalheight = totalheight + jQuery(this).outerHeight();
    });
    
    totalheight = totalheight + "px";
    
    jQuery("#et-main-area").css("padding-top",totalheight);
    });
    