//'use strict';

var FilesTabData = [{name: 'Overview', isActive: true}, {name: 'Parent-pool', isActive: false}];

var FilesTabs = React.createClass({displayName: 'FilesTabs',
    render: function render() {
        return (React.createElement('ul', {className: 'nav nav-pills'},
                FilesTabData.map(function (tab) {

                    return React.createElement(FilesTab, {data: tab, isActive: this.props.activeTab === tab,
                        handleClick: this.props.changeTab.bind(this, tab)});

                }.bind(this)))
                )
    }
});

var FilesTab = React.createClass({displayName: 'FilesTab',
    render: function render() {

        return (
                React.createElement('li', {onClick: this.props.handleClick, className: this.props.isActive ? "active" : null},
                        React.createElement('a', {href: '#'}, this.props.data.name))
                )
    }

});

var FilesContent = React.createClass({displayName: 'FilesContent',
    render: function render() {

        return (
                React.createElement('div', null,
                        this.props.activeTab.name === 'Overview' ? React.createElement('section', {className: 'panel panel-primary'},
                                React.createElement('div', {className: 'panel-body', id: 'filesGeneral'},
                                        React.createElement(FilesGeneralPanel, {projectName: this.props.projectName}))) : null,
                        this.props.activeTab.name === 'Parent-pool' ? React.createElement('section', {className: 'panel panel-primary'},
                                React.createElement('div', {className: 'panel-body', id: 'filesParentPool'},
                                        React.createElement(FilesParentPoolPanel))) : null)
                )
    }
});

var FilesTabsComponent = React.createClass({displayName: 'FilesTest',
    getInitialState: function getInitialState() {
        return {activeTab: FilesTabData[0]};
    },
    handleClick: function handleClick(tab) {
        this.setState({activeTab: tab});
    },
    render: function render() {
        return React.createElement('div', null,
                React.createElement(FilesTabs, {activeTab: this.state.activeTab, changeTab: this.handleClick}),
                React.createElement(FilesContent, {activeTab: this.state.activeTab, projectName: this.props.projectName, parent: this})
                );
    }
});

var renderFilesTabsComponent = function () {
    React.render(React.createElement(FilesTabsComponent, null), document.getElementById('filesTabsContainer'));
}