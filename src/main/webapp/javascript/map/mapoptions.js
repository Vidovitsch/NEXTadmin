var draw = document.getElementById("option-create");
var modify = document.getElementById("option-edit");

var editCountLocation = 0;
var editCountFloor = 0;

/**
 * This method generates the dynamic possible options belonging to the Create selection option
 * @returns {undefined}
 */
function mapCreationOptions() {
    if (draw.checked) {
        // Open div container
        document.getElementById("dynamic-options").innerHTML = '<div class="radio-wrapper-m">' +
        // Radio button: Rectangle, index 0
        '<input id="option-rectangle" type="radio" name="drawing-objects" value="rectangle" checked>' +
        '<label class="label-option" for="option-rectangle"><img src="/images/map/rectangle.png" width="64px" height="64px" /></label>' +
        // Radio button: Table, index 1
        '<input id="option-table" type="radio" name="drawing-objects" value="table">' +
        '<label class="label-option" for="option-table"><img src="/images/map/table.png" width="64px" height="64px" /></label>' +
        // Radio button: Room, index 2
        '<input id="option-room" type="radio" name="drawing-objects" value="room">' +
        '<label class="label-option" for="option-room"><img src="/images/map/room.png" width="64px" height="64px" /></label>' +
        // Radio button: Circle, index 3
        '<input id="option-circle" type="radio" name="drawing-objects" value="circle">' +
        '<label class="label-option" for="option-circle"><img src="/images/map/circle.png" width="64px" height="64px" /></label>' +
        // Radio button: Line, index 4
        '<input id="option-line" type="radio" name="drawing-objects" value="line">' +
        '<label class="label-option" for="option-line"><img src="/images/map/line.png" width="64px" height="64px" /></label>' +
        // Close div container
        '</div>';
    }
    else if (modify.checked) {
        // Open div container
        if (selectedLoc == null || selectedLoc.selectedFloor == null) {
            document.getElementById("dynamic-options").innerHTML = "Woops, you forgot to select a location and/or floor!";
            return;
        } 
        
        var selected = selectedLoc.selectedFloor.selected;
        if (selected == null) {
            document.getElementById("dynamic-options").innerHTML = createNewModifications();   
        }
        else if (selected.type == "rectangle") {
            document.getElementById("dynamic-options").innerHTML = createRectangleModifications(selected);
            document.getElementById("option-style").selectedIndex = 1;
        }
        else if (selected.type == "table") {
            document.getElementById("dynamic-options").innerHTML = createTableModifications(selected);
            document.getElementById("option-style").selectedIndex = 2
        }
        else if (selected.type == "room") {
            document.getElementById("dynamic-options").innerHTML = createRoomModifications(selected);
            document.getElementById("option-style").selectedIndex = 3;
        }
        else if (selected.type == "circle") {
            document.getElementById("dynamic-options").innerHTML = createCircleModifications(selected);
            document.getElementById("option-style").selectedIndex = 4;
        }
        else if (selected.type == "line") {
            document.getElementById("dynamic-options").innerHTML = createLineModifications(selected);
            document.getElementById("option-style").selectedIndex = 5;
        } 
    }
}

function createComboboxStyleSelection(isElementSelected) {
    var layout = '<label class="label-visual" for="option-style">Style: ' +
    '<select id="option-style" onchange="createSpecifiedElementForm()" ' + (isElementSelected ? ' disabled' : '')  + '>' +
            // Options: Rectangle, Circle, Line, Text, Optionally Table as well
            '<option></option>' + 
            '<option>Rectangle</option>' +
            '<option>Table</option>' +
            '<option>Room</option>' +
            '<option>Circle</option>' +
            '<option>Line</option>' +
    '</select>' +
    '</label>';
    return layout;
}
function createNewModifications() {
    var layout = '<div id="modify-div" class="radio-wrapper-m">' + 
    // Combox Style selection - element is not selected so parameter = false
    createComboboxStyleSelection(false) + 
    // Close div
    '</div>';
    return layout;
}

/**
 * This method creates the edit form for each specified element.
 * @param {Object} element modifications from the possible elements (rectangle, circle, room, table, line)
 * @returns {String}
 */
