import React, { Component } from 'react';
import Link from "react-router-dom/es/Link";

class Header extends Component{
    render(){
        return (
            <header>
                <h1>Boxinator</h1>
                <nav>
                    <ul>
                        <li><Link to='/addbox'>Add box</Link></li>
                        <li><Link to='/listboxes'>List boxes</Link></li>
                    </ul>
                </nav>
            </header>
        )

    }
}
export default Header;