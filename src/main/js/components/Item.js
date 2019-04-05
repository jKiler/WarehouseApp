import React, {Component} from 'react';

export default class Item extends Component {

    constructor(props) {
        super(props);
        this.handleDelete = this.handleDelete.bind(this);
    }

    handleDelete() {
        this.props.onDelete(this.props.item);
    }

    render() {
        const {item} = this.props;
        return (
            <tr>
                <td>{item.name}</td>
                <td>{item.type}</td>
                <td>{item.model}</td>
                <td>{item.category}</td>
                <td>{item.quantity}</td>
                <td>{item.inStock}</td>
                <td><button>Edit</button></td>
                <td><button onClick={this.handleDelete}>Delete</button></td>
            </tr>
        )
    }
}