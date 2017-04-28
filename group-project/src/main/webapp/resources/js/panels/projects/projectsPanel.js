var ProjectsDropdown = React.createClass({displayName: "ProjectsDropdown",
    getInitialState: function getInitialState() {
        
        (!$.cookie('activeProject') || $.cookie('activeProject') === "") ? $.cookie('activeProject', this.props.projectsList[0]) : null;
        console.log($.cookie('activeProject'));
        Structure.projectName = $.cookie('activeProject') ;
        BSAstructure.projectName = $.cookie('activeProject') ;
        RemoveStructure.projectName = $.cookie('activeProject') ;
        ImportStructure.newProjectName = $.cookie('activeProject') ;
            
        
        
        React.render(React.createElement(FilesPanel, { projectName: $.cookie('activeProject') }), document.getElementById('filesContainer'));
        
        return {activeProject: $.cookie('activeProject')};
    },
    renderBlock: function renderBlock(projectsList, parent)
    {
        return projectsList.map(function (projectName)
            {
                handleProjectChange: function handleProjectChange(event) {
                    event.preventDefault();
                    parent.state.activeProject = event.target.id;
                    $('#projectButton').children().first().text(parent.state.activeProject + ' ');
                    Structure.projectName = parent.state.activeProject;
                    BSAstructure.projectName = parent.state.activeProject;
                    ImportStructure.newProjectName = parent.state.activeProject;
                    $.cookie('activeProject', parent.state.activeProject);
                    React.render(React.createElement(FilesPanel, { projectName: $.cookie('activeProject') }), document.getElementById('filesContainer'));
                };
                return (React.createElement("li",{onClick: handleProjectChange, id: projectName}, projectName));
            });
    },
    render: function () 
    {
        return React.createElement('div', {className: 'btn-group'},
                        React.createElement('button', {className: 'btn btn-default dropdown-toggle',
                            'data-toggle': "dropdown", 'aria-haspopup': "true",
                            'aria-expanded': 'false', id: 'projectButton'}, this.state.activeProject + ' ',
                                React.createElement('span', {className: 'caret'})),
                        React.createElement('ul', {className: 'dropdown-menu'},
                                this.renderBlock(this.props.projectsList, this)));
    }
});

var ProjectsPanel = React.createClass({displayName: "projectsPanel",
    getInitialState: function getInitialState() {
        return {view: {showNewProjModal: false}};
    },
    handleShowNewProjModal: function handleShowNewProjModal() {
        this.setState({view: {showNewProjModal: true}});
    },
    handleHideNewProjModal: function handleHideNewProjModal() {
        this.setState({view: {showNewProjModal: false}});
        $(".modal-backdrop.in").remove();
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
                $('#uploadModalBtn').attr("disabled", false);
                $('#removeModalBtn').attr("disabled", false);
                $('#importModalBtn').attr("disabled", false);
                React.render(React.createElement(ProjectsDropdown, {projectsList: list}), document.getElementById('projects'));
            }
            else {
                $('#removeModalBtn').attr("disabled", true);
                $('#uploadModalBtn').attr("disabled", true);
                $('#importModalBtn').attr("disabled", true);
            }
            list.length > 2 ? $('#importModalBtn').attr('disabled', false) : $('#importModalBtn').attr('disabled', true);
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
        return (React.createElement('div', null,
                React.createElement('div', {className: "panel panel-primary"},
                React.createElement('div', {className: "panel-heading"}, React.createElement('strong', null, "Projects "),
                        React.createElement('button', {type: 'button', className: 'btn btn-primary btn-sm', onClick: this.handleShowNewProjModal},
                                React.createElement('span', {className: 'glyphicon glyphicon-plus', 'aria-hidden': 'true'}))),
                React.createElement('div', {className: "panel-body"},
                        React.createElement("div", {id: 'projects'}, 'Create your first project'),
                        this.state.view.showNewProjModal ? React.createElement(NewProjModal, {handleHideNewProjModal: this.handleHideNewProjModal}) : null))
                ));
    }
});

var renderProjectsPanel = function () {
    React.render(
            React.createElement(ProjectsPanel),
            document.getElementById("projectsContainer")
            );
};
