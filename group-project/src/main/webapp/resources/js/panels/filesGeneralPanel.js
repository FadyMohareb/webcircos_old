/* global React, Showdown, SecurityContextHolder, RequestContextHolder, UploadModal */

var converter = new Showdown.converter();

var FilesDropdownSequence = React.createClass({displayName: "FilesDropdownSequence",
    getInitialState: function getInitialState() {
        return {activeFileSequence: this.props.filesList[0]};
    },
    componentWillReceiveProps: function (newProperties) {
        this.state.activeFileSequence = newProperties.filesList[0];
        $('#sequenceBtn').children().first().text(this.state.activeFileSequence + ' ');
    },
    renderFilesBlockSequence: function renderBlockSequence(filesList, parent) {

        return filesList.map(function (fileName)
        {

            handleFileChangeSequence: function handleFileChangeSequence(event) {

                event.preventDefault();
                parent.state.activeFileSequence = event.target.id;
                $('#sequenceBtn').children().first().text(parent.state.activeFileSequence + ' ');

            }
            ;

            return (React.createElement("li", {onClick: handleFileChangeSequence, id: fileName}, fileName));
        })
    },
    render: function () {
        return React.createElement('div', {className: 'btn-group'},
                React.createElement('button', {className: 'btn btn-default dropdown-toggle',
                    'data-toggle': "dropdown", 'aria-haspopup': "true", 'aria-expanded': 'false', id: 'sequenceBtn'},
                        this.state.activeFileSequence + ' ',
                        React.createElement('span', {className: 'caret'})),
                React.createElement('ul', {className: 'dropdown-menu'},
                        this.renderFilesBlockSequence(this.props.filesList, this))  
                )
    }



});

var FilesDropdownGenCoverage = React.createClass({displayName: "FilesDropdownGenCoverage",
    getInitialState: function () {
        return {activeFileGenCoverage: this.props.filesList[0]};
    },
    componentWillReceiveProps: function (newProperties) {
        this.state.activeFileGenCoverage = newProperties.filesList[0];
        $('#genCoverageBtn').children().first().text(this.state.activeFileGenCoverage + ' ');
    },
    renderFilesBlock: function renderBlock(filesList, parent) {

        return filesList.map(function (fileName)
        {
            handleFileChange: function handleFileChange(event) {

                event.preventDefault();
                parent.setState({activeFileGenCoverage: event.target.id});
                $('#genCoverageBtn').children().first().text(parent.state.activeFileGenCoverage);

            }
            ;

            return (React.createElement("li", {onClick: handleFileChange, id: fileName}, fileName));
        })
    },
    render: function () {
        return React.createElement('div', {className: 'btn-group'},
                React.createElement('button', {className: 'btn btn-default dropdown-toggle',
                    'data-toggle': "dropdown", 'aria-haspopup': "true", 'aria-expanded': 'false', id: 'genCoverageBtn'},
                        this.state.activeFileGenCoverage + ' ',
                        React.createElement('span', {className: 'caret'})),
                React.createElement('ul', {className: 'dropdown-menu'},
                        this.renderFilesBlock(this.props.filesList, this))
                )
    }


});

var FilesDropdownVariants = React.createClass({displayName: "FilesDropdownVariants",
    getInitialState: function () {
        return {activeFileVariants: this.props.filesList[0]};
    },
    componentWillReceiveProps: function (newProperties) {
        this.state.activeFileVariants = newProperties.filesList[0];
        $('#variantsBtn').children().first().text(this.state.activeFileVariants + ' ');
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
        return React.createElement('div', {className: 'btn-group'},
                React.createElement('button', {className: 'btn btn-default dropdown-toggle',
                    'data-toggle': "dropdown", 'aria-haspopup': "true", 'aria-expanded': 'false', id: 'variantsBtn'},
                        this.state.activeFileVariants + ' ',
                        React.createElement('span', {className: 'caret'})),
                React.createElement('ul', {className: 'dropdown-menu'},
                        this.renderFilesBlockVariants(this.props.filesList, this))
                )
    }



});

