var converter = new Showdown.converter();

var UploadModal = React.createClass({className: "uploadModal",
    componentDidMount: function componentDidMount() {
        $(this.getDOMNode()).modal('show');
        $(this.getDOMNode()).on('hidden.bs.modal', this.props.handleHideUploadModal);
    },
    handleSubmit: function(e){
        e.preventDefault();
        var file = this.refs.fileUpload.getDOMNode().files[0];
        var filePath = this.refs.fileUpload.getDOMNode().value;
        var fileType="";
        var isSequence=false;
        var isAnnotation=false;
        var isVariants=false;
        var isAlignment=false;
        if (file.name.endsWith(".fasta") || file.name.endsWith(".fa") || file.name.endsWith(".frn") || file.name.endsWith(".ffn"))
        {
            fileType = "sequence";
            isSequence=true;
        }
        else if (file.name.endsWith(".gff") || file.name.endsWith(".gtf") || file.name.endsWith(".gff2") || file.name.endsWith(".gff3"))
        {
            fileType = "annotation";
            isAnnotation=true;
        }
        else if (file.name.endsWith(".vcf"))
        {
            fileType = "variants";
            isVariants = true;
        }
        else if (file.name.endsWith(".bam") || file.name.endsWith(".sam"))
        {
            fileType = "alignment";
            isAlignment = true;
        }
        else
        {
            fileType = "unrecognized";
        }
        
        if (fileType !== "unrecognized")
        {
            var newFile = {};
            newFile.fileType=fileType;
            newFile.filePath=filePath;
            newFile.fileName=file.name;
            console.log(file.name+" "+fileType);
            $.ajax({
            url: "/controller/upload",
            type: 'POST',
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(newFile),
            success: function (data) {
                if(data.errors === null)
                {
                    location = '/home';
                }else
                {
                    alert("Error with passing file");
                }
            },
            error: function (status, err) {
                console.log("File not sended");
                console.error(status, err.toString());
            }
        }); 
        }
        else
        {
            alert("File cannot be read");
        }

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
                                React.createElement('div', {className: 'modal-body'},
                                        React.createElement('h4', {className: 'modal-title'}, 'Choose file: '),
                                        React.createElement('input', {type: 'file', ref: 'fileUpload'}),
                                        React.createElement("hr"),
                                        React.createElement('h4', {className: 'modal-title'}, 'What is file content? '),
                                        React.createElement('br'),
                                        React.createElement('input', {type: "checkbox", ref: 'isSeq', checked: true}, " Sequence"),
                                        React.createElement('br'),
                                        React.createElement('input', {type: "checkbox", ref: 'isAlign'}, " Alignment"),
                                        React.createElement('br'),
                                        React.createElement('input', {type: "checkbox", ref: 'isVar'}, " Variants"),
                                        React.createElement('br'),
                                        React.createElement('input', {type: "checkbox", ref: 'isExpr'}, " Expression"),
                                        React.createElement('br'),
                                        React.createElement('input', {type: "checkbox", ref: 'isDiffExpr'}, " Differential expression")),
                                React.createElement('div', {className: 'modal-footer', 'float': 'left'},
                                        React.createElement('button', {className: 'btn btn-primary', onClick: this.handleSubmit},
                                                'Upload file')))
                        ))
                );
    },
    propTypes: {
        handleHideUploadModal: React.PropTypes.func.isRequired
    }
})