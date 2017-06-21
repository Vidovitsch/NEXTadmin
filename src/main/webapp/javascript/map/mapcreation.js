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

/**
 * This method will create a new Location and load it in the lcoation selection menu.
 * Changes will be saved to firebase.
 * @returns {undefined}
 */
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
/**
 * This method fills the selection menu with all the existing locations.
 * @returns {undefined}
 */
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
/**
 * This method finds and selects a location based on ID. 
 * If no matching ID can be found in the locations list, false will be returned.
 * Else, the location matching the ID will be selected and true will be returned.
 * @param {type} id
 * @returns {Boolean}
 */
function findLocation(id) { 
    for (var i = 0; i < locations.length; i++) {
        if (String(locations[i].id) === String(id)) {
            selectedLoc = locations[i];
            return true;
        }
    }
    return false;
}
/**
 * This method updates the information of an existing location based on the input fields of the locationform.
 * A location cannot be updated if no existing location is selected.
 * If the input fields 'name', 'address' or 'postal' are empty, null or undefined, the location will not be updated.
 * Changes will be saved to firebase.
 * @returns {undefined}
 */
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
/**
 * This method deletes a location based on the currently selected location.
 * A location cannot be deleted if no existing location has been selected.
 * Changes will be saved to firebase.
 * If possible, a different location and floor will be shown and all elements will be redrawn. 
 * Else a new location and floor has to be created and the map will only be cleared until manually changed.
 * @returns {undefined}
 */
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

/**
 * This method will create a new floor and add it to the currently selected existing location.
 * The floor cannot be created if no existing location has been selected
 * Changes will be saved to firebase.
 * @returns {undefined}
 */
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
/**
 * This method clears the current floor selection menu by setting the length value to 0.
 * @returns {undefined}
 */
function clearFloorList() { 
    document.getElementById("option-floor").options.length = 0;
}
/**
 * This method will load all the floors of a location in the Floor selection menu.
 * The floors cannot be loaded if no existing location has been selected.
 * @returns {undefined}
 */
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
/**
 * This method updates the information of an existing floor based on the input fields of the floorform.
 * A floor cannot be updates if no existing location and no existing floor are selected.
 * If the input fields 'name' or 'level' are empty, null or undefined, the floor will not be updated.
 * Changes will be saved to firebase.
 * @returns {undefined}
 */
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
/**
 * This method deletes a floor based on the currently selected Floor.
 * A floor cannot be deleted if no existing Location and no existing Floor are selected.
 * Changes are saved to firebase.
 * If possible, a different floor of the same location will be shown and all elements will be redrawn. 
 * Else a new floor has to be created and the map will only be cleared until manually changed.
 * @returns {undefined}
 */
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

/**
 * This method dynamically reloads the item menu based on a string value. 
 * The string value should be in the style of HTML <div></div> etc.
 * @param {type} layout
 * @returns {undefined}
 */
function reloadItemMenu(layout) {
    document.getElementById("dynamic-items").innerHTML = layout;
}

/**
 * This method creates an element based on element type selection menu and X + Y coördinates.
 * Creating an element is only possible if an existing location and existing floor are selected.
 * The created element will be saved to firebase.
 * @param {type} x
 * @param {type} y
 * @returns {undefined}
 */
function createComponent(x, y) {   
    if (selectedLoc === null || selectedLoc.selectedFloor === null) {
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

/**
 * This method selects an element by X and Y coördinate.
 * Selecting an element is only possible if an existing location and existing floor are selected.
 * If the X and Y coördinates aren't within any of the elements boundaries, the selected element will be cleared or no element will be selected.
 * @param {type} x
 * @param {type} y
 * @returns {undefined}
 */
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

/**
 * This method selects an element by ID.
 * Selecting an element is only possible if an existing location and existing floor are selected.
 * @param {type} id
 * @returns {undefined}
 */
function selectElementById(id) {
    if (!selectedLoc || !selectedLoc.selectedFloor) {
        alert("No location/floor selected.");
        return;
    }
    
    var elements = selectedLoc.selectedFloor.elements;
    for (var i = 0; i < elements.length; i++) {
        if (elements[i].id === id) {
            selectedLoc.selectedFloor.selectElement(elements[i]);
            mapCreationOptions();
            break;
        }
    }
}

/**
 * This method clears the element selection.
 * Clearing element selection is only possible if an existing location and existing floor are selected.
 * @returns {undefined}
 */
function clearElementSelection() {
    if (!selectedLoc || !selectedLoc.selectedFloor) return; 
    
    selectedLoc.selectedFloor.selectElement(null);
    document.getElementById("none").checked = true;
}

/**
 * This method clears the canvas. 
 * This method can be very useful for actions which require you to redraw elements on the map as it will remove the error of drawing over existing objects.
 * @returns {undefined}
 */
function clearCanvas() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
}

/**
 * This method redraws all the elements of the currently selected floor on the currently selected location.
 * Before drawing, the canvas will be cleared.
 * @returns {undefined}
 */
function redrawAll() {
    clearCanvas();
    if (!selectedLoc || !selectedLoc.selectedFloor) { 
        return;
    }
    
    console.log("[redrawAll]");

    selectedLoc.selectedFloor.drawElements();
}

/*********************
 * Map-EventHandlers *
 *********************/

/**
 * This method acts as an event handler for a mousedown event.
 * Based on the mouse X and Y coördinates the boolean for saving the mousedown status will be set to true. (mouseup = false)
 * Also, there will be a check if the Modifications option has been selected and the selected element is not null.
 * If this is true, there will be a check for checking if the resize corners or borders are within reach. 
 * If this is the case, the resize will be enabled for the object based on the mousedown coördinates.
 * @param {type} e
 * @returns {undefined}
 */
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
    if (selected !== null && modify.checked) {
        // Measure the click difference between the mouseclick and the selected XY
        // This has to happen in the mousedown as this is the part where the dragging/resizing will start
        clickDifX = mouseX - selected.x;
        clickDifY = mouseY - selected.y;
        if (selected.type === "line") {
            selected.checkCloseEnough(mouseX, mouseY, true);
        } else {
            selected.checkCloseEnough(mouseX, mouseY, true);
        }
    }
}

