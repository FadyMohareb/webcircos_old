var converter = new Showdown.converter();

var ProperListRender = React.createClass({displayName: "ProperListRender",
    
    render: function () {
       
        return (
                React.createElement('div', {className: 'btn-group'},
                        React.createElement('button', {className: 'btn btn-default dropdown-toggle', 'data-toggle': "dropdown", 'aria-haspopup': "true", 'aria-expanded': 'false'}, 'Action',
                                React.createElement('span', {className: 'caret'})),
                        React.createElement('ul', {className: 'dropdown-menu'},
                                this.props.list.map(function (projectName)
                                {
                                    handleProjectChange: function handleProjectChange(event) {
                                        event.preventDefault();
                                        console.log(event.target.id);
                                        React.render(React.createElement(FilesPanel, { projectName: event.target.id }), document.getElementById('filesContainer'));
                                    
                                    };
                                    
                                    return (React.createElement("li",{onClick: handleProjectChange, id: projectName}, projectName));
                                })
                        )
                )
                );
    }
});

var ProjectsPanel = React.createClass({displayName: "projectsPanel",
    getInitialState: function getInitialState() {
        return {view: {showNewProjModal: false, userLogged: false, currProj: 'test'}}; // need a method to check whether user is logged or not
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
        return;
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
                        list[i] = ProjectName;
                    }
                    return React.render(React.createElement(ProperListRender, {list: list}), document.getElementById('projects'));
                }
                 
            },
            error: function (status, err) {
                console.error(status, err.toString());
            }});

    },
    componentDidMount: function ()
    {
        this.getProjects()
    },
    handleSubmit: function handleSubmit(projName) {
        this.state.currProj = projName;
    },
    render: function () {
        return (React.createElement('div', {className: "panel panel-primary"},
                React.createElement('div', {className: "panel-heading"}, "Projects  ",
                        React.createElement('button', {type: 'button', className: 'btn btn-primary btn-sm', onClick: this.handleShowNewProjModal},
                                React.createElement('span', {className: 'glyphicon glyphicon-plus', 'aria-hidden': 'true'}))),
                React.createElement('div', {className: "panel-body"},
                        React.createElement("div", {id: 'projects'}, 'Create your first project'),

                        this.state.view.showNewProjModal ? React.createElement(NewProjModal, {handleHideNewProjModal: this.handleHideNewProjModal}) : null))

                );
    }
});

var renderProjectsPanel = function () {
    React.render(
            React.createElement(ProjectsPanel),
            document.getElementById("projectsContainer")
            );
};


