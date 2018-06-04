import React, {Component} from 'react';
import $ from "jquery";

class Add extends Component {

    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.state = {message: ""};
    }

    postBox(data) {
        $.ajax({
            type: "POST",
            url: "http://localhost:8080/backend/api/addbox",
            headers: {"Content-Type": "application/json"},
            dataType: "json",
            data: JSON.stringify(data),
            success: function () {
                this.setState({message: "Box posted successfully!"})
            }.bind(this),
            error: function () {
                this.setState({message: "Something went wrong!"});
            }.bind(this)
        });
    }

    //runs when Post Box buttin is activated
    //makes an object from the form data and posts the object
    handleSubmit(e) {
        e.preventDefault();
        const formData = new FormData(e.target);
        var boxObject = {};
        formData.forEach(function (value, key) {
            boxObject[key] = value;
        });
        //validate inputs
        if (this.validData(boxObject)) {
            this.postBox(boxObject);
        }

    }

    validData(data) {
        //check if weight is negative
        if (data.weight < 0) {
            this.setState({message: "Weight cant be less than 0"});
            return false;
        }
        //convert color formats to check if color is blue
        var colorRgb = this.hexToRgb(data.color);
        var colorHsl = this.rgbToHsl(colorRgb.r, colorRgb.g, colorRgb.b);
        //check if color is blue
        if (this.isBlue(colorHsl)) {
            this.setState({message: "Color blue is not allowed"});
            return false;
        }
        return true;
    }

    //if the angle (hue) is between 180 and 270 degrees (0.5 and 0.75) in the hsv scale te color is blue
    //values depend on what you would think as blue
    isBlue(hsl) {
        if (hsl.h > 0.5 && hsl.h < 0.75) {
            return true;
        } else {
            return false;
        }
    }

    //ref: stackoverflow
    hexToRgb(hex) {
        var result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
        return result ? {
            r: parseInt(result[1], 16),
            g: parseInt(result[2], 16),
            b: parseInt(result[3], 16)
        } : null;
    }

    // ref: stackoverflow
    rgbToHsl(r, g, b) {
        r /= 255
        g /= 255
        b /= 255;
        var max = Math.max(r, g, b), min = Math.min(r, g, b);
        var h, s, l = (max + min) / 2;

        if (max === min) {
            h = s = 0; // achromatic
        } else {
            var d = max - min;
            s = l > 0.5 ? d / (2 - max - min) : d / (max + min);
            switch (max) {
                case r:
                    h = (g - b) / d + (g < b ? 6 : 0);
                    break;
                case g:
                    h = (b - r) / d + 2;
                    break;
                case b:
                    h = (r - g) / d + 4;
                    break;
                default:
                    break;
            }
            h /= 6;
        }

        return {h: h, s: s, l: l};
    }

    render() {
        return (
            <div className="container">
                <form onSubmit={this.handleSubmit} name="boxForm">
                    <label htmlFor="reciever">Mottagarens namn</label>
                    <input type="text" name="reciever" id="reciever" required/><br/>
                    <label htmlFor="weight">Paketets vikt i kg</label>
                    <input type="number" step="any" name="weight" id="weight" required/><br/>
                    <label htmlFor="color">Paketets färg</label>
                    <input type="color" name="color" id="color"/><br/>
                    <label htmlFor="destination">Mottagarens land</label>
                    <select name="destination" id="destination">
                        <option value="Sweden">Sweden</option>
                        <option value="China">China</option>
                        <option value="Brazil">Brazil</option>
                        <option value="Australia">Australia</option>
                    </select><br/>
                    <button>Post Box</button>
                </form>
                <p>{this.state.message}</p>
            </div>
        )
    }
}

export default Add;