/**
 * This method acts as an event handler for the mousemove event.
 * Based on the mouse X and Y coördinates the selected element can be moved or resized.
 * If an element is selected and the Modifications option has been selected the element is allowed to move or resize.
 * In order to resize, the mousedown parameters should have been set to resizing (see mousedown javadoc).
 * Else the element will simply be moved till the mouseup event is being called.
 * @param {type} e
 * @returns {undefined}
 */
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
        if (selected === null) {
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

/**
 * This method acts as an event handler for a mouseup event.
 * Based on the mouse X & Y coördinates the boolean for saving the mousedown status will be set back to false. (mousedown = true)
 * The current selected element will stop resizing and will revert to it's old strokestyle.
 * After the resizing or moving, the element's location or size will differ. 
 * This difference will be saved to firebase.
 * @param {type} e
 * @returns {undefined}
 */
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
    if (curElement !== null) {
        clickDifX = mouseX - curElement.width;
        clickDifY = mouseY - curElement.height;
        
        curElement.stopResize();
        console.log("Should have stopped resizing");
        curElement.strokeStyle = "#000000";
        saveElement(selectedLoc, selectedLoc.selectedFloor, curElement);
    }
}

/**
 * This method acts as an event handler for a mouseclick event.
 * Based on the mouseclick the X and Y coördinates will be received by the event for pageX and pageY to set the mouseX and mouseY coördinates.
 * If the draw menu option is selected the selected element will be cleared and the selected component will be drawn based on the mouseX and mouseY coördinates.
 * If the modify menu option is selected, the selected element will be verified by the mouseX and mouseY coördinate. 
 * If successfully selecting an element, the element will be highlighted and the element specific options will be shown in the top menu.
 * @param {type} e
 * @returns {undefined}
 */
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

/**
 * This method acts as an event handler for key-events.
 * KeyCode 46 is the delete button.
 * When pressing deleted the selected element will be removed from the canvas, item menu and firebase.
 * The element selection will be cleared as well.
 * @param {type} e
 * @returns {undefined}
 */
function onKeyup(e) {
    if (!selectedLoc || !selectedLoc.selectedFloor) {
        return;
    }
    
    // Keycode 46 = delete (Del)
    if (e.keyCode === 46) {
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

/**
 * This method changes the current location and also loads all the floors from that location.
 * Also onFloorChange() will be called to load the right item menu (containing a list of elements of that floor) and all these elements will also be drawn on the canvas.
 * @returns {undefined}
 */
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

/**
 * This method changes the floor based on selection menu value.
 * The selection menu value will be used to select the floor from the current location. 
 * After selecting a floor on the current location, the item menu (which contains all elements of a floor) will be reloaded.
 * Element selection will be cleared so selection should be "none" which means that no element is selected.
 * Also all the elements of the selected floor will be redrawn on the canvas.
 * @returns {undefined}
 */
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

/**
 * This method gets the id from the itemlist selection event.
 * If the option "none" is selected, no elements may be selected.
 * If the option contains an ID the element which belongs to the ID will be selected.
 * @param {type} e
 * @returns {undefined}
 */
function onItemListSelectionChange(e) {
    var id = e.id;
    console.log("ItemList selection: " + id);
    if (id === "none") {
        clearElementSelection();
    } else {
        selectElementById(id);
    }
}

/**
 * This method shows a cursor style based on the location and selection of an element.
 * If an element is currently selected and you hover over this element, 2 types of cursor can be shown.
 * The first cursor is the "move" cursor, which will only be shown if the cursor can be found within the boundaries of the selected element.
 * The second cursor is the "crosshair" cursor, which will only be shown if the cursor is in resizing range of the selected element.
 * @param {type} x
 * @param {type} y
 * @returns {undefined}
 */
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
    if (curElement === null) return;
    else {
        if (curElement.isPointInside(x, y)) {
            if (curElement.checkCloseEnough(x, y, false)) {
                canvas.style.cursor = "crosshair";
            } else {
                canvas.style.cursor = "move";
            }
        }
    }
}