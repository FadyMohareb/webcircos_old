//'use strict';

var CircosTabData = [{name: 'Overview', isActive: true}, {name: 'Parent-pool', isActive: false}];

var CircosTabs = React.createClass({displayName: 'CircosTabs',
    render: function render() {
        return (React.createElement('ul', {className: 'nav nav-pills'},
                CircosTabData.map(function (tab) {

                    return React.createElement(CircosTab, {data: tab, isActive: this.props.activeTab === tab, handleClick: this.props.changeTab.bind(this, tab)});

                }.bind(this)))
                )
    }
});

var CircosTab = React.createClass({displayName: 'CircosTab',
    render: function render() {

        return (
                React.createElement('li', {onClick: this.props.handleClick, className: this.props.isActive ? "active" : null},
                        React.createElement('a', {href: '#'}, this.props.data.name))
                )
    }

});

var CircosContent = React.createClass({displayName: 'CircosContent',
    render: function render() {

        return (
                React.createElement('div', null,
                        this.props.activeTab.name === 'Overview' ? React.createElement('section', {className: 'panel panel-primary'},
                                React.createElement('div', {className: 'panel-body', id: 'circos'},
                                        React.createElement(CircosPanel))) : null,
                        this.props.activeTab.name === 'Parent-pool' ? React.createElement('section', {className: 'panel panel-primary'},
                                React.createElement('div', {className: 'panel-body', id: 'parentPool'},
                                        React.createElement(ParentPoolPanel))) : null)
                )
    }
});

var CircosTabsComponent = React.createClass({displayName: 'CircosTest',
    getInitialState: function getInitialState() {
        return {activeTab: CircosTabData[0]};
    },
    handleClick: function handleClick(tab) {
        this.setState({activeTab: tab});
    },
    render: function render() {
        return React.createElement('div', null,
                React.createElement(CircosTabs, {activeTab: this.state.activeTab, changeTab: this.handleClick}),
                React.createElement(CircosContent, {activeTab: this.state.activeTab})
                );
    }
});

var renderCircosTabsComponent = function () {
    React.render(React.createElement(CircosTabsComponent, null), document.getElementById('centerContainer'));
}