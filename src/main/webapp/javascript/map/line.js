/**************
 * Wall Class *
 **************/
var Wall = (function() {
    // constructor
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
    // Redraw the line - makes use of Draw
    Wall.prototype.redraw = function(x, y) {
        this.x = x || this.x;
        this.y = y || this.y;
        this.draw();
        return (this);
    }
    // Draw the line
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
    // Check if the click is close enough
    Wall.prototype.checkCloseEnough = function(mouseX, mouseY) {       
        if (mouseX <= this.x + 5 && mouseX >= this.x -5 && 
            mouseY <= this.y + 5 && mouseY >= this.y -5) {
            this.startLine = true;
            console.log("Startline selected");
            return true;
        } 
        else if (mouseX >= this.x2 - 5 && mouseX <= this.x2 +5 &&
            mouseY >= this.y2 - 5 && mouseY <= this.y2 +5) {
            this.endLine = true;
            console.log("Endline selected");
            return true;
        }
        console.log("Not close enough");
        return false;
    }
    // Move the line
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
    // Check if a point is inside the line
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
    // Resize the rectangle
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
    // Set the resizing values on false again
    Wall.prototype.stopResize = function() {
        this.startLine = false;
        this.endLine = false;
    }
    // Check if the start coordinate or end coordinate is selected
    Wall.prototype.isResizing = function() {
        if (this.startLine || this.endLine) return true;
        else return false;
    }	
    // toString() value for writing to the firebase
    Wall.prototype.toString = function() {
        return String(this.type + ";" + this.x + ";" + this.y + ";" + this.x2 + ";" + this.y2);
    }
    return Wall;
})();