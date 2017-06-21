// Requires:
// - Firebase
// - mapcreation.jsp
// - mapoptions.js for dynamic button elements
// - mapcreation.js for elements list which are displayed on the mapcreation canvas

// Get a reference to the database service
var database = firebase.database();
var v;
var f;

/* Saving */
/**
 * This method saves a new element to firebase. The element will be assigned to the right location and floor combination.
 * @param {type} location 
 * @param {type} floor  
 * @param {type}  element
 * @returns {undefined} void
 */
function saveElement(location, floor, element) {
    if (!element.id) {
        element.id = generateRandomId();
    }
    console.log("[saveElement] Location: " + location);
    console.log("[saveElement] Floor: " + floor);
    console.log("[saveElement] Element: " + element);
    //Check element and save it
    if (element.type === 'rectangle') {
        database.ref('Map/' + location.id + '/Floors/' + floor.id + "/Elements/" + element.id).set({
            X: element.x,
            Y: element.y,
            Width: element.width,
            Height: element.height,
            Type: element.type
        });
    } else if (element.type === 'room') {
        database.ref('Map/' + location.id + '/Floors/' + floor.id + "/Elements/" + element.id).set({
            X: element.x,
            Y: element.y,
            Width: element.width,
            Height: element.height,
            Name: element.roomname,
            Capacity: element.capacity,
            Type: element.type
        });
    } else if (element.type === 'circle') {
        database.ref('Map/' + location.id + '/Floors/' + floor.id + "/Elements/" + element.id).set({
            X: element.x,
            Y: element.y,
            Radius: element.radius,
            Type: element.type
        });
    } else if (element.type === 'table') {
        database.ref('Map/' + location.id + '/Floors/' + floor.id + "/Elements/" + element.id).set({
            X: element.x,
            Y: element.y,
            Width: element.width,
            Height: element.height,
            Number: element.number,
            Type: element.type
        });
    } else  {
        database.ref('Map/' + location.id + '/Floors/' + floor.id + "/Elements/" + element.id).set({
            X: element.x,
            Y: element.y,
            X2: element.x2,
            Y2: element.y2,
            Type: element.type
        });
    }
}
/**
 * This method saves a new location to firebase.
 * @param {type} location
 * @returns {undefined} void
 */
function saveLocation(location) {
    if (!location.id) {
        location.id = generateRandomId();
    }
    console.log("Location: " + location);
    database.ref('Map/' + location.id).set({
        Name: location.name,
        Address: location.address,
        Postal: location.postal
    });
}
/**
 * This method saves a new floor to firebase. The floor will be assigned to an existing location.
 * @param {type} location
 * @param {type} floor
 * @returns {undefined} void
 */
function saveFloor(location, floor) {
    if (!floor.id) {
        floor.id = generateRandomId();
    }
    console.log("Location: " + location + "Floor: " + floor);
    database.ref('Map/' + location.id + "/Floors/" + floor.id).set({
        Name: floor.name,
        Level: floor.level
    });
}

/* Loading */
/**
 * This method loads the map from firebase.
 * All locations will be loaded. The locations will be shown in a selection menu.
 * Within a location all assigned floors will be loaded. The floors will be shown in a selection menu (dynamically loaded by location selection)
 * Within a floor all assigned elements will be loaded. The elements will be shown on the map.
 * If several locations have been loaded from firebase, the very first location will be selected along with the first floor.
 * The elements for the selected location and floor combination will be shown on the map.
 * @returns {undefined} void
 */
function loadMap() {
    //Search foor locations
    console.log("Loading map!");
    var locRef = database.ref('Map');
    locRef.once("value", function(snapshot) {
        snapshot.forEach(function(loc) {
            var id = loc.key.toString();
            var address = loc.val().Address;
            var name = loc.val().Name;
            var postal = loc.val().Postal;
            v = new Venue(id, name, postal, address);
            console.log("New venue? " + v);
            
            //Search for floors
            var floorRef = snapshot.child(v.id + "/Floors");
            floorRef.forEach(function(floor) {
                var id = floor.key.toString();
                var level = floor.val().Level;
                var name = floor.val().Name;
                f = new Floor(id, name, level);
                console.log("New floor? " + f);

                //Search for elements
                var elemRef = snapshot.child(v.id + "/Floors/" + f.id + "/Elements");
                elemRef.forEach(function(elem) {
                    var e = loadElement(elem);
                    console.log("New element? " + e);
                    f.addElement(e);
                });
                v.addFloor(f);
            });
            locations.push(v);
        });
        if (locations.length > 0) {
            selectedLoc = locations[0];
            loadLocationList();
            loadFloorList();
            if (selectedLoc.floors.length > 0) {
                selectedLoc.selectedFloor = selectedLoc.floors[0];
                reloadItemMenu(createItemMenu(selectedLoc.selectedFloor));
            }
            redrawAll();
        }
    });
}
/**
 * This method acts as a help function for receiving the right type of element based on it's type.
 * @param {type} elem
 * @returns {Rectangle|Room|Wall|Table|Circle}
 */
