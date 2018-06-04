import React, { Component } from 'react';
import './App.css';
import Header from "./Header";
import RouterOutput from "./RouterOutput";

class App extends Component {
  render() {
    return (
      <div id="main">
        <Header/>
        <RouterOutput/>
      </div>
    );
  }
}

export default App;
