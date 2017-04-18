/* global React, Showdown */

var converter = new Showdown.converter();
var FilesListRender = React.createClass({displayName: "FilesListRender",
    render: function() {
      return (React.createElement("div", null,
            this.props.list.map(function(listValue)
            {
                return (React.createElement("h5",null," "+listValue+"\n"));
            })
        )
      );
    }
   });
var ImportModal = React.createClass({className: "importModal",
    componentDidMount: function componentDidMount()
    {
        $(this.getDOMNode()).modal('show');
        $(this.getDOMNode()).on('hidden.bs.modal', this.props.handleHideImportModal);
    },
    handleSubmit: function (e) 
    {
        e.preventDefault();
        var oldFileName = "S_lycopersicum_chromosomes.2.50.fa";;
        var oldProjectName = "test";
        var newProjectName = this.props.projectName;
        
        var fd = new FormData();
        fd.append('newProjectName', newProjectName);
        fd.append('oldProjectName', oldFileName);
        fd.append('oldFileName', oldFileName);
        
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
    getFiles: function(){
        $.ajax({
            url: "/import/getAll",
            type: 'POST',
            processData: false,
            contentType: false,
            success: function (data)
            {
                var filesList = data.message;
                if (filesList !== "" && filesList !== null)
                {
                    var filesSplited = filesList.split("\n");
                    var list = [];
                    var fileList = [];
                    var projectList = [];
                    var filesSplited2;
                    list[0] = 'Project name: --- File name: --- ';
                    fileList[0] = '---';
                    projectList[0] = '---';
                    var fileName;
                    var projectName;
                    for (var i = 0; i < filesSplited.length; i++)
                    {
                        if (filesSplited[i] !== "")
                        {
                            filesSplited2 = filesSplited[i].split(": ");
                            list[i+1]=filesSplited[i];
                            projectName = filesSplited2[0];
                            fileName = filesSplited2[filesSplited2.length - 1];
                            
                            fileList[i+1] = fileName;
                            projectList[i+1] = projectName;
                        }
                    }
                return React.render(
                        React.createElement('div', {className: 'container'},
                        React.createElement(FilesListRender, {list: list})),document.getElementById('importPanelBody'));
                }
            },
            error: function (status, err) 
            {
                console.log("Error: "+status+" "+err.toString());
            }
        });
    },
    render: function () {
        return (React.createElement('div', {className: 'modal fade'},
                    React.createElement('div', {className: 'modal-dialog'},
                        React.createElement('div', {className: 'modal-content'},
                                React.createElement('div', {className: 'modal-header'},
                                        React.createElement('button', {type: 'button', className: 'close','data-dismiss': 'modal', 'aria-label': 'Close'},
                                                React.createElement('span', {'aria-hidden': 'true'}, '\xD7')),
                                        React.createElement('h3', {className: 'modal-title'}, 'Import file')),
                                        
                                React.createElement('div', {className: 'modal-body', id: 'importPanelBody'},
                                        React.createElement('h4', {className: 'modal-title'}, 'Choose file: '),
                                        React.createElement(this.getFiles),
                                        React.createElement("hr")),
                                        
                                React.createElement('div', {className: 'modal-footer', 'float': 'left'},
                                    React.createElement('button', {className: 'btn btn-primary', onClick: this.handleSubmit},"Import file")))
                        ))
                );
    },
    propTypes: 
    {
        handleHideImportModal: React.PropTypes.func.isRequired,
        projectName: React.PropTypes.string
    }
});
