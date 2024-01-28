
const form = document.querySelector(".typing-area"),
inputField = form.querySelector(".input-field"),
sendBtn = form.querySelector("button"),
chatBox = document.querySelector(".chat-box");
 
  
let out_id = document.getElementById("outgoing_id").value;
let in_id = document.getElementById("incoming_id").value;

 
 form.onsubmit = (e) =>{
	 e.preventDefault();
 }
 

inputField.addEventListener('keydown', function(event) {
  if (event.key === 'Enter' || event.keyCode === 13) {
    event.preventDefault();
	
	submitForm();
	

    console.log('Enter key pressed');
  }
});

 
   function submitForm() {
            
           
         	var form1 = document.getElementById("message_box");
            var formData = new FormData(form1);

			let msg = document.getElementById("message").value;
			

			msg = msg.replaceAll(" ", "__5oO84a9__");
            // Send the form data using Ajax
            var xhr = new XMLHttpRequest();
            var url = "insertChat?outgoing_id=" + encodeURIComponent(out_id) + "&incoming_id=" + encodeURIComponent(in_id)+ "&message=" + encodeURIComponent(msg);

            xhr.open("POST", url, true);

            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    inputField.value = "";
                    scrollToBottom();
                }
            };

            xhr.send();
          
       } 
       
       
  chatBox.onmouseenter = () =>{
	  chatBox.classList.add("active");
  }     
    chatBox.onmouseleave = () =>{
	  chatBox.classList.remove("active");
  }     
       
  function sendGetRequest() {
  // Define the URL of your servlet page
  
 	var servletURL = "get_chat?outgoing_id=" + encodeURIComponent(out_id) + "&incoming_id=" + encodeURIComponent(in_id);
  // Send a GET request using the fetch API
  fetch(servletURL, {
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
         chatBox.innerHTML = data;
         
         if(!chatBox.classList.contains("active")){
	      	 scrollToBottom();
	 
		 }
         
    })
    .catch(error => {
      console.error('Error:', error);
    });
}
const intervalId = setInterval(sendGetRequest, 700);     


function scrollToBottom(){
	chatBox.scrollTop = chatBox.scrollHeight;
}

 