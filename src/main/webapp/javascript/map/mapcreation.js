/* 
 * Reserved variables:
 * - canvas
 * - ctx
 * - canvasOffset
 * - offsetX
 * - offsetY
 * - locations
 * - selectedLoc
 * - mouseDown
 * - clickDifX
 * - clickDifY
 * - venue (is the location variabele, location itself conflicts)
 * - floor
 * - room
 * - circle
 * - rectangle
 */

// Canvas + Context
var canvas = document.getElementById("map");
//canvas.width = window.innerWidth;
//canvas.height = window.innerHeight;
var ctx = canvas.getContext("2d");
var canvasOffset = $("#map").offset();
var offsetX = canvasOffset.left;
var offsetY = canvasOffset.top;

// Locations
var locations = [];

// Selection
var selectedLoc = null;
var mouseDown = false;
var clickDifX, clickDifY;

/*************
 * Functions *
 *************/
function newLocation() {
    // Check the filled in values
    var name = document.getElementById("locationform-name").value;
    var address = document.getElementById("locationform-address").value;
    var postal = document.getElementById("locationform-postal").value;
    // If not everything is filled in, don't continue
    if (!name || !address || !postal) {
        console.log("[LOCATION] Not all required info has been filled in.");
        return;
    }
    
    // Create a new location
    selectedLoc = new Venue(generateRandomId(), name, postal, address);
    locations.push(selectedLoc);
    saveLocation(selectedLoc);
    
    loadLocationList();
    createLocationForm();
}

function loadLocationList() {    
    document.getElementById("option-location").options.length = 0;
    var menu = document.getElementById("option-location");
    for (var i = 0; i < locations.length; i++) {
        var option = document.createElement("option");
        option.value = locations[i].id;
        option.text = locations[i].name;
        menu.add(option);
    }
    console.log("All locations have been added to the list.");
}

function findLocation(id) { 
    for (var i = 0; i < locations.length; i++) {
        if (String(locations[i].id) == String(id)) {
            selectedLoc = locations[i];
            return true;
        }
    }
    return false;
}

function newFloor() {
    // Check the filled in values
    var floorname = document.getElementById("floorform-floorname").value;
    var level = document.getElementById("floorform-level").value;
    console.log("Floor: " + floorname + ", level: " + level);
    // If not everything is filled in, don't continue
    if (!floorname || !level) {
        console.log("[FLOOR] Not all required info has been filled in.");
        return;
    }
    
    // Create new floor
    var selectedFloor = new Floor(generateRandomId(), floorname, level);
    selectedLoc.addFloor(selectedFloor);
    saveFloor(selectedLoc, selectedFloor);
    console.log("Floor: " + selectedFloor.name + ", level: " + selectedFloor.level);
    
    // Add the new floor to the GUI
    var menu = document.getElementById("option-floor");
    var option = document.createElement("option");
    option.value = selectedFloor.id;
    option.text = selectedFloor.name;
    menu.add(option);
    console.log("New floor has been added to " + selectedLoc.name + "!")
    createFloorForm();
    onFloorChange();
}

function clearFloorList() { 
    var menu = document.getElementById("option-floor").options.length = 0;
}

function loadFloorList() { 
    document.getElementById("option-floor").options.length = 0;
    var menu = document.getElementById("option-floor");
    for (var i = 0; i < selectedLoc.floors.length; i++) {
        var option = document.createElement("option");
        option.value = selectedLoc.floors[i].id;
        option.text = selectedLoc.floors[i].name;
        menu.add(option);
    }
    console.log("All floors have been added to the list related to the selected location.");
}

function createComponent(x, y) {   
    if (selectedLoc == null || selectedLoc.selectedFloor == null) {
        alert("No location/floor selected.");
        return;
    }
    
    var obj = document.getElementsByName("drawing-objects");
    redrawAll();
    var element;
    if (obj[0].checked) {
        //alert("Rectangle selected.");
        element = new Rectangle(generateRandomId(), x, y, 50, 50);
    } else if (obj[1].checked) {
        //alert("Table selected.");
        element = new Table(generateRandomId(), x, y, 50, 50, 0);
    } else if (obj[2].checked) {
        //alert("Room selected.");
        element = new Room(generateRandomId(), x, y, 100, 100, 0, "new_room");
    } else if (obj[3].checked) {
        //alert("Circle selected.");
        element = new Circle(generateRandomId(), x, y, 25);
    } else if (obj[4].checked) {
        //alert("Line selected.");
        element = new Wall(generateRandomId(), x, y);
    }
    selectedLoc.selectedFloor.addElement(element);
    element.strokeStyle = '#ff0000';
    saveElement(selectedLoc, selectedLoc.selectedFloor, element);
}

function getSelected() {
    if (!selectedLoc || !selectedLoc.selectedFloor) {
        alert("No location/floor selected.");
        return;
    }
    
    var elements = selectedLoc.selectedFloor.elements;
    for (var i = 0; i < elements.length; i++) {
        if (elements[i].isPointInside(mouseX, mouseY)) {
            selectedLoc.selectedFloor.selectElement(elements[i]);
            selectedLoc.selectedFloor.selected.strokeStyle = "#ff0000";
            redrawAll();
            mapCreationOptions();
            break;
        } else {
            selectedLoc.selectedFloor.selectElement(null);
        }
    }
}

