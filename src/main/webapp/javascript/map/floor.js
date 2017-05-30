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
        this.drawElements();
    }
    // remove element (floor remembers selected element, so no parameter required for removal)
    Floor.prototype.removeSelectedElement = function() {
        if (!this.selected) return;
        
        var remove;
        for (var i = 0; i < this.elements.length; i++) {
            if (this.elements[i].id == this.selected.id) {
                remove = i;
                break;
            }
        }
        this.elements.splice(remove, 1);
        console.log("REMOVED " + remove + ", LEFT: " + this.elements.length);
        //this.selected = null; // currently happens outside of floor class
        this.drawElements();
    }
    // draw all elements
    Floor.prototype.drawElements = function() { 
        console.log("Drawing " + this.elements.length + " elements.");
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        for (var i = 0; i < this.elements.length; i++) {
            if (this.selected == null || this.selected.id != this.elements[i].id) {
                this.elements[i].strokeStyle = "#000000";
            } else if (this.selected.id == this.elements[i].id) {
                this.elements[i].strokeStyle = "#ff0000"
            }
            this.elements[i].draw();
        }
    }
    // select element
    Floor.prototype.selectElement = function(element) {
        this.selected = element;
        this.drawElements();
    }
    // toString() value for writing to the firebase
    Floor.prototype.toString = function() {
        return String(this.id + ";" + this.name + ";" + this.level + ";" + this.elements);
    }
    return Floor;
})();


