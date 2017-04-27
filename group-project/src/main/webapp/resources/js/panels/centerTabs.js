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
                                                React.createElement(OverviewTab, {projectName: this.props.projectName})),
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