import React, { Component } from 'react';
import Item from './Item';

export default class ItemList extends Component {
    render() {
        const items = this.props.items.map(item =>
            <Item key={item._links.self.href}
                  item={item}
                  onDelete={this.props.onDelete}/>
        );
        return (
            <table>
                <tbody>
                <tr>
                    <th>Name</th>
                    <th>Type</th>
                    <th>Model</th>
                    <th>Category</th>
                    <th>Quantity</th>
                    <th>In stock</th>
                    <th> </th>
                    <th> </th>
                </tr>
                {items}
                </tbody>
            </table>
        )
    }
}