function createCircleModifications(element) {
    // Open div container
    var layout = '<div class="radio-wrapper-m">' +
    // Combobox Style selection - element is selected so parameter = true
    createComboboxStyleSelection(true) +
    // ID
    '<label class="label-visual" for="option-id">ID: ' +
    '<input id="option-id" type="text" placeholder="id" value="' + element.id + '" disabled>' +
    '</label>' + 
    // X
    '<label class="label-visual" for="option-x">X: '+
    '<input id="option-x" type="number" placeholder="x" min="0" step="1" value="' + element.x + '" >' +
    '</label>' + 
    // Y
    '<label class="label-visual" for="option-y">Y: ' +
    '<input id="option-y" type="number" placeholder="y" min="0" step="1" value="' + element.y + '" >' +
    '</label>' +
    // Width
    '<label class="label-visual" for="option-radius">Radius: ' +
    '<input id="option-radius" type="number" placeholder="radius" min="0" step="any" value="' + element.radius + '" >' +
    '</label>' + 
    // Update option
    createUpdateModification();
    // Close div container
    '</div>';
    return layout;
}
function createRectangleModifications(element) {
    // Open div container
    var layout = '<div class="radio-wrapper-m">' +
    // Combox Style selection - element is selected so parameter = true
    createComboboxStyleSelection(true) +
    // ID
    '<label class="label-visual" for="option-id">ID: ' +
    '<input id="option-id" type="text" placeholder="id" value="' + element.id + '" disabled>' +
    '</label>' + 
    // X
    '<label class="label-visual" for="option-x">X: '+
    '<input id="option-x" type="number" placeholder="x" min="0" step="1" value="' + element.x + '">' +
    '</label>' + 
    // Y
    '<label class="label-visual" for="option-y">Y: ' +
    '<input id="option-y" type="number" placeholder="y" min="0" step="1" value="' + element.y + '">' +
    '</label>' +
    // Width
    '<label class="label-visual" for="option-width">Width: ' +
    '<input id="option-width" type="number" placeholder="width" min="0" step="1" value="' + element.width + '">' +
    '</label>' + 
    // Height
    '<label class="label-visual" for="option-height">Height: ' + 
    '<input id="option-height" type="number" placeholder="height" min="0" step="1" value="' + element.height + '">' +
    '</label>' +
    // Update option
    createUpdateModification();
    // Close div container
    '</div>';
    return layout;
}
function createTableModifications(element) {
    // Open div container
    var layout = '<div class="radio-wrapper-m">' +
    // Combox Style selection - element is selected so parameter = true
    createComboboxStyleSelection(true) +
    // ID
    '<label class="label-visual" for="option-id">ID: ' +
    '<input id="option-id" type="text" placeholder="id" value="' + element.id + '" disabled>' +
    '</label>' + 
    // X
    '<label class="label-visual" for="option-x">X: '+
    '<input id="option-x" type="number" placeholder="x" min="0" step="1" value="' + element.x + '">' +
    '</label>' + 
    // Y
    '<label class="label-visual" for="option-y">Y: ' +
    '<input id="option-y" type="number" placeholder="y" min="0" step="1" value="' + element.y + '">' +
    '</label>' +
    // Width
    '<label class="label-visual" for="option-width">Width: ' +
    '<input id="option-width" type="number" placeholder="width" min="0" step="1" value="' + element.width + '">' +
    '</label>' + 
    // Height
    '<label class="label-visual" for="option-height">Height: ' + 
    '<input id="option-height" type="number" placeholder="height" min="0" step="1" value="' + element.height + '">' +
    '</label>' +
    // Number
    '<label class="label-visual" for="option-number">Number: ' +
    '<input id="option-number" type="number" placeholder="number" min="0" step="1" value="' + element.number + '">' +
    '</label>' +
    // Update option
    createUpdateModification();
    // Close div container
    '</div>';
    return layout;
}
function createRoomModifications(element) {
    // Open div container
    var layout = '<div class="radio-wrapper-m">' +
    // Combox Style selection - element is selected so parameter = true
    createComboboxStyleSelection(true) +
    // ID
    '<label class="label-visual" for="option-id">ID: ' +
    '<input id="option-id" type="text" placeholder="id" value="' + element.id + '" disabled>' +
    '</label>' + 
    // Name
    '<label class="label-visual" for="option-name">Name: ' + 
    '<input id="option-name" type="text" placeholder="name" value="' + element.roomname + '">' +
    '</label>' +
    // Capacity
    '<label class="label-visual" for="option-capacity">Capacity: ' +
    '<input id="option-capacity" type="number" placeholder="capacity" min="0" step="1" value="' + element.capacity + '">' +
    '</label>' +
    // X
    '<label class="label-visual" for="option-x">X: '+
    '<input id="option-x" type="number" placeholder="x" min="0" step="1" value="' + element.x + '">' +
    '</label>' + 
    // Y
    '<label class="label-visual" for="option-y">Y: ' +
    '<input id="option-y" type="number" placeholder="y" min="0" step="1" value="' + element.y + '">' +
    '</label>' +
    // Width
    '<label class="label-visual" for="option-width">Width: ' +
    '<input id="option-width" type="number" placeholder="width" min="0" step="1" value="' + element.width + '">' +
    '</label>' + 
    // Height
    '<label class="label-visual" for="option-height">Height: ' + 
    '<input id="option-height" type="number" placeholder="height" min="0" step="1" value="' + element.height + '">' +
    '</label>' +
    // Update option
    createUpdateModification();
    // Close div container
    '</div>';
    return layout;
}
function createLineModifications(element) {
	// Open div container
	var layout = '<div class="radio-wrapper-m">' +
	// Combox Style selection - element is selected so parameter = true
	createComboboxStyleSelection(true) +	
	// ID
	'<label class="label-visual" for="option-id">ID: ' +
	'<input id="option-id" type="text" placeholder="id" value="' + element.id + '" disabled>' +
	'</label>' + 
	// X
	'<label class="label-visual" for="option-x">X: '+
	'<input id="option-x" type="text" placeholder="x" value="' + element.x + '">' +
	'</label>' + 
	// Y
	'<label class="label-visual" for="option-y">Y: ' +
	'<input id="option-y" type="text" placeholder="y" value="' + element.y + '">' +
	'</label>' +
	// X2
	'<label class="label-visual" for="option-x2">X2: ' +
	'<input id="option-x2" type="text" placeholder="x2" value="' + element.x2 + '">' +
	'</label>' + 
	// Y2
	'<label class="label-visual" for="option-y2">Y2: ' +
	'<input id="option-y2" type="text" placeholder="y2" value="' + element.y2 + '">' +
	'</label>' +
	// Update option
	createUpdateModification();
	// Close div container
	'</div>';
	return layout;
}

