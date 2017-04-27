
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
        this.getFiles();
    },
    
    getFiles: function()
    {
        
        var project = this.props.projectName;
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
    
    handleSubmit: function(e)
    {   
        e.preventDefault();
	RemoveStructure.validateValues();
	var fileName = RemoveStructure.fileName;
	var projectName = RemoveStructure.projectName;
        console.log('PROJECT: ' + projectName);
        
	var fd = new FormData();
	fd.append('projectName', projectName);
	fd.append('fileName', fileName);
	$.ajax({
		url: "/controller/remove",
		type: 'POST',
		processData: false,
		contentType: false,
		data: fd,
	success: function (data)
	{
            //error
		if(data.errors == null) {
                    
                        location = '/home';
                    }
	},        
	error: function (status, err) 
	{
		console.log("Error: "+status+" "+err.toString());
	}});
        
    },
    render: function () {
        return (React.createElement('div', {className: 'modal', id: 'removeModal'},
                React.createElement('div', {className: 'modal-dialog'},
                        React.createElement('div', {className: 'modal-content'},
                                React.createElement('div', {className: 'modal-header'},
                                        React.createElement('button', {type: 'button', className: 'close',
                                            'data-dismiss': 'modal', 'aria-label': 'Close'},
                                                React.createElement('span', {'aria-hidden': 'true'}, '\xD7')),
                                        React.createElement('h3', {className: 'modal-title'}, 'Remove file')),
                                React.createElement('div', {className: 'modal-body', id: 'removePanelBody'},
                                React.createElement('label', {for: 'filesListRemove'}, "File: "),
                                React.createElement('div', {className: 'container', id: 'filesListRemove'})),
                                        React.createElement(this.getFiles),
                                React.createElement('div', {className: 'modal-footer', 'float': 'left'},
                                        React.createElement('button', {id: 'removeBtn', className: 'btn btn-primary', onClick: this.handleSubmit},
                                                'Remove file'))
                        ))
                ));
    },
    
    propTypes: {
        handleHideRemoveModal: React.PropTypes.func.isRequired,
        projectName: React.PropTypes.string
    }
    });