var converter = new Showdown.converter();
var ProjectsDropdownImport = React.createClass({displayName: "ProjectsDropdownImport",
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
                return React.render(React.createElement(FilesDropdownImport, { filesList: list }), document.getElementById('filesListImport'));
        
            },
            error: function (status, err) 
            {
                console.log("Error: "+status+" "+err.toString());
            }
        });
    },
    getInitialState: function getInitialState() 
    {   this.getFiles(this.props.projectsList[0]);
//        ImportStructure.oldProjectName = this.props.projectsList[0];
        return {activeProjectImport: this.props.projectsList[0]};
    },
    renderBlock: function renderBlock(projectsList, parent)
    { 
        return projectsList.map(function (projectName)
        {
            handleProjectChange: function handleProjectChange(event) {
                
                event.preventDefault();
                
                parent.state.activeProjectImport = event.target.id;
                ImportStructure.oldProjectName = parent.state.activeProjectImport;
                $('#projectBtnImport').children().first().text(parent.state.activeProjectImport + ' ');
                parent.getFiles(parent.state.activeProjectImport);
            };
            return (React.createElement("li",{onClick: handleProjectChange, id: projectName}, projectName));
        });
    },
    render: function () 
    { console.log('List goes as follows in dropdown: ' + this.props.projectsList);
        return React.createElement('div', {className: 'btn-group'},
                        React.createElement('button', {className: 'btn btn-default dropdown-toggle',
                            'data-toggle': "dropdown", 'aria-haspopup': "true",
                            'aria-expanded': 'false', id: 'projectBtnImport'}, this.state.activeProjectImport + ' ',
                                React.createElement('span', {className: 'caret'})),
                        React.createElement('ul', {className: 'dropdown-menu'},
                                this.renderBlock(this.props.projectsList, this))
                );
    }
});

var FilesDropdownImport = React.createClass({displayName: "FilesDropdownImport",
    getInitialState: function getInitialState()
    {   ImportStructure.fileName = this.props.filesList[0];
        return {activeFileImport: this.props.filesList[0]};
    },
    componentWillReceiveProps: function (newProperties)
    {   
        this.state.activeFileImport = newProperties.filesList[0];
        ImportStructure.fileName = this.state.activeFileImport;
        $('#fileBtnImport').children().first().text(this.state.activeFileImport + ' ');
    },
    renderFilesBlock: function renderBlock(filesList, parent)
    {
        return filesList.map(function (fileName)
        {
            handleFileChange: function handleFileChange(event)
            {
                event.preventDefault();
                parent.state.activeFileImport = event.target.id;
                ImportStructure.fileName = parent.state.activeFileImport;
                $('#fileBtnImport').children().first().text(parent.state.activeFileImport + ' ');
            }
            ;
            return (React.createElement("li", {onClick: handleFileChange, id: fileName}, fileName));
        });
    },
    render: function ()
    {
        return React.createElement('div', {className: 'btn-group'},
                React.createElement('button', {className: 'btn btn-default dropdown-toggle',
                    'data-toggle': "dropdown", 'aria-haspopup': "true", 'aria-expanded': 'false', id: 'fileBtnImport'},
                        this.state.activeFileImport + ' ',
                        React.createElement('span', {className: 'caret'})),
                React.createElement('ul', {className: 'dropdown-menu', style: {width: 'inherit'}},
                        this.renderFilesBlock(this.props.filesList, this)));
    }
});

   
var ImportModalTest = React.createClass({className: "importModalTest",
    componentDidMount: function componentDidMount()
    {
        $(this.getDOMNode()).modal('show');
        $(this.getDOMNode()).on('hidden.bs.modal', this.props.handleHideImportModal);
        
    },
    componentWillMount: function componentWillMount()
    {
        this.getProjects();
    },
    handleSubmit: function(e)
    {
//        alert('This button will import file from another project');
        e.preventDefault();
        ImportStructure.validateValues();
        var oldFileName = ImportStructure.fileName;
        var oldProjectName = ImportStructure.oldProjectName;
        var newProjectName = ImportStructure.newProjectName;
        
        var fd = new FormData();
        fd.append('newProjectName', newProjectName);
        fd.append('oldProjectName', oldProjectName);
        fd.append('oldFileName', oldFileName);
        
        console.log(fd);
        $.ajax({
            url: "/import/copyFile",
            type: 'POST',
            processData: false,
            contentType: false,
            data: fd,
            success: function (data)
            {
                if (data.message !== "" && data.message !== null)
                    alert(data.message);
                else
                    location = '/home';
            },
            error: function (status, err) 
            {
                alert("File not sended");
                console.error(status, err.toString());
            }});
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
                        React.createElement(ProjectsDropdownImport, {projectsList: list})),document.getElementById('projectsListImport'));
                
            }
        },
        error: function (status, err) {
            console.error(status, err.toString());
        }});
    },
    render: function () {
        return (React.createElement('div', {className: 'modal', id: 'importModal'},
                React.createElement('div', {className: 'modal-dialog'},
                        React.createElement('div', {className: 'modal-content'},
                                React.createElement('div', {className: 'modal-header'},
                                        React.createElement('button', {type: 'button', className: 'close',
                                            'data-dismiss': 'modal', 'aria-label': 'Close'},
                                                React.createElement('span', {'aria-hidden': 'true'}, '\xD7')),
                                        React.createElement('h3', {className: 'modal-title'}, 'Import file')),
                                React.createElement('div', {className: 'modal-body', id: 'importPanelBody'},
                                React.createElement('label', {for: 'projectsListImport'}, "Project: "),
                                React.createElement('div', {className: 'container', id: 'projectsListImport'}),
                                React.createElement('label', {for: 'filesListImport'}, "File: "),
                                React.createElement('div', {className: 'container', id: 'filesListImport'})),
                                        React.createElement(this.getProjects),
                                React.createElement('div', {className: 'modal-footer', 'float': 'left'},
                                        React.createElement('button', {className: 'btn btn-primary', onClick: this.handleSubmit},
                                                React.createElement('strong', null, 'Upload file')))
                        ))
                ));
    },
    propTypes: {
        handleHideImportModal: React.PropTypes.func.isRequired,
        projectName: React.PropTypes.string
    }
});