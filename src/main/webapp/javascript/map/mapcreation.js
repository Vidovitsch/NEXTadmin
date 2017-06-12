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
function updateLocation() {
    if (!selectedLoc) {
        alert("No location selected.");
        return;
    }
    
    var name = document.getElementById("locationform-name").value;
    var address = document.getElementById("locationform-address").value;
    var postal = document.getElementById("locationform-postal").value;
    if (!name || !address || !postal) {
        alert("[LOCATION] Not all required info has been filled in.");
        return;
    }
    
    // Update the location
    selectedLoc.name = name;
    selectedLoc.address = address;
    selectedLoc.postal = postal;
    editLocation(selectedLoc);
    
    // Close the form
    editLocationForm();
    
    // Modify the updated location in the GUI (name change)
    var options = document.getElementById("option-location").options;
    var index = options.selectedIndex;
    options[index].text = selectedLoc.name; 
}
function deleteLocation() {
    if (!selectedLoc) {
        alert("No location/floor selected.");
        return;
    }
    // Remove the location
    removeLocation(selectedLoc);
    
    // Close the form
    editLocationForm();
    
    // Remove the no longer existing location from the GUI
    var options = document.getElementById("option-location").options;
    var index = options.selectedIndex;
    options.remove(index);
    
    if (options.length > 0) {
        onLocationChange();
    } else {
        selectedLoc = null;
        reloadItemMenu(createItemMenu(null));
    }
    redrawAll();
}

function newFloor() {
    // Check the filled in values
    var floorname = document.getElementById("floorform-floorname").value;
    var level = document.getElementById("floorform-level").value;
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
    document.getElementById("option-floor").options.length = 0;
}
function loadFloorList() { 
    clearFloorList();
    var menu = document.getElementById("option-floor");
    for (var i = 0; i < selectedLoc.floors.length; i++) {
        var option = document.createElement("option");
        option.value = selectedLoc.floors[i].id;
        option.text = selectedLoc.floors[i].name;
        menu.add(option);
    }
    console.log("All floors have been added to the list related to the selected location.");
}
function updateFloor() {
    if (!selectedLoc || !selectedLoc.selectedFloor) {
        alert("No location/floor selected.");
        return;
    }
    
    var name = document.getElementById("floorform-name").value;
    var level = document.getElementById("floorform-level").value;
    if (!name || !level) {
        alert("[FLOOR] Not all required info has been filled in.");
        return;
    }
    
    selectedLoc.selectedFloor.name = name;
    selectedLoc.selectedFloor.level = level;
    editFloor(selectedLoc, selectedLoc.selectedFloor);
    
    // Close the form
    editFloorForm();
    
    // Modify the updated floor in the GUI (name change)
    var options = document.getElementById("option-floor").options;
    var index = options.selectedIndex;
    options[index].text = selectedLoc.selectedFloor.name; 
}
function deleteFloor() {
    if (!selectedLoc || !selectedLoc.selectedFloor) {
        alert("No location/floor selected.");
        return;
    }
    
    removeFloor(selectedLoc, selectedLoc.selectedFloor);
    
    // Close the form
    editFloorForm();
    
    // Remove the no longer existing floor from the GUI
    var options = document.getElementById("option-floor").options;
    var index = options.selectedIndex;
    options.remove(index);
    
    // The selected floor will be changed to the first floor in the list
    // This will only happen if you still have floors left, else no floor will be selected
    if (options.length > 0) {
        onFloorChange();
        //selectedLoc.selectFloor(options[0].id);
        //reloadItemMenu(createItemMenu(selectedLoc.selectedFloor));
    } else {
        selectedLoc.selectFloor(null);
        reloadItemMenu(createItemMenu(null));
    }
    redrawAll();
}

function reloadItemMenu(layout) {
    document.getElementById("dynamic-items").innerHTML = layout;
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
    reloadItemMenu(createItemMenu(selectedLoc.selectedFloor));

    saveElement(selectedLoc, selectedLoc.selectedFloor, element);
    clearElementSelection();
}

// This function selects an element based on mouseX and mouseY coordinates
function selectElement(x, y) {
    if (!selectedLoc || !selectedLoc.selectedFloor) {
        alert("No location/floor selected.");
        return;
    }
    
    var elements = selectedLoc.selectedFloor.elements;
    for (var i = 0; i < elements.length; i++) {
        if (elements[i].isPointInside(x, y)) {
            selectedLoc.selectedFloor.selectElement(elements[i]);
            //selectedLoc.selectedFloor.selected.strokeStyle = "#ff0000";
            document.getElementById(elements[i].id).checked = true;
            //redrawAll();
            mapCreationOptions();
            break;
        } else {
            clearElementSelection();
        }
    }
}

function selectElementById(id) {
    if (!selectedLoc || !selectedLoc.selectedFloor) {
        alert("No location/floor selected.");
        return;
    }
    
    var elements = selectedLoc.selectedFloor.elements;
    for (var i = 0; i < elements.length; i++) {
        if (elements[i].id == id) {
            selectedLoc.selectedFloor.selectElement(elements[i]);
            mapCreationOptions();
            break;
        }
    }
}