/**
 * This method creates the menu of items based on the floor.
 * @param floor - the floor which holds all the elements placed on the floor
 * @returns {String}
 */
function createItemMenu(floor) {   
    var layout = '';

    if (!floor || floor.elements.length == 0) return layout;

    for (var i = 0; i < floor.elements.length; i++) {
        var id = floor.elements[i].id;
        console.log("createItemMenu(floor): " + id);
        layout += '<div class="floor-item">';
        layout += '<input id="' + id + '" type="radio" name="floor-items" value="' + id + '" onchange="onItemListSelectionChange(this)">';
        layout += '<label class="label-default" for="' + id + '">' + id + '</label>';
        layout += '</div>';
    }
    return layout;
}

/**
 * This method creates a dynamic update button which is meant for updating selected elements by using the required form.
 * @returns {String}
 */
function createUpdateModification() {
    var layout = '<input type="button" class="soflow-btn-l" value="Update" onclick="updateElement()" />';
    return layout;
}

/**
 * This method creates the form for creating new elements based on the selected type of element.
 * @returns {undefined}
 */
function createSpecifiedElementForm() {
    var index = document.getElementById("option-style").selectedIndex;
    var layout;
    if (index == 0) {
        layout = '<label class="label-visual" for="option-style>Style: "' +
        '<select id="option-style" onchange="createSpecifiedElementForm()">' +
            // Options: nothing, Rectangle, Table, Room, Circle, Line, Text <-- this one is nothing
            '<option selected></option>' +
            '<option>Rectangle</option>' +
            '<option>Table</option>' +
            '<option>Room</option>' +
            '<option>Circle</option>' +
            '<option>Line</option>' +
        '</select>' +
        '</label>';
    }
    if (index == 1) {
        // Create rectangle form: id, x, y, width, height
        // Combobox selection menu
        layout = '<label class="label-visual" for="option-style">Style: ' +
        '<select id="option-style" onchange="createSpecifiedElementForm()">' +
            // Options: Rectangle, Table, Room, Circle, Line, Text
            '<option></option>' + 
            '<option selected>Rectangle</option>' +
            '<option>Table</option>' +
            '<option>Room</option>' +
            '<option>Circle</option>' +
            '<option>Line</option>' +
        '</select>' +
        '</label>' +
        // ID
        '<label class="label-visual" for="option-id">ID: ' +
        '<input id="option-id" type="text" placeholder="id" />' +
        '</label>' + 
        // X
        '<label class="label-visual" for="option-x">X: '+
        '<input id="option-x" type="number" placeholder="x" min="0" step="1" />' +
        '</label>' + 
        // Y
        '<label class="label-visual" for="option-y">Y: ' +
        '<input id="option-y" type="number" placeholder="y" min="0" step="1" />' +
        '</label>' +
        // Width
        '<label class="label-visual" for="option-width">Width: ' +
        '<input id="option-width" type="number" placeholder="width" min="0" step="1" />' +
        '</label>' + 
        // Height
        '<label class="label-visual" for="option-height">Height: ' + 
        '<input id="option-height" type="number" placeholder="height" min="0" step="1" />' +
        '</label>';
    }
    else if (index == 2) {
        // Create table form: id, x, y, width, height, number
        // combobox selection menu
        layout = '<label class="label-visual" for="option-style">Style: ' +
        '<select id="option-style" onchange="createSpecifiedElementForm()">' +
            // Options: Rectangle, Table, Room, Circle, Line, Text
            '<option></option>' + 
            '<option>Rectangle</option>' +
            '<option selected>Table</option>' +
            '<option>Room</option>' +
            '<option>Circle</option>' +
            '<option>Line</option>' +
        '</select>' +
        '</label>' + 
        // ID
        '<label class="label-visual" for="option-id">ID: ' +
        '<input id="option-id" type="text" placeholder="id" />' +
        '</label>' + 
        // X
        '<label class="label-visual" for="option-x">X: '+
        '<input id="option-x" type="number" placeholder="x" min="0" step="1" />' +
        '</label>' + 
        // Y
        '<label class="label-visual" for="option-y">Y: ' +
        '<input id="option-y" type="number" placeholder="y" min="0" step="1" />' +
        '</label>' +
        // Width
        '<label class="label-visual" for="option-width">Width: ' +
        '<input id="option-width" type="number" placeholder="width" min="0" step="1" />' +
        '</label>' + 
        // Height
        '<label class="label-visual" for="option-height">Height: ' + 
        '<input id="option-height" type="number" placeholder="height" min="0" step="1" />' +
        '</label>' + 
        // Number
        '<label class="label-visual" for="option-number">Number: ' +
        '<input id="option-number" type="number" placeholder="number" min="0" />' +
        '</label>';
    }
    else if (index == 3) {
        // Create room form: id, name, capacity, x, y, width, height
        // Combobox selection menu
        layout = '<label class="label-visual" for="option-style">Style: ' +
        '<select id="option-style" onchange="createSpecifiedElementForm()">' +
            // Options: Rectangle, Table, Room, Circle, Line, Text
            '<option></option>' + 
            '<option>Rectangle</option>' +
            '<option>Table</option>' +
            '<option selected>Room</option>' + 
            '<option>Circle</option>' +
            '<option>Line</option>' +
        '</select>' +
        '</label>' +
        // ID
        '<label class="label-visual" for="option-id">ID: ' +
        '<input id="option-id" type="text" placeholder="id" />' +
        '</label>' + 
        // Name
        '<label class="label-visual" for="option-name">Name: ' +
        '<input id="option-name" type="text" placeholder="name" />' +
        '</label>' +
        // Capacity
        '<label class="label-visual" for="option-capacity">Capacity: ' +
        '<input id="option-capacity" type="number" placeholder="capacity" min="1"  step="1" />' +
        '</label>' +
        // X
        '<label class="label-visual" for="option-x">X: ' +
        '<input id="option-x" type="number" placeholder="x" min="0" step="1" />' +
        '</label>' +
        // Y
        '<label class="label-visual" for="option-y">Y: ' +
        '<input id="option-y" type="number" placeholder="y" min="0" step="1" />' +
        '</label>' +        
        // Width
        '<label class="label-visual" for="option-width">Width: ' +
        '<input id="option-width" type="number" placeholder="width" min="0" step="1" />' +
        '</label>' +      
        // Height
        '<label class="label-visual" for="option-height">Height: ' +
        '<input id="option-height" type="number" placeholder="height" min="0" step="1" />' +
        '</label>';      
    }
    else if (index == 4) {
        // Create circle form: id, x, y, radius
        // Combobox selection menu
        layout = '<label class="label-visual" for="option-style">Style: ' +
        '<select id="option-style" onchange="createSpecifiedElementForm()">' +
            // Options: Rectangle, Table, Room, Circle, Line, Text
            '<option></option>' + 
            '<option>Rectangle</option>' +
            '<option>Table</option>' +
            '<option>Room</option>' + 
            '<option selected>Circle</option>' +
            '<option>Line</option>' +
        '</select>' +
        '</label>' +
        // ID
        '<label class="label-visual" for="option-id">ID: ' +
        '<input id="option-id" type="text" placeholder="id" />' +
        '</label>' + 
        // X
        '<label class="label-visual" for="option-x">X: '+
        '<input id="option-x" type="number" placeholder="x" min="0" step="1" />' +
        '</label>' + 
        // Y
        '<label class="label-visual" for="option-y">Y: ' +
        '<input id="option-y" type="number" placeholder="y" min="0" step="1" />' +
        '</label>' +
        // Radius
        '<label class="label-visual" for="option-width">Radius: ' +
        '<input id="option-radius" type="number" placeholder="radius" min="0" step="any" />' +
        '</label>';
    }
    else if (index == 5) {
        // Create line form: id, x, y, x2, y2
        // Combobox selection menu
        layout = '<label class="label-visual" for="option-style">Style: ' +
        '<select id="option-style" onchange="createSpecifiedElementForm()">' +
            // Options: Rectangle, Table, Room, Circle, Line, Text
            '<option></option>' + 
            '<option>Rectangle</option>' +
            '<option>Table</option>' +
            '<option>Room</option>' + 
            '<option>Circle</option>' +
            '<option selected>Line</option>' +
        '</select>' +
        '</label>' +
        // ID
        '<label class="label-visual" for="option-id">ID: ' +
        '<input id="option-id" type="text" placeholder="id" />' +
        '</label>' + 
        // X
        '<label class="label-visual" for="option-x">X: '+
        '<input id="option-x" type="number" placeholder="x" min="0" step="1" />' +
        '</label>' + 
        // Y
        '<label class="label-visual" for="option-y">Y: ' +
        '<input id="option-y" type="number" placeholder="y" min="0" step="1" />' +
        '</label>' +
        // X2
        '<label class="label-visual" for="option-width">X2: ' +
        '<input id="option-x2" type="number" placeholder="x2" min="0" step="1" />' +
        '</label>' + 
        // Y2
        '<label class="label-visual" for="option-height">Y2: ' + 
        '<input id="option-y2" type="number" placeholder="y2" min="0" step="1" />' +
        '</label>';
    }
	
    // Add the create option 
    layout += '<input type="button" class="soflow-btn-l" value="Create" onclick="createNewElement()" />';
    document.getElementById("modify-div").innerHTML = layout;
}

