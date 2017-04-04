/* global React, NewProjModal */
var ProperListRender = React.createClass({displayName: "ProperListRender",
    render: function() {
      return (React.createElement("div", null,
            this.props.list.map(function(listValue)
            {
                return (React.createElement("h5",null," "+listValue+"\n"));
                //checkbox instead of line
//                return (React.createElement("input", { type: 'checkbox' }, " "+listValue+"\n"));
            })
        )
      );
    }
  });
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
//    used to define any props accessed by this.props
    getDefaultProps: function ()
    {
        return ;
    },
    getProjects: function getProjects() 
    {
        $.ajax({
            url: "/project/getProjects",
            type: 'GET',
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            success: function (data) 
            {
                console.log(data.errors);
                var ProjectList = data.errors.split("\t");
                if (ProjectList[0].length !== 0)
                {
                    var n = ProjectList.length;
                    var list = [];
                    for (var i = 0; i < n; i++)
                    {
                        var ProjectName = ProjectList[i];
                            list[i]=ProjectName;
                    }
                }
                return React.render(React.createElement(ProperListRender, {list: list}), document.getElementById('projects'));
            },
            error: function (status, err) {
                console.error(status, err.toString());
            }});
        
//        return React.render(React.createElement(ProperListRender, {list: "Add project"}), document.getElementById('projects'));
//        return React.render(React.createElement('h5',null, "I'm here"),document.getElementById('projects'));
    },
    componentDidMount: function()
    {
        this.getProjects()
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
                        React.createElement('div', {className: 'container', id: 'projects'}),
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

