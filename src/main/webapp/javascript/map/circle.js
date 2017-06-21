/****************
 * Circle class *
 ****************/
var Circle = (function () {
    /**
     * This is the constructor of the Circle class.
     * @param {type} id
     * @param {type} x
     * @param {type} y
     * @param {type} radius
     * @returns {circle_L4.Circle}
     */
    function Circle(id, x, y, radius) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.type = "circle";

        this.strokeStyle = '#000000';

        this.borderSelected = false;
				
        this.redraw(this.x, this.y);
        return (this);
    }
    /**
     * This method redraws the Circle on the canvas.
     * Makes use of the draw method of this class.
     * @param {type} x
     * @param {type} y
     * @returns {circle_L4.Circle.prototype}
     */
    Circle.prototype.redraw = function (x, y) {
        this.x = x || this.x;
        this.y = y || this.y;
        this.draw();
        return (this);
    }
    /**
     * This method draws the Circle on the canvas.
     * @returns {undefined}
     */
    Circle.prototype.draw = function () {
        ctx.save();
        ctx.beginPath();
        ctx.arc(this.x, this.y, this.radius, 0, 2 * Math.PI);
        ctx.strokeStyle = this.strokeStyle;
        ctx.lineWidth = 5;
        ctx.stroke();
        ctx.restore();
    }
    /**
     * This method is used for checking if the mouseX and mouseY coördinate of your input are close enough to the border.
     * @param {type} mouseX
     * @param {type} mouseY
     * @param {type} doSelect
     * @returns {Boolean}
     */
    Circle.prototype.checkCloseEnough = function(mouseX, mouseY, doSelect) {
        // Check if the border is selected
        var distanceX = mouseX - this.x;
        var distanceY = mouseY - this.y;

        var root = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));

        if (root >= this.radius - 5 && root <= this.radius) {
            if (doSelect) this.borderSelected = true;
            return true;
        }
        return false;
    }
    /**
     * This method moves the X and Y coördinate to the newly given mouseX and mouseY coördinate of your input.
     * @param {type} mouseX
     * @param {type} mouseY
     * @returns {circle_L4.Circle.prototype}
     */
    Circle.prototype.moveTo = function(mouseX, mouseY) {
        this.x = mouseX; 
        this.y = mouseY; 

        this.draw();
        return (this);
    }	
    /**
     * This method checks if the mouseX and mouseY coördinate of your input are within the boundaries of the Circle's border.
     * @param {type} mouseX
     * @param {type} mouseY
     * @returns {Boolean}
     */
    Circle.prototype.isPointInside = function (mouseX, mouseY) {
        var bool = Math.pow(mouseX - this.x, 2) + Math.pow(mouseY - this.y, 2) < Math.pow(this.radius, 2);
        return bool;
    }
    /**
     * This method resizes the Circle depending if the border is selected.
     * In order to resize, checkCloseEnough should be used with the doSelect parameter as true.
     * This will enable the radius, if close enough, to be resizable.
     * @param {type} mouseX
     * @param {type} mouseY
     * @returns {circle_L4.Circle.prototype}
     */
    Circle.prototype.resizeTo = function(mouseX, mouseY) {
        // Calculate the difference for the new radius
        var distanceX = mouseX - this.x;
        var distanceY = mouseY - this.y;

        this.radius = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
        this.draw();
        return (this);
    }
    /**
     * This method disables resizing by putting the border selection value back to false.
     * @returns {undefined}
     */
    Circle.prototype.stopResize = function() {
        this.borderSelected = false;
    }
    /**
     * This method checks whether the border is enabled for resizing.
     * @returns {Boolean}
     */
    Circle.prototype.isResizing = function() {
        if (this.borderSelected) return true;
        else return false;
    }
    /**
     * This method returns a toString value for better readability.
     * This can be very useful for debugging or quickly showing values of the raw Circle objects.
     * @returns {unresolved}
     */
    Circle.prototype.toString = function() {
        return String(this.type + ";" + this.x + ";" + this.y + ";" + this.radius);
    }
    return Circle;
})();