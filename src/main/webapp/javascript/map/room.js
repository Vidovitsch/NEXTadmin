/**************
 * Room Class *
 **************/
var room = (function () {
    // constructor
    function room(id, x, y, width, height, capacity, name) {
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
    room.prototype.redraw = function (x, y) {
        this.x = x || this.x;
        this.y = y || this.y;
        this.draw();
        return (this);
    }
    // Draw the room
    room.prototype.draw = function () {
        ctx.save();
        ctx.beginPath();
        ctx.rect(this.x, this.y, this.width, this.height);
        ctx.font = "18px Arial";
        ctx.fillText(this.roomname, (this.x + this.width / 2) - 6);
        ctx.fillText(this.capacity, (this.x + this.width / 2) - 6, (this.y + this.height / 2) + 6);
        ctx.strokeStyle = this.strokeStyle;
        ctx.lineWidth = 5;
        ctx.stroke();
        ctx.restore();
    }
    // Check if the click is close enough
    room.prototype.checkCloseEnough = function(mouseX, mouseY) {
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
    room.prototype.moveTo = function(mouseX, mouseY) {
        this.x = mouseX; 
        this.y = mouseY; 

        this.draw();
        return (this);
    }
    // Check if a point is inside the room
    room.prototype.isPointInside = function (x, y) {
        return (x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height);
    }
    // Resize the room
    room.prototype.resizeTo = function (mouseX, mouseY) {
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
    room.prototype.stopResize = function() {
        this.topLeft = false;
        this.topRight = false;
        this.botLeft = false;
        this.botRight = false;
    }
    // 
    room.prototype.isResizing = function() {
        if (this.topLeft || this.topRight || this.botLeft || this.botRight) return true;
        else return false;
    }
    // toString() value for writing to the firebase
    room.prototype.toString = function() {
        return String(this.type + ";" + this.x + ";" + this.y + ";" + this.width + ";" + this.height + ";" + this.number);
    }
    return room;
})();