function loadElement(elem) {
    var id = elem.key.toString();
    var type = elem.val().Type;
    if (type === 'rectangle') {
        var x = elem.val().X;
        var y = elem.val().Y;
        var width = elem.val().Width;
        var height = elem.val().Height;
        return new Rectangle(id, x, y, width, height);
    } else if (type === 'room') {
        var x = elem.val().X;
        var y = elem.val().Y;
        var width = elem.val().Width;
        var height = elem.val().Height;
        var capacity = elem.val().Capacity;
        var name = elem.val().Name;
        return new Room(id, x, y, width, height, capacity, name);
    } else if (type === 'circle') {
        var x = elem.val().X;
        var y = elem.val().Y;
        var r = elem.val().Radius;
        return new Circle(id, x, y, r);
    } else if (type === 'table') {
        var x = elem.val().X;
        var y = elem.val().Y;
        var width = elem.val().Width;
        var height = elem.val().Height;
        var number = elem.val().Number;
        return new Table(id, x, y, width, height, number);
    } else  {
        var x = elem.val().X;
        var y = elem.val().Y;
        var x2 = elem.val().X2;
        var y2 = elem.val().Y2;
        return new Wall(id, x, y, x2, y2);
    }
}

/* Editting */
/**
 * This method updates an element's information in firebase on the given location and floor combination.
 * @param {type} location
 * @param {type} floor
 * @param {type} element
 * @returns {undefined} void
 */
function editElement(location, floor, element) { 
    var elemRef = database.ref('Map/' + location.id + '/Floors/' + floor.id + '/Elements/' + element.id);
    elemRef.once('value', function(snapshot) {
        if (snapshot.val() === null) {
            /* does not exist */
        } else {
            if (element.type === 'rectangle') {
                snapshot.ref.update({
                    X: element.x,
                    Y: element.y,
                    Width: element.width,
                    Height: element.height,
                    Type: element.type 
                });
            } 
            else if (element.type === 'room') {
                snapshot.ref.update({
                    X: element.x,
                    Y: element.y,
                    Width: element.width,
                    Height: element.height,
                    Name: element.roomname,
                    Capacity: element.capacity,
                    Type: element.type 
                });
            } 
            else if (element.type === 'circle') {
                snapshot.ref.update({
                    X: element.x,
                    Y: element.y,
                    Radius: element.radius,
                    Type: element.type
                });
            }
            else if (element.type === 'table') {
                snapshot.ref.update({
                    X: element.x,
                    Y: element.y,
                    Width: element.width,
                    Height: element.height,
                    Number: element.number,
                    Type: element.type
                });
            }
            else {
                snapshot.ref.update({
                    X: element.x,
                    Y: element.y,
                    X2: element.x2,
                    Y2: element.y2,
                    Type: element.type 
                });
            }
        }
    });
}
/**
 * This method updates a floor's information in firebase on the given location.
 * @param {type} location
 * @param {type} floor
 * @returns {undefined} void
 */
function editFloor(location, floor) {
    var floorRef = database.ref('Map/' + location.id + '/Floors/' + floor.id);
    floorRef.once('value', function(snapshot) { 
        if (snapshot.val() === null) {
            /* does not exist */
        } else {
            snapshot.ref.update({
               Name: floor.name,
               Level: floor.level
            });
        }
    });
}
/**
 * This method updates a location's information in firebase.
 * @param {type} location
 * @returns {undefined} void
 */
function editLocation(location) {
    var locRef = database.ref('Map/' + location.id);
    locRef.once('value', function(snapshot) {
        if( snapshot.val() === null ) {
            /* does not exist */
        } else {
            snapshot.ref.update({
                Name: location.name,
                Address: location.address,
                Postal: location.postal        
            });
        }
    });
}

/* Removing */
/**
 * This method removes an element from firebase from the given location and floor combination.
 * @param {type} location
 * @param {type} floor
 * @param {type} element
 * @returns {undefined} void
 */
function removeElement(location, floor, element) {
    var elemRef = database.ref('Map/' + location.id + '/Floors/' + floor.id + '/Elements/' + element.id);
    elemRef.once('value', function(snapshot) {
        if (snapshot.val() === null) {
            /* does not exist */
        } else {
            snapshot.ref.remove();
            console.log("Removed: " + snapshot.val());
        }
    });
}
/**
 * This method removes a location from firebase.
 * @param {type} location
 * @returns {undefined} void
 */
function removeLocation(location) {
    var locRef = database.ref('Map/' + location.id);
    locRef.once('value', function(snapshot) {
       if (snapshot.val() === null) {
           /* does not exist */
       } else {
           snapshot.ref.remove();
       }
    });
}
/**
 * This method removes a floor from firebase from the given location.
 * @param {type} location
 * @param {type} floor
 * @returns {undefined}
 */
function removeFloor(location, floor) {
    var floorRef = database.ref('Map/' + location.id + '/Floors/' + floor.id);
    floorRef.once('value', function(snapshot) {
       if (snapshot.val() === null) {
           /* does not exist */
       } else {
           snapshot.ref.remove();
       } 
    });
}

/* Other */
/**
 * This method generates a random 10-character long ID which.
 * @returns {String}
 */
function generateRandomId() {
    var text = "";
    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    for (var i = 0; i < 10; i++) {
        text += possible.charAt(Math.floor(Math.random() * possible.length));
    }
    console.log("Newly generated id: " + text);
    return text;
}

/**
 * When the page loads the loadMap() method will be executed. This will only run once on startup.
 * @param {type} param
 */
$(document).ready(loadMap());
