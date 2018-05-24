import React, {Component} from 'react';
import Switch from "react-router-dom/es/Switch";
import Route from "react-router-dom/es/Route";
import Add from "./Add";
import List from "./List";

class RouterOutput extends Component{
    render(){
        return(
            <div>
                <h1>Welcome to Boxinator</h1>
                <Switch>
                    <Route exact path='/addbox' component={Add}/>
                    <Route exact path='/listboxes' component={List}/>
                </Switch>
            </div>
        )
    }
}

export default RouterOutput;