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
    recognizeFileByLine: function()
    {
        var ajaxSuccess = this.ajaxSuccess;
        var file = this.refs.fileUpload.getDOMNode().files[0];
        var projectName = this.props.projectName;
        var fd = new FormData();
        fd.append('file', file);
        fd.append('projectName', projectName);
        $.ajax({
            url: "/recognizeFileType",
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
    recognizeFileType: function (e)
    {   $('#annotationFileWarning').html("");
        $('input:checkbox').prop('checked', false);
        var ajaxSuccess = this.ajaxSuccess;
        e.preventDefault();
        var file = this.refs.fileUpload.getDOMNode().files[0];
        var projectName = this.props.projectName;
        var fileName = file.name;
        var fd = new FormData();
        fd.append('fileName', fileName);
        fd.append('projectName', projectName);
        $.ajax({
            url: "/recognizeFileName",
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
        $('#annotationFileWarning').width($('#annotationFileWarning').parents().first().width()-20)
        this.state.fileType = e.errors;
        
        if (this.state.fileType === "sequence") {
            $('#sequenceChbox').prop('checked', true);
            $('#uploadBtn').attr('disabled', false);
        }
        else if (this.state.fileType === "annotation") {
            $('#annotationChbox').prop('checked', true);
            $('#uploadBtn').attr('disabled', false);
        }
        else if (this.state.fileType === "variants") {
            $('#variantsChbox').prop('checked', true);
            $('#uploadBtn').attr('disabled', false);
        }
        else if (this.state.fileType === "expression") {
            $('#expressionChbox').prop('checked', true);
            $('#uploadBtn').attr('disabled', false);
        }
        else if (this.state.fileType === "difExpression") {
            $('#difExpressionChbox').prop('checked', true);
            $('#uploadBtn').attr('disabled', false);
        }
        else if (this.state.fileType === "bedcov") {
            $('#bedcovChbox').prop('checked', true);
            $('#uploadBtn').attr('disabled', false);
        }
        else if (this.state.fileType === "") {
            this.recognizeFileByLine();
            $('#uploadBtn').attr('disabled', false);
        }
        else if (this.state.fileType === "existing" || this.state.fileType === "zipped")
        {
            $('#uploadBtn').attr('disabled', false);
        }
        else
        {
            React.render(React.createElement('div', {className: 'alert alert-danger', role: 'alert'}, 
            React.createElement('strong', null, 'Warning! '), "File type not recognized!"), document.getElementById('annotationFileWarning'));
            $('#annotationFileWarning').width($('#annotationFileWarning').parents().first().width()-20);
            $('#uploadBtn').attr('disabled', true);
        }
        
        // next conditional
        if(e.message !== "" && e.message !== null){
            React.render(React.createElement('div', {className: 'alert alert-danger', role: 'alert'}, 
            React.createElement('strong', null, 'Error! '), e.message), document.getElementById('annotationFileWarning'));
            $('#uploadBtn').attr('disabled', true);
        }
        else {
            $('#uploadBtn').attr('disabled', false);
        }
    },
    handleSubmit: function (e) {
        e.preventDefault();
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
                    if (data.message !== "" && data.message !== null)
                        alert(data.message);
                    else
                        location = '/home';
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
    changeBedcovChbox: function () {
        this.state.isBedcov = $('#bedcovChbox').prop('checked');
        $('input:checkbox').not('#bedcovChbox').prop('checked', false);
        console.log('Is bedcov? ' + this.state.isBedcov);
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
                                        React.createElement('div', {className: 'container', id: 'annotationFileWarning'}),
                                        React.createElement('input', {type: "checkbox", id: 'sequenceChbox', onChange: this.changeSeqChbox}, " Sequence"),
                                        React.createElement('br'),
                                        React.createElement('input', {type: "checkbox", id: 'annotationChbox', onChange: this.changeAnnotChbox}, " Annotation"),
                                        React.createElement('br'),
                                        React.createElement('input', {type: "checkbox", id: 'bedcovChbox', onChange: this.changeBedcovChbox}, " Coverage"),
                                        React.createElement('br'),
                                        React.createElement('input', {type: "checkbox", id: 'variantsChbox', onChange: this.changeVarChbox}, " Variants"),
                                        React.createElement('br'),
                                        React.createElement('input', {type: "checkbox", id: 'expressionChbox', onChange: this.changeExprChbox}, " Expression"),
                                        React.createElement('br'),
                                        React.createElement('input', {type: "checkbox", id: 'difExpressionChbox', onChange: this.changeDifExprChbox}, " Differential expression"),
                                        React.createElement('br')),
                                React.createElement('div', {className: 'modal-footer', 'float': 'left'},
                                        React.createElement('button', {id: 'uploadBtn', className: 'btn btn-primary', onClick: this.handleSubmit},
                                                'Upload file')))
                        ))
                );
    },
    propTypes: {
        handleHideUploadModal: React.PropTypes.func.isRequired,
        projectName: React.PropTypes.string
    }
});
