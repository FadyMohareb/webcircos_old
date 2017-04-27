////'use strict';
//
//var CircosTabData = [{name: 'Overview', isActive: true}, {name: 'BSA', isActive: false}];
//
//var CircosTabs = React.createClass({displayName: 'CircosTabs',
//    render: function render() {
//        return (React.createElement('ul', {className: 'nav nav-pills'},
//                CircosTabData.map(function (tab) {
//
//                    return React.createElement(CircosTab, {data: tab, isActive: this.props.activeTab === tab, handleClick: this.props.changeTab.bind(this, tab)});
//
//                }.bind(this)),
//                React.createElement('li', {style: {float: 'right'}},
//                        React.createElement('a', {href: '#'}, React.createElement('strong', null, "User manual")))
//                
//                )
//                )
//    }
//});
//
//var CircosTab = React.createClass({displayName: 'CircosTab',
//    render: function render() {
//
//        return (
//                React.createElement('li', {onClick: this.props.handleClick, className: this.props.isActive ? "active" : null},
//                        React.createElement('a', {href: '#'}, React.createElement('strong', null, this.props.data.name)))
//                )
//    }
//
//});
//
//var CircosContent = React.createClass({displayName: 'CircosContent',
//    render: function render() {
//
//        return (
//                React.createElement('div', null,
//                        this.props.activeTab.name === 'Overview' ? React.createElement('section', {className: 'panel panel-primary'},
//                                React.createElement('div', {className: 'panel-body', id: 'circos', style: {height: "100%"}},
//                                        React.createElement(CircosPanel))) : null,
//                        this.props.activeTab.name === 'BSA' ? React.createElement('section', {className: 'panel panel-primary'},
//                                React.createElement('div', {className: 'panel-body', id: 'parentPool', style: {height: "100%"}},
//                                        React.createElement(ParentPoolPanel))) : null)
//                )
//    }
//});
//
//var CircosTabsComponent = React.createClass({displayName: 'CircosTest',
//    getInitialState: function getInitialState() {
//        return {activeTab: CircosTabData[0]};
//    },
//    handleClick: function handleClick(tab) {
//        this.setState({activeTab: tab});
//    },
//    render: function render() {
//        var heig = $("#leftCol").height().toString();
//        $("#rightCol").height($("#leftCol").height());
//        return React.createElement('div', {className: "container", style: {height: heig, width: "100%"}},
//                React.createElement(CircosTabs, {activeTab: this.state.activeTab, changeTab: this.handleClick}),
//                React.createElement(CircosContent, {activeTab: this.state.activeTab})
//                );
//    }
//});
//
//var renderCircosTabsComponent = function () {
//    React.render(React.createElement(CircosTabsComponent, null), document.getElementById('centerContainer'));
//}

var CircosPanel = React.createClass({displayName: 'circosPanel',
    
    render: function () {

        return (
            React.createElement("div", {style: {width: $('#centerContainer').width(), float: "right"}, id: 'centerTabs'},
                        React.createElement("ul", {className: "nav nav-pills"},
                                React.createElement("li", {className: "active"}, React.createElement("a", {'data-target': "#overviewTab", 'data-toggle': "tab"}, React.createElement('strong', null, "Overview"))),
                                React.createElement("li", {className: ""}, React.createElement("a", {'data-target': "#bsaTab", 'data-toggle': "tab"}, React.createElement('strong', null, "BSA"))),
                                React.createElement('li', {style: {float: 'right'}}, React.createElement('a', {href: '#'}, React.createElement('strong', null, "User manual")))),
                        React.createElement("div", {className: "panel panel-primary"},
                                React.createElement("div", {className: "tab-content"},
                                React.createElement("br"),
                                React.createElement("div", {className: "tab-pane active", id: "overviewTab"},
                                        React.createElement(OverviewTab, { projectName: this.props.projectName})),
                                        React.createElement("div", {className: "tab-pane", id: "bsaTab"},
                                        React.createElement(BSATab, {projectName: this.props.projectName}))),
                                React.createElement("br")

                                )));
    
    }
    });
    
    var renderCircosPanel = function () {
    
    React.render(
            React.createElement(CircosPanel),
            document.getElementById("centerContainer")
            );
}