// This function redraws all the components and rectangles
function redrawAll() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    if (!selectedLoc || !selectedLoc.selectedFloor) { 
        return;
    }
    
    console.log("[redrawAll]");
    /*var elements = selectedLoc.selectedFloor.elements;
    for (var i = 0; i < elements.length; i++) {
        if (selected == null) {
            elements[i].strokeStyle = "#000000";
        }
        elements[i].draw();
    }*/
    selectedLoc.selectedFloor.drawElements();
}

/*********************
 * Map-EventHandlers *
 *********************/
function handleMouseDown(e) {
    if (!selectedLoc || !selectedLoc.selectedFloor) {
        alert("No location/floor selected.");
        return;
    }
    
    mouseX = parseInt(e.clientX - offsetX);
    mouseY = parseInt(e.clientY - offsetY);

    mouseDown = true;

    if (selectedLoc.selectedFloor.selected != null) {
        // Measure the click difference between the mouseclick and the selected XY
        // This has to happen in the mousedown as this is the part where the dragging/resizing will start
        clickDifX = mouseX - selectedLoc.selectedFloor.selected.x;
        clickDifY = mouseY - selectedLoc.selectedFloor.selected.y;
        if (selectedLoc.selectedFloor.selected.type == "line") {
            selectedLoc.selectedFloor.selected.checkCloseEnough(mouseX, mouseY);
        } else {
            selectedLoc.selectedFloor.selected.checkCloseEnough(mouseX, mouseY);
        }
    }
}

function handleMouseMove(e) {
    mouseX = parseInt(e.clientX - offsetX);
    mouseY = parseInt(e.clientY - offsetY);

    document.getElementById("coordinates").innerHTML = "<b>X:</b> " + mouseX + ", <b>Y:</b> " + mouseY;

    /*if (resize.checked && mouseDown) {
        if (selected != null) {
            var newX = mouseX - clickDifX;
            var newY = mouseY - clickDifY;

            if (selected.isResizing()) {
                selected.resizeTo(mouseX, mouseY);
                mapCreationOptions();
            } else {
                selected.moveTo(newX, newY);
                mapCreationOptions();
            }
            redrawAll();
        }
    }*/
}

function handleMouseUp(e) {
    if (!selectedLoc || !selectedLoc.selectedFloor) {
        alert("No location/floor selected.");
        return;
    }
    
    mouseX = parseInt(e.clientX - offsetX);
    mouseY = parseInt(e.clientY - offsetY);

    mouseDown = false;
    if (selectedLoc.selectedFloor.selected != null) {
        clickDifX = mouseX - selectedLoc.selectedFloor.selected.width;
        clickDifY = mouseY - selectedLoc.selectedFloor.selected.height;
        selectedLoc.selectedFloor.selected.stopResize();
        selectedLoc.selectedFloor.selected.strokeStyle = "#000000";
    }
}

function handleMouseClick(e) {
    if (!selectedLoc || !selectedLoc.selectedFloor) {
        alert("No location/floor selected.");
        return;
    }
    
    mouseX = parseInt(e.clientX - offsetX);
    mouseY = parseInt(e.clientY - offsetY);

    if (draw.checked) {
        selectedLoc.selectedFloor.selectElement(null);
        createComponent(mouseX, mouseY);
        console.log("Creating component");
    /*} else if (resize.checked) {
        getSelected();
        redrawAll();
        mapCreationOptions();*/
    } else if (modify.checked) {
        getSelected();
        redrawAll();
        mapCreationOptions();
    }
}

// Bind the EventHandler functions to the Map control
$("#map").mousedown(handleMouseDown);
$("#map").mousemove(handleMouseMove);
$("#map").mouseup(handleMouseUp);
$("#map").click(handleMouseClick);


/**********************
 * HTML-EventHandlers *
 **********************/
function onKeyup(e) {
    if (!selectedLoc || !selectedLoc.selectedFloor) {
        return;
    }
    
    if (e.keyCode == 46) {
        if (selectedLoc.selectedFloor.selected != null) {
            var elements = selectedLoc.selectedFloor.elements;
            var remove;
            for (var i = 0; i < elements.length; i++) {
                if (elements[i] == selectedLoc.selectedFloor.selected) {
                    remove = i;
                    break;
                }
            }
            elements.splice(remove, 1);
            removeElement(selectedLoc, selectedLoc.selectedFloor, selectedLoc.selectedFloor.selected);
            console.log("REMOVED " + remove + ", LEFT: " + elements.length);
            selectedLoc.selectedFloor.selectElement(null);
            redrawAll();
        }
    }
}

// Bind the EventHandler functions to the complete HTML control
$("html").keyup(onKeyup);


/******************************************
 * Location/Floor selection EventHandlers *
 ******************************************/
function onLocationChange() {
    var result = document.getElementById("option-location");
    redrawAll();
    // Find location by ID
    if (!findLocation(result.value)) {
        return;
    }
    
    // Load the designated floors in the list
    loadFloorList();
    onFloorChange();    
    
    // Reset floor selection 
    selectedLoc.selectFloor(null);    
}

function onFloorChange() {
    var result = document.getElementById("option-floor"); 
    
    console.log("Floor change: " + result.value);
    //  Set the selected floor
    selectedLoc.selectFloor(result.value);
    console.log("Selected floor... " + selectedLoc.selectedFloor);
    
    // Redraw the current floor
    redrawAll();
}