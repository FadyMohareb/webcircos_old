/* global React, Showdown, SecurityContextHolder, RequestContextHolder, UploadModal */

var converter = new Showdown.converter();

var FilesDropdownSequence = React.createClass({displayName: "FilesDropdownSequence",
    getInitialState: function getInitialState() {
        return {activeFileSequence: this.props.filesList[0]}
    },
    componentWillReceiveProps: function(newProperties) {
      this.setState({ activeFileSequence: newProperties.filesList[0] });
    },
    renderFilesBlockSequence: function renderBlockSequence(filesList, parent) {

        return filesList.map(function (fileName)
        {

            handleFileChangeSequence: function handleFileChangeSequence(event) {

                event.preventDefault();
                parent.state.activeFileSequence = event.target.id;
                $('#sequenceBtn').children().first().text(parent.state.activeFileSequence + ' ');

            };

            return (React.createElement("li", {onClick: handleFileChangeSequence, id: fileName}, fileName));
        })
    },
    render: function () {
        var btnId = this.props.fileType + 'Btn';
        console.log('Render state: ' + this.state.activeFileSequence);

        return React.createElement('div', {className: 'btn-group'},
                React.createElement('button', {className: 'btn btn-default dropdown-toggle',
                    'data-toggle': "dropdown", 'aria-haspopup': "true", 'aria-expanded': 'false', id: btnId},
                        this.state.activeFileSequence + ' ',
                        React.createElement('span', {className: 'caret'})),
                React.createElement('ul', {className: 'dropdown-menu'},
                        this.renderFilesBlockSequence(this.props.filesList, this))
                )
    }



});

var FilesDropdownAlignment = React.createClass({displayName: "FilesDropdownAlignment",
    getInitialState: function(){
        return {activeFileAlignment: this.props.filesList[0]};
    },
    componentWillReceiveProps: function(newProperties) {
      this.setState({ activeFileAlignment: newProperties.filesList[0] });
    },
    renderFilesBlock: function renderBlock(filesList, parent) {

        return filesList.map(function (fileName)
        {
            handleFileChange: function handleFileChange(event) {

                event.preventDefault();
                parent.setState({activeFileAlignment: event.target.id});
                $('#alignmentBtn').children().first().text(parent.state.activeFileAlignment);

            };

            return (React.createElement("li", {onClick: handleFileChange, id: fileName}, fileName));
        })
    },
    render: function () {
        var btnId = this.props.fileType + 'Btn';

        return React.createElement('div', {className: 'btn-group'},
                React.createElement('button', {className: 'btn btn-default dropdown-toggle',
                    'data-toggle': "dropdown", 'aria-haspopup': "true", 'aria-expanded': 'false', id: btnId},
                        this.state.activeFileAlignment + ' ',
                        React.createElement('span', {className: 'caret'})),
                React.createElement('ul', {className: 'dropdown-menu'},
                        this.renderFilesBlock(this.props.filesList, this))
                )
    }


});

var FilesDropdownVariants = React.createClass({displayName: "FilesDropdownVariants",
    getInitialState: function(){
        return {activeFileVariants: this.props.filesList[0]};
    },
    componentWillReceiveProps: function(newProperties) {
      this.setState({ activeFileVariants: newProperties.filesList[0] });
    },
    renderFilesBlockVariants: function renderBlockVariants(filesList, parent) {

        return filesList.map(function (fileName)
        {
            
            handleFileChangeVariants: function handleFileChangeVariants(eventVariants) {

                event.preventDefault();
                parent.state.activeFileVariants = eventVariants.target.id;
                
                $('#variantsBtn').children().first().text(parent.state.activeFileVariants + ' ');

            }
            ;

            return (React.createElement("li", {onClick: handleFileChangeVariants, id: fileName}, fileName));
        })
    },
    render: function () {
        var btnId = this.props.fileType + 'Btn';

        return React.createElement('div', {className: 'btn-group'},
                React.createElement('button', {className: 'btn btn-default dropdown-toggle',
                    'data-toggle': "dropdown", 'aria-haspopup': "true", 'aria-expanded': 'false', id: btnId},
                        this.state.activeFileVariants + ' ',
                        React.createElement('span', {className: 'caret'})),
                React.createElement('ul', {className: 'dropdown-menu'},
                        this.renderFilesBlockVariants(this.props.filesList, this))
                )
    }



});

var FilesDropdownAnnotation = React.createClass({displayName: "FilesDropdownAnnotation",
    getInitialState: function(){
        return {activeFileAnnotation: this.props.filesList[0]};
    },
    componentWillReceiveProps: function(newProperties) {
      this.setState({ activeFileAnnotation: newProperties.filesList[0] });
    },
    renderFilesBlock: function renderFilesBlock(filesList, parent){
      
        return filesList.map(function (fileName)
        {
            handleFileChange: function handleFileChange(event) {

                event.preventDefault();
                parent.state.activeFileAnnotation = event.target.id;
                $('#annotationBtn').children().first().text(parent.state.activeFileAnnotation + ' ');

            }
            ;

            return (React.createElement("li", {onClick: handleFileChange, id: fileName}, fileName));
        })
    },
    render: function () {
        var btnId = this.props.fileType + 'Btn';

        return React.createElement('div', {className: 'btn-group'},
                React.createElement('button', {className: 'btn btn-default dropdown-toggle',
                    'data-toggle': "dropdown", 'aria-haspopup': "true", 'aria-expanded': 'false', id: btnId},
                        this.state.activeFileAnnotation + ' ',
                        React.createElement('span', {className: 'caret'})),
                React.createElement('ul', {className: 'dropdown-menu'},
                        this.renderFilesBlock(this.props.filesList, this))
                )
    }



});