/**
 * This method updates an existing element's properties (the element which is currently selected) 
 * @returns {undefined}
 */
function updateElement() {
    var element = selectedLoc.selectedFloor.selected;
    if (!element) { 
        console.log("Woops, something went wrong with the element update!");
        return;
    }
    
    if (element.type == "rectangle") {
        element.id = document.getElementById("option-id").value;
        element.x = parseInt(document.getElementById("option-x").value);
        element.y = parseInt(document.getElementById("option-y").value);
        element.width = (parseInt(document.getElementById("option-width").value) > element.minWidth 
                            ? parseInt(document.getElementById("option-width").value) 
                            : element.minWidth);
        element.height = (parseInt(document.getElementById("option-height").value) > element.minHeight 
                            ? parseInt(document.getElementById("option-height").value) 
                            : element.minHeight);
        redrawAll();
    }
    if (element.type == "table") {
        element.id = document.getElementById("option-id").value;
        element.x = parseInt(document.getElementById("option-x").value);
        element.y = parseInt(document.getElementById("option-y").value);
        element.width = (parseInt(document.getElementById("option-width").value) > element.minWidth 
                            ? parseInt(document.getElementById("option-width").value) 
                            : element.minWidth);
        element.height = (parseInt(document.getElementById("option-height").value) > element.minHeight 
                            ? parseInt(document.getElementById("option-height").value)
                            : element.minHeight);
        element.number = parseInt(document.getElementById("option-number").value);
        redrawAll();
    }
    if (element.type == "room") {
        element.id = document.getElementById("option-id").value;
        element.roomname = document.getElementById("option-name").value;
        element.capacity = document.getElementById("option-capacity").value;
        element.x = parseInt(document.getElementById("option-x").value);
        element.y = parseInt(document.getElementById("option-y").value);
        element.width = (parseInt(document.getElementById("option-width").value) > element.minWidth
                            ? parseInt(document.getElementById("option-width").value)
                            : element.minWidth);
        element.height = (parseInt(document.getElementById("option-height").value > element.minHeight
                            ? parseInt(document.getElementById("option-height").value)
                            : element.minHeight));
        redrawAll();
    }
    else if (element.type == "circle") {
        element.id = document.getElementById("option-id").value;
        element.x = document.getElementById("option-x").value;
        element.y = document.getElementById("option-y").value;
        element.radius = document.getElementById("option-radius").value;
        redrawAll();
    }
    else if (element.type == "line") {
        element.id = document.getElementById("option-id").value;
        element.x = document.getElementById("option-x").value;
        element.y = document.getElementById("option-y").value;
        element.x2 = document.getElementById("option-x2").value;
        element.y2 = document.getElementById("option-y2").value;
        redrawAll();
    }
    editElement(selectedLoc, selectedLoc.selectedFloor, element);
}

