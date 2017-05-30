/**************
 * Room Class *
 **************/
var Room = (function () {
    // constructor
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
    // Redraw the room - makes use of Draw
    Room.prototype.redraw = function (x, y) {
        this.x = x || this.x;
        this.y = y || this.y;
        this.draw();
        return (this);
    }
    // Draw the room
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
    // Check if the click is close enough
    Room.prototype.checkCloseEnough = function(mouseX, mouseY) {
        console.log("Check close enough Rect");
        // Top left corner check
        if (Math.abs(mouseX - this.x) < 2 && Math.abs(mouseY - this.y) < 2) {
            this.topLeft = true;
            console.log("Rect: topLeft true");
        }
        // Top right corner check
        else if (Math.abs(mouseX - (this.x + this.width)) < 2 && Math.abs(mouseY - this.y) < 2) {
            this.topRight = true;
            console.log("Rect: topRight true");
        }
        // Bottom left corner check
        else if (Math.abs(mouseX - this.x) < 2 && Math.abs(mouseY - (this.y + this.height)) < 2) {
            this.botLeft = true;
            console.log("Rect: botLeft true");
        }
        // Bottom right corner check
        else if (Math.abs(mouseX - (this.x + this.width)) < 2 && Math.abs(mouseY - (this.y + this.height)) < 2) {
            this.botRight = true;
            console.log("Rect: botRight true");
        }
    }
    // Move the room
    Room.prototype.moveTo = function(mouseX, mouseY) {
        this.x = mouseX; 
        this.y = mouseY; 

        this.draw();
        return (this);
    }
    // Check if a point is inside the room
    Room.prototype.isPointInside = function (x, y) {
        return (x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height);
    }
    // Resize the room
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
    // Set the resizing values on false again
    Room.prototype.stopResize = function() {
        this.topLeft = false;
        this.topRight = false;
        this.botLeft = false;
        this.botRight = false;
    }
    // 
    Room.prototype.isResizing = function() {
        if (this.topLeft || this.topRight || this.botLeft || this.botRight) return true;
        else return false;
    }
    // toString() value for writing to the firebase
    Room.prototype.toString = function() {
        return String(this.type + ";" + this.x + ";" + this.y + ";" + this.width + ";" + this.height + ";" + this.capacity + ";" + this.roomname);
    }
    return Room;
})();