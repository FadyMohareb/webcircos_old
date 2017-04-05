var converter = new Showdown.converter();

var ProjectsPanelAnon = React.createClass({displayName: "projectsPanelAnon",
    render: function () {
        return (React.createElement('div', {className: "panel panel-primary"},
                React.createElement('div', {className: "panel-heading"}, "Projects  "),
                        React.createElement('div', {className: "panel-body"}, 
                        React.createElement('div', null, "Sign in to access your projects")))
                );
    }
});

var renderProjectsPanelAnon = function () {
    React.render(
            React.createElement(ProjectsPanelAnon),
            document.getElementById("projectsContainer")
            );
};

