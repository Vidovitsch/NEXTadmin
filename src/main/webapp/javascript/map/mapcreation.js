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
var selected = null;
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
    selectedLoc = new Venue(generateNewId("location"), name, postal, address);
    locations.push(selectedLoc);
    
    // Add the new location to the GUI
    var menu = document.getElementById("option-location");
    var option = document.createElement("option");
    option.value = selectedLoc.id;
    option.text = selectedLoc.name;
    menu.add(option);
    console.log("New location has been added!");
    createLocationForm();
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
    var selectedFloor = new Floor(generateNewId("floor"), floorname, level);
    selectedLoc.addFloor(selectedFloor);
    selectedLoc.selectFloor(selectedFloor.name);
    console.log("Floor: " + selectedFloor.name + ", level: " + selectedFloor.level);

    
    // Add the new floor to the GUI
    var menu = document.getElementById("option-floor");
    var option = document.createElement("option");
    option.value = selectedFloor.id;
    option.text = selectedFloor.name;
    menu.add(option);
    console.log("New floor has been added to " + selectedLoc.name + "!")
    createFloorForm();
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
    if (obj[0].checked) {
        //alert("Rectangle selected.");
        selected = new Rectangle(generateNewId("rect"), x, y, 50, 50);
    } else if (obj[1].checked) {
        //alert("Table selected.");
        selected = new Table(generateNewId("table"), x, y, 50, 50, 0);
    } else if (obj[2].checked) {
        //alert("Room selected.");
        selected = new Room(generateNewId("room"), x, y, 100, 100, 0, "new_room");
    } else if (obj[3].checked) {
        //alert("Circle selected.");
        selected = new Circle(generateNewId("circle"), x, y, 25);
    } else if (obj[4].checked) {
        //alert("Line selected.");
        selected = new Wall(generateNewId("wall"), x, y);
    }
    selectedLoc.selectedFloor.addElement(selected);
    selected.strokeStyle = '#ff0000';
}

function generateNewId(type) {
    var countup = 0;
    var id = type + countup;
    while (checkIfIdExists(id, type)) {
        id = type + countup++;
        console.log("New ID: " + id);
    }
    return id;
}

function checkIfIdExists(id, type) {
    if (type == "location") {
        for (var i = 0; i < locations.length; i++) {
            if (String(id) === String(locations[i].id)) {
                return true;
            }
        }
        return false;
    }
    else if (type == "floor") {
        for (var i = 0; i < selectedLoc.floors.length; i++) {
            if (String(id) === String(selectedLoc.floors[i].id)) {
                return true;
            }
        }
        return false;
    }
    else {
        for (var i = 0; i < selectedLoc.selectedFloor.elements.length; i++) {
            if (String(id) === String(selectedLoc.selectedFloor.elements[i].id)) {
                return true;
            }
        }
        return false;
    }
    
    /*for (var i = 0; i < components.length; i++) {
        if (String(id) === String(components[i].id)) {
            return true;
        }
    }*/
    return false;
}

function getSelected() {
    if (!selectedLoc || !selectedLoc.selectedFloor) {
        alert("No location/floor selected.");
        return;
    }
    
    var elements = selectedLoc.selectedFloor.elements;
    for (var i = 0; i < elements.length; i++) {
        if (elements[i].isPointInside(mouseX, mouseY)) {
            selected = elements[i];
            selected.strokeStyle = "#ff0000";
            redrawAll();
            mapCreationOptions();
            break;
        } else {
            selected = null;
        }
    }
    
    /*for (var i = 0; i < components.length; i++) {
        if (components[i].isPointInside(mouseX, mouseY)) {
            selected = components[i];
            selected.strokeStyle = '#ff0000';
            redrawAll();
            mapCreationOptions();
            break;
        } else {
            selected = null;
        }
    }*/
}

// This function redraws all the components and rectangles
function redrawAll() {
    if (!selectedLoc || !selectedLoc.selectedFloor) { 
        alert("No location/floor selected.");
        return;
    }
    
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    
    var elements = selectedLoc.selectedFloor.elements;
    for (var i = 0; i < elements.length; i++) {
        if (selected == null) {
            elements[i].strokeStyle = "#000000";
        }
        elements[i].draw();
    }
    
    /*for (var i = 0; i < components.length; i++)
    {
        if (selected == null) {
            components[i].strokeStyle = "#000000";
        }
        components[i].draw();
    }*/
}

/*********************
 * Map-EventHandlers *
 *********************/
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
        } else {
            selected.checkCloseEnough(mouseX, mouseY);
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
    if (e.keyCode == 46) {
        if (selected != null) {
            var elements = selectedLoc.selectedFloor.elements;
            var remove;
            for (var i = 0; i < elements.length; i++) {
                if (elements[i] == selected) {
                    remove = i;
                    break;
                }
            }
            elements.splice(remove, 1);
            console.log("REMOVED " + remove + ", LEFT: " + elements.length);
            selected = null;
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
    
    // Find location by ID
    if (!findLocation(result.value)) {
        return;
    }
    
    // Load the designated floors in the list
    loadFloorList();
    
    
    // Reset floor selection 
    selectedLoc.selectFloor(null);    
}

function onFloorChange() {
    var result = document.getElementById("option-floor"); 
    
    //  Set the selected floor
    selectedLoc.selectFloor(result.text);
    
    // Redraw the current floor
    redrawAll();
}