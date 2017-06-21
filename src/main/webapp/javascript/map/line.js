/**************
 * Wall Class *
 **************/
var Wall = (function() {
    /**
     * This is the constructor of the Wall class.
     * @param {type} id
     * @param {type} x
     * @param {type} y
     * @param {type} x2
     * @param {type} y2
     * @returns {line_L4.Wall}
     */
    function Wall(id, x, y, x2, y2) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.x2 = x2 || (x + 50);
        this.y2 = y2 || y;
        this.width = 5;
        this.type = "line";

        this.strokeStyle = '#000000';

        this.startLine = false;
        this.endLine = false;
		
        this.redraw(this.x, this.y);
		
        return (this);
    }
    /**
     * This method redraws the Wall on the canvas.
     * Makes use of the draw method of this class.
     * @param {type} x
     * @param {type} y
     * @returns {line_L4.Wall.prototype}
     */
    Wall.prototype.redraw = function(x, y) {
        this.x = x || this.x;
        this.y = y || this.y;
        this.draw();
        return (this);
    }
    /**
     * This method draws the Wall on the canvas.
     * @returns {undefined}
     */
    Wall.prototype.draw = function() {
        ctx.save();
        ctx.beginPath();
        ctx.moveTo(this.x, this.y);
        ctx.lineTo(this.x2, this.y2);
        ctx.strokeStyle = this.strokeStyle;
        ctx.lineWidth = this.width;
        ctx.stroke();
        ctx.restore();
    }
    /**
     * This method is used for checking if the mouseX and mouseY coördinate of your input are close enough to a resizable start or endpoint.
     * If doSelect is true, resizing will be enabled.
     * If doSelect is false, this method will just act as a check.
     * * @param {type} mouseX
     * @param {type} mouseY
     * @param {type} doSelect
     * @returns {Boolean}
     */
    Wall.prototype.checkCloseEnough = function(mouseX, mouseY, doSelect) {       
        if (mouseX <= this.x + 5 && mouseX >= this.x -5 && 
            mouseY <= this.y + 5 && mouseY >= this.y -5) {
            if (doSelect) this.startLine = true;
            return true;
        } 
        else if (mouseX >= this.x2 - 5 && mouseX <= this.x2 +5 &&
            mouseY >= this.y2 - 5 && mouseY <= this.y2 +5) {
            if (doSelect) this.endLine = true;
            return true;
        }
        return false;
    }
    /**
     * This method moves the X and Y coördinate to the newly given mouseX and mouseY coördinate of your input.
     * @param {type} mouseX
     * @param {type} mouseY
     * @returns {line_L4.Wall.prototype}
     */
    Wall.prototype.moveTo = function(mouseX, mouseY) {
        var difX = this.x2 - this.x;
        var difY = this.y2 - this.y;

        this.x = mouseX; 
        this.y = mouseY; 
        this.x2 = this.x + difX;
        this.y2 = this.y + difY;

        this.draw();
        return (this);
    }
    /**
     * This method checks if the mouseX and mouseY coördinate of your input are within the boundaries of the Wall element.
     * @param {type} x
     * @param {type} y
     * @returns {Boolean}
     */
    Wall.prototype.isPointInside = function (x, y) {
        //console.log("mousex"+ x);
        //console.log("mousey"+y);
        var width = this.x2 - this.x;
        //console.log("width"+width);
        var lowX = this.x < this.x2 ? this.x : this.x2; 
        var highX = this.x > this.x2 ? this.x : this.x2;
        //console.log("lowx"+lowX);
        var height = this.y2 - this.y;
        //console.log("height"+height);
        var lowY = this.y < this.y2 ? this.y : this.y2;
        var highY = this.y > this.y2 ? this.y : this.y2;
        //console.log("lowy"+ lowY);
		
        if(lowX - 2 < x &&
            lowY - 2 < y &&
            highX + 2 > x &&
            highY + 2 > y)
        {
            if (highY - lowY < 5 || highX - lowX < 5)
                return true;
            var linear = height/width;
            //console.log("linear"+ linear);
            var constante = (linear * this.x - this.y) * -1;
            //console.log("constante"+ constante);
            var lineSensitive = this.width;			
            //console.log("sensitive"+ lineSensitive);
            //console.log("uitkomst" +x * linear + constante > y - lineSensitive && x * linear + constante < y + lineSensitive);  
            return (x * linear + constante > y - lineSensitive && 
                    x * linear + constante < y + lineSensitive);
        }
        else return false;
    }
    /**
     * This method resizes the Wall depending on the selected point (end or startpoint).
     * In order to resize, checkCloseEnough should be used with the doSelect parameter as true.
     * This will enable the selected point, if close enough, to be resizable.
     * @param {type} mouseX
     * @param {type} mouseY
     * @returns {line_L4.Wall.prototype}
     */
    Wall.prototype.resizeTo = function(mouseX, mouseY) {
        if (this.startLine) {
            this.x = mouseX;
            this.y = mouseY;
        }
        else if (this.endLine) {
            this.x2 = mouseX;
            this.y2 = mouseY;
        }
        this.draw();
        return (this);
    }
    /**
     * This method disables resizing by putting every point back to the default false value.
     * @returns {undefined}
     */
    Wall.prototype.stopResize = function() {
        this.startLine = false;
        this.endLine = false;
    }
    /**
     * This method checks whether one of the points is enabled for resizing.
     * @returns {Boolean}
     */
    Wall.prototype.isResizing = function() {
        if (this.startLine || this.endLine) return true;
        else return false;
    }	
    /**
     * This method returns a toString value for better readability.
     * This can be very useful for debugging or quickly showing values of the raw Wall objects.
     * @returns {unresolved}
     */
    Wall.prototype.toString = function() {
        return String(this.type + ";" + this.x + ";" + this.y + ";" + this.x2 + ";" + this.y2);
    }
    return Wall;
})();