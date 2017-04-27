
var FilesTabsComponent = React.createClass({displayName: 'FilesTest',
    render: function render() {
        return (
                React.createElement("div", {className: 'container', style: {width: "inherit"}},
                        React.createElement("ul", {className: "nav nav-pills"},
                                React.createElement("li", {className: "active"}, React.createElement("a", {'data-target': "#overview", 'data-toggle': "tab"}, React.createElement('strong', null, "Overview"))),
                                React.createElement("li", {className: ""}, React.createElement("a", {'data-target': "#bsa", 'data-toggle': "tab"}, React.createElement('strong', null, "BSA")))),
                        React.createElement("div", {className: "panel panel-primary"},
                                React.createElement("div", {className: "tab-content"},
                                React.createElement("br"),
                                React.createElement("div", {className: "tab-pane active", id: "overview"},
                                        React.createElement(FilesGeneralPanel, { projectName: this.props.projectName})),
                                        React.createElement("div", {className: "tab-pane", id: "bsa"},
                                        React.createElement(FilesParentPoolPanel, {projectName: this.props.projectName}))),
                                React.createElement("br")


                                )));
    }
});
