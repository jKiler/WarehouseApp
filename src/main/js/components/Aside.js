import React, {Component, Fragment} from 'react';

export default class Aside extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <Fragment>
                <aside className="mdc-drawer">
                    <div className="mdc-drawer__header">
                        <h3 className="mdc-drawer__title">Username</h3>
                        <h6 className="mdc-drawer__subtitle">email@material.io</h6>
                    </div>
                    <div className="mdc-drawer__content">
                        <div className="mdc-list">
                            <div name="dashboard"
                                 className={this.props.selected === "dashboard" ? "mdc-list-item mdc-list-item--activated" : "mdc-list-item"}
                                 onClick={this.props.onClick}>
                                <i className="material-icons mdc-list-item__graphic" aria-hidden="true">dashboard</i>
                                <span className="mdc-list-item__text">Dashboard</span>
                            </div>
                            <div name="warehouse" className="mdc-list-item" onClick={this.props.onClick}>
                                <i className="material-icons mdc-list-item__graphic" aria-hidden="true">storage</i>
                                <span className="mdc-list-item__text">Warehouse</span>
                            </div>
                            <div className="mdc-list-item" href="/">
                                <i className="material-icons mdc-list-item__graphic" aria-hidden="true">settings</i>
                                <span className="mdc-list-item__text">Settings</span>
                            </div>
                            <form className="mdc-list-item" action="/logout" method="post">
                                <input className="mdc-button mdc-button--raised" type="submit" value="Logout"/>
                            </form>
                        </div>
                    </div>
                </aside>
            </Fragment>
        )
    }
}