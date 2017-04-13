/* global React */

var converter = new Showdown.converter();
//funkcja dla IE
String.prototype.endsWith = function (suffix) {
    return this.indexOf(suffix, this.length - suffix.length) !== -1;
};
var UploadModal = React.createClass({className: "uploadModal",
    componentDidMount: function componentDidMount()
    {
        $(this.getDOMNode()).modal('show');
        $(this.getDOMNode()).on('hidden.bs.modal', this.props.handleHideUploadModal);
    },
    setFileType: function (type)
    {
        this.state.fileType = type;
    },
    getInitialState: function ()
    {
        return {fileType: null};
    },
    recognizeFileType: function (e)
    {
        $('input:checkbox').prop('checked', false);
        var ajaxSuccess = this.ajaxSuccess;

        e.preventDefault();
        var file = this.refs.fileUpload.getDOMNode().files[0];
        var projectName = this.props.projectName;
        var fd = new FormData();
        fd.append('file', file);
        fd.append('projectName', projectName);
        $.ajax({
            url: "/recognizeFile",
            type: 'POST',
            processData: false,
            contentType: false,
            data: fd,
            success: ajaxSuccess,
            error: function (status, err) {
                console.error(status, err.toString());
            }
        });
    },
    ajaxSuccess: function (e) {
        this.state.fileType = e.errors;
        if (e.message !== "") {
            React.render(React.createElement('div', {className: 'alert alert-warning', role: 'alert'}, 
            React.createElement('strong', null, 'Warning! '), e.message), document.getElementById('annotationFileWarning'));
            $('#annotationFileWarning').width($('#annotationFileWarning').parents().first().width()-20);
        }
        if (this.state.fileType === "sequence") {
            $('#sequenceChbox').prop('checked', true);
        } else if (this.state.fileType === "annotation") {
            $('#annotationChbox').prop('checked', true);
        } else if (this.state.fileType === "variants") {
            $('#variantsChbox').prop('checked', true);
        } else if (this.state.fileType === "expression") {
            $('#expressionChbox').prop('checked', true);
        } else if (this.state.fileType === "difExpression") {
            $('#difExpressionChbox').prop('checked', true);

        }
    },
    handleSubmit: function (e) {
        e.preventDefault();
        console.log('test state: ' + this.state.fileType)
        if (this.state.fileType === "")
        {
            this.recognizeFileType();
        }
        if (this.state.fileType !== "unrecognized")
        {
            var file = this.refs.fileUpload.getDOMNode().files[0];
            var projectName = this.props.projectName;

            var fd = new FormData();
            fd.append('file', file);
            fd.append('projectName', projectName);
            fd.append('fileType', this.state.fileType);
            $.ajax({
                url: "/controller/upload",
                type: 'POST',
                processData: false,
                contentType: false,
                data: fd,
                success: function (data)
                {
                    if (data.errors === null)
                    {
                        location = '/home';
                    } else
                    {
                        alert("Error with pasing file");
                    }
                },
                error: function (status, err) {
                    alert("File not sended");
                    console.error(status, err.toString());
                }});
        }
    },
    changeSeqChbox: function () {
        this.state.isSequence = $('#sequenceChbox').prop('checked');
        $('input:checkbox').not('#sequenceChbox').prop('checked', false);
        console.log('Is sequence? ' + this.state.isSequence);
    },
    changeAnnotChbox: function () {
        this.state.isAnnotation = $('#annotationChbox').prop('checked');
        $('input:checkbox').not('#annotationChbox').prop('checked', false);
        console.log('Is annotation? ' + this.state.isAnnotation);
    },
    changeVarChbox: function () {
        this.state.isVariants = $('#variantsChbox').prop('checked');
        $('input:checkbox').not('#variantsChbox').prop('checked', false);
        console.log('Is variants? ' + this.state.isVariants);
    },
    changeExprChbox: function () {
        this.state.isExpression = $('#expressionChbox').prop('checked');
        $('input:checkbox').not('#expressionChbox').prop('checked', false);
        console.log('Is expression? ' + this.state.isExpression);
    },
    changeDifExprChbox: function () {
        this.state.isDifExpression = $('#difExpressionChbox').prop('checked');
        $('input:checkbox').not('#difExpressionChbox').prop('checked', false);
        console.log('Is differential expression? ' + this.state.isDifExpression);
    },
    render: function () {
        return (React.createElement('div', {className: 'modal fade'},
                React.createElement('div', {className: 'modal-dialog'},
                        React.createElement('div', {className: 'modal-content'},
                                React.createElement('div', {className: 'modal-header'},
                                        React.createElement('button', {type: 'button', className: 'close',
                                            'data-dismiss': 'modal', 'aria-label': 'Close'},
                                                React.createElement('span', {'aria-hidden': 'true'}, '\xD7')),
                                        React.createElement('h3', {className: 'modal-title'}, 'Upload file')),
                                React.createElement('div', {className: 'modal-body', id: 'uploadPanelBody'},
                                        React.createElement('h4', {className: 'modal-title'}, 'Choose file: '),
                                        React.createElement('input', {type: 'file', ref: 'fileUpload', onChange: this.recognizeFileType}),
                                        React.createElement("hr"),
                                        React.createElement('h4', {className: 'modal-title'}, 'What is file content? '),
//                                        React.createElement('button', {className: 'btn btn-primary', bsSize: "small", onClick: this.recognizeFileType}, 'Recognize file type'),
//                                        React.createElement('br'),
                                        React.createElement('div', {className: 'container', id: 'annotationFileWarning'}),
                                        React.createElement('br'),
                                        React.createElement('input', {type: "checkbox", id: 'sequenceChbox', onChange: this.changeSeqChbox}, " Sequence"),
                                        React.createElement('br'),
                                        React.createElement('input', {type: "checkbox", id: 'annotationChbox', onChange: this.changeAnnotChbox}, " Annotation"),
                                        React.createElement('br'),
                                        React.createElement('input', {type: "checkbox", id: 'variantsChbox', onChange: this.changeVarChbox}, " Variants"),
                                        React.createElement('br'),
                                        React.createElement('input', {type: "checkbox", id: 'expressionChbox', onChange: this.changeExprChbox}, " Expression"),
                                        React.createElement('br'),
                                        React.createElement('input', {type: "checkbox", id: 'difExpressionChbox', onChange: this.changeDifExprChbox}, " Differential expression"),
                                        React.createElement('br')),
                                React.createElement('div', {className: 'modal-footer', 'float': 'left'},
                                        React.createElement('button', {className: 'btn btn-primary', onClick: this.handleSubmit},
                                                'Upload file')))
                        ))
                );
    },
    propTypes: {
        handleHideUploadModal: React.PropTypes.func.isRequired,
        projectName: React.PropTypes.string
    }
});
