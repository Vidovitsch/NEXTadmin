// Requires:
// - Firebase
// - mapcreation.jsp
// - mapoptions.js for dynamic button elements
// - mapcreation.js for elements list which are displayed on the mapcreation canvas

// Get a reference to the database service
var database = firebase.database();
var v;
var f;

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

function loadLocations() {
    //Search foor locations
    var locRef = database.ref('Map');
    locRef.once("value", function(snapshot) {
        snapshot.forEach(function(loc) {
            var id = loc.key.toString();
            var address = loc.val().Address;
            var name = loc.val().Name;
            var postal = loc.val().Postal;
            v = new Venue(id, name, postal, address);
            
            //Search for floors
            var floorRef = database.ref('Map/' + id + '/Floors');
            floorRef.once("value", function(snapshot) {
                snapshot.forEach(function(floor) {
                    var id = floor.key.toString();
                    var level = loc.val().Level;
                    var name = loc.val().Name;
                    f = new Floor(id, name, level);
                    
                    //Search for elements
                    var elemRef = database.ref('Map/' + id + '/Floors/' + id + '/Elements');
                    elemRef.once("value", function(snapshot) {
                        snapshot.forEach(function(elem) {
                            var e = loadElement(elem);
                            f.addElement(e);
                        });
                        v.addFloor(f);
                    });
                });
            });
            locations.push(v);
        });
    });
}

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

function generateRandomId() {
    var text = "";
    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    for (var i = 0; i < 10; i++) {
        text += possible.charAt(Math.floor(Math.random() * possible.length));
    }
    console.log("Newly generated id: " + text);
    return text;
}
