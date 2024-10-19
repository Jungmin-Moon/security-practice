var depositButton = document.getElementById("deposit");



depositButton.onclick = function() {
	var depositDiv = document.getElementById("deposit");
	
	if (depositDiv.style.display == "none") {
		depositDiv.style.display = "block";
	} 
	else {
		depositDiv.style.display = "none";
	}
};