/**
 * This method creates a new rectangle, table, room, circle or line based on the selected element type
 * @returns {undefined}
 */
function createNewElement() {
    if (selectedLoc == null || selectedLoc.selectedFloor == null) {
        alert("[CREATE] No location and/or floor selected!");
        return;
    }
    
    var index = document.getElementById("option-style").selectedIndex;
    if (index == 1) {
        // Create rectangle: id, x, y, width, height
        //var id = document.getElementById("option-id").value;
        var x = document.getElementById("option-x").value;
        var y = document.getElementById("option-y").value;
        var width = document.getElementById("option-width").value;
        var height = document.getElementById("option-height").value;
        //components.push(new rectangle(id, parseInt(x), parseInt(y), parseInt(width), parseInt(height)));
        selectedLoc.selectedFloor.addElement(new Rectangle(id, parseInt(x), parseInt(y), parseInt(width), parseInt(height)))
    }
    else if (index == 2) {
        // Create table: id, x, y, width, height
        //var id = document.getElementById("option-id").value;
        var x = document.getElementById("option-x").value;
        var y = document.getElementById("option-y").value;
        var width = document.getElementById("option-width").value;
        var height = document.getElementById("option-height").value;
        var number = document.getElementById("option-number").value;
        //components.push(new table(id, parseInt(x), parseInt(y), parseInt(width), parseInt(height), parseInt(number)));
        selectedLoc.selectedFloor.addElement(new Table(id, parseInt(x), parseInt(y), parseInt(width), parseInt(height), parseInt(number)));
    }
    else if (index == 3) {
        // Create room: id, name, capacity, x, y, width, height
        //var id = document.getElementById("option-id").value;
        var roomname = document.getElementById("option-name").value;
        var capacity = document.getElementById("option-capacity").value;
        var x = document.getElementById("option-x").value;
        var y = document.getElementById("option-y").value;
        var width = document.getElementById("option-width").value;
        var height = document.getElementById("option-height").value;
        //components.push(new room(id, parseInt(x), parseInt(y), parseInt(width), parseInt(height), capacity, roomname));
        selectedLoc.selectedFloor.addElement(new Room(id, parseInt(x), parseInt(y), parseInt(width), parseInt(height), capacity, roomname));
    }
    else if (index == 4) {
        // Create circle: id, x, y, radius
        //var id = document.getElementById("option-id").value;
        var x = document.getElementById("option-x").value;
        var y = document.getElementById("option-y").value;
        var radius = document.getElementById("option-radius").value;
        //components.push(new circle(id, parseInt(x), parseInt(y), parseInt(radius)));
        selectedLoc.selectedFloor.addElement(new Circle(id, parseInt(x), parseInt(y), parseInt(radius)));
    }
    else if (index == 5) {
        // Create line: id, x, y, x2, y2
        //var id = document.getElementById("option-id").value;
        var x = document.getElementById("option-x").value;
        var y = document.getElementById("option-y").value;
        var x2 = document.getElementById("option-x2").value;
        var y2 = document.getElementById("option-y2").value;
        //components.push(new line(id, parseInt(x), parseInt(y), parseInt(x2), parseInt(y2)));
        selectedLoc.selectedFloor.addElement(new Wall(id, parseInt(x), parseInt(y), parseInt(x2), parseInt(y2)));
    }
    saveElement(selectedLoc, selectedLoc.selectedFloor, element);
    redrawAll();
}

