var ProjectsDropdownRemove = React.createClass({displayName: "ProjectsDropdownRemove",
    getFiles: function(projectName)
    {
        console.log('Getting files from the project: ' + projectName);
        var project = projectName;
        var fd = new FormData();
        fd.append('projectName', project);
        $.ajax({
            url: "/import/getAll",
            type: 'POST',
            processData: false,
            contentType: false,
            data: fd,
            success: function (data)
            {
                var list = [];
                    list[0] = '--- ';
                    
                var filesList = data.message;
                if (filesList !== "" && filesList !== null)
                {
                    var filesSplited = filesList.split("\t");
                    
                    for (var i = 0; i < filesSplited.length; i++)
                    {
                        if (filesSplited[i] !== "")
                        {
                            list[i+1]=filesSplited[i];
                        }
                    }
                }
                console.log(list);
                return React.render(React.createElement(FilesDropdownRemove, { filesList: list }), document.getElementById('filesListRemove'));
        
            },
            error: function (status, err) 
            {
                console.log("Error: "+status+" "+err.toString());
            }
        });
    },
    getInitialState: function getInitialState() 
    {   this.getFiles(this.props.projectsList[0]);
        RemoveStructure.projectName = this.props.projectsList[0];
        return {activeProjectRemove: this.props.projectsList[0]};
    },
    renderBlock: function renderBlock(projectsList, parent)
    { 
        return projectsList.map(function (projectName)
        {
            handleProjectChange: function handleProjectChange(event) {
                
                event.preventDefault();
                
                parent.state.activeProjectRemove = event.target.id;
                RemoveStructure.projectName = parent.state.activeProjectRemove;
                $('#projectBtnRemove').children().first().text(parent.state.activeProjectRemove + ' ');
                parent.getFiles(parent.state.activeProjectRemove);
            };
            return (React.createElement("li",{onClick: handleProjectChange, id: projectName}, projectName));
        });
    },
    render: function () 
    { console.log('List goes as follows in dropdown: ' + this.props.projectsList);
        return React.createElement('div', {className: 'btn-group'},
                        React.createElement('button', {className: 'btn btn-default dropdown-toggle',
                            'data-toggle': "dropdown", 'aria-haspopup': "true",
                            'aria-expanded': 'false', id: 'projectBtnRemove'}, this.state.activeProjectRemove + ' ',
                                React.createElement('span', {className: 'caret'})),
                        React.createElement('ul', {className: 'dropdown-menu'},
                                this.renderBlock(this.props.projectsList, this))
                );
    }
});

var FilesDropdownRemove = React.createClass({displayName: "FilesDropdownRemove",
    getInitialState: function getInitialState()
    {   RemoveStructure.fileName = this.props.filesList[0];
        return {activeFileRemove: this.props.filesList[0]};
    },
    componentWillReceiveProps: function (newProperties)
    {   
        this.state.activeFileRemove = newProperties.filesList[0];
        RemoveStructure.fileName = this.state.activeFileRemove;
        $('#fileBtnRemove').children().first().text(this.state.activeFileRemove + ' ');
    },
    renderFilesBlock: function renderBlock(filesList, parent)
    {
        return filesList.map(function (fileName)
        {
            handleFileChange: function handleFileChange(event)
            {
                event.preventDefault();
                parent.state.activeFileRemove = event.target.id;
                RemoveStructure.fileName = parent.state.activeFileRemove;
                $('#fileBtnRemove').children().first().text(parent.state.activeFileRemove + ' ');
            }
            ;
            return (React.createElement("li", {onClick: handleFileChange, id: fileName}, fileName));
        });
    },
    render: function ()
    {
        return React.createElement('div', {className: 'btn-group'},
                React.createElement('button', {className: 'btn btn-default dropdown-toggle',
                    'data-toggle': "dropdown", 'aria-haspopup': "true", 'aria-expanded': 'false', id: 'fileBtnRemove'},
                        this.state.activeFileRemove + ' ',
                        React.createElement('span', {className: 'caret'})),
                React.createElement('ul', {className: 'dropdown-menu', style: {width: 'inherit'}},
                        this.renderFilesBlock(this.props.filesList, this)));
    }
});

   
var RemoveModal = React.createClass({className: "removeModal",
    componentDidMount: function componentDidMount()
    {
        $(this.getDOMNode()).modal('show');
        $(this.getDOMNode()).on('hidden.bs.modal', this.props.handleHideRemoveModal);
        
    },
    componentWillMount: function componentWillMount()
    {
        this.getProjects();
    },
    getProjects: function getProjects()
    { 
        console.log('Im here');
        
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
            list[0] = '---';
            if (ProjectList[0].length !== 0)
            {
                
                var n = ProjectList.length;
                for (var i = 0; i < n; i++)
                {
                    var ProjectName = ProjectList[i];
                    list[i+1] = ProjectName;
                    
                }
                
                console.log('List goes as follows: ' + list);
                 return React.render(React.createElement('div', {className: 'container'},
                        React.createElement(ProjectsDropdownRemove, {projectsList: list})),document.getElementById('projectsListRemove'));
                
            }
        },
        error: function (status, err) {
            console.error(status, err.toString());
        }});
    },
    
    handleSubmit: function(e)
    {   
        e.preventDefault();
        RemoveStructure.validateValues();
        var fileName = RemoveStructure.fileName;
        var projectName = RemoveStructure.projectName;
        
        alert('Project name: ' + projectName + '\nFile name: ' + fileName );
        
    },
    render: function () {
        return (React.createElement('div', {className: 'modal', id: 'importModal'},
                React.createElement('div', {className: 'modal-dialog'},
                        React.createElement('div', {className: 'modal-content'},
                                React.createElement('div', {className: 'modal-header'},
                                        React.createElement('button', {type: 'button', className: 'close',
                                            'data-dismiss': 'modal', 'aria-label': 'Close'},
                                                React.createElement('span', {'aria-hidden': 'true'}, '\xD7')),
                                        React.createElement('h3', {className: 'modal-title'}, 'Remove file')),
                                React.createElement('div', {className: 'modal-body', id: 'removePanelBody'},
                                React.createElement('label', {for: 'projectsListRemove'}, "Project: "),
                                React.createElement('div', {className: 'container', id: 'projectsListRemove'}),
                                React.createElement('label', {for: 'filesListRemove'}, "File: "),
                                React.createElement('div', {className: 'container', id: 'filesListRemove'})),
                                        React.createElement(this.getProjects),
                                React.createElement('div', {className: 'modal-footer', 'float': 'left'},
                                        React.createElement('button', {id: 'removeBtn', className: 'btn btn-primary', onClick: this.handleSubmit},
                                                'Remove file'))
                        ))
                ));
    },
    
    propTypes: {
        handleHideRemoveModal: React.PropTypes.func.isRequired
    }
    });