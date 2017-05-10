var draw = document.getElementById("option-create");
//var resize = document.getElementById("option-resize");
var modify = document.getElementById("option-edit");
//var file = document.getElementById("option-file");

function mapCreationOptions() {
    if (draw.checked) {
        // Open div container
        document.getElementById("dynamic-options").innerHTML = '<div class="radio-wrapper-m">' +
        // Radio button: Rectangle
        '<input id="option-rectangle" type="radio" name="drawing-objects" value="rectangle" checked>' +
        '<label class="label-option" for="option-rectangle"><img src="/images/map/rectangle.png" width="64px" height="64px" /></label>' +
        // Radio button: Table
        '<input id="option-table" type="radio" name="drawing-objects" value="table">' +
        '<label class="label-option" for="option-table"><img src="/images/map/table.png" width="64px" height="64px" /></label>' +
        // Radio button: Circle
        '<input id="option-circle" type="radio" name="drawing-objects" value="circle">' +
        '<label class="label-option" for="option-circle"><img src="/images/map/circle.png" width="64px" height="64px" /></label>' +
        // Radio button: Line
        '<input id="option-line" type="radio" name="drawing-objects" value="line">' +
        '<label class="label-option" for="option-line"><img src="/images/map/line.png" width="64px" height="64px" /></label>' +
        // Radio button: Text
        '<input id="option-text" type="radio" name="drawing-objects" value="text">' +
        '<label class="label-option" for="option-text"><img src="/images/map/txt.png" width="64px" height="64pxpx" /></label>' +
        // Close div container
        '</div>';
    }
    /*else if (resize.checked) {
        if (selected == null) {
            document.getElementById("dynamic-options").innerHTML = "Nothing to see here, move on..";
        }
        else if (selected.type == "rectangle") {
            document.getElementById("dynamic-options").innerHTML = createRectangleResize();
        }
        else if(selected.type == "table") {
            document.getElementById("dynamic-options").innerHTML = createTableResize();
        }
        else if (selected.type == "circle") {
            document.getElementById("dynamic-options").innerHTML = createCircleResize();
        }
        else if (selected.type == "line") {
            document.getElementById("dynamic-options").innerHTML = createLineResize();
        }
        else if (selected.type == "text") {
            // TO DO
        }
    }*/
    else if (modify.checked) {
        // Open div container
        if (selected == null) {
            document.getElementById("dynamic-options").innerHTML = createNewModifications();
        }
        else if (selected.type == "rectangle") {
            document.getElementById("dynamic-options").innerHTML = createRectangleModifications();
            document.getElementById("option-style").selectedIndex = 1;
        }
        else if (selected.type == "table") {
            document.getElementById("dynamic-options").innerHTML = createTableModifications();
            document.getElementById("option-style").selectedIndex = 2
        }
        else if (selected.type == "circle") {
            document.getElementById("dynamic-options").innerHTML = createCircleModifications();
            document.getElementById("option-style").selectedIndex = 3;
        }
        else if (selected.type == "line") {
            document.getElementById("dynamic-options").innerHTML = createLineModifications();
            document.getElementById("option-style").selectedIndex = 4;
        }
        else if (selected.type == "text") {
            document.getElementById("dynamic-options").innerHTML = createTextModifications();
            document.getElementById("option-style").selectedIndex = 5;
        }
    }
    else if (file.checked) {
        document.getElementById("dynamic-options").innerHTML = createFileOptions();
    }
}

