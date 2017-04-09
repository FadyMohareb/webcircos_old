/* global React, Showdown, SecurityContextHolder, RequestContextHolder, UploadModal */

var converter = new Showdown.converter();

var FilesDropdown = React.createClass({displayName: "FilesDropdown",
    getInitialState: function getInitialState() {
        return {activeFile: this.props.filesList[0]}
    },
    renderFilesBlock: function renderBlock(filesList, parent) {

        return filesList.map(function (fileName)
        {
            handleFileChange: function handleFileChange(event) {
                
                event.preventDefault();
                parent.state.activeFile = event.target.id;
//                var btnId = parent.props.fileType + 'Btn';
//                console.log(btnId);
//                console.log('Active file: ' + parent.state.activeFile);
//                $('#btnId').children().first().text(parent.state.activeFile + ' ');

            }
            ;

            return (React.createElement("li", {onClick: handleFileChange, id: fileName}, fileName));
        })
    },
    render: function () {
        var btnId = this.props.fileType + 'Btn';
            
        return React.createElement('div', {className: 'btn-group'},
                React.createElement('button', {className: 'btn btn-default dropdown-toggle', 'data-toggle': "dropdown", 'aria-haspopup': "true", 'aria-expanded': 'false'}, this.state.activeFile + ' ',
                        React.createElement('span', {className: 'caret'})),
                React.createElement('ul', {className: 'dropdown-menu'},
                        this.renderFilesBlock(this.props.filesList, this))
                )
    }



});


var FilesGeneralPanel = React.createClass({className: "FilesGeneralPanel",
    getInitialState: function getInitialState() {
        return {view: {showUploadModal: false}};
    },
    handleShowUploadModal: function handleShowUploadModal() {
        this.setState({view: {showUploadModal: true}});
    },
    handleHideUploadModal: function handleHideUploadModal() {
        this.setState({view: {showUploadModal: false}});
        $(".modal-backdrop.in").remove();
    },
    contentUpdateProject: function (panelType)
    {
        var projectName = this.props.projectName;
        var fd = new FormData();
        var type = panelType.panelType;
        fd.append('panelType', type);
        fd.append('projectName', projectName);
        $.ajax({
            url: "/refresh",
            type: 'POST',
            processData: false,
            contentType: false,
            data: fd,
            success: function (data)
            {
                var filesList = data.errors;
                if (filesList !== "")
                {
                    var substring0 = filesList.substring(0, 1);
                    if (substring0 === "[")
                    {
                        filesList = filesList.substring(1, filesList.length - 1);
                        var filesSplited = filesList.split(",");
                        var n = filesSplited.length;
                        var list = [];
                        for (var i = 0; i < n; i++)
                        {
                            list[i] = filesSplited[i];
                        }
                    } else
                    {
                        var filesSplited = filesList.split("\t");
                        var list = [];
                        for (var i = 0; i < filesSplited.length; i++)
                        {
                            if (filesSplited[i] !== "")
                            {
                                var filesSplited2 = filesSplited[i].split("/");
                                var fileName = filesSplited2[filesSplited2.length - 1];
                                list[i] = fileName;
                            }
                        }


                    }

                    if (list.length > 0)
                        return React.render(React.createElement('div', {className: 'container'},
                                React.createElement(FilesDropdown, {filesList: list, fileType: type})),
                                document.getElementById(type));
                }
            },
            error: function (status, err) {
                console.log("Panel not refreshed");
                console.error(status, err.toString());
            }});
    },
    render: function () {
        return (React.createElement('div', {className: "container"},
                React.createElement('label', {for: 'sequenceBtn'}, 'Reference sequence: '),
                React.createElement('div', {className: 'container', id: 'sequence'},
                        React.createElement(this.contentUpdateProject, {panelType: "sequence"})),
                React.createElement('br'),
                React.createElement('label', {for: 'alignmentBtn'}, 'Coverage depth: '),
                React.createElement('div', {className: 'container', id: 'alignment'},
                        React.createElement(this.contentUpdateProject, {panelType: "alignment"})),
                React.createElement('br'),
                React.createElement('label', {for: 'variantsBtn'}, 'SNP density: '),
                React.createElement('div', {className: 'container', id: 'variants'},
                        React.createElement(this.contentUpdateProject, {panelType: "variants"})),
                React.createElement('br'),
                React.createElement('label', {for: 'annotationBtn'}, 'Annotation: '),
                React.createElement('div', {className: 'container', id: 'annotation'},
                        React.createElement(this.contentUpdateProject, {panelType: "annotation"})),
                React.createElement('br'),
                React.createElement('label', {for: 'expressionBtn'}, 'Genes expression: '),
                React.createElement('div', {className: 'container', id: 'expression'},
                        React.createElement(this.contentUpdateProject, {panelType: "expression"})),
                React.createElement('br'),
                React.createElement('label', {for: 'difExpressionBtn'}, 'Differential expression: '),
                React.createElement('div', {className: 'container', id: 'difExpression'},
                        React.createElement(this.contentUpdateProject, {panelType: "difExpression"})))
                );
    }
});

var renderFilesGeneralPanel = function () {
    React.render(React.createElement(FilesGeneralPanel), document.getElementById('filesContainer'));
};

