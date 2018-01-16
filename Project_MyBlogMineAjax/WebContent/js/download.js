window.onload = function(){
	var buttons = document.getElementsByTagName("input");
	for(var i=0; i<buttons.length; i++){
		var button = buttons.item(i);
		button.onclick = download;
	}
}

function download(){
	var fileName = this.getAttribute("name");
	window.location.href = "download.do?file=" + fileName;
}