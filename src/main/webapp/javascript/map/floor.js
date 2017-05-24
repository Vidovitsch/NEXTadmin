/***************
 * Floor Class *
 ***************/
var Floor = (function() {
    // constructor
    function Floor(id, name, level) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.elements = [];
        this.selected = null;
    }
    // add element
    Floor.prototype.addElement = function(element) {
        this.elements.push(element);
    }
    // draw all elements
    Floor.prototype.drawElements = function() { 
        console.log("Drawing " + this.elements.length + " elements.");
        for (var i = 0; i < this.elements.length; i++) {
            if (this.selected == null) {
                this.elements[i].strokeStyle = "#000000";
            }
            this.elements[i].draw();
        }
    }
    // select element
    Floor.prototype.selectElement = function(element) {
        this.selected = element;
    }
    // toString() value for writing to the firebase
    Floor.prototype.toString = function() {
        return String(this.id + ";" + this.name + ";" + this.level + ";" + this.elements);
    }
    return Floor;
})();


