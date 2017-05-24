// Requires:
// - Firebase
// - mapcreation.jsp
// - mapoptions.js for dynamic button elements
// - mapcreation.js for elements list which are displayed on the mapcreation canvas

// Get a reference to the database service
var database = firebase.database();

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

function generateRandomId() {
    var text = "";
    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    for (var i = 0; i < 10; i++) {
        text += possible.charAt(Math.floor(Math.random() * possible.length));
    }
    console.log("Newly generated id: " + text);
    return text;
}
