/***************
 * Venue Class *
 ***************/
var Venue = (function() {
    /**
     * This is the constructor of the Venue class (aka Location).
     * @param {type} id
     * @param {type} name
     * @param {type} postal
     * @param {type} address
     * @returns {location_L4.Venue}
     */
    function Venue(id, name, postal, address) {
        this.id = id;
        this.name = name;
        this.postal = postal;
        this.address = address;
        this.selectedFloor = null;
        this.floors = [];
        return (this);
    }
    /**
     * This method adds a floor to the floors list.
     * @param {type} floor
     * @returns {undefined}
     */
    Venue.prototype.addFloor = function(floor) {
        this.floors.push(floor);
    }
    /**
     * This method counts the amount of floors and returns the value.
     * @returns {location_L4.Venue.floors.length}
     */
    Venue.prototype.countFloors = function() {
        return this.floors.length;
    }
    /**
     * This method selects a floor from the list based on the given id. 
     * The floor which is found will be assigned as the current selected floor.
     * @param {type} id
     * @returns {undefined}
     */
    Venue.prototype.selectFloor = function(id) {
        for (var i = 0; i < this.floors.length; i++) {
            if (this.floors[i].id === id) {
                this.selectedFloor = this.floors[i];
                console.log("Selected Floor: " + this.selectedFloor);
                break;
            }
        }
    }
    /**
     * This method returns a toString value for better readability.
     * This can be very useful for debugging or quickly showing values of the raw Venue objects.
     * @returns {unresolved}
     */
    Venue.prototype.toString = function() {
        return String(this.id + ";" + this.name + ";" + this.postal + ";" + this.address + ";" + this.floors.length);
    }
    return Venue;
})();


