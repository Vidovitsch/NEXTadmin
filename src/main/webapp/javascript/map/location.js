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
    Venue.prototype.selectFloor = function(floorname) {
        for (var i = 0; i < this.floors.length; i++) {
            if (this.floors[i].name == floorname) {
                this.selectedFloor = this.floors[i];
                break;
            }
        }
    }
    return Venue;
})();


