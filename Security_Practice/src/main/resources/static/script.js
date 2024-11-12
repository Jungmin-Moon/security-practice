var depositDiv = document.getElementById("depositForm");
var withdrawDiv = document.getElementById("withdrawForm");
var transferDiv = document.getElementById("transferForm");

var depositAmount = document.getElementById("depositAmount");
var withdrawAmount = document.getElementById("withdrawAmount");
var transferAmount = document.getElementById("transferAmount");
var transferTarget = document.getElementById("transferTarget");

function toggleDepositForm() {
	
	if (depositDiv.style.display == "none" && withdrawDiv.style.display == "none" && transferDiv.style.display == "none") {
		depositDiv.style.display = "block";
		depositAmount.value = "";
	} 
	else {
		depositDiv.style.display = "none";
	}
}


function toggleWithdrawForm() {
	
	if (withdrawDiv.style.display == "none" && depositDiv.style.display == "none" && transferDiv.style.display == "none") {
		withdrawDiv.style.display = "block";
		withdrawAmount.value = "";
	} 
	else {
		withdrawDiv.style.display = "none";
	}
}


function toggleTransferForm() {
	
	if (transferDiv.style.display == "none" && withdrawDiv.style.display == "none" && depositDiv.style.display == "none") {
		transferDiv.style.display = "block";
		transferAmount.value = "";
		transferTarget.value = "";
	} 
	else {
		transferDiv.style.display = "none";
	}
}