var FilesDropdownExpression = React.createClass({displayName: "FilesDropdownExpression",
    getInitialState: function(){
        return {activeFileExpression: this.props.filesList[0]};
    },
    componentWillReceiveProps: function(newProperties) {
      this.setState({ activeFileExpression: newProperties.filesList[0] });
    },
    renderFilesBlock: function renderBlock(filesList, parent) {

        return filesList.map(function (fileName)
        {
            handleFileChange: function handleFileChange(event) {

                event.preventDefault();
                parent.state.activeFileExpression = event.target.id;
                console.log('Active file: ' + parent.state.activeFileExpression);
                $('#expressionBtn').children().first().text(parent.state.activeFileExpression + ' ');

            }
            ;

            return (React.createElement("li", {onClick: handleFileChange, id: fileName}, fileName));
        })
    },
    render: function () {
        var btnId = this.props.fileType + 'Btn';

        return React.createElement('div', {className: 'btn-group'},
                React.createElement('button', {className: 'btn btn-default dropdown-toggle',
                    'data-toggle': "dropdown", 'aria-haspopup': "true", 'aria-expanded': 'false', id: btnId},
                        this.state.activeFileExpression + ' ',
                        React.createElement('span', {className: 'caret'})),
                React.createElement('ul', {className: 'dropdown-menu'},
                        this.renderFilesBlock(this.props.filesList, this))
                )
    }



});

var FilesDropdownDifExpression = React.createClass({displayName: "FilesDropdownDifExpression",
    getInitialState: function(){
        return {activeFileDifExpression: this.props.filesList[0]};
    },
    componentWillReceiveProps: function(newProperties) {
      this.setState({ activeFileDifExpression: newProperties.filesList[0] });
    },
    renderFilesBlock: function renderBlock(filesList, parent) {

        return filesList.map(function (fileName)
        {
            handleFileChange: function handleFileChange(event) {

                event.preventDefault();
                parent.state.activeFileDifExpression = event.target.id;
                console.log('Active file: ' + parent.state.activeFileDifExpression);
                $('#difExpressionBtn').children().first().text(parent.state.activeFileDifExpression + ' ');

            }
            ;

            return (React.createElement("li", {onClick: handleFileChange, id: fileName}, fileName));
        })
    },
    render: function () {
        var btnId = this.props.fileType + 'Btn';

        return React.createElement('div', {className: 'btn-group'},
                React.createElement('button', {className: 'btn btn-default dropdown-toggle',
                    'data-toggle': "dropdown", 'aria-haspopup': "true", 'aria-expanded': 'false', id: btnId},
                        this.state.activeFileDifExpression + ' ',
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

                    if (list.length > 0){
                        if (type === "sequence")
                            return React.render(React.createElement('div', {className: 'container'},
                                    React.createElement(FilesDropdownSequence, {filesList: list, fileType: type})),
                                    document.getElementById(type));

                        if (type === "alignment")
                            return React.render(React.createElement('div', {className: 'container'},
                                    React.createElement(FilesDropdownAlignment, {filesList: list, fileType: type})),
                                    document.getElementById(type));

                        if (type === "variants")
                            return React.render(React.createElement('div', {className: 'container'},
                                    React.createElement(FilesDropdownVariants, {filesList: list, fileType: type})),
                                    document.getElementById(type));

                        if (type === "annotation")
                            return React.render(React.createElement('div', {className: 'container'},
                                    React.createElement(FilesDropdownAnnotation, {filesList: list, fileType: type})),
                                    document.getElementById(type));

                        if (type === "expression")
                            return React.render(React.createElement('div', {className: 'container'},
                                    React.createElement(FilesDropdownExpression, {filesList: list, fileType: type})),
                                    document.getElementById(type));      

                        if (type === "difExpression")
                            return React.render(React.createElement('div', {className: 'container'},
                                    React.createElement(FilesDropdownDifExpression, {filesList: list, fileType: type})),
                                    document.getElementById(type)); 
                }}
            },
            error: function (status, err) {
                console.log("Panel not refreshed");
                console.error(status, err.toString());
            }});
    },
    render: function () {
        return (React.createElement('div', {className: "container"},
                React.createElement('label', {for : 'sequenceBtn'}, 'Reference sequence: '),
                React.createElement('div', {className: 'container', id: 'sequence'},
                        React.createElement(this.contentUpdateProject, {panelType: "sequence"})),
                React.createElement('br'),
                React.createElement('label', {for : 'alignmentBtn'}, 'Coverage depth: '),
                React.createElement('div', {className: 'container', id: 'alignment'},
                        React.createElement(this.contentUpdateProject, {panelType: "alignment"})),
                React.createElement('br'),
                React.createElement('label', {for : 'variantsBtn'}, 'SNP density: '),
                React.createElement('div', {className: 'container', id: 'variants'},
                        React.createElement(this.contentUpdateProject, {panelType: "variants"})),
                React.createElement('br'),
                React.createElement('label', {for : 'annotationBtn'}, 'Annotation: '),
                React.createElement('div', {className: 'container', id: 'annotation'},
                        React.createElement(this.contentUpdateProject, {panelType: "annotation"})),
                React.createElement('br'),
                React.createElement('label', {for : 'expressionBtn'}, 'Genes expression: '),
                React.createElement('div', {className: 'container', id: 'expression'},
                        React.createElement(this.contentUpdateProject, {panelType: "expression"})),
                React.createElement('br'),
                React.createElement('label', {for : 'difExpressionBtn'}, 'Differential expression: '),
                React.createElement('div', {className: 'container', id: 'difExpression'},
                        React.createElement(this.contentUpdateProject, {panelType: "difExpression"})))
                );
    }
});

var renderFilesGeneralPanel = function () {
    React.render(React.createElement(FilesGeneralPanel), document.getElementById('filesContainer'));
};

