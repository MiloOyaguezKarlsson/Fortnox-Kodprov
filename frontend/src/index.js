import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import registerServiceWorker from './registerServiceWorker';
import HashRouter from "react-router-dom/es/HashRouter";
import BrowserRouter from "react-router-dom/es/BrowserRouter";

ReactDOM.render(
    <BrowserRouter basename={"/boxinator"}>
        <App />
    </BrowserRouter>,
    document.getElementById('root'));
registerServiceWorker();
