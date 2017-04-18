// Requires:
// - Firebase
// - mapcreation.jsp
// - mapoptions.js for dynamic button elements
// - mapcreation.js for elements list which are displayed on the mapcreation canvas

// Get a reference to the database service
var database = firebase.database();

// FireBase
function saveMapToDB() {
    clearMapFromDB();
    for (var i = 0; i < components.length; i++) {
        database.ref('Map/' + components[i].id).set({
            Object: components[i].toString()
        });
    }
}

// FireBase
function loadMapFromDB() {
    var mapRef = database.ref('Map');
    components = [];

    mapRef.on("child_added", function(snapshot) {
        var reference = snapshot.val();
        var object = reference.Object; // type;x;y;dependantOnType
        var values = object.replace("\"", "");
        var values = values.split(";");

        var key = snapshot.key; // id
        console.log("Key value: " + key + " & Object: " + object);
        console.log("Values: " + values);
        console.log("Values[0]: " + values[0]);
        if (values[0] == "circle") {
            console.log("is circle");
            // values[0] = type, values[1] = x, values[2] = y, values[3] = radius
            components.push(new circle(String(key), parseInt(values[1]), parseInt(values[2]), parseInt(values[3])));
        }
        else if (values[0] == "rectangle") {
            console.log("is rectangle");
            // values[0] = type, values[1] = x, values[2] = y, values[3] = width, values[4] = height
            components.push(new rectangle(String(key), parseInt(values[1]), parseInt(values[2]), parseInt(values[3]), parseInt(values[4])));
        }
        else if (values[0] == "table") {
            console.log("is table");
            // values[0] = type, values[1] = x, values[2] = y, values[3] = width, values[4] = height, values[5] = number
            components.push(new table(String(key), parseInt(values[1]), parseInt(values[2]), parseInt(values[3]), parseInt(values[4]), parseInt(values[5])));
        }
        else if (values[0] == "line") {
            console.log("is line");
            // values[0] = type, values[1] = x, values[2] = y, values[3] = x2, values[4] = y2
            components.push(new line(String(key), parseInt(values[1]), parseInt(values[2]), parseInt(values[3]), parseInt(values[4])));
        }
        else if (values[0] == "text") {
            // Not implemented yet/
        }
        console.log("Components size: " + components.length);
        redrawAll();
    });
}

// FireBase
function clearMapFromDB() {
    // Remove the Map reference in firebase
    var mapRef = database.ref('Map');
    mapRef.remove();
}


function writeToFirebase(dateTime, content, uid) {
    var groupID = getGroupID(uid);
    database.ref('Group/' + groupID + '/Messages/' + dateTime).set({
        Content: content,
        UID: uid
    });
};



//var messages = database.ref("Group/0/Messages");
//messages.on("child_added", function(snapshot) {
//var message = snapshot.val();
//var userName = database.ref("User/" + message.UID + "/Name").valueOf();
