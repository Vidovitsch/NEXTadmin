/**************
 * Room Class *
 **************/
var Room = (function () {
    /**
     * This is the constructor of the Room class.
     * @param {type} id
     * @param {type} x
     * @param {type} y
     * @param {type} width
     * @param {type} height
     * @param {type} capacity
     * @param {type} name
     * @returns {room_L4.Room}
     */
    function Room(id, x, y, width, height, capacity, name) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.capacity = capacity;
        this.roomname = name;
        this.type = "room";

        this.strokeStyle = '#000000';
        this.minWidth = 96;
        this.minHeight = 96;

        this.topLeft = false;
        this.topRight = false;
        this.botLeft = false;
        this.botRight = false;
		
        this.redraw(this.x, this.y);
        return (this);
    }
    /**
     * This method redraws the Room on the canvas.
     * Makes use of the draw method of this class.
     * @param {type} x
     * @param {type} y
     * @returns {table_L4.Table.prototype}
     */
    Room.prototype.redraw = function (x, y) {
        this.x = x || this.x;
        this.y = y || this.y;
        this.draw();
        return (this);
    }
    /**
     * This method draws the Room on the canvas.
     * @returns {undefined}
     */
    Room.prototype.draw = function () {
        ctx.save();
        ctx.beginPath();
        ctx.rect(this.x, this.y, this.width, this.height);
        ctx.font = "18px Arial";
        ctx.fillText(this.roomname, (this.x + 5), (this.y + 20));
        ctx.fillText("Cap.: " + this.capacity, (this.x + 5), (this.y + 40));
        ctx.strokeStyle = this.strokeStyle;
        ctx.lineWidth = 5;
        ctx.stroke();
        ctx.restore();
    }
    /**
     * This method is used for checking if the mouseX and mouseY coördinate of your input are close enough to a resizable corner.
     * If doSelect is true, resizing will be enabled.
     * If doSelect is false, this method will just act as a check.
     * @param {type} mouseX
     * @param {type} mouseY
     * @param {type} doSelect
     * @returns {Boolean}
     */
    Room.prototype.checkCloseEnough = function(mouseX, mouseY, doSelect) {
        // Top left corner check
        if (Math.abs(mouseX - this.x) < 5 && Math.abs(mouseY - this.y) < 5) {
            if (doSelect) this.topLeft = true;
            return true;
        }
        // Top right corner check
        else if (Math.abs(mouseX - (this.x + this.width)) < 5 && Math.abs(mouseY - this.y) < 5) {
            if (doSelect) this.topRight = true;
            return true;
        }
        // Bottom left corner check
        else if (Math.abs(mouseX - this.x) < 5 && Math.abs(mouseY - (this.y + this.height)) < 5) {
            if (doSelect) this.botLeft = true;
            return true;
        }
        // Bottom right corner check
        else if (Math.abs(mouseX - (this.x + this.width)) < 5 && Math.abs(mouseY - (this.y + this.height)) < 5) {
            if (doSelect) this.botRight = true;
            return true;
        }
        return false;
    }
    /**
     * This method moves the X and Y coördinate to the newly given mouseX and mouseY coördinate of your input.
     * @param {type} mouseX
     * @param {type} mouseY
     * @returns {table_L4.Table.prototype}
     */
    Room.prototype.moveTo = function(mouseX, mouseY) {
        this.x = mouseX; 
        this.y = mouseY; 

        this.draw();
        return (this);
    }
    /**
     * This method checks if the the mouseX and mouseY coördinate of your input are within the boundaries of the Room element.
     * @param {type} mouseX
     * @param {type} mouseY
     * @returns {Boolean}
     */
    Room.prototype.isPointInside = function (x, y) {
        return (x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height);
    }
    /**
     * This method resizes the Room depending on the selected corner.
     * In order to resize, checkCloseEnough should be used with the doSelect parameter as true. 
     * This will enable the selected corner, if close enough, to be resizable.
     * @param {type} mouseX
     * @param {type} mouseY
     * @returns {table_L4.Table.prototype}
     */
    Room.prototype.resizeTo = function (mouseX, mouseY) {
        if (this.topLeft) {
            this.width += this.x - mouseX;
            this.height += this.y - mouseY;
            this.x = mouseX;
            this.y = mouseY;
        }
        else if (this.topRight) {
            this.width = Math.abs(this.x - mouseX);
            this.height += this.y - mouseY;
            this.y = mouseY;
        }
        else if (this.botLeft) {
            this.width += this.x - mouseX;
            this.height = Math.abs(this.y - mouseY);
            this.x = mouseX;
        }
        else if (this.botRight) {
            this.width = Math.abs(this.x - mouseX);
            this.height = Math.abs(this.y - mouseY);
        }

        if (this.width < this.minWidth) {
            this.width = this.minWidth;
        }
        if (this.height < this.minHeight) {
            this.height = this.minHeight;
        }
        
        this.draw();
        return (this);
    }
    /**
     * This method disables resizing by putting every corner back to the default false value.
     * @returns {undefined}
     */
    Room.prototype.stopResize = function() {
        this.topLeft = false;
        this.topRight = false;
        this.botLeft = false;
        this.botRight = false;
    }
    /**
     * This method checks whether one of the corners is enabled for resizing.
     * @returns {Boolean}
     */
    Room.prototype.isResizing = function() {
        if (this.topLeft || this.topRight || this.botLeft || this.botRight) return true;
        else return false;
    }
    /**
     * This method returns a toString value for better readability.
     * This can be very useful for debugging or quickly showing values of the raw Room objects.
     * @returns {unresolved}
     */
    Room.prototype.toString = function() {
        return String(this.type + ";" + this.x + ";" + this.y + ";" + this.width + ";" + this.height + ";" + this.capacity + ";" + this.roomname);
    }
    return Room;
})();