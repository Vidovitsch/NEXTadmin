/******************
 * Location Class *
 ******************/
var Venue = (function() {
    // constructor
    function Venue(id, name, postal, address) {
        this.id = id;
        this.name = name;
        this.postal = postal;
        this.address = address;
        this.selectedFloor = null;
        this.floors = [];
        return (this);
    }
    Venue.prototype.addFloor = function(floor) {
        this.floors.push(floor);
    }
    Venue.prototype.countFloors = function() {
        return this.floors.length;
    }
    Venue.prototype.selectFloor = function(id) {
        for (var i = 0; i < this.floors.length; i++) {
            if (this.floors[i].id == id) {
                this.selectedFloor = this.floors[i];
                console.log("Selected Floor: " + this.selectedFloor);
                break;
            }
        }
    }
    // toString() value for writing to the firebase
    Venue.prototype.toString = function() {
        return String(this.id + ";" + this.name + ";" + this.postal + ";" + this.address + ";" + this.floors.length);
    }
    return Venue;
})();


