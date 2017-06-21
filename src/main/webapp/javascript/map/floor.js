/***************
 * Floor Class *
 ***************/
var Floor = (function() {
    /**
     * This is the constructor of the Floor class.
     * @param {type} id
     * @param {type} name
     * @param {type} level
     * @returns {floor_L4.Floor}
     */
    function Floor(id, name, level) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.elements = [];
        this.selected = null;
    }
    /**
     * This method adds an element to the elements list. 
     * Also redraws all the elements on the canvas after adding the new element.
     * @param {type} element
     * @returns {undefined}
     */
    Floor.prototype.addElement = function(element) {
        this.elements.push(element);
        this.drawElements();
    }
    /**
     * This method removes the currently selected element from the list.
     * If no element is currently selected, nothing will be deleted.
     * If an element is selected, it will be removed from the elements list.
     * Also redraws all the elements on the canvas after deleting the selected element.
     * @returns {undefined}
     */
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
    /**
     * This method draws all the elements in the elements list on the canvas.
     * @returns {undefined}
     */
    Floor.prototype.drawElements = function() { 
        console.log("Drawing " + this.elements.length + " elements.");
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        for (var i = 0; i < this.elements.length; i++) {
            if (this.selected === null || this.selected.id !== this.elements[i].id) {
                this.elements[i].strokeStyle = "#000000";
            } else if (this.selected.id === this.elements[i].id) {
                this.elements[i].strokeStyle = "#ff0000"
            }
            this.elements[i].draw();
        }
    }
    /**
     * This method selects an element from the list and assigns it as the current selected element of this floor.
     * Also redraws all the elements on the canvas, while showing the selected with a different colored border.
     * @param {type} element
     * @returns {undefined}
     */
    Floor.prototype.selectElement = function(element) {
        this.selected = element;
        this.drawElements();
    }
    /**
     * This method returns a toString value for better readability.
     * This can be very useful for debugging or quickly showing values of the raw Floor objects. 
     * @returns {unresolved}
     */
    Floor.prototype.toString = function() {
        return String(this.id + ";" + this.name + ";" + this.level + ";" + this.elements);
    }
    return Floor;
})();


