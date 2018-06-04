import React, {Component} from 'react';
import $ from "jquery";

class Add extends Component {

    constructor() {
        super();
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    postBox(data) {
        $.ajax({
            type: "POST",
            url: "http://localhost:8080/backend/api/addbox",
            headers: {"Content-Type": "application/json"},
            dataType: "json",
            data: JSON.stringify(data),
            success: function () {

            }
        });
    }

    handleSubmit(e) {
        e.preventDefault();
        const formData = new FormData(e.target);
        var boxObject = {};
        formData.forEach(function (value, key) {
            boxObject[key] = value;
        });

        this.postBox(boxObject);
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
            </div>
        )
    }
}

export default Add;