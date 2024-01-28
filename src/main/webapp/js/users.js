
const searchBar =  document.querySelector(".users .search input"),
searchBtn = document.querySelector(".users .search button");
const usersList = document.getElementById("user_list");
					
searchBtn.onclick = ()=>{

  searchBar.classList.toggle("active");
  searchBar.focus();
  searchBtn.classList.toggle("active");
  searchBar.value = "";
}

searchBar.onkeyup = ()=>{
	let searchTerm = searchBar.value; 
	if(searchTerm != ""){
		searchBar.classList.add("active");
	}else{
		searchBar.classList.remove("active");
	}
	
	let xhr = new XMLHttpRequest();
	xhr.open("POST","search",true);
	xhr.onload = ()=> {
		if(xhr.readyState === XMLHttpRequest.DONE){
			if(xhr.status === 200){
				let data = xhr.response;
				usersList.innerHTML = data;
			}
		}
	}
	xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xhr.send("searchTerm=" + searchTerm);
}
 
function sendGetRequest() {
  // Define the URL of your servlet page
  const servletUrl = 'users';

  // Send a GET request using the fetch API
  fetch(servletUrl, {
    method: 'GET',
    // You can set headers and other options here if needed
  })
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.text(); // or response.json() if the servlet returns JSON
    })
    .then(data => {
      // Process the response data here
      //console.log(data); 
      if(!searchBar.classList.contains("active")){
		  usersList.innerHTML = data;
	  }
      
      //usersList.innerHTML = data;
    })
    .catch(error => {
      console.error('Error:', error);
    });
}

const intervalId = setInterval(sendGetRequest, 500);

