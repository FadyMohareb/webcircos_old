var converter = new Showdown.converter();

var CircosTabData = [{name: 'Overview', isActive: true}, {name: 'Parent-pool', isActive: false}];

var ProperListRender = React.createClass({displayName: "ProperListRender",
    getInitialState: function getInitialState() {
        return { currProj: this.props.list[0] }; // need a method to check whether user is logged or not
    },
    render: function () {
        return (
                React.createElement('div', {className: 'btn-group'},
                        React.createElement('button', {className: 'btn btn-default dropdown-toggle', 'data-toggle': "dropdown", 'aria-haspopup': "true", 'aria-expanded': 'false'}, this.state.currProj,
                                React.createElement('span', {className: 'caret'})),
                        React.createElement('ul', {className: 'dropdown-menu'},
                                
                                this.props.list.map(function (projectName)
                                {
                                    handleProjectChange: function handleProjectChange(event) {
                                        this.state.currProj = event.target.id;
                                        event.preventDefault();
                                        console.log('CURR PROJ TEST: ' + this.state.currProj );
                                        React.render(React.createElement(FilesPanel, { projectName: this.state.currProj }), document.getElementById('filesContainer'));
                                    
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
        return {view: {showNewProjModal: false } }; // need a method to check whether user is logged or not
    },
    handleShowNewProjModal: function handleShowNewProjModal() {
        this.setState({view: {showNewProjModal: true}});
    },
    handleHideNewProjModal: function handleHideNewProjModal() {
        this.setState({view: {showNewProjModal: false}});
        $(".modal-backdrop.in").remove();
    },
//    used to define any props accessed by this.props
    
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
                var list = [];
                if (ProjectList[0].length !== 0)
                {
                    var n = ProjectList.length;
                    
                    for (var i = 0; i < n; i++)
                    {
                        var ProjectName = ProjectList[i];
                        list[i] = ProjectName;
                    }
                    React.render(React.createElement(ProperListRender, {list: list}), document.getElementById('projects'));
                    
                }
                 
            },
            error: function (status, err) {
                console.error(status, err.toString());
            }});

    },
    componentWillMount: function ()
    {
        this.getProjects();
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

