const form = document.querySelector(".signup form");
const errorText = form.querySelector(".error-txt");

   function submitForm() {
            // Serialize the form data
            var formData = $("#loginForm").serialize();

            // Send data to the JSP page using AJAX
            $.ajax({
                type: "POST",
                url: "login",
                data: formData,
                success: function(response) {
                    // Handle the response from the JSP page
                    $("#error_text").html(response);
                    
                    
                     if(response == "success"){
					 	location.href = "user-chatbox";
					 	errorText.style.background = "lightgreen";
					 	errorText.style.color = "green";
					 
					 }else
					 {
						errorText.style.background = "#f8d7da";
						errorText.style.color = "#721c24";
                        errorText.textContent = response;
						errorText.style.display = "block";
					 }
                },
                error: function(error) {
                    console.error('Error:', error);
                }
            });
       } 

 