function createCircleResize() {
    var layout = '<div class="radio-wrapper-m">' +
    // Type
    '<label class="label-visual">Type: <br>' + selected.type + '</label>' +  
    // ID
    '<label class="label-visual">ID: <br>' + selected.id + '</label>' +
    // X
    '<label class="label-visual">X: <br>' + selected.x + '</label>' +
    // Y
    '<label class="label-visual">Y: <br>' + selected.y + '</label>' +
    // Radius
    '<label class="label-visual">Radius: <br>' + selected.radius + '</label>' +
    // Close container
    '</div>';
    return layout;
}
function createRectangleResize() {
    var layout = '<div class="radio-wrapper-m">' +
    // Type
    '<label class="label-visual">Type: <br>' + selected.type + '</label>' +  
    // ID
    '<label class="label-visual">ID: <br>' + selected.id + '</label>' +
    // X
    '<label class="label-visual">X: <br>' + selected.x + '</label>' +
    // Y
    '<label class="label-visual">Y: <br>' + selected.y + '</label>' +
    // Width
    '<label class="label-visual">Width: <br>' + selected.width + '</label>' +
    // Height
    '<label class="label-visual">Height: <br>' + selected.height + '</label>' +
    // Close container
    '</div>';
    return layout;
}
function createLineResize() {
    var layout = '<div class="radio-wrapper-m">' +
    // Type
    '<label class="label-visual">Type: <br>' + selected.type + '</label>' +  
    // ID
    '<label class="label-visual">ID: <br>' + selected.id + '</label>' +
    // X
    '<label class="label-visual">X: <br>' + selected.x + '</label>' +
    // Y
    '<label class="label-visual">Y: <br>' + selected.y + '</label>' +
    // X2
    '<label class="label-visual">X2: <br>' + selected.x2 + '</label>' +
    // Y2
    '<label class="label-visual">Y2: <br>' + selected.y2 + '</label>' +
    // Close container
    '</div>';
    return layout;
}
function createTableResize() {
    var layout = '<div class="radio-wrapper-m">' + 
    // Type
    '<label class="label-visual">Type: <br>' + selected.type + '</label>' +  
    // ID
    '<label class="label-visual">ID: <br>' + selected.id + '</label>' +
    // X
    '<label class="label-visual">X: <br>' + selected.x + '</label>' +
    // Y
    '<label class="label-visual">Y: <br>' + selected.y + '</label>' +
    // Width
    '<label class="label-visual">Width: <br>' + selected.width + '</label>' +
    // Height
    '<label class="label-visual">Height: <br>' + selected.height + '</label>' +
    // Number
    '<label class="label-visual">Number: <br>' + selected.number + '</label>' +
    // Close container
    '</div>';
    return layout;
}

