import React, { Component } from 'react';
import logo from './logo.svg';

import MessageList from './MessageList';
import MessageInput from './MessageInput';
import './App.css';

import EventBus from 'vertx3-eventbus-client/vertx-eventbus'

class App extends Component {

    constructor(props) {
        super(props);

        this.state = {eb: new EventBus('http://localhost:8084/eventbus')};

    }

    render() {
        return (
            <div className="App">
                <div className="App-header">
                    <img src={logo} className="App-logo" alt="logo" />
                    <h2>Welcome to a Vert.x-based reactive chat</h2>
                </div>
                <p className="App-intro">
                    <MessageList eb={this.state.eb}/>
                </p>
                <MessageInput sendMsg={(msg) => this.state.eb.send("server", msg)}/>
            </div>
        );
    }
}

export default App;