/**
 * Below 2 methods create the forms for adding a new location or adding a new floor.
 * @returns {undefined}
 */
function createLocationForm() {
    var layout = '';
    if (document.getElementById("btn-add-location").value == "+") {
        layout = '<div><span class="soflow-regular-txt">NAME</span>' +
        '<input class="soflow-input-txt" type="text" id="locationform-name" placeholder="name" /></div><br>' +
        '<div><span class="soflow-regular-txt">ADDRESS</span>' +
        '<input class="soflow-input-txt" type="text" id="locationform-address" placeholder="address" /></div><br>' + 		
        '<div><span class="soflow-regular-txt">POSTAL</span>' +
        '<input class="soflow-input-txt" type="text" id="locationform-postal" placeholder="postal" /></div><br>' + 		
        '<div><input type="button" class="soflow-btn" value="SAVE" onclick="newLocation()" /></div>';
        // Set button from + to -
        document.getElementById("btn-add-location").value = "-";
        
        // Disable the button for creating an edit location form
        document.getElementById("btn-edit-location").disabled = true;
        // Disable the location selection menu
        document.getElementById("option-location").disabled = true;
        // Disable the floor selection menu
        document.getElementById("option-floor").disabled = true;
        // Disable the floor form options (add + edit)
        document.getElementById("btn-add-floor").disabled = true;
        document.getElementById("btn-edit-floor").disabled = true;
    }
    else {
        // Set button from - to +
        document.getElementById("btn-add-location").value = "+";
        
        // Disable the button for creating an edit location form
        document.getElementById("btn-edit-location").disabled = false;
        // Disable the location selection menu
        document.getElementById("option-location").disabled = false;
        // Disable the floor selection menu
        document.getElementById("option-floor").disabled = false;
        // Disable the floor form options (add + edit)
        document.getElementById("btn-add-floor").disabled = false;
        document.getElementById("btn-edit-floor").disabled = false;
    }	
    document.getElementById("location-form").innerHTML = layout;
}
function createFloorForm() {
    if (selectedLoc == null) {
        alert("Please select or create a location first.");
        return;
    }
    
    var layout = '';
    if (document.getElementById("btn-add-floor").value == "+") {
        layout = '<div><span class="soflow-regular-txt">FLOORNAME</span>' + 
        '<input class="soflow-input-txt" type="text" id="floorform-floorname" placeholder="floorname" /></div><br>' + 
        '<div><span class="soflow-regular-txt">LEVEL</span>' +
        '<input class="soflow-input-txt" type="text" id="floorform-level" placeholder="level" /></div><br>' +
        '<div><input type="button" class="soflow-btn" value="SAVE" onclick="newFloor()" /></div>';
        // Set button from + to -
        document.getElementById("btn-add-floor").value = "-";
        
        // Disable the button for creating an edit floor form
        document.getElementById("btn-edit-floor").disabled = true;
        // Disable the floor selection menu
        document.getElementById("option-floor").disabled = true;
        // Disable the location selection menu
        document.getElementById("option-location").disabled = true;
        // Disable the location related buttons (add + edit)
        document.getElementById("btn-add-location").disabled = true;
        document.getElementById("btn-edit-location").disabled = true;
    }
    else {
        // Set button from + to -
        document.getElementById("btn-add-floor").value = "+";	
        
        // Enable the button for creating an edit floor form
        document.getElementById("btn-edit-floor").disabled = false;
        // Enable the floor selection menu
        document.getElementById("option-floor").disabled = false;
        // Enable the location selection menu
        document.getElementById("option-location").disabled = false;
        // Enable the location related buttons (add + edit)
        document.getElementById("btn-add-location").disabled = false;
        document.getElementById("btn-edit-location").disabled = false;
    }
    document.getElementById("floor-form").innerHTML = layout;
}

