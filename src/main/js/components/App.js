import React, {Component, Fragment} from 'react';
import Aside from './Aside';
import {AppBar} from "./AppBar";
import Warehouse from "./Warehouse";

export default class App extends Component {

    constructor(props) {
        super(props);
        this.state = {
            currentPage: "dashboard"
        }

        this.handlePageChange = this.handlePageChange.bind(this)
    }

    handlePageChange(e) {
        this.setState({currentPage: e.target.attributes.name.value});
        console.log(e.target.attributes.name.value);
    }

    render() {
        const {currentPage} = this.state;
        return (
            <Fragment>
                <Aside onClick={this.handlePageChange} selected={currentPage}/>
                <AppBar>
                    {(currentPage === "dashboard") ? <Dashboard/> : <Warehouse/>}
                    {/*<Settings/>*/}
                </AppBar>
            </Fragment>
        )
    }
}

function Dashboard() {
    return <h1>Dashboard</h1>
}

function Settings() {
    return <h1>Settings</h1>
}