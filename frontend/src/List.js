import React, {Component} from 'react';
import $ from "jquery";

class List extends Component {

    constructor(props) {
        super(props);
        this.state = {boxes: []};
    }

    componentDidMount() {
        this.getBoxes();
    }

    getBoxes() {
        $.get("http://localhost:8080/backend/api/listboxes",
            data => {
                this.setState({boxes: data});
            });
    }

    render() {
        const rows = this.state.boxes.map((item, i) => (
            <tr key={i}>
                <th>{item.reciever}</th>
                <th>{item.weight} kg</th>
                <th style={{backgroundColor: item.color}}></th>
                <th>{item.cost} SEK</th>
            </tr>
        ));

        return (
            <div>
                <table>
                    <tbody>
                        <tr>
                            <th>Reciever</th>
                            <th>Weight</th>
                            <th>Box Color</th>
                            <th>Shipping cost</th>
                        </tr>
                        {rows}
                    </tbody>
                </table>
            </div>
        )
    }
}

export default List;