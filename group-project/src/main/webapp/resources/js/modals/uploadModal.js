/* global React */

var converter = new Showdown.converter();
//funkcja dla IE
String.prototype.endsWith = function(suffix) {
    return this.indexOf(suffix, this.length - suffix.length) !== -1;
};
var UploadModal = React.createClass({className: "uploadModal",
    componentDidMount: function componentDidMount() 
    {
        $(this.getDOMNode()).modal('show');
        $(this.getDOMNode()).on('hidden.bs.modal', this.props.handleHideUploadModal);
    },
    setFileType: function(type)
    {
        this.fileType = type;
    },
    getInitialState: function()
    {
        return { view: {
                isSequence: false, 
                isAnnotation: false, 
                isVariants: false,
                isAlignment: false, 
                isExpression: false, 
                isDiffExpression: false }};
    },
    handleChboxClicked: function(){
        this.state.view.isAnnotation = this.refs.isAnnot.getDOMNode().value;
    },
    recognizeFileType: function(e)
    {
        this.isSequence = false;
        this.isAlignment = false;
        this.isAnnotation = false;
        this.isVariants = false;
        this.isExpression = false;
        this.isDiffExpression = false;
        this.fileType = "";
//        e.preventDefault();
        var file = this.refs.fileUpload.getDOMNode().files[0];
        
        if (file.name.endsWith(".fasta") || file.name.endsWith(".fa") || file.name.endsWith(".frn") || file.name.endsWith(".ffn"))
        {
            this.fileType = "sequence";
            this.isSequence=true;
        }
        else if (file.name.endsWith(".gff") || file.name.endsWith(".gtf") || file.name.endsWith(".gff2") || file.name.endsWith(".gff3"))
        {
            this.fileType = "annotation";
            this.isAnnotation=true;
        }
        else if (file.name.endsWith(".vcf"))
        {
            this.fileType = "variants";
            this.isVariants = true;
        }
        else if (file.name.endsWith(".bam") || file.name.endsWith(".sam"))
        {
            this.fileType = "alignment";
            this.isAlignment = true;
        }
        else
        {
            var fd = new FormData();    
            fd.append('file', file);
            $.ajax({
            url: "/recognizeFile",
            type: 'POST',
            processData: false,
            contentType: false,
            data: fd,
            success: function (data) 
            {
                console.log(data.errors);
                return (this.fileType = data.errors);
            },
            error: function (status, err) {
                console.error(status, err.toString());
            }});
        }
        console.log("FileType inside: " + this.fileType + " seq: " + this.isSequence + " ano: "+ this.isAnnotation + " var: " + this.isVariants + " ali: " + this.isAlignment);
    },
    handleSubmit: function(e){
        e.preventDefault();
        if(this.fileType==="")
        {    
            this.recognizeFileType();
        }
//        console.log("FileType: " + this.fileType + " seq: " + this.isSequence + " ano: "+ this.isAnnotation + " var: " + this.isVariants + " ali: " + this.isAlignment);
        if (this.fileType !== "unrecognized")
        {
            var file = this.refs.fileUpload.getDOMNode().files[0];
            var fd = new FormData();    
            fd.append('file', file);
            fd.append('String', this.fileType);
            $.ajax({
            url: "/controller/upload",
            type: 'POST',
            processData: false,
            contentType: false,
            data: fd,
            success: function (data) 
            {
                if(data.errors === null)
                {
                    location = '/home';
                }else
                {
                    alert("Error with pasing file");
                }
            },
            error: function (status, err) {
                alert("File not sended");
                console.error(status, err.toString());
            }});
        }
//        {
//            var newFile = {};
//            newFile.fileType=fileType;
//            newFile.filePath=filePath;
//            newFile.fileName=file.name;
//            console.log(file.name+" "+fileType);
//            $.ajax({
//            url: "/controller/upload",
//            type: 'POST',
//            dataType: 'json',
//            contentType: "application/json; charset=utf-8",
//            data: JSON.stringify(newFile),
//            success: function (data) {
//                if(data.errors === null)
//                {
//                    location = '/home';
//                }else
//                {
//                    alert("Error with passing file");
//                }
//            },
//            error: function (status, err) {
//                console.log("File not sended");
//                console.error(status, err.toString());
//            }
//        }); 
//        }
//        else
//        {
//            alert("File cannot be read");
//        }

    },
    render: function () {
        this.isSequence = false;
        this.isAlignment = false;
        this.isAnnotation = false;
        this.isVariants = false;
        this.isExpression = false;
        this.isDiffExpression = false;
        this.fileType = "";
        return (React.createElement('div', {className: 'modal fade'},
                React.createElement('div', {className: 'modal-dialog'},
                        React.createElement('div', {className: 'modal-content'},
                                React.createElement('div', {className: 'modal-header'},
                                        React.createElement('button', {type: 'button', className: 'close',
                                            'data-dismiss': 'modal', 'aria-label': 'Close'},
                                                React.createElement('span', {'aria-hidden': 'true'}, '\xD7')),
                                        React.createElement('h3', {className: 'modal-title'}, 'Upload file')),
                                React.createElement('div', {className: 'modal-body'},
                                        React.createElement('h4', {className: 'modal-title'}, 'Choose file: '),
                                        React.createElement('input', {type: 'file', ref: 'fileUpload'}),
                                        React.createElement("hr"),
                                        React.createElement('h4', {className: 'modal-title'}, 'What is file content? '),
//                                        React.createElement('button', {className: 'btn btn-primary', bsSize: "small", onClick: this.recognizeFileType},'Recognize file type')),
                                        React.createElement('input', {type: "checkbox", ref: 'isSeque'}, " Sequence"),
                                        React.createElement('br'),
                                        React.createElement('input', {type: "checkbox", ref: 'isAlign'}, " Alignment"),
                                        React.createElement('br'),
                                        React.createElement('input', {type: "checkbox", ref: 'isVaria'}, " Variants"),
                                        React.createElement('br'),
                                        React.createElement('input', {type: "checkbox", ref: 'isExpre'}, " Expression"),
                                        React.createElement('br'),
                                        React.createElement('input', {type: "checkbox", ref: 'isDiffExpre'}, " Differential expression"),
                                        React.createElement('br'),
                                        React.createElement('input', {type: "checkbox", ref: 'isAnnot', onClick: this.handleChboxClicked}, "  Annotation")),
                                React.createElement('div', {className: 'modal-footer', 'float': 'left'},
                                        React.createElement('button', {className: 'btn btn-primary', onClick: this.handleSubmit},
                                                'Upload file')))
                        ))
                );
    },
    propTypes: {
        handleHideUploadModal: React.PropTypes.func.isRequired
    }
});