function createComboboxStyleSelection(isElementSelected) {
    var layout = '<label class="label-visual" for="option-style">Style: ' +
    '<select id="option-style" onchange="createSpecifiedElementForm()" ' + (isElementSelected ? ' disabled' : '')  + '>' +
            // Options: Rectangle, Circle, Line, Text, Optionally Table as well
            '<option></option>' + 
            '<option>Rectangle</option>' +
            '<option>Table</option>' +
            '<option>Circle</option>' +
            '<option>Line</option>' +
            '<option>Text</option>' +
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
function createCircleModifications() {
    // Open div container
    var layout = '<div class="radio-wrapper-m">' +
    // Combobox Style selection - element is selected so parameter = true
    createComboboxStyleSelection(true) +
    // ID
    '<label class="label-visual" for="option-id">ID: ' +
    '<input id="option-id" type="text" placeholder="id" value="' + selected.id + '"/>' +
    '</label>' + 
    // X
    '<label class="label-visual" for="option-x">X: '+
    '<input id="option-x" type="text" placeholder="x" value="' + selected.x + '" />' +
    '</label>' + 
    // Y
    '<label class="label-visual" for="option-y">Y: ' +
    '<input id="option-y" type="text" placeholder="y" value="' + selected.y + '" />' +
    '</label>' +
    // Width
    '<label class="label-visual" for="option-radius">Radius: ' +
    '<input id="option-radius" type="text" placeholder="radius" value="' + selected.radius + '" />' +
    '</label>' + 
    // Update option
    createUpdateModification();
    // Close div container
    '</div>';
    return layout;
}
function createRectangleModifications() {
    // Open div container
    var layout = '<div class="radio-wrapper-m">' +
    // Combox Style selection - element is selected so parameter = true
    createComboboxStyleSelection(true) +
    // ID
    '<label class="label-visual" for="option-id">ID: ' +
    '<input id="option-id" type="text" placeholder="id" value="' + selected.id + '" />' +
    '</label>' + 
    // X
    '<label class="label-visual" for="option-x">X: '+
    '<input id="option-x" type="text" placeholder="x" value="' + selected.x + '" />' +
    '</label>' + 
    // Y
    '<label class="label-visual" for="option-y">Y: ' +
    '<input id="option-y" type="text" placeholder="y" value="' + selected.y + '" />' +
    '</label>' +
    // Width
    '<label class="label-visual" for="option-width">Width: ' +
    '<input id="option-width" type="text" placeholder="width" value="' + selected.width + '"/>' +
    '</label>' + 
    // Height
    '<label class="label-visual" for="option-height">Height: ' + 
    '<input id="option-height" type="text" placeholder="height" value="' + selected.height + '" />' +
    '</label>' +
    // Update option
    createUpdateModification();
    // Close div container
    '</div>';
    return layout;
}
function createTableModifications() {
    // Open div container
    var layout = '<div class="radio-wrapper-m">' +
    // Combox Style selection - element is selected so parameter = true
    createComboboxStyleSelection(true) +
    // ID
    '<label class="label-visual" for="option-id">ID: ' +
    '<input id="option-id" type="text" placeholder="id" value="' + selected.id + '" />' +
    '</label>' + 
    // X
    '<label class="label-visual" for="option-x">X: '+
    '<input id="option-x" type="text" placeholder="x" value="' + selected.x + '" />' +
    '</label>' + 
    // Y
    '<label class="label-visual" for="option-y">Y: ' +
    '<input id="option-y" type="text" placeholder="y" value="' + selected.y + '" />' +
    '</label>' +
    // Width
    '<label class="label-visual" for="option-width">Width: ' +
    '<input id="option-width" type="text" placeholder="width" value="' + selected.width + '"/>' +
    '</label>' + 
    // Height
    '<label class="label-visual" for="option-height">Height: ' + 
    '<input id="option-height" type="text" placeholder="height" value="' + selected.height + '" />' +
    '</label>' +
    // Number
    '<label class="label-visual" for="option-number">Number: ' +
    '<input id="option-number" type="text" placeholder="number" value="' + selected.number + '" />' +
    '</label>' +
    // Update option
    createUpdateModification();
    // Close div container
    '</div>';
    return layout;
}
function createTextModifications() {
    // Open div container
    var layout = '<div class="radio-wrapper-m">' +
    // Combox Style selection - element is selected so parameter = true
    createComboboxStyleSelection(true) +	
    // ID
    '<label class="label-visual" for="option-id">ID: ' +
    '<input id="option-id" type="text" placeholder="id" />' +
    '</label>' + 
    // X
    '<label class="label-visual" for="option-x">X: '+
    '<input id="option-x" type="text" placeholder="x" />' +
    '</label>' + 
    // Y
    '<label class="label-visual" for="option-y">Y: ' +
    '<input id="option-y" type="text" placeholder="y" />' +
    '</label>' +
    // Width
    '<label class="label-visual" for="option-radius"> ' +
    '<input id="option-radius" type="text" placeholder="width" />' +
    '</label>' + 
    // Update option
    createUpdateModification();
    // Close div container
    '</div>';
    return layout;
}
function createLineModifications() {
	// Open div container
	var layout = '<div class="radio-wrapper-m">' +
	// Combox Style selection - element is selected so parameter = true
	createComboboxStyleSelection(true) +	
	// ID
	'<label class="label-visual" for="option-id">ID: ' +
	'<input id="option-id" type="text" placeholder="id" value="' + selected.id + '" />' +
	'</label>' + 
	// X
	'<label class="label-visual" for="option-x">X: '+
	'<input id="option-x" type="text" placeholder="x" value="' + selected.x + '" />' +
	'</label>' + 
	// Y
	'<label class="label-visual" for="option-y">Y: ' +
	'<input id="option-y" type="text" placeholder="y" value="' + selected.y + '" />' +
	'</label>' +
	// X2
	'<label class="label-visual" for="option-x2">X2: ' +
	'<input id="option-x2" type="text" placeholder="x2" value="' + selected.x2 + '" />' +
	'</label>' + 
	// Y2
	'<label class="label-visual" for="option-y2">Y2: ' +
	'<input id="option-y2" type="text" placeholder="y2" value="' + selected.y2 + '" />' +
	'</label>' +
	// Update option
	createUpdateModification();
	// Close div container
	'</div>';
	return layout;
}

function createFileOptions() {
    var layout = '<div class="radio-wrapper-m">' + 
    // Save (DB)
    '<label class="label-visual" onclick="saveMapToDB()">Save</label>' +
    // Load (DB)
    '<label class="label-visual" onclick="loadMapFromDB()">Load</label>' +
    // Optional: Add other options
    // Close container
    '</div>';
    return layout;
}
function createUpdateModification() {
    var layout = '<label class="label-visual" onclick="updateElement()">Update</label>';
    return layout;
}

function createSpecifiedElementForm() {
    var index = document.getElementById("option-style").selectedIndex;
    var layout;
    if (index == 1) {
        // Create rectangle form: id, x, y, width, height
        // Combobox selection menu
        layout = '<label class="label-visual" for="option-style">Style: ' +
        '<select id="option-style" onchange="createSpecifiedElementForm()">' +
            // Options: Rectangle, Circle, Line, Text, Optionally Table as well
            '<option></option>' + 
            '<option selected>Rectangle</option>' +
            '<option>Table</option>' +
            '<option>Circle</option>' +
            '<option>Line</option>' +
            '<option>Text</option>' +
        '</select>' +
        '</label>' +
        // ID
        '<label class="label-visual" for="option-id">ID: ' +
        '<input id="option-id" type="text" placeholder="id" />' +
        '</label>' + 
        // X
        '<label class="label-visual" for="option-x">X: '+
        '<input id="option-x" type="text" placeholder="x" />' +
        '</label>' + 
        // Y
        '<label class="label-visual" for="option-y">Y: ' +
        '<input id="option-y" type="text" placeholder="y" />' +
        '</label>' +
        // Width
        '<label class="label-visual" for="option-width">Width: ' +
        '<input id="option-width" type="text" placeholder="width" />' +
        '</label>' + 
        // Height
        '<label class="label-visual" for="option-height">Height: ' + 
        '<input id="option-height" type="text" placeholder="height" />' +
        '</label>';
    }
    else if (index == 2) {
        // Create table form: id, x, y, width, height, number
        // combobox selection menu
        layout = '<label class="label-visual" for="option-style">Style: ' +
        '<select id="option-style" onchange="createSpecifiedElementForm()">' +
        // Options: Rectangle, Circle, Line, Text, Optionally Table as well
            '<option></option>' + 
            '<option>Rectangle</option>' +
            '<option selected>Table</option>' +
            '<option>Circle</option>' +
            '<option>Line</option>' +
            '<option>Text</option>' +
        '</select>' +
        '</label>' + 
        // ID
        '<label class="label-visual" for="option-id">ID: ' +
        '<input id="option-id" type="text" placeholder="id" />' +
        '</label>' + 
        // X
        '<label class="label-visual" for="option-x">X: '+
        '<input id="option-x" type="text" placeholder="x" />' +
        '</label>' + 
        // Y
        '<label class="label-visual" for="option-y">Y: ' +
        '<input id="option-y" type="text" placeholder="y" />' +
        '</label>' +
        // Width
        '<label class="label-visual" for="option-width">Width: ' +
        '<input id="option-width" type="text" placeholder="width" />' +
        '</label>' + 
        // Height
        '<label class="label-visual" for="option-height">Height: ' + 
        '<input id="option-height" type="text" placeholder="height" />' +
        '</label>' + 
        // Number
        '<label class="label-visual" for="option-number">Number: ' +
        '<input id="option-number" type="text" placeholder="number" />' +
        '</label>';
    }
    else if (index == 3) {
        // Create circle form: id, x, y, radius
        // Combobox selection menu
        layout = '<label class="label-visual" for="option-style">Style: ' +
        '<select id="option-style" onchange="createSpecifiedElementForm()">' +
        // Options: Rectangle, Circle, Line, Text, Optionally Table as well
            '<option></option>' + 
            '<option>Rectangle</option>' +
            '<option>Table</option>' +
            '<option selected>Circle</option>' +
            '<option>Line</option>' +
            '<option>Text</option>' +
        '</select>' +
        '</label>' +
        // ID
        '<label class="label-visual" for="option-id">ID: ' +
        '<input id="option-id" type="text" placeholder="id" />' +
        '</label>' + 
        // X
        '<label class="label-visual" for="option-x">X: '+
        '<input id="option-x" type="text" placeholder="x" />' +
        '</label>' + 
        // Y
        '<label class="label-visual" for="option-y">Y: ' +
        '<input id="option-y" type="text" placeholder="y" />' +
        '</label>' +
        // Radius
        '<label class="label-visual" for="option-width">Radius: ' +
        '<input id="option-radius" type="text" placeholder="radius" />' +
        '</label>';
    }
    else if (index == 4) {
        // Create line form: id, x, y, x2, y2
        // Combobox selection menu
        layout = '<label class="label-visual" for="option-style">Style: ' +
        '<select id="option-style" onchange="createSpecifiedElementForm()">' +
        // Options: Rectangle, Circle, Line, Text, Optionally Table as well
            '<option></option>' + 
            '<option>Rectangle</option>' +
            '<option>Table</option>' +
            '<option>Circle</option>' +
            '<option selected>Line</option>' +
            '<option>Text</option>' +
        '</select>' +
        '</label>' +
        // ID
        '<label class="label-visual" for="option-id">ID: ' +
        '<input id="option-id" type="text" placeholder="id" />' +
        '</label>' + 
        // X
        '<label class="label-visual" for="option-x">X: '+
        '<input id="option-x" type="text" placeholder="x" />' +
        '</label>' + 
        // Y
        '<label class="label-visual" for="option-y">Y: ' +
        '<input id="option-y" type="text" placeholder="y" />' +
        '</label>' +
        // X2
        '<label class="label-visual" for="option-width">X2: ' +
        '<input id="option-x2" type="text" placeholder="x2" />' +
        '</label>' + 
        // Y2
        '<label class="label-visual" for="option-height">Y2: ' + 
        '<input id="option-y2" type="text" placeholder="y2" />' +
        '</label>';
    }
    else if (index == 5) {
        // Create text form: not just implemented
        // Combobox selection menu
        layout = '<label class="label-visual" for="option-style">Style: ' +
        '<select id="option-style" onchange="createSpecifiedElementForm()">' +
            // Options: Rectangle, Circle, Line, Text, Optionally Table as well
            '<option></option>' + 
            '<option>Rectangle</option>' +
            '<option>Table</option>' +
            '<option>Circle</option>' +
            '<option>Line</option>' +
            '<option selected>Text</option>' +
        '</select>' +
        '</label>';
    }
	
    // Add the create option 
    layout += '<input type="button" class="soflow-btn-l" value="Create" onclick="createNewElement()" />';
    document.getElementById("modify-div").innerHTML = layout;
}

function updateElement() {
    if (selected.type == "rectangle") {
        selected.id = document.getElementById("option-id").value;
        selected.x = parseInt(document.getElementById("option-x").value);
        selected.y = parseInt(document.getElementById("option-y").value);
        selected.width = (parseInt(document.getElementById("option-width").value) > selected.minWidth 
                            ? selected.minWidth 
                            : parseInt(document.getElementById("option-width").value));
        selected.height = (parseInt(document.getElementById("option-height").value) > selected.minHeight 
                            ? selected.minHeight 
                            : parseInt(document.getElementById("option-height").value));
        redrawAll();
    }
    if (selected.type == "table") {
        selected.id = document.getElementById("option-id").value;
        selected.x = parseInt(document.getElementById("option-x").value);
        selected.y = parseInt(document.getElementById("option-y").value);
        selected.width = (parseInt(document.getElementById("option-width").value) > selected.minWidth 
                            ? selected.minWidth 
                            : parseInt(document.getElementById("option-width").value));
        selected.height = (parseInt(document.getElementById("option-height").value) > selected.minHeight 
                            ? selected.minHeight 
                            : parseInt(document.getElementById("option-height").value));
        selected.number = parseInt(document.getElementById("option-number").value);
        redrawAll();
    }
    else if (selected.type == "circle") {
        selected.id = document.getElementById("option-id").value;
        selected.x = document.getElementById("option-x").value;
        selected.y = document.getElementById("option-y").value;
        selected.radius = document.getElementById("option-radius").value;
        redrawAll();
    }
    else if (selected.type == "line") {
        selected.id = document.getElementById("option-id").value;
        selected.x = document.getElementById("option-x").value;
        selected.y = document.getElementById("option-y").value;
        selected.x2 = document.getElementById("option-x2").value;
        selected.y2 = document.getElementById("option-y2").value;
        redrawAll();
    }
    else if (selected.type == "text") {
        // not implemented
    }
}

function createNewElement() {
    var index = document.getElementById("option-style").selectedIndex;
    if (index == 1) {
        // Create rectangle: id, x, y, width, height
        var id = document.getElementById("option-id").value;
        var x = document.getElementById("option-x").value;
        var y = document.getElementById("option-y").value;
        var width = document.getElementById("option-width").value;
        var height = document.getElementById("option-height").value;
        components.push(new rectangle(id, parseInt(x), parseInt(y), parseInt(width), parseInt(height)));
    }
    else if (index == 2) {
        // Create table: id, x, y, width, height
        var id = document.getElementById("option-id").value;
        var x = document.getElementById("option-x").value;
        var y = document.getElementById("option-y").value;
        var width = document.getElementById("option-width").value;
        var height = document.getElementById("option-height").value;
        var number = document.getElementById("option-number").value;
        components.push(new table(id, parseInt(x), parseInt(y), parseInt(width), parseInt(height), parseInt(number)));
    }
    else if (index == 3) {
        // Create circle: id, x, y, radius
        var id = document.getElementById("option-id").value;
        var x = document.getElementById("option-x").value;
        var y = document.getElementById("option-y").value;
        var radius = document.getElementById("option-radius").value;
        components.push(new circle(id, parseInt(x), parseInt(y), parseInt(radius)));
    }
    else if (index == 4) {
        // Create line: id, x, y, x2, y2
        var id = document.getElementById("option-id").value;
        var x = document.getElementById("option-x").value;
        var y = document.getElementById("option-y").value;
        var x2 = document.getElementById("option-x2").value;
        var y2 = document.getElementById("option-y2").value;
        components.push(new line(id, parseInt(x), parseInt(y), parseInt(x2), parseInt(y2)));
    }
    else if (index == 5) {
        // unimplemented
    }
    redrawAll();
}

mapCreationOptions();