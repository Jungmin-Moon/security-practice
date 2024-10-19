function toggleDepositForm() {
	var depositDiv = document.getElementById("depositForm");
	
	if (depositDiv.style.display == "none") {
		depositDiv.style.display = "block";
	} 
	else {
		depositDiv.style.display = "none";
	}
};