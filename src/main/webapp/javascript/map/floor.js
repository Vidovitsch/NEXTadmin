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
    }
    // add element
    Floor.prototype.addElement = function(element) {
        this.elements.push(element);
    }
    return Floor;
})();