/**
 * Below 2 methods create the forms for editting an existing location or editting an existing floor.
 */
function editLocationForm() {
    var layout = '';
    if (!selectedLoc) {
        alert("Please select a location first.");
        return;
    }
    
    if (editCountLocation == 0) {
        // NAME
        layout = '<div><span class="soflow-regular-txt">NAME</span>' +
        '<input class="soflow-input-txt" type="text" id="locationform-name" placeholder="name" value="' + selectedLoc.name + '" /></div><br>' +
        // ADDRESS
        '<div><span class="soflow-regular-txt">ADDRESS</span>' +
        '<input class="soflow-input-txt" type="text" id="locationform-address" placeholder="address" value="' + selectedLoc.address + '" /></div><br>' + 		
        // POSTAL
        '<div><span class="soflow-regular-txt">POSTAL</span>' +
        '<input class="soflow-input-txt" type="text" id="locationform-postal" placeholder="postal" value="' + selectedLoc.postal + '"/></div><br>' + 		
        // Buttons
        '<div><input type="button" class="soflow-btn" value="UPDATE" onclick="updateLocation()" />'+ 
        '<input type="button" class="soflow-btn" value="DELETE" onclick="deleteLocation()" /></div>';
        // Set editCountLocation to 1
        editCountLocation = 1;
        
        // Disable the button for creating a new location form
        document.getElementById("btn-add-location").disabled = true;
        // Disable the location selection menu
        document.getElementById("option-location").disabled = true;
        // Disable the floor selection menu
        document.getElementById("option-floor").disabled = true;
        // Disable the floor form options (add + edit)
        document.getElementById("btn-add-floor").disabled = true;
        document.getElementById("btn-edit-floor").disabled = true;
    } else {
        // Set editCountLocation to 0
        editCountLocation = 0;
        
        // Disable the button for creating a new location form
        document.getElementById("btn-add-location").disabled = false;
        // Disable the location selection menu
        document.getElementById("option-location").disabled = false;
        // Disable the floor selection menu
        document.getElementById("option-floor").disabled = false;
        // Disable the floor form options (add + edit)
        document.getElementById("btn-add-floor").disabled = false;
        document.getElementById("btn-edit-floor").disabled = false;
    }
    document.getElementById("location-form").innerHTML = layout;
}
function editFloorForm() {
    var layout = '';
    if (!selectedLoc || !selectedLoc.selectedFloor) {
        alert("Please select a location and floor first.");
        return;
    }
    
    var floor = selectedLoc.selectedFloor;
    if (editCountFloor == 0) {
        // NAME
        layout = '<div><span class="soflow-regular-txt">NAME</span>' +
        '<input class="soflow-input-txt" type="text" id="floorform-name" placeholder="name" value="' + floor.name + '" /></div><br>' +
        // LEVEL
        '<div><span class="soflow-regular-txt">LEVEL</span>' + 
        '<input class="soflow-input-txt" type="text" id="floorform-level" placeholder="level" value="' + floor.level + '" /></div><br>' + 
        // BUTTONS
        '<div><input type="button" class="soflow-btn" value="UPDATE" onclick="updateFloor()" />'+ 
        '<input type="button" class="soflow-btn" value="DELETE" onclick="deleteFloor()" /></div>';
        // Set editCountFloor to 1
        editCountFloor = 1;
        
        // Disable the button for creating a new floor form
        document.getElementById("btn-add-floor").disabled = true;
        // Disable the floor selection menu
        document.getElementById("option-floor").disabled = true;
        // Disable the location selection menu
        document.getElementById("option-location").disabled = true;
        // Disable the location related buttons (add + edit)
        document.getElementById("btn-add-location").disabled = true;
        document.getElementById("btn-edit-location").disabled = true;
    } else {
        // Set editCountFloor to 0
        editCountFloor = 0;
        
        // Enable the button for creating a new floor form
        document.getElementById("btn-add-floor").disabled = false;
        // Enable the floor selection menu
        document.getElementById("option-floor").disabled = false;
        // Enable the location selection menu
        document.getElementById("option-location").disabled = false;
        // Enable the location related buttons (add + edit)
        document.getElementById("btn-add-location").disabled = false;
        document.getElementById("btn-edit-location").disabled = false;
    }
    document.getElementById("floor-form").innerHTML = layout;
}

mapCreationOptions();