function clearElementSelection() {
    if (!selectedLoc || !selectedLoc.selectedFloor) return; 
    
    selectedLoc.selectedFloor.selectElement(null);
    document.getElementById("none").checked = true;
}

function clearCanvas() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
}
// This function redraws all the components and rectangles
function redrawAll() {
    clearCanvas();
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
    
    //mouseX = parseInt(e.clientX - offsetX);
    //mouseY = parseInt(e.clientY - offsetY);
    mouseX = parseInt(e.pageX - offsetX);
    mouseY = parseInt(e.pageY - offsetY);
    
    mouseDown = true;
    
    var selected = selectedLoc.selectedFloor.selected;
    if (selected != null && modify.checked) {
        // Measure the click difference between the mouseclick and the selected XY
        // This has to happen in the mousedown as this is the part where the dragging/resizing will start
        clickDifX = mouseX - selected.x;
        clickDifY = mouseY - selected.y;
        if (selected.type == "line") {
            selected.checkCloseEnough(mouseX, mouseY, true);
        } else {
            selected.checkCloseEnough(mouseX, mouseY, true);
        }
    }
}

function handleMouseMove(e) {    
    //mouseX = parseInt(e.clientX - offsetX);
    //mouseY = parseInt(e.clientY - offsetY);
    mouseX = parseInt(e.pageX - offsetX);
    mouseY = parseInt(e.pageY - offsetY);

    document.getElementById("coordinates").innerHTML = "<b>X:</b> " + mouseX + ", <b>Y:</b> " + mouseY;

    if (!selectedLoc || !selectedLoc.selectedFloor) {
        mouseDown = false;
        return;
    }
    
    if (modify.checked) {
        showDrawableIcon(mouseX, mouseY);
    }
    
    if (mouseDown && modify.checked) {
        var selected = selectedLoc.selectedFloor.selected;
        if (selected == null) {
            mouseDown = false;
            return;
        }
        
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
}

function handleMouseUp(e) {
    if (!selectedLoc || !selectedLoc.selectedFloor) {
        alert("No location/floor selected.");
        return;
    }
    
    //mouseX = parseInt(e.clientX - offsetX);
    //mouseY = parseInt(e.clientY - offsetY);
    mouseX = parseInt(e.pageX - offsetX);
    mouseY = parseInt(e.pageY - offsetY);

    mouseDown = false;
    var curElement = selectedLoc.selectedFloor.selected;
    if (curElement != null) {
        clickDifX = mouseX - curElement.width;
        clickDifY = mouseY - curElement.height;
        
        curElement.stopResize();
        console.log("Should have stopped resizing");
        curElement.strokeStyle = "#000000";
        saveElement(selectedLoc, selectedLoc.selectedFloor, curElement);
    }
}

function handleMouseClick(e) {
    if (!selectedLoc || !selectedLoc.selectedFloor) {
        alert("No location/floor selected.");
        return;
    }
    
    //mouseX = parseInt(e.clientX - offsetX);
    //mouseY = parseInt(e.clientY - offsetY);
    mouseX = parseInt(e.pageX - offsetX);
    mouseY = parseInt(e.pageY - offsetY);

    if (draw.checked) {
        selectedLoc.selectedFloor.selectElement(null);
        createComponent(mouseX, mouseY);
        console.log("Creating component");
    } else if (modify.checked) {
        selectElement(mouseX, mouseY);
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
    
    // Keycode 46 = delete (Del)
    if (e.keyCode == 46) {
        selectedLoc.selectedFloor.removeSelectedElement();
        removeElement(selectedLoc, selectedLoc.selectedFloor, selectedLoc.selectedFloor.selected);
        reloadItemMenu(createItemMenu(selectedLoc.selectedFloor));
        clearElementSelection();
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

    reloadItemMenu(createItemMenu(selectedLoc.selectedFloor));
    
    clearElementSelection();

    // Redraw the current floor
    redrawAll();
}

function onItemListSelectionChange(e) {
    var id = e.id;
    console.log("ItemList selection: " + id);
    if (id == "none") {
        clearElementSelection();
    } else {
        selectElementById(id);
    }
}

function showDrawableIcon(x, y) {
    //canvas.style.cursor = "default";
    //canvas.style.cursor = "move"; // for moving an object
    //canvas.style.cursor = "crosshair"; // for resizing
    canvas.style.cursor = "default";
    
    if (!selectedLoc || !selectedLoc.selectedFloor) {
        alert("No location/floor selected.");
        return;
    }    
    
    var curElement = selectedLoc.selectedFloor.selected;
    if (curElement == null) return;
    else {
        if (curElement.isPointInside(x, y)) {
            if (curElement.checkCloseEnough(x, y, false)) {
                canvas.style.cursor = "crosshair";
            } else {
                canvas.style.cursor = "move";
            }
        }
    }
    
    /*for (var i = 0; i < elements.length; i++) {
        curElement = elements[i];
        if (curElement.isPointInside(x, y)) {
            if (curElement.checkCloseEnough(x, y)) {
                canvas.style.cursor = "crosshair";
            } else {
                canvas.style.cursor = "move";
            }
            break;
        }
    }*/
}