var converter = new Showdown.converter();

var ProjectsPanelAnon = React.createClass({displayName: "projectsPanelAnon",
    
    render: function () {
        return (React.createElement('div', {className: "panel panel-primary"},
                React.createElement('div', {className: "panel-heading"}, "Projects  ",
                        React.createElement('button', {type: 'button', className: 'btn btn-primary btn-sm', onClick: this.handleShowNewProjModal},
                                React.createElement('span', {className: 'glyphicon glyphicon-plus', 'aria-hidden': 'true'}))),
                React.createElement('div', {className: "panel-body"}, "Sign in to access your projects"))
                );
    }
});

var renderProjectsPanelAnon = function () {
    React.render(
            React.createElement(ProjectsPanelAnon),
            document.getElementById("projectsContainer")
            );
};

