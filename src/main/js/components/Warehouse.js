import React, {Component} from 'react';
import ItemList from './ItemList';
import '@babel/polyfill';
import CreateDialog from "./CreateDialog";
import {TabBar} from "./TabBar";
import {Tab} from "./Tab";

export default class Warehouse extends Component {

    constructor(props) {
        super(props);
        this.state = {
            items: [],
            attributes: [
                'name', 'type', 'model', 'category', 'quantity', 'inStock'
            ],
            selectedCategory: "sound"
        };

        this.handleSelectCategory = this.handleSelectCategory.bind(this);
    }

    componentDidMount() {
        this.loadFromServer();
    }

    loadFromServer() {
        fetch('https://localhost:8443/items', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(response => {
            return response.json();
        }).then(response => {
            if (response._embedded) {
                this.setState({
                    items: response._embedded.itemList
                });
            } else {
                this.setState({
                    items: []
                });
            }
        })
    }

    onCreate(newItem) {
        fetch('https://localhost:8443/items', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newItem)
        }).then(() => {
            this.loadFromServer();
        })
    }

    onDelete(item) {
        fetch(item._links.self.href, {
            method: 'DELETE'
        }).then(() => {
            this.loadFromServer();
        })
    }

    generateCategories() {
        const {items} = this.state;
        const categories = new Set();
        items.forEach(item => {
            categories.add(item.category)
        })
        return Array.from(categories);
    }

    handleSelectCategory(e) {
        this.setState({selectedCategory: e.target.offsetParent.name});
    }

    filterItemsByCategory() {
        const {items, selectedCategory} = this.state;
        const filteredItems = items.filter(item => item.category === selectedCategory);
        return filteredItems;
    }

    render() {

        const {attributes, selectedCategory} = this.state;
        const tabs = this.generateCategories().map(category =>
            <Tab name={category} isActive={selectedCategory} selected={this.handleSelectCategory}/>)
        const items = this.filterItemsByCategory();

        return (
            <div>
                <TabBar>
                    {tabs}
                </TabBar>
                <ItemList items={items} onDelete={this.onDelete.bind(this)}/>
                <CreateDialog attributes={attributes} onCreate={this.onCreate.bind(this)}/>
                {/*<UserForm />*/}
            </div>
        )
    }
}