var FilesDropdownAnnotation = React.createClass({displayName: "FilesDropdownAnnotation",
    getInitialState: function () {
        return {activeFileAnnotation: this.props.filesList[0]};
    },
    componentWillReceiveProps: function (newProperties) {
        this.state.activeFileAnnotation = newProperties.filesList[0];
        $('#annotationBtn').children().first().text(this.state.activeFileAnnotation + ' ');
    },
    renderFilesBlock: function renderFilesBlock(filesList, parent) {

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
        return React.createElement('div', {className: 'btn-group'},
                React.createElement('button', {className: 'btn btn-default dropdown-toggle',
                    'data-toggle': "dropdown", 'aria-haspopup': "true", 'aria-expanded': 'false', id: 'annotationBtn'},
                        this.state.activeFileAnnotation + ' ',
                        React.createElement('span', {className: 'caret'})),
                React.createElement('ul', {className: 'dropdown-menu'},
                        this.renderFilesBlock(this.props.filesList, this))
                )
    }



});

var FilesDropdownTransCoverage = React.createClass({displayName: "FilesDropdownTransCoverage",
    getInitialState: function () {
        return {activeFileTransCoverage: this.props.filesList[0]};
    },
    componentWillReceiveProps: function (newProperties) {
        this.state.activeFileTransCoverage =  newProperties.filesList[0];
        $('#transCoverageBtn').children().first().text(this.state.activeFileTransCoverage);
    },
    renderFilesBlock: function renderBlock(filesList, parent) {

        return filesList.map(function (fileName)
        {
            handleFileChange: function handleFileChange(event) {

                event.preventDefault();
                parent.setState({activeFileTransCoverage: event.target.id});
                $('#transCoverageBtn').children().first().text(parent.state.activeFileTransCoverage);

            }
            ;

            return (React.createElement("li", {onClick: handleFileChange, id: fileName}, fileName));
        })
    },
    render: function () {
        return React.createElement('div', {className: 'btn-group'},
                React.createElement('button', {className: 'btn btn-default dropdown-toggle',
                    'data-toggle': "dropdown", 'aria-haspopup': "true", 'aria-expanded': 'false', id: 'transCoverageBtn'},
                        this.state.activeFileTransCoverage + ' ',
                        React.createElement('span', {className: 'caret'})),
                React.createElement('ul', {className: 'dropdown-menu'},
                        this.renderFilesBlock(this.props.filesList, this))
                )
    }


});

var FilesDropdownExpression = React.createClass({displayName: "FilesDropdownExpression",
    getInitialState: function () {
        return {activeFileExpression: this.props.filesList[0]};
    },
    componentWillReceiveProps: function (newProperties) {
        this.state.activeFileExpression = newProperties.filesList[0];
        $('#expressionBtn').children().first().text(this.state.activeFileExpression + ' ');
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
        return React.createElement('div', {className: 'btn-group'},
                React.createElement('button', {className: 'btn btn-default dropdown-toggle',
                    'data-toggle': "dropdown", 'aria-haspopup': "true", 'aria-expanded': 'false', id: 'expressionBtn'},
                        this.state.activeFileExpression + ' ',
                        React.createElement('span', {className: 'caret'})),
                React.createElement('ul', {className: 'dropdown-menu'},
                        this.renderFilesBlock(this.props.filesList, this))
                )
    }



});

