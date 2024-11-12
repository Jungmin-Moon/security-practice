var depositDiv = document.getElementById("depositForm");
var withdrawDiv = document.getElementById("withdrawForm");
var transferDiv = document.getElementById("transferForm");

function toggleDepositForm() {
	
	if (depositDiv.style.display == "none" && withdrawDiv.style.display == "none" && transferDiv.style.display == "none") {
		depositDiv.style.display = "block";
	} 
	else {
		depositDiv.style.display = "none";
	}
}


function toggleWithdrawForm() {
	
	if (withdrawDiv.style.display == "none" && depositDiv.style.display == "none" && transferDiv.style.display == "none") {
		withdrawDiv.style.display = "block";
	} 
	else {
		withdrawDiv.style.display = "none";
	}
}


function toggleTransferForm() {
	
	if (transferDiv.style.display == "none" && withdrawDiv.style.display == "none" && depositDiv.style.display == "none") {
		transferDiv.style.display = "block";
	} 
	else {
		transferDiv.style.display = "none";
	}
}


