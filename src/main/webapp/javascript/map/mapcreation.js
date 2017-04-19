var canvas = document.getElementById("map");
canvas.width = window.innerWidth;
canvas.height = window.innerHeight;

var ctx = canvas.getContext("2d");

// Set the canvas offsets
var canvasOffset = $("#map").offset();
var offsetX = canvasOffset.left;
var offsetY = canvasOffset.top;

var components = [];

var selected = null;
var mouseDown = false;
var clickDifX, clickDifY;

/*************
 * Functions *
 *************/
function createComponent(x, y) {
    var obj = document.getElementsByName("drawing-objects");
    if (obj[0].checked) {
        //alert("Rectangle selected.");
        selected = new rectangle(generateNewId(), x, y, 50, 50);
        components.push(selected);
    }
    else if (obj[1].checked) {
        //alert("Table selected.");
        selected = new table(generateNewId(), x, y, 50, 50, 0);
        components.push(selected);
    }
    else if (obj[2].checked) {
        //alert("Circle selected.");
        selected = new circle(generateNewId(), x, y, 25);
        components.push(selected);
    }
    else if (obj[3].checked) {
        //alert("Line selected.");
        selected = new line(generateNewId(), x, y);
        components.push(selected);
    }
    else if (obj[4].checked) {
        //alert("Text selected.");
    }
}

function generateNewId() {
    var countup = 0;
    var id = "obj" + countup; 
    while (checkIfIdExists(id)) {
        id = "obj" + countup++;
        console.log("New ID: " + id);
    }
    return id;
}

function checkIfIdExists(id) {
    for (var i = 0; i < components.length; i++) {
        if (String(id) === String(components[i].id)) {
            return true;
        }
    }
    return false;
}

function getSelected() {
    for (var i = 0; i < components.length; i++) {
        if (components[i].isPointInside(mouseX, mouseY)) {
            selected = components[i];
            selected.strokeStyle = '#ff0000';
            redrawAll();
            mapCreationOptions();
            break;
        }
        else {
            selected = null;
        }
    }
}

// This function redraws all the components and rectangles
function redrawAll() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    for (var i = 0; i < components.length; i++)
    {
        if (selected == null) {
            components[i].strokeStyle = "#000000";
        }
        components[i].draw(); 
    }
}

/*****************
 * EventHandlers *
 *****************/
function handleMouseDown(e) {
    mouseX = parseInt(e.clientX - offsetX);
    mouseY = parseInt(e.clientY - offsetY);
	
	mouseDown = true;
		
	if (selected != null) {
		// Measure the click difference between the mouseclick and the selected XY
		// This has to happen in the mousedown as this is the part where the dragging/resizing will start
		clickDifX = mouseX - selected.x;
		clickDifY = mouseY - selected.y;
		if (selected.type == "line") {
			selected.checkCloseEnough(mouseX, mouseY);
		}
		else {
			selected.checkCloseEnough(mouseX, mouseY);
		}

	}
}


function handleMouseMove(e) {
    mouseX = parseInt(e.clientX - offsetX);
    mouseY = parseInt(e.clientY - offsetY);
		
	document.getElementById("coordinates").innerHTML = "<b>X:</b> " + mouseX + ", <b>Y:</b> " + mouseY;
	
	if (resize.checked && mouseDown) {	
		if (selected != null) {
			var newX = mouseX - clickDifX;
			var newY = mouseY - clickDifY;
			
			if (selected.isResizing()) {
				selected.resizeTo(mouseX, mouseY);
				mapCreationOptions();
			}
			else {
				selected.moveTo(newX, newY);
				mapCreationOptions();
			}
			redrawAll();
		}
	}
}

function handleMouseUp(e) {
	mouseX = parseInt(e.clientX - offsetX);
	mouseY = parseInt(e.clientY - offsetY);
	
	mouseDown = false;
	if (selected != null) {
		clickDifX = mouseX - selected.width;
		clickDifY = mouseY - selected.height;
		selected.stopResize();
		selected.strokeStyle = "#000000";
	}
}

function handleMouseClick(e) {
	mouseX = parseInt(e.clientX - offsetX);
	mouseY = parseInt(e.clientY - offsetY);	
		
	if (draw.checked) {
		selected = null;
		createComponent(mouseX, mouseY);
	}
	else if (resize.checked) {	
		getSelected();
		redrawAll();
		mapCreationOptions();
	}
	else if (modify.checked) {
		getSelected();
		redrawAll();
		mapCreationOptions();
	}
}

function onKeyup(e) {
	if(e.keyCode == 46) {
		if (selected != null) {
			var confirmation = prompt("You are about to delete " + selected.id + ". Are you sure? (YES / NO)");
			if (confirmation.toUpperCase() == "YES") {
				var remove;
				for (var i = 0; i < components.length; i++)
				{
					if (components[i] == selected) {
						remove = i;
						break;
					}
				}
				components.splice(remove, 1);
				console.log("REMOVED " + remove + ", LEFT: " + components.length);
				selected = null;
				redrawAll();
			}
		}
	}
}

//
$("#map").mousedown(handleMouseDown);
$("#map").mousemove(handleMouseMove);
$("#map").mouseup(handleMouseUp);
$("#map").click(handleMouseClick);
$("html").keyup(onKeyup);

// Draw dummy data
//components.push(new rectangle("rectangle1", 150, 50, 75, 75));
//components.push(new rectangle("rectangle2", 10, 200, 75, 75));
//components.push(new circle("circle1", 300, 300, 25));
//components.push(new line("line1", 200, 200));
//components.push(new line("line2", 400, 200, 450, 300));