var FilesDropdownDifExpression = React.createClass({displayName: "FilesDropdownDifExpression",
    getInitialState: function () {
        return {activeFileDifExpression: this.props.filesList[0]};
    },
    componentWillReceiveProps: function (newProperties) {
        this.state.activeFileDifExpression = newProperties.filesList[0];
        $('#difExpressionBtn').children().first().text(parent.state.activeFileDifExpression + ' ');
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
        return React.createElement('div', {className: 'btn-group'},
                React.createElement('button', {className: 'btn btn-default dropdown-toggle',
                    'data-toggle': "dropdown", 'aria-haspopup': "true", 'aria-expanded': 'false', id: 'difExpressionBtn'},
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
                        list[0] = '---';
                        for (var i = 0; i < n; i++)
                        {
                            list[i+1] = filesSplited[i];
                        }
                    } else
                    {
                        var filesSplited = filesList.split("\t");
                        var list = [];
                        list[0] = '---';
                        for (var i = 0; i < filesSplited.length; i++)
                        {
                            if (filesSplited[i] !== "")
                            {
                                var filesSplited2 = filesSplited[i].split("/");
                                var fileName = filesSplited2[filesSplited2.length - 1];
                                list[i+1] = fileName;
                            }
                        }


                    }

                    if (list.length > 0) {
                        if (type === "sequence")
                            return React.render(React.createElement('div', {className: 'container'},
                                    React.createElement(FilesDropdownSequence, {filesList: list, fileType: type})),
                                    document.getElementById('sequence'));

                        if (type === "variants")
                            return (React.createElement('div', null,
                                    React.render(React.createElement('div', {className: 'container'},
                                            React.createElement(FilesDropdownGenCoverage, {filesList: list, fileType: type})),
                                            document.getElementById('genCoverage')),
                                    React.render(React.createElement('div', {className: 'container'},
                                            React.createElement(FilesDropdownVariants, {filesList: list, fileType: type})),
                                            document.getElementById('variants'))));

                        if (type === "bedcov")
                            return React.render(React.createElement('div', {className: 'container'},
                                    React.createElement(FilesDropdownTransCoverage, {filesList: list, fileType: type})),
                                    document.getElementById('transCoverage'));

                        if (type === "expression")
                            return React.render(React.createElement('div', {className: 'container'},
                                    React.createElement(FilesDropdownExpression, {filesList: list, fileType: type})),
                                    document.getElementById('expression'));

                        if (type === "difExpression")
                            return React.render(React.createElement('div', {className: 'container'},
                                    React.createElement(FilesDropdownDifExpression, {filesList: list, fileType: type})),
                                    document.getElementById('difExpression'));

                        if (type === "annotation")
                            return React.render(React.createElement('div', {className: 'container'},
                                    React.createElement(FilesDropdownAnnotation, {filesList: list, fileType: type})),
                                    document.getElementById('annotation'));

                    }
                }
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
                React.createElement('label', {for : 'genCoverageBtn'}, 'Genomic coverage: '),
                React.createElement('div', {className: 'container', id: 'genCoverage'},
                        React.createElement(this.contentUpdateProject, {panelType: "variants"})),
                React.createElement('br'),
                React.createElement('label', {for : 'variantsBtn'}, 'SNP density: '),
                React.createElement('div', {className: 'container', id: 'variants'}),
                React.createElement('br'),
                React.createElement('label', {for : 'transCoverageBtn'}, 'Transcriptiomic coverage: '),
                React.createElement('div', {className: 'container', id: 'transCoverage'},
                        React.createElement(this.contentUpdateProject, {panelType: "bedcov"})),
                React.createElement('br'),
                React.createElement('label', {for : 'expressionBtn'}, 'Genes expression: '),
                React.createElement('div', {className: 'container', id: 'expression'},
                        React.createElement(this.contentUpdateProject, {panelType: "expression"})),
                React.createElement('br'),
                React.createElement('label', {for : 'difExpressionBtn'}, 'Differential expression: '),
                React.createElement('div', {className: 'container', id: 'difExpression'},
                        React.createElement(this.contentUpdateProject, {panelType: "difExpression"})),
                React.createElement('br'),
                React.createElement('label', {for : 'annotationBtn'}, 'Annotation: '),
                React.createElement('div', {className: 'container', id: 'annotation'},
                        React.createElement(this.contentUpdateProject, {panelType: "annotation"})))
                );
    }
});

var renderFilesGeneralPanel = function () {
    React.render(React.createElement(FilesGeneralPanel), document.getElementById('filesContainer'));
};

