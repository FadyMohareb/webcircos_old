/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var converter = new Showdown.converter();

var ProjectsPanel = React.createClass({displayName: "projectsPanel",
    getInitialState: function getInitialState() {
        return {view: {showNewProjModal: false, userLogged: false, currProj: null}}; // need a method to check whether user is logged or not
    },
    handleShowNewProjModal: function handleShowNewProjModal() {
        this.setState({view: {showNewProjModal: true}});
    },
    handleHideNewProjModal: function handleHideNewProjModal() {
        this.setState({view: {showNewProjModal: false}});
        $(".modal-backdrop.in").remove();
    },
    handleUserLogged: function handleUserLogged() {
        this.setState({view: {userLogged: true, currProj: null}});
    },
    handleUserAnonym: function handleUserAnonym() {
        this.setState({view: {userLogged: false, currProj: null}});
    },
    getProjects: function getProjects() {
        var projectsList = ''; // need to create a method to get list of projects and display it; dropdown menu should updated so that as the button name current project would be displayed
        return projectsList;
    },
    handleSubmit: function handleSubmit(projName){
        this.state.currProj = projName;
    },
    
    render: function () {
        return (React.createElement('div', {className: "panel panel-primary"},
                React.createElement('div', {className: "panel-heading"}, "Projects  ",
                        React.createElement('button', {type: 'button', className: 'btn btn-primary btn-sm', onClick: this.handleShowNewProjModal},
                                React.createElement('span', {className: 'glyphicon glyphicon-plus', 'aria-hidden': 'true'}))),
                React.createElement('div', {className: "panel-body"},
                this.state.view.userLogged ? "Sign in to access your projects" : 
                        React.createElement("div", {className: 'btn-group'},
                                React.createElement("button", {type: 'button', className: "btn btn-default dropdown-toggle",
                                    'data-toggle': "dropdown", 'aria-haspopup': "true", 'aria-expanded': "false"},
                                        "Current project name  ",
                                        React.createElement("span", {className: 'caret'}),
                                        React.createElement("span", {className: 'sr-only'})),
                                React.createElement("ul", {className: 'dropdown-menu dropdown-menu-right'},
                                        React.createElement("li", {className: 'dropdown-header'}, "Current project name"),
                                        React.createElement("li", {role: 'separator', className: 'divider'}),
                                        React.createElement("li", null,
                                                React.createElement("a", {href: "#", onClick: this.handleSubmit("Project 1 name")}, "Project 1 name")),
                                        React.createElement("li", null,
                                                React.createElement("a", {href: '#', onClick: this.handleSubmit("Project 2 name")}, "Project 2 name")),
                                        React.createElement("li", null,
                                                React.createElement("a", {href: '#', onClick: this.handleSubmit("Project 3 name")}, "Project 3 name"))
                                        )
                                ),
                        this.state.view.showNewProjModal ? React.createElement(NewProjModal, {  }) : null))
                        
                );
    }
});

var renderProjectsPanel = function () {
    React.render(
            React.createElement(ProjectsPanel),
            document.getElementById("projectsContainer")
            );
};

