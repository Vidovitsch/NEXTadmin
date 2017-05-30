/****************
 * Circle class *
 ****************/
var Circle = (function () {
    // constructor
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
    // Redraw the rectangle - makes use of Draw
    Circle.prototype.redraw = function (x, y) {
        this.x = x || this.x;
        this.y = y || this.y;
        this.draw();
        return (this);
    }
    // 
    Circle.prototype.highlight = function (x, y) {
        this.x = x || this.x;
        this.y = y || this.y;
        this.draw();
        return (this);
    }
    // Draw the rectangle
    Circle.prototype.draw = function () {
        ctx.save();
        ctx.beginPath();
        ctx.arc(this.x, this.y, this.radius, 0, 2 * Math.PI);
        ctx.strokeStyle = this.strokeStyle;
        ctx.lineWidth = 5;
        ctx.stroke();
        ctx.restore();
    }
    // Check if the click is close enough
    Circle.prototype.checkCloseEnough = function(mouseX, mouseY) {
        // Check if the border is selected
        var distanceX = mouseX - this.x;
        var distanceY = mouseY - this.y;

        var root = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
        //console.log("Show root: " + root);
        //console.log("Radius: " + this.radius + " " + (this.radius - 5) + "& " + (root < this.radius));
        //console.log("X: " + this.x + ", Y: " + this.y);
        //console.log("MouseX: " + mouseX + ", MouseY: " + mouseY);

        if (root >= this.radius - 5 && root <= this.radius) {
                this.borderSelected = true;
        }
        //console.log("border selected: " + this.borderSelected);
    }
    // Move the circle
    Circle.prototype.moveTo = function(mouseX, mouseY) {
        this.x = mouseX; 
        this.y = mouseY; 

        this.draw();
        return (this);
    }	
    // Check if a point is inside the rectangle
    Circle.prototype.isPointInside = function (mouseX, mouseY) {
        var bool = Math.pow(mouseX - this.x, 2) + Math.pow(mouseY - this.y, 2) < Math.pow(this.radius, 2);
        //console.log(Math.pow(mouseX - this.x, 2) + Math.pow(mouseY - this.y, 2));
        //console.log(Math.pow(this.radius, 2));
        return bool;
    }
    // Resize the rectangle
    Circle.prototype.resizeTo = function(mouseX, mouseY) {
        // Calculate the difference for the new radius
        var distanceX = mouseX - this.x;
        var distanceY = mouseY - this.y;

        this.radius = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
        this.draw();
        return (this);
    }
    // Set the resizing values on false again
    Circle.prototype.stopResize = function() {
        this.borderSelected = false;
    }
    // Check if currently resizing the circle
    Circle.prototype.isResizing = function() {
        if (this.borderSelected) return true;
        else return false;
    }
    // toString() value for writing to the firebase
    Circle.prototype.toString = function() {
        return String(this.type + ";" + this.x + ";" + this.y + ";" + this.radius);
    }
    return Circle;
})();