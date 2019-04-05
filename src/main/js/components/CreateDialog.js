import React, { Component } from 'react';
import { findDOMNode } from 'react-dom';

export default class CreateDialog extends Component {

    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(e) {
        e.preventDefault();
        const newItem = {};
        this.props.attributes.forEach(attribute => {
            newItem[attribute] = findDOMNode(this.refs[attribute]).value.trim();
        });
        this.props.onCreate(newItem);

        // clear out the dialog's inputs
        this.props.attributes.forEach(attribute => {
            findDOMNode(this.refs[attribute]).value = '';
        });

        // Navigate away from the dialog to hide it.
        window.location = "#";
    }

    render() {
        const inputs = this.props.attributes.map(attribute =>
            <p key={attribute}>
                <input type="text" placeholder={attribute} ref={attribute} className="field"/>
            </p>
        );

        return (
            <div>
                <button onClick={() => location.href='#createItem'}>Create</button>

                <div id="createItem" className="modalDialog">
                    <div>
                        <a href="#" title="Close" className="close">X</a>

                        <h2>Create new item</h2>

                        <form>
                            {inputs}
                            <button onClick={this.handleSubmit}>Create</button>
                        </form>
                    </div>
                </div>
            </div>
        )
    }

}